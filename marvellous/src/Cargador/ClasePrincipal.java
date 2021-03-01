package Cargador;
/**
 * Clase creada para ser usada en la utilidad cargador
 * contiene el main del cargador. Se crea una instancia de la clase Cargador
 * y se procesa el fichero de inicio, es decir, se leen todas las líneas y se van creando todas las instancias de la simulación
 *
 * @version 4.0 -  15/10/2014
 * @author Profesores DP
 */

import Game.Manhattan;

import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * NombreGrupo: TODO: Ten out of ten
 *
 * @author Ramón Gómez Recio && Adrián Fernández Ramos
 * @version EC1 - 29-X-2017 && EC2 - 1-XII-2017 && EC3 - 20-XII-17 && Entrega_Enero - 11-I-18
 * Curso: 3
 */
public class ClasePrincipal {
    public static void main(String[] args) {
        /**
         instancia asociada al fichero de entrada inicio.txt
         */
        Cargador cargador = new Cargador();
        try {
            /**
             Método que procesa línea a línea el fichero de entrada inicio.txt
             */

            FicheroCarga.procesarFichero(args[0], cargador);
            Manhattan mapa = Manhattan.getInstancia();
            mapa.simulacion();


        } catch (FileNotFoundException valor) {
            System.err.println("Excepción capturada al procesar fichero: " + valor.getMessage());
        } catch (IOException valor) {
            System.err.println("Excepción capturada al procesar fichero: " + valor.getMessage());
        }
    }
}
