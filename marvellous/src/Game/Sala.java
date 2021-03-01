package Game;

import Characters.HombrePuerta;
import Characters.Personaje;
import Characters.SuperHéroe;
import Characters.Villano;

import java.util.*;

public class Sala {
    /**
     * Número que identifica a una sala en concreto
     */
    private int sala_id;
    /**
     * Coordenada 'x' de la posición concreta de la sala
     */
    private int dimX;
    /**
     * Coordenada 'y' de la posición concreta de la sala
     */
    private int dimY;
    /**
     * Lista que almacena las armas que hay en la sala
     */
    public List<Arma> armas;
    /**
     * Lista que almacena los personajes que hay en una determinada sala.
     */
    public List<Personaje> personajes;
    /**
     * Hombre puerta que se encuentra en una sala determinada.
     */
    public HombrePuerta HPuerta;
    /**
     * atributo que indica si en esta sala se encuentra el hombre puerta o no
     */
    private boolean estaHombrePuerta;
    /**
     * atributo que indica la marca de la sala
     */
    private int marcaSala;
    /**
     * atributo que indica la frecuencia en la que aparece la sala en los caminos
     */
    private int frecuenciaCamino;

    /**
     * Constructor por defecto de la clase Game.Sala
     */
    public Sala() {
        this.setSala_id(999);
        this.armas = new LinkedList<>();
        this.personajes = new LinkedList<>();
        this.setEstaHombrePuerta(false);
        this.marcaSala = 999;
    }

    /**
     * Constructor parametrizado de la clase Game.Sala
     *
     * @param _salaID el cual queremos establecer a la nueva Game.Sala.
     */
    public Sala(int _salaID, int _dimX, int _dimY) {
        this.setDimX(_dimX);
        this.setDimY(_dimY);
        this.setSala_id(_salaID);
        this.armas = new LinkedList<>();
        this.personajes = new LinkedList<>();
        this.setEstaHombrePuerta(false);
        this.marcaSala = _salaID;
    }

    /**
     * Método que ordena la lista de armas según su poder (de más alto a mas bajo)
     */
    public void ordenarArmas() {
        Collections.sort(armas,new sortArma());
    }

    /**
     * Comparador externo usado para ordenar adecuadamente las armas que
     * se encuentran distribuidas en las salas
     */
    class sortArma implements Comparator<Arma>{
        @Override
        public int compare(Arma o1, Arma o2){
            if(o1.getPoder() > o2.getPoder())
                return -1;
            if(o1.getPoder() < o2.getPoder())
                return 1;

        return 0;
        }

    }
    /**
     * Método que muestra por pantalla las armas de la sala concreta.
     */
    public String showArmas() {
        if (!this.armas.isEmpty()) {
            String message = "(square:" + this.getSala_id() + ":";
            for (int i = 0; i < armas.size(); i++) {
                message += armas.get(i).toString();
            }
            message += ")";
            System.out.println(message);
            return message +"\n";
        }
    return "";
    }

    /**
     * Método que realiza la simulación de cada sala concreta
     *
     * @param turnoMapa procedente del atributo turno de la clase mapa
     * @return
     */
    public boolean simulacion(int turnoMapa) {
        Manhattan mapa = Manhattan.getInstancia();
        boolean portalAbierto = true;
        int indice = 0;
        while (indice < personajes.size()) {
            if (!personajes.isEmpty()) {
                Personaje personaje = this.personajes.remove(0);
                if (personaje.getTurnoPersonaje() == turnoMapa) {
                    portalAbierto = personaje.acciones();
                    if (!portalAbierto) {
                        personaje.setSalaDePersonaje(mapa.getTeseracto());
                        mapa.getTeseracto().insertarPersonajeTeseracto(personaje);
                        personaje.setSeHaMovido(true);
                    }
                }
                if (!personaje.isSeHaMovido()) {
                    indice++;
                    personajes.add(personaje);
                } else 
                    personaje.setSeHaMovido(false);

            }
        }
        return portalAbierto;
    }

