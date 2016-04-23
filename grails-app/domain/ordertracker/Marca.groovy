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

	static mapping = {
		productos cascade: "all-delete-orphan"
	}

    static constraints = {
		nombre blank: false
		imagen nullable: true
		productos nullable: true
	}
}
