package ordertracker

import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder
import ordertracker.Perfiles.Vendedor
import ordertracker.Security.Rol
import ordertracker.Security.Usuario

class MensajePushService {
    // Variables para Mensajes Push
    static public final String PUSH_URL = "https://fcm.googleapis.com/fcm/send"
    static public final String PUSH_SERVER_KEY = "AIzaSyBV5ysIhGWjfm_5sPlcksvt-z4kncKVfA8"

    // Genera un mensaje PUSH para un vendedor
    Map mensajePush(Vendedor vend, String titulo, String texto) {
        if (!vend.pushToken.isEmpty()) {
            def ret = [:]
            Date fecha = new Date()

            String url = PUSH_URL
            String msg = "{ \"to\": \"$vend.pushToken\", \"notification\": { \"title\": \"$titulo\", \"text\": \"$texto\" } }"
            String auth = "key=$PUSH_SERVER_KEY"

            RestBuilder rest = new RestBuilder()

            def response = rest.post(url) {
                header 'Authorization', auth
                contentType 'application/json'
                json msg
            }

            Object output = JSON.parse(response.getBody())

            if (response.status == 200 && output.failure == 0) {
                println "[OT-LOG] Mensaje Push Exitoso! ${response.status} <${fecha}>\n[OT-LOG] Cuerpo de la respuesta: ${response.body}"
                ret.put('estadoMensajePush', "SUCCESS")
                ret.put('resultado', "${output.results[0].message_id}")
                return ret
            }
            else {
                println "[OT-LOG] Mensaje Push Fallido! ${response.status} <${fecha}>\n[OT-LOG] Cuerpo de la respuesta: ${response.body}"
                ret.put('estadoMensajePush', "ERROR")
                ret.put('resultado', "${output.results[0].error}")
                return ret
            }
        }
        else {
            def ret = [:]
            ret.put('estadoMensajePush', "INVALID")
            return ret
        }
    }

    // Genera un mensaje PUSH para todos los vendedores
    Map mensajeBroadcast(String titulo, String texto) {
        ArrayList<String> dest = []
        Rol rolVendedor = Rol.findByAuthority(Utils.VENDEDOR)

        Usuario.getAll().each {
            if (it.getAuthorities().contains(rolVendedor) && !it.pushToken.isEmpty()) {
                dest.add(it.pushToken)
            }
        }

        if (!dest.isEmpty()) {
            def ret = [:]
            Date fecha = new Date()
            String array = (dest as JSON).toString()
            //println array

            String url = PUSH_URL
            String msg = "{ \"registration_ids\": $array, \"notification\": { \"title\": \"$titulo\", \"text\": \"$texto\" } }"
            String auth = "key=$PUSH_SERVER_KEY"

            RestBuilder rest = new RestBuilder()

            def response = rest.post(url) {
                header 'Authorization', auth
                contentType 'application/json'
                json msg
            }

            Object output = JSON.parse(response.getBody())

            if (response.status == 200 && output.failure == 0) {
                println "[OT-LOG] Mensaje Push Exitoso! ${response.status} <${fecha}>\n[OT-LOG] Cuerpo de la respuesta: ${response.body}"
                ret.put('estadoMensajePush', "SUCCESS")
                ret.put('resultado', "${output.results}")
                return ret
            }
            else {
                println "[OT-LOG] Mensaje Push Fallido! ${response.status} <${fecha}>\n[OT-LOG] Cuerpo de la respuesta: ${response.body}"
                ret.put('estadoMensajePush', "ERROR")
                ret.put('resultado', "${output.results}")
                return ret
            }
        }
        else {
            def ret = [:]
            ret.put('estadoMensajePush', "INVALID")
            return ret
        }
    }
}
