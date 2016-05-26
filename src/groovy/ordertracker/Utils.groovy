package ordertracker

import ordertracker.Estados.EstadoCliente
import ordertracker.Perfiles.Vendedor
import ordertracker.Security.Perfil
import ordertracker.Security.Rol
import ordertracker.Security.UsuarioRol
import org.codehaus.groovy.grails.plugins.qrcode.QrCodeService
import org.springframework.core.io.ByteArrayResource
/**
 * Created by dgacitua on 26-04-16.
 */
class Utils {
    // Paginación
    static public final int RESULTADOS_POR_PAGINA = 20

    // Códigos de días de la semana
    static public final int DOMINGO = 0
    static public final int LUNES = 1
    static public final int MARTES = 2
    static public final int MIERCOLES = 3
    static public final int JUEVES = 4
    static public final int VIERNES = 5
    static public final int SABADO = 6

    // Verificador de semana
    static public final List<Integer> SEMANA = [DOMINGO, LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO]

    // Formatos de Fecha y Hora
    static public final String dateFormat = "yyyy-MM-dd'T'HH:mm:ssZ"
    static public final TimeZone timeZone = TimeZone.getTimeZone("America/Buenos_Aires")

    // Permisos de perfiles de Usuario
    static public final String ADMIN = Perfil.ADMIN.valor
    static public final String VENDEDOR = Perfil.VENDEDOR.valor
    static public final String CLIENTE = Perfil.CLIENTE.valor

    // Funciones auxiliares
    static int enteroAleatorio(int min, int max) {
        return Math.abs(new Random().nextInt() % max) + min
    }

    static Date fechaAleatoria(Range<Date> range) {
        return range.from + new Random().nextInt(range.to - range.from + 1)
    }

    static Pedido crearPedidoAleatorio() {
        Cliente cl = Cliente.findById(enteroAleatorio(1, Cliente.count))
        Vendedor v = vendedorCliente(cl)
        int numProductos = enteroAleatorio(1, 3)
        ArrayList<PedidoElemento> pd = new ArrayList<PedidoElemento>()
        Date fin = new Date()
        Date ini = fin - 15

        for (int i=1; i<=numProductos; i++) {
            Producto p = Producto.findById(enteroAleatorio(1, Producto.count))
            if (!(pd.producto).contains(p)) {
                int numItems = enteroAleatorio(1, 5)
                pd.add(new PedidoElemento(producto: p, cantidad: numItems))
            }
        }

        Date fecha = fechaAleatoria(ini..fin)
        Visita vis = new Visita(cliente: cl, vendedor: v, fechaVisita: fecha).save(flush: true)
        return new Pedido(cliente: cl, vendedor: v, elementos: pd, fechaRealizado: fecha, visita: vis)
    }

    static Vendedor vendedorAleatorio() {
        Rol rol = Rol.findByAuthority(VENDEDOR)
        def vends = UsuarioRol.findAllByRol(rol).usuario
        return (Vendedor)(vends.sort{new Random()}?.take(1)[0])
    }

    static Vendedor vendedorCliente(Cliente cl) {
        Vendedor resp = null
        Rol rol = Rol.findByAuthority(VENDEDOR)
        def vends = UsuarioRol.findAllByRol(rol).usuario

        vends.each {
            Vendedor temp = (Vendedor)it
            if (temp.clientes.contains(cl)) {
                resp = it
            }
        }

        return resp? resp : vendedorAleatorio()
    }

    static Date parsearFechaEntrada(String fecha) {
        Date ahora = new Date()
        return ahora.parse(dateFormat, fecha)       // http://snipplr.com/view/10814/
    }

