package ordertracker

class Categoria {
	String nombre
	String descripcion = ""

	static hasMany = [producto: Producto]

    static constraints = {
		producto nullable: true
    }
}
