import java.awt.Desktop.Action;

class UrlMappings {

	static mappings = {
		/*		
		"/cliente"(parseRequest: true) {
			action = [GET: "index", POST: "save"]
		}
		"/cliente/$id"(parseRequest: true) {
			action = [GET: "show", PUT: "update", DELETE: "delete"]
		}
		*/
		
		"/cliente"(resources:"cliente") {
			"/search/$id?"(action:"search")
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
