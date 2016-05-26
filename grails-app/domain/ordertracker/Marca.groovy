package ordertracker

class Marca {
	String nombre = ""
	String codigo = ""

	Imagen imagen

	static hasMany = [productos: Producto]

	String rutaImagen() {
		if (imagen!=null) {
			return "imagen/ver/$imagen.id"
		}
		else {
			Imagen img = Imagen.findByOriginalFilename(ImagenController.nombreImagenRelleno)
			return "imagen/ver/$img.id"
		}
	}
	String rutaMiniatura() {
		if (imagen!=null) {
			return "imagen/miniatura/$imagen.id"
		}
		else {
			Imagen img = Imagen.findByOriginalFilename(ImagenController.nombreImagenRelleno)
			return "imagen/miniatura/$img.id"
		}
	}

	static mapping = {
		//productos cascade: "all-delete-orphan"
	}

    static constraints = {
		nombre blank: false
		imagen nullable: true
		productos nullable: true
	}

	void eliminarInstancias() {
		this.productos = null
		this.imagen = null

		Producto.list().each {
			if (it.marca == this) {
				it.marca = null
			}
		}
	}
}
