package ordertracker

import ordertracker.Perfiles.Vendedor

class Comentario {
    Date fechaComentario = new Date()
    String razonComun = "Otro"
    String comentario = ""

    static belongsTo = [cliente: Cliente, vendedor: Vendedor]

    static constraints = {
        cliente blank: false
        vendedor blank: true, nullable: true
    }
}
