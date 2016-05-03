package ordertracker.Estados

public enum EstadoProducto{
	SUSP("Suspendido", 1),
	NODISP("No disponible", 2),
	DISP("Disponible", 3)

	final String valor
	final int num

	EstadoProducto(String valor, int num) {
		this.valor = valor
		this.num = num
	}

	int toNum() { num }
	String toString() { valor }
	String getKey() { name() }

	def displayEnum() {
		return [id: toNum(), tipo: getKey(), nombre: toString()]
	}

	static EstadoProducto obtenerEstado(int valor) {
		EstadoProducto resp = null

		values().each {
			if (it.num == valor) {
				resp = it
			}
		}
		return resp
	}
}
