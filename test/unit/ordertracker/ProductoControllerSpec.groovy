package ordertracker



import static org.springframework.http.HttpStatus.*
import grails.converters.JSON
import grails.test.mixin.*
import spock.lang.*

@TestFor(ProductoController)
@Mock([Marca, Producto])
class ProductoControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        params['nombre'] = "Mochila Deportiva Negra"
		params['precio'] = 899.99
		params['stock'] = 3
		params['marca'] = new Marca(nombre: "Adidas")
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
	
	void "Testeando listado productos"() {
		given:
		def marca = new Marca(nombre: "Adidas")
		def p1 = new Producto(nombre: "Mochila Deportiva Negra", marca: marca, precio: 849.00, stock: 3).save(flush:true)
		def p2 = new Producto(nombre: "Bolso de la Seleccion", marca: marca, precio: 1002.99, stock: 34).save(flush:true)
		def p3 = new Producto(nombre: "Remera Deportiva", marca: marca, precio: 1433.99, stock: 12).save(flush:true)
		
		when:
		controller.index()
		
		then:
		response.status == OK.value
		response.text == ([p1, p2, p3] as JSON).toString()
	}
	
	void "Testeando listar un producto"() {
		given:
		def marca = new Marca(nombre: "Adidas")
		def p1 = new Producto(nombre: "Mochila Deportiva Negra", marca: marca, precio: 849.00, stock: 3).save(flush:true)
		def p2 = new Producto(nombre: "Bolso de la Seleccion", marca: marca, precio: 1002.99, stock: 34).save(flush:true)
		def p3 = new Producto(nombre: "Remera Deportiva", marca: marca, precio: 1433.99, stock: 12).save(flush:true)
		
		when:
		def productoBuscado = p2		// Cambiar por c1, c2 o c3 (a gusto)
		populateCustomParams(params, productoBuscado.id)
		controller.show()
		
		then:
		response.status == OK.value
		response.text == (productoBuscado as JSON).toString()
	}
	
	void "Testeando busqueda de un producto"() {
		given:
		def marca = new Marca(nombre: "Adidas")
		def p1 = new Producto(nombre: "Mochila Deportiva Negra", marca: marca, precio: 849.00, stock: 3).save(flush:true)
		def p2 = new Producto(nombre: "Bolso de la Seleccion", marca: marca, precio: 1002.99, stock: 34).save(flush:true)
		def p3 = new Producto(nombre: "Remera Deportiva", marca: marca, precio: 1433.99, stock: 12).save(flush:true)
		
		when: "Búsqueda normal"
		def productoBuscado = p2		// Cambiar por c1, c2 o c3 (a gusto)
		String query = productoBuscado.nombre.substring(0,3)
		populateCustomParams(params, query)
		controller.search()
		
		then:
		response.status == OK.value
		response.text == ([productoBuscado] as JSON).toString()
		
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
            def producto = new Producto()

			request.method = 'POST'
			response.format = 'json'
            controller.save(producto)

        then:"The response status is NOT_ACCEPTABLE"
            response.status == NOT_ACCEPTABLE.value

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            producto = new Producto(params)

			request.method = 'POST'
			response.format = 'json'
            controller.save(producto)

        then:"The response status is OK and the instance is returned"
            response.status == OK.value
            response.text == (producto as JSON).toString()
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
            def producto = new Producto()

			request.method = 'PUT'
			response.format = 'json'
            controller.update(producto)

        then:"The response status is NOT_ACCEPTABLE"
            response.status == NOT_ACCEPTABLE.value

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            producto = new Producto(params).save(flush: true)

			request.method = 'PUT'
			response.format = 'json'
            controller.update(producto)

        then:"The response status is OK and the updated instance is returned"
            response.status == OK.value
            response.text == (producto as JSON).toString()
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
            def producto = new Producto(params).save(flush: true)

        then:"It exists"
            Producto.count() == 1

        when:"The domain instance is passed to the delete action"
			request.method = 'DELETE'
			response.format = 'json'
            controller.delete(producto)

        then:"The instance is deleted"
            Producto.count() == 0
            response.status == OK.value
    }
}
