package ordertracker.Marshallers

import grails.converters.JSON
import ordertracker.Servicios.Utils
/**
 * Created by dgacitua on 28-04-16.
 */
class DateMarshaller {
    void register() {
        JSON.registerObjectMarshaller(Date) { Date d ->
            return d.format(Utils.dateFormat, Utils.timeZone)
        }
    }
}
