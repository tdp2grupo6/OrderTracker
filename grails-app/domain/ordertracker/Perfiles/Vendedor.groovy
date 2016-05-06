package ordertracker.Perfiles

import ordertracker.Agenda
import ordertracker.Cliente
import ordertracker.Security.Perfil
import ordertracker.Security.Rol
import ordertracker.Security.Usuario
import ordertracker.Security.UsuarioRol

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

        Agenda ag = new Agenda(vendedor: this)
        this.agenda = ag
        ag.poblarAgendaVacia()
        ag.save(flush:true, insert:true)
    }
}
