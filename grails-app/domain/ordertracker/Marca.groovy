package ordertracker

class Marca {
	String nombre
	String codigo = ""
	String rutaImagen = "" // TODO cambiar por imagen por defecto
	
	// TODO Decidir implementación de imagen (nativa o Base64)
	/*
	byte[] imagen		// codificada en Base64
	String imagenTipo	// MIME type
	*/
	
	static hasMany = [productos: Producto]
	
    static constraints = {
		nombre blank: false
		codigo blank: true
		rutaImagen nullable: true
		productos nullable: true
		
		// TODO Decidir implementación de imagen (nativa o Base64)
		/*
		imagen nullable: true, maxSize: 32768	// 32KB en Base64, 24KB en JPG/PNG
		imagenTipo nullable: true
		 */
    }
}
