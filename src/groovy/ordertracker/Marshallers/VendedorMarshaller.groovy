package ordertracker.Marshallers

import grails.converters.JSON
import ordertracker.Perfiles.Vendedor
import ordertracker.Security.Usuario

/**
 * Created by dgacitua on 04-05-16.
 */
class VendedorMarshaller {
    void register() {
        JSON.registerObjectMarshaller(Vendedor) { Vendedor v ->
            return [
                id: v.id,
                username: v.username,
                nombreCompleto: v.nombreCompleto(),
                nombre: v.nombre,
                apellido: v.apellido,
                email: v.email,
                telefono: v.telefono,
                clientes: v.clientes
            ]
        }
    }
}
