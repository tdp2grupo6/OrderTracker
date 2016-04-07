package ordertracker

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Marca)
@Mock([Producto, Categoria])
class MarcaSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Verificar datos por defecto"() {
        when:
        def m1 = new Marca(nombre: "")

        then:
        !m1.validate()

        when:
        def m2 = new Marca(nombre: "Marca 2")

        then:
        m2.validate()
        m2.imagen == null
    }

    void "Verificar multiples productos"() {
        when:
        // Definir marcas
        def m1 = new Marca(nombre: "Marca 1").save()
        def m2 = new Marca(nombre: "Marca 2").save()
        // Definir productos
        def p1 = new Producto(nombre: "Producto 1", marca: m1).save()
        def p2 = new Producto(nombre: "Producto 2", marca: m2).save()
        def p3 = new Producto(nombre: "Producto 3", marca: m1).save()

        then:
        // Verificar cat1
        m1.validate()
        m1.productos.contains(p1)
        !m1.productos.contains(p2)
        m1.productos.contains(p3)
        // Verificar cat2
        m2.validate()
        !m2.productos.contains(p1)
        m2.productos.contains(p2)
        !m2.productos.contains(p3)
        // Verificar productos
        p1.marca == m1
        p2.marca == m2
        p3.marca == m1
        p1.marca != m2
    }

    void "Verificar eliminar marcas"() {
        given:
        // Definir marcas
        def m1 = new Marca(nombre: "Marca 1").save(flush: true)
        def m2 = new Marca(nombre: "Marca 2").save(flush: true)
        // Definir productos
        def p1 = new Producto(nombre: "Producto 1", marca: m1).save(flush: true)
        def p2 = new Producto(nombre: "Producto 2", marca: m2).save(flush: true)
        def p3 = new Producto(nombre: "Producto 3", marca: m1).save(flush: true)

        when:
        m1.delete(flush: true)

        then:
        // Se elimina la marca pero no los productos
        assertFalse Marca.exists(m1.id)
        assertFalse !Producto.exists(p1.id)
        assertFalse !Producto.exists(p2.id)
        assertFalse !Producto.exists(p3.id)
    }
}
