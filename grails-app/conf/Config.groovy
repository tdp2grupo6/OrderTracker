// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
    all:           '*/*', // 'all' maps to '*' or the first available format in withFormat
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    hal:           ['application/hal+json','application/hal+xml'],
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        // filteringCodecForContentType.'text/html' = 'html'
    }
}


grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// configure passing transaction's read-only attribute to Hibernate session, queries and criterias
// set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.pass.readonly = false
// configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode
grails.hibernate.osiv.readonly = false

environments {
    development {
		grails.logging.jul.usebridge = true
        grails.plugin.springsecurity.mock.active = false
    }
    test {
        grails.logging.jul.usebridge = true
        // Spring Security Mock
        grails.plugin.springsecurity.mock.active = true
        grails.plugin.springsecurity.mock.fullName = "Vendedor Ordertracker"
        grails.plugin.springsecurity.mock.email = "vendedor@ordertracker.com.ar"
        grails.plugin.springsecurity.mock.username =  "vendedor"
        grails.plugin.springsecurity.mock.roles = [ 'ROLE_ADMIN', 'ROLE_VENDEDOR' ]
        grails.plugin.springsecurity.ipRestrictions = [ '/**': ['127.0.0.0/8', '::1/128'] ]
    }
    production {
		grails.logging.jul.usebridge = false
        grails.plugin.springsecurity.mock.active = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
	openshift {
		grails.logging.jul.usebridge = false
        grails.plugin.springsecurity.mock.active = false
	}
}

// log4j configuration
log4j.main = {
    // Example of changing the log pattern for the default console appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',        // controllers
           'org.codehaus.groovy.grails.web.pages',          // GSP
           'org.codehaus.groovy.grails.web.sitemesh',       // layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping',        // URL mapping
           'org.codehaus.groovy.grails.commons',            // core / classloading
           'org.codehaus.groovy.grails.plugins',            // plugins
           'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'
}

// dgacitua: Formatos de ingreso de fecha
grails.databinding.dateFormats = ["yyyy-MM-dd'T'HH:mm:ssZ", "yyyy-MM-dd"]

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'ordertracker.Security.Usuario'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'ordertracker.Security.UsuarioRol'
grails.plugin.springsecurity.authority.className = 'ordertracker.Security.Rol'
grails.plugin.springsecurity.requestMap.className = 'ordertracker.Security.RequestMap'
grails.plugin.springsecurity.securityConfigType = 'InterceptUrlMap'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	'/':                ['permitAll'],
	'/index':           ['permitAll'],
	'/index.gsp':       ['permitAll'],
	'/assets/**':       ['permitAll'],
	'/**/js/**':        ['permitAll'],
	'/**/css/**':       ['permitAll'],
	'/**/images/**':    ['permitAll'],
	'/**/favicon.ico':  ['permitAll']
]
grails.plugin.springsecurity.interceptUrlMap = [
    '/':                        ['permitAll'],
    '/index':                   ['permitAll'],
    '/index.gsp':               ['permitAll'],
    '/assets/**':               ['permitAll'],
    '/**/js/**':                ['permitAll'],
    '/**/css/**':               ['permitAll'],
    '/**/images/**':            ['permitAll'],
    '/**/favicon.ico':          ['permitAll'],
    '/login/**':                ['permitAll'],
    '/logout/**':               ['permitAll'],
    '/oauth/access_token/**':   ['permitAll'],
    '/login-test/**':           ['IS_AUTHENTICATED_REMEMBERED'],
    '/agenda/**':               ['permitAll'],
    '/cliente/**':              ['permitAll'],
    '/vendedor/**':             ['permitAll'],
    '/marca/**':                ['permitAll'],
    '/categoria/**':            ['permitAll'],
    '/producto/**':             ['permitAll'],
    '/pedido/**':               ['permitAll'],
    '/imagen/**':               ['permitAll'],
    '/qrcode/**':               ['permitAll'],
    '/descuento/**':            ['permitAll'],
    '/**':                      ['IS_AUTHENTICATED_REMEMBERED']
]

