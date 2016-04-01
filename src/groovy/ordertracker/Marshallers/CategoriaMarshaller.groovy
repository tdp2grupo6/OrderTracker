package ordertracker.Marshallers

import ordertracker.Categoria
import grails.converters.JSON

class CategoriaMarshaller {
	void register() {
		JSON.registerObjectMarshaller(Categoria) { Categoria c ->
			return [
				id: c.id,
				nombre: c.nombre,
				descripcion: c.descripcion
			]
		}
	}
}
