package ordertracker.Perfiles

import ordertracker.Security.Perfil
import ordertracker.Security.Rol
import ordertracker.Security.Usuario
import ordertracker.Security.UsuarioRol

class Admin extends Usuario {

    static constraints = {
    }

    void habilitarUsuario() {
        def rol = Rol.findByAuthority(Perfil.ADMIN.valor)

        this.save()
        UsuarioRol.create(this, rol, true)
    }
}
