package ordertracker

/**
 * Created by dgacitua on 26-04-16.
 */
class FiltroResultado {
    int paginaActual
    int totalPaginas
    int resultadosPorPagina
    int totalResultados
    List resultados

    FiltroResultado(int pagina, int totalRes, List res) {
        paginaActual = pagina
        totalPaginas = Math.ceil(totalRes / (double)Constants.RESULTADOS_POR_PAGINA)
        resultadosPorPagina = Constants.RESULTADOS_POR_PAGINA
        totalResultados = totalRes
        resultados = res
    }
}
