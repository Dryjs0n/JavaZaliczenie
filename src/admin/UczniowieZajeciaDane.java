package admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UczniowieZajeciaDane {
    private final StringProperty imie;
    private final StringProperty nazwisko;
    private final StringProperty nazwa;
    private final StringProperty nr;
    private final StringProperty idu;
    private final StringProperty idn;

    public UczniowieZajeciaDane(String imie, String nazwisko, String nazwa, String nr, String idu, String idn){
        this.imie = new SimpleStringProperty(imie);
        this.nazwisko = new SimpleStringProperty(nazwisko);
        this.nazwa = new SimpleStringProperty(nazwa);
        this.nr = new SimpleStringProperty(nr);
        this.idu = new SimpleStringProperty(idu);
        this.idn = new SimpleStringProperty(idn);

    }

    public String getImie() {
        return imie.get();
    }

    public StringProperty imieProperty() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie.set(imie);
    }

    public String getNazwisko() {
        return nazwisko.get();
    }

    public StringProperty nazwiskoProperty() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko.set(nazwisko);
    }

    public String getNazwa() {
        return nazwa.get();
    }

    public StringProperty nazwaProperty() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa.set(nazwa);
    }

    public String getNr() {
        return nr.get();
    }

    public StringProperty nrProperty() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr.set(nr);
    }

    public String getIdu() {
        return idu.get();
    }

    public StringProperty iduProperty() {
        return idu;
    }

    public void setIdu(String idu) {
        this.idu.set(idu);
    }

    public String getIdn() {
        return idn.get();
    }

    public StringProperty idnProperty() {
        return idn;
    }

    public void setIdn(String idn) {
        this.idn.set(idn);
    }
}
