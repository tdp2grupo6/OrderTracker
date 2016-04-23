package ordertracker

class Pedido {
    Date fechaRealizado = new Date()
    float totalCompra = 0f
    EstadoPedido estado = EstadoPedido.ESTADO_ENVIADO

    static belongsTo = [cliente: Cliente]
    static hasMany = [elementos: PedidoDetalle]

    float actualizarTotal() {
        float acum = 0f

        for (e in elementos) {
            acum += e.producto.precio * e.cantidad
        }

        totalCompra = acum
        return totalCompra
    }

    def desplegarItems() {
        return elementos.collect() {
            [
                codigoProducto: it.producto.id,
                nombre: it.producto.nombre,
                cantidad: it.cantidad
            ]
        }
    }

    static constraints = {
        cliente blank: false
        elementos nullable: true
    }
}
