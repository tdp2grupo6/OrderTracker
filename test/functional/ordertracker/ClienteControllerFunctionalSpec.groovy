package ordertracker

import grails.test.mixin.TestFor
import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient
import ordertracker.Cliente
import ordertracker.ClienteController
import spock.lang.Specification
/**
 * Created by dgacitua on 09-04-16.
 */
@TestFor(ClienteController)
class ClienteControllerFunctionalSpec extends Specification { //IntegrationSpec {
    String baseUrl = "http://localhost:8080/OrderTracker"
    String consulta = "cliente"
    int numClientes = 0
    Cliente c1, c2, c3

    def setup() {
        c1 = new Cliente(apellido: "Luna", nombre: "Silvina", email: "silvi@gmail.com", razonSocial: "Silvina Luna", direccion: "Las Heras 2850", latitud: -34.5887297d, longitud: -58.3966085d).save(flush: true)
        c2 = new Cliente(apellido: "Rial", nombre: "Jorge", email: "jrial@hotmail.com", razonSocial: "Jorge Rial", direccion: "Monroe 1501", latitud: -34.552929d, longitud: -58.451036d).save(flush: true)
        c3 = new Cliente(apellido: "Peña", nombre: "Florencia", email: "pena@gmail.com", razonSocial: "Florencia Peña", direccion: "Libertador 1090", latitud: -34.5887297d, longitud: -58.3966085d).save(flush: true)
        numClientes = Cliente.count

        Cliente.list().each {
            println "id: ${it.id}   nombre: ${it.nombreCompleto()}"
        }
    }

    def cleanup() {

    }

    void "Listar clientes"() {
        given:
        numClientes = Cliente.count

        RESTClient restClient = new RESTClient("${baseUrl}/")

        when:
        def response = restClient.get(path: "${consulta}")

        then:
        response.status == 200     // OK
        Cliente.count == numClientes
    }

    void "Listar un cliente"() {
        given:
        numClientes = Cliente.count

        RESTClient restClient = new RESTClient("${baseUrl}/")

        when:
        long targetId = 1//c2.id
        def response = restClient.get(path: "${consulta}/${targetId}")

        then:
        response.status == 200     // OK
        //response.data.id == targetId
        Cliente.count == numClientes
    }


    void "Crear un cliente"() {
        given:
        numClientes = Cliente.count
        RESTClient restClient = new RESTClient("${baseUrl}/")

        when:
        def response = restClient.post(path: "${consulta}",
                body: [apellido: "Tinelli", nombre: "Marcelo", email: "mtinelli@gmail.com", razonSocial: "Marcelo Tinelli", direccion: "Ugarte 152", latitud: -34.5887297, longitud: -58.3966085],
                contentType: ContentType.JSON)

        then:
        response.status == 200     // OK
        response.data.nombre == "Marcelo"
        Cliente.count == (numClientes + 1)
    }

    void "Actualizar un cliente"() {
        given:
        numClientes = Cliente.count
        RESTClient restClient = new RESTClient("${baseUrl}/")

        when:
        long targetId = 3
        def response = restClient.put(path: "${consulta}/${targetId}",
                body: [apellido: "Perez", nombre: "Juan", email: "mtinelli@gmail.com", razonSocial: "Kioscos Juanito"],
                contentType: ContentType.JSON)

        then:
        response.status == 200     // OK
        //Cliente.findById(targetId).nombre == "Juan"
        Cliente.count == numClientes
    }

    void "Borrar un cliente"() {
        given:
        numClientes = Cliente.count
        RESTClient restClient = new RESTClient("${baseUrl}/")

        when:
        long targetId = 2
        def response = restClient.delete(path: "${consulta}/${targetId}")

        then:
        response.status == 200     // OK
        Cliente.count == (numClientes - 1)
    }
}