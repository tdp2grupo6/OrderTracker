package ordertracker

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

import static org.springframework.http.HttpStatus.*

@TestFor(CategoriaController)
@Mock(Categoria)
class CategoriaControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        params['nombre'] = "Ferreteria"
        params['descripcion'] = "Herramientas de trabajo"
    }

    def populateCustomParams(params, value) {
        assert params != null
        params['id'] = value
        params['term'] = value
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The response is correct"
            response.status == OK.value
            response.text == ([] as JSON).toString()
    }

    void "Testeando listado categorias"() {
        given:
        def c1 = new Categoria(nombre: "Ropa de Mujer").save(flush: true)
        def c2 = new Categoria(nombre: "Indumentaria Deportiva").save(flush: true)
        def c3 = new Categoria(nombre: "Zapatería").save(flush: true)

        when:
        controller.index()

        then:
        response.status == OK.value
        response.text == ([c1, c2, c3] as JSON).toString()
    }

    void "Testeando listar un categoria"() {
        given:
        def c1 = new Categoria(nombre: "Ropa de Mujer").save(flush: true)
        def c2 = new Categoria(nombre: "Indumentaria Deportiva").save(flush: true)
        def c3 = new Categoria(nombre: "Zapatería").save(flush: true)

        when:
        def categoriaBuscada = c2		// Cambiar por c1, c2 o c3 (a gusto)
        populateCustomParams(params, categoriaBuscada.id)
        controller.show()

        then:
        response.status == OK.value
        response.text == (categoriaBuscada as JSON).toString()
    }

    void "Testeando busqueda de un categoria"() {
        given:
        def c1 = new Categoria(nombre: "Ropa de Mujer").save(flush: true)
        def c2 = new Categoria(nombre: "Indumentaria Deportiva").save(flush: true)
        def c3 = new Categoria(nombre: "Zapatería").save(flush: true)

        when: "Búsqueda normal"
        def categoriaBuscado = c2		// Cambiar por c1, c2 o c3 (a gusto)
        String query = categoriaBuscado.nombre.substring(0,3)
        populateCustomParams(params, query)
        controller.search()

        then:
        response.status == OK.value
        response.text == ([categoriaBuscado] as JSON).toString()

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
            def categoria = new Categoria()

            request.method = 'POST'
            response.format = 'json'
            controller.save(categoria)

        then:"The response status is NOT_ACCEPTABLE"
            response.status == NOT_ACCEPTABLE.value

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            categoria = new Categoria(params)

            request.method = 'POST'
            response.format = 'json'
            controller.save(categoria)

        then:"The response status is OK and the instance is returned"
            response.status == OK.value
            response.text == (categoria as JSON).toString()
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
            def categoria = new Categoria()

            request.method = 'PUT'
            response.format = 'json'
            controller.update(categoria)

        then:"The response status is NOT_ACCEPTABLE"
            response.status == NOT_ACCEPTABLE.value

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            categoria = new Categoria(params).save(flush: true)

            request.method = 'PUT'
            response.format = 'json'
            controller.update(categoria)

        then:"The response status is OK and the updated instance is returned"
            response.status == OK.value
            response.text == (categoria as JSON).toString()
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
            def categoria = new Categoria(params).save(flush: true)

        then:"It exists"
            Categoria.count() == 1

        when:"The domain instance is passed to the delete action"
            request.method = 'DELETE'
            response.format = 'json'
            controller.delete(categoria)

        then:"The instance is deleted"
            Categoria.count() == 0
            response.status == OK.value
    }
}
