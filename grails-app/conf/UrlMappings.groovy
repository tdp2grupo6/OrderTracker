import java.awt.Desktop.Action;

class UrlMappings {

	static mappings = {
		// TODO Declarar nuevos Endpoints de esta forma en el UrlMapping
		"/cliente"(resources:"cliente")
		"/cliente/search/$id?"(controller:"cliente", action:"search")
		
		"/producto"(resources:"producto")
		"/producto/search/$id?"(controller:"producto", action:"search")
		
		"/marca"(resources:"marca")
		"/marca/search/$id?"(controller:"marca", action:"search")
		
		"/categoria"(resources:"categoria")
		"/categoria/search/$id?"(controller:"categoria", action:"search")
		
		"/imagen/ver/$id"(controller:"imagen", action:"picture")
		"/imagen/miniatura/$id"(controller:"imagen", action:"thumbnail")
		"/imagen/subir"(controller:"imagen", action:"upload")
		"/imagen/borrar/$id"(controller:"imagen", action:"delete")		
		
		// UrlMappings por defecto
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
		
        "/"(view:"/index")
        "500"(view:'/error')
	}
}
