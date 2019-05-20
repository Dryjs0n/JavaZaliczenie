package admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sun.java2d.pipe.SpanShapeRenderer;

public class ZajeciaDane {
    private final StringProperty nazwa;
    private final StringProperty nr_pokoju;
    private final StringProperty imie;
    private final StringProperty nazwisko;
    private final StringProperty id;

    public ZajeciaDane(String nazwa, String nr_pokoju, String imie, String nazwisko, String id){
        this.nazwa = new SimpleStringProperty(nazwa);
        this.nr_pokoju = new SimpleStringProperty(nr_pokoju);
        this.imie = new SimpleStringProperty(imie);
        this.nazwisko = new SimpleStringProperty(nazwisko);
        this.id = new SimpleStringProperty(id);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
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

    public String getNr_pokoju() {
        return nr_pokoju.get();
    }

    public StringProperty nr_pokojuProperty() {
        return nr_pokoju;
    }

    public void setNr_pokoju(String nr_pokoju) {
        this.nr_pokoju.set(nr_pokoju);
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
}
