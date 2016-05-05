package ordertracker.Domain

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import ordertracker.Categoria
import ordertracker.Estados.EstadoProducto
import ordertracker.Marca
import ordertracker.Producto
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Producto)
@Mock([Marca, Categoria])
class ProductoSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

	void "Verificar datos por defecto"() {
		when:
		def m = new Marca(nombre: "Fantabuloso").save()
		def p = new Producto(nombre: "Producto de Prueba", marca: m).save()

		then:
		p.validate()
		p.caracteristicas == ""
		p.stock == 0
		p.precio == 0
		p.estado == EstadoProducto.NODISP
		p.imagen == null
	}

	void "Rechazar producto sin marca"() {
		when:
		def p = new Producto(nombre: "Producto de Prueba")

		then:
		!p.validate()
	}

	void "Verificar multiples categorias"() {
		when:
		def cat1 = new Categoria(nombre: "Categoria 1").save()
		def cat2 = new Categoria(nombre: "Categoria 2").save()
		def cat3 = new Categoria(nombre: "Categoria 3").save()

		def m = new Marca(nombre: "Fantabuloso").save()
		def p1 = new Producto(nombre: "Producto de Prueba 1", marca: m).addToCategorias(cat1).addToCategorias(cat2).addToCategorias(cat3).save()
		def p2 = new Producto(nombre: "Producto de Prueba 2", marca: m).addToCategorias(cat2).save()

		then:
		// verficar p1
		p1.validate()
		p1.categorias.contains(cat1)
		p1.categorias.contains(cat2)
		p1.categorias.contains(cat3)
		// verificar p2
		p2.validate()
		!p2.categorias.contains(cat1)
		p2.categorias.contains(cat2)
		!p2.categorias.contains(cat1)
		// verificar categorias
		cat1.productos.contains(p1)
		!cat1.productos.contains(p2)
		cat2.productos.contains(p1)
		cat2.productos.contains(p2)
	}

	void "Verificar eliminar productos"() {
		given:
		def m = new Marca(nombre: "Marca 1").save(flush: true)
		// Definir productos
		def p1 = new Producto(nombre: "Producto 1", marca: m).save(flush: true)
		def p2 = new Producto(nombre: "Producto 2", marca: m).save(flush: true)
		def p3 = new Producto(nombre: "Producto 3", marca: m).save(flush: true)
		// Definir categorias
		def cat1 = new Categoria(nombre: "Categoría 1").addToProductos(p1).addToProductos(p2).save(flush: true)
		def cat2 = new Categoria(nombre: "Categoría 2").addToProductos(p2).addToProductos(p3).save(flush: true)

		when:
		p2.delete(flush: true)

		then:
		// Se elimina el Producto pero no las Categorias
		assertFalse Producto.exists(p2.id)
		assertFalse !Marca.exists(m.id)
		assertFalse !Categoria.exists(cat1.id)
		assertFalse !Categoria.exists(cat2.id)
	}
}
