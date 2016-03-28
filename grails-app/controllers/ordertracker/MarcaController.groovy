package ordertracker



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class MarcaController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", show: "GET", search: "GET"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def result1 = Marca.list(params)
       	def result2 = result1*.filtroMarca()
		respond result2, [status: OK]
    }

	def show(Marca tm) {
		if (!tm) {
			respond null, [status: NOT_FOUND]
		}
		else {
			respond tm.filtroMarca()
		}
	}
	
	def search(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		def tm = Marca.createCriteria()
		def result1 = tm.list(params) {
			ilike("nombre", "$params.id%")
		}
		def result2 = result1*.filtroMarca()
		respond result2, model:[totalResultados: result2.totalCount]
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
        respond marcaInstance, [status: CREATED]
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

        marcaInstance.delete flush:true
        render status: NO_CONTENT
    }
}
