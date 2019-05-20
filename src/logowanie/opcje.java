package logowanie;

public enum opcje {
    Admin, Nauczyciel , Uczen;

    private opcje(){}

    public String wartosc(){
        return name();
    }

    public static opcje zWartosci(String v){
        return valueOf(v);
    }
}
