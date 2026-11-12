package controlador;

import modelo.Baloncesto;
import modelo.Copa;
import modelo.Encuentro;
import modelo.Equipo;
import modelo.Exhibicion;
import modelo.FutbolSala;
import modelo.Programable;
import modelo.Programacion;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

// Programa principal: carga los datos iniciales y muestra el menu de la copa.
public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static Copa copa;

    public static void main(String[] args) {
        copa = new Copa("Copa ArenaGT 2026");
        cargarDatosIniciales();

        System.out.println("=============================================");
        System.out.println("          " + copa.getNombre().toUpperCase());
        System.out.println("=============================================");
        System.out.println("Se cargaron los datos iniciales de la copa.");

        mostrarMenu();
        scanner.close();
    }

    // Crea los equipos, encuentros y exhibiciones con los que arranca el programa.
    private static void cargarDatosIniciales() {
        Equipo faro = new Equipo("FAR", "Faro", "Tigo");
        Equipo sol = new Equipo("SOL", "Sol", "Claro");
        Equipo luna = new Equipo("LUN", "Luna", "Tigo");
        Equipo rayo = new Equipo("RAY", "Rayo", "Claro");

        copa.registrarEquipo(faro);
        copa.registrarEquipo(sol);
        copa.registrarEquipo(luna);
        copa.registrarEquipo(rayo);

        // Primero se agregan los encuentros, asi su numero es el mismo
        // en la lista de eventos y en la lista de encuentros (1 al 6).
        copa.agregarEncuentro(new FutbolSala(faro, sol));    // 1
        copa.agregarEncuentro(new Baloncesto(sol, faro));    // 2
        copa.agregarEncuentro(new FutbolSala(luna, rayo));   // 3
        copa.agregarEncuentro(new FutbolSala(luna, sol));    // 4
        copa.agregarEncuentro(new Baloncesto(rayo, faro));   // 5
        copa.agregarEncuentro(new FutbolSala(sol, rayo));    // 6 (queda sin programar)

        // Despues las exhibiciones: son los eventos 7 y 8.
        copa.agregarExhibicion(new Exhibicion("Clinica de baloncesto"));      // 7
        copa.agregarExhibicion(new Exhibicion("Demostracion de freestyle"));  // 8 (queda sin programar)

        copa.programarEvento(1, new Programacion(LocalDate.of(2026, 11, 12), 9, "Gimnasio Central"));
        copa.programarEvento(2, new Programacion(LocalDate.of(2026, 11, 12), 14, "Gimnasio Central"));
        copa.programarEvento(3, new Programacion(LocalDate.of(2026, 11, 13), 10, "Gimnasio Central"));
        copa.programarEvento(4, new Programacion(LocalDate.of(2026, 11, 14), 16, "Cancha Norte"));
        copa.programarEvento(5, new Programacion(LocalDate.of(2026, 11, 20), 18, "Gimnasio Central"));
        // Misma fecha y hora que el evento 1 pero en otra sede: en el calendario
        // va primero porque "Cancha Norte" va antes que "Gimnasio Central".
        copa.programarEvento(7, new Programacion(LocalDate.of(2026, 11, 12), 9, "Cancha Norte"));

        // Resultados: Faro y Luna terminan con 4 puntos y se desempata por nombre.
        copa.registrarResultado(1, 3, 1);    // Faro 3 - Sol 0 (futbol sala)
        copa.registrarResultado(2, 78, 74);  // Sol 2 - Faro 1 (baloncesto)
        copa.registrarResultado(3, 3, 0);    // Luna 3 - Rayo 0 (futbol sala)
        copa.registrarResultado(4, 2, 2);    // Luna 1 - Sol 1 (empate en futbol sala)
    }

    // Muestra las opciones y llama a la operacion elegida hasta que el usuario salga.
    private static void mostrarMenu() {
        boolean seguir = true;

        while (seguir) {
            System.out.println("");
            System.out.println("------------------- MENU -------------------");
            System.out.println("1. Registrar equipo");
            System.out.println("2. Crear encuentro");
            System.out.println("3. Crear exhibicion");
            System.out.println("4. Programar o reprogramar evento");
            System.out.println("5. Registrar resultado de un encuentro");
            System.out.println("6. Mostrar tabla de posiciones");
            System.out.println("7. Mostrar calendario");
            System.out.println("8. Salir");

            int opcion = leerEntero("Elija una opcion:");

            if (opcion == 1) {
                registrarEquipo();
            } else if (opcion == 2) {
                crearEncuentro();
            } else if (opcion == 3) {
                crearExhibicion();
            } else if (opcion == 4) {
                programarEvento();
            } else if (opcion == 5) {
                registrarResultado();
            } else if (opcion == 6) {
                mostrarTablaPosiciones();
            } else if (opcion == 7) {
                mostrarCalendario();
            } else if (opcion == 8) {
                System.out.println("Gracias por usar el sistema de ArenaGT. Hasta pronto!");
                seguir = false;
            } else {
                System.out.println("Opcion invalida, intente de nuevo.");
            }
        }
    }

    // Pide los datos de un equipo y lo registra en la copa.
    private static void registrarEquipo() {
        System.out.println("");
        System.out.println("--- REGISTRAR EQUIPO ---");
        String codigo = leerTexto("Codigo:");
        String nombre = leerTexto("Nombre:");
        String empresa = leerTexto("Empresa:");

        try {
            Equipo equipo = new Equipo(codigo, nombre, empresa);
            copa.registrarEquipo(equipo);
            System.out.println("Equipo registrado: " + equipo.getNombre());
        } catch (IllegalArgumentException error) {
            System.out.println("No se registro el equipo: " + error.getMessage());
        }
    }

    // Pide el deporte y los dos equipos, y crea el encuentro.
    private static void crearEncuentro() {
        System.out.println("");
        System.out.println("--- CREAR ENCUENTRO ---");
        System.out.println("Equipos registrados:");
        for (Equipo equipo : copa.obtenerTablaPosiciones()) {
            System.out.println("   " + equipo.getCodigo() + " - " + equipo.getNombre());
        }

        System.out.println("Deportes: 1. Futbol sala   2. Baloncesto");
        int deporte = leerEntero("Deporte:");
        if (deporte != 1 && deporte != 2) {
            System.out.println("No se creo el encuentro: el deporte debe ser 1 o 2.");
            return;
        }

        Equipo local = copa.buscarEquipo(leerTexto("Codigo del equipo local:"));
        Equipo visitante = copa.buscarEquipo(leerTexto("Codigo del equipo visitante:"));

        if (local == null || visitante == null) {
            System.out.println("No se creo el encuentro: alguno de los codigos no existe.");
            return;
        }

        try {
            Encuentro encuentro = null;
            // Aqui si se escoge la clase, porque es el momento de crear el objeto.
            if (deporte == 1) {
                encuentro = new FutbolSala(local, visitante);
            } else {
                encuentro = new Baloncesto(local, visitante);
            }
            copa.agregarEncuentro(encuentro);
            System.out.println("Encuentro creado: " + encuentro.getDescripcion());
        } catch (IllegalArgumentException error) {
            System.out.println("No se creo el encuentro: " + error.getMessage());
        }
    }

    // Pide el titulo y agrega la exhibicion a la copa.
    private static void crearExhibicion() {
        System.out.println("");
        System.out.println("--- CREAR EXHIBICION ---");
        String titulo = leerTexto("Titulo:");

        try {
            Exhibicion exhibicion = new Exhibicion(titulo);
            copa.agregarExhibicion(exhibicion);
            System.out.println("Exhibicion creada: " + exhibicion.getDescripcion());
        } catch (IllegalArgumentException error) {
            System.out.println("No se creo la exhibicion: " + error.getMessage());
        }
    }

    // Pide el evento, la fecha, la hora y la sede, y lo programa o reprograma.
    private static void programarEvento() {
        System.out.println("");
        System.out.println("--- PROGRAMAR O REPROGRAMAR EVENTO ---");
        System.out.print(copa.listarEventos());

        int numero = leerEntero("Numero del evento:");
        LocalDate fecha = leerFecha("Fecha (aaaa-mm-dd):");
        int hora = leerEntero("Hora de inicio (0-23):");
        String sede = leerTexto("Sede:");

        try {
            Programacion programacion = new Programacion(fecha, hora, sede);
            copa.programarEvento(numero, programacion);
            System.out.println("Evento programado para " + programacion);
        } catch (IllegalArgumentException error) {
            System.out.println("No se programo el evento: " + error.getMessage());
        }
    }

    // Pide el encuentro y el marcador, y registra el resultado.
    private static void registrarResultado() {
        System.out.println("");
        System.out.println("--- REGISTRAR RESULTADO ---");
        System.out.print(copa.listarEncuentros());

        int numero = leerEntero("Numero del encuentro:");
        int marcadorLocal = leerEntero("Marcador del local:");
        int marcadorVisitante = leerEntero("Marcador del visitante:");

        try {
            copa.registrarResultado(numero, marcadorLocal, marcadorVisitante);
            System.out.println("Resultado registrado. La tabla ya fue actualizada.");
        } catch (IllegalArgumentException error) {
            System.out.println("No se registro el resultado: " + error.getMessage());
        }
    }

    // Imprime la tabla de posiciones ya ordenada.
    private static void mostrarTablaPosiciones() {
        System.out.println("");
        System.out.println("--------- TABLA DE POSICIONES ---------");

        List<Equipo> tabla = copa.obtenerTablaPosiciones();
        for (int i = 0; i < tabla.size(); i++) {
            System.out.println((i + 1) + ". " + tabla.get(i));
        }
    }

    // Recorre la copa con for-each: los eventos ya vienen en orden cronologico.
    private static void mostrarCalendario() {
        System.out.println("");
        System.out.println("-------------- CALENDARIO --------------");

        boolean hayEventos = false;
        for (Programable evento : copa) {
            System.out.println(evento.getProgramacion() + " | " + evento.getDescripcion());
            hayEventos = true;
        }

        if (!hayEventos) {
            System.out.println("Todavia no hay eventos programados.");
        }
    }

    // Pide un numero entero y repite hasta que el usuario escriba uno valido.
    private static int leerEntero(String mensaje) {
        int numero = 0;
        boolean valido = false;

        while (!valido) {
            System.out.print(mensaje + " ");
            String texto = scanner.nextLine();
            try {
                numero = Integer.parseInt(texto.trim());
                valido = true;
            } catch (NumberFormatException error) {
                System.out.println("   Debe escribir un numero entero. Intente de nuevo.");
            }
        }

        return numero;
    }

    // Pide un texto y repite si el usuario lo deja vacio.
    private static String leerTexto(String mensaje) {
        String texto = "";

        while (texto.equals("")) {
            System.out.print(mensaje + " ");
            texto = scanner.nextLine().trim();
            if (texto.equals("")) {
                System.out.println("   Este dato no puede quedar vacio. Intente de nuevo.");
            }
        }

        return texto;
    }

    // Pide una fecha con formato aaaa-mm-dd y repite si el formato esta mal.
    private static LocalDate leerFecha(String mensaje) {
        LocalDate fecha = null;

        while (fecha == null) {
            System.out.print(mensaje + " ");
            String texto = scanner.nextLine().trim();
            try {
                fecha = LocalDate.parse(texto);
            } catch (DateTimeParseException error) {
                System.out.println("   Fecha invalida, use el formato aaaa-mm-dd (ej. 2026-11-12).");
            }
        }

        return fecha;
    }
}
