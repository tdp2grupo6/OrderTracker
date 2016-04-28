package ordertracker

import static org.springframework.http.HttpStatus.*
import grails.converters.JSON
import grails.test.mixin.*
import spock.lang.*

@TestFor(PedidoController)
@Mock([Pedido,PedidoElemento,Cliente,Producto,Marca,Visita])
class PedidoControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        params['cliente'] = new Cliente(apellido: "Tinelli", nombre: "Marcelo", email: "mtinelli@gmail.com", razonSocial: "Marcelo Tinelli", direccion: "Ugarte 152", latitud: -34.5887297d, longitud: -58.3966085d)
        params['elementos'] = [
                new PedidoElemento(producto: new Producto(nombre: "Bolso de la Seleccion", marca: new Marca(nombre: "Reebok"), precio: 1002.99, stock: 34), cantidad: 10),
                new PedidoElemento(producto: new Producto(nombre: "Remera Deportiva", marca: new Marca(nombre: "Nike"), precio: 1433.99, stock: 12), cantidad: 25),
                new PedidoElemento(producto: new Producto(nombre: "Zapatillas de Running", marca: new Marca(nombre: "Adidas"), precio: 999.99, stock: 12), cantidad: 5)
        ]
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
            def pedido = new Pedido()

            request.method = 'POST'
            response.format = 'json'
            controller.save(pedido)

        then:"The response status is NOT_ACCEPTABLE"
            response.status == NOT_ACCEPTABLE.value

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            pedido = new Pedido(params)

            request.method = 'POST'
            response.format = 'json'
            controller.save(pedido)

        then:"The response status is OK and the instance is returned"
            response.status == OK.value
            response.text == (pedido as JSON).toString()
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
            def pedido = new Pedido()

            request.method = 'PUT'
            response.format = 'json'
            controller.update(pedido)

        then:"The response status is NOT_ACCEPTABLE"
            response.status == NOT_ACCEPTABLE.value

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            pedido = new Pedido(params).save(flush: true)

            request.method = 'PUT'
            response.format = 'json'
            controller.update(pedido)

        then:"The response status is OK and the updated instance is returned"
            response.status == OK.value
            response.text == (pedido as JSON).toString()
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
            def pedido = new Pedido(params).save(flush: true)

        then:"It exists"
            Pedido.count() == 1

        when:"The domain instance is passed to the delete action"
            request.method = 'DELETE'
            response.format = 'json'
            controller.delete(pedido)

        then:"The instance is deleted"
            Pedido.count() == 0
            response.status == OK.value
    }
}
