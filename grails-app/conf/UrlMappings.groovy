class UrlMappings {

	static mappings = {
		// TODO Declarar nuevos Endpoints de esta forma en el UrlMapping
		"/cliente"(resources:"cliente", parseRequest:true)
		"/cliente/buscar/$id"(controller:"cliente", action:"search")
		"/cliente/filtro"(controller:"cliente", action:"filtroAdmin")
		"/cliente/lista-corta"(controller:"cliente", action:"listaCorta")
		"/cliente/reenviar/$id"(controller:"cliente", action:"reenviarQR")
		
		"/producto"(resources:"producto", parseRequest:true)
		"/producto/filtro"(controller:"producto", action:"filtroAdmin")
		"/producto/lista-corta"(controller:"producto", action:"listaCorta")
		"/producto/buscar/$term"(controller:"producto", action:"search")
		"/producto/buscar-categoria/$id"(controller:"producto", action:"searchInCategoria")
		"/producto/buscar-marca/$id"(controller:"producto", action:"searchInMarca")

		"/marca"(resources:"marca", parseRequest:true)
		"/marca/filtro"(controller:"marca", action:"filtroAdmin")
		"/marca/buscar/$term"(controller:"marca", action:"search")

		"/categoria"(resources:"categoria", parseRequest:true)
		"/categoria/filtro"(controller:"categoria", action:"filtroAdmin")
		"/categoria/buscar/$term"(controller:"categoria", action:"search")
		"/categoria/buscar-producto/$id"(controller:"categoria", action:"searchInProducto")

		"/pedido"(resources:"pedido", parseRequest:true)
		"/pedido/filtro"(controller:"pedido", action:"filtroAdmin")
		"/pedido/descontar/$id"(controller:"pedido", action:"descontarStock")
		"/pedido/buscar-cliente/$id"(controller:"pedido", action:"searchByCliente")
		"/pedido/buscar-estado/$id"(controller:"pedido", action:"searchByEstado")

		"/imagen/ver/$id"(controller:"imagen", action:"picture")
		"/imagen/miniatura/$id"(controller:"imagen", action:"thumbnail")
		"/imagen/listar"(controller:"marca", action:"listarImagenes")
		"/imagen/subir"(controller:"imagen", action:"upload")
		"/imagen/borrar/$id"(controller:"imagen", action:"delete")

		"/agenda"(resources:"agenda", parseRequest:true)
		"/agenda/dia/$codigoDia"(controller:"agenda", action:"agendaDia")
		"/agenda/semana"(controller:"agenda", action:"agendaSemana")
		"/agenda/admin-dia/$idVendedor/$codigoDia"(controller:"agenda", action:"agendaAdminDia")
		"/agenda/admin-semana/$idVendedor"(controller:"agenda", action:"agendaAdminSemana")
		"/agenda/admin-editar"(controller:"agenda", action:"agendaEditar")

		"/comentario"(resources:"comentario")

		"/visita"(resources:"visita")

		"/vendedor"(resources:"vendedor", parseRequest:true)
		"/vendedor/$id"(controller: "vendedor", action: "show")
		"/vendedor/filtro"(controller:"vendedor", action:"filtroAdmin")
		"/vendedor/lista-corta"(controller:"vendedor", action:"listaCorta")
		"/vendedor/push-token"(controller: "vendedor", action: "refreshPushToken")
		"/vendedor/transferir"(controller:"vendedor", action:"transferirCliente")
		"/vendedor/transferir-clientes"(controller:"vendedor", action:"transferirClientes")

		"/persona"(controller: "persona")

		"/descuento"(resources:"descuento", parseRequest:true)
		"/descuento/filtro"(controller: "descuento", action: "filtroAdmin")

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
