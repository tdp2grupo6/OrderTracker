package ordertracker

import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient
import spock.lang.Specification
/**
 * Created by dgacitua on 19-04-16.
 */
class PedidoControllerFunctionalSpec extends Specification { //IntegrationSpec {
    String baseUrl = "http://localhost:8080/OrderTracker"
    String consulta = "pedido"
    int numPedidos = 0
    Pedido p1, p2, p3

    def setup() {
        /*
        c1 = new Pedido(apellido: "Luna", nombre: "Silvina", email: "silvi@gmail.com", razonSocial: "Silvina Luna", direccion: "Las Heras 2850", latitud: -34.5887297d, longitud: -58.3966085d).save(flush: true)
        c2 = new Pedido(apellido: "Rial", nombre: "Jorge", email: "jrial@hotmail.com", razonSocial: "Jorge Rial", direccion: "Monroe 1501", latitud: -34.552929d, longitud: -58.451036d).save(flush: true)
        c3 = new Pedido(apellido: "Peña", nombre: "Florencia", email: "pena@gmail.com", razonSocial: "Florencia Peña", direccion: "Libertador 1090", latitud: -34.5887297d, longitud: -58.3966085d).save(flush: true)
        numPedidos = Pedido.count

        Pedido.list().each {
            println "id: ${it.id}   nombre: ${it.nombreCompleto()}"
        }
        */
    }

    def cleanup() {

    }
    /*
    void "Listar pedidos"() {
        given:
        numPedidos = Pedido.count

        RESTClient restClient = new RESTClient("${baseUrl}/")

        when:
        def response = restClient.get(path: "${consulta}")

        then:
        response.status == 200     // OK
        Pedido.count == numPedidos
    }

    void "Listar un pedido"() {
        given:
        numPedidos = Pedido.count

        RESTClient restClient = new RESTClient("${baseUrl}/")

        when:
        long targetId = 1//c2.id
        def response = restClient.get(path: "${consulta}/${targetId}")

        then:
        response.status == 200     // OK
        //response.data.id == targetId
        Pedido.count == numPedidos
    }


    void "Crear un pedido"() {
        given:
        numPedidos = Pedido.count
        RESTClient restClient = new RESTClient("${baseUrl}/")

        when:
        def response = restClient.post(path: "${consulta}",
                body: [cliente:[id:2], elementos:[[producto:[id:1], cantidad:5], [producto:[id:3], cantidad:2]]],
                contentType: ContentType.JSON)

        then:
        response.status == 201     // CREATED
        response.data.totalCompra > 0
        Pedido.count == (numPedidos + 1)
    }

    void "Actualizar un pedido"() {
        given:
        numPedidos = Pedido.count
        RESTClient restClient = new RESTClient("${baseUrl}/")

        when:
        long targetId = 1
        def response = restClient.put(path: "${consulta}/${targetId}",
                body: [elementos:[[producto:[id:1], cantidad:5], [producto:[id:3], cantidad:2]]],
                contentType: ContentType.JSON)

        then:
        response.status == 200     // OK
        response.data.totalCompra > 0
        Pedido.count == numPedidos
    }

    void "Borrar un pedido"() {
        given:
        numPedidos = Pedido.count
        RESTClient restClient = new RESTClient("${baseUrl}/")

        when:
        long targetId = 2
        def response = restClient.delete(path: "${consulta}/${targetId}")

        then:
        response.status == 204     // NO_CONTENT
        Pedido.count == (numPedidos - 1)
    }
    */
}
