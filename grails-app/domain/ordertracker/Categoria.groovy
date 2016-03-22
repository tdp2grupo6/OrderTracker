package ordertracker

class Categoria {
	String nombre
	
	static belongsTo = [producto: Producto]

    static constraints = {
    }
}
