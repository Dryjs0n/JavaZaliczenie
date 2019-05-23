package admin;


import polaczenie.Polaczenie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logowanie.Logowanie;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable  {
//
//    UCZEN DANE
//

    @FXML
    private TableColumn uczenIDCol;
    @FXML
    private TextField imie;
    @FXML
    private TextField nazwisko;
    @FXML
    private TextField login;
    @FXML
    private DatePicker data_ur;
    @FXML
    private TableView<UczenDane> uczenTable;
    @FXML
    private TableColumn<UczenDane, String> imieColumn;
    @FXML
    private TableColumn<UczenDane, String> nazwiskoColumn;
    @FXML
    private TableColumn<UczenDane, String> loginColumn;
    @FXML
    private TableColumn<UczenDane, String> data_urColumn;

    @FXML
    private Label zwrotUczen;
    @FXML
    private Label zwrotUczenDel;

//
//    NAUCZYCIEL DANE
//
    @FXML
    private TextField imien;
    @FXML
    private TextField nazwiskon;
    @FXML
    private TextField loginn;
    @FXML
    private DatePicker data_urn;
    @FXML
    private TextField pensja;

    @FXML
    private TableView<NauczycielDane> nauczTable;
    @FXML
    private TableColumn<NauczycielDane, String> nimieCol;
    @FXML
    private TableColumn<NauczycielDane, String> nnazwiskoCol;
    @FXML
    private TableColumn<NauczycielDane, String> nloginCol;
    @FXML
    private TableColumn<NauczycielDane, String> ndata_urCol;
    @FXML
    private TableColumn<NauczycielDane, String> pensjaCol;

    @FXML
    private Label zwrotLogowanie;



    private Polaczenie dc;
    private ObservableList<UczenDane> dane;
    private ObservableList<NauczycielDane> nauczdane;
    private ObservableList<LogowanieDane> logdane;

    private String sql = "SELECT imie, nazwisko,  logowanie_login,data_ur, id FROM uczen";
    private String sqln = "SELECT id,imie, nazwisko, data_ur, pensja, logowanie_login FROM nauczyciel";
    private String sqll = "SELECT * FROM logowanie";

    public void initialize(URL url, ResourceBundle rb){
        this.dc = new Polaczenie();
        try{
            zaladujDaneUczniow();
            zaladujZajecia();
            zaladujDaneNauczycieli();
            zaladujDaneUzytkownikow();
            zaladujPrzypisania();
            zaladujPrzypisaniaZajec();
            this.wybierzZajeciaPrzedmiot.setItems(FXCollections.observableArrayList(przedmioty));
            this.wybierzZajeciaNr.setItems(FXCollections.observableArrayList(numery));

        }catch(Exception e){
            e.printStackTrace();

        }


    }

    @FXML
    private TableColumn nauczIDCol;

    @FXML
    private void zaladujDaneNauczycieli() throws SQLException{
        try{
            Connection conn = Polaczenie.getConnection();
            this.nauczdane = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(sqln);
            while(rs.next()){
                this.nauczdane.add(new NauczycielDane(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));
            }
            zwrotNauczDel.setText("");
            zwrotNaucz.setText("");
        }catch(SQLException e){
            e.printStackTrace();
        }

        this.nimieCol.setCellValueFactory(new PropertyValueFactory<NauczycielDane, String>("imie"));
        this.nnazwiskoCol.setCellValueFactory(new PropertyValueFactory<NauczycielDane, String>("nazwisko"));
        this.ndata_urCol.setCellValueFactory(new PropertyValueFactory<NauczycielDane, String>("data_ur"));
        this.pensjaCol.setCellValueFactory(new PropertyValueFactory<NauczycielDane, String>("pensja"));
        this.nloginCol.setCellValueFactory(new PropertyValueFactory<NauczycielDane, String>("login"));
        this.nauczIDCol.setCellValueFactory(new PropertyValueFactory<NauczycielDane, String>("id"));

        this.nimieCol.setResizable(false);
        this.nnazwiskoCol.setResizable(false);
        this.ndata_urCol.setResizable(false);
        this.pensjaCol.setResizable(false);
        this.nloginCol.setResizable(false);
        this.nauczIDCol.setResizable(false);

        this.nauczTable.setItems(null);
        this.nauczTable.setItems(this.nauczdane);
    }

    @FXML
    private void zaladujDaneUczniow() throws SQLException{
        try{
            Connection conn = Polaczenie.getConnection();
            this.dane = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(sql);
            while(rs.next()){
                this.dane.add(new UczenDane(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));

            }
            zwrotUczen.setText("");
            zwrotUczenDel.setText("");
        }catch(SQLException e){
            e.printStackTrace();
        }


        this.imieColumn.setCellValueFactory(new PropertyValueFactory<UczenDane, String>("imie"));
        this.nazwiskoColumn.setCellValueFactory(new PropertyValueFactory<UczenDane, String>("nazwisko"));
        this.data_urColumn.setCellValueFactory(new PropertyValueFactory<UczenDane, String>("data_ur"));
        this.loginColumn.setCellValueFactory(new PropertyValueFactory<UczenDane, String>("login"));
        this.uczenIDCol.setCellValueFactory(new PropertyValueFactory<UczenDane, String>("id"));


        this.imieColumn.setResizable(false);
        this.nazwiskoColumn.setResizable(false);
        this.data_urColumn.setResizable(false);
        this.loginColumn.setResizable(false);
        this.uczenIDCol.setResizable(false);

        this.uczenTable.setItems(null);
        this.uczenTable.setItems(this.dane);
    }

    @FXML
    private void dodajUcznia(ActionEvent event){
        String sql1 = "INSERT INTO logowanie(login, grupa) VALUES(?, 'Uczen')";
        String sql2 = "INSERT INTO uczen(imie, nazwisko, data_ur, logowanie_login) VALUES(?,?,?,?)";
        if(this.login.getText().isEmpty()||this.imie.getText().isEmpty()||this.nazwisko.getText().isEmpty()||this.data_ur.getEditor().getText().isEmpty()||this.login.getText().isEmpty()) {
            zwrotUczen.setText("Jedno lub więcej pól zostało puste. Proszę poprawić.");
        }else{
            try {
            Connection conn = Polaczenie.getConnection();
            PreparedStatement stmt1 = conn.prepareStatement(sql1);
            PreparedStatement stmt2 = conn.prepareStatement(sql2);

            stmt1.setString(1, this.login.getText());
            stmt2.setString(1, this.imie.getText());
            stmt2.setString(2, this.nazwisko.getText());
            stmt2.setString(3, this.data_ur.getEditor().getText());
            stmt2.setString(4, this.login.getText());

            stmt1.execute();
            stmt2.execute();
            conn.close();

            this.imie.setText("");
            this.nazwisko.setText("");
            this.data_ur.setValue(null);
            this.login.setText("");

            zaladujDaneUczniow();
            zwrotUczen.setText("Dodano ucznia");



        } catch (SQLException e) {
            e.printStackTrace();
        }

        }


    }

    @FXML
    private Label zwrotNaucz;
    @FXML
    private Label zwrotNauczDel;

    @FXML
    private void dodajNauczyciela(ActionEvent event){
        String sql1 = "INSERT INTO logowanie(login, grupa) VALUES(?, 'Nauczyciel')";
        String sql2 = "INSERT INTO nauczyciel(imie, nazwisko, data_ur, pensja,logowanie_login) VALUES(?,?,?,?,?)";
        if(this.loginn.getText().isEmpty()||this.imien.getText().isEmpty()||this.nazwiskon.getText().isEmpty()||this.data_urn.getEditor().getText().isEmpty()||this.loginn.getText().isEmpty()){
            zwrotNaucz.setText("Jedno lub więcej pól zostało puste. Proszę poprawić.");
        }
        else{
            if(this.pensja.getText().isEmpty()==false) {
                String pensjas = this.pensja.getText();
                int pensja = Integer.parseInt(pensjas);

            if(pensja<0){
                zwrotNaucz.setText("Pensja nie może być mniejsza niż 0!");}
            }else {

            try {
                Connection conn = Polaczenie.getConnection();
                PreparedStatement stmt1 = conn.prepareStatement(sql1);
                PreparedStatement stmt2 = conn.prepareStatement(sql2);

                stmt1.setString(1, this.loginn.getText());
                stmt2.setString(1, this.imien.getText());
                stmt2.setString(2, this.nazwiskon.getText());
                stmt2.setString(3, this.data_urn.getEditor().getText());
                if(this.pensja.getText().isEmpty()) {
                    stmt2.setString(4, "2000");
                }else {
                    stmt2.setString(4, this.pensja.getText());
                }
                stmt2.setString(5, this.loginn.getText());

                stmt1.execute();
                stmt2.execute();
                conn.close();
                zaladujDaneNauczycieli();
                zwrotNaucz.setText("Pomyślnie dodano nauczyciela");


                this.imien.setText("");
                this.nazwiskon.setText("");
                this.data_urn.setValue(null);
                this.pensja.setText("");
                this.loginn.setText("");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            }
        }
    }

    ObservableList<LogowanieDane> uzytkownicy;

    @FXML
    private void dodajUzytkownika(){
        uzytkownicy = logowanieTab.getSelectionModel().getSelectedItems();




        String sql = "UPDATE logowanie SET haslo=? WHERE login=?";
        if( this.haslo.getText().isEmpty()||uzytkownicy.isEmpty()) {
            zwrotLogowanie.setText("Nie wybrano rekordu lub hasło jest puste!");
        }else{try {
            String login = uzytkownicy.get(0).getLogin();
            Connection conn = Polaczenie.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(2, login);
            stmt.setString(1, this.haslo.getText());


            stmt.execute();
            conn.close();

            wyczyscPolaL();
            this.haslo.setText("");


            zaladujDaneUzytkownikow();
            zwrotLogowanie.setText("Hasło zostało zmienione!");


        } catch (SQLException e) {
            e.printStackTrace();

        }

        }
    }

    ObservableList<UczenDane> uczniowie;


    @FXML
    private void usunUcznia(){
        String id = "";
        String login = "";
        uczniowie = uczenTable.getSelectionModel().getSelectedItems();

        if(uczniowie.isEmpty()==false) {


            id = uczniowie.get(0).getId();
            login = uczniowie.get(0).getLogin();


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdzenie");
            alert.setHeaderText("Czy na pewno chcesz usunąć ucznia?");

            ButtonType bt1 = new ButtonType("Tak");
            ButtonType bt2 = new ButtonType("Nie");

            alert.getButtonTypes().setAll(bt1, bt2);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == bt1) {
                String sql1 = "DELETE FROM uczen WHERE id=?";
                String sql2 = "DELETE FROM logowanie WHERE login=?";
                String sql3 = "DELETE FROM ocena WHERE uczen_id=?";
                String sql4 = "DELETE FROM przypisanieZajec WHERE uczen_id=?";
                String sql5 = "DELETE FROM uczenNauczyciel WHERE uczen_id=?";
                int u_id;

                try {
                    Connection conn = Polaczenie.getConnection();
//                ResultSet resultq = stmtq.executeQuery("SELECT * FROM uczen WHERE login=?");


                    PreparedStatement stmt1 = conn.prepareStatement(sql1);
                    PreparedStatement stmt2 = conn.prepareStatement(sql2);
                    PreparedStatement stmt3 = conn.prepareStatement(sql3);
                    PreparedStatement stmt4 = conn.prepareStatement(sql4);
                    PreparedStatement stmt5 = conn.prepareStatement(sql5);

                    stmt1.setString(1, id);
                    stmt2.setString(1, login);
                    stmt3.setString(1, id);
                    stmt4.setString(1, id);
                    stmt5.setString(1, id);


                    stmt1.execute();
                    stmt2.execute();
                    stmt3.execute();
                    stmt4.execute();
                    stmt5.execute();

                    conn.close();

//                        this.uczenDel.setText("");
                    zaladujDaneUczniow();

                    zwrotUczenDel.setText("Pomyślnie usunięto ucznia");


                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
            }
        }else{
            this.zwrotUczenDel.setText("Nie wybrano wiersza.");
        }

    }

//    @FXML
//    private void usunUzytkownika(ActionEvent event){
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Potwierdzenie");
//        alert.setHeaderText("Czy na pewno chcesz usunąć użytkownika?");
//        alert.setContentText("Usuwając użytkownika usuniesz także ucznia/nauczyciela!");
//
//        ButtonType bt1 = new ButtonType("Tak");
//        ButtonType bt2 = new ButtonType("Nie");
//
//        alert.getButtonTypes().setAll(bt1,bt2);
//        Optional<ButtonType> result = alert.showAndWait();
//        if(result.get()==bt1) {
//            String sql = "DELETE FROM logowanie WHERE login=?";
//            String sql1 = "DELETE FROM uczen WHERE logowanie_login=?";
//            String sql2 = "DELETE FROM nauczyciel WHERE logowanie_login=?";
////            String sql3 = "DELETE FROM uczenNauczyciel WHERE uczen_id=?";
////            int uczen_id;
//            try {
//                Connection conn = Polaczenie.getConnection();
////                Statement stmtq;
////                stmtq = conn.createStatement();
////                ResultSet resultq = stmtq.executeQuery("SELECT id FROM uczen WHERE login="+this.loginDel);
////                while(resultq.next()){
////                    uczen_id = resultq.getInt("id");
////
////                }
////                String uczen_ids = Integer.toString(uczen_id);
//
//
//
//                PreparedStatement stmt = conn.prepareStatement(sql);
//                PreparedStatement stmt1 = conn.prepareStatement(sql1);
//                PreparedStatement stmt2 = conn.prepareStatement(sql2);
////                PreparedStatement stmt3 = conn.prepareStatement(sql3);
//                stmt.setString(1, this.loginDel.getText());
////                stmt.setString(1, uczen_ids);
//                stmt1.setString(1, this.loginDel.getText());
//                stmt2.setString(1, this.loginDel.getText());
////                stmt3.setString(1, uczen_ids);
//                stmt.execute();
//                stmt1.execute();
//                stmt2.execute();
////                stmt3.execute();
//                conn.close();
//                this.loginDel.setText("");
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }else{}
//    }

    @FXML
    private void wyczyscPola(ActionEvent event){
        this.imie.setText("");
        this.nazwisko.setText("");
        this.data_ur.setValue(null);
        this.login.setText("");

    }
    @FXML
    private void wyczyscPolaN(ActionEvent event){
        this.imien.setText("");
        this.nazwiskon.setText("");
        this.data_urn.setValue(null);
        this.pensja.setText("");
        this.loginn.setText("");

    }

    ObservableList<NauczycielDane> nauczyciele;

    @FXML
    private void usunNauczyciela(){
        String id ="";
        String login;

        nauczyciele=nauczTable.getSelectionModel().getSelectedItems();
        if(nauczyciele.isEmpty()==false){
        id = nauczyciele.get(0).getId();
        login = nauczyciele.get(0).getLogin();



            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdzenie");
            alert.setHeaderText("Czy na pewno chcesz usunąć nauczyciela?");

            ButtonType bt1 = new ButtonType("Tak");
            ButtonType bt2 = new ButtonType("Nie");

            alert.getButtonTypes().setAll(bt1, bt2);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == bt1) {
                String sql1 = "DELETE FROM nauczyciel WHERE logowanie_login=?";
                String sql2 = "DELETE FROM logowanie WHERE login=?";
                String sql3 = "DELETE FROM zajecia WHERE nauczyciel_id=?";
                String sql4 = "DELETE FROM uczenNauczyciel WHERE nauczyciel_id=?";

                try {
                    Connection conn = Polaczenie.getConnection();

                    Statement stmtq = conn.createStatement();
//                ResultSet resultq = stmtq.executeQuery("SELECT * FROM uczen WHERE login=?");





                        PreparedStatement stmt1 = conn.prepareStatement(sql1);
                        PreparedStatement stmt2 = conn.prepareStatement(sql2);
                        PreparedStatement stmt3 = conn.prepareStatement(sql3);
                        PreparedStatement stmt4 = conn.prepareStatement(sql4);


                        stmt1.setString(1, login);
                        stmt2.setString(1, login);
                        stmt3.setString(1, id);
                        stmt4.setString(1, id);

                        stmt1.execute();
                        stmt2.execute();
                        stmt3.execute();
                        stmt4.execute();
                        conn.close();

                        zaladujDaneNauczycieli();
                        zwrotNauczDel.setText("Pomyślnie usunięto nauczyciela!");


                } catch (SQLException e) {
                    e.printStackTrace();
                }

        }
        }else{
            zwrotNauczDel.setText("Nie wybrano wiersza.");
        }
    }




