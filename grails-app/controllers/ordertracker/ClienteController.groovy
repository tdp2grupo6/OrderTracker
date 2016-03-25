package ordertracker

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ClienteController {

    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", show: "GET", search: "GET"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Cliente.list(params), [status: OK]
    }
	
	def show(Cliente cl) {
		respond cl
	}
	
	def search(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		def c = Cliente.createCriteria()
		def results = c.list(params) {
			or {
				ilike("nombre", "$params.id%")
				ilike("apellido", "$params.id%")
				ilike("razonSocial", "$params.id%")
			}
		}
		respond results, model:[totalResultados: results.totalCount]
	}
	

    @Transactional
    def save(Cliente clienteInstance) {
        if (clienteInstance == null) {
            render status: NOT_FOUND
            return
        }

        clienteInstance.validate()
        if (clienteInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        clienteInstance.save flush:true
        respond clienteInstance, [status: CREATED]
    }

    @Transactional
    def update(Cliente clienteInstance) {
        if (clienteInstance == null) {
            render status: NOT_FOUND
            return
        }

        clienteInstance.validate()
        if (clienteInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        clienteInstance.save flush:true
        respond clienteInstance, [status: OK]
    }

    @Transactional
    def delete(Cliente clienteInstance) {

        if (clienteInstance == null) {
            render status: NOT_FOUND
            return
        }

        clienteInstance.delete flush:true
        render status: NO_CONTENT
    }
}