    /**
     * Método que muestra los personajes de una sala, ESPECÍFICO para PINTAR MAPA
     */
    public String mostrarPersonajes() {
        if (this.personajes.isEmpty())
            return " ";
        if (this.personajes.size() > 1)
            return String.valueOf(this.personajes.size());
        else
            return String.valueOf(this.personajes.get(0).getID());
    }

    public String  mostrarPersonajesSala() {
        if (!personajes.isEmpty()) {
            String message = "";
            for (int i = 0; i < personajes.size(); i++) {
                message += personajes.get(i).mostrarPersonaje();
            }
            return message;
        }
    return "";
    }

    /**
     * Método que introduce a un personaje en el teseracto
     *
     * @param p
     */
    public void insertarPersonajeTeseracto(Personaje p) {
        this.personajes.add(p);
    }

    /**
     * Método que devuelve los vecinos de una sala ordenados por las coordenadas especificadas
     *
     * @return
     */
    public List<Sala> devVecinosNoAcc() {
        List<Sala> vecinosNoAcc = this.getVecinosNoAcc(devVecinos());
        return vecinosNoAcc;
    }

    /**
     * Método que devuelve los vecinos accesibles de una sala concreta
     *
     * @return lista con las salas vecinas
     */
    public List<Sala> devVecinos() {
        SortedSet<Integer> vecinos = new TreeSet<>();
        List<Sala> vecinosList = new LinkedList<>();
        Manhattan mapa = Manhattan.getInstancia();
        mapa.grafoCaminos.adyacentes(this.getSala_id(), vecinos);
        while (!vecinos.isEmpty()) {
            vecinosList.add(mapa.devolverSalawNum(vecinos.first()));
            vecinos.remove(vecinos.first());
        }
        return vecinosList;
    }
    /**
     * Método que devuelve el poder de una sala
     */
    public int poderSala(){
        int poderTotal =0;
        if(this.armas.size()!=0){
            for (int i = 0; i <armas.size() ; i++) {
                poderTotal += armas.get(i).getPoder();
            }
        }
        return poderTotal;
    }
    /**
     * Método que devuelve los vecinos no accesibles de una sala concreta
     *
     * @param vecinosList
     * @return lista de vecinos no accesibles
     */
    public List<Sala> getVecinosNoAcc(List<Sala> vecinosList) {
        List<Sala> vecinosNoAcc = new LinkedList<>();
        Manhattan mapa = Manhattan.getInstancia();
        int x = this.getSala_id() / mapa.getAncho();
        int y = this.getSala_id() % mapa.getAncho();
        if (x > 0)
            if (!vecinosList.contains(mapa.devolverSalawNum(this.getSala_id() - mapa.getAncho())))//sala norte
                vecinosNoAcc.add(mapa.devolverSalawNum(this.getSala_id() - mapa.getAncho()));
        if (x < mapa.getAlto() - 1)
            if (!vecinosList.contains(mapa.devolverSalawNum(this.getSala_id() + mapa.getAncho())))//sala sur
                vecinosNoAcc.add(mapa.devolverSalawNum(this.getSala_id() + mapa.getAncho()));
        if (y > 0)
            if (!vecinosList.contains(mapa.devolverSalawNum(this.getSala_id() - 1)))//sala oeste
                vecinosNoAcc.add(mapa.devolverSalawNum(this.getSala_id() - 1));
        if (y < mapa.getAncho() - 1)
            if (!vecinosList.contains(mapa.devolverSalawNum(this.getSala_id() + 1)))//sala este
                vecinosNoAcc.add(mapa.devolverSalawNum(this.getSala_id() + 1));
        return vecinosNoAcc;
    }

    /**
     * Método que devuelve el id de una sala
     *
     * @return id de la sala
     */
    public int getSala_id() {
        return sala_id;
    }

