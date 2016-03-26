package ordertracker



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ProductoController {

    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", show: "GET", search: "GET"]
	
	def index(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		def result = Producto.list(params).collect {
			[
				nombre: it.nombre,
				marca: it.marca.nombre,
				codigo: it.id,
				caracteristicas: it.caracteristicas,
				categoria: it.categoria,
				rutaImagen: it.rutaImagen,
				stock: it.stock,
				precio: it.precio,
				estado: it.estado.toString()
			]
		}
		respond result, [status: OK]
	}
	
	def show(Producto prod) {
		respond prod.collect {
			[
				nombre: it.nombre,
				marca: it.marca.nombre,
				codigo: it.id,
				caracteristicas: it.caracteristicas,
				categoria: it.categoria,
				rutaImagen: it.rutaImagen,
				stock: it.stock,
				precio: it.precio,
				estado: it.estado.toString()
			]
		}
	}
	
	def search(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		def prod = Producto.createCriteria()
		def result1 = prod.list(params) {
			ilike("nombre", "$params.id%")
		}
		def result2 = result1.collect {
			[
				nombre: it.nombre,
				marca: it.marca.nombre,
				codigo: it.id,
				caracteristicas: it.caracteristicas,
				categoria: it.categoria,
				rutaImagen: it.rutaImagen,
				stock: it.stock,
				precio: it.precio,
				estado: it.estado.toString()
			]
		}
		respond result2, model:[totalResultados: result2.totalCount]
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
