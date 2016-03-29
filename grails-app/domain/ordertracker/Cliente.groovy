package ordertracker

class Cliente {
	String nombre
	String apellido
	String telefono = ""
	
	String email
	String contrasena = ""
	
	String identificador = ""
	
	String razonSocial = ""
	String codigoUnico = ""
	
	String direccion = ""
	double latitud
	double longitud
	
	Date fechaRegistro = new Date()
	
	String nombreCompleto() { "$apellido, $nombre" }
	
	def filtroCliente() {
		return [
			id: id,
			nombreCompleto: nombreCompleto(),
			nombre: nombre,
			apellido: apellido,
			razonSocial: razonSocial,
			direccion: direccion,
			telefono: telefono,
			email: email,
			latitud: latitud,
			longitud: longitud
		]
	}
	
	static constraints = {
		nombre blank: false
		apellido blank: false
		email blank: false, mail: true
		latitud blank: false, nullable: true
		longitud blank: false, nullable: true
    }
}
