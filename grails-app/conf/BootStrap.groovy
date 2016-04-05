import org.springframework.web.context.support.WebApplicationContextUtils
import grails.util.Environment
import ordertracker.Cliente
import ordertracker.Marca
import ordertracker.Producto

class BootStrap {
	def init = { servletContext ->
		// Importando Marshallers
		def springContext = WebApplicationContextUtils.getWebApplicationContext( servletContext )
		springContext.getBean( "customObjectMarshallers" ).register()
		
		// Código separado por Ambiente de Deploy
		def result = 'Corriendo en otro modo!'
		println "[OT-LOG] Iniciando Order Tracker..."
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
		println "[OT-LOG] Ambiente actual: $Environment.current"
		println "[OT-LOG] $result"	
	}
	
	def destroy = {
		println "[OT-LOG] Terminando Order Tracker... "
	}
	
	private void seedDevData() {
		println "[OT-LOG] Iniciando carga de datos de prueba en la Base de Datos"
		
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
		
		println "[OT-LOG] Finalizada carga de $Cliente.count clientes en la Base de Datos"
		
		// Ejemplo de inserción de Marca y Producto
		def marca = null
		def producto = null
		marca = new Marca(nombre: "Adidas")
		assert marca.save(failOnError:true, flush:true, insert: true)
		marca.errors = null
		
		producto = new Producto(nombre: "Mochila Deportiva Negra", caracteristicas: "Mochila de alta capacidad y costura reforzada", marca: marca, precio: 849.00, stock: 3)
		assert producto.save(failOnError:true, flush:true, insert: true)
		producto.errors = null
		
		producto = new Producto(nombre: "Bolso de la Seleccion", caracteristicas: "Diseño y estilo de la Selección Argentina", marca: marca, precio: 1002.99, stock: 34)
		assert producto.save(failOnError:true, flush:true, insert: true)
		producto.errors = null
		
		marca = new Marca(nombre: "A.Y. NOT DEAD")
		assert marca.save(failOnError:true, flush:true, insert: true)
		marca.errors = null
		
		producto = new Producto(nombre: "Vestido Print", caracteristicas: "Temporada otoño-invierno 2016", marca: marca, precio: 1433.99, stock: 12)
		assert producto.save(failOnError:true, flush:true, insert: true)
		producto.errors = null
		
		marca = new Marca(nombre: "Akiabara")
		assert marca.save(failOnError:true, flush:true, insert: true)
		marca.errors = null
		
		producto = new Producto(nombre: "Vestido Basico", caracteristicas: "Diseño sencillo", marca: marca, precio: 995.99, stock: 2)
		assert producto.save(failOnError:true, flush:true, insert: true)
		producto.errors = null
		
		producto = new Producto(nombre: "Vestido Mini Negro", caracteristicas: "Fuera de temporada", marca: marca, precio: 666.99, stock: 12)
		assert producto.save(failOnError:true, flush:true, insert: true)
		producto.errors = null
		
		marca = new Marca(nombre: "Nike")
		assert marca.save(failOnError:true, flush:true, insert: true)
		marca.errors = null
		
		producto = new Producto(nombre: "Zapatillas de Correr", caracteristicas: "Modelo Runner Fit", marca: marca, precio: 1600.00, stock: 45)
		assert producto.save(failOnError:true, flush:true, insert: true)
		producto.errors = null
		
		marca = new Marca(nombre: "Zara")
		assert marca.save(failOnError:true, flush:true, insert: true)
		marca.errors = null
		
		producto = new Producto(nombre: "Remera Zebra", caracteristicas: "Colección Animal", marca: marca, precio: 349.99, stock: 15)
		assert producto.save(failOnError:true, flush:true, insert: true)
		producto.errors = null
		
		producto = new Producto(nombre: "Remera Leopardo", caracteristicas: "Colección Animal", marca: marca, precio: 449.99, stock: 15)
		assert producto.save(failOnError:true, flush:true, insert: true)
		producto.errors = null
		
		println "[OT-LOG] Finalizada carga de $Marca.count marcas en la Base de Datos"
		
		println "[OT-LOG] Finalizada carga de $Producto.count productos en la Base de Datos"
		
		println "[OT-LOG] Finalizando carga de datos de prueba en la Base de Datos"
	}
	
	private void seedProdData() {
		// TODO Cargar datos de prueba en modo producción
		println "[OT-LOG] Carga de datos en modo produccion no habilitada!"
	}
	
	private void seedOpenshiftData() {
		seedDevData()
	}
}
