package ordertracker.Relations

import ordertracker.Categoria
import ordertracker.Producto

class CategoriaProducto implements Serializable {
    Categoria categoria
    Producto producto

    static mapping = {
        table 'categoria_producto'
        version false
        id composite: ['categoria', 'producto']
    }

    static CategoriaProducto create(Categoria categoria, Producto prod, boolean flush = false) {
        CategoriaProducto categoriaProducto = new CategoriaProducto(categoria: categoria, producto: prod)
        categoriaProducto.save(flush: flush, insert: true)
        return categoriaProducto
    }

    static boolean remove(Categoria categoria, Producto prod, boolean flush = false) {
        CategoriaProducto categoriaProducto = CategoriaProducto.findByCategoriaAndProducto(categoria, prod)
        return categoriaProducto ? categoriaProducto.delete(flush: flush) : false
    }

    static void removeAll(Categoria categoria) {
        executeUpdate("DELETE FROM CategoriaProducto WHERE categoria=:categoria", [categoria: categoria])
    }

    static void removeAll(Producto producto) {
        executeUpdate("DELETE FROM CategoriaProducto WHERE producto=:producto", [producto: producto])
    }
}
