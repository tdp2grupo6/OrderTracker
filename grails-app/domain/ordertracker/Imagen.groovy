package ordertracker

class Imagen {
	String originalFilename = ""
	String thumbnailFilename = ""
	String newFilename = ""
	int fileSize = 0

	static belongsTo = [producto: Producto, marca: Marca]

    static constraints = {
		producto nullable: true
		marca nullable: true
    }
}