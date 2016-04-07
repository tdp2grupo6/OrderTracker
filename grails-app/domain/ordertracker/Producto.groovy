package ordertracker

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
	static belongsTo = Categoria
	
	def listaCategorias() {
		return categorias.collect() {
			[
				codigo: it.id,
				nombre: it.nombre
			]
		}
	}
	
	String rutaImagen() { "imagen/ver/$imagen.id" }
	String rutaMiniatura() { "imagen/miniatura/$imagen.id" }
	
    static constraints = {
		nombre blank: false
		codigo blank: true
		marca nullable: false
		categorias nullable: true
		imagen nullable: true
	}
}
