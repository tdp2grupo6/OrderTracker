package ordertracker.Marshallers

import grails.converters.JSON
import ordertracker.Pedido
/**
 * Created by dgacitua on 18-04-16.
 */
class PedidoMarshaller {
    void register() {
        JSON.registerObjectMarshaller(Pedido) { Pedido p ->
            return [
                id: p.id,
                codigoCliente: p.cliente.id,
                nombreCliente: p.cliente.nombreCompleto(),
                fechaRealizado: p.fechaRealizado,
                estado: p.estado.displayEnum(),
                totalCompra: p.actualizarTotal(),
                items: p.elementos
            ]
        }
    }
}