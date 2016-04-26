package ordertracker

import ordertracker.Estados.EstadoProducto

class Producto {
	String nombre
	String codigo = ""
	String caracteristicas = ""
	
	int stock = 0
	float precio = 0f
	EstadoProducto estado = EstadoProducto.NODISP

	Imagen imagen

	static hasOne = [marca: Marca]
	static hasMany = [categorias: Categoria]
	static belongsTo = [Marca, Categoria]
	
	def listaCategorias() {
		return categorias.collect() {
			[
				codigo: it.id,
				nombre: it.nombre
			]
		}
	}
	
	String rutaImagen() {
		if (imagen!=null) {
			return "imagen/ver/$imagen.id"
		}
		else {
			return "imagen/miniatura/${ImagenController.nombreImagenRelleno}"
		}
	}
	String rutaMiniatura() {
		if (imagen!=null) {
			return "imagen/miniatura/$imagen.id"
		}
		else {
			return "imagen/miniatura/${ImagenController.nombreMiniaturaRelleno}"
		}
	}
	
    static constraints = {
		nombre blank: false
		codigo blank: true
		marca nullable: false
		categorias nullable: true
		imagen nullable: true
	}
}
