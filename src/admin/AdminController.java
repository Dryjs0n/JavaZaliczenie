package admin;

import com.sun.org.glassfish.external.statistics.annotations.Reset;
import javafx.application.Application;
import javafx.application.Platform;
import logowanie.LogowanieApp;
import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logowanie.LogowanieApp;

import javax.swing.*;
import javax.xml.soap.Text;
import java.io.IOException;
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



    private dbConnection dc;
    private ObservableList<UczenDane> dane;
    private ObservableList<NauczycielDane> nauczdane;
    private ObservableList<LogowanieDane> logdane;

    private String sql = "SELECT imie, nazwisko,  logowanie_login,data_ur, id FROM uczen";
    private String sqln = "SELECT id,imie, nazwisko, data_ur, pensja, logowanie_login FROM nauczyciel";
    private String sqll = "SELECT * FROM logowanie";

    public void initialize(URL url, ResourceBundle rb){
        this.dc = new dbConnection();
        try{
            zaladujDaneUczniow();
            zaladujZajecia();
            zaladujDaneNauczycieli();
            zaladujDaneUzytkownikow();

        }catch(Exception e){
            e.printStackTrace();

        }


    }

    @FXML
    private TableColumn nauczIDCol;

    @FXML
    private void zaladujDaneNauczycieli() throws SQLException{
        try{
            Connection conn = dbConnection.getConnection();
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
            Connection conn = dbConnection.getConnection();
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
            Connection conn = dbConnection.getConnection();
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

            zwrotUczen.setText("Dodano ucznia");

            zaladujDaneUczniow();

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
            try {
                Connection conn = dbConnection.getConnection();
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

                zwrotNaucz.setText("Pomyślnie dodano nauczyciela");
                zaladujDaneNauczycieli();

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

    @FXML
    private void dodajUzytkownika(ActionEvent event){
        String sql = "UPDATE logowanie SET haslo=? WHERE login=?";
        if(this.loginl.getText().isEmpty() || this.haslo.getText().isEmpty()) {
            zwrotLogowanie.setText("Pole login lub hasło nie może być puste!");
        }else{try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(2, this.loginl.getText());
            stmt.setString(1, this.haslo.getText());


            stmt.execute();
            conn.close();

            this.loginl.setText("");
            this.haslo.setText("");

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
                    Connection conn = dbConnection.getConnection();
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
                        zwrotUczenDel.setText("Pomyślnie usunięto ucznia");
                        zaladujDaneUczniow();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
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
//                Connection conn = dbConnection.getConnection();
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
    private void usunNauczyciela(ActionEvent event){
        String id ="";
        String login;

        nauczyciele=nauczTable.getSelectionModel().getSelectedItems();
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
                    Connection conn = dbConnection.getConnection();

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


                        zwrotNauczDel.setText("Pomyślnie usunięto nauczyciela!");
                        zaladujDaneNauczycieli();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

        }
    }




//    ObservableList<String> grupy = FXCollections.observableArrayList("Nauczyciel","Uczen");

    @FXML
    private TextField loginl;
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
            Connection conn = dbConnection.getConnection();
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
    private void wyczyscPolaL(ActionEvent event){
        this.loginl.setText("");
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
                LogowanieApp.window.show();
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
            Connection conn = dbConnection.getConnection();
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
//
        id = zajecia.get(0).getId();
//        System.out.println(id);
        if(id.isEmpty()==false) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdzenie");
            alert.setHeaderText("Czy na pewno chcesz usunąć zajęcia?");

            ButtonType bt1 = new ButtonType("Tak");
            ButtonType bt2 = new ButtonType("Nie");

            alert.getButtonTypes().setAll(bt1, bt2);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == bt1) {
                try {
                    Connection conn = dbConnection.getConnection();
                    PreparedStatement resultq = conn.prepareStatement(sql);
                    PreparedStatement resultq2 = conn.prepareStatement(sql2);
                    resultq.setString(1, id);
                    resultq2.setString(1, id);

                    resultq.execute();
                    resultq2.execute();
                    conn.close();
//                System.out.println("usunieto ocene");
                    this.zwrotZajDel.setText("Usunięto zajęcia");


                } catch (Exception e) {

                    e.printStackTrace();
                }


            } else {
            }
        }else{this.zwrotZajDel.setText("Nie wybrano oceny");}

    }


}
