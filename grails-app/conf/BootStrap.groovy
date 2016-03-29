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
		println "Iniciando carga de datos de prueba en la Base de Datos"
		
		// Ejemplo de inserción de Cliente
		def cliente = null
		cliente = new Cliente(apellido: "Luna", nombre: "Silvina", email: "silvi@gmail.com", razonSocial: "Silvina Luna", direccion: "Las Heras 2850", latitud: -34.5887297d, longitud: -58.3966085d)
		assert cliente.save(failOnError:true, flush:true, insert: true)
		cliente.errors = null
				
		cliente = new Cliente(apellido: "Rial", nombre: "Jorge", email: "jrial@hotmail.com", razonSocial: "Jorge Rial", direccion: "Monroe 1501", latitud: -34.552929d, longitud: -58.451036d)
		assert cliente.save(failOnError:true, flush:true, insert: true)
		cliente.errors = null
		
		cliente = new Cliente(apellido: "Tinelli", nombre: "Marcelo", email: "mtinelli@gmail.com", razonSocial: "Marcelo Tinelli", direccion: "Ugarte 152", latitud: -34.5887297d, longitud: -58.3966085d)
		assert cliente.save(failOnError:true, flush:true, insert: true)
		cliente.errors = null
		
		cliente = new Cliente(apellido: "Tevez", nombre: "Carlos", email: "apache@hotmail.com", razonSocial: "Carlos Tevez", direccion: "Libertador 1052", latitud: -34.5887297d, longitud: -58.3966085d)
		assert cliente.save(failOnError:true, flush:true, insert: true)
		cliente.errors = null
		
		cliente = new Cliente(apellido: "Peña", nombre: "Florencia", email: "pena@gmail.com", razonSocial: "Florencia Peña", direccion: "Libertador 1090", latitud: -34.5887297d, longitud: -58.3966085d)
		assert cliente.save(failOnError:true, flush:true, insert: true)
		cliente.errors = null
		
		cliente = new Cliente(apellido: "Canosa", nombre: "Viviana", email: "vivicanosa@gmail.com", razonSocial: "Viviana Canosa", direccion: "Monroe 890", latitud: -34.5887297d, longitud: -58.3966085d)
		assert cliente.save(failOnError:true, flush:true, insert: true)
		cliente.errors = null
		
		cliente = new Cliente(apellido: "Mendoza", nombre: "Flavio", email: "fmendoza@gmail.com", razonSocial: "Flavio Mendoza", direccion: "Blanco Encalada 390", latitud: -34.5887297d, longitud: -58.3966085d)
		assert cliente.save(failOnError:true, flush:true, insert: true)
		cliente.errors = null
		
		println "Finalizada carga de $Cliente.count clientes en la Base de Datos"
		
		// Ejemplo de inserción de Marca y Producto
		def marca = null
		def producto = null
		marca = new Marca(nombre: "Adidas")
		assert marca.save(failOnError:true, flush:true, insert: true)
		marca.errors = null
		
		producto = new Producto(nombre: "Mochila Deportiva Negra", marca: marca, precio: 849.00, stock: 3)
		assert producto.save(failOnError:true, flush:true, insert: true)
		producto.errors = null
		
		producto = new Producto(nombre: "Bolso de la Seleccion", marca: marca, precio: 1002.99, stock: 34)
		assert producto.save(failOnError:true, flush:true, insert: true)
		producto.errors = null
		
		marca = new Marca(nombre: "A.Y. NOT DEAD")
		assert marca.save(failOnError:true, flush:true, insert: true)
		marca.errors = null
		
		producto = new Producto(nombre: "Vestido Print", marca: marca, precio: 1433.99, stock: 12)
		assert producto.save(failOnError:true, flush:true, insert: true)
		producto.errors = null
		
		marca = new Marca(nombre: "Akiabara")
		assert marca.save(failOnError:true, flush:true, insert: true)
		marca.errors = null
		
		producto = new Producto(nombre: "Vestido Basico", marca: marca, precio: 995.99, stock: 2)
		assert producto.save(failOnError:true, flush:true, insert: true)
		producto.errors = null
		
		producto = new Producto(nombre: "Vestido Mini Negro", marca: marca, precio: 666.99, stock: 12)
		assert producto.save(failOnError:true, flush:true, insert: true)
		producto.errors = null
		
		marca = new Marca(nombre: "Nike")
		assert marca.save(failOnError:true, flush:true, insert: true)
		marca.errors = null
		
		producto = new Producto(nombre: "Zapatillas de Correr", marca: marca, precio: 1600.00, stock: 45)
		assert producto.save(failOnError:true, flush:true, insert: true)
		producto.errors = null
		
		marca = new Marca(nombre: "Zara")
		assert marca.save(failOnError:true, flush:true, insert: true)
		marca.errors = null
		
		producto = new Producto(nombre: "Remera zebra", marca: marca, precio: 349.99, stock: 15)
		assert producto.save(failOnError:true, flush:true, insert: true)
		producto.errors = null
		
		producto = new Producto(nombre: "Remera leopardo", marca: marca, precio: 449.99, stock: 15)
		assert producto.save(failOnError:true, flush:true, insert: true)
		producto.errors = null
		
		println "Finalizada carga de $Marca.count marcas en la Base de Datos"
		
		println "Finalizada carga de $Producto.count productos en la Base de Datos"
		
		println "Finalizando carga de datos de prueba en la Base de Datos"
	}
	
	private void seedProdData() {
		// TODO Cargar datos de prueba en modo producción
		println "Carga de datos en modo produccion no habilitada!"
	}
	
	private void seedOpenshiftData() {
		seedDevData()
	}
}
