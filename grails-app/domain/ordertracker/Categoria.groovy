package ordertracker

class Categoria {
	String nombre
	String descripcion = ""

	static hasMany = [productos: Producto]

    static constraints = {
		nombre blank: false
		productos nullable: true
    }
}
