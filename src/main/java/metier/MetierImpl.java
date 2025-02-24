package metier;

import dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("metier")
public class MetierImpl implements IMetier {
    @Autowired
    private IDao dao =null;

    public MetierImpl() {
    }
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
