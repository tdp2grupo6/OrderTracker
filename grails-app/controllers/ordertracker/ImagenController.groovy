package ordertracker

import grails.converters.JSON
import grails.util.Environment
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.imgscalr.Scalr
import org.springframework.http.HttpStatus
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest
import javax.imageio.ImageIO
import java.awt.image.BufferedImage

import static org.springframework.http.HttpStatus.*

class ImagenController {
	static final String nombreImagenRelleno = "sinfoto.jpg"
	static final String nombreMiniaturaRelleno = "sinfoto-thumbnail.png"

	FileUploadService fileUploadService

	def upload() {
		switch(request.method){
			case "GET":
				def results = []
				Imagen.findAll().each { Imagen picture ->
					results << [
							name: picture.originalFilename,
							size: picture.fileSize,
							url: createLink(controller:'imagen', action:'picture', id: picture.id),
							thumbnail_url: createLink(controller:'imagen', action:'thumbnail', id: picture.id),
							delete_url: createLink(controller:'imagen', action:'delete', id: picture.id),
							delete_type: "DELETE"
					]
				}
				render results as JSON
				break;
			case "POST":
				def results = []
				if (request instanceof MultipartHttpServletRequest){
					for(filename in request.getFileNames()){
						MultipartFile file = request.getFile(filename)

						String newFilenameBase = UUID.randomUUID().toString()
						String originalFileExtension = file.originalFilename.substring(file.originalFilename.lastIndexOf("."))
						String newFilename = newFilenameBase + originalFileExtension
						String storageDirectory = obtenerRuta("uploads")?:'/tmp'
						println "[OT-LOG] Subiendo imagen $newFilename a $storageDirectory"

						File newFile = new File("$storageDirectory/$newFilename")
						file.transferTo(newFile)

						BufferedImage thumbnail = Scalr.resize(ImageIO.read(newFile), 250)
						String thumbnailFilename = newFilenameBase + '-thumbnail.png'
						File thumbnailFile = new File("$storageDirectory/$thumbnailFilename")
						ImageIO.write(thumbnail, 'png', thumbnailFile)

						Imagen picture = new Imagen(
								originalFilename: file.originalFilename,
								thumbnailFilename: thumbnailFilename,
								newFilename: newFilename,
								fileSize: file.size
						).save()

						results << [
								id: picture.id,
								name: picture.originalFilename,
								size: picture.fileSize,
								url: createLink(controller:'imagen', action:'picture', id: picture.id),
								thumbnail_url: createLink(controller:'imagen', action:'thumbnail', id: picture.id),
								delete_url: createLink(controller:'imagen', action:'delete', id: picture.id),
								delete_type: "DELETE"
						]
					}
				}

				render results as JSON
				break;
			default: render status: HttpStatus.METHOD_NOT_ALLOWED.value()
		}
	}

	def upload2() {
		MultipartFile image = request.getFile('file')
		if (!image.isEmpty()) {
			//userInstance.avatar = fileUploadService.uploadFile(avatarImage, "logo.png", "~/Desktop/upload")
			String storageDirectory = obtenerRuta("uploads")?:'/tmp'
			String newFilenameBase = UUID.randomUUID().toString()
			String originalFileExtension = image.originalFilename.substring(image.originalFilename.lastIndexOf("."))
			String newFilename = newFilenameBase + originalFileExtension
			println "[OT-LOG] Subiendo imagen $newFilename a $storageDirectory"

			String original = fileUploadService.uploadFile(image, newFilename, storageDirectory)
			File newFile = new File("$storageDirectory/$newFilename")
			image.transferTo(newFile)

			BufferedImage thumbnail = Scalr.resize(ImageIO.read(newFile), 250)
			String thumbnailFilename = newFilenameBase + '-thumbnail.png'
			File thumbnailFile = new File("$storageDirectory/$thumbnailFilename")
			ImageIO.write(thumbnail, 'png', thumbnailFile)


			Imagen picture = new Imagen(
					originalFilename: image.originalFilename,
					thumbnailFilename: thumbnailFilename,
					newFilename: newFilename,
					fileSize: image.size
			).save(flush: true)

			def results = [
					id: picture.id,
					name: picture.originalFilename,
					size: picture.fileSize,
					url: createLink(controller:'imagen', action:'picture', id: picture.id),
					thumbnail_url: createLink(controller:'imagen', action:'thumbnail', id: picture.id),
					delete_url: createLink(controller:'imagen', action:'delete', id: picture.id),
					delete_type: "DELETE"
			]

			response.status = 200
			render results as JSON
		}
		else {
			render status: NOT_FOUND
		}
	}

