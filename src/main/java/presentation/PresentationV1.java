package presentation;

import dao.DaoImpl;
import metier.MetierImpl;

public class PresentationV1 {

    public static void main(String[] args) {
        DaoImpl d = new DaoImpl();
        MetierImpl metier = new MetierImpl(d);
        //metier.setDao(d);
        System.out.println("Result: "+metier.calcul());

    }
}
