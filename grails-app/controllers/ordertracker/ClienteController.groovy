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
		def result = Cliente.list(params).collect {
			[
				nombreCompleto: it.nombreCompleto(),
				direccion: it.direccion,
				telefono: it.telefono,
				email: it.email,
				latitud: it.latitud,
				longitud: it.longitud
			]
		} 
        respond result, [status: OK]
    }
	
	def show(Cliente cl) {
		respond cl.collect {
			[
				nombreCompleto: it.nombreCompleto(),
				direccion: it.direccion,
				telefono: it.telefono,
				email: it.email,
				latitud: it.latitud,
				longitud: it.longitud
			]
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
		def result2 = result1.collect {
			[
				nombreCompleto: it.nombreCompleto(),
				direccion: it.direccion,
				telefono: it.telefono,
				email: it.email,
				latitud: it.latitud,
				longitud: it.longitud
			]
		}
		respond result2, model:[totalResultados: result2.totalCount]
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