//    ObservableList<String> grupy = FXCollections.observableArrayList("Nauczyciel","Uczen");


    @FXML
    private PasswordField haslo;
    @FXML
    private TableView logowanieTab;
    @FXML
    private TextField loginDel;
    @FXML
    private TableColumn<LogowanieDane,String> loginlCol;
    @FXML
    private TableColumn<LogowanieDane,String> haslolCol;
    @FXML
    private TableColumn<LogowanieDane,String> grupaCol;


    @FXML
    private void zaladujDaneUzytkownikow() throws SQLException{
        try{
            Connection conn = Polaczenie.getConnection();
            this.logdane = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(sqll);
            while(rs.next()){
                this.logdane.add(new LogowanieDane(rs.getString(1),rs.getString(2),rs.getString(3)));
            }
            zwrotLogowanie.setText("");
        }catch(SQLException e){
            e.printStackTrace();
        }

        this.loginlCol.setCellValueFactory(new PropertyValueFactory<LogowanieDane, String>("login"));
        this.haslolCol.setCellValueFactory(new PropertyValueFactory<LogowanieDane, String>("haslo"));
        this.grupaCol.setCellValueFactory(new PropertyValueFactory<LogowanieDane, String>("grupa"));

        this.loginlCol.setResizable(false);
        this.haslolCol.setResizable(false);
        this.grupaCol.setResizable(false);

        this.logowanieTab.setItems(null);
        this.logowanieTab.setItems(this.logdane);
    }

    @FXML
    private void wyczyscPolaL(){
        this.haslo.setText("");
    }
