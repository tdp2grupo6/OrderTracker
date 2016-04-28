package ordertracker

class Visita {
    Date fechaVisita = new Date()

    static belongsTo = [cliente: Cliente]

    //static hasOne = [comentario: Comentario, pedido: Pedido]
    Comentario comentario
    Pedido pedido

    static constraints = {
        cliente blank: false
        fechaVisita blank: false
        comentario nullable: true
        pedido nullable: true
    }
}