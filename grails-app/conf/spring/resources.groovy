import ordertracker.CustomObjectMarshallers
import ordertracker.Marshallers.ClienteMarshaller
import ordertracker.Marshallers.ProductoMarshaller

// Place your Spring DSL code here
beans = {
	customObjectMarshallers( CustomObjectMarshallers ) {
		marshallers = [
			new ClienteMarshaller(),
			new ProductoMarshaller()
		]
	}
}

