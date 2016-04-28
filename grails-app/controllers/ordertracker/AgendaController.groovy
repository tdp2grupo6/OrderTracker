package ordertracker

import org.codehaus.groovy.runtime.StringGroovyMethods

import static org.springframework.http.HttpStatus.*

class AgendaController {
    static responseFormats = ['json']
    static allowedMethods = [agendaDia: "GET", agendaSemana: "GET"]

    def agendaDia() {
        if (StringGroovyMethods.isInteger(params.codigoDia) && Utils.SEMANA.contains(params.int('codigoDia'))) {
            AgendaVendedor av = new AgendaVendedor(params.int('codigoDia'))
            respond av, [status: OK]
        }
        else {
            render status: NOT_ACCEPTABLE
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
