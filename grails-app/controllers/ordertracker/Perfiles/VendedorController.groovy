package ordertracker.Perfiles

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.transaction.Transactional
import ordertracker.PushToken
import ordertracker.Utils

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class VendedorController {
    static responseFormats = ['json']
    static allowedMethods = [index: "GET", show: "GET", save: "POST", update: "PUT", delete: "DELETE", refreshPushId: "POST"]

    def springSecurityService

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

    @Transactional
    def save(Vendedor vendedorInstance) {
        if (vendedorInstance == null) {
            render status: NOT_FOUND
            return
        }

        vendedorInstance.validate()
        if (vendedorInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        vendedorInstance.save flush:true
        vendedorInstance.habilitarUsuario()
        respond vendedorInstance, [status: OK]
    }

    @Transactional
    def update(Vendedor vendedorInstance) {
        if (vendedorInstance == null) {
            render status: NOT_FOUND
            return
        }

        vendedorInstance.validate()
        if (vendedorInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        vendedorInstance.save flush:true
        respond vendedorInstance, [status: OK]
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

            Map respuesta = Utils.mensajePush(v, "Order Tracker: Vendedor Asociado", "Se ha asociado este dispositivo a Order Tracker")

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
}
