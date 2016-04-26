package ordertracker.Estados
/**
 * Created by dgacitua on 18-04-16.
 */
enum EstadoPedido {
    ESTADO_NUEVO("Nuevo", 1),
    ESTADO_CONFIRMADO("Confirmado", 2),
    ESTADO_ENVIADO("Enviado", 3),
    ESTADO_ACEPTADO("Aceptado", 4),
    ESTADO_DESPACHADO("Despachado", 5),
    ESTADO_CANCELADO("Cancelado", 6)

    final String valor
    final int num

    EstadoPedido(String valor, int num) {
        this.valor = valor
        this.num = num
    }

    int toNum() { num }
    String toString() { valor }
    String getKey() { name() }

    def displayEnum() {
        return [id: toNum(), tipo: getKey(), nombre: toString()]
    }

    static EstadoPedido obtenerEstado(int valor) {
        EstadoPedido resp = null

        values().each {
            if (it.num == valor) {
                resp = it
            }
        }
        return resp
    }
}