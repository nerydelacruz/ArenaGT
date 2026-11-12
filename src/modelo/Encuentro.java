package modelo;

// Clase abstracta con todo lo que comparten los encuentros de cualquier deporte:
// los dos equipos, el marcador, el estado y la programacion.
// Lo que cambia segun el deporte queda en metodos abstractos.
public abstract class Encuentro implements Programable, Puntuable {

    private Equipo local;
    private Equipo visitante;
    private int marcadorLocal;
    private int marcadorVisitante;
    private String estado;
    private Programacion programacion;

    // Es protected porque solo lo llaman las subclases con super().
    protected Encuentro(Equipo local, Equipo visitante) {
        if (local == null || visitante == null) {
            throw new IllegalArgumentException("Los dos equipos deben existir.");
        }
        if (local == visitante) {
            throw new IllegalArgumentException("Un equipo no puede jugar contra si mismo.");
        }

        this.local = local;
        this.visitante = visitante;
        this.marcadorLocal = 0;
        this.marcadorVisitante = 0;
        // Todo encuentro nace sin programar y sin fecha.
        this.estado = "Sin programar";
        this.programacion = null;
    }

    public Equipo getLocal() {
        return local;
    }

    public Equipo getVisitante() {
        return visitante;
    }

    public int getMarcadorLocal() {
        return marcadorLocal;
    }

    public int getMarcadorVisitante() {
        return marcadorVisitante;
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

    // Se puede programar o reprogramar mientras no tenga resultado.
    @Override
    public boolean puedeProgramarse() {
        return !estaFinalizado();
    }

    @Override
    public void programar(Programacion programacion) {
        if (!puedeProgramarse()) {
            throw new IllegalArgumentException("Un encuentro finalizado no se puede reprogramar.");
        }
        this.programacion = programacion;
        this.estado = "Programado";
    }

    // Es final para que ningun deporte cambie los pasos. Primero valida todo
    // y solo si todo esta bien cambia el marcador, los puntos y el estado.
    @Override
    public final void registrarResultado(int marcadorLocal, int marcadorVisitante) {
        if (estado.equals("Finalizado")) {
            throw new IllegalArgumentException("El encuentro ya esta finalizado y no acepta otro resultado.");
        }
        if (!estado.equals("Programado")) {
            throw new IllegalArgumentException("El encuentro no esta programado, primero asignele fecha.");
        }
        if (marcadorLocal < 0 || marcadorVisitante < 0) {
            throw new IllegalArgumentException("El marcador no puede tener numeros negativos.");
        }
        // Cada deporte decide que marcador acepta.
        if (!esMarcadorValido(marcadorLocal, marcadorVisitante)) {
            throw new IllegalArgumentException("El marcador " + marcadorLocal + "-" + marcadorVisitante
                    + " no es valido en " + getNombreDeporte() + ".");
        }

        this.marcadorLocal = marcadorLocal;
        this.marcadorVisitante = marcadorVisitante;

        // Cada deporte decide cuantos puntos de tabla le tocan a cada equipo.
        local.sumarPuntos(calcularPuntos(marcadorLocal, marcadorVisitante));
        visitante.sumarPuntos(calcularPuntos(marcadorVisitante, marcadorLocal));

        this.estado = "Finalizado";
    }

    @Override
    public boolean estaFinalizado() {
        return estado.equals("Finalizado");
    }

    // Dice si el equipo es el local o el visitante de este encuentro.
    @Override
    public boolean involucraEquipo(Equipo equipo) {
        return local == equipo || visitante == equipo;
    }

    // Revisa si el otro evento tiene a alguno de nuestros dos equipos.
    // Si el otro es una exhibicion, su involucraEquipo() siempre da false.
    @Override
    public boolean comparteEquipoCon(Programable otro) {
        return otro.involucraEquipo(local) || otro.involucraEquipo(visitante);
    }

    // Muestra el deporte, los equipos y el marcador si ya se jugo.
    @Override
    public String getDescripcion() {
        String texto = getNombreDeporte() + ": " + local.getNombre() + " vs " + visitante.getNombre();

        if (estaFinalizado()) {
            texto = texto + " (" + marcadorLocal + " - " + marcadorVisitante + ")";
        }

        return texto + " [" + estado + "]";
    }

    // Cada deporte dice que marcador acepta.
    protected abstract boolean esMarcadorValido(int marcadorLocal, int marcadorVisitante);

    // Cada deporte dice cuantos puntos de tabla gana un equipo segun su marcador y el del rival.
    protected abstract int calcularPuntos(int marcadorPropio, int marcadorRival);

    // Cada deporte devuelve su nombre para mostrarlo.
    public abstract String getNombreDeporte();
}
