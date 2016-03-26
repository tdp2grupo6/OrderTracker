import java.awt.Desktop.Action;

class UrlMappings {

	static mappings = {
		// TODO Declarar nuevos Endpoints de esta forma en el UrlMapping
		"/cliente"(resources:"cliente")
		"/marca"(resources:"marca")
		"/producto"(resources:"producto")
		"/categoria"(resources:"categoria")
		
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
