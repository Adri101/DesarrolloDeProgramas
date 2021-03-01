package Characters;

import Game.Manhattan;
import Game.Sala;
import data_structures.Dir;

import java.util.LinkedList;
import java.util.List;

public class SHExtrasensorial extends SuperHéroe {
    /**
     * Constructor por defecto de los Super Heroés con poderes extrasensoriales
     */
    public SHExtrasensorial() {
    }

    /**
     * Constructor por parametrizado de los Super Heroés con poderes extrasensoriales
     *
     * @param nombre
     * @param id
     */
    public SHExtrasensorial(String nombre, char id, int turno) {
        super(nombre, id, turno);
    }
    /**
     * Método que genera la ruta del personaje a través de la regla de la mano derecha para los personajes de tipo extrasensorial
     */
    @Override
    public void generarRuta() {
        List<Sala> camino = new LinkedList<>();
        camino.add(0,this.getSalaDePersonaje());
        Manhattan mapa = Manhattan.getInstancia();
        Sala salaActual = this.getSalaDePersonaje();
        int orientacion = 2;
        while (salaActual.getSala_id() != mapa.getDailyPlanet()) {
           switch (orientacion) {
                //norte
                case 0:
                    if (mapa.grafoCaminos.adyacente(salaActual.getSala_id(), salaActual.getSala_id() + 1)) {//no hay pared derecha
                        orientacion = 1;
                        salaActual = mapa.devolverSalawNum(salaActual.getSala_id() + 1);
                        camino.add(salaActual);
                    } else { //hay pared derecha
                        if (mapa.grafoCaminos.adyacente(salaActual.getSala_id(), salaActual.getSala_id() - mapa.getAncho())) { //no hay pared arriba
                            salaActual = mapa.devolverSalawNum(salaActual.getSala_id() - mapa.getAncho());
                            camino.add(salaActual);
                        } else// hay pared arriba
                            orientacion = 3;
                    }
                    break;
                //este
                case 1:
                    if (mapa.grafoCaminos.adyacente(salaActual.getSala_id(), salaActual.getSala_id() + mapa.getAncho())) { // no hay pared derecha
                        orientacion = 2;
                        salaActual = mapa.devolverSalawNum(salaActual.getSala_id() + mapa.getAncho());
                        camino.add(salaActual);
                    } else { // hay pared a la derecha
                        if (mapa.grafoCaminos.adyacente(salaActual.getSala_id(), salaActual.getSala_id() + 1)) {
                            salaActual = mapa.devolverSalawNum(salaActual.getSala_id() + 1);
                            camino.add(salaActual);
                        } else
                            orientacion = 0;
                    }
                    break;
                //sur
                case 2:
                    if (mapa.grafoCaminos.adyacente(salaActual.getSala_id(), salaActual.getSala_id() - 1)) { // no hay pared derecha
                        orientacion = 3;
                        salaActual = mapa.devolverSalawNum(salaActual.getSala_id() - 1);
                        camino.add(salaActual);
                    } else { // hay pared derecha
                        if (mapa.grafoCaminos.adyacente(salaActual.getSala_id(), salaActual.getSala_id() + mapa.getAncho())) {
                            salaActual = mapa.devolverSalawNum(salaActual.getSala_id() + mapa.getAncho());
                            camino.add(salaActual);
                        } else
                            orientacion = 1;
                    }
                    break;
                //oeste
                case 3:
                    if(mapa.grafoCaminos.adyacente(salaActual.getSala_id(),salaActual.getSala_id() - mapa.getAncho())){
                        orientacion = 0;
                        salaActual = mapa.devolverSalawNum(salaActual.getSala_id() - mapa.getAncho());
                        camino.add(salaActual);
                    }else{
                        if(mapa.grafoCaminos.adyacente(salaActual.getSala_id(),salaActual.getSala_id() - 1)){
                            salaActual = mapa.devolverSalawNum(salaActual.getSala_id() -1);
                            camino.add(salaActual);
                        }else
                            orientacion = 2;
                    }
                    break;

            }
        }
        this.setCaminoRuta(camino);
        this.setRuta(casteoRuta());
    }
}
