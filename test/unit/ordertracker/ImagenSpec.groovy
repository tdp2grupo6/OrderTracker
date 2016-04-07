package ordertracker

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Imagen)
@Mock([Marca, Producto])
class ImagenSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Verificar asociación"() {
        given:
        def m = new Marca(nombre: "Marca 1").save(flush: true)
        def p = new Producto(nombre: "Producto 1", marca: m).save(flush: true)

        when:
        def i1 = new Imagen()
        i1.setMarca(m)

        then:
        i1.validate()

        when:
        def i2 = new Imagen()
        i2.setProducto(p)

        then:
        i2.validate()

        when:
        def i3 = new Imagen()
        i3.setProducto(p)
        i3.setMarca(m)

        then:
        i3.validate()
    }

    void "Eliminar imagen asociada a Marca"() {
        given:
        def m = new Marca(nombre: "Marca 1").save(flush: true)
        def i = new Imagen(marca: m).save(flush: true)

        when:
        i.delete(flush: true)

        then:
        Marca.exists(m.id)
        !Imagen.exists(i.id)
    }

    void "Eliminar imagen asociada a Producto"() {
        given:
        def m = new Marca(nombre: "Marca 1").save(flush: true)
        def p = new Producto(nombre: "Producto 1", marca: m).save(flush: true)
        def i = new Imagen(producto: p).save(flush: true)

        when:
        i.delete(flush: true)

        then:
        Marca.exists(m.id)
        Producto.exists(p.id)
        !Imagen.exists(i.id)
    }
}
