package ordertracker

class Categoria {
	String nombre
	String caracteristicas = ""
	
	static belongsTo = Producto
	static hasMany = [producto: Producto]
	
	def filtroCategoria() {
		return [
			nombre: nombre,
			codigo: id,
			caracteristicas: caracteristicas
		]
	}

    static constraints = {
		producto nullable: true
    }
}
