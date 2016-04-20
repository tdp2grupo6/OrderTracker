package ordertracker



import static org.springframework.http.HttpStatus.*
import grails.converters.JSON
import grails.test.mixin.*
import spock.lang.*

@TestFor(MarcaController)
@Mock([Marca, Producto])
class MarcaControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        params['nombre'] = "Reebok"
    }

    def populateCustomParams(params, value) {
        assert params != null
        params['id'] = value
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The response is correct"
            response.status == OK.value
            response.text == ([] as JSON).toString()
    }

    void "Testeando listar un categoria"() {
        given:
        def m1 = new Marca(nombre: "Adidas").save(flush: true)
        def m2 = new Marca(nombre: "Reebok").save(flush: true)
        def m3 = new Marca(nombre: "Nike").save(flush: true)

        when:
        def marcaBuscada = m2		// Cambiar por m1, m2 o m3 (a gusto)
        populateCustomParams(params, marcaBuscada.id)
        controller.show()

        then:
        response.status == OK.value
        response.text == (marcaBuscada as JSON).toString()
    }

    void "Testeando busqueda de una marca"() {
        given:
        def m1 = new Marca(nombre: "Adidas").save(flush: true)
        def m2 = new Marca(nombre: "Reebok").save(flush: true)
        def m3 = new Marca(nombre: "Nike").save(flush: true)

        when: "Búsqueda normal"
        def marcaBuscada = m2		// Cambiar por c1, c2 o c3 (a gusto)
        String query = marcaBuscada.nombre.substring(0,3)
        populateCustomParams(params, query)
        controller.search()

        then:
        response.status == OK.value
        response.text == ([marcaBuscada] as JSON).toString()

        when: "Búsqueda de algo que no existe"
        response.reset()
        query = "1234"
        populateCustomParams(params, query)
        controller.search()

        then:
        response.status == OK.value
        response.text == ([] as JSON).toString()
    }

	void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            // Make sure the domain class has at least one non-null property
            // or this test will fail.
            def marca = new Marca()

            request.method = 'POST'
            response.format = 'json'
            controller.save(marca)

        then:"The response status is NOT_ACCEPTABLE"
            response.status == NOT_ACCEPTABLE.value

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            marca = new Marca(params)

            request.method = 'POST'
            response.format = 'json'
            controller.save(marca)

        then:"The response status is CREATED and the instance is returned"
            response.status == CREATED.value
            response.text == (marca as JSON).toString()
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
            def marca = new Marca()

            request.method = 'PUT'
            response.format = 'json'
            controller.update(marca)

        then:"The response status is NOT_ACCEPTABLE"
            response.status == NOT_ACCEPTABLE.value

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            marca = new Marca(params).save(flush: true)

            request.method = 'PUT'
            response.format = 'json'
            controller.update(marca)

        then:"The response status is OK and the updated instance is returned"
            response.status == OK.value
            response.text == (marca as JSON).toString()
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
            def marca = new Marca(params).save(flush: true)

        then:"It exists"
            Marca.count() == 1

        when:"The domain instance is passed to the delete action"
            request.method = 'DELETE'
            response.format = 'json'
            controller.delete(marca)

        then:"The instance is deleted"
            Marca.count() == 0
            response.status == NO_CONTENT.value
    }
}
