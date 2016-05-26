package ordertracker

import grails.plugin.springsecurity.SpringSecurityUtils
import ordertracker.Estados.EstadoProducto
import ordertracker.Filtros.FiltroProducto
import ordertracker.Filtros.FiltroResultado

import static org.springframework.http.HttpStatus.*
import org.codehaus.groovy.runtime.StringGroovyMethods
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ProductoController {
    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", show: "GET", search: "GET",
                             searchInCategoria: "GET", searchInMarca: "GET", filtroAdmin: "POST"]

    def mensajePushService
	
	def index(Integer max) {
		//params.max = Math.min(max ?: 10, 100)
		def result = Producto.list(params)
		respond result, [status: OK]
	}
	
	def show(Producto prod) {
		if (!prod) {
            render status: NOT_FOUND
		}
		else {
			respond prod, [status: OK]
		}
	}
	
	def search(Integer max) {
		//params.max = Math.min(max ?: 10, 100)
		def prod = Producto.createCriteria()
		def result = prod.list(params) {
			ilike("nombre", "$params.term%")
		}
		respond result, model:[status: OK, totalResultados: result.count]
	}

    def searchInCategoria(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        if (StringGroovyMethods.isLong(params.id)) {
            long idFiltro = StringGroovyMethods.toLong(params.id)
            def res = Producto.withCriteria {
                    categorias {
                        idEq(idFiltro)
                    }
                }
            respond res, model: [status: OK, totalResultados: res.count]
        }
        else {
            render status: NOT_ACCEPTABLE
        }
    }

    def searchInMarca(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        if (StringGroovyMethods.isLong(params.id)) {
            long idFiltro = StringGroovyMethods.toLong(params.id)
            def marSet = [Marca.findById(idFiltro)] as Set
            def result = Producto.findAllByMarca(marSet)
            respond result, model:[status: OK, totalResultados: result.count]
        }
        else {
            render status: NOT_ACCEPTABLE
        }
    }

    def filtroAdmin(FiltroProducto fp) {
        int offset = fp.primerValor()
        int maxPage = Utils.RESULTADOS_POR_PAGINA
        def prod = Producto.createCriteria()
        def result = prod.list (max: maxPage, offset: offset) {
            and {
                if (fp.estado) {
                    eq('estado', EstadoProducto.obtenerEstado(fp.estado))
                }
                if (fp.nombre) {
                    ilike('nombre', "%${fp.nombre}%")
                }
                if (fp.marca) {
                    eq('marca', Marca.findByNombre(fp.marca))
                }
                if (fp.categoria) {
                    categorias {
                        eq('nombre', "${fp.categoria}")
                    }
                }
            }
        }
        int pagina = fp.pagina? fp.pagina : 1
        result.sort { it.id }
        FiltroResultado respuesta = new FiltroResultado(pagina, result.totalCount, result as List)
        respond respuesta, model:[status: OK, totalResultados: result.totalCount]
    }

    def listaCorta() {
        if (SpringSecurityUtils.ifAllGranted(Utils.ADMIN)) {
            def result = Producto.list().collect {
                [
                        id: it.id,
                        nombre: it.nombre,
                        estado: it.estado? it.estado.toString() : "No existe"
                ]
            }
            respond result, [status: OK]
        }
        else {
            // TODO eliminar
            def result = Producto.list().collect {
                [
                        id: it.id,
                        nombre: it.nombre,
                        estado: it.estado? it.estado.toString() : "No existe"
                ]
            }
            respond result, [status: OK]
        }
    }

    @Transactional
    def save(Producto productoInstance) {
        if (productoInstance == null) {
            render status: NOT_FOUND
            return
        }

        productoInstance.validate()
        if (productoInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        productoInstance.save flush:true
        respond productoInstance, [status: OK]
    }

    @Transactional
    def update(Producto productoInstance) {
        if (productoInstance == null) {
            render status: NOT_FOUND
            return
        }

        productoInstance.validate()
        if (productoInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        Producto productoViejo = Producto.findById(productoInstance.id)

        productoInstance.save flush:true
        respond productoInstance, [status: OK]

        if (productoInstance.estado != productoViejo.estado && productoInstance.estado == EstadoProducto.DISP) {
            mensajePushService.mensajeBroadcast("Order Tracker: Producto nuevamente disponible", "$productoInstance.nombre")
        }
    }

    @Transactional
    def delete(Producto productoInstance) {
        if (productoInstance == null) {
            render status: NOT_FOUND
            return
        }

        productoInstance.eliminarInstancias()

        productoInstance.delete flush:true
        render status: OK
    }
}
