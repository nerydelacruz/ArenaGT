package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

// Guarda los equipos y los eventos de la copa. Se puede recorrer con for-each
// y devuelve los eventos programados en orden cronologico.
public class Copa implements Iterable<Programable> {

    private String nombre;
    private ArrayList<Equipo> equipos;
    private ArrayList<Programable> eventos;
    private ArrayList<Encuentro> encuentros;

    public Copa(String nombre) {
        this.nombre = nombre;
        this.equipos = new ArrayList<Equipo>();
        this.eventos = new ArrayList<Programable>();
        this.encuentros = new ArrayList<Encuentro>();
    }

    public String getNombre() {
        return nombre;
    }

    // Agrega un equipo si su codigo no esta repetido.
    public void registrarEquipo(Equipo equipo) {
        if (buscarEquipo(equipo.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe un equipo con el codigo " + equipo.getCodigo() + ".");
        }
        equipos.add(equipo);
    }

    // Devuelve el equipo con ese codigo, o null si no existe.
    public Equipo buscarEquipo(String codigo) {
        for (Equipo equipo : equipos) {
            if (equipo.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return equipo;
            }
        }
        return null;
    }

    // El encuentro se guarda en las dos listas: eventos (calendario) y encuentros (resultados).
    public void agregarEncuentro(Encuentro encuentro) {
        eventos.add(encuentro);
        encuentros.add(encuentro);
    }

    // La exhibicion solo va al calendario porque no tiene resultado.
    public void agregarExhibicion(Exhibicion exhibicion) {
        eventos.add(exhibicion);
    }

    // Programa o reprograma el evento con el numero que se ve en listarEventos().
    // Si hay cualquier problema se rechaza sin cambiar nada.
    public void programarEvento(int indiceEvento, Programacion programacion) {
        if (indiceEvento < 1 || indiceEvento > eventos.size()) {
            throw new IllegalArgumentException("No existe el evento numero " + indiceEvento + ".");
        }

        Programable evento = eventos.get(indiceEvento - 1);

        if (!evento.puedeProgramarse()) {
            throw new IllegalArgumentException("Ese evento ya esta finalizado y no se puede reprogramar.");
        }
        if (haySedeOcupada(evento, programacion)) {
            throw new IllegalArgumentException("La sede " + programacion.getSede()
                    + " ya tiene otro evento en esa fecha y hora.");
        }
        if (hayConflictoEquipo(evento, programacion)) {
            throw new IllegalArgumentException("Uno de los equipos ya tiene otro encuentro en esa fecha y hora.");
        }

        evento.programar(programacion);
    }

    // Revisa si otro evento ya usa la misma sede en esa fecha y hora.
    private boolean haySedeOcupada(Programable evento, Programacion programacion) {
        for (Programable otro : eventos) {
            // Se ignora el mismo evento para poder reprogramarlo en su misma sede.
            if (otro != evento && otro.estaProgramado()
                    && otro.getProgramacion().ocupaMismaSede(programacion)) {
                return true;
            }
        }
        return false;
    }

    // Revisa si algun equipo del evento ya juega otro encuentro en esa fecha y hora.
    private boolean hayConflictoEquipo(Programable evento, Programacion programacion) {
        for (Programable otro : eventos) {
            if (otro != evento && otro.estaProgramado()
                    && otro.getProgramacion().coincideEnHorario(programacion)
                    && evento.comparteEquipoCon(otro)) {
                return true;
            }
        }
        return false;
    }

    // Registra el resultado del encuentro con el numero que se ve en listarEncuentros().
    public void registrarResultado(int indiceEncuentro, int marcadorLocal, int marcadorVisitante) {
        if (indiceEncuentro < 1 || indiceEncuentro > encuentros.size()) {
            throw new IllegalArgumentException("No existe el encuentro numero " + indiceEncuentro + ".");
        }

        // Se usa la interfaz Puntuable: no importa de que deporte sea.
        Puntuable encuentro = encuentros.get(indiceEncuentro - 1);
        encuentro.registrarResultado(marcadorLocal, marcadorVisitante);
    }

    // Devuelve una copia de los equipos ordenada con el compareTo de Equipo.
    public List<Equipo> obtenerTablaPosiciones() {
        ArrayList<Equipo> tabla = new ArrayList<Equipo>(equipos);
        Collections.sort(tabla);
        return tabla;
    }

    // Devuelve todos los eventos numerados para que el usuario escoja uno.
    public String listarEventos() {
        String texto = "";

        for (int i = 0; i < eventos.size(); i++) {
            Programable evento = eventos.get(i);
            texto = texto + (i + 1) + ". " + evento.getDescripcion();

            if (evento.estaProgramado()) {
                texto = texto + " | " + evento.getProgramacion();
            }
            texto = texto + "\n";
        }

        return texto;
    }

    // Devuelve solo los encuentros numerados para registrar resultados.
    public String listarEncuentros() {
        String texto = "";

        for (int i = 0; i < encuentros.size(); i++) {
            Encuentro encuentro = encuentros.get(i);
            texto = texto + (i + 1) + ". " + encuentro.getDescripcion();

            if (encuentro.estaProgramado()) {
                texto = texto + " | " + encuentro.getProgramacion();
            }
            texto = texto + "\n";
        }

        return texto;
    }

    // Arma una copia con los eventos que ya tienen fecha, la ordena y devuelve su iterador.
    // Asi quien recorre la copa no ve la lista interna ni repite el criterio de orden.
    @Override
    public Iterator<Programable> iterator() {
        ArrayList<Programable> calendario = new ArrayList<Programable>();

        for (Programable evento : eventos) {
            if (evento.estaProgramado()) {
                calendario.add(evento);
            }
        }

        Collections.sort(calendario);
        return calendario.iterator();
    }
}