    static int retornarCodigoDia(Date fecha) {
        switch (fecha[Calendar.DAY_OF_WEEK]) {
            case Calendar.SUNDAY:
                return DOMINGO
            case Calendar.MONDAY:
                return LUNES
            case Calendar.TUESDAY:
                return MARTES
            case Calendar.WEDNESDAY:
                return MIERCOLES
            case Calendar.THURSDAY:
                return JUEVES
            case Calendar.FRIDAY:
                return VIERNES
            case Calendar.SATURDAY:
                return SABADO
            default:
                return -1
        }
    }
    static boolean verificarDia(Date fecha, int codigoDia) {
        return (SEMANA.contains(codigoDia) && retornarCodigoDia(fecha)==codigoDia)
    }

    // Actualización Estado Clientes
    static void actualizarClientesInicio() {
        println "[OT-LOG] Actualizando estados de los Clientes..."

        Date hoy = new Date()
        int codigoHoy = Utils.retornarCodigoDia(hoy)

        def crit1 = Visita.createCriteria()
        List<Visita> visitas = crit1.list() {
            and {
                ge('fechaVisita', hoy.clearTime())
                lt('fechaVisita', hoy.clearTime()+1)
            }
        }

        Cliente.list().each {
            if (visitas*.cliente.contains(it)) {
                it.estado = EstadoCliente.VERDE
            }
            else {
                if (it.agendaCliente.contains(codigoHoy)) {
                    it.estado = EstadoCliente.AMARILLO
                }
                else {
                    it.estado = EstadoCliente.ROJO
                }
            }
        }
    }

    static void actualizarClientesServicio() {
        println "[OT-LOG] Ejecutando servicio de actualización de clientes..."

        Date hoy = new Date().clearTime()
        Date ayer = hoy - 1
        int codigoHoy = Utils.retornarCodigoDia(hoy)

        Cliente.list().each {
            if (it.estado == EstadoCliente.VERDE) {
                if (it.agendaCliente.contains(codigoHoy)) {
                    it.estado = EstadoCliente.AMARILLO
                }
            }
            else if (it.estado == EstadoCliente.AMARILLO) {
                it.estado = EstadoCliente.ROJO
            }
        }

        def crit1 = Visita.createCriteria()
        def crit2 = Pedido.createCriteria()
        def crit3 = Comentario.createCriteria()

        List<Visita> visitas = crit1.list() {
            and {
                ge('fechaVisita', ayer)
                lt('fechaVisita', hoy)
            }
        } as List<Visita>

        List<Pedido> pedidos = crit2.list() {
            and {
                ge('fechaRealizado', ayer)
                lt('fechaRealizado', hoy)
            }
        } as List<Pedido>

        List<Comentario> comentarios = crit3.list() {
            and {
                ge('fechaComentario', ayer)
                lt('fechaComentario', hoy)
            }
        } as List<Comentario>

        for (Visita v: visitas) {
            if (v.pedido==null && v.comentario==null) {
                // Enlazar pedido
                for (Pedido p: pedidos) {
                    if (p.cliente==v.cliente) {
                        v.pedido = p
                        pedidos.remove(p)
                    }
                }
                // Enlazar comentario
                for (Comentario c: comentarios) {
                    if (c.cliente==v.cliente) {
                        v.comentario = c
                        comentarios.remove(c)
                    }
                }
            }
        }
    }

    static void copiarAgendaSimple(AgendaUpdate.AgendaSimple asim, AgendaDia adia) {
        if (SEMANA.contains(asim.codigoDia) && asim.codigoDia==adia.codigoDia) {
            adia.listaClientes = []
            adia.listaClientes = asim.listaClientes
        }
    }

    static List<Vendedor> otrosVendedoresAsociados(Vendedor v, Cliente cl) {
        def ls = Vendedor.executeQuery("from Vendedor v where :cliente in elements(v.clientes)", [cliente: cl])
        if (ls.contains(v)) { ls.remove(v) }
        return ls
    }

    // Genera un QR en base a un texto y lo copia a un InputStreamSource
    static ByteArrayResource generarQR(String texto) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        QrCodeService qr = new QrCodeService()
        qr.renderPng("${texto}", 300, baos)
        ByteArrayResource bar = new ByteArrayResource(baos.toByteArray())
        baos.close()
        return bar
    }
}