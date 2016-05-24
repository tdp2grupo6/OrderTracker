package ordertracker.Marshallers

import grails.converters.JSON
import ordertracker.PedidoElemento

/**
 * Created by dgacitua on 25-04-16.
 */
class PedidoElementoMarshaller {
    void register() {
         JSON.registerObjectMarshaller(PedidoElemento) { PedidoElemento it ->
            return [
                codigoProducto: it.producto? it.producto.id : 0,
                nombreProducto: it.producto? it.producto.nombre : "No existe",
                precioUnitario: it.producto? it.producto.precio : 0f,
                cantidad: it.cantidad,
                subtotalProducto: it.subTotal
            ]
        }
    }
}
