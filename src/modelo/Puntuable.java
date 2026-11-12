package modelo;

// Contrato de la tabla de posiciones: lo cumple todo lo que recibe un resultado
// y reparte puntos de tabla. Solo lo cumplen los encuentros, no las exhibiciones.
public interface Puntuable {

    // Recibe el marcador y reparte los puntos de tabla a los dos equipos.
    void registrarResultado(int marcadorLocal, int marcadorVisitante);

    // Dice si ya tiene resultado y por eso no acepta otro.
    boolean estaFinalizado();
}
