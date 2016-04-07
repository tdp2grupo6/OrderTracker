package ordertracker.Marshallers

import ordertracker.Producto
import grails.converters.JSON

class ProductoMarshaller {
	void register() {
		JSON.registerObjectMarshaller(Producto) { Producto p ->
			return [
				id: p.id,
				nombre: p.nombre,
				marca: p.marca.nombre,
				caracteristicas: p.caracteristicas,
				categorias: p.listaCategorias(),
				rutaImagen: p.rutaImagen(),
				rutaMiniatura: p.rutaMiniatura(),
				stock: p.stock,
				precio: p.precio,
				estado: p.estado.displayEnum()
			]
		}
	}
}
