package ordertracker

class CustomObjectMarshallers {
	List marshallers = []

	def register() {
		marshallers.each{ it.register() }
	}
}
