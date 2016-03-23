package ordertracker

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Producto)
class ProductoSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "prueba enum estado"() {
		when:
		def p = new Producto(nombre: "Producto de Prueba")
		
		then:
		p.validate()
		assert p.estado.toString() == "No disponible"
		assert p.stock == 0
    }
}
