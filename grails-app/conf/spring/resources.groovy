import ordertracker.CustomObjectMarshallers
import ordertracker.Marshallers.CategoriaMarshaller
import ordertracker.Marshallers.ClienteMarshaller
import ordertracker.Marshallers.MarcaMarshaller
import ordertracker.Marshallers.ProductoMarshaller
import ordertracker.Marshallers.PedidoMarshaller

// Place your Spring DSL code here
beans = {
	customObjectMarshallers( CustomObjectMarshallers ) {
		marshallers = [
			new ClienteMarshaller(),
			new ProductoMarshaller(),
			new MarcaMarshaller(),
			new CategoriaMarshaller(),
			new PedidoMarshaller()
		]
	}
}

