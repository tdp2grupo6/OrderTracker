package ordertracker

class AgendaDia {
    int codigoDia
    ArrayList<Integer> listaClientes = []

    static constraints = {
        codigoDia blank: false, min: 0, max: 6
    }
}
