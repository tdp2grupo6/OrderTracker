package ordertracker.Filtros

import grails.validation.Validateable
import ordertracker.Utils

/**
 * Created by dgacitua on 10-05-16.
 */
@Validateable
class FiltroCliente {
    String nombre
    String apellido
    String email
    String direccion

    long idCliente

    int pagina

    int primerValor() {
        return pagina ? ((pagina-1)*Utils.RESULTADOS_POR_PAGINA) : 0
    }
}
