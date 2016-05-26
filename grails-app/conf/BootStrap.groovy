import grails.util.Environment
import ordertracker.*
import ordertracker.Perfiles.Vendedor
import ordertracker.Security.Perfil
import ordertracker.Security.Rol
import ordertracker.Security.Usuario
import ordertracker.Security.UsuarioRol
import ordertracker.Perfiles.Admin
import ordertracker.Perfiles.Vendedor
import ordertracker.Utils
import org.springframework.web.context.support.WebApplicationContextUtils

class BootStrap {
	def init = { servletContext ->
		// Importando Marshallers
		def springContext = WebApplicationContextUtils.getWebApplicationContext( servletContext )
		springContext.getBean( "customObjectMarshallers" ).register()

		// Fijando la zona horaria del servidor
		//setTimeZone()

		// Cargando Perfil y Usuarios
		precargarUsuarios()

		// Código separado por Ambiente de Deploy
		def result = 'Corriendo en otro modo!'
		println "[OT-LOG] Iniciando Order Tracker..."
		Environment.executeForCurrentEnvironment {
			development {
				result = 'Corriendo en modo DEVELOPMENT!'
				precargarClientes()
				enlazarClientes()
				precargarProductos()
				precargarPedidos()
				Utils.actualizarClientesInicio()
			}
			test {
				result = 'Corriendo en modo TEST!'
			}
			production {
				result = 'Corriendo en modo PRODUCTION!'
			}
			openshift {
				result = 'Corriendo en modo OPENSHIFT!'
				precargarClientes()
				enlazarClientes()
				precargarProductos()
				precargarPedidos()
				Utils.actualizarClientesInicio()
			}
		}
		println "[OT-LOG] Ambiente actual: $Environment.current"
		println "[OT-LOG] $result"
	}

	def destroy = {
		println "[OT-LOG] Terminando Order Tracker... "
	}

	private void precargarUsuarios() {
		// Perfil
		println "[OT-LOG] Iniciando carga de Roles en la Base de Datos..."

		def rolAdmin = new Rol(Perfil.ADMIN.valor).save(flush:true)
		def rolVendedor = new Rol(Perfil.VENDEDOR.valor).save(flush:true)
		def rolCliente = new Rol(Perfil.CLIENTE.valor).save(flush:true)

		assert Rol.count == 3
		println "[OT-LOG] Finalizada carga de $Rol.count roles en la Base de Datos"

		// Usuarios de prueba
		println "[OT-LOG] Iniciando carga de usuarios en la Base de Datos..."

		def admin = new Admin(username: 'admin', password: 'admin', nombre: 'Administrador', apellido: 'OrderTracker', email: 'admin@ordertracker.com.ar').habilitarUsuario()		//.save(failOnError:true, flush:true, insert: true)
		//UsuarioRol.create admin, rolAdmin, true

		def vend = new Vendedor(username: 'vendedor', password: 'vendedor', nombre: 'Vendedor', apellido: 'OrderTracker', email: 'vendedor@ordertracker.com.ar').habilitarUsuario()
		//UsuarioRol.create vend, rolVendedor, true

		def vend1 = new Vendedor(username: 'bbeltran', password: 'bbeltran', nombre: 'Belen', apellido: 'Beltran', email: 'bbeltran@ordertracker.com.ar').habilitarUsuario()
		//UsuarioRol.create vend1, rolVendedor, true

		def vend2 = new Vendedor(username: 'dgacitua', password: 'dgacitua', nombre: 'Daniel', apellido: 'Gacitua', email: 'dgacitua@ordertracker.com.ar').habilitarUsuario()
		//UsuarioRol.create vend2, rolVendedor, true

		def vend3 = new Vendedor(username: 'poddo', password: 'poddo', nombre: 'Pablo', apellido: 'Oddo', email: 'poddo@ordertracker.com.ar').habilitarUsuario()
		//UsuarioRol.create vend3, rolVendedor, true

		def vend4 = new Vendedor(username: 'mroitman', password: 'mroitman', nombre: 'Maximiliano', apellido: 'Roitman', email: 'mroitman@ordertracker.com.ar').habilitarUsuario()
		//UsuarioRol.create vend4, rolVendedor, true

		assert Usuario.count == 6
		println "[OT-LOG] Finalizada carga de $Usuario.count usuarios en la Base de Datos"
	}

