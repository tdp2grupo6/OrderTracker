package ordertracker

import ordertracker.Perfiles.Vendedor
import ordertracker.Servicios.Utils

class Agenda {
    boolean ordenada = false

    static hasMany = [agendaDia: AgendaDia]
    static belongsTo = [vendedor: Vendedor]

    static constraints = {
        vendedor blank: false
        agendaDia nullable: true
    }

    static mapping = {
        agendaDia sort: "codigoDia"
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
                AgendaDia ad = new AgendaDia(codigoDia:(int)it, agenda:this).save()
                agendaDia.add(ad)
            }
        }
        assert agendaDia.size() == 7
    }

    void poblarAgendaVacia() {
        agendaDia = []
        Utils.SEMANA.each {
            //println "Agenda vendedor ${vendedor.id} dia ${it}"
            AgendaDia ad = new AgendaDia(codigoDia:it, agenda:this).save(flush: true)
            agendaDia.add(ad)
        }
        verificarAgenda()
        assert agendaDia.size() == 7
    }

    void poblarAgendaClientes() {
        agendaDia.each {
            obtenerClientes(it)
        }
        verificarAgenda()
    }

    def mostrarAgendaDia(int codigoDia) {
        AgendaDia ad = AgendaDia.findByAgendaAndCodigoDia(this, codigoDia)

        def res = [
                codigoDia: ad.codigoDia,
                listaClientes: ad.listaClientes
        ]

        return res
    }

    def mostrarAgendaSemana() {
        List res = []

        Utils.SEMANA.each {
            res.add(mostrarAgendaDia((int)it))
        }

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
