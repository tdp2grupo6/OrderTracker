package ordertracker

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Cliente)
class ClienteSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }
	
	void "datos del Cliente en blanco"() {
		when:
		def c = new Cliente(nombre: "", apellido: "", email: "", direccion: "Paseo Colón 850", latitud: -34.5887297d, longitud: -58.3966085d)
		
		then:
		!c.validate()
		
		when:
		c = new Cliente(nombre: "Juan", apellido: "Perez", email: "jp@mail.com", direccion: "Paseo Colón 850", latitud: -34.5887297d, longitud: -58.3966085d)
		
		then:
		c.validate()
    }
}
