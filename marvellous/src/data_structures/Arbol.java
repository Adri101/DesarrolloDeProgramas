package data_structures;

import java.util.List;

/**
 /**
 * Implementacion de arbol binario de busqueda.
 *
 * @author Asignatura Desarrollo de Programas<br/>
 * <b> Profesores DP </b><br>
 * Curso 14/15
 * @version 1.0
 */
public class Arbol<TYPE extends Comparable<TYPE>> {

    /**
     * Dato almacenado en cada nodo del árbol.
     */
    private TYPE datoRaiz;

    /**
     * Atributo que indica si el árbol está vacío.
     */
    boolean esVacio;

    /**
     * Hijo izquierdo del nodo actual
     */
    private Arbol<TYPE> hIzq;

    /**
     * Hijo derecho del nodo actual
     */
    private Arbol<TYPE> hDer;

    /**
     * Constructor por defecto de la clase. Inicializa un árbol vacío.
     */
    public Arbol() {
        this.esVacio = true;
        this.hIzq = null;
        this.hDer = null;
    }

    /**
     * Crea un nuevo árbol a partir de los datos pasados por parámetro.
     *
     * @param hIzq     El hijo izquierdo del árbol que se está creando
     * @param datoRaiz Raíz del árbol que se está creando
     * @param hDer     El hijo derecho del árbol que se está creando
     */
    public Arbol(Arbol<TYPE> hIzq, TYPE datoRaiz, Arbol<TYPE> hDer) {
        this.esVacio = false;
        this.datoRaiz = datoRaiz;
        this.hIzq = hIzq;
        this.hDer = hDer;
    }

    /**
     * Devuelve el hijo izquierdo del árbol
     *
     * @return El hijo izquierdo
     */
    public Arbol<TYPE> getHijoIzq() {
        return hIzq;
    }

    /**
     * Devuelve el hijo derecho del árbol
     *
     * @return Hijo derecho del árbol
     */
    public Arbol<TYPE> getHijoDer() {
        return hDer;
    }

    /**
     * Devuelve la raíz del árbol
     *
     * @return La raíz del árbol
     */
    public TYPE getRaiz() {
        return datoRaiz;
    }

    /**
     * Comprueba si el árbol está vacío.
     *
     * @return verdadero si el árbol está vacío, falso en caso contrario
     */
    public boolean vacio() {
        return esVacio;
    }

    /**
     * Inserta un nuevo dato en el árbol.
     *
     * @param dato El dato a insertar
     * @return verdadero si el dato se ha insertado correctamente, falso en caso contrario
     */
    public boolean insertar(TYPE dato) {
        boolean resultado = true;
        if (vacio()) {
            datoRaiz = dato;
            esVacio = false;
        } else {
            if (!(this.datoRaiz.equals(dato))) {
                Arbol<TYPE> aux;
                if (dato.compareTo(this.datoRaiz) < 0) { //dato < datoRaiz
                    if ((aux = getHijoIzq()) == null)
                        hIzq = aux = new Arbol<>();
                } else {                                    //dato > datoRaiz
                    if ((aux = getHijoDer()) == null)
                        hDer = aux = new Arbol<>();
                }
                resultado = aux.insertar(dato);
            } else
                resultado = false;
        }
        return resultado;
    }

    /**
     * Comprueba si un dato se encuentra almacenado en el árbol
     *
     * @param dato El dato a buscar
     * @return verdadero si el dato se encuentra en el árbol, falso en caso contrario
     */
    public boolean pertenece(TYPE dato) {
        Arbol<TYPE> aux = null;
        boolean encontrado = false;
        if (!vacio()) {
            if (this.datoRaiz.equals(dato))
                encontrado = true;
            else {
                if (dato.compareTo(this.datoRaiz) < 0)    //dato < datoRaiz
                    aux = getHijoIzq();
                else                                    //dato > datoRaiz
                    aux = getHijoDer();
                if (aux != null)
                    encontrado = aux.pertenece(dato);
            }
        }
        return encontrado;
    }
    /**
     * Borrar un dato del árbol.
     *
     * @param dato El dato que se quiere borrar
     */
    public void borrar(TYPE dato) {
        if (!vacio()) {
            if (dato.compareTo(this.datoRaiz) < 0) {            //dato<datoRaiz
                hIzq = hIzq.borrarOrden(dato);
            } else if (dato.compareTo(this.datoRaiz) > 0) {        //dato>datoRaiz
                hDer = hDer.borrarOrden(dato);
            } else //En este caso el dato es datoRaiz
            {
                if (hIzq == null && hDer == null) {
                    esVacio = true;
                    this.datoRaiz = null;
                } else
                    borrarOrden(dato);
            }
        }
    }


