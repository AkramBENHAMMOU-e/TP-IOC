package dao;

import org.springframework.stereotype.Component;

@Component("dao")
public class DaoImplV1  implements IDao {
    public double getData() {
        System.out.println("Version 1");
        return 0.0;
    }
}
