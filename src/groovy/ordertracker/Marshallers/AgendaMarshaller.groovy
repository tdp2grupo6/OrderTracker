package ordertracker.Marshallers

import grails.converters.JSON
import ordertracker.Agenda

/**
 * Created by dgacitua on 05-05-16.
 */
class AgendaMarshaller {
    void register() {
        JSON.registerObjectMarshaller(Agenda) { Agenda ag ->
            return [
                    id: ag.id,
                    idVendedor: ag.vendedor.id,
                    nombreVendedor: ag.vendedor.nombreCompleto(),
                    clientes: ag.vendedor.clientes,
                    agendaOrdenada: ag.ordenada,
                    agenda: ag.agendaDia
            ]
        }
    }
}
