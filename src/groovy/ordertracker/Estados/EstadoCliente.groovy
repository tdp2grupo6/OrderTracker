package ordertracker.Estados

/**
 * Created by dgacitua on 07-04-16.
 */
public enum EstadoCliente {
    ROJO("Visita pendiente", 1),
    AMARILLO("No visitado hoy", 2),
    VERDE("Cliente visitado", 3)

    final String valor
    final int num

    EstadoCliente(String valor, int num) {
        this.valor = valor
        this.num = num
    }

    int toNum() { num }
    String toString() { valor }
    String getKey() { name() }

    def displayEnum() {
        return [id: toNum(), tipo: getKey(), nombre: toString()]
    }

    static EstadoCliente obtenerEstado(int valor) {
        EstadoCliente resp = null

        values().each {
            if (it.num == valor) {
                resp = it
            }
        }
        return resp
    }
}