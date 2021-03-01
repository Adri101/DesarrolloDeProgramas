package Characters;

import Game.Manhattan;
import Game.Sala;

import java.util.LinkedList;
import java.util.List;

public class SHFísicos extends SuperHéroe {
    /**
     * Constructor por defecto de la Clase Super Heroés Físicos
     */
    public SHFísicos() {
    }

    /**
     * Constructor por defecto de la Clase Super Heroés Físicos
     *
     * @param nombre
     * @param id
     */
    public SHFísicos(String nombre, char id, int turno) {
        super(nombre, id, turno);
    }

    /**
     * Método el cual genera la ruta del personaje de tipo físico
     */
    @Override
    public void generarRuta() {
        Manhattan mapa = Manhattan.getInstancia();
        List<Sala> visitados = new LinkedList<>();
        Sala salaInicio = mapa.devolverSalawNum(0);
        profundidadFisico(salaInicio, visitados);
        this.setRuta(casteoRuta());

    }


    /**
     * Método que calcula el camino que debe seguir el personaje físico
     *
     * @param s
     * @param visitados
     */
    public void profundidadFisico(Sala s, List<Sala> visitados) {
        if (!visitados.isEmpty() && visitados.get(visitados.size() - 1).getSala_id() == Manhattan.getInstancia().getDailyPlanet() && !this.isCaminoRutaOk()) {
            List<Sala> visitadosAux = new LinkedList<>();
            visitadosAux.addAll(visitados);
            this.setCaminoRuta(visitadosAux);
            this.setCaminoRutaOk(true);
        }
        List<Sala> ady;
        Sala saux;
        visitados.add(s);
        ady = s.devVecinos();
        while (!ady.isEmpty()) {
            saux = ady.remove(0);
            if (!visitados.contains(saux))
                profundidadFisico(saux, visitados);
        }
        visitados.remove(s);
    }
}
