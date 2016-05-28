package ordertracker

import ordertracker.Relations.CategoriaProducto

class Categoria {
	String nombre
	String descripcion = ""

	//static hasMany = [productos: Producto]

    static constraints = {
		nombre blank: false
		//productos nullable: true
    }

	Set<Producto> obtenerProductos() {
		CategoriaProducto.findAllByCategoria(this).collect { it.producto } as Set
	}

	boolean tieneProducto(Producto prod) {
		CategoriaProducto.countByCategoriaAndProducto(this, prod) > 0
	}

	void eliminarInstancias() {
		//this.productos = null

		/*
		Producto.list().each {
			if (it.categorias.contains(this)) {
				it.removeFromCategorias(this)
			}
		}
		*/
		CategoriaProducto.removeAll(this)
	}
}
