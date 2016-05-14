package ordertracker

class AgendaDia {
    int codigoDia
    ArrayList<Long> listaClientes = []

    static belongsTo = [agenda: Agenda]

    static constraints = {
        codigoDia blank: false
    }
}
