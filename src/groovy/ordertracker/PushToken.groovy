package ordertracker

import grails.validation.Validateable

/**
 * Created by dgacitua on 20-05-16.
 */
@Validateable
class PushToken {
    String username
    String token
}
