package ordertracker

class Producto {
	String nombre
	String codigo = ""
	String caracteristicas = ""
	
	String rutaImagen
	
	int stock = 0
	float precio = 0f
	EstadoProducto estado = EstadoProducto.NODISP
	
	static hasOne = [marca: Marca]		// TODO implementar Marcas
	static hasMany = [categorias: Categoria]
	
	def listaCategorias() {
		return categorias.collect() {
			[
				codigo: it.id,
				nombre: it.nombre
			]
		}
	}
	
	def filtroProducto() {
		return [
			id: id,
			nombre: nombre,
			marca: nombreMarca(),
			caracteristicas: caracteristicas,
			categoria: categorias,
			rutaImagen: rutaImagen,
			stock: stock,
			precio: precio,
			estado: estado.toString()
		]
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
