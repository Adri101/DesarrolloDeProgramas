package Characters;

import Game.Manhattan;
import Game.Sala;

import java.util.LinkedList;
import java.util.List;

public class SHViajes extends SuperHéroe {
    /**
     * Constructor por defecto de los super heroes con poderes de viaje
     */
    public SHViajes() {
    }

    /**
     * Constructor parametrizado de los super heroes con poderes de viaje
     *
     * @param nombre
     * @param id
     */
    public SHViajes(String nombre, char id, int turno) {
        super(nombre, id, turno);

    }
    /**
     * Método especifico de interaccion con el Hpuerta del personaje de vuelo
     */

    /**
     * Método que genera la ruta de los personajes de tipo vuelo a través del camino más corto
     */
    @Override
    public void generarRuta() {
        Manhattan mapa = Manhattan.getInstancia();
        List<Sala> caminoSalas = new LinkedList<>();
        List<Integer> caminoID = mapa.grafoCaminos.path(this.getSalaDePersonaje().getSala_id(), mapa.getDailyPlanet());
        caminoID.add(0,this.getSalaDePersonaje().getSala_id());
        for (int i = 0; i < caminoID.size(); i++) {
            caminoSalas.add(mapa.devolverSalawNum(caminoID.get(i)));
        }
        this.setCaminoRuta(caminoSalas);
        this.setRuta(this.casteoRuta());
    }
}
