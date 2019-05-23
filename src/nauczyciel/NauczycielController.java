package nauczyciel;

import polaczenie.Polaczenie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logowanie.Logowanie;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class NauczycielController implements Initializable {
    private Polaczenie dc;

    @FXML
    private Label dziendobry;
    @FXML
    private Label dziendobry1;
    @FXML
    private Label dziendobry2;



    public void initialize(URL url, ResourceBundle rb){
        this.dc = new Polaczenie();
        przypiszImie();
        dziendobry.setText(imie_w);
        dziendobry1.setText(imie_w);
        dziendobry2.setText(imie_w);
        this.wyborWartosci.setItems(FXCollections.observableArrayList(wartosci));
        this.wyborPrzedmiot.setItems(FXCollections.observableArrayList(przedmioty));
        try{
            zaladujOceny();
            zaladujUczniow();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public static String login_w;

    private String imie_w;

    public void przypiszImie(){
        try {
            Connection conn = Polaczenie.getConnection();
            PreparedStatement result = conn.prepareStatement("SELECT imie FROM nauczyciel WHERE logowanie_login=?");
            result.setString(1,login_w);
            ResultSet rs = result.executeQuery();
            if(rs.next()){
                imie_w=rs.getString(1);
                conn.close();
            }
        }catch (SQLException e ){
            e.printStackTrace();
        }
    }


    private ObservableList<UczniowieDane> uczniowieDane;

    private ObservableList<OcenyDane> ocenyDane;

    @FXML
    private TableView uczniowieTab;
    @FXML
    private TableColumn imieCol;
    @FXML
    private TableColumn nazwiskoCol;
    @FXML
    private TableColumn loginCol;

    @FXML
    private TableColumn IDCol;

    ObservableList<UczniowieDane> uczniowie;



    @FXML
    private void zaladujUczniow(){
        try{
            String sql = "SELECT imie, nazwisko, logowanie_login, id FROM uczen";
            Connection conn = Polaczenie.getConnection();
            this.uczniowieDane = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(sql);
            while(rs.next()){
                this.uczniowieDane.add(new UczniowieDane(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
            }

        }catch(SQLException e){

        }

        this.imieCol.setCellValueFactory(new PropertyValueFactory<UczniowieDane, String>("imie"));
        this.nazwiskoCol.setCellValueFactory(new PropertyValueFactory<UczniowieDane, String>("nazwisko"));
        this.loginCol.setCellValueFactory(new PropertyValueFactory<UczniowieDane, String>("login"));
        this.IDCol.setCellValueFactory(new PropertyValueFactory<UczniowieDane, String>("id"));


        this.uczniowieTab.setItems(null);
        this.uczniowieTab.setItems(this.uczniowieDane);

        this.imieCol.setResizable(false);
        this.nazwiskoCol.setResizable(false);
        this.loginCol.setResizable(false);
        this.IDCol.setResizable(false);
    }


    @FXML
    private Button wylogujButton;

    public void wyloguj() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setHeaderText("Czy chcesz się wylogować?");

        ButtonType bt1 = new ButtonType("Tak");
        ButtonType bt2 = new ButtonType("Nie");

        alert.getButtonTypes().setAll(bt1, bt2);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == bt1) {
            try {
                Stage loginStage = (Stage) this.wylogujButton.getScene().getWindow();
                loginStage.close();
                Logowanie.window.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
        }

    }

    @FXML
    private Label ukryteHaslo;

    @FXML
    private void pokazHaslo(){
        try{
            String hasloUkryte;
            Connection conn = Polaczenie.getConnection();
            PreparedStatement result = conn.prepareStatement("SELECT haslo FROM logowanie WHERE login=?");
            result.setString(1,login_w);
            ResultSet rs = result.executeQuery();
            if(rs.next()){
                hasloUkryte = rs.getString(1);
                ukryteHaslo.setText(hasloUkryte);
                conn.close();

            }
        }catch(SQLException e ){
            e.printStackTrace();
        }
    }
    @FXML
    private void ukryjHaslo(){
        try{
            ukryteHaslo.setText("Hasło ukryte");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private PasswordField labelzmiana;
    @FXML
    private Label haslozostalo;




    @FXML
    private void zmienHaslo(){
        String sql = "UPDATE logowanie SET haslo=? WHERE login=?";
        if(this.labelzmiana.getText().isEmpty()) {
            haslozostalo.setText("Hasło nie może być puste!");
        }else{
            try {
                Connection conn = Polaczenie.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setString(1, this.labelzmiana.getText());
                stmt.setString(2, login_w);

                stmt.execute();
                stmt.close();
                conn.close();
                labelzmiana.setText("");
                haslozostalo.setText("Hasło zostało zmienione!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private Button zaladujOceny;

    @FXML
    private TableView ocenyTab;
    @FXML
    private TableColumn ocenyImieCol;
    @FXML
    private TableColumn ocenyNazwiskoCol;
    @FXML
    private TableColumn ocenyPrzedmiotCol;
    @FXML
    private TableColumn ocenyWartoscCol;
    @FXML
    private TableColumn ocenyIDCol;

    @FXML
    private void zaladujOceny(){
        String sql = "SELECT u.imie, u.nazwisko, o.przedmiot, o.wartosc,o.id FROM uczen u INNER JOIN ocena o ON u.id=o.uczen_id";
        try{
            Connection conn = new Polaczenie().getConnection();
            this.ocenyDane = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(sql);
            while(rs.next()){
                this.ocenyDane.add(new OcenyDane(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        this.ocenyImieCol.setCellValueFactory(new PropertyValueFactory<OcenyDane, String>("imie"));
        this.ocenyNazwiskoCol.setCellValueFactory(new PropertyValueFactory<OcenyDane, String>("nazwisko"));
        this.ocenyPrzedmiotCol.setCellValueFactory(new PropertyValueFactory<OcenyDane, String>("przedmiot"));
        this.ocenyWartoscCol.setCellValueFactory(new PropertyValueFactory<OcenyDane, String>("wartosc"));
        this.ocenyIDCol.setCellValueFactory(new PropertyValueFactory<OcenyDane, String>("id"));

        this.ocenyTab.setItems(null);
        this.ocenyTab.setItems(this.ocenyDane);

        this.ocenyImieCol.setResizable(false);
        this.ocenyNazwiskoCol.setResizable(false);
        this.ocenyPrzedmiotCol.setResizable(false);
        this.ocenyWartoscCol.setResizable(false);
        this.ocenyIDCol.setResizable(false);

        this.wyborWartosci.setValue(null);
        this.wyborPrzedmiot.setValue(null);

        this.ocenaDodana.setText("");
        this.zwrotOceny.setText("");
    }


    ObservableList<OcenyDane> oceny;
    @FXML
    private Label zwrotOceny;


    @FXML
    private void usunOcene(){
        String id = "";
        String sql = "DELETE FROM ocena WHERE id=?";

        oceny = ocenyTab.getSelectionModel().getSelectedItems();
        if(oceny.isEmpty()){
            this.zwrotOceny.setText("Nie wybrano oceny.");
        }else {
            id = oceny.get(0).getId();
//        System.out.println(id);


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdzenie");
            alert.setHeaderText("Czy na pewno chcesz usunąć ocenę?");

            ButtonType bt1 = new ButtonType("Tak");
            ButtonType bt2 = new ButtonType("Nie");

            alert.getButtonTypes().setAll(bt1, bt2);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == bt1) {
                try {
                    Connection conn = Polaczenie.getConnection();
                    PreparedStatement resultq = conn.prepareStatement(sql);
                    resultq.setString(1, id);

                    resultq.execute();
                    conn.close();
//                System.out.println("usunieto ocene");
                    this.zwrotOceny.setText("Usunięto ocenę");
                    zaladujOceny();

                } catch (Exception e) {

                    e.printStackTrace();
                }


            } else {
            }
        }
    }

    @FXML
    private ComboBox<String> wyborWartosci;

    ObservableList<String> wartosci = FXCollections.observableArrayList("1","2","2.5","3","3.5","4","4.5","5");


    @FXML
    private ComboBox<String> wyborPrzedmiot;
    ObservableList<String> przedmioty = FXCollections.observableArrayList("Matematyka","Fizyka","Polski","Angielski","Francuski");

    @FXML
    private Label ocenaDodana;

    @FXML
    private void dodajOcene() {
        uczniowie = uczniowieTab.getSelectionModel().getSelectedItems();


        String login;
        String sql = "INSERT INTO ocena (przedmiot, wartosc, uczen_id) VALUES(?,?,?)";
        String uczen_id;
//        String sqlu = "SELECT login FROM uczen WHERE id=?";
        if (uczniowie.isEmpty()||this.wyborWartosci.getValue()==null||this.wyborPrzedmiot.getValue()==null) {
            ocenaDodana.setText("Jedno lub więcej pól pozostało puste lub nie wybrano ucznia. Proszę poprawić.");
        } else {
            String id = uczniowie.get(0).getId();
            try {
                Connection conn = Polaczenie.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
//                PreparedStatement stmtu = conn.prepareStatement(sqlu);

//                stmtu.setString(1,  id);
//                System.out.println(this.pobierzLogin.getText());
//                ResultSet rs = stmtu.executeQuery();

                    try {
//                        System.out.println("przeszło");

                        stmt.setString(1, this.wyborPrzedmiot.getValue());
                        stmt.setString(2, this.wyborWartosci.getValue());
                        stmt.setString(3, id);

                        stmt.execute();
                        conn.close();
                        zaladujOceny();
                        ocenaDodana.setText("Ocena została dodana.");

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }



            } catch (SQLException e) {

            }
//            ocenaDodana.setText("");


        }
    }
}
