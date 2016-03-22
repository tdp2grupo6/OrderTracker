package ordertracker

class Ubicacion {
	String direccion = ""
	
	String ciudad = ""
	String region = ""
	
	double latitud
	double longitud
	
	String direccionCompleta() { "$direccion, $ciudad, $region" }
	
	static belongsTo = [cliente: Cliente]

    static constraints = {
		latitud min: -90d, max: 90d, blank: false
		longitud blank: false		
    }
}
