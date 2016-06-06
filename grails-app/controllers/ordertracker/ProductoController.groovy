package ordertracker

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.transaction.Transactional
import ordertracker.Estados.EstadoProducto
import ordertracker.Filtros.FiltroProducto
import ordertracker.Filtros.FiltroResultado
import ordertracker.Relations.CategoriaProducto
import org.codehaus.groovy.grails.web.json.JSONObject
import org.codehaus.groovy.runtime.StringGroovyMethods

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class ProductoController {
    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", show: "GET", search: "GET",
                             searchInCategoria: "GET", searchInMarca: "GET", filtroAdmin: "POST",
                             buscarDescuentos: "GET"]

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
            Categoria cat = Categoria.findById(idFiltro)
            def res = CategoriaProducto.findAllByCategoria(cat).collect { it.producto } as Set
            /*
            def res = Producto.withCriteria {
                    categorias {
                        idEq(idFiltro)
                    }
                }
            */
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
                    ilike('nombre', "${fp.nombre}%")
                }
                if (fp.marca) {
                    eq('marca', Marca.findByNombre(fp.marca))
                }
            }
        }

        if (fp.categoria) {
            Categoria cat = Categoria.findByNombre(fp.categoria)
            def result2 = cat.obtenerProductos()
            result = result.intersect(result2)
        }

        int pagina = fp.pagina? fp.pagina : 1
        result.sort { it.id }
        FiltroResultado respuesta = new FiltroResultado(pagina, result.size(), result as List)
        respond respuesta, model:[status: OK, totalResultados: result.size()]
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

    def buscarDescuentos(Producto p) {
        List<Descuento> resp = Descuento.findAllByProducto(p)
        respond resp, [status: OK]
    }

    @Transactional
    def save() {
        JSONObject body = new JSONObject()

        JSON.use('deep') {
            //println "Here is request.JSON: ${request.JSON as JSON}"
            //println "Here is params: $params"
            body = request.JSON as JSONObject
            desc = body.descuentos
        }

        Producto productoInstance = new Producto(body)

        List<Descuento> desc2 = []
        desc.each {
            desc2.add(new Descuento(it))
        }

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
        productoInstance.procesarCategorias(body)
        desc2.each {
            it.save flush:true
        }

        respond productoInstance, [status: OK]
    }

    @Transactional
    def update() {
        JSONObject body = new JSONObject()
        long targetId = StringGroovyMethods.isLong(params.id)? StringGroovyMethods.toLong(params.id) : 0

        JSON.use('deep') {
            //println "Here is request.JSON: ${request.JSON as JSON}"
            //println "Here is params: $params"
            body = request.JSON as JSONObject
            desc = body.descuentos
        }

        Producto productoInstance = Producto.findById(targetId)
        int stockAntiguo = productoInstance? productoInstance.stock : 0

        if (productoInstance == null) {
            render status: NOT_FOUND
            return
        }

        productoInstance.properties = body
        productoInstance.validate()
        if (productoInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        Producto productoViejo = Producto.findById(productoInstance.id)

        productoInstance.save flush:true
        productoInstance.procesarCategorias(body)
        println "[OT-LOG] Producto editado: ${productoInstance.nombre} - Stock Antiguo: ${stockAntiguo} - Stock Nuevo: ${productoInstance.stock}"

        respond productoInstance, [status: OK]

        if (stockAntiguo == 0 && productoInstance.stock > 0) {
            mensajePushService.mensajeBroadcast("OT: Producto nuevamente disponible", "${productoInstance.nombre}")
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