	private void precargarClientes() {
		println "[OT-LOG] Iniciando carga de datos de prueba en la Base de Datos..."

		// Ejemplo de inserción de Cliente
		def cliente, agenda
		agenda = [Utils.LUNES, Utils.MIERCOLES, Utils.VIERNES]
		cliente = new Cliente(apellido: "Luna", nombre: "Silvina", email: "silvi@gmail.com", telefono: "11 2233-4455", razonSocial: "Silvina Luna", direccion: "Av. Gral. Las Heras 2214", latitud: -34.588446d, longitud: -58.39601d, agendaCliente: agenda, validador: "ff5a6227-e6d5-481d-85d4-9684d728f7a2")
		assert cliente.save(failOnError: true, flush: true, insert: true)
		cliente.errors = null

		agenda = [Utils.MARTES, Utils.JUEVES, Utils.VIERNES]
		cliente = new Cliente(apellido: "Rial", nombre: "Jorge", email: "jrial@hotmail.com", telefono: "11 2233-4456", razonSocial: "Jorge Rial", direccion: "Jean Jaures 379", latitud: -34.605707d, longitud: -58.409504d, agendaCliente: agenda, validador: "f40cbd4c-9428-40d8-9a1b-11a62f79c513")
		assert cliente.save(failOnError: true, flush: true, insert: true)
		cliente.errors = null

		agenda = [Utils.MARTES, Utils.MIERCOLES, Utils.JUEVES]
		cliente = new Cliente(apellido: "Tinelli", nombre: "Marcelo", email: "mtinelli@gmail.com", telefono: "11 2233-9900", razonSocial: "Videoclub El Match", direccion: "Av. Raúl Scalabrini Ortiz 1896", latitud: -34.589242d, longitud: -58.422554d, agendaCliente: agenda, validador: "c66c9507-c11e-4b85-9a65-8bd17c234e1c")
		assert cliente.save(failOnError: true, flush: true, insert: true)
		cliente.errors = null

		agenda = [Utils.LUNES, Utils.MARTES, Utils.MIERCOLES]
		cliente = new Cliente(apellido: "Tévez", nombre: "Carlos", email: "apache@hotmail.com", telefono: "11 2233-0303", razonSocial: "Kioscos Apache", direccion: "Av. Santa Fe 3233", latitud: -34.588553d, longitud: -58.410603d, agendaCliente: agenda, validador: "80d45a49-29a0-46e3-a3b4-4cf3ea6f0808")
		assert cliente.save(failOnError: true, flush: true, insert: true)
		cliente.errors = null

		agenda = [Utils.MARTES, Utils.JUEVES, Utils.SABADO]
		cliente = new Cliente(apellido: "Peña", nombre: "Florencia", email: "pena@gmail.com", telefono: "11 2233-0666", razonSocial: "Florencia Peña", direccion: "Laprida 2025", latitud: -34.587662d, longitud: -58.399794d, agendaCliente: agenda, validador: "b5d5cdfd-b790-4c18-987a-1ae73368685a")
		assert cliente.save(failOnError: true, flush: true, insert: true)
		cliente.errors = null

		agenda = [Utils.MIERCOLES, Utils.JUEVES, Utils.VIERNES]
		cliente = new Cliente(apellido: "Canosa", nombre: "Viviana", email: "vivicanosa@gmail.com", telefono: "11 2233-7777", razonSocial: "Viviana Canosa", direccion: "Dr. Tomás Manuel de Anchorena 1676", latitud: -34.591166d, longitud: -58.402729d, agendaCliente: agenda, validador: "383cc558-16f2-49ea-b8a9-804fd7f11fe5")
		assert cliente.save(failOnError: true, flush: true, insert: true)
		cliente.errors = null

		agenda = [Utils.LUNES, Utils.MARTES, Utils.JUEVES]
		cliente = new Cliente(apellido: "Mendoza", nombre: "Flavio", email: "fmendoza@gmail.com", telefono: "11 2233-4111", razonSocial: "Flavio Mendoza", direccion: "Azcuénaga 1517", latitud: -34.591316d, longitud: -58.397939d, agendaCliente: agenda, validador: "7fa98ddd-a1fe-4ef1-b4df-258c8cfd98dd")
		assert cliente.save(failOnError: true, flush: true, insert: true)
		cliente.errors = null

		agenda = [Utils.MARTES, Utils.MIERCOLES, Utils.SABADO]
		cliente = new Cliente(apellido: "Lanzelotta", nombre: "Brian", email: "brianista@hotmail.com", telefono: "11 2233-4169", razonSocial: "Tiendas Gran Hermano", direccion: "Av. Santa Fe 2385", latitud: -34.594764d, longitud: -58.400834d, agendaCliente: agenda, validador: "d4ac8fa4-f15d-4449-a668-04596e5b135e")
		assert cliente.save(failOnError: true, flush: true, insert: true)
		cliente.errors = null

		agenda = [Utils.MIERCOLES, Utils.JUEVES, Utils.VIERNES]
		cliente = new Cliente(apellido: "Nara", nombre: "Wanda", email: "wanda@gmail.com", telefono: "11 2233-4169", razonSocial: "Boutique Le Amour", direccion: "Av. Pueyrredón 1368", latitud: -34.595068d, longitud: -58.402616d, agendaCliente: agenda, validador: "4efa300e-bc12-4c51-9e51-bf1423f2686c")
		assert cliente.save(failOnError: true, flush: true, insert: true)
		cliente.errors = null

		agenda = [Utils.MARTES, Utils.VIERNES, Utils.SABADO]
		cliente = new Cliente(apellido: "Maradona", nombre: "Diego", email: "d10s@gmail.com", telefono: "11 2233-1010", razonSocial: "Fotografías El Pibe de Oro", direccion: "Caminito S/N", latitud: -34.639436d, longitud: -58.361991d, agendaCliente: agenda, validador: "51239661-48aa-4968-b552-906cc72188ed")
		assert cliente.save(failOnError: true, flush: true, insert: true)
		cliente.errors = null

		agenda = [Utils.MIERCOLES, Utils.JUEVES, Utils.SABADO]
		cliente = new Cliente(apellido: "Legrand", nombre: "Mirtha", email: "legrand@gmail.com", telefono: "11 4512-5069", razonSocial: "Mirtha Legrand", direccion: "Defensa 1060", latitud: -34.620147d, longitud: -58.371383d, agendaCliente: agenda, validador: "62e910ae-aedf-44e1-af40-19c8685a9458")
		assert cliente.save(failOnError: true, flush: true, insert: true)
		cliente.errors = null

		agenda = [Utils.LUNES, Utils.MARTES, Utils.JUEVES]
		cliente = new Cliente(apellido: "Suárez", nombre: "María Eugenia", email: "lachina@gmail.com", telefono: "11 4512-1736", razonSocial: "Vicuña Producciones", direccion: "Florida 145", latitud: -34.606465d, longitud: -58.375165d, agendaCliente: agenda, validador: "9f189515-1f9e-4a8d-8962-406202819f89")
		assert cliente.save(failOnError: true, flush: true, insert: true)
		cliente.errors = null

		agenda = [Utils.MIERCOLES, Utils.VIERNES, Utils.SABADO]
		cliente = new Cliente(apellido: "Revilla", nombre: "Ángel David", email: "drossrotzank@yahoo.com", telefono: "11 4512-1712", razonSocial: "Productora Top 7", direccion: "Av. Pres. Roque Sáenz Peña 865", latitud: -34.605222d, agendaCliente: agenda, longitud: -58.378619d, validador: "46b5f0f3-8f45-4ed2-885a-21f4dbd89fa2")
		assert cliente.save(failOnError: true, flush: true, insert: true)
		cliente.errors = null

		agenda = [Utils.MARTES, Utils.JUEVES, Utils.VIERNES]
		cliente = new Cliente(apellido: "Giménez", nombre: "Susana", email: "sgimenez@gmail.com", telefono: "11 4512-1765", razonSocial: "Susana Giménez", direccion: "Carlos Pellegrini 301", latitud: -34.604896d, longitud: -58.38082d, agendaCliente: agenda, validador: "60567712-ca75-4fed-9c19-6eded83fe6b9")
		assert cliente.save(failOnError: true, flush: true, insert: true)
		cliente.errors = null

		agenda = [Utils.JUEVES, Utils.VIERNES, Utils.SABADO]
		cliente = new Cliente(apellido: "Páez", nombre: "Fito", email: "fitopaez@gmail.com", telefono: "11 4512-1787", razonSocial: "Fito Páez", direccion: "Av. Corrientes 1197", latitud: -34.603723d, longitud: -58.383567d, agendaCliente: agenda, validador: "36e8a390-9461-4a06-92ff-451aa0a35674")
		assert cliente.save(failOnError: true, flush: true, insert: true)
		cliente.errors = null

		println "[OT-LOG] Finalizada carga de $Cliente.count clientes en la Base de Datos"
	}

