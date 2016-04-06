package ordertracker.Marshallers

import ordertracker.Marca
import grails.converters.JSON

class MarcaMarshaller {
	void register() {
		JSON.registerObjectMarshaller(Marca) { Marca m ->
			return [
				id: m.id,
				nombre: m.nombre,
				rutaImagen: m.rutaImagen(),
				rutaMiniatura: m.rutaMiniatura()
			]
		}
	}
}
