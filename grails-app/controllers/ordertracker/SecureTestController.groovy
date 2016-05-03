package ordertracker

import grails.plugin.springsecurity.annotation.Secured

class SecureTestController {
    @Secured('ROLE_VENDEDOR')
    def indexVendedor() {
        render 'Acceso a vendedor'
    }

    @Secured('ROLE_ADMIN')
    def indexAdmin() {
        render 'Acceso a admin'
    }

    @Secured('ROLE_CLIENTE')
    def indexCliente() {
        render 'Acceso a cliente'
    }
}
