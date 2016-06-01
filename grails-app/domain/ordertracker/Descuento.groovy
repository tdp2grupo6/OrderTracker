package ordertracker

class Descuento {
    Producto producto
    String nombre = ""
    int descuento
    int minimoProductos
    int maximoProductos

    static constraints = {
        descuento blank: false, min: 0, max: 100
        producto blank: false, nullable: true
        minimoProductos blank: false, min: 1
        maximoProductos blank: false, min: 1
    }

    boolean verificarNoSuperposicion() {
        List<Descuento> desc = Descuento.findAllByProducto(producto)
        boolean check = true

        desc.each {
            if (this != it) {
                if (this.minimoProductos <= it.maximoProductos && it.minimoProductos <= this.maximoProductos) {
                    check = false
                }
            }
        }

        return check
    }

    boolean verificarDescuentoValido() {
        boolean cond1 = minimoProductos <= maximoProductos
        boolean cond2 = verificarNoSuperposicion()

        return (cond1 && cond2)
    }

}