    /**
     * Método que permite modificar el id de una sala
     *
     * @param sala_id
     */
    public void setSala_id(int sala_id) {
        this.sala_id = sala_id;
    }

    /**
     * Método que devuelve el hombre puerta de la sala correspondiente.
     *
     * @return instancia del hombre puerta.
     */
    public HombrePuerta getHPuerta() {
        return this.HPuerta;
    }

    /**
     * Método que devuelve el primer Villano de la sala en cuestión
     *
     * @return primer villano
     */
    public Villano getPrimerVillano() {
        for (int i = 0; i < this.personajes.size(); i++) {
            if (this.personajes.get(i) instanceof Villano)
                return (Villano) this.personajes.get(i);
        }
        return null;
    }
    /**
     * Método que dado un villano, lo elimina de la lista de personajes de la sala
     * @param villano
     */
    public void eliminarVillano(Villano villano) {
    		this.personajes.remove(this.personajes.indexOf(villano));
    }
    /**
     * Método que devuelve el primer Superheroe de la sala en cuestión
     *
     * @return primer superhéroe
     */
    public SuperHéroe getPrimerSuperheroe() {
        for (int i = 0; i < this.personajes.size(); i++) {
            if (this.personajes.get(i) instanceof SuperHéroe)
                return (SuperHéroe) this.personajes.get(i);
        }
        return null;
    }

    /**
     * Método que permite modificar el hombre puerta de una sala determinada.
     *
     * @param H
     */
    public void setHPuerta(HombrePuerta H) {
        this.HPuerta = H;
    }

    /**
     * Método que devuelve el primer personaje de la lista de la sala determinada.
     *
     * @return
     */
    public Personaje devolverYBorrarPersonaje(Personaje p) {
        return this.personajes.remove(this.personajes.indexOf(p));
    }

    /**
     * Método que devuelve la confirmación de si está o no el hombre puerta en la sala determinada
     *
     * @return
     */
    public boolean getEstaHombrePuerta() {
        return estaHombrePuerta;
    }

    /**
     * Método que permite modificar si está o no el hombre puerta en la sala correspondiente
     *
     * @param estaHombrePuerta
     */
    public void setEstaHombrePuerta(boolean estaHombrePuerta) {
        this.estaHombrePuerta = estaHombrePuerta;
    }

    /**
     * Método que devuelve la marca de la sala correspondiente
     *
     * @return atributo marca de la sala
     */
    public int getMarcaSala() {
        return marcaSala;
    }

    /**
     * Método que permite modificar la marca de la sala
     *
     * @param marcaSala
     */
    public void setMarcaSala(int marcaSala) {
        this.marcaSala = marcaSala;
    }

    /**
     * Método que devuelve la coordenada X de la sala concreta
     *
     * @return
     */
    public int getDimX() {
        return dimX;
    }

    /**
     * Método que permite modificar la coordenada x de la sala concreta
     *
     * @param dimX
     */
    public void setDimX(int dimX) {
        this.dimX = dimX;
    }

    /**
     * Método que devuelve la coordenada Y de la sala concreta
     *
     * @return
     */
    public int getDimY() {
        return dimY;
    }

    /**
     * Método que permite modificar la coordenada Y de la sala concreta
     *
     * @param dimY
     */
    public void setDimY(int dimY) {
        this.dimY = dimY;
    }

    /**
     * Método que devuelve la frecuencia de una sala concreta
     *
     * @return
     */
    public int getFrecuenciaCamino() {
        return frecuenciaCamino;
    }

    /**
     * Método que permite modificar la frecuencia de una sala concreta
     *
     * @param frecuenciaCamino
     */
    public void setFrecuenciaCamino(int frecuenciaCamino) {
        this.frecuenciaCamino = frecuenciaCamino;
    }

    /**
     * Método que incrementa en 1 la frecuencia de una sala concreta
     */
    public void incrementarFrecuencia() {
        this.frecuenciaCamino++;
    }
}