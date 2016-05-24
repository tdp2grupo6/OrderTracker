package ordertracker.Perfiles

import ordertracker.Agenda
import ordertracker.Cliente
import ordertracker.Comentario
import ordertracker.Pedido
import ordertracker.Security.Perfil
import ordertracker.Security.Rol
import ordertracker.Security.Usuario
import ordertracker.Security.UsuarioRol
import ordertracker.Visita

class Vendedor extends Usuario {
    static hasOne = [agenda: Agenda]
    static hasMany = [clientes: Cliente]

    static constraints = {
        agenda nullable: true
        clientes nullable: true
    }

    void habilitarUsuario() {
        def rol = Rol.findByAuthority(Perfil.VENDEDOR.valor)

        this.save()
        UsuarioRol.create(this, rol, true)

        Agenda ag = new Agenda(vendedor: this).save(flush:true)
        this.agenda = ag
        ag.poblarAgendaVacia()
    }

    void eliminarInstancias() {
        def ag = this.agenda

        this.agenda = null
        this.clientes = null
        this.save flush:true

        ag.delete flush:true

        Comentario.list().each {
            if (it.vendedor == this) {
                it.vendedor = null
            }
        }
        Pedido.list().each {
            if (it.vendedor == this) {
                it.vendedor = null
            }
        }
        Visita.list().each {
            if (it.vendedor == this) {
                it.vendedor = null
            }
        }
        UsuarioRol.list().each {
            if (it.usuario == this) {
                it.delete flush:true
            }
        }
    }
}
