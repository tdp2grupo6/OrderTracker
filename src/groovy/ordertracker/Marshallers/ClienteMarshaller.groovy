package ordertracker.Marshallers

import ordertracker.Cliente
import grails.converters.JSON

class ClienteMarshaller {
	void register() {
		JSON.registerObjectMarshaller(Cliente) { Cliente c ->
			return [
				id: c.id,
				nombreCompleto: c.nombreCompleto(),
				nombre: c.nombre,
				apellido: c.apellido,
				razonSocial: c.razonSocial,
				direccion: c.direccion,
				telefono: c.telefono,
				email: c.email,
				latitud: c.latitud,
				longitud: c.longitud,
				validador: c.validador,
				disponibilidad: c.disponibilidad,
				estado: c.estado.displayEnum()
			]
		}
	}
}
