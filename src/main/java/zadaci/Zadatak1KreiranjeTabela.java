package zadaci;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import model.Knjiga;
import model.Oblast;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Resenje zadatka 2.2
 */
public class Zadatak1KreiranjeTabela {

    public static void main(String[] args) {
        ConnectionSource connectionSource = null;

        try {
            //Potrebno je prvo konektovati se na bazu
            connectionSource=new JdbcConnectionSource("jdbc:sqlite:knjigaOblast.db");

            /*Brisanje tabela iz baze
              Zbog ogranicenja referencijalnog integriteta
              prvo treba obrisati tabelu za klasu Oblast a
              zatim Knjiga
            */
            TableUtils.dropTable(connectionSource, Oblast.class,true);
            TableUtils.dropTable(connectionSource, Knjiga.class,true);

            /*Kreiranje tabela u bazi
              Zbog ogranicenja referencijalnog integriteta
              prvo treba kreirati tabelu za klasu Knjiga a
              zatim Oblast
            */
            TableUtils.createTable(connectionSource, Knjiga.class);
            TableUtils.createTable(connectionSource, Oblast.class);
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
