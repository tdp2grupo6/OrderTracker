package ordertracker

import ordertracker.Estados.EstadoCliente
import ordertracker.Perfiles.Vendedor

class Cliente {
	String nombre
	String apellido
	String telefono = ""
	
	String email = ""
	String contrasena = ""
	
	String validador = UUID.randomUUID().toString()
	
	String razonSocial = ""
	String codigoUnico = ""
	String disponibilidad = ""

	EstadoCliente estado = EstadoCliente.ROJO

	String direccion = ""
	double latitud = 0
	double longitud = 0

	ArrayList<Integer> agendaCliente = []
	
	Date fechaRegistro = new Date()
	
	String nombreCompleto() { "$apellido, $nombre" }
	
	static constraints = {
		nombre blank: false
		apellido blank: false
		razonSocial blank: true, nullable: true
		email blank: false, email: true, unique: true
		latitud blank: true, nullable: true, min: -90d, max: 90d
		longitud blank: true, nullable: true, min: -180d, max: 180d
		telefono blank: true, nullable: true
		direccion blank: false, nullable: true
		disponibilidad blank: true, nullable: true
    }

	void eliminarInstancias() {
		def vs = Visita.findAllByCliente(this)
		def ps = Pedido.findAllByCliente(this)
		def cs = Comentario.findAllByCliente(this)

		vs.each {
			it.cliente =  null
			it.save flush:true
		}

		ps.each {
			it.cliente =  null
			it.save flush:true
		}

		cs.each {
			it.cliente =  null
			it.save flush:true
		}

		Vendedor.list().each {
			if (it.clientes.contains(this)) {
				it.removeFromClientes(this)
				it.save flush:true
			}
		}
	}
}
