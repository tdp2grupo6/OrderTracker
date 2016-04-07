package ordertracker

class Categoria {
	String nombre
	String descripcion = ""

	static hasMany = [productos: Producto]
	static belongsTo = Producto

    static constraints = {
		nombre blank: false
		productos nullable: true
    }
}
