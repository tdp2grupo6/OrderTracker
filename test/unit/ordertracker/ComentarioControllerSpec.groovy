package ordertracker



import static org.springframework.http.HttpStatus.*
import grails.converters.JSON
import grails.test.mixin.*
import spock.lang.*

@TestFor(ComentarioController)
@Mock([Comentario,Cliente])
class ComentarioControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        params['cliente'] = new Cliente(apellido: "Tinelli", nombre: "Marcelo", email: "mtinelli@gmail.com", razonSocial: "Marcelo Tinelli", direccion: "Ugarte 152", latitud: -34.5887297d, longitud: -58.3966085d)
        params['razonComun'] = "Otro"
        params['comentario'] = "No hay acuerdo con el cliente respecto al pedido"
    }

    def populateCustomParams(params, id) {
        assert params != null
        params['id'] = id
    }

    void "Testeando listar un cliente"() {
        given:
        def cln = new Cliente(apellido: "Luna", nombre: "Silvina", email: "silvi@gmail.com", razonSocial: "Silvina Luna", direccion: "Las Heras 2850", latitud: -34.5887297d, longitud: -58.3966085d).save(flush: true)
        def c1 = new Comentario(cliente: cln, comentario: "Muy bueno").save(flush: true)
        def c2 = new Comentario(cliente: cln, comentario: "Muy malo").save(flush: true)
        def c3 = new Comentario(cliente: cln, comentario: "Muy mediocre").save(flush: true)

        when:
        def clienteBuscado = c2		// Cambiar por c1, c2 o c3 (a gusto)
        populateCustomParams(params, clienteBuscado.id)
        controller.show()

        then:
        response.status == OK.value
        response.text == (clienteBuscado as JSON).toString()
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The response is correct"
            response.status == OK.value
            response.text == ([] as JSON).toString()
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            // Make sure the domain class has at least one non-null property
            // or this test will fail.
            def comentario = new Comentario()
            request.method = 'POST'
            response.format = 'json'
            controller.save(comentario)

        then:"The response status is NOT_ACCEPTABLE"
            response.status == NOT_ACCEPTABLE.value

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            comentario = new Comentario(params)

            request.method = 'POST'
            response.format = 'json'
            controller.save(comentario)

        then:"The response status is OK and the instance is returned"
            response.status == OK.value
            response.text == (comentario as JSON).toString()
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.method = 'PUT'
            response.format = 'json'
            controller.update(null)

        then:"The response status is NOT_FOUND"
            response.status == NOT_FOUND.value

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def comentario = new Comentario()
            request.method = 'PUT'
            response.format = 'json'
            controller.update(comentario)

        then:"The response status is NOT_ACCEPTABLE"
            response.status == NOT_ACCEPTABLE.value

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            comentario = new Comentario(params).save(flush: true)
            request.method = 'PUT'
            response.format = 'json'
            controller.update(comentario)

        then:"The response status is OK and the updated instance is returned"
            response.status == OK.value
            response.text == (comentario as JSON).toString()
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.method = 'DELETE'
            response.format = 'json'
            controller.delete(null)

        then:"A NOT_FOUND is returned"
            response.status == NOT_FOUND.value

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def comentario = new Comentario(params).save(flush: true)

        then:"It exists"
            Comentario.count() == 1

        when:"The domain instance is passed to the delete action"
            request.method = 'DELETE'
            response.format = 'json'
            controller.delete(comentario)

        then:"The instance is deleted"
            Comentario.count() == 0
            response.status == OK.value
    }
}
