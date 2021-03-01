package Cargador;

import Characters.*;
import Game.Arma;
import Game.Manhattan;
import Game.Sala;

import java.util.List;

/**
 * Clase creada para ser usada en la utilidad cargador
 * contiene el main del cargador. Se crea una instancia de la clase Estacion, una instancia de la clase Cargador
 * y se procesa el fichero de inicio, es decir, se leen todas las líneas y se van creando todas las instancias de la simulación
 *
 * @author Profesores DP
 * @version 5.0 -  27/10/2016
 */
public class Cargador {
    /**
     *
     * número de elementos distintos que tendrá la simulación - Mapa, Stark, Lannister, Baratheon, Targaryen
     */
    static final int NUMELTOSCONF = 6;
    //Longitud del vector de las cosas que me interesan
    /**
     * atributo para almacenar el mapeo de los distintos elementos
     */
    static private DatoMapeo[] mapeo;

    /**
     * constructor por defecto
     */
    Cargador() {
        mapeo = new DatoMapeo[NUMELTOSCONF];
        mapeo[0] = new DatoMapeo("MAP", 5);
        mapeo[1] = new DatoMapeo("SHPHYSICAL", 4);
        mapeo[2] = new DatoMapeo("SHEXTRASENSORIAL", 4);
        mapeo[3] = new DatoMapeo("SHFLIGHT", 4);
        mapeo[4] = new DatoMapeo("VILLAIN", 4);
        mapeo[5] = new DatoMapeo("SHWEAPONPHYSICAL",4); //creado para la defensa
        //Exactamente igual que en el fichero , tiene siempre 4 por ser personaje

    }

    /**
     * busca en mapeo el elemento leído del fichero inicio.txt y devuelve la posición en la que está
     *
     * @param elto elemento a buscar en el array
     * @return res posición en mapeo de dicho elemento
     */
    private int queElemento(String elto) {
        int res = -1;
        boolean enc = false;

        for (int i = 0; (i < NUMELTOSCONF && !enc); i++) {
            if (mapeo[i].getNombre().equals(elto)) {
                res = i;
                enc = true;
            }
        }
        return res;
    }

    /**
     * método que crea las distintas instancias de la simulación
     *
     * @param elto      nombre de la instancia que se pretende crear
     * @param numCampos número de atributos que tendrá la instancia
     * @param vCampos   array que contiene los valores de cada atributo de la instancia
     */
    public void crear(String elto, int numCampos, List<String> vCampos) {
        //Si existe elemento y el número de campos es correcto, procesarlo... si no, error
        int numElto = queElemento(elto);

        //Comprobación de datos básicos correctos
        if ((numElto != -1) && (mapeo[numElto].getCampos() == numCampos)) {
            //procesar
            switch (numElto) {
                case 0:
                    crearMap(numCampos, vCampos);
                    break;
                case 1:
                    crearSHPhysical(numCampos, vCampos);
                    break;
                case 2:
                    crearSHExtraSensorial(numCampos, vCampos);
                    break;
                case 3:
                    crearSHFlight(numCampos, vCampos);
                    break;
                case 4:
                    crearVillain(numCampos, vCampos);
                    break;
                case 5:
                   crearSHWeaponPhysical(numCampos,vCampos);
                    break;
            }
        } else
            System.out.println("ERROR Cargador::crear: Datos de configuración incorrectos... " + elto + "," + numCampos + "\n");
    }

