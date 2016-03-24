import grails.util.Environment
import ordertracker.Cliente

class BootStrap {
	def init = { servletContext ->
	def result = 'Corriendo en otro modo!'
	println "Iniciando Order Tracker..."
	switch (Environment.current) {
		case Environment.DEVELOPMENT:
			result = 'Corriendo en modo DEV!'
			seedTestData()
			break;
		case Environment.TEST:
			result = 'Corriendo en modo TEST!'
			break;
		case Environment.PRODUCTION:
			result = 'Corriendo en modo PROD!'
			seedProdData()
			break;
   		}
		println "Ambiente actual: $Environment.current"
		println "$result"
	}
	def destroy = {
		println "Terminando Order Tracker... "
	}
	
	private void seedTestData() {
		def cliente = null
		println "Iniciando carga de clientes en la Base de Datos"
		
		cliente = new Cliente(apellido: "Gacitúa Vásquez", nombre: "Daniel", email: "dgv@mail.com", codigoUnico: "0", razonSocial: "0", direccion: "Paseo Colón 850", latitud: -34.5887297d, longitud: -58.3966085d)
		assert cliente.save(failOnError:true, flush:true, insert: true)
		cliente.errors = null
		
		cliente = new Cliente(apellido: "Beltrán", nombre: "Belén", email: "bb@mail.com", codigoUnico: "1", razonSocial: "1", direccion: "Av Las Heras 2214", latitud: -34.5884456d, longitud: -58.3960098d)
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
