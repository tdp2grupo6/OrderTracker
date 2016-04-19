package ordertracker

import grails.rest.RestfulController
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class PedidoController extends RestfulController {
    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", show: "GET", search: "GET",
                             searchByCliente: "GET", searchByEstado: "GET"]

    PedidoController() {
        super(Pedido)
    }

    def searchByCliente(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def prod = Pedido.createCriteria()
        def result1 = prod.list(params) {
            eq("codigoCliente", "$params.id")
        }
        respond result1, model:[totalResultados: result1.count]
    }

    def searchByEstado(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def prod = Pedido.createCriteria()
        def result1 = prod.list(params) {
            eq("estado", "$params.id")
        }
        respond result1, model:[totalResultados: result1.count]
    }
}


/*
@Transactional(readOnly = true)
class PedidoController {

    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", show: "GET", search: "GET",
                             searchByCliente: "GET", searchByEstado: "GET"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Pedido.list(params), [status: OK]
    }

    def show(Producto prod) {
        if (!prod) {
            def result = []
            respond result
        }
        else {
            respond prod
        }
    }

    def searchByCliente(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def prod = Pedido.createCriteria()
        def result1 = prod.list(params) {
            eq("codigoCliente", "$params.id")
        }
        respond result1, model:[totalResultados: result1.count]
    }

    def searchByEstado(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def prod = Pedido.createCriteria()
        def result1 = prod.list(params) {
            eq("estado", "$params.id")
        }
        respond result1, model:[totalResultados: result1.count]
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

        if (Cliente.exists(pedidoInstance.cliente.id)) {
            ArrayList<PedidoDetalle> elems;

            pedidoInstance.elementos.each {
                PedidoDetalle pd = new PedidoDetalle(producto: Producto.findById(it.producto.id), cantidad: it.cantidad).save flush: true
                elems.add(pd)
            }

            //pedidoInstance.save flush:true
            new Pedido(cliente: Cliente.findById(pedidoInstance.cliente.id), elementos: elems).save flush:true
            respond pedidoInstance, [status: CREATED]
        }
        else {
            render status: NOT_ACCEPTABLE
        }
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
        render status: NO_CONTENT
    }
}
*/