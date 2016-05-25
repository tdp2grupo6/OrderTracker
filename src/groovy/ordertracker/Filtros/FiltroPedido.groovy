package ordertracker.Filtros

import grails.validation.Validateable
import ordertracker.Utils

/**
 * Created by dgacitua on 26-04-16.
 */
@Validateable
class FiltroPedido {
    int estado
    Date fechaInicio
    Date fechaFin

    long idCliente
    long idVendedor

    int pagina

    int primerValor() {
        return pagina ? ((pagina-1)*Utils.RESULTADOS_POR_PAGINA) : 0
    }
}
