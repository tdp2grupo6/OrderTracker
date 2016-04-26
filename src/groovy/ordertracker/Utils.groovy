package ordertracker

/**
 * Created by dgacitua on 26-04-16.
 */
class Utils {
    static public final int RESULTADOS_POR_PAGINA = 20

    static int enteroAleatorio(int min, int max) {
        return Math.abs(new Random().nextInt() % max) + min
    }

    static Date fechaAleatoria(Range<Date> range) {
        return range.from + new Random().nextInt(range.to - range.from + 1)
    }

    static Pedido crearPedidoAleatorio() {
        Cliente cl = Cliente.findById(Utils.enteroAleatorio(1, Cliente.count))
        int numProductos = Utils.enteroAleatorio(1, 3)
        ArrayList<PedidoDetalle> pd = new ArrayList<PedidoDetalle>()
        Date fin = new Date()
        Date ini = fin - 15

        for (int i=1; i<=numProductos; i++) {
            Producto p = Producto.findById(Utils.enteroAleatorio(1, Producto.count))
            int numItems = Utils.enteroAleatorio(1, 5)
            pd.add(new PedidoDetalle(producto: p, cantidad: numItems))
        }

        return new Pedido(cliente: cl, elementos: pd, fechaRealizado: Utils.fechaAleatoria(ini..fin))
    }
}