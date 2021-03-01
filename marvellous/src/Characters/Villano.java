package Characters;

import Game.Arma;
import Game.Manhattan;
import Game.Sala;

import java.util.LinkedList;
import java.util.List;

public class Villano extends Personaje {

    /**
     * Arma unica del personaje Villano
     */
    private Arma armaUnica;

    /**
     * Constructor parametrizado de la clase Villano
     *
     * @param _nombre
     * @param _ID
     */
    public Villano(String _nombre, char _ID, int turno) {
        super(_nombre, _ID,turno);
    }
    /**
     * Método que genera la ruta que deben de seguir los villano a través de la regla de la mano izquierda
     */
    @Override
    public void generarRuta(){
        List<Sala> camino = new LinkedList<>();
        camino.add(0,this.getSalaDePersonaje());
        Manhattan mapa = Manhattan.getInstancia();
        Sala salaActual = this.getSalaDePersonaje();
        int orientacion = 2;
        while (salaActual.getSala_id() != mapa.getDailyPlanet()) {
            switch (orientacion) {
                //norte
                case 0:
                    if (mapa.grafoCaminos.adyacente(salaActual.getSala_id(), salaActual.getSala_id() - 1)) {//no hay pared izquierda
                        orientacion = 3;
                        salaActual = mapa.devolverSalawNum(salaActual.getSala_id() - 1);
                        camino.add(salaActual);
                    } else { //hay pared derecha
                        if (mapa.grafoCaminos.adyacente(salaActual.getSala_id(), salaActual.getSala_id() - mapa.getAncho())) { //no hay pared arriba
                            salaActual = mapa.devolverSalawNum(salaActual.getSala_id() - mapa.getAncho());
                            camino.add(salaActual);
                        } else// hay pared arriba
                            orientacion = 1;
                    }
                    break;
                //este
                case 1:
                    if (mapa.grafoCaminos.adyacente(salaActual.getSala_id(), salaActual.getSala_id() - mapa.getAncho())) { // no hay pared izquierda
                        orientacion = 0;
                        salaActual = mapa.devolverSalawNum(salaActual.getSala_id() - mapa.getAncho());
                        camino.add(salaActual);
                    } else { // hay pared a la derecha
                        if (mapa.grafoCaminos.adyacente(salaActual.getSala_id(), salaActual.getSala_id() + 1)) {
                            salaActual = mapa.devolverSalawNum(salaActual.getSala_id() + 1);
                            camino.add(salaActual);
                        } else
                            orientacion = 2;
                    }
                    break;
                //sur
                case 2:
                    if (mapa.grafoCaminos.adyacente(salaActual.getSala_id(), salaActual.getSala_id() + 1)) { // no hay pared derecha
                        orientacion = 1;
                        salaActual = mapa.devolverSalawNum(salaActual.getSala_id() + 1);
                        camino.add(salaActual);
                    } else { // hay pared derecha
                        if (mapa.grafoCaminos.adyacente(salaActual.getSala_id(), salaActual.getSala_id() + mapa.getAncho())) {
                            salaActual = mapa.devolverSalawNum(salaActual.getSala_id() + mapa.getAncho());
                            camino.add(salaActual);
                        } else
                            orientacion = 3;
                    }
                    break;
                //oeste
                case 3:
                    if(mapa.grafoCaminos.adyacente(salaActual.getSala_id(),salaActual.getSala_id() + mapa.getAncho())){
                        orientacion = 2;
                        salaActual = mapa.devolverSalawNum(salaActual.getSala_id() + mapa.getAncho());
                        camino.add(salaActual);
                    }else{
                        if(mapa.grafoCaminos.adyacente(salaActual.getSala_id(),salaActual.getSala_id() - 1)){
                            salaActual = mapa.devolverSalawNum(salaActual.getSala_id() -1);
                            camino.add(salaActual);
                        }else
                            orientacion = 0;
                    }
                    break;

            }
        }
        this.setCaminoRuta(camino);
        this.setRuta(casteoRuta());
    }
    /**
     * Método que muestra por pantalla el arma única de cada villano concreto
     * @return
     */
    @Override
    public String mostrarArmas() {
        if(this.getArmaUnica() != null)
            return this.getArmaUnica().toString();
        return "";
    }

    /**
     * Método que permite recoger al villano un arma de una sala
     */
    @Override
    public void recogerArma() {
        Sala salaPersonaje = this.getSalaDePersonaje();
        if (!salaPersonaje.armas.isEmpty()) {
            if(this.getArmaUnica() == null)
                this.setArmaUnica(salaPersonaje.armas.remove(0));
            else if(salaPersonaje.armas.get(0).getPoder() > this.getArmaUnica().getPoder()) {
                salaPersonaje.armas.add(this.getArmaUnica());
                salaPersonaje.ordenarArmas();
                Arma armaNuevaVillano = salaPersonaje.armas.remove(0);
                this.setArmaUnica(armaNuevaVillano);
            }
        }
    }

    /**
     * Método que realiza el interaccionConHPuerta entre el villano y el Hombre Puerta
     *
     */
    @Override
    public boolean interaccionConHPuerta() {
        if(this.getSalaDePersonaje().getEstaHombrePuerta()) {
            if (this.getArmaUnica() != null) {
                HombrePuerta Haux = this.getSalaDePersonaje().getHPuerta();
                Arma armaHpuerta = Haux.cogerMayorArma();
                if (this.getArmaUnica().getPoder() >= armaHpuerta.getPoder())
                    Haux.armasHPuerta.borrar(armaHpuerta);

                return Haux.comprobarCondicionAperturaPuerta();
            }
        }
        return true;
    }

    /**
     * Método que realiza la interacción entre un villano y el primer superhéroe de su misma sala,
     * en el caso de que lo haya
     */
    @Override
    public void interaccionConPersonaje() {
        SuperHéroe superheroe;
        if((superheroe = devolverPrimerSuperheroe()) != null)
            if(this.getArmaUnica() != null)
                if(superheroe.armasPersonaje.pertenece(this.getArmaUnica())){
                    Arma armaSH = superheroe.buscarArma(this.getArmaUnica());
                    if(armaSH.getPoder() < this.getArmaUnica().getPoder())
                        superheroe.armasPersonaje.borrar(armaSH);
            }

    }
    /**
     * Método que devuelve el primer
     * @return
     */
    public SuperHéroe devolverPrimerSuperheroe(){
        Sala salaVillano = this.getSalaDePersonaje();
        return salaVillano.getPrimerSuperheroe();
    }

    /**
     * Método que devuelve el arma del villano
     *
     * @return
     */
    public Arma getArmaUnica() {
        return armaUnica;
    }

    /**
     * Método que permite modificar el arma del villano.
     *
     * @param armaUnica
     */
    public void setArmaUnica(Arma armaUnica) {
        this.armaUnica = armaUnica;
    }
}
