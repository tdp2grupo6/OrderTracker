package ordertracker

/**
 * Created by dgacitua on 18-04-16.
 */
enum EstadoPedido {
    ABI("Abierto"), PEND("Pendiente"), VERI("Por Verificar"), ACEP("Aceptado"), RECH("Rechazado"), DESP("Despachado")

    final String valor

    EstadoPedido(String valor) { this.valor = valor }

    String toString() { valor }
    String getKey() { name() }

    def displayEnum() {
        return [ tipo: getKey(), nombre: toString() ]
    }
}