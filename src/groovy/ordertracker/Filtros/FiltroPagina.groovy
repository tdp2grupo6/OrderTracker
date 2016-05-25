package ordertracker.Filtros

import ordertracker.Servicios.Utils

/**
 * Created by dgacitua on 25-05-16.
 */
class FiltroPagina {
    int pagina

    int primerValor() {
        return pagina ? ((pagina-1)*Utils.RESULTADOS_POR_PAGINA) : 0
    }
}
