package ordertracker

import ordertracker.Perfiles.Admin
import ordertracker.Perfiles.Vendedor
import ordertracker.Security.Usuario

import static org.springframework.http.HttpStatus.OK

class SecureTestController {
    static responseFormats = ['json']
    static allowedMethods = [index: "GET", datosUsuario: "GET", listaVendedores: "GET", listaAdmins: "GET"]

    def springSecurityService

    def index() {
        String res = "Acceso a zona logeada"
        render res
    }

    def datosUsuario() {
        Usuario user = springSecurityService.currentUser
        respond user, [status: OK]
    }

    def listaVendedores() {
        def res = Vendedor.list()
        respond res, [status: OK]
    }

    def listaAdmins() {
        def res = Admin.list()
        respond res, [status: OK]
    }
}
