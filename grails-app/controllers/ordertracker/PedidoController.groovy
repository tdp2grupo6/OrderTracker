package ordertracker

import grails.transaction.Transactional
import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class PedidoController {
    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", show: "GET", search: "GET",
                             searchByCliente: "GET", searchByEstado: "GET"]

    def index(Integer max) {
        //params.max = Math.min(max ?: 10, 100)
        respond Pedido.list(params), [status: OK]
    }

    def show(Producto prod) {
        if (!prod) {
            render status: NOT_FOUND
        }
        else {
            respond prod, [status: OK]
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

    @Transactional
    def save(Pedido pedidoInstance) {
        if (pedidoInstance == null) {
            render status: NOT_FOUND
            return
        }

        pedidoInstance.validate()
        if (pedidoInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        pedidoInstance.save flush:true
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
}