package Game;

public class Arma implements Comparable<Arma> {
    /**
     * Nombre del arma.
     */
    private String nombre;
    /**
     * Cantidad de poder del arma en cuestión.
     */
    private int poder;

    public Arma(){
            this.setNombre("default");
            this.setPoder(0);
    }
    /**
     * Constructor parametrizado de la clase Game.Arma
     * @param _nombre del arma
     * @param _poder del arma
     */
    public Arma(String _nombre, int _poder) {
            this.setNombre(_nombre);
            this.setPoder(_poder);

    }

    /**
     * Método equals to de la clase Arma
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj){
        // Para optimizar, comparamos si las referencias de los dos objetos son iguales.
        //En este caso, los objetos son iguales siempre
        if (this == obj)
            return true;
        // Siempre debemos comparar si el objeto pasado por parametro es del mismo tipo.
        if (!(obj instanceof Arma))
            return false;
        // Hacemos un casting... se ve en mas detalle en sesiones de herencia
        Arma aAux = (Arma) obj;
        return (this.nombre.equals(aAux.getNombre()));
    }
    /**
     * Método compareTo de la clase Arma
     * @param o
     * @return
     */
    @Override
    public int compareTo(Arma o){
        if(this.nombre.compareTo(o.getNombre()) == 0) //lo cual generará un árbol ordenado por nombre.
            return 0;
        if(this.nombre.compareTo(o.getNombre()) > 0)
            return 1;
        else
            return -1;
    }
    /**
     * Método compareTo de la clase Arma.
     * @param o
     * @return
     */
    /*
    @Override
    public int compareTo(Arma o){
        if(this.poder == o.getPoder())   // lo cual generará un arbol ordenado por poder.
            return 0;
        if(this.poder > o.getPoder())
            return 1;
        else
            return -1;
    }*/

    /**
     * Método toString de la clase Arma que muestra sus valores.
     * @return valores del arma
     */
    public String toString(){

        return "("+this.getNombre()+","+this.getPoder()+")";
    }
    /**
     * Método que devuelve el nombre del arma
     * @return nombre del arma
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método que permite modificar el nombre del arma
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que devuelve el poder que tiene un arma
     * @return poder del arma
     */
    public int getPoder() {
        return poder;
    }

    /**
     * Método que permite modificar el poder de un arma
     * @param poder del arma
     */
    public void setPoder(int poder)  {
        this.poder = poder;

    }
}
