package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import services.IService;

import java.io.IOException;

public class LoginController {
    private IService server;
    //private TeacherController teacherController;
    //private Parent parent;


    public LoginController() {
    }

    @FXML
    TextField textboxUsername;

    @FXML
    TextField textboxPassword;

    @FXML
    Button buttonLogin;

//    public void setParent(Parent parent){
//        this.parent=parent;
//    }
//
//    public  void setController(TeacherController controller){
//        this.teacherController=controller;
//    }
    public void setService(IService service){
        this.server=service;

        init();
    }

    private void init(){

    }

    @FXML
    public void login() throws IOException{
        String username=textboxUsername.getText();
        String password=textboxPassword.getText();
        loginTeacher(username, password);

        //User user=server.login(this.teacherController,username, password);
    }

    public void loginTeacher(String username, String password) throws IOException {
        Stage primaryStage=new Stage();

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/teacherView.fxml"));
        Parent root=loader.load();

        TeacherController teacherController=loader.getController();
        User user= server.login(teacherController,username,password);
        if (user == null){
            showErrorMessage("Datele introduse nu sunt corecte!");
        }
        else {
            teacherController.setTeacher(username);
            teacherController.setService(server);

            primaryStage.setScene(new Scene(root, 700, 500));
            primaryStage.setTitle("TEACHER " + username);
            primaryStage.show();
        }
    }

    private static void showErrorMessage(String err){
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Error message!");
        message.setContentText(err);
        message.showAndWait();
    }
}
