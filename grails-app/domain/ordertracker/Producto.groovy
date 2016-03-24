package ordertracker

class Producto {
	String nombre
	String codigo =  ""
	String urlImagen = ""
	String caracteristicas = ""
	
	String rutaImagen
	
	String categoria = "" // TODO cambiar categoría a clase de dominio
	Marca marca
	
	// TODO Decidir implementación de imagen (nativa o Base64)
	/*
	byte[] imagen		// codificada en Base64
	String imagenTipo	// MIME type
	*/
	
	int stock = 0
	float precio = 0f
	EstadoProducto estado = EstadoProducto.NODISP
	
	//static belongsTo = [marca: Marca]		// TODO implementar Marcas
	
    static constraints = {
		nombre blank: false
		marca nullable: true
		rutaImagen nullable: true
		
		// TODO Decidir implementación de imagen (nativa o Base64)
		/*
		imagen nullable: true, maxSize: 32768	// 32KB en Base64, 24KB en JPG/PNG
		imagenTipo nullable: true
		 */
    }
}
