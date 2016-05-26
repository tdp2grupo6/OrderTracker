package ordertracker

import ordertracker.Filtros.FiltroPagina
import ordertracker.Filtros.FiltroResultado

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class MarcaController {

    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", show: "GET", search: "GET", filtroAdmin: "POST",
                            listarImagenes: "GET"]

    def index(Integer max) {
        //params.max = Math.min(max ?: 10, 100)
        def result = Marca.list(params)
		respond result, [status: OK]
    }

	def show(Marca tm) {
		if (!tm) {
            render status: NOT_FOUND
		}
		else {
			respond tm, [status: OK]
		}
	}
	
	def search(Integer max) {
		//params.max = Math.min(max ?: 10, 100)
		def tm = Marca.createCriteria()
		def result = tm.list(params) {
			ilike("nombre", "$params.term%")
		}
		respond result, model:[status: OK, totalResultados: result.totalCount]
	}
	
    @Transactional
    def save(Marca marcaInstance) {
        if (marcaInstance == null) {
            render status: NOT_FOUND
            return
        }

        marcaInstance.validate()
        if (marcaInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        marcaInstance.save flush:true
        respond marcaInstance, [status: OK]
    }

    @Transactional
    def update(Marca marcaInstance) {
        if (marcaInstance == null) {
            render status: NOT_FOUND
            return
        }

        marcaInstance.validate()
        if (marcaInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        marcaInstance.save flush:true
        respond marcaInstance, [status: OK]
    }

    @Transactional
    def delete(Marca marcaInstance) {
        if (marcaInstance == null) {
            render status: NOT_FOUND
            return
        }

        marcaInstance.eliminarInstancias()
        marcaInstance.delete flush:true
        render status: OK
    }

    def filtroAdmin(FiltroPagina fp) {
        int offset = fp.primerValor()
        int maxPage = Utils.RESULTADOS_POR_PAGINA
        def prod = Marca.createCriteria()
        def result = prod.list (max: maxPage, offset: offset) { }
        int pagina = fp.pagina? fp.pagina : 1
        result.sort { it.id }
        FiltroResultado respuesta = new FiltroResultado(pagina, result.totalCount, result as List)
        respond respuesta, model:[status: OK, totalResultados: result.totalCount]
    }

    def listarImagenes() {
        def lst = Imagen.list()
        respond lst, [status: OK]
    }
}
