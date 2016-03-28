package ordertracker

class Marca {
	String nombre
	String codigo = ""
	String rutaImagen = "" // TODO cambiar por imagen por defecto
	
	static hasMany = [productos: Producto]
	
	def filtroMarca() {
		return [
			nombre: nombre,
			codigo: id,
			rutaImagen: rutaImagen
		]
	}
	
    static constraints = {
		nombre blank: false
		codigo blank: true
		rutaImagen nullable: true
		productos nullable: true
	}
}
