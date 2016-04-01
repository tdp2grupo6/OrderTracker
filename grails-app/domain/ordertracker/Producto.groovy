package ordertracker

class Producto {
	String nombre
	String codigo = ""
	String caracteristicas = ""
	
	String rutaImagen = ""  // TODO cambiar por imagen por defecto
	
	int stock = 0
	float precio = 0f
	EstadoProducto estado = EstadoProducto.NODISP
	
	//static belongsTo = [Marca]
	static hasOne = [marca: Marca]
	static hasMany = [categorias: Categoria]
	
	def listaCategorias() {
		return categorias.collect() {
			[
				codigo: it.id,
				nombre: it.nombre
			]
		}
	}
	
	String nombreMarca() { "$marca.nombre" }
	
    static constraints = {
		nombre blank: false
		codigo blank: true
		marca nullable: false
		categorias nullable: true
		rutaImagen nullable: true
	}
}
