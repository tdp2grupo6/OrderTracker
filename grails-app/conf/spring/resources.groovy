import ordertracker.CustomObjectMarshallers
import ordertracker.Marshallers.*

// Place your Spring DSL code here
beans = {
	customObjectMarshallers( CustomObjectMarshallers ) {
		marshallers = [
				new DateMarshaller(),
				new ClienteMarshaller(),
				new ProductoMarshaller(),
				new MarcaMarshaller(),
				new CategoriaMarshaller(),
				new PedidoMarshaller(),
				new PedidoElementoMarshaller(),
				new ComentarioMarshaller(),
				new VisitaMarshaller(),
				new FiltroResultadoMarshaller(),
				new UsuarioMarshaller(),
				new VendedorMarshaller(),
				new AgendaMarshaller(),
				new AgendaDiaMarshaller(),
				new DescuentoMarshaller()
		]
	}
}

