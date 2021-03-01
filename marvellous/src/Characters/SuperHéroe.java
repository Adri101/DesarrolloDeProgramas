package Characters;

import Game.Arma;
import Game.Sala;
import LineFormat.formatLine;
import data_structures.Arbol;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public abstract class SuperHéroe extends Personaje {
    /**
     * Arbol dónde se almacenaran las armas de cada personaje.
     */
    public Arbol<Arma> armasPersonaje;


    /**
     * Constructor por defecto de la clase SuperHeroe
     */
    public SuperHéroe() {
        armasPersonaje = new Arbol<>();
        villanosCapturados = new LinkedList<>();
    }

    /**
     * Lista de villanos capturados que contiene cada personaje concreto
     */
    public List<Villano> villanosCapturados;

    /**
     * Constructor por defecto de la clase SuperHeroe
     *
     * @param nombre del superhéroe
     * @param _id    del superhéroe
     */
    public SuperHéroe(String nombre, char _id, int turno) {
        super(nombre, _id, turno);
        armasPersonaje = new Arbol<>();
        villanosCapturados = new LinkedList<>();
    }
    /**
     * método que muestra las armas de cada superhéroe concreto
     *
     * @return
     */
    @Override
    public String mostrarArmas() {
        String message = "";
        if (!this.armasPersonaje.vacio()) {
            List<Arma> armas = new LinkedList<>();
            this.armasPersonaje.inOrden(armas);

            for (int i = 0; i < armas.size(); i++) {
                message += armas.get(i);
            }
        }
        return message;
    }

    /**
     * Método que permite recoger al Superheroe un arma de una sala
     */
    @Override
    public void recogerArma() {
        Sala sala_person = this.getSalaDePersonaje();
        if (!sala_person.armas.isEmpty()) {
            Arma armaRecogida = sala_person.armas.get(0);
            if (this.armasPersonaje.pertenece(armaRecogida)) {
                Arma[] a1aux = new Arma[1];
                this.armasPersonaje.buscarPNom(armaRecogida, false, a1aux);
                a1aux[0].setPoder(a1aux[0].getPoder() + armaRecogida.getPoder());
            } else {
                this.armasPersonaje.insertar(armaRecogida);
            }
            sala_person.armas.remove(armaRecogida);
        }
    }

    /**
     * Método que añade un arma al árbol de armas del superhéroe
     *
     * @param _arma el cual se desea añadir
     */
    public void añadirArma(Arma _arma) {
        this.armasPersonaje.insertar(_arma);
    }

    /**
     * Método que devuelve el arma de mayor poder del superhéroe
     *
     * @return arma de mayor poder del SH
     */
    public Arma cogerArmaMayorPoder() {
        Arma[] b = new Arma[1];
        b[0] = new Arma("x", 0);
        Arma A1aux = null;
        this.buscarMayorArma(this.armasPersonaje, b);
        A1aux = b[0];
        return A1aux;
    }
    /**
     * Método que devuelve el arma de menor poder del superhéroe
     * @return arma de menos poder del SH
     */
    public Arma cogerArmaMenorPoder() {
    		Arma[] b = new Arma[1];
    		b[0] = new Arma("x",9999);
    		Arma A1aux = null;
    		this.buscarMenorArma(this.armasPersonaje, b);
    		A1aux = b[0];
    		return A1aux;
    }
    /**
     * Método a través el cual un superhéroe interactúa con otro personaje de la sala,
     * en este caso, con el primer Villano que encuentre.
     */
    @Override
    public void interaccionConPersonaje() {
        Villano villano;
        if ((villano = devolverPrimerVillano()) != null)
            if (villano.getArmaUnica() != null)
                if (this.armasPersonaje.pertenece(villano.getArmaUnica())) {
                    Arma armaSH = this.buscarArma(villano.getArmaUnica());
                    if (armaSH.getPoder() > villano.getArmaUnica().getPoder()) {
                    	   this.villanosCapturados.add(villano);
                    	   eliminarVillano(villano);
                    }
                     
                }

	    }
    /**
     * Método que elimina un villano dado por parámetro de la lista en donde lo captura el superhéroe
     * @param villano
     */
    public void eliminarVillano(Villano villano) {
    	Sala salaSH = this.getSalaDePersonaje();
    	salaSH.eliminarVillano(villano);
    }
    /**
     * Método que busca un arma especificada en el arbol de armas del superhéroe.
     *
     * @param armaVillano que se desea buscar
     * @return
     */
    public Arma buscarArma(Arma armaVillano) {
        Arma[] b = new Arma[1];
        b[0] = new Arma("x", 0);
        if (this.armasPersonaje.buscarPNom(armaVillano, false, b))
            return b[0];
        return null;
    }

    /**
     * Método que devuelve el primer villano que se encuentra en la sala del superhéroe
     *
     * @return TODO: hacerlo en un sólo método para sh y villano
     */
    public Villano devolverPrimerVillano() {
        Sala salaSH = this.getSalaDePersonaje();
        return salaSH.getPrimerVillano();
    }

    /**
     * Método que realiza la interaccion entre el SH y el HombrePuerta
     */
    public boolean interaccionConHPuerta() {
        if (this.getSalaDePersonaje().getEstaHombrePuerta()) {
            if (!this.armasPersonaje.vacio()) {
                HombrePuerta Haux = this.getSalaDePersonaje().getHPuerta();
                Arma armaMayorPoder = this.cogerArmaMayorPoder();
                if (Haux.armasHPuerta.pertenece(armaMayorPoder)) {
                    Arma armaHPuerta = Haux.buscarArma(armaMayorPoder);
                    this.combate(armaMayorPoder, armaHPuerta, Haux);
                }
                this.armasPersonaje.borrar(armaMayorPoder);

                return Haux.comprobarCondicionAperturaPuerta();
            }
        }
        return true;
    }

    /**
     * Método que simula el combate entre el SH y el HombrePuerta
     *
     * @param armaMayorPoder
     * @param armaHPuerta
     * @param Haux
     */
    public void combate(Arma armaMayorPoder, Arma armaHPuerta, HombrePuerta Haux) {
        if (armaMayorPoder.getPoder() > armaHPuerta.getPoder())
            Haux.armasHPuerta.borrar(armaHPuerta); 
        
    }

}
