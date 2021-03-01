package Characters;

import Game.Arma;
import Game.Manhattan;
import Game.Sala;
import data_structures.Arbol;
import data_structures.Dir;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public abstract class Personaje {
	/**
	 * Id que identifica al personaje en el mapa.
	 */
	private char ID;
	/**
	 * Nombre que identifica al personaje.
	 */
	private String nombre;
	/**
	 * Instancia de la sala en la que se encuentra el personaje
	 */
	private Sala salaDePersonaje;
	/**
	 * Ruta de cada personaje concreto para la simulación
	 */
	private List<Dir> ruta;
	/**
	 * Caminos que sigue cada personaje para la simulación
	 */
	private List<Sala> caminoRuta;
	/**
	 * Variable booleana que indica si se ha movido de una sala a otra
	 */
	private boolean seHaMovido = false;
	/**
	 * Variable turno de cada personaje concreto
	 */
	private int turnoPersonaje;
	/**
	 * Variable que indica el turno de inicio de un personaje concreto
	 */
	private int turnoInicio;
	/**
	 * Variable booleana que indica
	 */
	private boolean caminoRutaOk = false;
	/**
	 * Variable booleana que indica si en una sala concreta el superhéroe ha
	 * capturado un villano
	 */
	public boolean villanoCapturado = false;

	/**
	 * Constructor por defecto de la clase Characters.Personaje
	 */
	public Personaje() {
		try {
			this.setID('X');
			this.setNombre("Default X");
			this.setTurnoPersonaje(0);
			this.ruta = new LinkedList<>();
		} catch (IOException e) {
			System.err.println("Algún paramentro que ha introducido por pantalla es incorrecto "
					+ "de la clase Personaje: " + e.getMessage());
		}

	}

	/**
	 * Constructor parametrizado de la clase Characters.Personaje
	 *
	 * @param _nombre
	 *            del personajex
	 * @param _id
	 *            del personaje
	 * @param _turno
	 *            del personaje
	 */
	public Personaje(String _nombre, char _id, int _turno) {

		try {
			this.setNombre(_nombre);
			this.setID(_id);
			this.setTurnoPersonaje(_turno);
			this.setTurnoInicio(_turno);
			this.ruta = new LinkedList<>();
		} catch (IOException e) {
			System.err.println("Algún parametro introducido por pantalla de la clase Personaje " + "es incorrecto:"
					+ e.getMessage());
		}
	}

	/**
	 * Método que realiza las acciones correspondientes de cada personaje
	 */
	public boolean acciones() {
		boolean portalAbierto = this.interaccionConHPuerta();
		this.movimiento();
		this.recogerArma();
		this.interaccionConPersonaje();
		this.incrementarTurnoPersonaje();
		return portalAbierto;
	}

	/**
	 * Método que muestra por pantalla la ruta del personaje concreto
	 */
	public String mensajeRutaPersonaje() {
		String message = "";
		message += "(path:" + getID() + ":" + mostrarRuta() + ")";
		System.out.println(message);
		return message + "\n";
	}

	/**
	 * Método que castea de una lista de salas a una lista de direcciones
	 *
	 * @return
	 */
	public List<Dir> casteoRuta() {
		List<Dir> direcciones = new LinkedList<>();
		int dimY = Manhattan.getInstancia().getAncho();
		if (!caminoRuta.isEmpty())
			for (int i = 0; i < caminoRuta.size() - 1; i++) {
				Sala inicio = caminoRuta.get(i);
				Sala destino = caminoRuta.get(i + 1);
				// caso norte
				if (destino.getSala_id() == inicio.getSala_id() - dimY)
					direcciones.add(Dir.N);
				// caso sur
				if (destino.getSala_id() == inicio.getSala_id() + dimY)
					direcciones.add(Dir.S);
				// caso este
				if (destino.getSala_id() == inicio.getSala_id() + 1)
					direcciones.add(Dir.E);
				// caso oeste
				if (destino.getSala_id() == inicio.getSala_id() - 1)
					direcciones.add(Dir.O);
			}
		return direcciones;
	}

	/**
	 * Método que devuelve la ruta del personaje concreto en un String.
	 *
	 * @return
	 */
	public String mostrarRuta() {
		String message = "";
		for (int i = 0; i < ruta.size(); i++) {
			message += " " + ruta.get(i);
		}
		return message;
	}

	/**
	 * Método que muestra los atributos imprescindibles de cada personaje concreto
	 */
	public String mostrarPersonaje() {
		String message = "";
		if(this instanceof SHWeaponPhysical)
			message += "(shweaponphysical:";
		else if (this instanceof SHFísicos)
			message += "(shphysical:";
		if (this instanceof SHExtrasensorial)
			message += "(shextrasensorial:";
		if(this instanceof SHViajes)
			message += "(shflight:";
		if (this instanceof Villano)
			message += "(villain:";
		message += this.getID() + ":" + this.getSalaDePersonaje().getSala_id() + ":";
		if (turnoPersonaje <= turnoInicio)
			message += turnoInicio;
		else
			message += turnoPersonaje - 1;
		message += ":" + this.mostrarArmas() + ")";
		System.out.println(message);
		return message + "\n";
	}

	public void incrementarTurnoPersonaje() {
		turnoPersonaje++;
	}

	/**
	 * Método que muestra las armas de cada personaje por cada turno de la
	 * simulación
	 */
	public abstract String mostrarArmas();

	/**
	 * Método que permite recoger el arma de la sala
	 */
	public abstract void recogerArma();

	/**
	 * Método que realiza la interacción con el Hombre Puerta
	 */
	public abstract boolean interaccionConHPuerta();

	/**
	 * Método a través el cual un personaje interactúa con otro personaje de la
	 * misma sala
	 */
	public abstract void interaccionConPersonaje();

	/**
	 * Método a través del cual cada personaje genera su ruta concreta
	 */
	public abstract void generarRuta();

	/**
	 * Método que encuentra el arma de mayor poder en el arbol de armas.
	 *
	 * @param abb
	 *            Arbol auxiliar necesario para este método recursivo
	 * @param b
	 *            vector de armas dónde se va almacenando la mejor arma hasta el
	 *            momento.
	 */
	public void buscarMayorArma(Arbol<Arma> abb, Arma[] b) {
		Arbol<Arma> aux = null;
		if (!abb.vacio()) {
			if ((aux = abb.getHijoIzq()) != null) {
				this.buscarMayorArma(aux, b);
			}
			if (b[0].getPoder() < abb.getRaiz().getPoder())
				b[0] = abb.getRaiz();

			if ((aux = abb.getHijoDer()) != null) {
				this.buscarMayorArma(aux, b);
			}
		}
	}
	/**
	 * Método que encuentra el arma de menor poder en el arbol de armas del personaje
	 * @param abb
	 * @param b
	 */
	public void buscarMenorArma(Arbol<Arma> abb, Arma[] b) {
		Arbol<Arma> aux = null;
		if (!abb.vacio()) {
			if ((aux = abb.getHijoIzq()) != null) {
				this.buscarMenorArma(aux, b);
			}
			if (b[0].getPoder() > abb.getRaiz().getPoder())
				b[0] = abb.getRaiz();

			if ((aux = abb.getHijoDer()) != null) {
				this.buscarMenorArma(aux, b);
			}
		}
	}

	/**
	 * Método que realiza la simulación de los movimientos de los personajes
	 */
	public void movimiento() {
		Manhattan mapa = Manhattan.getInstancia();
		Sala salaPersonaje = this.getSalaDePersonaje();
		Sala nuevaSalaPersonaje = new Sala(9999, 10, 10);
		boolean move = false; // condición para ver si puede realizar el movimiento
		// establecemos las coordenadas x,y de la sala actual del personaje
		int x = salaPersonaje.getDimX();
		int y = salaPersonaje.getDimY();
		if (!(this.salaDePersonaje.getSala_id() == mapa.getDailyPlanet())) {
			if (!this.ruta.isEmpty()) {
				switch (this.ruta.remove(0)) {
				case N:
					if (x > 0)
						nuevaSalaPersonaje = mapa.devolverSalawCoordenates(x - 1, y);

					break;
				case S:
					if (x < mapa.getAlto() - 1)
						nuevaSalaPersonaje = mapa.devolverSalawCoordenates(x + 1, y);

					break;
				case O:
					if (y > 0)
						nuevaSalaPersonaje = mapa.devolverSalawCoordenates(x, y - 1);

					break;
				case E:
					if (y < mapa.getAncho() - 1)
						nuevaSalaPersonaje = mapa.devolverSalawCoordenates(x, y + 1);

					break;
				}
				if (!salaPersonaje.devVecinosNoAcc().contains(nuevaSalaPersonaje)
						&& mapa.existeSala(nuevaSalaPersonaje.getSala_id()))
					move = true;
				if (move) {
					this.setSalaDePersonaje(nuevaSalaPersonaje);
					nuevaSalaPersonaje.personajes.add(this);
					this.setSeHaMovido(true);
				}
			}
		}
	}

	/**
	 * Método que devuelve el nombre del personaje en cuestión
	 * 
	 * @return nombre del personaje
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método que permite modificar el nombre del personaje
	 *
	 * @param nombre
	 *            del personaje
	 * @throws IOException
	 */
	public void setNombre(String nombre) throws IOException {

		if (nombre != "") {
			this.nombre = nombre;
		} else {
			throw new IOException("El nombre debe estar especificado ");
		}
	}

	/**
	 * Método que devuelve el ID de un personaje.
	 *
	 * @return
	 */
	public char getID() {
		return ID;
	}

	/**
	 * Método que permite modificar el ID de un personaje.
	 *
	 * @param ID
	 *            del personaje
	 */
	public void setID(char ID) {
		this.ID = ID;
	}

	/**
	 * Método que devuelve la sala en la que se encuentra el personaje en cuestión
	 *
	 * @return Sala en la que se localiza el personaje
	 */
	public Sala getSalaDePersonaje() {
		return salaDePersonaje;
	}

	/**
	 * Método que permite modificar la sala en la que se encuentra el personaje.
	 *
	 * @param salaDePersonaje
	 */
	public void setSalaDePersonaje(Sala salaDePersonaje) {
		this.salaDePersonaje = salaDePersonaje;
	}

	/**
	 * Método que devuelve la ruta del personaje concreto
	 *
	 * @return
	 */
	public List<Dir> getRuta() {
		return ruta;
	}

	/**
	 * Método que permite modificar la ruta de cada personaje concreto
	 *
	 * @param direcciones
	 */
	public void setRuta(Dir[] direcciones) {

		for (int i = 0; i < direcciones.length; i++) {
			if (direcciones[i] == Dir.N || direcciones[i] == Dir.E || direcciones[i] == Dir.S
					|| direcciones[i] == Dir.O) {
				this.ruta.add(direcciones[i]);
			}
		}
	}

	/**
	 * Método que permite modificar la ruta de cada personaje concreto con diferente
	 * parametro
	 * 
	 * @param direcciones
	 */
	public void setRuta(List<Dir> direcciones) {
		this.ruta.addAll(direcciones);
	}

	/**
	 * Método que devuelve el turno del personaje concreto
	 *
	 * @return
	 */
	public int getTurnoPersonaje() {
		if (turnoPersonaje <= turnoInicio)
			return turnoInicio;
		return turnoPersonaje;
	}

	/**
	 * Método que permite modificar el turno del personaje concreto
	 *
	 * @param turnoPersonaje
	 */
	public void setTurnoPersonaje(int turnoPersonaje) {
		if (turnoPersonaje > -1)
			this.turnoPersonaje = turnoPersonaje;

	}

	/**
	 * Método que devuelve si el personaje se ha movido o no
	 *
	 * @return
	 */
	public boolean isSeHaMovido() {
		return seHaMovido;
	}

	/**
	 * Método que permite modificar si el personaje se ha movido o no
	 *
	 * @param seHaMovido
	 */
	public void setSeHaMovido(boolean seHaMovido) {
		this.seHaMovido = seHaMovido;
	}

	/**
	 * Método que me devuelve el turno de inicio de un personaje concreto
	 *
	 * @return
	 */
	public int getTurnoInicio() {
		return turnoInicio;
	}

	/**
	 * Método que permite modificar el turno de inicio de un personaje conret
	 *
	 * @param turnoInicio
	 */
	public void setTurnoInicio(int turnoInicio) {
		this.turnoInicio = turnoInicio;
	}

	/**
	 * Método que devuelve el camino que sigue cada personaje concreto
	 *
	 * @return
	 */
	public List<Sala> getCaminoRuta() {
		return caminoRuta;
	}

	/**
	 * Método que permite modificar el camino que sigue cada personaje concreto
	 *
	 * @param caminoRuta
	 */
	public void setCaminoRuta(List<Sala> caminoRuta) {
		this.caminoRuta = caminoRuta;
	}
	/**
	 * Método que devuelve el camino
	 */

	/**
	 * Método que devuelve el estado de sí la ruta está formada.
	 *
	 * @return
	 */
	public boolean isCaminoRutaOk() {
		return caminoRutaOk;
	}

	/**
	 * Método que permite modificar el booleano que indica si el camino del
	 * personaje ya está formado o no.
	 *
	 * @param caminoRutaOk
	 */
	public void setCaminoRutaOk(boolean caminoRutaOk) {
		this.caminoRutaOk = caminoRutaOk;
	}

	/**
	 * Método que indica si un superhéroe ha capturado a un villano en esta sala
	 * durante este turno
	 * 
	 * @return
	 */
	public boolean isVillanoCapturado() {
		return villanoCapturado;
	}

	/**
	 * Método que permite modificar el estado de esta variable
	 * 
	 * @param villanoCapturado
	 */
	public void setVillanoCapturado(boolean villanoCapturado) {
		this.villanoCapturado = villanoCapturado;
	}

}
