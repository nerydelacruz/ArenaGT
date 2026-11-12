package modelo;

// Encuentro de baloncesto: no se permiten empates y reparte 2 o 1 puntos.
public class Baloncesto extends Encuentro {

    private static final int PUNTOS_VICTORIA = 2;
    private static final int PUNTOS_DERROTA = 1;

    public Baloncesto(Equipo local, Equipo visitante) {
        // Las validaciones de los equipos las hace Encuentro.
        super(local, visitante);
    }

    // En baloncesto un marcador empatado se rechaza.
    @Override
    protected boolean esMarcadorValido(int marcadorLocal, int marcadorVisitante) {
        return marcadorLocal != marcadorVisitante;
    }

    @Override
    protected int calcularPuntos(int marcadorPropio, int marcadorRival) {
        if (marcadorPropio > marcadorRival) {
            return PUNTOS_VICTORIA;
        } else {
            return PUNTOS_DERROTA;
        }
    }

    @Override
    public String getNombreDeporte() {
        return "Baloncesto";
    }
}
