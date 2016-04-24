package ordertracker.Marshallers

import grails.converters.JSON
import ordertracker.Cliente
import ordertracker.Comentario

/**
 * Created by dgacitua on 24-04-16.
 */
class ComentarioMarshaller {
    void register() {
        JSON.registerObjectMarshaller(Comentario) { Comentario c ->
            return [
                id: c.id,
                idCliente: c.cliente.id,
                nombreCliente: c.cliente.nombreCompleto(),
                fechaComentario: c.fechaComentario,
                razonComun: c.razonComun,
                comentario: c.comentario
            ]
        }
    }
}
