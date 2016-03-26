package ordertracker

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CategoriaController {
	
	static responseFormats = ['json']
	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", show: "GET", search: "GET"]

	def index(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		def result = Categoria.list(params).collect {
			[
				nombre: it.nombre,
				caracteristicas: it.caracteristicas
			]
		}
		respond result, [status: OK]
	}
	
	def show(Categoria cat) {
		respond cat.collect {
			[
				nombre: it.nombre,
				caracteristicas: it.caracteristicas
			]
		}
	}
	
	def search(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		def c = Cliente.createCriteria()
		def result1 = c.list(params) {
			ilike("nombre", "$params.id%")
		}
		def result2 = result1.collect {
			[
				nombre: it.nombre,
				caracteristicas: it.caracteristicas
			]
		}
		respond result2, model:[totalResultados: result2.totalCount]
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
        respond categoriaInstance, [status: CREATED]
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

        categoriaInstance.delete flush:true
        render status: NO_CONTENT
    }
}
