package nauczyciel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UczniowieDane {
    private final StringProperty imie;
    private final StringProperty nazwisko;
    private final StringProperty login;

    public UczniowieDane(String imie, String nazwisko, String login){
        this.imie = new SimpleStringProperty(imie);
        this.nazwisko = new SimpleStringProperty(nazwisko);
        this.login = new SimpleStringProperty(login);
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
}
