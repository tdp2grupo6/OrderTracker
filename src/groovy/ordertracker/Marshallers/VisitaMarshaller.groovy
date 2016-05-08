package ordertracker.Marshallers

import grails.converters.JSON
import ordertracker.Visita

/**
 * Created by dgacitua on 24-04-16.
 */
class VisitaMarshaller {
    void register() {
        JSON.registerObjectMarshaller(Visita) { Visita v ->
            return [
                id: v.id,
                idVendedor: v.vendedor? v.vendedor.id : 0,
                nombreVendedor: v.vendedor? v.vendedor.nombreCompleto() : "No existe",
                idCliente: v.cliente.id,
                nombreCliente: v.cliente.nombreCompleto(),
                fechaProgramada: v.fechaVisita,
                pedido: v.pedido,
                comentario: v.comentario
            ]
        }
    }
}
