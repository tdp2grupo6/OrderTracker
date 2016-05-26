package ordertracker.Filtros

import grails.validation.Validateable
import ordertracker.Utils

/**
 * Created by dgacitua on 25-05-16.
 */
@Validateable
class FiltroProducto {
    int estado

    String nombre
    String marca
    String categoria

    int pagina

    int primerValor() {
        return pagina ? ((pagina-1)*Utils.RESULTADOS_POR_PAGINA) : 0
    }
}
