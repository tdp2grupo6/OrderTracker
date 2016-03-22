package ordertracker

class Marca {
	String nombre
	String codigo
	String urlImagen = ""
	
	static hasMany = [productos: Producto]

    static constraints = {
		nombre blank: false
		codigo blank: false
    }
}
