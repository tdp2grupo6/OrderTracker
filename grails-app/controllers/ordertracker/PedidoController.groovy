package ordertracker

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.transaction.Transactional
import ordertracker.Estados.EstadoPedido
import ordertracker.Filtros.FiltroPedido
import ordertracker.Filtros.FiltroResultado
import ordertracker.Perfiles.Vendedor
import org.codehaus.groovy.runtime.StringGroovyMethods

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class PedidoController {
    static responseFormats = ['json']
    static allowedMethods = [index: "GET", save: "POST", update: "PUT", delete: "DELETE", show: "GET", search: "GET",
                             searchByCliente: "GET", searchByEstado: "GET", filtroAdmin: "POST", descontarStock: "GET"]

    def springSecurityService

    def index(Integer max) {
        if (SpringSecurityUtils.ifAllGranted(Utils.VENDEDOR)) {
            Vendedor v = springSecurityService.currentUser
            def res = Pedido.findAllByVendedor(v)
            respond res, [status: OK]
        }
        else {
            //params.max = Math.min(max ?: 10, 100)
            respond Pedido.list(params), [status: OK]
        }
    }

    def show(Pedido p) {
        if (!p) {
            render status: NOT_FOUND
        }
        else {
            respond p, [status: OK]
        }
    }

    def searchByCliente(Integer max) {
        //params.max = Math.min(max ?: 10, 100)
        def prod = Pedido.createCriteria()
        def result = prod.list(params) {
            eq("cliente.id", Long.parseLong("$params.id"))
        }
        respond result, model:[status: OK, totalResultados: result.count]
    }

    def searchByEstado(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def prod = Pedido.createCriteria()
        def result = prod.list(params) {
            eq("estado", Integer.parseInt("$params.id"))
        }
        respond result, model:[status: OK, totalResultados: result.count]
    }

    def filtroAdmin(FiltroPedido fp) {
        int offset = fp.primerValor()
        int maxPage = Utils.RESULTADOS_POR_PAGINA
        def prod = Pedido.createCriteria()
        def result = prod.list (max: maxPage, offset: offset) {
            and {
                if (fp.estado) {
                    eq("estado", EstadoPedido.obtenerEstado(fp.estado))
                }
                if (fp.idCliente) {
                    eq("cliente", Cliente.findById(fp.idCliente))
                }
                if (fp.idVendedor) {
                    eq("vendedor", Vendedor.findById(fp.idVendedor))
                }
                if (fp.fechaInicio && fp.fechaFin) {
                    between("fechaRealizado", fp.fechaInicio, fp.fechaFin.clearTime()+1)
                }
                else if (fp.fechaInicio) {
                    ge("fechaRealizado", fp.fechaInicio.clearTime())
                }
                else if (fp.fechaFin) {
                    lt("fechaRealizado", fp.fechaFin.clearTime()+1)
                }
            }
        }
        int pagina = fp.pagina? fp.pagina : 1
        result.sort { it.id }
        FiltroResultado respuesta = new FiltroResultado(pagina, result.totalCount, result as List)
        respond respuesta, model:[status: OK, totalResultados: result.totalCount]
    }

    @Transactional
    def save(Pedido pedidoInstance) {
        if (pedidoInstance == null) {
            render status: NOT_FOUND
            return
        }

        if (SpringSecurityUtils.ifAllGranted(Utils.VENDEDOR) && !(pedidoInstance.vendedor)) {
            Vendedor v = springSecurityService.currentUser
            pedidoInstance.vendedor = v
        }

        pedidoInstance.actualizarTotal()

        pedidoInstance.validate()
        if (pedidoInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        pedidoInstance.save flush:true

        if (pedidoInstance.visita) {
            pedidoInstance.visita.pedido = pedidoInstance
        }

        respond pedidoInstance, [status: OK]
    }

    @Transactional
    def update(Pedido pedidoInstance) {
        if (pedidoInstance == null) {
            render status: NOT_FOUND
            return
        }

        pedidoInstance.validate()
        if (pedidoInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        if (pedidoInstance.visita) {
            pedidoInstance.visita.pedido = pedidoInstance
        }

        pedidoInstance.save flush:true
        respond pedidoInstance, [status: OK]
    }

    @Transactional
    def delete(Pedido pedidoInstance) {
        if (pedidoInstance == null) {
            render status: NOT_FOUND
            return
        }

        pedidoInstance.delete flush:true
        render status: OK
    }

    @Transactional
    def descontarStock() {
        long pedidoId = StringGroovyMethods.isLong(params.id)? StringGroovyMethods.toLong(params.id) : 0
        Pedido pedidoInstance = Pedido.findById(pedidoId)

        if (pedidoInstance == null) {
            render status: NOT_FOUND
            return
        }

        boolean checkStock = true

        for (PedidoElemento pe in pedidoInstance.elementos) {
            Producto base = Producto.findById(pe.producto.id)
            if (base.stock < pe.cantidad) {
                checkStock = false
            }
        }

        if (checkStock && !(pedidoInstance.elementos.isEmpty())) {
            ArrayList<Producto> lista = []

            for (PedidoElemento pe in pedidoInstance.elementos) {
                Producto base = Producto.findById(pe.producto.id)
                base.stock -= pe.cantidad
                base.save flush:true
                lista.add(base)
            }

            respond lista, [status: OK]
        }
        else {
            render status: NOT_ACCEPTABLE
        }
    }
}