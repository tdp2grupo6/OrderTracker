package ordertracker

class PedidoElemento {
    Producto producto
    int cantidad

    float obtenerSubtotal() {
        return producto.precio * cantidad
    }

    static constraints = {
        producto blank: false
        cantidad blank: false
    }
}
