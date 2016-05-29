package ordertracker

import grails.validation.Validateable
import ordertracker.Perfiles.Vendedor

@Validateable
class RequestTransfer {
    Cliente cliente
    Vendedor vendedorViejo
    Vendedor vendedorNuevo
}
