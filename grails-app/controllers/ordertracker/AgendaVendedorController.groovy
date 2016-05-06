package ordertracker

import ordertracker.Security.Usuario
import org.codehaus.groovy.runtime.StringGroovyMethods

import static org.springframework.http.HttpStatus.*

class AgendaVendedorController {
    static responseFormats = ['json']
    static allowedMethods = [agendaDia: "GET", agendaSemana: "GET"]

    def springSecurityService

    def agendaDia() {
        Usuario user = springSecurityService.currentUser

        if (sec.ifAllGranted(roles:'ROLE_VENDEDOR')) {

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
        List res = []

        Utils.SEMANA.each {
            res.add(new AgendaVendedor((int)it))
        }

        respond res, [status: OK]
    }
}
