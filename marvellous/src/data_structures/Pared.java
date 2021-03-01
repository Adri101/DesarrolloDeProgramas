package data_structures;

import java.io.IOException;

public class Pared {
    /**
     * identificador de la sala que forma parte x de la pared
     */
    private int x;
    /**
     * identificador de la sala que forma parte y de la pared
     */
    private int y;

    /**
     * Constructor parametrizado de la clase pared
     *
     * @param _x de la pared
     * @param _y de la pared
     */
    public Pared(int _x, int _y) {
        this.x = _x;
        this.y = _y;

    }

    /**
     * Método que devuelve el lado x de la pared
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Método que permite establecer el lado x de la pared
     *
     * @param x
     * @throws IOException
     */
    public void setX(int x) throws IOException {
        if (x > -1)
            this.x = x;
        else{
            throw new IOException("El parámetro x debe de ser mayor que cero");
        }
    }

    /**
     * Método que devuelve el lado y de la pared
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * Método que permite estbalecer el lado y de la pared
     *
     * @param y
     * @throws IOException
     */
    public void setY(int y) throws IOException {
        if (y > -1)
            this.y = y;
        else {
            throw new IOException("El parámetro Y  debe de ser mayor que cero");
        }
    }
}
