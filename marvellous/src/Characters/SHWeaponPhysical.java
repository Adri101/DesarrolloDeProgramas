package Characters;

import Game.Manhattan;
import Game.Sala;

import java.util.LinkedList;
import java.util.List;

public class SHWeaponPhysical extends SHFísicos {
    public List<List<Sala>> caminosConPoder = new LinkedList<>();
    /**
     * Constructor por defecto del perosnaje
     * @param nombre
     * @param id
     * @param turno
     */
    public SHWeaponPhysical(String nombre, char id, int turno){
        super(nombre,id,turno);
    }
    @Override
    public void generarRuta(){
        //Misma sala de los super heroes fisicos
        Manhattan mapa = Manhattan.getInstancia();
        List<Sala> visitados = new LinkedList<>();
        Sala salaInicio = mapa.devolverSalawNum(0);
        profundidadWeapon(salaInicio, visitados);
        mostrarCaminos();
        escogerCaminoWeapon();


    }
    /**
     * Método que escoge el camino de salas con mayor poder
     */
        public void escogerCaminoWeapon() {
            List<Sala> caminoMejor = new LinkedList<>();
            List<Integer> poderCamino = new LinkedList<>();
            int poderTotal = 0;
            int poderMejor = 0;
            int caminoMasPoder = 0;
            for (int i = 0; i <caminosConPoder.size() ; i++) {
                poderTotal=0;
                for (int j = 0; j <caminosConPoder.get(i).size() ; j++) {

                    poderTotal +=caminosConPoder.get(i).get(j).poderSala();
                    poderCamino.add(poderTotal);


                }
                if(poderTotal>poderMejor){
                    poderMejor = poderTotal;
                    poderTotal=0;
                    caminoMasPoder= i;
                }

            }
            this.setCaminoRuta(caminosConPoder.get(caminoMasPoder));
            this.setCaminoRutaOk(true);
            this.setRuta(casteoRuta());

    }
    /**
     * Método que muestra todos los caminos possibles de la sala 0 a la sala daily planet
     */
    public void mostrarCaminos() {
        String message = "";
        System.out.println("MOSTRANDO LOS CAMINOS DEL PERSONAJE WEAPON");
        for (int i = 0; i < caminosConPoder.size(); i++) {
            for (int j = 0; j < caminosConPoder.get(i).size(); j++) {
                message += caminosConPoder.get(i).get(j).getSala_id() + ":" + caminosConPoder.get(i).get(j).getFrecuenciaCamino() + " ";
            }
            message += "\n";
        }
        System.out.println(message);
    }

    /**
     * Método que calcula el camino con mayor suma de poder de sus armas
     */
    public void profundidadWeapon(Sala s, List<Sala> visitados){
        if (!visitados.isEmpty() && visitados.get(visitados.size() - 1).getSala_id() == Manhattan.getInstancia().getDailyPlanet() && !this.isCaminoRutaOk())
            if (visitados.get(visitados.size() - 1).getSala_id() == Manhattan.getInstancia().getDailyPlanet()) {
                List<Sala> visitadosAux = new LinkedList<>();
                visitadosAux.addAll(visitados);
                caminosConPoder.add(visitadosAux);
            }
        List<Sala> ady;
        Sala salaAux;
        visitados.add(s);
        ady = s.devVecinos();
        while (!ady.isEmpty()) {
            salaAux = ady.remove(0);
            if (!visitados.contains(salaAux))
                profundidadWeapon(salaAux, visitados);

        }
        visitados.remove(s);
    }
}