// dgacitua: Configuración de CORS
cors.enabled = true
cors.url.pattern = '/*'
cors.headers = [
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Credentials': true,
        'Access-Control-Allow-Headers': 'origin, authorization, accept, content-type, x-requested-with',
        'Access-Control-Allow-Methods': 'GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS',
        'Access-Control-Max-Age': 3600
]

//Config for Spring Security REST plugin
//login
grails.plugin.springsecurity.rest.login.active = true
grails.plugin.springsecurity.rest.login.endpointUrl = "/login"
grails.plugin.springsecurity.rest.login.failureStatusCode = 401
grails.plugin.springsecurity.rest.login.useJsonCredentials = true
grails.plugin.springsecurity.rest.login.usernamePropertyName = 'username'
grails.plugin.springsecurity.rest.login.passwordPropertyName = 'password'
//logout
grails.plugin.springsecurity.rest.logout.endpointUrl = '/logout'
//token generation
//grails.plugin.springsecurity.rest.token.generation.useSecureRandom = true
//grails.plugin.springsecurity.rest.token.generation.useUUID = false
//Json Web Token
grails.plugin.springsecurity.rest.token.storage.useJwt = true
grails.plugin.springsecurity.rest.token.storage.jwt.useSignedJwt = true
grails.plugin.springsecurity.rest.token.storage.jwt.secret = 'c0rlhqelhas8m2agg8ttg10lqk2e7smn'
grails.plugin.springsecurity.rest.token.storage.jwt.expiration = 3600
//use cache as storage
//grails.plugin.springsecurity.rest.token.storage.useGrailsCache = true
//grails.plugin.springsecurity.rest.token.storage.grailsCacheName = 'xauth-token'
//token rendering
grails.plugin.springsecurity.rest.token.rendering.usernamePropertyName = 'username'
grails.plugin.springsecurity.rest.token.rendering.authoritiesPropertyName = 'roles'
//token validate
grails.plugin.springsecurity.rest.token.validation.useBearerToken = true
grails.plugin.springsecurity.rest.token.validation.active = true
grails.plugin.springsecurity.rest.token.validation.endpointUrl = '/validate'
//filter chain
grails.plugin.springsecurity.filterChain.chainMap = [
        //'/api/**': 'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter',  // Stateless chain
        '/login/**':                'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter',  // Stateless chain
        '/logout/**':               'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter',  // Stateless chain
        '/validate/**':             'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter',  // Stateless chain
        '/oauth/access_token/**':   'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter',  // Stateless chain
        '/cliente/**':              'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter',  // Stateless chain
        '/producto/**':             'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter',  // Stateless chain
        '/marca/**':                'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter',  // Stateless chain
        '/categoria/**':            'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter',  // Stateless chain
        '/pedido/**':               'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter',  // Stateless chain
        '/agenda/**':               'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter',  // Stateless chain
        '/comentario/**':           'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter',  // Stateless chain
        '/visita/**':               'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter',  // Stateless chain
        '/imagen/**':               'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter',  // Stateless chain
        '/persona/**':              'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter',  // Stateless chain
        '/login-test/**':           'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter',  // Stateless chain
        '/vendedor/**':             'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter',  // Stateless chain
        '/descuento/**':            'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter',  // Stateless chain
        '/**':                      'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'                                                                      // Traditional chain
]
grails.plugin.springsecurity.rest.token.validation.enableAnonymousAccess = true

// dgacitua: Configuración para Grails Mail
grails {
    mail {
        host = "smtp.gmail.com"
        port = 465
        username = "tdp2grupo6@gmail.com"
        password = "ubascrum"
        props = ["mail.smtp.auth":"true",
                 "mail.smtp.socketFactory.port":"465",
                 "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
                 "mail.smtp.socketFactory.fallback":"false"]

    }
}