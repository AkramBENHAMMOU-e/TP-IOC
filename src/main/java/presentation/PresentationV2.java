package presentation;

import dao.IDao;
import metier.IMetier;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class PresentationV2 {
    public static void main(String[] args)  {
        try {
            Scanner scanner = new Scanner(new File("config.txt"));
            //DaoImplV2 d = new DaoImplV2();
            String daoClassName = scanner.nextLine();
            Class cDai = Class.forName(daoClassName);
            IDao o =(IDao) cDai.getConstructor().newInstance();
            System.out.println("Result: "+o.getData());
            //MetierImpl metier = new MetierImpl(d);
            String metierClassName = scanner.nextLine();
            Class cMetier = Class.forName(metierClassName);
            IMetier metier = (IMetier) cMetier.getConstructor(IDao.class).newInstance(o);
            System.out.println("Result: "+metier.calcul());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
