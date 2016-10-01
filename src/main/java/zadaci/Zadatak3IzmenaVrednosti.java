package zadaci;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import model.Knjiga;
import model.Oblast;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Resenje 2.4
 */
public class Zadatak3IzmenaVrednosti {
    //Dao objekti sa pomocnim metodama za rad sa bazom
    static Dao<Knjiga,Integer> knjigaDao;
    static Dao<Oblast,Integer> oblastDao;
    public static void main(String[] args) {
        ConnectionSource connectionSource = null;
        try {
            //Potrebno je prvo konektovati se na bazu
            connectionSource=new JdbcConnectionSource("jdbc:sqlite:knjigaOblast.db");
            //Instanciranje Dao objekata
            knjigaDao= DaoManager.createDao(connectionSource, Knjiga.class);
            oblastDao=DaoManager.createDao(connectionSource, Oblast.class);

            //Prikaz vrednosti tabele Oblast
            List<Oblast> oblasti=oblastDao.queryForAll();
            for(Oblast o:oblasti)
                System.out.println("o = " + o);

            //Pronalazenje oblasti koje za vrednost kolone naziv imaju
            // vrednost "Activity klasa"
            oblasti=oblastDao.queryForEq(Oblast.POLJE_NAZIV,"Activity klasa");
            Oblast zaIzmenu=oblasti.get(0);//Preuzimamo prvi pronadjeni
            //Menjamo vrednost atributa pocetnaStrana na 35
            zaIzmenu.setPocetnaStrana(35);
            //Cuvamo izmene u bazi, menja se vrednost kolone pocetna_strana
            oblastDao.update(zaIzmenu);

            /*Prikaz vrednosti tabele Oblast
               da potvrdimo da je vrednost izmenjena
             */
            oblasti=oblastDao.queryForAll();
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
