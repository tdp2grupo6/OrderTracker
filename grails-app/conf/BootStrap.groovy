import grails.util.Environment
import ordertracker.Cliente
import ordertracker.Marca
import ordertracker.Producto

class BootStrap {
	def init = { servletContext ->
		def result = 'Corriendo en otro modo!'
		println "Iniciando Order Tracker..."
		/*
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
	   	*/
		Environment.executeForCurrentEnvironment {
			development {
				result = 'Corriendo en modo DEVELOPMENT!'
				seedDevData()
			}
			test {
				result = 'Corriendo en modo TEST!'
			}
			production {
				result = 'Corriendo en modo PRODUCTION!'
				seedProdData()
			}
			openshift {
				result = 'Corriendo en modo OPENSHIFT!'
				seedOpenshiftData()
			}
		}
		println "Ambiente actual: $Environment.current"
		println "$result"	
	}
	
	def destroy = {
		println "Terminando Order Tracker... "
	}
	
	private void seedDevData() {
		// TODO Terminar Carga de datos en modo desarrollo
		println "Iniciando carga de datos de prueba en la Base de Datos"
		
		// Ejemplo de inserción de Cliente
		def cliente = null
		cliente = new Cliente(apellido: "Gacitúa Vásquez", nombre: "Daniel", email: "dgv@mail.com", razonSocial: "Mi Negocio", direccion: "Paseo Colón 850", latitud: -34.5887297d, longitud: -58.3966085d)
		assert cliente.save(failOnError:true, flush:true, insert: true)
		cliente.errors = null
		
		cliente = new Cliente(apellido: "Beltrán", nombre: "Belén", email: "bb@mail.com", razonSocial: "Un Kiosco Cualquiera", direccion: "Av Las Heras 2214", latitud: -34.5884456d, longitud: -58.3960098d)
		assert cliente.save(failOnError:true, flush:true, insert: true)
		cliente.errors = null
		
		assert Cliente.count == 2;
		println "Finalizada carga de $Cliente.count clientes en la Base de Datos"
		
		// Ejemplo de inserción de Marca
		def marca = null
		marca = new Marca(nombre: "SuperFantabuloso")
		assert marca.save(failOnError:true, flush:true, insert: true)
		marca.errors = null
		
		assert Marca.count == 1;
		println "Finalizada carga de $Marca.count marcas en la Base de Datos"
		
		// Ejemplo de inserción de Producto
		def producto = null
		producto = new Producto(nombre: "Clavos Princesa", marca: marca, precio: 49.99, stock: 0)
		assert producto.save(failOnError:true, flush:true, insert: true)
		producto.errors = null
		
		assert Producto.count == 1;
		println "Finalizada carga de $Producto.count productos en la Base de Datos"
		
		println "Finalizando carga de datos de prueba en la Base de Datos"
	}
	
	private void seedProdData() {
		// TODO Cargar datos de prueba en modo producción
		println "Carga de datos en modo produccion no habilitada!"
	}
	
	private void seedOpenshiftData() {
		// TODO Cargar datos de prueba para la carga en OpenShift
		println "Carga de datos en modo OpenShift no habilitada!"
	}
}
