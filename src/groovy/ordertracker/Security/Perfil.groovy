package ordertracker.Security

/**
 * Created by dgacitua on 04-05-16.
 */
enum Perfil {
    ADMIN('ROLE_ADMIN'),
    VENDEDOR('ROLE_VENDEDOR'),
    CLIENTE('ROLE_CLIENTE')

    String valor
    Perfil(String str) { valor = str }
}