import java.awt.Desktop.Action;

class UrlMappings {

	static mappings = {
		// TODO Declarar nuevos Endpoints de esta forma en el UrlMapping
		"/cliente"(resources:"cliente")
		"/cliente/buscar/$id"(controller:"cliente", action:"search")
		
		"/producto"(resources:"producto")
		"/producto/buscar/$id"(controller:"producto", action:"search")
		"/producto/buscar-categoria/$id"(controller:"producto", action:"searchInCategoria")
		"/producto/buscar-marca/$id"(controller:"producto", action:"searchInMarca")

		"/marca"(resources:"marca")
		"/marca/buscar/$id"(controller:"marca", action:"search")

		"/categoria"(resources:"categoria")
		"/categoria/buscar/$id"(controller:"categoria", action:"search")
		"/categoria/buscar-producto/$id"(controller:"categoria", action:"searchInProducto")

		"/pedido"(resources:"pedido")
		"/pedido/buscar-cliente/$id"(controller:"pedido", action:"searchByCliente")
		"/pedido/buscar-estado/$id"(controller:"pedido", action:"searchByEstado")

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
