package ordertracker.DomainTests

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import ordertracker.Categoria
import ordertracker.Marca
import ordertracker.Producto
import spock.lang.Specification
/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Categoria)
@Mock([Producto, Marca])
class CategoriaSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Verificar datos por defecto"() {
        when:
        def cat1 = new Categoria(nombre: "")

        then:
        !cat1.validate()

        when:
        def cat2 = new Categoria(nombre: "Categoría")

        then:
        cat2.validate()
        cat2.descripcion == ""
    }

    void "Verificar multiples productos"() {
        when:
        def m = new Marca(nombre: "Marca 1").save()
        // Definir productos
        def p1 = new Producto(nombre: "Producto 1", marca: m).save()
        def p2 = new Producto(nombre: "Producto 2", marca: m).save()
        def p3 = new Producto(nombre: "Producto 3", marca: m).save()
        // Definir categorias
        def cat1 = new Categoria(nombre: "Categoría 1").addToProductos(p1).addToProductos(p2).addToProductos(p3)
        def cat2 = new Categoria(nombre: "Categoría 2").addToProductos(p2)

        then:
        // Verificar cat1
        cat1.validate()
        cat1.productos.contains(p1)
        cat1.productos.contains(p2)
        cat1.productos.contains(p3)
        // Verificar cat2
        cat2.validate()
        !cat2.productos.contains(p1)
        cat2.productos.contains(p2)
        !cat2.productos.contains(p3)
        // Verificar productos
        p1.categorias.contains(cat1)
        !p1.categorias.contains(cat2)
        p2.categorias.contains(cat1)
        p2.categorias.contains(cat2)
    }

    void "Verificar eliminar categorias"() {
        given:
        def m = new Marca(nombre: "Marca 1").save(flush: true)
        // Definir productos
        def p1 = new Producto(nombre: "Producto 1", marca: m).save(flush: true)
        def p2 = new Producto(nombre: "Producto 2", marca: m).save(flush: true)
        def p3 = new Producto(nombre: "Producto 3", marca: m).save(flush: true)
        // Definir categorias
        def cat1 = new Categoria(nombre: "Categoría 1").addToProductos(p1).addToProductos(p2).save(flush: true)

        when:
        cat1.delete(flush: true)

        then:
        // Se elimina la categoria pero no los productos
        assertFalse !Producto.exists(p1.id)
        assertFalse !Producto.exists(p2.id)
        assertFalse !Producto.exists(p3.id)
    }
}
