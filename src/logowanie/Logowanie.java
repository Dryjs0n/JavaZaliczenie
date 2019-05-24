package logowanie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.util.Optional;


public class Logowanie extends Application {


    public static Stage window = new Stage();

    @Override
    public void start(Stage stage)throws Exception{
        window=stage;
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("logowanie.fxml"));

        stage.setOnCloseRequest(e ->{e.consume();
            closeProgram();
        });

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Panel Logowania");
        stage.setResizable(false);
        stage.show();


    }




    private void closeProgram(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setHeaderText("Czy chcesz zamknąć program?");

        ButtonType bt1 = new ButtonType("Tak");
        ButtonType bt2 = new ButtonType("Nie");

        alert.getButtonTypes().setAll(bt1,bt2);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()==bt1){
            window.close();
        }else{
        }

    }

    public static void main(String[]args){
        launch(args);
    }



}
