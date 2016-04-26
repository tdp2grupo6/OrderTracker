package ordertracker

import grails.validation.Validateable
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
        return pagina ? ((pagina-1)*Constants.RESULTADOS_POR_PAGINA+1) : 0
    }
}
