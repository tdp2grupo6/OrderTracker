package ordertracker.Marshallers

import grails.converters.JSON
import ordertracker.PedidoDetalle
/**
 * Created by dgacitua on 25-04-16.
 */
class PedidoDetalleMarshaller {
    void register() {
         JSON.registerObjectMarshaller(PedidoDetalle) { PedidoDetalle it ->
            return [
                codigoProducto: it.producto.id,
                nombreProducto: it.producto.nombre,
                precioUnitario: it.producto.precio,
                cantidad: it.cantidad,
                subtotalProducto: it.obtenerSubtotal()
            ]
        }
    }
}
