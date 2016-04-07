package ordertracker

class Marca {
	String nombre = ""
	String codigo = ""

	Imagen imagen

	static hasMany = [productos: Producto]

	String rutaImagen() { "imagen/ver/$imagen.id" }
	String rutaMiniatura() { "imagen/miniatura/$imagen.id" }

    static constraints = {
		nombre blank: false
		imagen nullable: true
		productos nullable: true
	}
}
