package ordertracker

import grails.plugin.springsecurity.SpringSecurityUtils
import ordertracker.Perfiles.Vendedor

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ComentarioController {

    static responseFormats = ['json']
    static allowedMethods = [show: "GET", save: "POST", update: "PUT", delete: "DELETE"]

    def springSecurityService

    def index(Integer max) {
        //params.max = Math.min(max ?: 10, 100)
        respond Comentario.list(params), [status: OK]
    }

    def show(Comentario c) {
        if (!c) {
            render status: NOT_FOUND
        }
        else {
            respond c, [status: OK]
        }
    }

    @Transactional
    def save(Comentario comentarioInstance) {
        if (comentarioInstance == null) {
            render status: NOT_FOUND
            return
        }

        if (SpringSecurityUtils.ifAllGranted(Utils.VENDEDOR) && !(comentarioInstance.vendedor)) {
            Vendedor v = springSecurityService.currentUser
            comentarioInstance.vendedor = v
        }

        comentarioInstance.validate()
        if (comentarioInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        if (comentarioInstance.visita) {
            comentarioInstance.visita.comentario = comentarioInstance
        }

        comentarioInstance.save flush:true
        respond comentarioInstance, [status: OK]
    }

    @Transactional
    def update(Comentario comentarioInstance) {
        if (comentarioInstance == null) {
            render status: NOT_FOUND
            return
        }

        comentarioInstance.validate()
        if (comentarioInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        if (comentarioInstance.visita) {
            comentarioInstance.visita.comentario = comentarioInstance
        }

        comentarioInstance.save flush:true
        respond comentarioInstance, [status: OK]
    }

    @Transactional
    def delete(Comentario comentarioInstance) {

        if (comentarioInstance == null) {
            render status: NOT_FOUND
            return
        }

        comentarioInstance.delete flush:true
        render status: OK
    }
}
