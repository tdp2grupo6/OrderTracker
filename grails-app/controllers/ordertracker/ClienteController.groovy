package ordertracker

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ClienteController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Cliente.list(params), [status: OK]
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
