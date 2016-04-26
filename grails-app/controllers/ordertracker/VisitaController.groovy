package ordertracker



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class VisitaController {

    static responseFormats = ['json']
    static allowedMethods = [show: "GET", save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        //params.max = Math.min(max ?: 10, 100)
        respond Visita.list(params), [status: OK]
    }

    def show(Visita v) {
        if (!v) {
            render status: NOT_FOUND
        }
        else {
            respond v, [status: OK]
        }
    }

    @Transactional
    def save(Visita visitaInstance) {
        if (visitaInstance == null) {
            render status: NOT_FOUND
            return
        }

        visitaInstance.validate()
        if (visitaInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        visitaInstance.save flush:true
        respond visitaInstance, [status: OK]
    }

    @Transactional
    def update(Visita visitaInstance) {
        if (visitaInstance == null) {
            render status: NOT_FOUND
            return
        }

        visitaInstance.validate()
        if (visitaInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        visitaInstance.save flush:true
        respond visitaInstance, [status: OK]
    }

    @Transactional
    def delete(Visita visitaInstance) {
        if (visitaInstance == null) {
            render status: NOT_FOUND
            return
        }

        visitaInstance.delete flush:true
        render status: OK
    }
}
