package ordertracker

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.transaction.Transactional
import ordertracker.Filtros.FiltroCliente
import ordertracker.Filtros.FiltroResultado
import ordertracker.Perfiles.Vendedor
import ordertracker.Servicios.Utils

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class ClienteController {
    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", show: "GET", search: "GET",
                            filtroAdmin: "POST", listaCorta: "GET", reenviarQR: "GET"]

    def springSecurityService

    def index(Integer max) {
        if (SpringSecurityUtils.ifAllGranted(Utils.VENDEDOR)) {
            Vendedor user = springSecurityService.currentUser
            List<Cliente> lista = user.clientes.toList()
            respond lista, [status: OK]
        }
        else {
            //params.max = Math.min(max ?: 10, 100)
            def result = Cliente.list(params)
            respond result, [status: OK]
        }
    }
	
	def show(Cliente cl) {
		if (!cl) {
            render status: NOT_FOUND
		}
		else {
			respond cl, [status: OK]
		}		
	}
	
	def search(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		def c = Cliente.createCriteria()
		def result = c.list(params) {
			or {
				ilike("nombre", "${params.id}%")
				ilike("apellido", "${params.id}%")
				ilike("razonSocial", "${params.id}%")
			}
		}
		respond result, model:[status: OK, totalResultados: result.totalCount]
	}

    def filtroAdmin(FiltroCliente fc) {
        int offset = fc.primerValor()
        int maxPage = Utils.RESULTADOS_POR_PAGINA
        def prod = Cliente.createCriteria()
        def result = prod.list (max: maxPage, offset: offset) {
            and {
                if (fc.idCliente) {
                    eq("id", fc.idCliente)
                }
                if (fc.nombre) {
                    ilike("nombre", "${fc.nombre}%")
                }
                if (fc.apellido) {
                    ilike("apellido", "${fc.apellido}%")
                }
                if (fc.email) {
                    ilike("email", "${fc.email}%")
                }
                if (fc.direccion) {
                    ilike("direccion", "${fc.direccion}%")
                }
            }
        }
        int pagina = fc.pagina? fc.pagina : 1
        result.sort { it.id }
        FiltroResultado respuesta = new FiltroResultado(pagina, result.totalCount, result as List)
        respond respuesta, model:[status: OK, totalResultados: result.totalCount]
    }

    def listaCorta() {
        if (SpringSecurityUtils.ifAllGranted(Utils.ADMIN)) {
            def result = Cliente.list().collect {
                [
                        id: it.id,
                        nombre: it.nombre,
                        apellido: it.apellido,
                        email: it.email,
                        direccion: it.direccion
                ]
            }
            respond result, [status: OK]
        }
        else {
            // TODO eliminar
            def result = Cliente.list().collect {
                [
                        id: it.id,
                        nombre: it.nombre,
                        apellido: it.apellido,
                        email: it.email,
                        direccion: it.direccion
                ]
            }
            respond result, [status: OK]
        }
    }

    def reenviarQR(Cliente cl) {
        if (!cl) {
            render status: NOT_FOUND
        }
        else {
            sendMail {
                multipart true
                to "${cl.email}"
                subject "Recordatorio como Cliente en Order Tracker"
                inline 'qrcode', 'image/png', Utils.generarQR(cl.validador)
                html g.render (template:"reenviarClienteTemplate", model:[nombreCompleto:"${cl.nombre} ${cl.apellido}", validador:"${cl.validador}"])
            }

            render status: OK
        }
    }

    @Transactional
    def save(Cliente clienteInstance) {
        if (clienteInstance == null) {
            render status: NOT_FOUND
            return
        }

        clienteInstance.validate()
        if (clienteInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        clienteInstance.save flush:true

        respond clienteInstance, [status: OK]

        sendMail {
            multipart true
            to "${clienteInstance.email}"
            subject "Su registro como Cliente en Order Tracker"
            inline 'qrcode', 'image/png', Utils.generarQR(clienteInstance.validador)
            html g.render (template:"nuevoClienteTemplate", model:[nombreCompleto:"${clienteInstance.nombre} ${clienteInstance.apellido}", validador:"${clienteInstance.validador}"])
        }
    }

    @Transactional
    def update(Cliente clienteInstance) {
        if (clienteInstance == null) {
            render status: NOT_FOUND
            return
        }

        clienteInstance.validate()
        if (clienteInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        clienteInstance.save flush:true
        respond clienteInstance, [status: OK]
    }

    @Transactional
    def delete(Cliente clienteInstance) {
        if (clienteInstance == null) {
            render status: NOT_FOUND
            return
        }

        clienteInstance.eliminarInstancias()

        clienteInstance.delete flush:true
        render status: OK
    }
}
