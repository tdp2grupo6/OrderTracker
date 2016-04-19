package ordertracker

class PedidoDetalle {
    Producto producto
    int cantidad

    //static belongsTo = [pedido: Pedido]

    static constraints = {
        producto blank: false
        cantidad blank: false
    }
}
