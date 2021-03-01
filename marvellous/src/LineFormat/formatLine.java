package LineFormat;

import Characters.HombrePuerta;
import Characters.Personaje;
import Characters.SuperHéroe;
import Characters.Villano;
import Game.Arma;
import Game.Manhattan;

import java.io.IOException;
import java.io.PrintWriter;

public class formatLine {
    static String message = "";
    /**
     * Método que imprime por pantalla una linea continua de guiones
     */
    public static void formatContinousLine() {
        System.out.println("--------------------------------------------------------------------------------------\n");
    }

    /**
     * Método que introduce un mensaje dentro de un recuadro
     *
     * @param message el cual se mostrará dentro del recuadro
     */
    public static void formatRectangular(String message) {
        System.out.println("┌────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf(message);
        System.out.println("└────────────────────────────────────────────────────────────────────────────────────┘\n");
    }

    public static void formatRectangularDiscont(String message) {
        System.out.println("┌ - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  ┐");
        System.out.printf(message);
        System.out.println("└ - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  ┘\n");
    }

    /**
     * Método que muestra por pantalla qué personaje va a realizar su acción.
     *
     * @param personaje
     */
    public static void accionPersonaje(Personaje personaje) {
        System.out.println(">>> Character <" + personaje.getNombre() + "> actions:");
    }

    /**
     * Método que muestra por pantalla el personaje que va a realizar sus acciones con el portal
     *
     * @param personaje
     */
    public static void portalAccionPersonaje(Personaje personaje) {
        System.out.println(">>> Portal action: " + personaje.getNombre());
    }

    /**
     * Método que muestra por pantalla el personaje que va a realizar sus acciones con la sala
     *
     * @param personaje
     */
    public static void squareAccionPersonaje(Personaje personaje) {
        System.out.println(">>> Square action: " + personaje.getNombre());
    }

    /**
     * Método que muestra por pantalla el personaje el cual ha abierto la puerta
     *
     * @param personaje
     */
    public static void portalAbierto(Personaje personaje) {
        System.out.println("Character: " + personaje.getNombre() + " opens the Doorman gate!!!!  He/she is the owner of the world");
    }

    /**
     * Método que muestra por pantalla el turno actual de la simulación
     *
     * @param turno
     */
    public static void newTurn(int turno) {
        System.out.println("New turn in simulation ---->>>" + turno + "\n");
    }

    /**
     * Método que muestra por pantalla el arma que usa el personaje
     *
     * @param personaje
     * @param arma
     */
    public static void characterUses(Object personaje, Arma arma) {
        String message = "";
        if (personaje instanceof SuperHéroe)
            message += "Superhero uses: Arma: " + arma.toString();
        if (personaje instanceof Villano)
            message += "Villain uses: Arma: " + arma.toString();
        if (personaje instanceof HombrePuerta)
            message += "Doorman uses: Arma: " + arma.toString();
        System.out.println(message);
    }

    /**
     * Método que muestra por pantalla que el Hombre Puerta no posee el arma en su configuración
     */
    public static void doormanDontHaveWeapon() {
        System.out.println("DoorMan does not have the weapon, no figth");
    }

    /**
     * Método que muestra por pantalla que el superhéroe elimina un arma de su configuración
     *
     * @param arma arma que elimina
     */
    public static void superheroLosesWeapon(Arma arma) {
        System.out.println("Superhero loses his weapon: Arma: " + arma.toString());
    }

    /**
     * Método que muestra por pantalla el arma con mayor poder que
     * se encuentra en la sala disponible para ser recogida
     *
     * @param arma
     */
    public static void weaponInSquare(Arma arma) {
        System.out.println("Weapon in square: Arma: " + arma.toString());
    }

    /**
     * Método que muestra por pantalla que no hay ningún arma en la sala
     */
    public static void noWeaponInSquare() {
        System.out.println("No weapon in square");
    }

    /**
     * Método que muestra por pantalla que el arma se ha eliminado de la sala
     */
    public static void eraseWeaponFromSquare() {
        System.out.println("Weapon is erased from the square");
    }

    /**
     * Método que muestra por pantalla que el superheroe ha incrementado el poder en una de sus armas
     * con el arma que ha recogido de la sala
     */
    public static void superHeroIncrementsPower() {
        System.out.println("Superhero picks up the weapon and increments power");
    }

    /**
     * Método que muestra por pantalla que el personaje ha recogido el arma de la sala
     */
    public static void characterTakesWeapon(Object personaje) {
        String message = "";
        if (personaje instanceof SuperHéroe)
            message += "Superhero picks up the weapon";
        if (personaje instanceof Villano)
            message += "Villain picks up the weapon";
        System.out.println(message);
    }

    /**
     * Método que muestra por pantalla que el superheroe/villano ha ganado el combate con
     * el Hombre Puerta
     *
     * @param personaje
     * @param arma      que pierde el Hombre Puerta de su configuración
     */
    public static void characterWins(Object personaje, Arma arma) {
        String message = "";
        if (personaje instanceof SuperHéroe)
            message += "Superhero wins and DoorMan loses his weapon: " + arma.toString();
        if (personaje instanceof Villano)
            message += "Villain wins and DoorMan loses his weapon: " + arma.toString();
        System.out.println(message);
    }

    /**
     * Método que muestra por pantalla que el superheroe ha perdido el combate con
     * el Hombre Puerta
     *
     * @param arma
     */
    public static void superheroLoses(Arma arma) {
        System.out.println("Superhero have lost with Doorman with the weapon" + arma.toString());
    }
    static public void escribirSimulacion(String message) {
        try {
            PrintWriter writer = new PrintWriter("record.log", "UTF-8");
            writer.println(message);
            writer.close();
        } catch (IOException e) {
            System.out.println("Se ha producido un error al escribir el fichero de salida...");
        }
    }
    /**
     * Método que muestra por pantalla los datos necesarios para cada turno de la simulación
     */
    public static String  mensajeSimulaciónTurno() {
        message = "";
        Manhattan mapa = Manhattan.getInstancia();
        HombrePuerta hpuerta = mapa.devolverSalawNum(mapa.getDailyPlanet()).getHPuerta();
        message += mapa.mostrarTurno();
        message += mapa.mostrarDailyPlanet();
        message += hpuerta.mostrarHombrePuerta();
        message += mapa.pintarMapa();
        message += mapa.mostrarArmasSala();
        message += mapa.mostrarPersonajesSala();
        message += mapa.mostrarTeseracto();
        return message;

    }
    /**
     * Método que muestra por pantalla que el Hombre Puerta ha ganado
     */
    public static void doormanWins() {
        System.out.println("DoorMan wins");
    }
}
