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
	
	void "Cliente sin ubicacion"() {
		when:
		def c = new Cliente(nombre: "Juan", apellido: "Perez", email: "jp@mail.com")
		
		then:
		!c.validate()
		
		when:
		def l = new Ubicacion(direccion: "Paseo Colón 850", latitud: -34.5887297d, longitud: -58.3966085d)
		c = new Cliente(nombre: "Juan", apellido: "Perez", email: "jp@mail.com", ubicacion: l)
		
		then:
		c.validate()
	}

    void "datos del Cliente en blanco"() {
		when:
		def l = new Ubicacion(direccion: "Paseo Colón 850", latitud: -34.5887297d, longitud: -58.3966085d)
		def c = new Cliente(nombre: "", apellido: "", email: "", ubicacion: l)
		
		then:
		!c.validate()
		
		when:
		c = new Cliente(nombre: "Juan", apellido: "Perez", email: "jp@mail.com", ubicacion: l)
		
		then:
		c.validate()
    }
}
