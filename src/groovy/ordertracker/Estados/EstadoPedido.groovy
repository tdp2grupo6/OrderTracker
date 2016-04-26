package ordertracker.Estados
/**
 * Created by dgacitua on 18-04-16.
 */
enum EstadoPedido {
    ESTADO_NUEVO("Nuevo", 0),
    ESTADO_CONFIRMADO("Confirmado", 1),
    ESTADO_ENVIADO("Enviado", 2),
    ESTADO_ACEPTADO("Aceptado", 3),
    ESTADO_DESPACHADO("Despachado", 4),
    ESTADO_CANCELADO("Cancelado", 5)

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

    static EstadoPedido int2enum(int num) {
        values().each {
            if (num == it.num) { return it }
        }

        return ESTADO_NUEVO
    }
}