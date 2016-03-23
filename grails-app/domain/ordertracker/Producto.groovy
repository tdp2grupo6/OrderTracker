package ordertracker

import org.codehaus.groovy.grails.orm.hibernate.cfg.IdentityEnumType

class Producto {
	String nombre
	String codigo =  ""
	String urlImagen = ""
	String caracteristicas = ""
	
	String categoria = "" // TODO cambiar categoría a clase de dominio
	
	int stock = 0
	float precio = 0f
	EstadoProducto estado = EstadoProducto.NODISP
	
	//static belongsTo = [marca: Marca]		// TODO implementar Marcas
	
    static constraints = {
		nombre blank: false
    }
	
	static mapping = {
		estado(type: IdentityEnumType)
	}
}
