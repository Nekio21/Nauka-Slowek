package umk.mat.jakuburb.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import umk.mat.jakuburb.encje.*;

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
        configuration.addAnnotatedClass(ZestawySlowek.class);
        configuration.addAnnotatedClass(Slowka.class);

        configuration.addAnnotatedClass(Kalendarz.class);
        configuration.addAnnotatedClass(Gra.class);
        configuration.addAnnotatedClass(SlowkaGra.class);
        configuration.addAnnotatedClass(HistoriaZestawu.class);

        sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.getTransaction().commit();
    }

    public void makeSession(MyDatabaseInterface myDatabaseInterface){
//        Session session = sessionFactory.getCurrentSession();
//        MyDatabaseBox myDatabaseBox = new MyDatabaseBox();
//        myDatabaseBox.setStany(StanyDatabase.NULL);
//
//        session.beginTransaction();
//
//        Object wynik = myDatabaseInterface.inside(myDatabaseBox, session);
//
//        session.getTransaction().commit();
//
//        myDatabaseInterface.after(myDatabaseBox, wynik);
        makeSession(null, myDatabaseInterface);
    }

    public void makeSession(MyDatabaseBox myDatabaseBox, MyDatabaseInterface myDatabaseInterface){
        Session session = sessionFactory.getCurrentSession();

        if(myDatabaseBox == null){
            myDatabaseBox = new MyDatabaseBox();
            myDatabaseBox.setStany(StanyDatabase.NULL);
        }

        try {
            session.beginTransaction();

            Object wynik = myDatabaseInterface.inside(myDatabaseBox, session);

            session.getTransaction().commit();

            myDatabaseInterface.after(myDatabaseBox, wynik);
        }catch(Exception e){
            try{
                session.getTransaction().commit();

                if(myDatabaseBox.isSafe()){
                    myDatabaseBox.setSafe(false);
                    makeSession(myDatabaseBox, myDatabaseInterface);
                }

            }catch(Exception ef){
                System.out.println("Cos nie działa");
            }

        }
    }

    public void stopDatabase(){
        sessionFactory.close();
    }

    @Override
    public void run() {
        startDatabase();
    }
}
