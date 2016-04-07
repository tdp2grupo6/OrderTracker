package ordertracker

/**
 * Created by dgacitua on 07-04-16.
 */
public enum EstadoCliente {
    VERDE("Cliente visitado"), AMARILLO("No visitado hoy"), ROJO("Visita pendiente")

    final String valor

    EstadoCliente(String valor) { this.valor = valor }

    String toString() { valor }
    String getKey() { name() }

    def displayEnum() {
        return [ tipo: getKey(), nombre: toString() ]
    }
}