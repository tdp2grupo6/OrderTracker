package ordertracker

import grails.transaction.Transactional
import org.springframework.web.multipart.MultipartFile

@Transactional
class SubirArchivoService {
	def String uploadFile(MultipartFile file, String name, String destinationDirectory) {

		def servletContext = ServletContextHolder.servletContext
		def storagePath = servletContext.getRealPath(destinationDirectory)
		
		// Create storage path directory if it does not exist
		def storagePathDirectory = new File(storagePath)
		if (!storagePathDirectory.exists()) {
			print "CREANDO DIRECTORIO ${storagePath}: "
			if (storagePathDirectory.mkdirs()) {
				println "EXITO!"
			} else {
				println "ERROR"
			}
		}
		
		// Guardar Archivo
		if (!file.isEmpty()) {
			file.transferTo(new File("${storagePath}/${name}"))
			println "Archivo guardado: ${storagePath}/${name}"
			return "${storagePath}/${name}"
	
		} else {
			println "El archivo ${file.inspect()} estaba vacío!"
			return null
		}
	}
}
