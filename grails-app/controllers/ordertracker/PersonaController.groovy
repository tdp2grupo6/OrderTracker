package ordertracker

import grails.plugin.springsecurity.SpringSecurityUtils
import ordertracker.Perfiles.Admin
import ordertracker.Perfiles.Vendedor
import ordertracker.Servicios.Utils

import static org.springframework.http.HttpStatus.*

class PersonaController {
    static responseFormats = ['json']
    static allowedMethods = [index: "GET"]

    def springSecurityService

    def index() {
        if (SpringSecurityUtils.ifAllGranted(Utils.ADMIN)) {
            Admin u = springSecurityService.currentUser
            respond u, [status: OK]
        }
        else if (SpringSecurityUtils.ifAllGranted(Utils.VENDEDOR)) {
            Vendedor u = springSecurityService.currentUser
            respond u, [status: OK]
        }
        else {
            render status: NOT_FOUND
        }
    }
}
