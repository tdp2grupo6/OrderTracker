package ordertracker.Filtros

import grails.validation.Validateable
import ordertracker.Servicios.Utils

/**
 * Created by dgacitua on 24-05-16.
 */
@Validateable
class FiltroVendedor {
    String username
    String nombre
    String apellido

    int pagina

    int primerValor() {
        return pagina ? ((pagina-1)*Utils.RESULTADOS_POR_PAGINA) : 0
    }
}
