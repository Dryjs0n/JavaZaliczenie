package admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LogowanieDane {
    private final StringProperty login;
    private final StringProperty haslo;
    private final StringProperty grupa;

    public LogowanieDane(String login, String haslo, String grupa){
        this.login = new SimpleStringProperty(login);
        this.haslo = new SimpleStringProperty(haslo);
        this.grupa = new SimpleStringProperty(grupa);
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

    public String getHaslo() {
        return haslo.get();
    }

    public StringProperty hasloProperty() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo.set(haslo);
    }

    public String getGrupa() {
        return grupa.get();
    }

    public StringProperty grupaProperty() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa.set(grupa);
    }
}

