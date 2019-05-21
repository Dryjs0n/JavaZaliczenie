package admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UczenNauczDane {
    private final StringProperty uimie;
    private final StringProperty unazwisko;
    private final StringProperty nimie;
    private final StringProperty nnazwisko;
    private final StringProperty uid;
    private final StringProperty nid;


    public UczenNauczDane(String uimie, String unazwisko, String nimie, String nnazwisko, String uid, String nid){
        this.uimie = new SimpleStringProperty(uimie);
        this.unazwisko = new SimpleStringProperty(unazwisko);
        this.nimie = new SimpleStringProperty(nimie);
        this.nnazwisko = new SimpleStringProperty(nnazwisko);
        this.uid = new SimpleStringProperty(uid);
        this.nid = new SimpleStringProperty(nid);

    }

    public String getUimie() {
        return uimie.get();
    }

    public StringProperty uimieProperty() {
        return uimie;
    }

    public void setUimie(String uimie) {
        this.uimie.set(uimie);
    }

    public String getUnazwisko() {
        return unazwisko.get();
    }

    public StringProperty unazwiskoProperty() {
        return unazwisko;
    }

    public void setUnazwisko(String unazwisko) {
        this.unazwisko.set(unazwisko);
    }

    public String getNimie() {
        return nimie.get();
    }

    public StringProperty nimieProperty() {
        return nimie;
    }

    public void setNimie(String nimie) {
        this.nimie.set(nimie);
    }

    public String getNnazwisko() {
        return nnazwisko.get();
    }

    public StringProperty nnazwiskoProperty() {
        return nnazwisko;
    }

    public void setNnazwisko(String nnazwisko) {
        this.nnazwisko.set(nnazwisko);
    }

    public String getUid() {
        return uid.get();
    }

    public StringProperty uidProperty() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid.set(uid);
    }

    public String getNid() {
        return nid.get();
    }

    public StringProperty nidProperty() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid.set(nid);
    }
}
