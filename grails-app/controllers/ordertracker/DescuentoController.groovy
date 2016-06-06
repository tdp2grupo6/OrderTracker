package ordertracker

import ordertracker.Filtros.FiltroPagina
import ordertracker.Filtros.FiltroResultado

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DescuentoController {
    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", filtroAdmin: "POST"]

    def mensajePushService

    def index(Integer max) {
        //params.max = Math.min(max ?: 10, 100)
        respond Descuento.list(params), [status: OK]
    }

    def filtroAdmin(FiltroPagina fp) {
        int offset = fp.primerValor()
        int maxPage = Utils.RESULTADOS_POR_PAGINA
        def prod = Descuento.createCriteria()
        def result = prod.list (max: maxPage, offset: offset) { }
        int pagina = fp.pagina? fp.pagina : 1
        result.sort { it.id }
        FiltroResultado respuesta = new FiltroResultado(pagina, result.totalCount, result as List)
        respond respuesta, model:[status: OK, totalResultados: result.totalCount]
    }

    @Transactional
    def save(Descuento descuentoInstance) {
        if (descuentoInstance == null) {
            render status: NOT_FOUND
            return
        }

        descuentoInstance.validate()
        if (descuentoInstance.hasErrors() || !descuentoInstance.verificarDescuentoValido() || Descuento.countByProducto(descuentoInstance.producto) >= 4) {
            render status: NOT_ACCEPTABLE
            return
        }

        descuentoInstance.save flush:true
        respond descuentoInstance, [status: OK]

        mensajePushService.mensajeBroadcast("OT: Nuevo descuento", "${descuentoInstance.descuento}% en ${descuentoInstance.producto.nombre}")
    }

    @Transactional
    def update(Descuento descuentoInstance) {
        if (descuentoInstance == null) {
            render status: NOT_FOUND
            return
        }

        descuentoInstance.validate()
        if (descuentoInstance.hasErrors() || !descuentoInstance.verificarDescuentoValido()) {
            render status: NOT_ACCEPTABLE
            return
        }

        descuentoInstance.save flush:true
        respond descuentoInstance, [status: OK]
    }

    @Transactional
    def delete(Descuento descuentoInstance) {

        if (descuentoInstance == null) {
            render status: NOT_FOUND
            return
        }

        descuentoInstance.delete flush:true
        render status: OK
    }
}
