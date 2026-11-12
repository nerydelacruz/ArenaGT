package modelo;

import java.time.LocalDate;

// Guarda la fecha, la hora y la sede de un evento.
// Aqui se escribe una sola vez el criterio de orden del calendario.
public class Programacion implements Comparable<Programacion> {

    private LocalDate fecha;
    private int hora;
    private String sede;

    public Programacion(LocalDate fecha, int hora, String sede) {
        // Se valida todo antes de guardar, asi nunca queda una programacion a medias.
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede quedar vacia.");
        }
        if (hora < 0 || hora > 23) {
            throw new IllegalArgumentException("La hora debe estar entre 0 y 23.");
        }
        if (sede == null || sede.trim().equals("")) {
            throw new IllegalArgumentException("La sede no puede quedar vacia.");
        }

        this.fecha = fecha;
        this.hora = hora;
        this.sede = sede.trim();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getHora() {
        return hora;
    }

    public String getSede() {
        return sede;
    }

    // Dice si las dos programaciones caen el mismo dia y a la misma hora.
    public boolean coincideEnHorario(Programacion otra) {
        return fecha.equals(otra.getFecha()) && hora == otra.getHora();
    }

    // Dice si las dos programaciones usan la misma sede el mismo dia y a la misma hora.
    public boolean ocupaMismaSede(Programacion otra) {
        return coincideEnHorario(otra) && sede.equalsIgnoreCase(otra.getSede());
    }

    // Ordena por fecha, luego por hora y luego por el nombre de la sede.
    @Override
    public int compareTo(Programacion otra) {
        int resultado = fecha.compareTo(otra.getFecha());

        // Si es el mismo dia se desempata con la hora.
        if (resultado == 0) {
            resultado = hora - otra.getHora();
        }

        // Si tambien es la misma hora se desempata con el nombre de la sede.
        if (resultado == 0) {
            resultado = sede.compareToIgnoreCase(otra.getSede());
        }

        return resultado;
    }

    // Muestra la programacion asi: 2026-11-12 09:00 | Gimnasio Central
    @Override
    public String toString() {
        String horaTexto = "" + hora;
        if (hora < 10) {
            horaTexto = "0" + hora;
        }
        return fecha + " " + horaTexto + ":00 | " + sede;
    }
}
