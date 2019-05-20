package logowanie;

import admin.AdminController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import nauczyciel.NauczycielController;
import uczen.UczenController;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LogowanieController implements Initializable {

    ModelLogowania modelLogowania = new ModelLogowania();

    @FXML
    private Label status;
    @FXML
    private TextField login;
    @FXML
    private PasswordField haslo;
    @FXML
    private ComboBox<opcje> wybor;
    @FXML
    private Button zaloguj;
    @FXML
    private Label logowanieStatus;

    public String login_wpisany;




    public void initialize(URL url, ResourceBundle rb){

        if(this.modelLogowania.czyBazaPolaczona()){
            this.status.setText("Połączono");
        }
        else{
            this.status.setText("Brak połączenia");
        }
//        login_wpisany=this.login.getText();
        this.wybor.setItems(FXCollections.observableArrayList(opcje.values()));
    }

//    public void pobierzLogin(){
//        login_wpisany=this.login.getText();
//    }

//    public LogowanieController(){
//        this.login_wpisany = this.login.getText();
//    }
    @FXML
    public void Zaloguj(ActionEvent event){
        try{
            UczenController.login_w=this.login.getText();
            NauczycielController.login_w=this.login.getText();
            if(this.modelLogowania.poprawnoscLogowania(this.login.getText(),this.haslo.getText(),((opcje)this.wybor.getValue()).toString())){
                Stage stage = (Stage)this.zaloguj.getScene().getWindow();
                stage.close();
                switch(((opcje)this.wybor.getValue()).toString()){
                    case "Admin":
                        adminLogin();
                        break;
                    case "Uczen":
                        uczenLogin();
                        break;
                    case "Nauczyciel":
                        nauczycielLogin();
                        break;
                }
            }
            else{
                this.logowanieStatus.setText("BŁĄD! Niepoprawne dane!");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    Stage uczenClose = new Stage();

    public void uczenLogin(){
        try{
            Stage uczenStage = new Stage();
            uczenStage = uczenClose;
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("/uczen/Uczen.fxml").openStream());

            uczenStage.setOnCloseRequest(e ->{e.consume();
                closeUczen();
            });


            UczenController uczenController = (UczenController)loader.getController();

            Scene scene = new Scene(root);
            uczenStage.setScene(scene);
            uczenStage.setTitle("Panel Ucznia");
            uczenStage.setResizable(false);
            uczenStage.show();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    Stage adminClose = new Stage();

    public void adminLogin(){
        try{

            Stage adminStage = new Stage();
            adminStage = adminClose;
            FXMLLoader adminloader = new FXMLLoader();
            Pane adminroot = (Pane)adminloader.load(getClass().getResource("/admin/Admin.fxml").openStream());

            adminStage.setOnCloseRequest(e ->{e.consume();
                closeAdmin();
            });

            AdminController adminController = (AdminController)adminloader.getController();

            Scene scene = new Scene(adminroot);
            adminStage.setScene(scene);
            adminStage.setTitle("Panel Administratora");
            adminStage.setResizable(false);
            adminStage.show();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    Stage nauczycielClose = new Stage();

    public void nauczycielLogin(){
        try{
            Stage nauczycielStage = new Stage();
            nauczycielStage = nauczycielClose;
            FXMLLoader nauczycielloader = new FXMLLoader();
            Pane nauczycielroot = (Pane)nauczycielloader.load(getClass().getResource("/nauczyciel/Nauczyciel.fxml").openStream());

            nauczycielStage.setOnCloseRequest(e ->{e.consume();
                closeNauczyciel();
            });


            NauczycielController nauczycielController = (NauczycielController)nauczycielloader.getController();

            Scene scene = new Scene(nauczycielroot);
            nauczycielStage.setScene(scene);
            nauczycielStage.setTitle("Panel Nauczyciela");
            nauczycielStage.setResizable(false);
            nauczycielStage.show();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void closeAdmin() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setHeaderText("Czy chcesz zamknąć program?");

        ButtonType bt1 = new ButtonType("Tak");
        ButtonType bt2 = new ButtonType("Nie");

        alert.getButtonTypes().setAll(bt1, bt2);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == bt1) {
            adminClose.close();
        } else {
        }
    }
        private void closeUczen(){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdzenie");
            alert.setHeaderText("Czy chcesz zamknąć program?");

            ButtonType bt1 = new ButtonType("Tak");
            ButtonType bt2 = new ButtonType("Nie");

            alert.getButtonTypes().setAll(bt1,bt2);

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()==bt1){
                uczenClose.close();
            }else{
            }

    }

    private void closeNauczyciel() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setHeaderText("Czy chcesz zamknąć program?");

        ButtonType bt1 = new ButtonType("Tak");
        ButtonType bt2 = new ButtonType("Nie");

        alert.getButtonTypes().setAll(bt1, bt2);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == bt1) {
            nauczycielClose.close();
        } else {
        }
    }
}
