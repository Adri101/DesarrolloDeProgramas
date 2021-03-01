package Game;

import Characters.HombrePuerta;
import Characters.Personaje;
import LineFormat.formatLine;
import LineFormat.frecuencyComparator;
import data_structures.GenAleatorios;
import data_structures.Grafo;
import data_structures.Pared;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class Manhattan {
    /**
     * Patrón SINGLETON de la clase mapa
     */
    static private Manhattan instancia = null;
    /**
     * Es el valor que toma el alto del mapa.
     */
    private int alto;
    /**
     * Es el valor que toma el ancho del mapa.
     */
    private int ancho;
    /**
     * id de la sala dailyPlanet.
     */
    private int dailyPlanet;
    /**
     * altura en la que se abre el arbol de la configuración del hombre puerta
     */
    private int alturaApertura;
    /**
     * Variable que determina el turno de la simulación
     */
    private int turno;
    /**
     * Matriz la cual será el mapa de la ciudad de Game.Manhattan.
     */
    public Sala[][] city;
    /**
     * Atributo usado para la limpieza a la hora de mostrar información sobre la simulación del juego.
     */
    public formatLine lineFormat;
    /**
     * Sala donde se almacenan los ganadores del juego.
     */
    private Sala teseracto;
    /**
     * Estructura de paredes del mapa
     */
    public List<Pared> paredes;
    /**
     * Grafo que almacenara los caminos posibles en el mapa
     */
    public Grafo grafoCaminos;
    /**
     * String que contendra cada turno de la simulación para escribirlo al fichero externo al final de la simulación
     */
    private String messageFichero = "";
    /**
     * Lista que almacena todos los caminos posibles de la sala 0 a la daily planet
     */
    public List<List<Sala>> caminos = new LinkedList<>();

    /**
     * Constructor parametrizado de la clase mapa.
     *
     * @param idDaylyPlanet
     * @param alto
     * @param ancho
     * @param _alturaApertura
     */
    private Manhattan(int idDaylyPlanet, int alto, int ancho, int _alturaApertura) {
        try {
            this.setAlto(alto);
            this.setAncho(ancho);
            this.setDailyPlanet(idDaylyPlanet);
            this.setAlturaApertura(_alturaApertura);
            this.setTurno(0);
            this.teseracto = new Sala(1111, 1111, 1111);
            this.city = new Sala[this.getAlto()][this.getAncho()];
            this.lineFormat = new formatLine();
            this.paredes = new LinkedList<>();
            this.grafoCaminos = new Grafo();
            this.formarMapa();
            this.generarParedes();
            this.derribarParedes();
            this.actualizarGrafo();
        }catch (IOException e) {
            System.err.println("Algún paramentro que ha introducido por pantalla es incorrecto "
                    + "de la clase Manhattan: " + e.getMessage());
        }

    }

    /**
     * Método que devuelve la instancia del mapa en el caso de que ésta este creada, sino crea una nueva.
     *
     * @return instancia del mapa creado
     */

    static public Manhattan getInstancia() {
        if (instancia == null)
            instancia = new Manhattan(35, 6, 6, 4);
        return instancia;
    }

    /**
     * Método que devuelve la instancia en el caso de que exista, o bien crea una nueva con los parametros especificados.
     *
     * @param dailyPlanet
     * @param alto
     * @param ancho
     * @param alturaApertura
     * @return instancia del mapa creado
     */
    static public Manhattan getInstancia(int dailyPlanet, int alto, int ancho, int alturaApertura) {
        if (instancia == null)
            instancia = new Manhattan(dailyPlanet, alto, ancho, alturaApertura);
        return instancia;
    }

    /**
     * Método que distribuye las armas por el mapa
     *
     * @param idSalasConArmas
     * @param armas
     */

    public void distribuirArmas(List<Sala> idSalasConArmas, Arma[] armas) {
        int k = 0;
        for (int i = 0; i < idSalasConArmas.size() && k < armas.length; i++) {
            Sala saux = devolverSalawNum(idSalasConArmas.get(i).getSala_id());
            for (int j = 0; j < 5 && k < armas.length; j++) {
                saux.armas.add(armas[k]);
                k++;

            }
            saux.ordenarArmas();
        }
    }

    /**
     * Método que establece los id de las salas del mapa.
     */
    public void formarMapa() {
        for (int i = 0; i < this.getAlto() * this.getAncho(); i++) {
            this.city[i / this.getAncho()][i % this.getAncho()] = new Sala(i, i / this.getAncho(), i % this.getAncho());
            this.grafoCaminos.nuevoNodo(i);
        }
    }

    /**
     * Método que muestra las salas con arma
     */
    public String mostrarArmasSala() {
        String message = "";
        for (int i = 0; i < this.alto * this.ancho; i++) {
            message += this.devolverSalawCoordenates(i / this.ancho, i % this.ancho).showArmas();

        }
        return message;
    }

    /**
     * Método que muestra por pantalla los personajes que se encuentran en la simulación por cada turno
     */
    public String mostrarPersonajesSala() {
        String message = "";
        for (int i = 0; i < this.alto * this.ancho; i++) {
            message += this.devolverSalawCoordenates(i / this.ancho, i % this.ancho).mostrarPersonajesSala();
        }
        return message;
    }

    /**
     * Método que muestra por pantalla el turno de cada simulación
     */
    public String mostrarTurno() {
        String message = "(turn:" + this.getTurno() + ")";
        System.out.println(message);
        return message + "\n";
    }

    /**
     * Método que muestra por pantalla donde se encuentra la sala daily planet del mapa
     */
    public String mostrarDailyPlanet() {
        String message = "(map:" + this.getDailyPlanet() + ")";
        System.out.println(message);
        return message + "\n";
    }

    /**
     * Metodo que proporcionandole el ID de la sala nos devuelve una instancia de esta en el caso de que exista.
     *
     * @param _salaID de la sala deseada
     * @return instancia pedida de la clase Game.Sala.
     */
    public Sala devolverSalawNum(int _salaID) {
        if (_salaID < this.alto * this.ancho)
            return this.city[_salaID / this.getAncho()][_salaID % this.getAncho()];
        return null;
    }

    /**
     * Método que proporcionandole las coordenadas x,y de la sala, devuelve una instancia de ésta en el caso de que exista.
     *
     * @param alto
     * @param ancho
     * @return instancia pedida de sala de la clase mapa.
     */
    public Sala devolverSalawCoordenates(int alto, int ancho) {
        return this.city[alto][ancho];
    }

    /**
     * Método de la clase mapa el cual realiza la simulación del videjuego
     */
    public void simulacion() {

        boolean portalAbierto = true;
        int turnosMax = 50;
        while (this.turno <= turnosMax && portalAbierto) {
            for (int j = 0; j < this.alto * this.ancho && portalAbierto; j++) {
                Sala sala = this.devolverSalawNum(j);
                portalAbierto = sala.simulacion(turno);
            }
            messageFichero += formatLine.mensajeSimulaciónTurno();
            this.incrementarTurno();
        }
        formatLine.escribirSimulacion(messageFichero);
    }

    /**
     * Método que devuelve la sala mas lejana dada una sala origen.
     *
     * @param origen
     * @return
     */
    public Sala salaMasLejana(int origen) {
        int coste = 0;
        Sala salaLejana = new Sala(999, 999, 999);
        for (int i = 0; i < this.alto * this.ancho; i++) {
            int costeEnCurso = this.grafoCaminos.costeCamino(origen, i);
            if (coste < costeEnCurso) {
                coste = costeEnCurso;
                salaLejana = this.devolverSalawNum(i);
            }
        }
        return salaLejana;
    }

    /**
     * Método que muestra por pantalla los personajes del teseracto
     */
    public String mostrarTeseracto() {
        String message = "";
        if (!this.teseracto.personajes.isEmpty()) {
            message += "(teseractomembers)\n";
            System.out.println("(teseractomembers)");
            System.out.printf("(owneroftheworld:");
            message += "(owneroftheworld:";
            message += this.getTeseracto().mostrarPersonajesSala();

        }
        return message;
    }

    /**
     * Método que devuelve la sala sur oeste del mapa
     *
     * @return
     */
    public Sala devolverSalaSurOeste() {
        return this.city[this.alto - 1][0];
    }

    /**
     * Método que devuelve la sala nor este del mapa
     *
     * @return
     */
    public Sala devolverSalaNorEste() {
        return this.city[0][this.ancho - 1];
    }

    /**
     * Método que inserta al hombre puerta en la sala dailyPlanet
     *
     * @param _doorMan
     */
    public void insertarHombrePuerta(HombrePuerta _doorMan) {
        Sala salaAux = this.city[this.getDailyPlanet() / this.getAncho()][this.getDailyPlanet() % this.getAncho()];
        _doorMan.setSalaHombrePuerta(salaAux);
        salaAux.setHPuerta(_doorMan);
        salaAux.setEstaHombrePuerta(true);
    }

    /**
     * Método que utiliza el algoritmo de kruskal para derribar paredes del mapa siguiendo ciertas condiciones
     */
    public void derribarParedes() {
        while (!this.paredes.isEmpty()) {
            Pared paredAuxiliar = this.paredes.remove(GenAleatorios.generarNumero(this.paredes.size()));
            Sala salaOrigen = this.devolverSalawNum(paredAuxiliar.getX());
            Sala salaDestino = this.devolverSalawNum(paredAuxiliar.getY());
            if (salaOrigen.getMarcaSala() != salaDestino.getMarcaSala()) {
                grafoCaminos.nuevoArco(salaOrigen.getSala_id(), salaDestino.getSala_id(), 1);
                grafoCaminos.nuevoArco(salaDestino.getSala_id(), salaOrigen.getSala_id(), 1);
            }
            int marcaAntigua = salaDestino.getMarcaSala();
            int marcaNueva = salaOrigen.getMarcaSala();
            this.propagarMarca(marcaAntigua, marcaNueva);
        }
        this.actualizarGrafo();
    }

    /**
     * Método que propaga la marca por el mapa una vez derribada la pared
     */
    public void propagarMarca(int marcaAntigua, int marcaNueva) {
        for (int i = 0; i < this.alto * this.ancho; i++) {
            Sala salaActual = devolverSalawNum(i);
            if (salaActual.getMarcaSala() == marcaAntigua)
                salaActual.setMarcaSala(marcaNueva);
        }
    }

    /**
     * Método que actualiza las matrices de warshall y floyd del grafo.
     */
    public void actualizarGrafo() {
        this.grafoCaminos.warshall();
        this.grafoCaminos.floyd();
    }

    /**
     * Método que ordena las salas por su frecuencia según especificado en el enunciado del EC3
     *
     * @return
     */
    public List<Sala> ordenarSalasFrecuencia() {
        frecuentarSalas();
        List<Sala> salasOrdenadas = new LinkedList<>();
        for (int i = 0; i < this.alto * this.ancho; i++) {
            salasOrdenadas.add(this.devolverSalawNum(i));
        }
        Collections.sort(salasOrdenadas, new frecuencyComparator());
        return salasOrdenadas;
    }

    /**
     * Método que se encarga de obtener las salas más frecuentadas de los caminos
     */
    public void frecuentarSalas() {
        for (int i = 0; i < caminos.size(); i++) {
            for (int j = 0; j < caminos.get(i).size(); j++) {
                caminos.get(i).get(j).incrementarFrecuencia();
            }
        }

    }

    /**
     * Método que introduce al personaje en la sala establecida por parámetro
     *
     * @param _personaje       que se desea introducir
     * @param salaDelPersonaje en donde se desea introducir
     */
    public void insertarPersonaje(Personaje _personaje, int salaDelPersonaje) {
        _personaje.setSalaDePersonaje(devolverSalawNum(salaDelPersonaje));
        this.city[salaDelPersonaje / this.getAncho()][salaDelPersonaje % this.getAncho()].personajes.add(_personaje);
    }

    /**
     * Método que determina todos los algoritmos posibles mediante el algoritmo de profundidad.
     */
    public void profundidad() {
        List<Sala> visitados = new LinkedList<>();
        Sala salaInicio = this.devolverSalawNum(0);
        prof(salaInicio, visitados);


    }

    /**
     * Método que muestra todos los caminos possibles de la sala 0 a la sala daily planet
     */
    public void mostrarCaminos() {
        String message = "";
        for (int i = 0; i < caminos.size(); i++) {
            for (int j = 0; j < caminos.get(i).size(); j++) {
                message += caminos.get(i).get(j).getSala_id() + ":" + caminos.get(i).get(j).getFrecuenciaCamino() + " ";
            }
            message += "\n";
        }
        System.out.println(message);
    }

    /**
     * Método que calcula todos los caminos posibles de la sala 0 a la daily planet
     *
     * @param s
     * @param visitados
     */
    public void prof(Sala s, List<Sala> visitados) {
        if (!visitados.isEmpty())
            if (visitados.get(visitados.size() - 1).getSala_id() == this.getDailyPlanet()) {
                List<Sala> visitadosAux = new LinkedList<>();
                visitadosAux.addAll(visitados);
                caminos.add(visitadosAux);
            }
        List<Sala> ady;
        Sala salaAux;
        visitados.add(s);
        ady = s.devVecinos();
        while (!ady.isEmpty()) {
            salaAux = ady.remove(0);
            if (!visitados.contains(salaAux))
                prof(salaAux, visitados);

        }
        visitados.remove(s);
    }

    /**
     * Método que muestra por pantalla las paredes existentes en el mapa
     */
    public void showParedes() {
        String message = "";
        for (int i = 0; i < this.paredes.size(); i++) {
            message += "[" + this.paredes.get(i).getX() + "," + this.paredes.get(i).getY() + "] ";
        }
        System.out.println(this.paredes.size() + " " + message);
    }

    /**
     * Método que comprueba si una sala existe en el mapa
     *
     * @param sala_id
     * @return
     */
    public boolean existeSala(int sala_id) {
        if (this.devolverSalawNum(sala_id) == null)
            return false;
        return true;
    }

    /**
     * Método que generará las paredes del laberinto del mapa
     */
    public void generarParedes() {
        List<Character> direccionesPosibles;
        for (int i = 0; i < this.alto * this.ancho; i++) {
            direccionesPosibles = this.comprobarDirecciones(i / this.ancho, i % this.ancho);
            while (!direccionesPosibles.isEmpty()) {
                switch (direccionesPosibles.remove(0)) {
                    case 'N':
                        paredes.add(new Pared(i, i - this.ancho));
                        break;
                    case 'E':
                        paredes.add(new Pared(i, i + 1));
                        break;
                    case 'S':
                        paredes.add(new Pared(i, i + this.ancho));
                        break;
                    case 'O':
                        paredes.add(new Pared(i, i - 1));
                        break;
                }
            }
        }
    }

    /**
     * Método que devuelve una lista con las direcciones que tiene disponibles desde su posición (sala actual)
     *
     * @param x
     * @param y
     * @return lista de direcciones , en orden Norte(N),Este(E),Sur(S),Oeste(O)
     */
    public List<Character> comprobarDirecciones(int x, int y) {
        List<Character> direcciones = new LinkedList<>();
        //condición norte
        if (x > 0)
            direcciones.add('N');
        //condición este
        if (y < this.ancho - 1)
            direcciones.add('E');
        //condición sur
        if (x < this.alto - 1)
            direcciones.add('S');
        //condicion oeste
        if (y > 0)
            direcciones.add('O');
        return direcciones;
    }

    /**
     * Método que devuelve el alto de la matriz.
     *
     * @return alto
     */
    public int getAlto() {
        return alto;
    }

    /**
     * Método que permite modificar el valor "alto" de la matriz.
     *
     * @param alto
     */
    public void setAlto(int alto) throws IOException {
        if (alto > -1)
            this.alto = alto;
        else
            throw new IOException("El alto del mapa es incorrecto");

    }

    /**
     * Método que devuelve el ancho de la matriz.
     *
     * @return
     */
    public int getAncho() {
        return ancho;
    }

    /**
     * Método que permite modificar el valor "ancho" de la matriz.
     *
     * @param ancho
     */
    public void setAncho(int ancho) throws IOException {
        if (ancho > -1)
            this.ancho = ancho;
        else
            throw new IOException("El ancho del mapa es incorrecto");

    }

    /**
     * Método que incrementa el turno de la simulación en 1.
     */
    public void incrementarTurno() {
        this.turno++;
    }

    /**
     * Método que devuelve la variable turno
     *
     * @return
     */
    public int getTurno() {
        return turno;
    }

    /**
     * Método que permite modificar el turno
     *
     * @param turno
     */
    public void setTurno(int turno) {
        this.turno = turno;
    }

    /**
     * Método que devuelve el id de la sala daily Planet
     *
     * @return id de la sala dailyPlanet
     */
    public int getDailyPlanet() {
        return dailyPlanet;
    }

    /**
     * Método que permite modificar el id de la sala dailyPlanet
     *
     * @param dailyPlanet
     */
    public void setDailyPlanet(int dailyPlanet) {
        this.dailyPlanet = dailyPlanet;
    }

    /**
     * Método que crea los atajos del mapa una vez se ha aplicado el algoritmo de Kruskal.
     */
    public void crearAtajos() {
        List<Integer> camino;
        List<Sala> vecinosNoAcc;
        boolean creado;
        int maximoAtajos = ((this.alto * this.ancho) * 5) / 100;
        while (maximoAtajos > 0) {
            creado = false;
            Sala salaAleatoria = this.devolverSalawNum(GenAleatorios.generarNumero(this.alto * this.ancho));
            vecinosNoAcc = salaAleatoria.devVecinosNoAcc();
            while (vecinosNoAcc.size() > 0 && !creado) {
                Sala salaNoAcc = vecinosNoAcc.remove(0);
                camino = this.grafoCaminos.path(salaAleatoria.getSala_id(), salaNoAcc.getSala_id());
                if (camino.size() > 3) { //se puede crear el atajo, tirando la pared
                    this.grafoCaminos.nuevoArco(salaAleatoria.getSala_id(), salaNoAcc.getSala_id(), 1);
                    this.grafoCaminos.nuevoArco(salaNoAcc.getSala_id(), salaAleatoria.getSala_id(), 1);
                    maximoAtajos--;
                    creado = true;

                }
            }
        }
        this.actualizarGrafo();
    }

    /**
     * Método que pinta el mapa según las paredes y personajes
     *
     * @return
     */
    public String pintarMapa() {
        String result = "";
        for (int i = 0; i < this.getAncho(); i++) {
            result += " _";
        }
        result += "\n";
        for (int i = 0; i < this.getAlto(); i++) {
            result += "|";
            for (int j = 0; j < this.getAncho(); j++) { //equivalente al recorrido de las salas (j) de cada fila (i)
                result += pintarSala((this.getAncho() * i) + j);

            }
            result += "\n";
        }
        System.out.printf(result);
        return result;
    }

    /**
     * Método que pinta en la sala si hay personaje o no, y si tiene una pared a la derecha o no.
     *
     * @param sala_id
     * @return
     */
    public String pintarSala(int sala_id) {
        Sala sala = this.devolverSalawNum(sala_id);
        String message = sala.mostrarPersonajes();
        if (message.equals(" ") && !this.grafoCaminos.adyacente(sala_id, sala_id + this.getAncho())) {
            message = "_";
        }
        if (!this.grafoCaminos.adyacente(sala_id, sala_id + 1) || sala_id % this.getAncho() == this.getAncho() - 1) {
            message += "|";
        } else
            message += " ";
        return message;
    }

    /**
     * Método que muestra por pantalla las marcas del mapa
     */
    public void showMapa() {
        String message = "";
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                message += this.city[i][j].getMarcaSala() + " ";
            }
            message += "\n";
        }
        System.out.printf(message);
    }

    /**
     * Método que devuelve la altura de apertura del arbol
     *
     * @return altura de apertura del arbol
     */
    public int getAlturaApertura() {
        return alturaApertura;
    }

    /**
     * Métood que devuelve la instancia de la sala teseracto
     *
     * @return
     */
    public Sala getTeseracto() {
        return teseracto;
    }

    /**
     * Método que devuelve el String que contiene los mensajes de la simulación por cada turno
     *
     * @return
     */
    public String getMessageFichero() {
        return this.messageFichero;
    }

    /**
     * Método que añade texto al string del mapa que contiene los datos de registro externos.
     *
     * @param message
     */
    public void añadirTexto(String message) {
        this.messageFichero += message;
    }

    /**
     * Método que permite modificar la sala teseracto
     *
     * @param teseracto
     */
    public void setTeseracto(Sala teseracto) {
        this.teseracto = teseracto;
    }

    /**
     * Método que permite modificar la altura de apertura del arbol
     *
     * @param alturaApertura
     */
    public void setAlturaApertura(int alturaApertura) {
        this.alturaApertura = alturaApertura;
    }
}