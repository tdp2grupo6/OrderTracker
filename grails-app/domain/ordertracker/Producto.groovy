package ordertracker

class Producto {
	String nombre
	String codigo = ""
	String caracteristicas = ""
	
	String rutaImagen
	
	// TODO Decidir implementación de imagen (nativa o Base64)
	/*
	byte[] imagen		// codificada en Base64
	String imagenTipo	// MIME type
	*/
	
	int stock = 0
	float precio = 0f
	EstadoProducto estado = EstadoProducto.NODISP
	
	static hasOne = [marca: Marca]		// TODO implementar Marcas
	static hasMany = [categorias: Categoria]
	
	def listaCategorias() {
		def cat = this.categorias.collect() {
			[
				codigo: it.id,
				nombre: it.nombre
			]
		}
		return cat
	}
	
	String nombreMarca() { "$marca.nombre" }
	
    static constraints = {
		nombre blank: false
		codigo blank: true
		marca nullable: false
		categorias nullable: true
		rutaImagen nullable: true
		
		// TODO Decidir implementación de imagen (nativa o Base64)
		/*
		imagen nullable: true, maxSize: 32768	// 32KB en Base64, 24KB en JPG/PNG
		imagenTipo nullable: true
		 */
    }
}
