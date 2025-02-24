package extension;

import dao.IDao;
import org.springframework.stereotype.Component;

@Component("dao2")
public class DaoImplV2 implements IDao {
    public double getData() {
        System.out.println("Version 2");
        return 0.0;
    }
}
