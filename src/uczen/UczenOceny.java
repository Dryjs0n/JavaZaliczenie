package uczen;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UczenOceny {
    private final StringProperty przedmiot;
    private final StringProperty wartosc;

    public UczenOceny(String przedmiot,String wartosc){
        this.przedmiot = new SimpleStringProperty(przedmiot);
        this.wartosc = new SimpleStringProperty(wartosc);
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
}