	private void precargarProductos() {
		// Inserción de Categoría
		def cat1 = new Categoria(nombre: "Ropa Deportiva", descripcion: "Indumentaria para los deportes")
		assert cat1.save(failOnError: true, flush: true, insert: true)

		def cat2 = new Categoria(nombre: "Ropa Hombre", descripcion: "Camisas y Pantalones")
		assert cat2.save(failOnError: true, flush: true, insert: true)

		def cat3 = new Categoria(nombre: "Ropa Mujer", descripcion: "Vestidos y Faldas")
		assert cat3.save(failOnError: true, flush: true, insert: true)

		def cat4 = new Categoria(nombre: "Accesorios", descripcion: "Vestimenta Complementaria")
		assert cat4.save(failOnError: true, flush: true, insert: true)

		def cat5 = new Categoria(nombre: "Calzado", descripcion: "Zapatos y Zapatillas")
		assert cat5.save(failOnError: true, flush: true, insert: true)

		println "[OT-LOG] Finalizada carga de $Categoria.count categorías en la Base de Datos"

		// Inserción de Marca y Producto
		def marca = null
		def producto = null
		def imagen = null

		imagen = new Imagen(originalFilename: "t01.jpg", thumbnailFilename: "tm01-thumbnail.png", newFilename: "tm01.jpg", size: 512)
		marca = new Marca(nombre: "Adidas", imagen: imagen)
		assert marca.save(failOnError: true, flush: true, insert: true)
		imagen.errors = null
		marca.errors = null

		imagen = new Imagen(originalFilename: "01.jpg", thumbnailFilename: "prod01-thumbnail.png", newFilename: "prod01.jpg", size: 512)
		producto = new Producto(nombre: "Mochila Deportiva Negra", caracteristicas: "Mochila de alta capacidad y costura reforzada", marca: marca, precio: 719.00, stock: 40, imagen: imagen).addToCategorias(cat4)
		assert producto.save(failOnError: true, flush: true, insert: true)
		imagen.errors = null
		producto.errors = null

		imagen = new Imagen(originalFilename: "02.jpg", thumbnailFilename: "prod02-thumbnail.png", newFilename: "prod02.jpg", size: 512)
		producto = new Producto(nombre: "Bolso de la Seleccion", caracteristicas: "Diseño y estilo de la Selección Argentina", marca: marca, precio: 599.00, stock: 22, imagen: imagen).addToCategorias(cat4)
		assert producto.save(failOnError: true, flush: true, insert: true)
		imagen.errors = null
		producto.errors = null

		imagen = new Imagen(originalFilename: "t02.jpg", thumbnailFilename: "tm02-thumbnail.png", newFilename: "tm02.jpg", size: 512)
		marca = new Marca(nombre: "A.Y. NOT DEAD", imagen: imagen)
		assert marca.save(failOnError: true, flush: true, insert: true)
		imagen.errors = null
		marca.errors = null

		imagen = new Imagen(originalFilename: "03.jpg", thumbnailFilename: "prod03-thumbnail.png", newFilename: "prod03.jpg", size: 512)
		producto = new Producto(nombre: "Vestido Print", caracteristicas: "Temporada otoño-invierno 2016", marca: marca, precio: 1645.00, stock: 8, imagen: imagen).addToCategorias(cat3)
		assert producto.save(failOnError: true, flush: true, insert: true)
		imagen.errors = null
		producto.errors = null

		imagen = new Imagen(originalFilename: "t03.jpg", thumbnailFilename: "tm03-thumbnail.png", newFilename: "tm03.jpg", size: 512)
		marca = new Marca(nombre: "Akiabara", imagen: imagen)
		assert marca.save(failOnError: true, flush: true, insert: true)
		imagen.errors = null
		marca.errors = null

		imagen = new Imagen(originalFilename: "04.jpg", thumbnailFilename: "prod04-thumbnail.png", newFilename: "prod04.jpg", size: 512)
		producto = new Producto(nombre: "Vestido Basico", caracteristicas: "Diseño sencillo", marca: marca, precio: 999.99, stock: 16, imagen: imagen).addToCategorias(cat3)
		assert producto.save(failOnError: true, flush: true, insert: true)
		producto.errors = null

		imagen = new Imagen(originalFilename: "05.jpg", thumbnailFilename: "prod05-thumbnail.png", newFilename: "prod05.jpg", size: 512)
		producto = new Producto(nombre: "Vestido Mini Negro", caracteristicas: "Fuera de temporada", marca: marca, precio: 449.99, stock: 5, imagen: imagen).addToCategorias(cat3)
		assert producto.save(failOnError: true, flush: true, insert: true)
		producto.errors = null

		imagen = new Imagen(originalFilename: "t04.jpg", thumbnailFilename: "tm04-thumbnail.png", newFilename: "tm04.jpg", size: 512)
		marca = new Marca(nombre: "Nike", imagen: imagen)
		assert marca.save(failOnError: true, flush: true, insert: true)
		marca.errors = null

		imagen = new Imagen(originalFilename: "06.jpg", thumbnailFilename: "prod06-thumbnail.png", newFilename: "prod06.jpg", size: 512)
		producto = new Producto(nombre: "Zapatillas de Correr", caracteristicas: "Modelo Free Run +3", marca: marca, precio: 1749.00, stock: 45, imagen: imagen).addToCategorias(cat5)
		assert producto.save(failOnError: true, flush: true, insert: true)
		imagen.errors = null
		producto.errors = null

		imagen = new Imagen(originalFilename: "t05.jpg", thumbnailFilename: "tm05-thumbnail.png", newFilename: "tm05.jpg", size: 512)
		marca = new Marca(nombre: "Zara", imagen: imagen)
		assert marca.save(failOnError: true, flush: true, insert: true)
		imagen.errors = null
		marca.errors = null

		imagen = new Imagen(originalFilename: "07.jpg", thumbnailFilename: "prod07-thumbnail.png", newFilename: "prod07.jpg", size: 512)
		producto = new Producto(nombre: "Remera Zebra", caracteristicas: "Colección Animal", marca: marca, precio: 349.99, stock: 35, imagen: imagen).addToCategorias(cat3)
		assert producto.save(failOnError: true, flush: true, insert: true)
		imagen.errors = null
		producto.errors = null

		imagen = new Imagen(originalFilename: "08.jpg", thumbnailFilename: "prod08-thumbnail.png", newFilename: "prod08.jpg", size: 512)
		producto = new Producto(nombre: "Remera Leopardo", caracteristicas: "Colección Animal", marca: marca, precio: 399.99, stock: 35, imagen: imagen).addToCategorias(cat3)
		assert producto.save(failOnError: true, flush: true, insert: true)
		imagen.errors = null
		producto.errors = null

		imagen = new Imagen(originalFilename: "t06.jpg", thumbnailFilename: "tm06-thumbnail.png", newFilename: "tm06.jpg", size: 512)
		marca = new Marca(nombre: "Reebok", imagen: imagen)
		assert marca.save(failOnError: true, flush: true, insert: true)
		imagen.errors = null
		marca.errors = null

		imagen = new Imagen(originalFilename: "09.jpg", thumbnailFilename: "prod09-thumbnail.png", newFilename: "prod09.jpg", size: 512)
		producto = new Producto(nombre: "Zapatillas Outdoor", caracteristicas: "Modelo Reebok The Stone", marca: marca, precio: 1399.00, stock: 7, imagen: imagen).addToCategorias(cat5)
		assert producto.save(failOnError: true, flush: true, insert: true)
		imagen.errors = null
		producto.errors = null

		imagen = new Imagen(originalFilename: "10.jpg", thumbnailFilename: "prod10-thumbnail.png", newFilename: "prod10.jpg", size: 512)
		producto = new Producto(nombre: "Conjunto Delta Suit", caracteristicas: "Varios colores disponibles", marca: marca, precio: 1319.00, stock: 9, imagen: imagen).addToCategorias(cat1)
		assert producto.save(failOnError: true, flush: true, insert: true)
		imagen.errors = null
		producto.errors = null

		imagen = new Imagen(originalFilename: "11.jpg", thumbnailFilename: "prod11-thumbnail.png", newFilename: "prod11.jpg", size: 512)
		producto = new Producto(nombre: "Chaqueta Alpha Suit", caracteristicas: "Alta durabilidad outdoor", marca: marca, precio: 879.00, stock: 4, imagen: imagen).addToCategorias(cat1)
		assert producto.save(failOnError: true, flush: true, insert: true)
		imagen.errors = null
		producto.errors = null

		imagen = new Imagen(originalFilename: "t07.jpg", thumbnailFilename: "tm07-thumbnail.png", newFilename: "tm07.jpg", size: 512)
		marca = new Marca(nombre: "Converse", imagen: imagen)
		assert marca.save(failOnError: true, flush: true, insert: true)
		imagen.errors = null
		marca.errors = null

		imagen = new Imagen(originalFilename: "12.jpg", thumbnailFilename: "prod12-thumbnail.png", newFilename: "prod12.jpg", size: 512)
		producto = new Producto(nombre: "Zapatillas Chuck Taylor All Star", caracteristicas: "Modelo clásico, varias tallas", marca: marca, precio: 999.00, stock: 21, imagen: imagen).addToCategorias(cat5)
		assert producto.save(failOnError: true, flush: true, insert: true)
		imagen.errors = null
		producto.errors = null

		imagen = new Imagen(originalFilename: "13.jpg", thumbnailFilename: "prod13-thumbnail.png", newFilename: "prod13.jpg", size: 512)
		producto = new Producto(nombre: "Remera Mono Patch Ringer Tee", caracteristicas: "Solo negro, varias tallas", marca: marca, precio: 699.00, stock: 7, imagen: imagen).addToCategorias(cat2)
		assert producto.save(failOnError: true, flush: true, insert: true)
		imagen.errors = null
		producto.errors = null

		imagen = new Imagen(originalFilename: "14.jpg", thumbnailFilename: "prod14-thumbnail.png", newFilename: "prod14.jpg", size: 512)
		producto = new Producto(nombre: "Jeans Jean Harlow Skinny", caracteristicas: "Solo tallas XS, S, M", marca: marca, precio: 1199.00, stock: 12, imagen: imagen).addToCategorias(cat3)
		assert producto.save(failOnError: true, flush: true, insert: true)
		imagen.errors = null
		producto.errors = null

		imagen = new Imagen(originalFilename: "t08.jpg", thumbnailFilename: "tm08-thumbnail.png", newFilename: "tm08.jpg", size: 512)
		marca = new Marca(nombre: "Wrangler", imagen: imagen)
		assert marca.save(failOnError: true, flush: true, insert: true)
		imagen.errors = null
		marca.errors = null

		imagen = new Imagen(originalFilename: "15.jpg", thumbnailFilename: "prod15-thumbnail.png", newFilename: "prod15.jpg", size: 512)
		producto = new Producto(nombre: "Falda Nelly Basic Skirt", caracteristicas: "Modelo clásico, varias tallas", marca: marca, precio: 849.00, stock: 16, imagen: imagen).addToCategorias(cat3)
		assert producto.save(failOnError: true, flush: true, insert: true)
		imagen.errors = null
		producto.errors = null

		// Imagen de relleno
		def imagenRelleno = new Imagen(originalFilename: "${ImagenController.nombreImagenRelleno}", thumbnailFilename: "${ImagenController.nombreMiniaturaRelleno}", newFilename: "${ImagenController.nombreImagenRelleno}", size: 512)
		assert imagenRelleno.save(failOnError: true, flush: true, insert: true)

		println "[OT-LOG] Finalizada carga de $Marca.count marcas en la Base de Datos"
		println "[OT-LOG] Finalizada carga de $Producto.count productos en la Base de Datos"
		println "[OT-LOG] Finalizada carga de $Imagen.count imágenes en la Base de Datos"
	}

