package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Resenje 2.1.2
 */
@DatabaseTable(tableName = "oblast")//Anotacija za tabelu oblast
public class Oblast {
    //Staticki atributi
    public static final String POLJE_NAZIV="naziv";
    public static final String POLJE_POCETNA_STRANA="pocetna_strana";

    //atributi za upisu u bazu
    @DatabaseField(generatedId = true)//primarni kljuc koji se automatski generise
    private int id;
    @DatabaseField(columnName = POLJE_NAZIV,canBeNull = false)
    private String naziv;
    @DatabaseField(columnName = POLJE_POCETNA_STRANA,canBeNull = false)
    private int pocetnaStrana;

    //Atribut za jedinicni kraj veze izmedju klasa Oblast i Knjiga
    @DatabaseField(foreign = true,foreignAutoRefresh = false)
    private Knjiga knjiga;

    //Konstruktor bez parametara
    public Oblast() {
        //Obavezan za potrebe ORMLite-a
    }

    //Konstrutkor sa parametrima naziv i pocetnaStrana
    public Oblast(String naziv, int pocetnaStrana) {
        this.naziv = naziv;
        this.pocetnaStrana = pocetnaStrana;
    }

    // get i set metode
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getPocetnaStrana() {
        return pocetnaStrana;
    }

    public void setPocetnaStrana(int pocetnaStrana) {
        this.pocetnaStrana = pocetnaStrana;
    }

    public Knjiga getKnjiga() {
        return knjiga;
    }

    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    //Redefinisana toString metoda
    @Override
    public String toString() {
        return "Oblast{" +
                "id=" + id +
                ", naziv='" + naziv + '\'' +
                ", pocetnaStrana=" + pocetnaStrana +
                '}';
    }
}

