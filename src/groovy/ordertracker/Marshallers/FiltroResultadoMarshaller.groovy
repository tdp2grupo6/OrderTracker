package ordertracker.Marshallers

import grails.converters.JSON
import ordertracker.Filtros.FiltroResultado

/**
 * Created by dgacitua on 26-04-16.
 */
class FiltroResultadoMarshaller {
    void register() {
        JSON.registerObjectMarshaller(FiltroResultado) { FiltroResultado fr ->
            return [
                paginaActual: fr.paginaActual,
                totalPaginas: fr.totalPaginas,
                resultadosPorPagina: fr.resultadosPorPagina,
                totalResultados: fr.totalResultados,
                resultados: fr.resultados
            ]
        }
    }
}