	private void precargarPedidos() {
		def pedido, vendedor
		def ped1, ped2, ped3, ped4

		for (int i=1; i<=50; i++) {
			pedido = Utils.crearPedidoAleatorio()
			pedido.actualizarTotal()
			assert pedido.save(failOnError:true, flush:true, insert: true)
			pedido.errors = null
		}

		println "[OT-LOG] Finalizada carga de $Pedido.count pedidos en la Base de Datos"
	}
	
	private void enlazarClientes() {
		Vendedor v
		Cliente cl1, cl2, cl3

		v = Vendedor.findByUsername('dgacitua')
		cl1 = Cliente.findById(1)
		cl2 = Cliente.findById(3)
		cl3 = Cliente.findById(5)
		v.addToClientes(cl1).addToClientes(cl2).addToClientes(cl3)

		v = Vendedor.findByUsername('bbeltran')
		cl1 = Cliente.findById(2)
		cl2 = Cliente.findById(4)
		cl3 = Cliente.findById(6)
		v.addToClientes(cl1).addToClientes(cl2).addToClientes(cl3)

		v = Vendedor.findByUsername('poddo')
		cl1 = Cliente.findById(7)
		cl2 = Cliente.findById(8)
		cl3 = Cliente.findById(9)
		v.addToClientes(cl1).addToClientes(cl2).addToClientes(cl3)

		v = Vendedor.findByUsername('mroitman')
		cl1 = Cliente.findById(10)
		cl2 = Cliente.findById(13)
		cl3 = Cliente.findById(14)
		v.addToClientes(cl1).addToClientes(cl2).addToClientes(cl3)

		v = Vendedor.findByUsername('vendedor')
		cl1 = Cliente.findById(11)
		cl2 = Cliente.findById(12)
		cl3 = Cliente.findById(15)
		v.addToClientes(cl1).addToClientes(cl2).addToClientes(cl3)

		Agenda.list().each {
			it.poblarAgendaClientes()
		}
	}

	private void setTimeZone() {
		String tz = "UTC-3"
		TimeZone.setDefault(TimeZone.getTimeZone(tz))
		println "[OT-LOG] Configurando zona horaria del servidor a $tz"
		println "[OT-LOG] La hora actual es ${new Date()}"
	}
}
