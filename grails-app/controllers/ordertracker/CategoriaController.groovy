package ordertracker

import org.codehaus.groovy.runtime.StringGroovyMethods

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CategoriaController {
	
	static responseFormats = ['json']
	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", show: "GET", search: "GET",
                             searchInProducto: "GET"]

	def index(Integer max) {
		//params.max = Math.min(max ?: 10, 100)
		def result = Categoria.list(params)
		respond result, [status: OK]
	}
	
	def show(Categoria cat) {
		if (!cat) {
            render status: NOT_FOUND
		}
		else {
			respond cat, [status: OK]
		}
	}
	
	def search(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		def c = Categoria.createCriteria()
		def result = c.list(params) {
			ilike("nombre", "$params.term%")
		}
		respond result, model:[status: OK, totalResultados: result.count]
	}

    def searchInProducto(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        if (StringGroovyMethods.isLong(params.id)) {
            long idFiltro = StringGroovyMethods.toLong(params.id)
            def prod = Producto.findById(idFiltro)
            def res = prod.categorias.toList()
            respond res, model: [status: OK, totalResultados: res.count]
        }
        else {
            render status: NOT_ACCEPTABLE
        }
    }
	
    @Transactional
    def save(Categoria categoriaInstance) {
        if (categoriaInstance == null) {
            render status: NOT_FOUND
            return
        }

        categoriaInstance.validate()
        if (categoriaInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        categoriaInstance.save flush:true
        respond categoriaInstance, [status: OK]
    }

    @Transactional
    def update(Categoria categoriaInstance) {
        if (categoriaInstance == null) {
            render status: NOT_FOUND
            return
        }

        categoriaInstance.validate()
        if (categoriaInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        categoriaInstance.save flush:true
        respond categoriaInstance, [status: OK]
    }

    @Transactional
    def delete(Categoria categoriaInstance) {
        if (categoriaInstance == null) {
            render status: NOT_FOUND
            return
        }

        categoriaInstance.eliminarInstancias()
        categoriaInstance.delete flush:true
        render status: OK
    }
}
