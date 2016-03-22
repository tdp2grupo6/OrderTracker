package ordertracker

class Cliente {
	String nombre
	String apellido
	String telefono = ""
	
	String email = ""
	String contrasena = ""
	
	String identificador = ""
	
	String razonSocial = ""
	String codigoUnico = ""
	
	Date fechaRegistro
	
	String nombreCompleto() { "$apellido, $nombre" }
	
	static hasOne = [ubicacion: Ubicacion]

    static constraints = {
		nombre blank: false
		apellido blank: false
		email blank: false, mail: true
		fechaRegistro blank: false
    }
}
