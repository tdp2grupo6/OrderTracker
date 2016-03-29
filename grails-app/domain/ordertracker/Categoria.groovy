package ordertracker

class Categoria {
	String nombre
	String caracteristicas = ""
	
	static belongsTo = Producto
	static hasMany = [producto: Producto]
	
	def filtroCategoria() {
		return [
			id: id,
			nombre: nombre,
			caracteristicas: caracteristicas
		]
	}

    static constraints = {
		producto nullable: true
    }
}
