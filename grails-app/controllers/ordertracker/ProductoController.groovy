package ordertracker



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ProductoController {

    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", show: "GET", search: "GET"]
	
	def index(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		def result1 = Producto.list(params)
		def result2 = result1*.filtroProducto()
		respond result2, [status: OK]
	}
	
	def show(Producto prod) {
		if (!prod) {
			respond null, [status: NOT_FOUND]
		}
		else {
			respond prod.filtroProducto()
		}		
	}
	
	def search(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		def prod = Producto.createCriteria()
		def result1 = prod.list(params) {
			ilike("nombre", "$params.id%")
		}
		if (!result1 || !(result1.isEmpty())) {
			def result2 = result1*.filtroProducto()
			respond result2, model:[totalResultados: result2.totalCount]
		}
		else {
			respond null, [status: NOT_FOUND]
		}
	}	

    @Transactional
    def save(Producto productoInstance) {
        if (productoInstance == null) {
            render status: NOT_FOUND
            return
        }

        productoInstance.validate()
        if (productoInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        productoInstance.save flush:true
        respond productoInstance, [status: CREATED]
    }

    @Transactional
    def update(Producto productoInstance) {
        if (productoInstance == null) {
            render status: NOT_FOUND
            return
        }

        productoInstance.validate()
        if (productoInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        productoInstance.save flush:true
        respond productoInstance, [status: OK]
    }

    @Transactional
    def delete(Producto productoInstance) {

        if (productoInstance == null) {
            render status: NOT_FOUND
            return
        }

        productoInstance.delete flush:true
        render status: NO_CONTENT
    }
}
