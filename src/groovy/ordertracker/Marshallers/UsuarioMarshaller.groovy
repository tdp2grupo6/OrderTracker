package ordertracker.Marshallers

import grails.converters.JSON
import ordertracker.Security.Usuario

/**
 * Created by dgacitua on 03-05-16.
 */
class UsuarioMarshaller {
    void register() {
        JSON.registerObjectMarshaller(Usuario) { Usuario u ->
            return [
                id: u.id,
                username: u.username,
                nombreCompleto: u.nombreCompleto(),
                nombre: u.nombre,
                apellido: u.apellido,
                email: u.email,
                telefono: u.telefono
            ]
        }
    }
}