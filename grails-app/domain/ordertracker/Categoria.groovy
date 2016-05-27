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
