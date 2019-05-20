package admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UczenDane {
    private final StringProperty imie;
    private final StringProperty nazwisko;
    private final StringProperty data_ur;
    private final StringProperty login;
    private final StringProperty id;

    public UczenDane(String imie, String nazwisko, String login, String data_ur, String id){
        this.imie = new SimpleStringProperty(imie);
        this.nazwisko = new SimpleStringProperty(nazwisko);
        this.data_ur = new SimpleStringProperty(data_ur);
        this.login = new SimpleStringProperty(login);
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

    public String getLogin() {
        return login.get();
    }

    public StringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getData_ur() {
        return data_ur.get();
    }

    public StringProperty data_urProperty() {
        return data_ur;
    }

    public void setData_ur(String data_ur) {
        this.data_ur.set(data_ur);
    }

}
