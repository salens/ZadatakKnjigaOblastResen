package model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Resenje zadatak 2.1.1
 */
@DatabaseTable(tableName = "knjiga")//Anotacija za tabelu knjiga
public class Knjiga {

    //Staticki atributi
    public static final String POLJE_NASLOV="naslov";
    public static final String POLJE_BROJ_STRANA="broj_strana";
    public static final String POLJE_DATUM_IZDANJA="datum_izdanja";

    //atributi za upisu u bazu
    @DatabaseField(generatedId = true)//primarni kljuc koji se automatski generise
    private int id;
    @DatabaseField(columnName = POLJE_NASLOV)
    private String naslov;
    @DatabaseField(columnName = POLJE_BROJ_STRANA)
    private int brojStrana;
    @DatabaseField(columnName = POLJE_DATUM_IZDANJA)
    private Date datumIzdanja;

    //Atribut koji ne treba da se upisuje u bazu,
    // ne navodi se anotacija DatabaseField
    //Potrebno je inicijalizovati na true jer je knjiga odmah prisutna
    private Boolean prisutna = true;

    //Atribut za vise kraj veze izmendju klasa Oblast i Knjiga
    @ForeignCollectionField(foreignFieldName = "knjiga")
    private ForeignCollection<Oblast> oblasti;

    //Konstrutkor bez parametara
    public Knjiga(){
        //Obavezan za potrebe ORMLite-a
    }

    //Konstrutkor koji ocekuje parametre naslov, brojStrana i datumIzdanja
    public Knjiga(String naslov, int brojStrana, Date datumIzdanja) {
        this.naslov = naslov;
        this.brojStrana = brojStrana;
        this.datumIzdanja = datumIzdanja;
    }

    //get i set metode
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public int getBrojStrana() {
        return brojStrana;
    }

    public void setBrojStrana(int brojStrana) {
        this.brojStrana = brojStrana;
    }

    public Date getDatumIzdanja() {
        return datumIzdanja;
    }

    public void setDatumIzdanja(Date datumIzdanja) {
        this.datumIzdanja = datumIzdanja;
    }

    public Boolean isPrisutna() {
        return prisutna;
    }

    public void setPrisutna(Boolean prisutna) {
        this.prisutna = prisutna;
    }

    public ForeignCollection<Oblast> getOblasti() {
        return oblasti;
    }

    public void setOblasti(ForeignCollection<Oblast> oblasti) {
        this.oblasti = oblasti;
    }

    //Redefinisana metoda toString
    @Override
    public String toString() {
        return "Knjiga{" +
                "naslov='" + naslov + '\'' +
                ", brojStrana=" + brojStrana +
                ", datumIzdanja=" + datumIzdanja +
                '}';
    }
}
