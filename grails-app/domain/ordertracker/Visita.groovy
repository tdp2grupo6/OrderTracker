package ordertracker

import ordertracker.Perfiles.Vendedor

class Visita {
    Date fechaVisita = new Date()

    static belongsTo = [cliente: Cliente, vendedor: Vendedor]
    //static hasOne = [comentario: Comentario, pedido: Pedido]

    Comentario comentario
    Pedido pedido

    static constraints = {
        cliente blank: false, nullable: true
        vendedor blank: true, nullable: true
        fechaVisita blank: false
        comentario blank: true, nullable: true
        pedido blank: true, nullable: true
    }
}