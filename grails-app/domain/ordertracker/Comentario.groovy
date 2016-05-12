package ordertracker

import ordertracker.Perfiles.Vendedor

class Comentario {
    Date fechaComentario = new Date()
    String razonComun = "Otro"
    String comentario = ""

    static belongsTo = [cliente: Cliente, vendedor: Vendedor, visita: Visita]

    static constraints = {
        cliente blank: false
        vendedor blank: true, nullable: true
        visita blank: true, nullable: true
    }
}