	def picture() {
		def pic = Imagen.get(params.id)

		if (pic) {
			def picFile = new File("${obtenerRuta("uploads")?:'/tmp'}/${pic.newFilename}")

			if (picFile.exists()) {
				File picture = new File("${obtenerRuta("uploads")?:'/tmp'}/${pic.newFilename}")
				response.contentType = 'image/jpeg'
				response.outputStream << new FileInputStream(picture)
				response.outputStream.flush()
			}
		}
		else {
			mostrarImagenRelleno()
		}
	}

	def thumbnail() {
		def pic = Imagen.get(params.id)

		if (pic) {
			def picFile = new File("${obtenerRuta("uploads")?:'/tmp'}/${pic.thumbnailFilename}")

			if (picFile.exists()) {
				File picture = new File("${obtenerRuta("uploads")?:'/tmp'}/${pic.thumbnailFilename}")
				response.contentType = 'image/png'
				response.outputStream << new FileInputStream(picture)
				response.outputStream.flush()
			}
		}
		else {
			mostrarMiniaturaRelleno()
		}
	}

	def delete() {
		def pic = Imagen.get(params.id)

		if (pic) {
			File picFile = new File("${obtenerRuta("uploads")?:'/tmp'}/${pic.newFilename}")
			picFile.delete()
			File thumbnailFile = new File("${obtenerRuta("uploads")?:'/tmp'}/${pic.thumbnailFilename}")
			thumbnailFile.delete()
			pic.delete()

			def result = [success: true]
			render result as JSON
		}
		else {
			def result = [success: false]
			render result as JSON
		}
	}

	def mostrarImagenRelleno() {
		def picFile = new File("${obtenerRuta("uploads")?:'/tmp'}/${nombreImagenRelleno}")

		if (picFile.exists()) {
			File picture = new File("${obtenerRuta("uploads")?:'/tmp'}/${nombreImagenRelleno}")
			response.contentType = 'image/jpeg'
			response.outputStream << new FileInputStream(picture)
			response.outputStream.flush()
		}
	}

	def mostrarMiniaturaRelleno() {
		def picFile = new File("${obtenerRuta("uploads")?:'/tmp'}/${nombreMiniaturaRelleno}")

		if (picFile.exists()) {
			File picture = new File("${obtenerRuta("uploads")?:'/tmp'}/${nombreMiniaturaRelleno}")
			response.contentType = 'image/png'
			response.outputStream << new FileInputStream(picture)
			response.outputStream.flush()
		}
	}

	def obtenerRuta(String rutaRelativa) {
		String ruta = "/tmp"

		Environment.executeForCurrentEnvironment {
			development {
				ruta = ServletContextHolder.servletContext.getRealPath(rutaRelativa)
			}
			openshift {
				def dir = System.getenv("OPENSHIFT_DATA_DIR")
				ruta = dir + rutaRelativa
			}
		}

		// Crear carpeta de subida si no existe
		def rutaFinal = new File(ruta)
		if (!rutaFinal.exists()) {
			print "[OT-LOG] CREANDO DIRECTORIO ${ruta}: "
			if (rutaFinal.mkdirs()) {
				println "ÉXITO!"
			} else {
				println "FALLÓ"
			}
		}

		return rutaFinal
	}
}