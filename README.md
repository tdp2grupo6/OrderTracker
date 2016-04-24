# OrderTracker (backend) [![Build Status](https://travis-ci.org/tdp2grupo6/OrderTracker.svg?branch=master)](https://travis-ci.org/tdp2grupo6/OrderTracker)

Backend aplicación "Order Tracker" - Taller de Desarrollo de Proyectos II

## Requerimientos
* [OpenJDK 7](http://openjdk.java.net/)
* [SDKMAN 4.0](http://sdkman.io/)
* [Groovy 2.3.7](http://www.groovy-lang.org/)
* [Grails 2.4.4](https://grails.org/)
* [GGTS 3.6.4](https://spring.io/tools/ggts) o [IntelliJ IDEA 15](https://www.jetbrains.com/idea/)

## Instrucciones de uso

1. Clonar repositorio

		$ git clone git@github.com:tdp2grupo6/OrderTracker.git

2. En GGTS (>=3.6.4) habilitar la perspectiva Git en el menú Window -> Open Perspective -> Other...

3. Desde la perspectiva Git, abrir como repositorio local el directorio /.git del repositorio recién clonado, también indicar que importe todos los Proyectos de Eclipse que encuentre

4. En la Perspectiva Grails de GGTS se cargará el proyecto recién importado, si el proyecto no compila por errores de ruta o dependencias faltantes, abrir el Grails Command Prompt de GGTS y ejecutar los siguientes comandos:

		> clean
		> refresh-dependencies

5. Se debe gestionar el proyecto como un Proyecto Grails de GGTS

## Comandos Varios

### Instalar dependencias (desde Terminal)
```
$ grails clean
$ grails refresh-dependencies
```

### Correr aplicacion (desde Terminal)
```
$ grails run-app
```

### Tests unitarios (desde Terminal)
```
$ grails test-app unit:
```

## Otros

### Travis CI
https://travis-ci.org/tdp2grupo6/OrderTracker

### Servidor de Pre-Producción
http://ordertracker-tdp2grupo6.rhcloud.com/