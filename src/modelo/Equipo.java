package modelo;

// Guarda un equipo de la copa con su codigo, nombre, empresa y puntos de tabla.
public class Equipo implements Comparable<Equipo> {

    private String codigo;
    private String nombre;
    private String empresa;
    private int puntosTabla;

    public Equipo(String codigo, String nombre, String empresa) {
        if (codigo == null || codigo.trim().equals("")) {
            throw new IllegalArgumentException("El codigo del equipo no puede quedar vacio.");
        }
        if (nombre == null || nombre.trim().equals("")) {
            throw new IllegalArgumentException("El nombre del equipo no puede quedar vacio.");
        }
        if (empresa == null || empresa.trim().equals("")) {
            throw new IllegalArgumentException("La empresa del equipo no puede quedar vacia.");
        }

        this.codigo = codigo.trim();
        this.nombre = nombre.trim();
        this.empresa = empresa.trim();
        // Todo equipo empieza la copa sin puntos.
        this.puntosTabla = 0;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmpresa() {
        return empresa;
    }

    public int getPuntosTabla() {
        return puntosTabla;
    }

    // No es public: solo los encuentros (mismo paquete) pueden sumar puntos.
    void sumarPuntos(int puntos) {
        this.puntosTabla = this.puntosTabla + puntos;
    }

    // Ordena de mas puntos a menos puntos. Si empatan, por nombre en orden alfabetico.
    @Override
    public int compareTo(Equipo otro) {
        if (puntosTabla != otro.getPuntosTabla()) {
            // Se resta al reves para que el de mas puntos quede primero.
            return otro.getPuntosTabla() - puntosTabla;
        }
        return nombre.compareToIgnoreCase(otro.getNombre());
    }

    // Muestra la fila del equipo en la tabla de posiciones.
    @Override
    public String toString() {
        return nombre + " [" + codigo + "] - " + empresa + " | " + puntosTabla + " pts";
    }
}
