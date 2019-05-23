package uczen;

import admin.UczenDane;
import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logowanie.LogowanieApp;
import logowanie.LogowanieController;


import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class UczenController implements Initializable {
    LogowanieController lc = new LogowanieController();

    @FXML
    private Label witaj;

    private dbConnection dc;

    public static String login_w;
    private String imie_w;

    @FXML
    private Label witaj1;

    public void initialize(URL url, ResourceBundle rb){
        this.dc = new dbConnection();
        przypiszImie();
        this.witaj.setText(imie_w);
        this.witaj1.setText(imie_w);
        try{
            zaladujOceny();
        }catch (Exception e){

        }

    }

    public void przypiszImie(){
       try {
           Connection conn = dbConnection.getConnection();
           PreparedStatement result = conn.prepareStatement("SELECT imie FROM uczen WHERE logowanie_login=?");
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

    @FXML
    private Label labelhaslo;

    @FXML
    private void pokazHaslo(){
        try{
            String hasloUkryte;
            Connection conn = dbConnection.getConnection();
            PreparedStatement result = conn.prepareStatement("SELECT haslo FROM logowanie WHERE login=?");
            result.setString(1,login_w);
            ResultSet rs = result.executeQuery();
            if(rs.next()){
                hasloUkryte = rs.getString(1);
                labelhaslo.setText(hasloUkryte);
                conn.close();

            }
        }catch(SQLException e ){
            e.printStackTrace();
        }
    }

    @FXML
    private void ukryjHaslo(){
        try{
            labelhaslo.setText("Hasło ukryte");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private Button wyloguj;

    public void wyloguj(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setHeaderText("Czy chcesz się wylogować?");

        ButtonType bt1 = new ButtonType("Tak");
        ButtonType bt2 = new ButtonType("Nie");

        alert.getButtonTypes().setAll(bt1, bt2);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == bt1) {
            try {
                Stage loginStage = (Stage) this.wyloguj.getScene().getWindow();
                loginStage.close();
                LogowanieApp.window.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
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
                Connection conn = dbConnection.getConnection();
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
    private void wyczyscZmien(){
        haslozostalo.setText("");
    }


    private ObservableList<UczenOceny> uczenoceny;

    @FXML
    private TableView ocenyTab;

    @FXML
    private TableColumn przedmiotCol;
    @FXML
    private TableColumn wartoscCol;
    @FXML
    private Button zaladujOcenyButton;


    @FXML
    private void zaladujOceny() throws SQLException{
        int u_id;
        String uczen_id;
        String sqlq = "SELECT id  FROM uczen WHERE logowanie_login=?";
        try {
            String sql = "SELECT * FROM ocena WHERE uczen_id=?";
            Connection conn = dbConnection.getConnection();

            PreparedStatement result = conn.prepareStatement(sqlq);
            result.setString(1, login_w);
            ResultSet rsq = result.executeQuery();
            if (rsq.next()) {
                u_id=rsq.getInt(1);
                uczen_id = Integer.toString(u_id);

                this.uczenoceny = FXCollections.observableArrayList();

                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, uczen_id);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    this.uczenoceny.add(new UczenOceny(rs.getString(2), rs.getString(3)));
                }
                conn.close();
            }
                zaladujOcenyButton.setText("Odśwież");
            }catch(SQLException e){
                e.printStackTrace();
        }


        this.przedmiotCol.setCellValueFactory(new PropertyValueFactory<UczenDane, String>("przedmiot"));
        this.wartoscCol.setCellValueFactory(new PropertyValueFactory<UczenDane, String>("wartosc"));

        this.ocenyTab.setItems(null);
        this.ocenyTab.setItems(this.uczenoceny);
    }




}


