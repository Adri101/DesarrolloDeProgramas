package Characters;

import Game.Arma;
import Game.Sala;
import data_structures.Arbol;
import LineFormat.formatLine;

import java.util.LinkedList;
import java.util.List;

public class HombrePuerta {
    /**
     * Estado de la configuración de armas del hombre puerta.
     */
    private boolean estado; //true means that the doorMan is closed, and false means it is opened.
    /**
     * Árbol en donde se almacenan las armas del hombre puerta.
     */
    public Arbol<Arma> armasHPuerta;
    /**
     * Altura de apertura de la configuración del Hombre puerta
     */
    private int alturaApertura;
    /**
     * Sala en la que se encuentra el hombre puerta
     */
    private Sala salaHombrePuerta;
    /**
     * Constructor por defecto de la clase Hombre puerta.
     */
    public HombrePuerta() {
        this.setEstado(false);
        armasHPuerta = new Arbol<>();
        this.setAlturaApertura(99999);
    }

    /**
     * Constructor parametrizado de la clase Hombre Puerta.
     * @param alturaApertura
     */
    public HombrePuerta(int alturaApertura){
        this.setEstado(false);
        armasHPuerta = new Arbol<>();
        this.setAlturaApertura(alturaApertura);

    }

    /**
     * Método que establece la configuración de armas en el arbol del hombre puerta
     *
     */
    public void configurar() {
        Arma[] armas = {new Arma("CampoEnergia", 5), new Arma("Armadura", 13), new
                Arma("Anillo", 11), new Arma("Acido", 1), new Arma("Antorcha", 5), new
                Arma("Bola", 3), new Arma("Baston", 22), new Arma("CadenaFuego", 11), new
                Arma("Espada", 11), new Arma("Cetro", 20), new Arma("Capa", 10), new
                Arma("CampoMagnetico", 5), new Arma("Escudo", 3), new Arma("Garra", 22), new
                Arma("Flecha", 12), new Arma("Gema", 4)};
        for (int i = 0; i < armas.length; i++) {
            this.armasHPuerta.insertar(armas[i]);
        }
        this.cerrar();
    }

    /**
     * Método que muestra los atributos esenciales de la salida por pantalla del Hombre puerta.
     */
    public String mostrarHombrePuerta(){
        String estadoPuerta = "closed";
        String message;
        if(!this.getEstado())
            estadoPuerta = "open";
        message = "(doorman:"+estadoPuerta+":"+this.alturaApertura+":";
        message += mostrarArmasConfiguracion()+")";

        System.out.println(message);
        return message+"\n";

    }

    /**
     * Método que devuelve las armas de la configuración del hombre puerta
     * @return
     */
    public String mostrarArmasConfiguracion(){
        List<Arma> armas = new LinkedList<>();
        this.armasHPuerta.inOrden(armas);
        String message = "";
        for (int i = 0; i < armas.size(); i++) {
            message += armas.get(i);
        }
        return message;
    }
    /**
     * comprueba si el portal se ha abierto o no
     */
    public boolean comprobarCondicionAperturaPuerta() {
        int profundidadHombrePuerta = this.armasHPuerta.profundidad(this.armasHPuerta);
        if (!this.armasHPuerta.vacio() && profundidadHombrePuerta < this.alturaApertura) {
            this.abrir();
        }
        return this.getEstado();
    }

    /**
     * Método que busca el arma introducida por parametro en el árbol del HombrePuerta
     * @param armaSH
     */
    public Arma buscarArma(Arma armaSH){
        Arma[] A2aux = new Arma[1];
        this.armasHPuerta.buscarPNom(armaSH, false, A2aux);
    return A2aux[0];
    }

    /**
     * Método que coge el arma de mayor poder del personaje mostrándolo por pantalla
     * @return arma de mayor poder del Hombre Puerta
     */
    public Arma cogerMayorArma(){
        Arma[] b = new Arma[1];
        b[0] = new Arma("x", 0);
        Arma A1aux = null;
        if(!this.armasHPuerta.vacio()){
            this.buscarMayorArma(this.armasHPuerta,b);
            A1aux = b[0];

        }
        return A1aux;
    }

    /**
     * Método que devuelve el mayor arma que tenga el Hombre Puerta en su poder
     *
     * @param abb
     * @param b
     */
    public void buscarMayorArma(Arbol<Arma> abb, Arma[] b){
        Arbol<Arma> aux = null;
        if (!abb.vacio()) {
            if ((aux = abb.getHijoIzq()) != null) {
                this.buscarMayorArma(aux,b);
            }
            if (b[0].getPoder() < abb.getRaiz().getPoder())
                b[0] = abb.getRaiz();

            if ((aux = abb.getHijoDer()) != null) {
                this.buscarMayorArma(aux,b);
            }
        }
    }
    /**
     * Método que abre el portal del hombre puerta
     */
    public void abrir() {
        this.setEstado(false);
    }

    /**
     * Método que permite cerrar el portal del hombre puerta
     */
    public void cerrar() {
        this.setEstado(true);
    }

    /**
     * Método que devuelve el estado de la configuración del hombre puerta
     *
     * @return
     */
    public boolean getEstado() {
        return estado;
    }

    /**
     * Método que permite modificar el estado de la configuración del hombre puerta
     *
     * @param estado
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    /**
     * Método que devuelve la sala en la que se encuentra el hombre puerta.
     * @return Sala del hombre puerta
     */
    public Sala getSalaHombrePuerta() {
        return salaHombrePuerta;
    }

    /**
     * Método que permite modificar la sala en donde se encuentra el hombre puerta
     * @param salaHombrePuerta del hombre puerta
     */
    public void setSalaHombrePuerta(Sala salaHombrePuerta) {
        this.salaHombrePuerta = salaHombrePuerta;
    }

    /**
     * Método que devuelve la altura de apertura de la configuración del Hombre puerta
     * @return
     */
    public int getAlturaApertura() {
        return alturaApertura;
    }

    /**
     * Método que permite modificar la altura de apertura de la configuración del Hombre puerta
     * @param alturaApertura
     */
    public void setAlturaApertura(int alturaApertura) {
        this.alturaApertura = alturaApertura;
    }
}
