package ordertracker

import org.codehaus.groovy.grails.orm.hibernate.cfg.IdentityEnumType

class Producto {
	String nombre
	String codigo =  ""
	String urlImagen = ""
	String caracteristicas = ""
	
	int stock = 0
	float precio = 0f
	EstadoProducto estado = EstadoProducto.NODISP
	
	static hasOne = [categoria: Categoria]
	static belongsTo = [marca: Marca]
	
    static constraints = {
		nombre blank: false
    }
	
	static mapping = {
		estadoProducto(type: IdentityEnumType)
	}

	enum EstadoProducto {
		DISP("Disponible"), NODISP("No Disponible"), SUSP("Suspendido")
		String id
		EstadoProducto(String id) { this.id = id }
	}
}
