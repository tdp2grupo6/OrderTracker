package ordertracker

import ordertracker.Estados.EstadoCliente
import ordertracker.Perfiles.Vendedor

class Cliente {
	String nombre
	String apellido
	String telefono = ""
	
	String email
	String contrasena = ""
	
	String validador = UUID.randomUUID().toString()
	
	String razonSocial = ""
	String codigoUnico = ""
	String disponibilidad = ""

	EstadoCliente estado = EstadoCliente.ROJO

	String direccion = ""
	double latitud
	double longitud

	ArrayList<Integer> agendaCliente = []
	
	Date fechaRegistro = new Date()
	
	String nombreCompleto() { "$apellido, $nombre" }
	
	static constraints = {
		nombre blank: false
		apellido blank: false
		email blank: false, email: true
		latitud blank: true, nullable: true, min: -90d, max: 90d
		longitud blank: true, nullable: true, min: -180d, max: 180d
    }

	void eliminarInstancias() {
		Pedido.findAllByCliente(this)*.cliente = null

		Vendedor.list().each {
			if (it.clientes.contains(this)) {
				it.removeFromClientes(this)
			}
		}
	}
}
