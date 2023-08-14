package umk.mat.jakuburb.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import umk.mat.jakuburb.encje.User;

import java.io.File;
import java.net.URL;

public class MyDatabase implements Runnable{

    private static MyDatabase myDatabase =  null;

    private Configuration configuration;
    private SessionFactory sessionFactory;



    //TODO: Ogarnij to, ze czy czasami sie poptawnie zamyka bazadanych, booo wykres w pgAdmin ???
    private MyDatabase(){
       Thread thread = new Thread(this);

       thread.start();

    }

    public static MyDatabase createDatabase(){
        if(myDatabase == null){
            myDatabase = new MyDatabase();
        }

        return myDatabase;
    }

    private void startDatabase(){
        configuration = new Configuration();
        try {
            URL url = new File("src/main/resources/umk/mat/jakuburb/hibernate.cfg.xml").toURI().toURL();
            configuration.configure(url);
        }catch (Exception e){
            System.out.println(e);
        }
        configuration.addAnnotatedClass(User.class);

        sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.getTransaction().commit();
    }

    public void makeSession(MyDatabaseInterface mdi){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Object wynik = mdi.inside(session);

        session.getTransaction().commit();

        mdi.after(wynik);
    }

    public void stopDatabase(){
        sessionFactory.close();
    }

    @Override
    public void run() {
        startDatabase();
    }
}
