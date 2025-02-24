# TP Injection de Dépendances

Ce TP vise à comprendre et implémenter différentes techniques d'injection de dépendances en Java, en utilisant le couplage faible et le Framework Spring.

## Objectifs

1. Comprendre le principe de couplage faible
2. Implémenter l'injection de dépendances de différentes manières
3. Utiliser le Framework Spring pour gérer les dépendances

## Structure du Projet

Le projet est structuré comme suit:

```
src/
├── main/
│   ├── java/
│   │   ├── dao/
│   │   │   ├── IDao.java           // Interface DAO
│   │   │   └── DaoImplV1.java        // Implémentation de l'interface
│   │   ├── extension/
│   |   │   ├── DaoImplV2.java        // Implémentation alternative de l'interface
│   |   │   └── DaoImplV2SpringXML.java     // Implémentation alternative de l'interface
│   │   ├── metier/
│   │   │   ├── IMetier.java        // Interface métier
│   │   │   └── MetierImpl.java     // Implémentation avec couplage faible
│   │   └── presentation/
│   │       ├── PresentationV1.java          // Instanciation statique
│   │       ├── PresentationV2.java          // Instanciation dynamique
│   │       └── PresenattionSpringXML.java  // Utilisation de Spring (XML)
│   │       └── PresenatationSpringAnnot.java// Utilisation de Spring (Annotations)
│   └── resources/
│       └── config.xml  // Configuration Spring XML
```

## Étapes d'implémentation

### 1. Création de l'interface IDao

Cette interface définit une méthode `getData()` qui sera implémentée par les classes concrètes.

```java
public interface IDao {
    double getData();
}
```

### 2. Implémentation de l'interface IDao

Création d'une classe qui implémente l'interface IDao:

```java
public class DaoImplV1 implements IDao {
    @Override
    public double getData() {
        System.out.println("Version 1");
        return 0.0;
    }
}
```

### 3. Création de l'interface IMetier

Cette interface définit une méthode `calcul()`:

```java
public interface IMetier {
    double calcul();
}
```

### 4. Implémentation avec couplage faible

Création d'une classe qui implémente l'interface IMetier et utilise l'interface IDao (couplage faible):

```java
public class MetierImpl implements IMetier {
    // Couplage faible - dépendance sur l'interface et non sur l'implémentation
    private  IDao dao =null;

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
```

### 5. Injection des dépendances

#### a. Par instanciation statique (PresentationV1.java)

```java
public class PresentationV1 {

    public static void main(String[] args) {
        DaoImplV2 d = new DaoImplV2();
        // MetierImpl metier = new MetierImpl();
        //metier.setDao(d);  //injection via setter
        MetierImpl metier = new MetierImpl(d); //injection via constructeur
        System.out.println("Result: "+metier.calcul());

    }
}
```

#### b. Par instanciation dynamique (PresenatationV2.java)

création d'un fichier `config.txt` contenant les noms des classes à instancier: 
```java
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
            //instanciation dynamique via setter
//            String metierClassName = scanner.nextLine();
//            Class cMetier = Class.forName(metierClassName);
//            IMetier metier = (IMetier) cMetier.getConstructor().newInstance();
//            //metier.setDao(d);
//            Method m = cMetier.getDeclaredMethod("setDao", IDao.class);
//            m.invoke(metier, o);
//            System.out.println("Result: "+metier.calcul());

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
```

#### c. En utilisant le Framework Spring

##### Version XML (config.xml)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="dao" class="extension.DaoImplV2SpringXML"></bean>
    <bean id="metier" class="metier.MetierImpl">
        <property name="dao" ref="dao"></property>
    </bean>

</beans>
```

```java
public class PresentationSpringXML {
    public static void main(String[] args) {
        ApplicationContext context =new ClassPathXmlApplicationContext("config.xml");
        IMetier metier = (IMetier) context.getBean("metier");
        System.out.println("Result: "+metier.calcul());
    }
}
```

##### Version annotations

Configuration des annotations:

```java
@Component("dao")
public class DaoImpl implements IDao {
    // ...
}

@Component("metier")
public class MetierImpl implements IMetier {
    @Autowired
    private IDao dao;
    // ...
}
```

Application:

```java
public class PresentationSpringAnotation {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("extension", "metier");
        IMetier metier = (IMetier) context.getBean(IMetier.class);
        System.out.println("Result: " + metier.calcul());
    }
}
```

## Exécution du projet

Pour exécuter le projet, vous pouvez lancer chacune des classes principales (`PresenatationV1`, `PresenatationV2`, `PresenatationSpringXML`, `PresenatationSpringAnnotation`) pour observer les différentes méthodes d'injection de dépendances.

## Dépendances Maven

Ajoutez ces dépendances à votre fichier `pom.xml` pour utiliser Spring:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-core</artifactId>
        <version>5.3.20</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>5.3.20</version>
    </dependency>
    <dependency>
        <groupId>javax.annotation</groupId>
        <artifactId>javax.annotation-api</artifactId>
        <version>1.3.2</version>
    </dependency>
</dependencies>
```