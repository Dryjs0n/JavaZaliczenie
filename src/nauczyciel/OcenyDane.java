package nauczyciel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OcenyDane {
    private final StringProperty imie;
    private final StringProperty nazwisko;
    private final StringProperty przedmiot;
    private final StringProperty wartosc;
    private final StringProperty id;

    public OcenyDane(String imie, String nazwisko, String przedmiot, String wartosc, String id){
        this.imie = new SimpleStringProperty(imie);
        this.nazwisko = new SimpleStringProperty(nazwisko);
        this.przedmiot = new SimpleStringProperty(przedmiot);
        this.wartosc = new SimpleStringProperty(wartosc);
        this.id = new SimpleStringProperty(id);
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

    public String getPrzedmiot() {
        return przedmiot.get();
    }

    public StringProperty przedmiotProperty() {
        return przedmiot;
    }

    public void setPrzedmiot(String przedmiot) {
        this.przedmiot.set(przedmiot);
    }

    public String getWartosc() {
        return wartosc.get();
    }

    public StringProperty wartoscProperty() {
        return wartosc;
    }

    public void setWartosc(String wartosc) {
        this.wartosc.set(wartosc);
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
}