    /**
     * método que crea una instancia de la clase Planta
     *
     * @param numCampos número de atributos que tendrá la instancia
     * @param vCampos   array que contiene los valores de cada atributo
     */
    private void crearMap(int numCampos, List<String> vCampos) {
        //System.out.println("Creado Map: " + vCampos.get(1) + "\n");
        //1:alto 2:ancho 3:dailyplanet 4:condicionApertura
        Manhattan mapa = Manhattan.getInstancia(Integer.parseInt(vCampos.get(3)), Integer.parseInt(vCampos.get(2)), Integer.parseInt(vCampos.get(1)), Integer.parseInt(vCampos.get(4)));
        mapa.añadirTexto(mapa.pintarMapa());
        HombrePuerta hombrePuerta = new HombrePuerta(Integer.parseInt(vCampos.get(4)));
        hombrePuerta.configurar();
        mapa.insertarHombrePuerta(hombrePuerta);
        Arma[] armasSalas = {new Arma("Mjolnir", 29), new Arma("Anillo", 1), new Arma("Garra", 27),
                new Arma("Armadura", 3), new Arma("Red", 25), new Arma("Escudo", 5),
                new Arma("Lucille", 23), new Arma("Lawgiver", 7), new Arma("GuanteInfinito", 21),
                new Arma("LazoVerdad", 9), new Arma("CadenaFuego", 19), new Arma("Capa", 11),
                new Arma("Flecha", 17), new Arma("Tridente", 13), new Arma("Antorcha", 15),
                new Arma("Baston", 28), new Arma("Latigo", 2), new Arma("MazaOro", 26),
                new Arma("CampoMagnetico", 4), new Arma("Tentaculo", 24),
                new Arma("CampoEnergia", 6), new Arma("Cetro", 22), new Arma("RayoEnergia", 8),
                new Arma("Laser", 20), new Arma("Bola", 10), new Arma("Espada", 18),
                new Arma("Sable", 12), new Arma("Acido", 16), new Arma("Gema", 14),
                new Arma("Nullifier", 23), new Arma("Mjolnir", 1), new Arma("Anillo", 29),
                new Arma("Garra", 3), new Arma("Armadura", 27), new Arma("Red", 5),
                new Arma("Escudo", 25), new Arma("Lucille", 7), new Arma("Lawgiver", 23),
                new Arma("GuanteInfinito", 9), new Arma("LazoVerdad", 21),
                new Arma("CadenaFuego", 11), new Arma("Capa", 19), new Arma("Flecha", 13),
                new Arma("Tridente", 17), new Arma("Antorcha", 28), new Arma("Baston", 15),
                new Arma("Latigo", 26), new Arma("MazaOro", 2), new Arma("CampoMagnetico", 24),
                new Arma("Tentaculo", 4), new Arma("CampoEnergia", 22), new Arma("Cetro", 6),
                new Arma("RayoEnergia", 20), new Arma("Laser", 8), new Arma("Bola", 18),
                new Arma("Espada", 10), new Arma("Sable", 16), new Arma("Acido", 12),
                new Arma("Gema", 1), new Arma("Nullifier", 3)};
        mapa.crearAtajos();
        mapa.profundidad();
        List<Sala> salasConArmas = mapa.ordenarSalasFrecuencia();
        //mapa.mostrarCaminos();
        mapa.distribuirArmas(salasConArmas, armasSalas);


    }
    private void crearSHWeaponPhysical(int numCampos, List<String> vCampos){
        Manhattan mapa = Manhattan.getInstancia();
        Personaje personaje = new SHWeaponPhysical(vCampos.get(1),vCampos.get(2).charAt(0),Integer.parseInt(vCampos.get(3)));
        personaje.generarRuta();
        mapa.añadirTexto(personaje.mensajeRutaPersonaje()); // para hacer esto debemos settear la ruta lo primero
        mapa.insertarPersonaje(personaje,0);



    }
    /**
     * método que crea una instancia de la clase SHPhysical
     *
     * @param numCampos número de atributos que tendrá la instancia
     * @param vCampos   array que contiene los valores de cada atributo
     */
    private void crearSHPhysical(int numCampos, List<String> vCampos) {
        //System.out.println("Creado SHPhysical: " + vCampos.get(1) + "\n");
        //Registrar SHPhysical en el mapa
        Manhattan mapa = Manhattan.getInstancia();
        Personaje personaje = new SHFísicos(vCampos.get(1), vCampos.get(2).charAt(0), Integer.parseInt(vCampos.get(3)));
        mapa.insertarPersonaje(personaje, 0);
        personaje.generarRuta();

        mapa.añadirTexto(personaje.mensajeRutaPersonaje());


    }

    /**
     * método que crea una instancia de la clase SHExtraSensorial
     *
     * @param numCampos número de atributos que tendrá la instancia
     * @param vCampos   array que contiene los valores de cada atributo
     */
    private void crearSHExtraSensorial(int numCampos, List<String> vCampos) {
        //System.out.println("Creado SHExtraSensorial: " + vCampos.get(1) + "\n");
        //Registrar SHExtraSensorial en el mapa
        Personaje personaje = new SHExtrasensorial(vCampos.get(1), vCampos.get(2).charAt(0), Integer.parseInt(vCampos.get(3)));
        Manhattan mapa = Manhattan.getInstancia();
        mapa.insertarPersonaje(personaje, 0);
        personaje.generarRuta();
        mapa.añadirTexto(personaje.mensajeRutaPersonaje());

    }

    /**
     * método que crea una instancia de la clase SHFlight
     *
     * @param numCampos número de atributos que tendrá la instancia
     * @param vCampos   array que contiene los valores de cada atributo
     */
    private void crearSHFlight(int numCampos, List<String> vCampos) {
        //System.out.println("Creado SHFlight: " + vCampos.get(1) + "\n");
        //Registrar SHFlight en el mapa
        Personaje personaje = new SHViajes(vCampos.get(1), vCampos.get(2).charAt(0), Integer.parseInt(vCampos.get(3)));
        Manhattan mapa = Manhattan.getInstancia();
        mapa.insertarPersonaje(personaje, mapa.devolverSalaSurOeste().getSala_id());
        personaje.generarRuta();
        mapa.añadirTexto(personaje.mensajeRutaPersonaje());
    }

    /**
     * método que crea una instancia de la clase Villain
     *
     * @param numCampos número de atributos que tendrá la instancia
     * @param vCampos   array que contiene los valores de cada atributo
     */
    private void crearVillain(int numCampos, List<String> vCampos) {
        //System.out.println("Creado Villain: " + vCampos.get(1) + "\n");
        //Registrar Villain en el mapa
        Personaje personaje = new Villano(vCampos.get(1), vCampos.get(2).charAt(0), Integer.parseInt(vCampos.get(3)));
        Manhattan mapa = Manhattan.getInstancia();
        Villano villano = (Villano) personaje;
        villano.setArmaUnica(null);
        mapa.insertarPersonaje(villano, mapa.devolverSalaNorEste().getSala_id());
        personaje.generarRuta();
        mapa.añadirTexto(personaje.mensajeRutaPersonaje());

    }

}
