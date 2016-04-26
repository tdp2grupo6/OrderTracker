package ordertracker

import grails.converters.JSON
import grails.transaction.Transactional
import ordertracker.Estados.EstadoPedido
import org.codehaus.groovy.grails.web.json.JSONObject

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class PedidoController {
    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", show: "GET", search: "GET",
                             searchByCliente: "GET", searchByEstado: "GET", filtroAdmin: "POST"]

    def index(Integer max) {
        //params.max = Math.min(max ?: 10, 100)
        respond Pedido.list(params), [status: OK]
    }

    def show(Pedido p) {
        if (!p) {
            render status: NOT_FOUND
        }
        else {
            respond p, [status: OK]
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

    def filtroAdmin(FiltroPedido fp) {
        int offset = fp.primerValor()
        int maxPage = Constants.RESULTADOS_POR_PAGINA
        def prod = Pedido.createCriteria()
        def result = prod.list (max: maxPage, offset: offset) {
            and {
                if (fp.estado) {
                    eq("estado", EstadoPedido.obtenerEstado(fp.estado))
                }
                if (fp.idCliente) {
                    eq("cliente", Cliente.findById(fp.idCliente))
                }
                /*
                if (fp.idVendedor) {
                    eq("vendedor", Cliente.findById(fp.idVendedor))
                }
                 */
                if (fp.fechaInicio && fp.fechaFin) {
                    between("fechaRealizado", fp.fechaInicio, fp.fechaFin)
                }
                else if (fp.fechaInicio) {
                    ge("fechaRealizado", fp.fechaInicio)
                }
                else if (fp.fechaFin) {
                    le("fechaRealizado", fp.fechaFin)
                }
            }
        }
        int pagina = fp.pagina? fp.pagina : 1
        FiltroResultado respuesta = new FiltroResultado(pagina, result.totalCount, result as List)
        respond respuesta, model:[status: OK, totalResultados: result.totalCount]
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