    /**
     * Borrar un dato. Este método es utilizado por el método borrar anterior.
     *
     * @param dato El dato a borrar
     * @return Devuelve el árbol resultante después de haber realizado el borrado
     */
    private Arbol<TYPE> borrarOrden(TYPE dato) {
        TYPE datoaux;
        Arbol<TYPE> retorno = this;
        Arbol<TYPE> aborrar;
        Arbol<TYPE> candidato;
        Arbol<TYPE> antecesor;

        if (!vacio()) {
            if ((dato.compareTo(this.datoRaiz) < 0) && hIzq != null) {        // dato<datoRaiz
                hIzq = hIzq.borrarOrden(dato);
            } else if ((dato.compareTo(this.datoRaiz) > 0) && hDer != null) {    // dato>datoRaiz
                hDer = hDer.borrarOrden(dato);
            } else {
                aborrar = this;
                if ((hDer == null) && (hIzq == null)) { /*si es hoja*/
                    retorno = null;
                } else {
                    if (hDer == null) { /*Solo hijo izquierdo*/
                        aborrar = hIzq;
                        datoaux = this.datoRaiz;
                        datoRaiz = hIzq.getRaiz();
                        hIzq.datoRaiz = datoaux;
                        hIzq = hIzq.getHijoIzq();
                        hDer = aborrar.getHijoDer();

                        retorno = this;
                    } else if (hIzq == null) { /*Solo hijo derecho*/
                        aborrar = hDer;
                        datoaux = datoRaiz;
                        datoRaiz = hDer.getRaiz();
                        hDer.datoRaiz = datoaux;
                        hDer = hDer.getHijoDer();
                        hIzq = aborrar.getHijoIzq();

                        retorno = this;
                    } else { /* Tiene dos hijos */
                        candidato = this.getHijoIzq();
                        antecesor = this;
                        while (candidato.getHijoDer() != null) {
                            antecesor = candidato;
                            candidato = candidato.getHijoDer();
                        }

	                            /*Intercambio de datos de candidato*/
                        datoaux = datoRaiz;
                        datoRaiz = candidato.getRaiz();
                        candidato.datoRaiz = datoaux;
                        aborrar = candidato;
                        if (antecesor == this)
                            hIzq = candidato.getHijoIzq();
                        else
                            antecesor.hDer = candidato.getHijoIzq();
                    } //Eliminar solo ese nodo, no  el subarbol
                    aborrar.hIzq = null;
                    aborrar.hDer = null;
                }
            }
        }
        return retorno;
    }


    /**
     * Recorrido inOrden del árbol.
     */
    public void inOrden(List<TYPE> lista) {
        Arbol<TYPE> aux = null;
        String message= "";
        if (!vacio()) {
            if ((aux = getHijoIzq()) != null) {
                aux.inOrden(lista);
            }
            //message+= this.datoRaiz + " ";
            lista.add(this.datoRaiz);

            if ((aux = getHijoDer()) != null) {
                aux.inOrden(lista);
            }
        }
        //System.out.printf(message);
    }
    /**
     * Método que devuelve una instancia del dato el cual buscado por su nombre por parámetro
     * @param dato el cual se desea buscar
     * @return instancia del dato deseado.
     */
    public boolean buscarPNom(TYPE dato, boolean encontrado,TYPE[] dAux) {
        Arbol<TYPE> aux = null;
        if (!vacio()) {
            if (this.datoRaiz.equals(dato) && !encontrado) {
                encontrado = true;
                dAux[0] = this.datoRaiz;
            }
            else {
                if (dato.compareTo(this.datoRaiz) < 0 && !encontrado)    //dato < datoRaiz
                    aux = getHijoIzq();
                else                                    //dato > datoRaiz
                    aux = getHijoDer();
                if (aux != null && !encontrado)
                    encontrado = aux.buscarPNom(dato,encontrado,dAux);
            }
        }
        return encontrado;
    }


    /**
     * Cuenta la profundidad de un árbol binario
     *
     * @param bst el arbol en cual contamos la profundidad
     * @return Devuelve el número de niveles de nodos
     */

    public int profundidad(Arbol<TYPE> bst) {
        Arbol<TYPE> aux = new Arbol<TYPE>();
        int res = 0, pI = 0, pD = 0;
        if (!bst.vacio()) {
            aux = bst.hIzq;
            if (aux != null)
                pI = pI + profundidad(aux);
            aux = bst.hDer;
            if (aux != null)
                pD = pD + profundidad(aux);
            if (pI > pD)
                res = pI + 1;
            else
                res = pD + 1;
        }
        return res;
    }

    /**
     * Cuenta los nodos que no tienen nodos hijos a null
     *
     * @param bst el arbol que queremos contar sus nodos internos
     * @return número de de nodos internos del arbol introducido
     */
    public int nodosInternos(Arbol<TYPE> bst) {
        int num = 0;
        int interno = 0;
        if (bst != null) {
            if (bst.hIzq != null || bst.hDer != null) {
                interno = 1;
            }
            num = interno + nodosInternos(bst.hIzq) + nodosInternos(bst.hDer);
        }
        return num;
    }

    /**
     * Cuenta el número de nodos con algún hijo apuntando a null
     *
     * @param bst el arbol que queremos contar sus nodos finales
     * @return número de nodos finales de un arbol
     */
    public int nodosFinales(Arbol<TYPE> bst) {
        int num = 0;
        if (bst != null) {
            if (bst.hIzq == null && bst.hDer == null) {
                num = 1;
            }
            num = num + nodosFinales(bst.hIzq) + nodosFinales(bst.hDer);

        }
        return num;
    }
/**
 public static void main(String[] args) {
 Arbol arbol = new Arbol();
 System.out.println("Ejemplos sesion árbol binario de búsqueda");
 Integer[] datos = {new Integer(15), new Integer(7), new Integer(3),
 new Integer(1), new Integer(5), new Integer(11),
 new Integer(9), new Integer(13), new Integer(23), new Integer(19), new Integer(17), new Integer(21), new Integer(27), new Integer(25), new Integer(29)};
 for (int i = 0; i < datos.length; i++) {
 arbol.insertar(datos[i]);
 }
 int y = arbol.nodosFinales(arbol);
 System.out.println(y);
 int x = arbol.nodosInternos(arbol);
 System.out.println(x);
 int z = arbol.profundidad(arbol);
 System.out.println(z);
 }
 */
}