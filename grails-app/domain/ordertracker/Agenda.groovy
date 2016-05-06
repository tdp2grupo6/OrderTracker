package ordertracker

import ordertracker.Perfiles.Vendedor
import org.codehaus.groovy.grails.web.json.JSONObject

class Agenda {
    boolean ordenada = false

    static hasMany = [agendaDia: AgendaDia]
    static belongsTo = [vendedor: Vendedor]

    static constraints = {
        vendedor blank: false
        agendaDia nullable: true
    }

    void verificarAgenda() {
        // Elimina dias no válidos
        agendaDia.each{
            if (!Utils.SEMANA.contains(it.codigoDia)) {
                agendaDia.remove(it)
            }
        }
        // Agrega los dias que faltan
        Utils.SEMANA.each {
            if (!agendaDia.codigoDia.contains(it)) {
                agendaDia.add(new AgendaDia(codigoDia: (int)it))
            }
        }
        assert agendaDia.size() == 7
    }

    void poblarAgendaVacia() {
        agendaDia = []
        Utils.SEMANA.each {
            agendaDia.add(new AgendaDia(codigoDia: (int)it))
        }
        assert agendaDia.size() == 7
    }

    void poblarAgendaClientes() {
        if (!ordenada) {
            agendaDia.each {
                obtenerClientes(it)
            }
        }
        verificarAgenda()
    }

    def mostrarCitas() {
        List res = []

        agendaDia.each {
            res.add([
                codigoDia: it.codigoDia,
                listaClientes: it.listaClientes
            ])
        }

        return res
    }

    def mostrarDia(int codigoDia) {
        AgendaDia ad = agendaDia.find { it.codigoDia = codigoDia }
        JSONObject res = new JSONObject()
        res.put("codigoDia", ad.codigoDia)
        res.put("listaClientes", ad.listaClientes)

        return res
    }

    void obtenerClientes(AgendaDia ad) {
        ad.listaClientes = clientesEnDia(ad.codigoDia)
    }

    List clientesEnDia(int codigoDia) {
        if (Utils.SEMANA.contains(codigoDia)) {
            List result = []

            vendedor.clientes.each {
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
