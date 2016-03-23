package ordertracker

public enum EstadoProducto{
	DISP("Disponible"), NODISP("No disponible"), SUSP("Suspendido")
	
	final String valor
	
	EstadoProducto(String valor) { this.valor = valor }
	
	String toString() { valor }
	String getKey() { name() }
}
