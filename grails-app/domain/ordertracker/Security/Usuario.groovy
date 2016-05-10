package ordertracker.Security

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class Usuario implements Serializable {

	private static final long serialVersionUID = 1

	transient springSecurityService

	String username
	String password
	String nombre
	String apellido
	String email
	String telefono = ""

	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	Usuario(String username, String password) {
		this()
		this.username = username
		this.password = password
	}

	Set<Rol> getAuthorities() {
		UsuarioRol.findAllByUsuario(this)*.rol
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}

	static transients = ['springSecurityService']

	static constraints = {
		username blank: false, unique: true
		password blank: false
		email blank: false, email: true, unique: true
		telefono blank: true
		nombre blank: false
		apellido blank: false
	}

	static mapping = {
		tablePerHierarchy false
		password column: '`password`'
	}

	// Métodos Custom
	String nombreCompleto() { "$apellido, $nombre" }

	void habilitarUsuario() {
		this.save(flush: true)
	}

	void eliminarInstancias() {
		UsuarioRol.findAllByUsuario(this)*.delete()
	}
}
