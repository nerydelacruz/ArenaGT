# ArenaGT Lite - Lab 4 Polimorfismo

Programa de consola en Java para llevar la copa deportiva entre empresas de ArenaGT:
equipos, encuentros de futbol sala y baloncesto, exhibiciones, calendario y tabla de posiciones.

**Autor:** Nery Javier de la Cruz Huinil - 261233
**Curso:** CC2008 Programacion Orientada a Objetos, UVG

## Requisitos

- Java 11 o superior (JDK).

## Compilar y ejecutar

Desde la carpeta raiz del proyecto:

```bash
javac -d bin src/modelo/*.java src/controlador/*.java
java -cp bin controlador.Main
```

En Windows (PowerShell o CMD) los comandos son los mismos, cambiando `/` por `\` si hace falta.

## Estructura

```
src/
├── controlador/
│   └── Main.java          Menu, lectura de datos y datos iniciales
└── modelo/
    ├── Programable.java   Interfaz del calendario (Comparable<Programable>)
    ├── Puntuable.java     Interfaz de la tabla de posiciones
    ├── Programacion.java  Fecha, hora y sede; unico criterio de orden
    ├── Encuentro.java     Clase abstracta de los encuentros
    ├── FutbolSala.java    Reglas de futbol sala (3/1/0, permite empate)
    ├── Baloncesto.java    Reglas de baloncesto (2/1, no permite empate)
    ├── Exhibicion.java    Evento sin equipos ni resultado
    ├── Equipo.java        Equipo con puntos de tabla (Comparable<Equipo>)
    └── Copa.java          Guarda equipos y eventos (Iterable<Programable>)
```

## Datos iniciales

| Equipo | Codigo | Empresa | Puntos |
|---|---|---|---|
| Faro | FAR | Tigo | 4 |
| Luna | LUN | Tigo | 4 |
| Sol  | SOL | Claro | 3 |
| Rayo | RAY | Claro | 0 |

Faro y Luna empatan con 4 puntos y Faro va primero por orden alfabetico.
Hay 6 encuentros (4 con resultado, uno de ellos un empate 2-2 en futbol sala, 1 programado sin
resultado y 1 sin programar) y 2 exhibiciones (una programada y otra sin programar).
