package ordertracker.Marshallers

import grails.converters.JSON
import ordertracker.Descuento
/**
 * Created by dgacitua on 01-06-16.
 */
class DescuentoMarshaller {
    void register() {
        JSON.registerObjectMarshaller(Descuento) { Descuento d ->
            return [
                    id: d.id,
                    nombre: d.nombre,
                    idProducto: d.producto? d.producto.id : 0,
                    nombreProducto: d.producto? d.producto.nombre : 'No existe',
                    descuento: d.descuento,
                    minimoProductos: d.minimoProductos,
                    maximoProductos: d.maximoProductos
            ]
        }
    }
}

