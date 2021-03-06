package ordertracker

import ordertracker.Estados.EstadoPedido
import ordertracker.Perfiles.Vendedor

class Pedido {
    Date fechaRealizado = new Date()
    float totalCompra = 0f
    EstadoPedido estado = EstadoPedido.ESTADO_ENVIADO

    static belongsTo = [cliente: Cliente, vendedor: Vendedor, visita: Visita]
    static hasMany = [elementos: PedidoElemento]

    float actualizarTotal() {
        float acum = 0f

        elementos.each {
            it.obtenerSubtotal()
            acum += it.subTotal
        }

        totalCompra = acum
        return totalCompra
    }

    static constraints = {
        cliente blank: false, nullable: true
        vendedor blank: true, nullable: true
        elementos blank: false, nullable: true
        visita blank: true, nullable: true
        totalCompra blank: true
    }
}
