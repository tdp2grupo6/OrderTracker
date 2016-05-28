package ordertracker

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.transaction.Transactional
import ordertracker.Perfiles.Vendedor
import org.codehaus.groovy.grails.web.json.JSONObject
import org.codehaus.groovy.runtime.StringGroovyMethods

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class AgendaController {
    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", agendaDia: "GET", agendaSemana: "GET",
                            agendaAdminDia: "GET", agendaAdminSemana: "GET", agendaEditar: "POST"]

    def springSecurityService
    def mensajePushService

    // Métodos de Negocio //
    def agendaDia() {
        if (SpringSecurityUtils.ifAllGranted(Utils.VENDEDOR)) {
            if (StringGroovyMethods.isInteger(params.codigoDia) && Utils.SEMANA.contains(params.int('codigoDia'))) {
                Vendedor user = springSecurityService.currentUser
                Agenda ag = user.agenda
                //if (!ag.ordenada) { ag.poblarAgendaClientes() }
                JSONObject resp = ag.mostrarAgendaDia(params.int('codigoDia'))

                respond resp, [status: OK]
            }
            else {
                render status: NOT_ACCEPTABLE
            }
        }
        else {
            if (StringGroovyMethods.isInteger(params.codigoDia) && Utils.SEMANA.contains(params.int('codigoDia'))) {
                AgendaVendedor av = new AgendaVendedor(params.int('codigoDia'))
                respond av, [status: OK]
            }
            else {
                render status: NOT_ACCEPTABLE
            }
        }
    }

    def agendaSemana() {
        if (SpringSecurityUtils.ifAllGranted(Utils.VENDEDOR)) {
            Vendedor v = springSecurityService.currentUser
            def ag = v.agenda
            //if (!ag.ordenada) { ag.poblarAgendaClientes() }
            def resp = ag.mostrarAgendaSemana()
            respond resp, [status: OK]
        }
        else {
            List res = []

            Utils.SEMANA.each {
                res.add(new AgendaVendedor((int) it))
            }

            respond res, [status: OK]
        }
    }

    def agendaAdminDia() {
        //if (SpringSecurityUtils.ifAllGranted(Utils.ADMIN)) {
            if (StringGroovyMethods.isLong(params.idVendedor) && StringGroovyMethods.isInteger(params.codigoDia) && Utils.SEMANA.contains(params.int('codigoDia'))) {
                Vendedor v = Vendedor.findById(params.long('idVendedor'))
                def ag = v.agenda
                //if (!ag.ordenada) { ag.poblarAgendaClientes() }
                def resp = AgendaDia.findByAgendaAndCodigoDia(ag, params.int('codigoDia'))
                respond resp, [status: OK]
            }
            else {
                render status: NOT_ACCEPTABLE
            }
        //}
        //else {
        //    render status: NOT_FOUND
        //}
    }

    def agendaAdminSemana() {
        //if (SpringSecurityUtils.ifAllGranted(Utils.ADMIN)) {
            if (StringGroovyMethods.isLong(params.idVendedor)) {
                Vendedor v = Vendedor.findById(params.long('idVendedor'))
                def ag = v.agenda
                //if (!ag.ordenada) { ag.poblarAgendaClientes() }
                respond ag, [status: OK]
            }
            else {
                render status: NOT_ACCEPTABLE
            }
        //}
        //else {
        //    render status: NOT_FOUND
        //}
    }

    @Transactional
    def agendaEditar(AgendaUpdate au) {
        if (SpringSecurityUtils.ifAllGranted(Utils.ADMIN)) {
            render status: NOT_FOUND
        }
        else {
            // TODO Mover código al 'if' de arriba
            if (au == null) {
                render status: NOT_FOUND
                return
            }

            Vendedor v = Vendedor.findById(au.idVendedor)
            int clientesAntiguos = v.clientes?.size()?: 0
            v.clientes = []

            au.clientes.each {
                v.addToClientes(Cliente.findById(it))
            }

            v.save flush:true
            int clientesNuevos = v.clientes?.size()?: 0

            println "[OT-LOG] Vendedor: ${v.username} - Clientes Antiguos: ${clientesAntiguos} - Clientes Nuevos: ${clientesNuevos}"

            def listaClientes = v.clientes
            Agenda agenda = v.agenda

            for (Cliente cl: listaClientes) {
                cl.agendaCliente = []
                cl.save flush:true

                List vendedores = Utils.otrosVendedoresAsociados(v, cl)

                for (Vendedor v2: vendedores) {
                    v2.agenda.agendaDia*.listaClientes.remove(cl.id)
                }
            }

            /*
            for (AgendaUpdate.AgendaSimple ag: au.agenda) {
                if (Utils.SEMANA.contains(ag.codigoDia)) {
                    AgendaDia ad = AgendaDia.findByAgendaAndCodigoDia(agenda, ag.codigoDia)
                    Utils.copiarAgendaSimple(ag, ad)
                    ad.save(flush: true)

                    for (int idCliente: ag.listaClientes) {
                        Cliente cl = Cliente.findById(idCliente)
                        cl.agendaCliente.add(ag.codigoDia)
                        cl.save(flush: true)
                    }
                }
            }
            */

            for (AgendaUpdate.AgendaSimple ag: au.agenda) {
                for (Long idCliente: ag.listaClientes) {
                    Cliente cl = Cliente.findById(idCliente)
                    cl.agendaCliente.add(ag.codigoDia)
                    cl.save flush:true

                    List vendedores = Utils.otrosVendedoresAsociados(v, cl)

                    for (Vendedor v2: vendedores) {
                        AgendaDia ad2 = AgendaDia.findByAgendaAndCodigoDia(v2.agenda, ag.codigoDia)
                        ad2.listaClientes.add(cl.id)
                    }
                }

                AgendaDia ad = AgendaDia.findByAgendaAndCodigoDia(agenda, ag.codigoDia)
                Utils.copiarAgendaSimple(ag, ad)
                ad.save(flush: true)
            }


            //agenda.poblarAgendaClientes()
            agenda.ordenada = au.agendaOrdenada
            agenda.save flush:true

            respond agenda, [status: OK]

            if (clientesNuevos > clientesAntiguos) {
                int diff = clientesNuevos - clientesAntiguos
                mensajePushService.mensajePush(v, "Order Tracker: Nuevos Clientes", "Tenés ${diff} nuevo(s) cliente(s)")
            }
        }
    }

    // Métodos RESTful //
    def index(Integer max) {
        //params.max = Math.min(max ?: 10, 100)
        respond Agenda.list(params), [status: OK]
    }

    @Transactional
    def save(Agenda agendaInstance) {
        if (agendaInstance == null) {
            render status: NOT_FOUND
            return
        }

        agendaInstance.validate()
        if (agendaInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        agendaInstance.save flush:true
        respond agendaInstance, [status: OK]
    }

    @Transactional
    def update(Agenda agendaInstance) {
        if (agendaInstance == null) {
            render status: NOT_FOUND
            return
        }

        agendaInstance.validate()
        if (agendaInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        agendaInstance.save flush:true
        respond agendaInstance, [status: OK]
    }

    @Transactional
    def delete(Agenda agendaInstance) {

        if (agendaInstance == null) {
            render status: NOT_FOUND
            return
        }

        agendaInstance.delete flush:true
        render status: OK
    }
}
