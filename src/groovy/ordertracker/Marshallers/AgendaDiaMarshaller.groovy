package ordertracker.Marshallers

import grails.converters.JSON
import ordertracker.AgendaDia

/**
 * Created by dgacitua on 08-05-16.
 */
class AgendaDiaMarshaller {
    void register() {
        JSON.registerObjectMarshaller(AgendaDia) { AgendaDia ad ->
            return [
                    id: ad.id,
                    idAgenda: ad.agenda.id,
                    codigoDia: ad.codigoDia,
                    listaClientes: ad.listaClientes
            ]
        }
    }
}
