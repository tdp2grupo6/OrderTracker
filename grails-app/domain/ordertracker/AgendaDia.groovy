package ordertracker

class AgendaDia {
    int codigoDia
    ArrayList<Integer> listaClientes = []

    static belongsTo = [agenda: Agenda]

    static constraints = {
        codigoDia blank: false
    }
}
