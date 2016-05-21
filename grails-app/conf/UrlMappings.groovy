class UrlMappings {

	static mappings = {
		// TODO Declarar nuevos Endpoints de esta forma en el UrlMapping
		"/cliente"(resources:"cliente")
		"/cliente/buscar/$id"(controller:"cliente", action:"search")
		"/cliente/filtro"(controller:"cliente", action:"filtroAdmin")
		"/cliente/lista-corta"(controller:"cliente", action:"listaCorta")
		
		"/producto"(resources:"producto")
		"/producto/buscar/$term"(controller:"producto", action:"search")
		"/producto/buscar-categoria/$id"(controller:"producto", action:"searchInCategoria")
		"/producto/buscar-marca/$id"(controller:"producto", action:"searchInMarca")

		"/marca"(resources:"marca")
		"/marca/buscar/$term"(controller:"marca", action:"search")

		"/categoria"(resources:"categoria")
		"/categoria/buscar/$term"(controller:"categoria", action:"search")
		"/categoria/buscar-producto/$id"(controller:"categoria", action:"searchInProducto")

		"/pedido"(resources:"pedido")
		"/pedido/filtro"(controller:"pedido", action:"filtroAdmin")
		"/pedido/buscar-cliente/$id"(controller:"pedido", action:"searchByCliente")
		"/pedido/buscar-estado/$id"(controller:"pedido", action:"searchByEstado")

		"/imagen/ver/$id"(controller:"imagen", action:"picture")
		"/imagen/miniatura/$id"(controller:"imagen", action:"thumbnail")
		"/imagen/subir"(controller:"imagen", action:"upload")
		"/imagen/borrar/$id"(controller:"imagen", action:"delete")

		"/agenda"(resources:"agenda")
		"/agenda/dia/$codigoDia"(controller:"agenda", action:"agendaDia")
		"/agenda/semana"(controller:"agenda", action:"agendaSemana")
		"/agenda/admin-dia/$idVendedor/$codigoDia"(controller:"agenda", action:"agendaAdminDia")
		"/agenda/admin-semana/$idVendedor"(controller:"agenda", action:"agendaAdminSemana")
		"/agenda/admin-editar"(controller:"agenda", action:"agendaEditar")

		"/comentario"(resources:"comentario")

		"/visita"(resources:"visita")

		"/vendedor"(resources:"vendedor")
		"/vendedor/$id"(controller: "vendedor", action: "refreshPushId")
		"/vendedor/push-id"(controller: "vendedor", action: "refreshPushId")

		"/persona"(controller: "persona")

		"/login-test"(controller: "secureTest")
		"/login-test/usuario"(controller: "secureTest", action: "datosUsuario")
		"/login-test/lista-vendedores"(controller: "secureTest", action: "listaVendedores")
		"/login-test/lista-admins"(controller: "secureTest", action: "listaAdmins")
		
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
