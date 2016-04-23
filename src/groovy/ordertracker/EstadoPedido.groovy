package ordertracker

/**
 * Created by dgacitua on 18-04-16.
 */
enum EstadoPedido {
    ESTADO_NUEVO("Nuevo", 0),
    ESTADO_CONFIRMADO("Confirmado", 1),
    ESTADO_ENVIADO("Enviado", 2),
    ESTADO_ACEPTADO("Aceptado", 3),
    ESTADO_RECHAZADO("Rechazado", 4),
    ESTADO_CANCELADO("Cancelado", 5)

    final String valor
    final int id

    EstadoPedido(String valor, int id) {
        this.valor = valor
        this.id = id
    }

    int toId() { id }
    String toString() { valor }
    String getKey() { name() }

    def displayEnum() {
        return [ id: toId(), tipo: getKey(), nombre: toString() ]
    }
}