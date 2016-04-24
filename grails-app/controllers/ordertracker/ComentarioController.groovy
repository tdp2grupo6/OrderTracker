package ordertracker



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ComentarioController {

    static responseFormats = ['json']
    static allowedMethods = [show: "GET", save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        //params.max = Math.min(max ?: 10, 100)
        respond Comentario.list(params), [status: OK]
    }

    def show(Comentario c) {
        if (!c) {
            render status: NOT_FOUND
        }
        else {
            respond c
        }
    }

    @Transactional
    def save(Comentario comentarioInstance) {
        if (comentarioInstance == null) {
            render status: NOT_FOUND
            return
        }

        comentarioInstance.validate()
        if (comentarioInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        comentarioInstance.save flush:true
        respond comentarioInstance, [status: CREATED]
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
        render status: NO_CONTENT
    }
}
