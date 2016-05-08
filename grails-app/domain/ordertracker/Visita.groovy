package ordertracker

import ordertracker.Perfiles.Vendedor

class Visita {
    Date fechaVisita = new Date()

    static belongsTo = [cliente: Cliente, vendedor: Vendedor]

    Comentario comentario
    Pedido pedido

    static constraints = {
        cliente blank: false
        vendedor blank: true, nullable: true
        fechaVisita blank: false
        comentario nullable: true
        pedido nullable: true
    }
}