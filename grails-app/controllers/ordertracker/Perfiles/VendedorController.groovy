package ordertracker.Perfiles

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.transaction.Transactional
import ordertracker.Cliente
import ordertracker.Filtros.FiltroResultado
import ordertracker.Filtros.FiltroVendedor
import ordertracker.PushToken
import ordertracker.Utils

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class VendedorController {
    static responseFormats = ['json']
    static allowedMethods = [index: "GET", show: "GET", save: "POST", update: "PUT", delete: "DELETE",
                             refreshPushId: "POST", filtroAdmin: "POST", listaCorta: "GET"]

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
}
