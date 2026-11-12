package modelo;

// Contrato del calendario: lo cumple todo lo que se puede programar y mostrar.
// Lo cumplen los encuentros y las exhibiciones.
public interface Programable extends Comparable<Programable> {

    // Devuelve la fecha, hora y sede del evento (null si no esta programado).
    Programacion getProgramacion();

    // Dice si el evento ya tiene fecha para salir en el calendario.
    boolean estaProgramado();

    // Dice si el evento todavia se puede programar o reprogramar.
    boolean puedeProgramarse();

    // Asigna o cambia la fecha, hora y sede del evento.
    void programar(Programacion programacion);

    // Texto que muestra el calendario sin saber que tipo de evento es.
    String getDescripcion();

    // Por defecto un evento no tiene equipos. Encuentro lo sobrescribe.
    default boolean involucraEquipo(Equipo equipo) {
        return false;
    }

    // Por defecto un evento no comparte equipos con otro. Encuentro lo sobrescribe.
    default boolean comparteEquipoCon(Programable otro) {
        return false;
    }

    // Para ordenar dos eventos se comparan sus programaciones.
    // Asi el criterio de orden solo esta escrito en Programacion.
    @Override
    default int compareTo(Programable otro) {
        return getProgramacion().compareTo(otro.getProgramacion());
    }
}
