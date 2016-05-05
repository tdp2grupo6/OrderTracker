package ordertracker.Perfiles

import ordertracker.Cliente
import ordertracker.Security.Perfil
import ordertracker.Security.Rol
import ordertracker.Security.Usuario
import ordertracker.Security.UsuarioRol

class Vendedor extends Usuario {
    static hasMany = [clientes: Cliente]

    static constraints = {
        clientes nullable: true
    }

    void habilitarUsuario() {
        def rol = Rol.findByAuthority(Perfil.VENDEDOR.valor)

        this.save()
        UsuarioRol.create(this, rol, true)
    }
}
