package ordertracker

class Comentario {
    Date fechaComentario = new Date()
    String razonComun = "Otro"
    String comentario = ""

    static belongsTo = [cliente: Cliente]

    static constraints = {
        cliente blank: false
    }
}
