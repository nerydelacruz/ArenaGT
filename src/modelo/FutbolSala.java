package modelo;

// Encuentro de futbol sala: se permiten empates y reparte 3, 1 o 0 puntos.
public class FutbolSala extends Encuentro {

    private static final int PUNTOS_VICTORIA = 3;
    private static final int PUNTOS_EMPATE = 1;
    private static final int PUNTOS_DERROTA = 0;

    public FutbolSala(Equipo local, Equipo visitante) {
        // Las validaciones de los equipos las hace Encuentro.
        super(local, visitante);
    }

    // En futbol sala cualquier marcador es valido, incluso el empate.
    @Override
    protected boolean esMarcadorValido(int marcadorLocal, int marcadorVisitante) {
        return true;
    }

    @Override
    protected int calcularPuntos(int marcadorPropio, int marcadorRival) {
        if (marcadorPropio > marcadorRival) {
            return PUNTOS_VICTORIA;
        } else if (marcadorPropio == marcadorRival) {
            return PUNTOS_EMPATE;
        } else {
            return PUNTOS_DERROTA;
        }
    }

    @Override
    public String getNombreDeporte() {
        return "Futbol sala";
    }
}
