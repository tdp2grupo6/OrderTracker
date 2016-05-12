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
                nombreVendedor: v.vendedor? v.vendedor.nombreCompleto() : "NO EXISTE",
                idCliente: v.cliente.id,
                nombreCliente: v.cliente.nombreCompleto(),
                fechaProgramada: v.fechaVisita,
                tipoVisita: v.pedido? "Pedido" : (v.comentario? "Comentario" : "NO EXISTE"),
                elementoVisita: v.pedido? v.pedido : (v.comentario? v.comentario : "NO EXISTE")
            ]
        }
    }
}
