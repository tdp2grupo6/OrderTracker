package ordertracker

/**
 * Created by dgacitua on 26-04-16.
 */
class Utils {
    static public final int RESULTADOS_POR_PAGINA = 20

    static public final int DOMINGO = 0
    static public final int LUNES = 1
    static public final int MARTES = 2
    static public final int MIERCOLES = 3
    static public final int JUEVES = 4
    static public final int VIERNES = 5
    static public final int SABADO = 6

    static public final List SEMANA = [DOMINGO, LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO]

    static public final String dateFormat = "yyyy-MM-dd'T'HH:mm:ssZ"
    static public final TimeZone timeZone = TimeZone.getTimeZone("America/Buenos_Aires")

    static int enteroAleatorio(int min, int max) {
        return Math.abs(new Random().nextInt() % max) + min
    }

    static Date fechaAleatoria(Range<Date> range) {
        return range.from + new Random().nextInt(range.to - range.from + 1)
    }

    static Pedido crearPedidoAleatorio() {
        Cliente cl = Cliente.findById(enteroAleatorio(1, Cliente.count))
        int numProductos = enteroAleatorio(1, 3)
        ArrayList<PedidoElemento> pd = new ArrayList<PedidoElemento>()
        Date fin = new Date()
        Date ini = fin - 15

        for (int i=1; i<=numProductos; i++) {
            Producto p = Producto.findById(enteroAleatorio(1, Producto.count))
            int numItems = enteroAleatorio(1, 5)
            pd.add(new PedidoElemento(producto: p, cantidad: numItems))
        }

        return new Pedido(cliente: cl, elementos: pd, fechaRealizado: fechaAleatoria(ini..fin))
    }

    static Date parsearFechaEntrada(String fecha) {
        Date ahora = new Date()
        return ahora.parse(dateFormat, fecha)       // http://snipplr.com/view/10814/
    }
}