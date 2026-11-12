package modelo;

// Evento que sale en el calendario pero no tiene equipos ni resultado.
// Solo cumple la interfaz Programable, no la de la tabla.
public class Exhibicion implements Programable {

    private String titulo;
    private String estado;
    private Programacion programacion;

    public Exhibicion(String titulo) {
        if (titulo == null || titulo.trim().equals("")) {
            throw new IllegalArgumentException("El titulo de la exhibicion no puede quedar vacio.");
        }

        this.titulo = titulo.trim();
        // Toda exhibicion nace sin programar.
        this.estado = "Sin programar";
        this.programacion = null;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public Programacion getProgramacion() {
        return programacion;
    }

    @Override
    public boolean estaProgramado() {
        return programacion != null;
    }

    // Como no tiene resultado, una exhibicion siempre se puede reprogramar.
    @Override
    public boolean puedeProgramarse() {
        return true;
    }

    @Override
    public void programar(Programacion programacion) {
        this.programacion = programacion;
        this.estado = "Programada";
    }

    @Override
    public String getDescripcion() {
        return "Exhibicion: " + titulo + " [" + estado + "]";
    }
}
