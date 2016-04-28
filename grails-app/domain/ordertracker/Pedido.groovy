package ordertracker

import ordertracker.Estados.EstadoPedido

class Pedido {
    Date fechaRealizado = new Date()
    float totalCompra = 0f
    EstadoPedido estado = EstadoPedido.ESTADO_ENVIADO

    static belongsTo = [cliente: Cliente]
    static hasMany = [elementos: PedidoElemento]

    float actualizarTotal() {
        float acum = 0f

        elementos.each {
            acum += it.producto.precio * it.cantidad
        }

        totalCompra = acum
        return totalCompra
    }

    static constraints = {
        cliente blank: false
        elementos nullable: true
    }
}
