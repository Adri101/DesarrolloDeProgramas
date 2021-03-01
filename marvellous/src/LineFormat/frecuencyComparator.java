package LineFormat;

import Game.Sala;

/**
 * Comparador externo usado para ordenar adecuadamente las salas por su frecuencia  e id
 * @author rgomezr
 *
 */
public class frecuencyComparator implements java.util.Comparator<Sala> {
        @Override
        public int compare(Sala o1, Sala o2) {
            if(o1.getFrecuenciaCamino() > o2.getFrecuenciaCamino())
                return -1;
            if(o1.getFrecuenciaCamino() < o2.getFrecuenciaCamino())
                return 1;
            if(o1.getFrecuenciaCamino() == o2.getFrecuenciaCamino())
                if(o1.getSala_id() < o2.getSala_id())
                    return -1;
                else
                    return 1;
            else
                return 0;
        }
    }

