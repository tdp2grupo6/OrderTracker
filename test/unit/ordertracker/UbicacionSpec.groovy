package ordertracker

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Ubicacion)
class UbicacionSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "latitud o longitud invalida"() {
		when:
		def lat = -91
		def lng = 0
		def u = new Ubicacion(latitud: lat, longitud: lng)
		
		then:
		!u.validate()
		
		when:
		lat = 91
		lng = 0
		u = new Ubicacion(latitud: lat, longitud: lng)
		
		then:
		!u.validate()
		
		when:
		lat = 0
		lng = -1
		u = new Ubicacion(latitud: lat, longitud: lng)
		
		then:
		!u.validate()
		
		when:
		lat = 91
		lng = 181
		u = new Ubicacion(latitud: lat, longitud: lng)
		
		then:
		!u.validate()
    }
}
