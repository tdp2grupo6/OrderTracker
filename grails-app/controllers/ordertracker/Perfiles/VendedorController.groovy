package ordertracker.Perfiles

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.transaction.Transactional
import ordertracker.Agenda
import ordertracker.AgendaDia
import ordertracker.Cliente
import ordertracker.Filtros.FiltroResultado
import ordertracker.Filtros.FiltroVendedor
import ordertracker.Pedido
import ordertracker.PushToken
import ordertracker.RequestTransfer
import ordertracker.Utils
import org.codehaus.groovy.runtime.StringGroovyMethods

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class VendedorController {
    static responseFormats = ['json']
    static allowedMethods = [index: "GET", show: "GET", save: "POST", update: "PUT", delete: "DELETE", refreshPushId: "POST",
                             filtroAdmin: "POST", listaCorta: "GET", transferirCliente: "POST", transferirClientes: "POST"]

    def springSecurityService
    def mensajePushService

    def index(Integer max) {
        //params.max = Math.min(max ?: 10, 100)
        respond Vendedor.list(params), [status: OK]
    }

    def show(Vendedor v) {
        if (!v) {
            render status: NOT_FOUND
        }
        else {
            respond v, [status: OK]
        }
    }

    def filtroAdmin(FiltroVendedor fv) {
        int offset = fv.primerValor()
        int maxPage = Utils.RESULTADOS_POR_PAGINA
        def prod = Vendedor.createCriteria()
        def result = prod.list (max: maxPage, offset: offset) {
            and {
                if (fv.username) {
                    ilike("username", "${fv.username}%")
                }
                if (fv.nombre) {
                    ilike("nombre", "${fv.nombre}%")
                }
                if (fv.apellido) {
                    ilike("apellido", "${fv.apellido}%")
                }
            }
        }
        int pagina = fv.pagina? fv.pagina : 1
        result.sort { it.id }
        FiltroResultado respuesta = new FiltroResultado(pagina, result.totalCount, result as List)
        respond respuesta, model:[status: OK, totalResultados: result.totalCount]
    }

    def listaCorta() {
        if (SpringSecurityUtils.ifAllGranted(Utils.ADMIN)) {
            def result = Vendedor.list().collect {
                [
                        id: it.id,
                        nombre: it.nombre,
                        apellido: it.apellido,
                        username: it.username
                ]
            }
            respond result, [status: OK]
        }
        else {
            // TODO eliminar
            def result = Vendedor.list().collect {
                [
                        id: it.id,
                        nombre: it.nombre,
                        apellido: it.apellido,
                        username: it.username
                ]
            }
            respond result, [status: OK]
        }
    }

    @Transactional
    def save(Vendedor vendedorInstance) {
        if (vendedorInstance == null) {
            render status: NOT_FOUND
            return
        }

        String pass = vendedorInstance.password

        vendedorInstance.validate()
        if (vendedorInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        vendedorInstance.save flush:true
        vendedorInstance.habilitarUsuario()
        respond vendedorInstance, [status: OK]

        Utils.log("Se va a enviar un correo a ${vendedorInstance.email}")
        sendMail {
            to "${vendedorInstance.email}"
            subject "Su registro como Vendedor en Order Tracker"
            html g.render (template:"nuevoVendedorTemplate", model:[nombreCompleto:"${vendedorInstance.nombre} ${vendedorInstance.apellido}", username: "${vendedorInstance.username}", password: "${pass}"])
        }
    }

    @Transactional
    def update(Vendedor vendedorInstance) {
        if (vendedorInstance == null) {
            render status: NOT_FOUND
            return
        }

        String pass = vendedorInstance.password

        boolean usernameEditado = vendedorInstance.isDirty('username')
        boolean passwordEditado = vendedorInstance.isDirty('password')

        vendedorInstance.validate()
        if (vendedorInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        vendedorInstance.save flush:true
        respond vendedorInstance, [status: OK]

        if (usernameEditado || passwordEditado) {
            String newPass = passwordEditado? pass : "< NO CAMBIA >"
            Utils.log("Se va a enviar un correo a ${vendedorInstance.email}")
            sendMail {
                to "${vendedorInstance.email}"
                subject "Sus credenciales como Vendedor en Order Tracker"
                html g.render (template:"reenviarVendedorTemplate", model:[nombreCompleto:"${vendedorInstance.nombre} ${vendedorInstance.apellido}", username: "${vendedorInstance.username}", password: "${newPass}"])
            }
        }
    }

    @Transactional
    def delete(Vendedor vendedorInstance) {

        if (vendedorInstance == null) {
            render status: NOT_FOUND
            return
        }

        vendedorInstance.eliminarInstancias()
        vendedorInstance.delete flush:true
        render status: OK
    }

    @Transactional
    def refreshPushToken(PushToken pt) {
        if (SpringSecurityUtils.ifAllGranted(Utils.VENDEDOR)) {
            Vendedor v = springSecurityService.currentUser
            v.pushToken = pt.token
            v.save flush:true

            Map respuesta = mensajePushService.mensajePush(v, "Order Tracker: Vendedor Asociado", "Se ha asociado este dispositivo a Order Tracker")

            if (respuesta['estadoMensajePush'].equals("SUCCESS")) {
                response.status = 200       // OK
                render (respuesta as JSON)
            }
            else {
                response.status = 404      // NOT_FOUND
                render (respuesta as JSON)
            }
        }
        else {
            render status: FORBIDDEN
        }
    }

    @Transactional
    def transferirCliente(RequestTransfer rt) {
        if (rt.vendedorViejo != null && rt.vendedorNuevo != null && rt.cliente != null && rt.vendedorViejo.clientes.contains(rt.cliente)) {
            // Actualizar agenda
            Agenda agendaVieja = rt.vendedorViejo.agenda
            Agenda agendaNueva = rt.vendedorNuevo.agenda

            for (int codigoDia in Utils.SEMANA) {
                AgendaDia diaViejo = AgendaDia.findByAgendaAndCodigoDia(agendaVieja, codigoDia);
                AgendaDia diaNuevo = AgendaDia.findByAgendaAndCodigoDia(agendaNueva, codigoDia);

                if (diaViejo.listaClientes.contains(rt.cliente.id)) {
                    diaViejo.listaClientes.remove(rt.cliente.id)
                    diaViejo.save flush:true

                    if (!diaNuevo.listaClientes.contains(rt.cliente.id)) {
                        diaNuevo.listaClientes.add(rt.cliente.id)
                        diaNuevo.save flush:true
                    }
                }
            }
            agendaVieja.save flush:true
            agendaNueva.save flush:true

            // Transferir clientes
            rt.vendedorViejo.removeFromClientes(rt.cliente)
            rt.vendedorNuevo.addToClientes(rt.cliente)

            rt.vendedorViejo.save flush:true
            rt.vendedorNuevo.save flush:true

            List<Vendedor> resp = [rt.vendedorViejo, rt.vendedorNuevo]
            respond resp, [status: OK]
        }
        else {
            render status: NOT_ACCEPTABLE
        }
    }


    @Transactional
    def transferirClientes(RequestTransfer rt) {
        if (rt.vendedorViejo != null && rt.vendedorNuevo != null) {
            // Cargar clientes a transferir
            Set<Cliente> listaClientes = []

            for (Cliente cl in rt.vendedorViejo.clientes) {
                listaClientes.add(cl)
            }

            // Actualizar agenda
            Agenda agendaVieja = rt.vendedorViejo.agenda
            Agenda agendaNueva = rt.vendedorNuevo.agenda

            for (int codigoDia in Utils.SEMANA) {
                AgendaDia diaViejo = AgendaDia.findByAgendaAndCodigoDia(agendaVieja, codigoDia);
                AgendaDia diaNuevo = AgendaDia.findByAgendaAndCodigoDia(agendaNueva, codigoDia);

                for (Cliente cl in listaClientes) {
                    if (diaViejo.listaClientes.contains(cl.id)) {
                        diaViejo.listaClientes.remove(cl.id)
                        diaViejo.save flush: true

                        if (!diaNuevo.listaClientes.contains(cl.id)) {
                            diaNuevo.listaClientes.add(cl.id)
                            diaNuevo.save flush: true
                        }
                    }
                }
            }
            agendaVieja.save flush:true
            agendaNueva.save flush:true

            // Actualizar pedidos
            Set<Pedido> listaPedidos = Pedido.findAllByVendedor(rt.vendedorViejo)

            for (Pedido p in listaPedidos) {
                p.vendedor = rt.vendedorNuevo
                p.save flush:true
            }

            // Transferir clientes
            for (Cliente cl in listaClientes) {
                rt.vendedorViejo.removeFromClientes(cl)
                rt.vendedorNuevo.addToClientes(cl)
            }

            rt.vendedorViejo.save flush:true
            rt.vendedorNuevo.save flush:true

            List<Vendedor> resp = [rt.vendedorViejo, rt.vendedorNuevo]
            respond resp, [status: OK]
        }
        else {
            render status: NOT_ACCEPTABLE
        }
    }
}
