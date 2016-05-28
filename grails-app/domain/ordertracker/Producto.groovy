package ordertracker

import grails.converters.JSON
import ordertracker.Estados.EstadoProducto
import ordertracker.Relations.CategoriaProducto
import org.codehaus.groovy.grails.web.json.JSONObject

class Producto {
	String nombre
	String codigo = ""
	String caracteristicas = ""
	
	int stock = 0
	float precio = 0f
	EstadoProducto estado = EstadoProducto.DISP

	Imagen imagen
	Object categorias

	static hasOne = [marca: Marca]
	static belongsTo = [Marca]
	//static hasMany = [categorias: Categoria]
	//static belongsTo = [Marca, Categoria]
	
	def listaCategorias() {
		List<Categoria> cats = obtenerCategorias() as List
		return cats.collect() {
			[
				codigo: it.id,
				nombre: it.nombre
			]
		}
	}
	
	String rutaImagen() {
		if (imagen!=null) {
			return "imagen/ver/$imagen.id"
		}
		else {
			Imagen img = Imagen.findByOriginalFilename(ImagenController.nombreImagenRelleno)
			return "imagen/ver/$img.id"
		}
	}

	String rutaMiniatura() {
		if (imagen!=null) {
			return "imagen/miniatura/$imagen.id"
		}
		else {
			Imagen img = Imagen.findByOriginalFilename(ImagenController.nombreImagenRelleno)
			return "imagen/miniatura/$img.id"
		}
	}

	Set<Categoria> obtenerCategorias() {
		CategoriaProducto.findAllByProducto(this).collect { it.categoria } as Set
	}

	boolean tieneCategoria(Categoria cat) {
		CategoriaProducto.countByProductoAndCategoria(this, cat) > 0
	}
	
    static constraints = {
		nombre blank: false
		codigo blank: true
		marca nullable: true
		categorias nullable: true
		imagen nullable: true
	}

	void procesarCategorias(JSONObject input) {
		List parsed = input.categorias

		CategoriaProducto.removeAll(this)

		parsed.each {
			Categoria cat = Categoria.findById(it.id)
			CategoriaProducto.create(cat, this)
			println "[OT-LOG] Asociando producto ${this.nombre} con categoria ${cat.nombre}"
		}
	}

	void eliminarInstancias() {
		this.marca = null
		//this.categorias = null
		this.imagen = null

		this.save(flush: true)

		/*
		Categoria.list().each {
			if (it.productos.contains(this)) {
				it.removeFromProductos(this)
			}
		}
		*/
		CategoriaProducto.removeAll(this)

		Marca.list().each {
			if (it.productos.contains(this)) {
				it.removeFromProductos(this)
			}
		}

		PedidoElemento.list().each {
			if (it.producto == this) {
				it.producto = null
			}
		}
	}
}
