import grails.util.Environment
import ordertracker.Cliente
import ordertracker.Ubicacion

class BootStrap {
	def init = { servletContext ->
	def result = 'corriendo en otro modo!'
	println "Iniciando Order Tracker..."
	switch (Environment.current) {
		case Environment.DEVELOPMENT:
			result = 'corriendo en modo DEV!'
			seedTestData()
			break;
		case Environment.TEST:
			result = 'corriendo en modo TEST!'
			break;
		case Environment.PRODUCTION:
			result = 'corriendo en modo PROD!'
			seedProdData()
			break;
   		}
		println "current environment: $Environment.current"
		println "$result"
	}
	def destroy = {
		println "Terminando Order Tracker... "
	}
	
	private void seedTestData() {
		def cliente = null
		def lugar = null
		println "Iniciando carga de clientes en la Base de Datos"
		
		lugar = new Ubicacion(direccion: "Paseo Colón 850", latitud: -34.5887297d, longitud: -58.3966085d)
		cliente = new Cliente(apellido: "Gacitúa Vásquez", nombre: "Daniel", email: "dgv@mail.com", codigoUnico: "0", razonSocial: "0", fechaRegistro: new Date(), ubicacion: lugar)
		assert cliente.save(failOnError:true, flush:true, insert: true)
		cliente.errors = null
		
		lugar = new Ubicacion(direccion: "Av Las Heras 2214", latitud: -34.5884456d, longitud: -58.3960098d)
		cliente = new Cliente(apellido: "Beltrán", nombre: "Belén", email: "bb@mail.com", codigoUnico: "1", razonSocial: "1", fechaRegistro: new Date(), ubicacion: lugar)
		assert cliente.save(failOnError:true, flush:true, insert: true)
		cliente.errors = null
		
		assert Cliente.count == 2;
		
		println "Finalizada carga de $Cliente.count clientes en la Base de Datos"
	}
	
	/*
    def init = { servletContext ->
    }
    def destroy = {
    }
    */
}
