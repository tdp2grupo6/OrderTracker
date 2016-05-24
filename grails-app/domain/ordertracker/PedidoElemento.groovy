package ordertracker

class PedidoElemento {
    Producto producto
    int cantidad
    float subTotal = 0f

    float obtenerSubtotal() {
        float precioProducto = producto.precio? producto.precio : 0f
        subTotal = precioProducto * cantidad
        return subTotal
    }

    static constraints = {
        producto blank: false, nullable: true
        cantidad blank: false
        subTotal blank: true
    }
}
