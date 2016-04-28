package ordertracker

/**
 * Created by dgacitua on 27-04-16.
 */
class AgendaVendedor {
    int codigoDia
    List listaClientes

    AgendaVendedor(int codigoDia) {
        this.codigoDia = codigoDia
        listaClientes = clientesEnDia(codigoDia)
    }

    List clientesEnDia(int codigoDia) {
        if (Utils.SEMANA.contains(codigoDia)) {
            List result = []

            Cliente.all.each {
                if (it.agendaCliente.contains(codigoDia)) {
                    result.add(it.id)
                }
            }

            return result
        }
        else {
            return []
        }
    }
}
