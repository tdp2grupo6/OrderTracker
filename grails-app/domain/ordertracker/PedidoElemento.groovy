package ordertracker

class PedidoElemento {
    Producto producto
    int cantidad
    float subTotal = 0f

    float obtenerSubtotal() {
        List<Descuento> descuentos = Descuento.findAllByProducto(producto)
        float factorDescuento = 1.0f

        for (Descuento d in descuentos) {
            if (d.minimoProductos <= cantidad && cantidad <= d.maximoProductos) {
                factorDescuento = 1.0f - (d.descuento / 100f)
            }
        }

        float precioProducto = producto.precio? producto.precio : 0f
        subTotal = precioProducto * factorDescuento * cantidad
        return subTotal
    }

    static constraints = {
        producto blank: false, nullable: true
        cantidad blank: false
        subTotal blank: true
    }
}
