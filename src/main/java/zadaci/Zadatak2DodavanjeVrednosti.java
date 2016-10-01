package zadaci;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import model.Knjiga;
import model.Oblast;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Resenje zadatka 2.3
 */
public class Zadatak2DodavanjeVrednosti {
    //Dao objekti sa pomocnim metodama za rad sa bazom
    static Dao<Knjiga,Integer> knjigaDao;
    static Dao<Oblast,Integer> oblastDao;

    public static void main(String[] args) {
        ConnectionSource connectionSource = null;

        try {
            //Potrebno je prvo konektovati se na bazu
            connectionSource=new JdbcConnectionSource("jdbc:sqlite:knjigaOblast.db");
            //Instanciranje Dao objekata
            knjigaDao=DaoManager.createDao(connectionSource, Knjiga.class);
            oblastDao=DaoManager.createDao(connectionSource, Oblast.class);

            //Kreiranje objekta klase Knjiga
            //a)
            Knjiga k1=new Knjiga("Java programiranje",650,new Date());
            knjigaDao.create(k1);

            //b)
            Knjiga k2=new Knjiga("Android programiranje",650,new Date());
            knjigaDao.create(k2);

            //Kreiranje objekta klase Oblast
            //a)
            Oblast o1=new Oblast("Uvod",2);
            o1.setKnjiga(k1);
            oblastDao.create(o1);

            //b)
            Oblast o2=new Oblast("Naredbe",10);
            o2.setKnjiga(k1);
            oblastDao.create(o2);

            //c)
            Oblast o3=new Oblast("Aritmeticki operatori",20);
            o3.setKnjiga(k1);
            oblastDao.create(o3);

            //d)
            Oblast o4=new Oblast("Android operativni sistem",2);
            o4.setKnjiga(k2);
            oblastDao.create(o4);

            //e)
            Oblast o5=new Oblast("Activity klasa",30);
            o5.setKnjiga(k2);
            oblastDao.create(o5);

            //Prikaz svih vrednosti tabela Knjiga
            List<Knjiga> knjige=knjigaDao.queryForAll();
            for(Knjiga k:knjige)
                System.out.println("k = " + k);

            //Prikaz svih vrednosti tabela Oblast
            List<Oblast> oblasti=oblastDao.queryForAll();
            for(Oblast o:oblasti)
                System.out.println("o = " + o);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                //Zatvaranje konekcije sa bazom
                connectionSource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
