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
import java.util.Random;

/**
 * Created by lazar on 29.9.16..
 */
public class KnjigaNit extends Thread {
    private String imeClana;
    private Knjiga knjiga;

    static Dao<Knjiga,Integer> knjigaDao;

    public KnjigaNit(String imeClana, Knjiga knjiga) {
        this.imeClana = imeClana;
        this.knjiga = knjiga;
    }

    @Override
    public void run() {
        //Koristicemo lokalnu promenljivu da bismo izbegli sinhronizaciju u while (videti dole)
        boolean uzeoKnjigu = false;
        System.out.println("Clan " + imeClana + " trazi knjigu " + knjiga.getNaslov());

            do {
                 synchronized (knjiga) {
                     // Ceo if mora biti sinhronizovan jer se knjiga.isPrisutna poziva unutar if-a
                     if (knjiga.isPrisutna()) {
                         //Knjiga je sada zauzeta
                         uzeoKnjigu = true;
                         knjiga.setPrisutna(false);
                         System.out.println("Clan " + imeClana + " uzima knjigu " + knjiga.getNaslov());

                         Random random = new Random();

                         try {
                             // Clan uzima knjigu na neki period od 0 do 5 sekundi
                             // Zbog duzine moze delovati da je program stao, u tom slucaju smanjiti cekanje
                             sleep(random.nextInt(5000));
                         } catch (InterruptedException e) {
                             e.printStackTrace();
                         }

                         //Knjiga je sada vracena i sledeci clan moze da je uzme kada se izadje iz ovog bloka
                         knjiga.setPrisutna(true);
                         System.out.println("Clan " + imeClana + " vraca knjigu " + knjiga.getNaslov());
                     }
                     // Kada bismo ovde stavili liniju:
                     // while(!knjiga.isPrisutna())
                     // Moze se desiti da ce druga petlja uzeti knjigu dok ova spava, tako da se videti da je knjiga slobodna,
                     // pretpostavice da ju je uzela i zavrsice sa radom. Ako ceo while stavimo u synchronized blok, program ce
                     // postati sekvencijalan jer ce prva nit blokirati ostale dok se ne zavrsi.
                     // Koriscenjem lokalne promenljive se izbegava sinhronizovani pristup, tako da ako je uzeoKnjigu true, 100%
                     // smo sigurni da ju je ova nit uzela
                 }
            } while (!uzeoKnjigu) ;
    }

    public static void main(String[] args){
        ConnectionSource connectionSource = null;
        try {
            //Potrebno je prvo konektovati se na bazu
            connectionSource=new JdbcConnectionSource("jdbc:sqlite:knjigaOblast.db");
            //Instanciranje Dao objekata
            knjigaDao= DaoManager.createDao(connectionSource, Knjiga.class);

            //Uzmemo sve knjige (bice ih 2)
            //Za obe kreiramo po 3 niti
            List<Knjiga> sveKnjige = knjigaDao.queryForAll();

            KnjigaNit kn1 = new KnjigaNit("Pera", sveKnjige.get(0));
            KnjigaNit kn2 = new KnjigaNit("Mika", sveKnjige.get(0));
            KnjigaNit kn3 = new KnjigaNit("Ana", sveKnjige.get(0));

            KnjigaNit kn4 = new KnjigaNit("Zika", sveKnjige.get(1));
            KnjigaNit kn5 = new KnjigaNit("Branka", sveKnjige.get(1));
            KnjigaNit kn6 = new KnjigaNit("Sanja", sveKnjige.get(1));

            kn1.start();
            kn2.start();
            kn3.start();
            kn4.start();
            kn5.start();
            kn6.start();

            try {
                kn1.join();
                kn2.join();
                kn3.join();
                kn4.join();
                kn5.join();
                kn6.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Biblioteka se zatvara.");

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
