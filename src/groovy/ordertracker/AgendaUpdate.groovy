package ordertracker

import grails.validation.Validateable
/**
 * Created by dgacitua on 14-05-16.
 */
@Validateable
class AgendaUpdate {
    long idVendedor
    List<Long> clientes
    List<AgendaSimple> agenda

    boolean agendaOrdenada = true

    class AgendaSimple {
        int codigoDia
        List<Integer> listaClientes
    }
}