//    @FXML
//    private void wyczyscPolaDelL(ActionEvent event){
//        this.loginDel.setText("");
//    }

    @FXML
    private Button logoutucz;
    @FXML
    private Button logoutnaucz;

    public void wylogujUcz(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setHeaderText("Czy chcesz się wylogować?");

        ButtonType bt1 = new ButtonType("Tak");
        ButtonType bt2 = new ButtonType("Nie");

        alert.getButtonTypes().setAll(bt1, bt2);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == bt1) {
            try {
                Stage loginStage = (Stage) this.logoutucz.getScene().getWindow();
                loginStage.close();
                Logowanie.window.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
        }
    }

    @FXML
    private TableView zajTab;
    @FXML
    private TableColumn zajnazwaCol;
    @FXML
    private TableColumn zajnrCol;
    @FXML
    private TableColumn zajimieCol;
    @FXML
    private TableColumn zajnazwCol;
    @FXML
    private Label zwrotZajecia;
    @FXML
    private Label zwrotZajeciaDel;


    private ObservableList<ZajeciaDane> zajeciadane;

//    private
    @FXML
    private TableColumn zajIDCol;

    @FXML
    private void zaladujZajecia(){
        String sqlz = "SELECT z.nazwa, z.nr_pokoju, n.imie, n.nazwisko, z.id FROM zajecia z INNER JOIN nauczyciel n ON z.nauczyciel_id = n.id";
//        String sqlz = "SELECT * FROM zajecia";
        try{
            Connection conn = Polaczenie.getConnection();
            this.zajeciadane = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(sqlz);
            while(rs.next()){
                this.zajeciadane.add(new ZajeciaDane(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
            }

        }catch (SQLException e ){
            e.printStackTrace();
        }
        this.zajnazwaCol.setCellValueFactory(new PropertyValueFactory<ZajeciaDane, String>("nazwa"));
        this.zajnrCol.setCellValueFactory(new PropertyValueFactory<ZajeciaDane, String>("nr_pokoju"));
        this.zajimieCol.setCellValueFactory(new PropertyValueFactory<ZajeciaDane, String>("imie"));
        this.zajnazwCol.setCellValueFactory(new PropertyValueFactory<ZajeciaDane, String>("nazwisko"));
        this.zajIDCol.setCellValueFactory(new PropertyValueFactory<ZajeciaDane, String>("id"));

        this.zajnazwaCol.setResizable(false);
        this.zajnrCol.setResizable(false);
        this.zajimieCol.setResizable(false);
        this.zajnazwCol.setResizable(false);


        this.zajTab.setItems(null);
        this.zajTab.setItems(this.zajeciadane);
        this.wybierzZajeciaNr.setPromptText("Numer pokoju");
        this.wybierzZajeciaPrzedmiot.setPromptText("Przedmiot");

        this.zwrotZajecia.setText("");
        this.zwrotZajDel.setText("");


    }

    @FXML
    private Button usunZajecia;

    ObservableList<ZajeciaDane> zajecia;

    @FXML
    private Label zwrotZajDel;

    @FXML
    private void usunZajecia(){
        String id = "";
        String sql = "DELETE FROM zajecia WHERE id=?";
        String sql2 = "DELETE FROM przypisanieZajec WHERE zajecia_id=?";

        zajecia = zajTab.getSelectionModel().getSelectedItems();
        if(zajecia.isEmpty()==false) {
//
            id = zajecia.get(0).getId();
//        System.out.println(id);
            if (id.isEmpty() == false) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Potwierdzenie");
                alert.setHeaderText("Czy na pewno chcesz usunąć zajęcia?");

                ButtonType bt1 = new ButtonType("Tak");
                ButtonType bt2 = new ButtonType("Nie");

                alert.getButtonTypes().setAll(bt1, bt2);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == bt1) {
                    try {
                        Connection conn = Polaczenie.getConnection();
                        PreparedStatement resultq = conn.prepareStatement(sql);
                        PreparedStatement resultq2 = conn.prepareStatement(sql2);
                        resultq.setString(1, id);
                        resultq2.setString(1, id);

                        resultq.execute();
                        resultq2.execute();
                        conn.close();
//                System.out.println("usunieto ocene");
                        zaladujZajecia();

                        this.zwrotZajDel.setText("Usunięto zajęcia");


                    } catch (Exception e) {

                        e.printStackTrace();
                    }


                } else {
                }
            } else {
                this.zwrotZajDel.setText("Nie wybrano oceny");
            }
        }else{
            this.zwrotZajDel.setText("Nie wybrano rekordu.");
        }
    }

    @FXML
    private ComboBox<String> wybierzZajeciaNr;
    ObservableList<String> numery = FXCollections.observableArrayList("3","123","2323","4123","4","2","5","532","512","634","514");

    @FXML
    private ComboBox<String> wybierzZajeciaPrzedmiot;
    ObservableList<String> przedmioty = FXCollections.observableArrayList("Matematyka","Fizyka","Polski","Angielski","Francuski");


    @FXML
    private void dodajZajecia(){
        String sql = "INSERT INTO zajecia (nazwa, nr_pokoju, nauczyciel_id) VALUES(?,?,?)";
        nauczyciele=  nauczTable.getSelectionModel().getSelectedItems();


            if (nauczyciele.isEmpty()||this.wybierzZajeciaNr.getValue().isEmpty() || this.wybierzZajeciaPrzedmiot.getValue().isEmpty()) {
                this.zwrotZajecia.setText("Jedno lub więcej pól zostalo puste lub nie wybrano rekordu w tabeli \"Nauczyciele\"");
            } else {
                String id = nauczyciele.get(0).getId();
                try {
                    Connection conn = Polaczenie.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, this.wybierzZajeciaPrzedmiot.getValue());
                    stmt.setString(2, this.wybierzZajeciaNr.getValue());
                    stmt.setString(3, id);

                    stmt.execute();
                    conn.close();
                    zaladujZajecia();
                    zwrotZajecia.setText("Zajęcia zostały dodane.");

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


    }

    @FXML
    private TableView<UczenNauczDane> uczenNauczTab;
    @FXML
    private TableColumn uczenNauczUImieCol;
    @FXML
    private TableColumn uczenNauczUNazwCol;
    @FXML
    private TableColumn uczenNauczNImieCol;
    @FXML
    private TableColumn uczenNauczNNazwCol;
    @FXML
    private TableColumn uczenNauczUIDCol;
    @FXML
    private TableColumn uczenNauczNIDCol;
    @FXML
    private Label zwrotPrzypisanie;
    @FXML
    private Label zwrotPrzypisanieDel;


    private ObservableList<UczenNauczDane> uczenNauczDane;

    @FXML
    private void zaladujPrzypisania(){
        String sql ="SELECT u.imie AS uimie,u.nazwisko AS unazw,n.imie AS nimie, n.nazwisko AS nnazw,u.id AS uid, n.id AS nid FROM uczen u INNER JOIN nauczyciel n INNER JOIN uczenNauczyciel un ON u.id = uczen_id AND n.id = nauczyciel_id";
        try{
            Connection conn = Polaczenie.getConnection();
            this.uczenNauczDane = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(sql);
            while(rs.next()) {
                this.uczenNauczDane.add(new UczenNauczDane(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }
            conn.close();
            this.zwrotPrzypisanieDel.setText("");
            this.zwrotPrzypisanie.setText("");
        }catch(SQLException e){
            e.printStackTrace();
        }
        this.uczenNauczUIDCol.setCellValueFactory(new PropertyValueFactory<UczenNauczDane, String>("uid"));
        this.uczenNauczNIDCol.setCellValueFactory(new PropertyValueFactory<UczenNauczDane, String>("nid"));
        this.uczenNauczUImieCol.setCellValueFactory(new PropertyValueFactory<UczenNauczDane, String>("uimie"));
        this.uczenNauczUNazwCol.setCellValueFactory(new PropertyValueFactory<UczenNauczDane, String>("unazwisko"));
        this.uczenNauczNImieCol.setCellValueFactory(new PropertyValueFactory<UczenNauczDane, String>("nimie"));
        this.uczenNauczNNazwCol.setCellValueFactory(new PropertyValueFactory<UczenNauczDane, String>("nnazwisko"));

        this.uczenNauczUIDCol.setResizable(false);
        this.uczenNauczNIDCol.setResizable(false);
        this.uczenNauczUImieCol.setResizable(false);
        this.uczenNauczUNazwCol.setResizable(false);
        this.uczenNauczNImieCol.setResizable(false);
        this.uczenNauczNNazwCol.setResizable(false);


        this.uczenNauczTab.setItems(null);
        this.uczenNauczTab.setItems(this.uczenNauczDane);
    }


    ObservableList<UczniowieZajeciaDane> uczzajdane;



    @FXML
    private TableView<UczniowieZajeciaDane> uczZajTab;
    @FXML
    private TableColumn uczZajImieCol;
    @FXML
    private TableColumn uczZajNazwCol;
    @FXML
    private TableColumn uczZajNrCol;
    @FXML
    private TableColumn uczZajNazwaCol;
    @FXML
    private TableColumn uczZajIDUCol;
    @FXML
    private TableColumn uczZajIDNCol;




    @FXML
    private void zaladujPrzypisaniaZajec(){
        String sql ="SELECT u.imie, u.nazwisko, z.nazwa, z.nr_pokoju, u.id, z.id FROM uczen u INNER JOIN zajecia z INNER JOIN przypisanieZajec p ON u.id = p.uczen_id AND z.id = p.zajecia_id";
        try{
            Connection conn = Polaczenie.getConnection();
            this.uczzajdane = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(sql);
            while(rs.next()) {
               this.uczzajdane.add(new UczniowieZajeciaDane(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }
            conn.close();
            this.zwrotUZ.setText("");
            this.zwrotUZDel.setText("");
        }catch(SQLException e){
            e.printStackTrace();
        }

        this.uczZajImieCol.setCellValueFactory(new PropertyValueFactory<UczniowieZajeciaDane, String>("imie"));
        this.uczZajNazwCol.setCellValueFactory(new PropertyValueFactory<UczniowieZajeciaDane, String>("nazwisko"));
        this.uczZajNazwaCol.setCellValueFactory(new PropertyValueFactory<UczniowieZajeciaDane, String>("nazwa"));
        this.uczZajNrCol.setCellValueFactory(new PropertyValueFactory<UczniowieZajeciaDane, String>("nr"));
        this.uczZajIDUCol.setCellValueFactory(new PropertyValueFactory<UczniowieZajeciaDane, String>("idu"));
        this.uczZajIDNCol.setCellValueFactory(new PropertyValueFactory<UczniowieZajeciaDane, String>("idn"));

        this.uczZajTab.setItems(null);
        this.uczZajTab.setItems(this.uczzajdane);
    }

    @FXML
    private Label zwrotUZ;
    @FXML
    private Label zwrotUZDel;

    @FXML
    private void dodajUZ(){
        String sql = "INSERT INTO przypisanieZajec (uczen_id, zajecia_id) VALUES(?,?)";
        uczniowie = uczenTable.getSelectionModel().getSelectedItems();
        zajecia = zajTab.getSelectionModel().getSelectedItems();
        if(uczniowie.isEmpty()||zajecia.isEmpty()){
            this.zwrotUZ.setText("Nie wybrano odpowiednich rekordów.");
        }else {
            String uid = uczniowie.get(0).getId();
            String zid = zajecia.get(0).getId();
            try {
                Connection conn = Polaczenie.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, uid);
                stmt.setString(2, zid);

                stmt.execute();
                conn.close();

                zaladujPrzypisaniaZajec();
                this.zwrotUZ.setText("Przypisano zajęcia.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }



    @FXML
    private void przypiszUN(){
        uczniowie = uczenTable.getSelectionModel().getSelectedItems();
        nauczyciele = nauczTable.getSelectionModel().getSelectedItems();
        if(nauczyciele.isEmpty()||uczniowie.isEmpty()){
            this.zwrotPrzypisanie.setText("Nie wybrano odpowiednich rekordów.");
        }else {
            String uid = uczniowie.get(0).getId();
            String nid = nauczyciele.get(0).getId();
            String sql = "INSERT INTO uczenNauczyciel (uczen_id, nauczyciel_id) VALUES(?,?)";
            try {
                Connection conn = Polaczenie.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, uid);
                stmt.setString(2, nid);

                stmt.execute();
                conn.close();

                zaladujPrzypisania();
                this.zwrotPrzypisanie.setText("Pomyślnie dodano przypisanie.");


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }




    @FXML
    private void usunPrzypisanieUN(){
        uczenNauczDane = uczenNauczTab.getSelectionModel().getSelectedItems();
        if(uczenNauczDane.isEmpty()){
            this.zwrotPrzypisanieDel.setText("Nie wybrano rekordu");
        }else {
            String uczen_id = uczenNauczDane.get(0).getUid();
            String nauczyciel_id = uczenNauczDane.get(0).getNid();
            String sql = "DELETE FROM uczenNauczyciel WHERE uczen_id = ? AND nauczyciel_id = ?";
            try {
                Connection conn = Polaczenie.getConnection();

                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setString(1, uczen_id);
                stmt.setString(2, nauczyciel_id);
                stmt.execute();
                conn.close();

                zaladujPrzypisania();
                this.zwrotPrzypisanieDel.setText("Przypisanie usunięto pomyślnie.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void usunUZ(){
        uczzajdane = uczZajTab.getSelectionModel().getSelectedItems();
        if(uczzajdane.isEmpty()){
            zwrotUZDel.setText("Nie wybrano rekordu.");
        }else{
        String uczen_id = uczzajdane.get(0).getIdu();
        String zajecia_id = uczzajdane.get(0).getIdn();
        String sql = "DELETE FROM przypisanieZajec WHERE uczen_id = ? AND zajecia_id = ?";
        try{
            Connection conn = Polaczenie.getConnection();

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, uczen_id);
            stmt.setString(2,zajecia_id);
            stmt.execute();
            conn.close();

            zaladujPrzypisaniaZajec();
            this.zwrotUZDel.setText("Przypisanie usunięto pomyślnie.");
        }catch (SQLException e){
            e.printStackTrace();
        }

        }
    }

}
