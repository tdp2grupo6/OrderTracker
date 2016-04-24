package ordertracker

class Visita {
    Date fechaProgramada
    Date fechaVisitada

    boolean visitaConcretada = false
    //boolean pedidoConcretado = false

    static belongsTo = [cliente: Cliente]

    static constraints = {
        cliente blank: false
        fechaProgramada blank: false
        fechaVisitada nullable: true
    }
}