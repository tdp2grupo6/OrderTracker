package ordertracker

import static org.springframework.http.HttpStatus.*
import grails.converters.JSON
import grails.test.mixin.*
import spock.lang.*


@TestFor(ClienteController)
@Mock(Cliente)
class ClienteControllerSpec extends Specification {
	
	def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params['name'] = 'someValidName'
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
	
	void "Testeando listado clientes"() {
		given:
		def c1 = new Cliente(apellido: "Luna", nombre: "Silvina", email: "silvi@gmail.com", razonSocial: "Silvina Luna", direccion: "Las Heras 2850", latitud: -34.5887297d, longitud: -58.3966085d).save(flush: true)
		def c2 = new Cliente(apellido: "Rial", nombre: "Jorge", email: "jrial@hotmail.com", razonSocial: "Jorge Rial", direccion: "Monroe 1501", latitud: -34.552929d, longitud: -58.451036d).save(flush: true)
		def c3 = new Cliente(apellido: "Tinelli", nombre: "Marcelo", email: "mtinelli@gmail.com", razonSocial: "Marcelo Tinelli", direccion: "Ugarte 152", latitud: -34.5887297d, longitud: -58.3966085d).save(flush: true)
		
		when:
		controller.index()
		
		then:
		response.status == OK.value
		response.text == ([c1, c2, c3] as JSON).toString()
	}
	
	void "Testeando listar un cliente"() {
		given:
		def c1 = new Cliente(apellido: "Luna", nombre: "Silvina", email: "silvi@gmail.com", razonSocial: "Silvina Luna", direccion: "Las Heras 2850", latitud: -34.5887297d, longitud: -58.3966085d).save(flush: true)
		def c2 = new Cliente(apellido: "Rial", nombre: "Jorge", email: "jrial@hotmail.com", razonSocial: "Jorge Rial", direccion: "Monroe 1501", latitud: -34.552929d, longitud: -58.451036d).save(flush: true)
		def c3 = new Cliente(apellido: "Tinelli", nombre: "Marcelo", email: "mtinelli@gmail.com", razonSocial: "Marcelo Tinelli", direccion: "Ugarte 152", latitud: -34.5887297d, longitud: -58.3966085d).save(flush: true)
		
		when:
		def clienteBuscado = c2		// Cambiar por c1, c2 o c3 (a gusto)
		populateCustomParams(params, clienteBuscado.id)
		controller.show()
		
		then:
		response.status == OK.value
		response.text == (clienteBuscado as JSON).toString()		
	}
	
	void "Testeando busqueda de un cliente"() {
		given:
		def c1 = new Cliente(apellido: "Luna", nombre: "Silvina", email: "silvi@gmail.com", razonSocial: "Silvina Luna", direccion: "Las Heras 2850", latitud: -34.5887297d, longitud: -58.3966085d).save(flush: true)
		def c2 = new Cliente(apellido: "Rial", nombre: "Jorge", email: "jrial@hotmail.com", razonSocial: "Jorge Rial", direccion: "Monroe 1501", latitud: -34.552929d, longitud: -58.451036d).save(flush: true)
		def c3 = new Cliente(apellido: "Tinelli", nombre: "Marcelo", email: "mtinelli@gmail.com", razonSocial: "Marcelo Tinelli", direccion: "Ugarte 152", latitud: -34.5887297d, longitud: -58.3966085d).save(flush: true)
		
		when: "Búsqueda normal"
		def clienteBuscado = c2		// Cambiar por c1, c2 o c3 (a gusto)
		String query = clienteBuscado.nombre.substring(0,3)
		populateCustomParams(params, query)
		controller.search()
		
		then:
		response.status == OK.value
		response.text == ([clienteBuscado] as JSON).toString()
	}
	
	// TODO arreglar los tests unitarios de forma urgente
	/*
    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            // Make sure the domain class has at least one non-null property
            // or this test will fail.
            def cliente = new Cliente()
            controller.save(cliente)
			
        then:"The response status is NOT_ACCEPTABLE"
			// TODO corregir response.status (405) para que sea NOT_ACCEPTABLE (406)
            response.status == NOT_ACCEPTABLE.value

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            cliente = new Cliente(params)

            controller.save(cliente)

        then:"The response status is CREATED and the instance is returned"
            response.status == CREATED.value
            response.text == (cliente as JSON).toString()
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"The response status is NOT_FOUND"
            response.status == NOT_FOUND.value

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def cliente = new Cliente()
            controller.update(cliente)

        then:"The response status is NOT_ACCEPTABLE"
            response.status == NOT_ACCEPTABLE.value

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            cliente = new Cliente(params).save(flush: true)
            controller.update(cliente)

        then:"The response status is OK and the updated instance is returned"
            response.status == OK.value
            response.text == (cliente as JSON).toString()
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            controller.delete(null)

        then:"A NOT_FOUND is returned"
            response.status == NOT_FOUND.value

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def cliente = new Cliente(params).save(flush: true)

        then:"It exists"
            Cliente.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(cliente)

        then:"The instance is deleted"
            Cliente.count() == 0
            response.status == NO_CONTENT.value
    }
	*/
}
