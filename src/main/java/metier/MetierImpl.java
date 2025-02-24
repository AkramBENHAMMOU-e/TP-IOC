package metier;

import dao.IDao;

public class MetierImpl implements IMetier {
    private IDao dao =null;

    public MetierImpl(IDao dao) {
        this.dao = dao;
    }

    public double calcul() {
        double data = dao.getData();
        double result = data * 23;
        return result;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
