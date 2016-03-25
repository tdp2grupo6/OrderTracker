import java.awt.Desktop.Action;

class UrlMappings {

	static mappings = {
		// Declarar nuevos Endpoints de esta forma en el UrlMapping
		"/cliente"(resources:"cliente") {
			"/search/$id?"(resources:"cliente", action:"search")
		}
		"/marca"(resources:"marca") {
			"/search/$id?"(resources:"marca", action:"search")
		}
			
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
		
        "/"(view:"/index")
        "500"(view:'/error')
	}
}
