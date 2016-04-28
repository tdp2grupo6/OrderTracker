import ordertracker.CustomObjectMarshallers
import ordertracker.Marshallers.DateMarshaller
import ordertracker.Marshallers.CategoriaMarshaller
import ordertracker.Marshallers.ClienteMarshaller
import ordertracker.Marshallers.ComentarioMarshaller
import ordertracker.Marshallers.MarcaMarshaller
import ordertracker.Marshallers.ProductoMarshaller
import ordertracker.Marshallers.PedidoMarshaller
import ordertracker.Marshallers.PedidoDetalleMarshaller
import ordertracker.Marshallers.VisitaMarshaller
import ordertracker.Marshallers.FiltroResultadoMarshaller

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
			new PedidoDetalleMarshaller(),
			new ComentarioMarshaller(),
			new VisitaMarshaller(),
			new FiltroResultadoMarshaller()
		]
	}
}

