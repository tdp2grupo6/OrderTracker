package ordertracker

import static org.springframework.http.HttpStatus.*

import org.hibernate.validator.internal.util.Contracts;

import grails.transaction.Transactional

@Transactional(readOnly = true)
class ClienteController {

    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", show: "GET", search: "GET"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
		def result1 = Cliente.list(params)
		def result2 = result1*.filtroCliente()
        respond result2, [status: OK]
    }
	
	def show(Cliente cl) {
		if (!cl) {
			respond null, [status: NOT_FOUND]
		}
		else {
			respond cl.filtroCliente()
		}		
	}
	
	def search(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		def c = Cliente.createCriteria()
		def result1 = c.list(params) {
			or {
				ilike("nombre", "$params.id%")
				ilike("apellido", "$params.id%")
				ilike("razonSocial", "$params.id%")
			}
		}
		if (!result1 || !(result1.isEmpty())) {
			def result2 = result1*.filtroCliente()
			respond result2, model:[totalResultados: result2.totalCount]
		}
		else {
			respond null, [status: NOT_FOUND]
		}
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
