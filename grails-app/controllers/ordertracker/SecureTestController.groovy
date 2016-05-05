package ordertracker

import ordertracker.Perfiles.Admin
import ordertracker.Perfiles.Vendedor

import static org.springframework.http.HttpStatus.OK

class SecureTestController {
    static responseFormats = ['json']
    static allowedMethods = [index: "GET", listaVendedores: "GET", listaAdmins: "GET"]

    def index() {
        String res = "Acceso a zona logeada"
        render res
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
