package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Paper;
import services.IObserver;
import services.IService;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class TeacherController extends UnicastRemoteObject implements IObserver, Serializable {
    private IService service;
    private String teacher;
    ObservableList<Paper> papers= FXCollections.observableArrayList();

    @FXML
    TableView<Paper> tablePapers;

    @FXML
    TableColumn<Paper, Integer> columnId;

    @FXML
    TableColumn<Paper,String> columnName;

    @FXML
    TableColumn<Paper,String> columnTeacher1;

    @FXML
    TableColumn<Paper,String> columnTeacher2;

    @FXML
    TableColumn<Paper, Double> columnGrade1;

    @FXML
    TableColumn<Paper, Double> columnGrade2;

    @FXML
    TableColumn<Paper, Double> columnFinalGrade;

    @FXML
    TableColumn<Paper,String> columnRecheck;

    @FXML
    Button buttonGrade;

    @FXML
    Button buttonLogout;

    @FXML
    TextField textboxGrade;

    public TeacherController() throws RemoteException {
    }

    public void setService(IService service) {
        this.service = service;

        setPapers();

        init();

    }

    public void setPapers(){
        List<Paper> list=new ArrayList<>();
        for(Paper paper:service.getPaperByTeacher(this.teacher)){
            list.add(paper);
        }

        papers.setAll(list);
    }

    public void init(){
        columnId.setCellValueFactory((new PropertyValueFactory<Paper, Integer>("id")));
        columnName.setCellValueFactory((new PropertyValueFactory<Paper, String>("name")));
        columnTeacher1.setCellValueFactory(new PropertyValueFactory<Paper,String>("teacher1"));
        columnTeacher2.setCellValueFactory(new PropertyValueFactory<Paper, String>("teacher2"));
        columnGrade1.setCellValueFactory(new PropertyValueFactory<Paper,Double>("grade1"));
        columnGrade2.setCellValueFactory(new PropertyValueFactory<Paper, Double>("grade2"));
        columnFinalGrade.setCellValueFactory(new PropertyValueFactory<Paper,Double>("final_grade"));
        columnRecheck.setCellValueFactory(new PropertyValueFactory<Paper, String>("recheck"));

        tablePapers.setItems(papers);
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @FXML
    public void logout(){
        this.service.logout(this, this.teacher);
        Platform.exit();
    }

    @FXML
    public void grade(){
        Integer id=tablePapers.getSelectionModel().getSelectedItem().getId();
        String name=tablePapers.getSelectionModel().getSelectedItem().getName();
        String teacher1=tablePapers.getSelectionModel().getSelectedItem().getTeacher1();
        String teacher2=tablePapers.getSelectionModel().getSelectedItem().getTeacher2();
        Double grade1=tablePapers.getSelectionModel().getSelectedItem().getGrade1();
        Double grade2=tablePapers.getSelectionModel().getSelectedItem().getGrade2();

        Paper paper=new Paper(id, name, teacher1, teacher2);
        Double grade;
        String otherTeacher="";
        try {
            grade = Double.parseDouble(textboxGrade.getText());

            if(grade<0)
                throw new NumberFormatException("nu ie bn");
            if(this.teacher.equals(teacher1)){
                paper.setGrade1(grade);
                paper.setGrade2(grade2);
                otherTeacher=teacher2;
            }
            else
            if(this.teacher.equals(teacher2)){
                paper.setGrade2(grade);
                paper.setGrade1(grade1);
                otherTeacher=teacher1;
            }
        }catch(NumberFormatException ex){
            showErrorMessage("Trebuie introdus un numar real ca si nota!");
        }


        Double medie=(paper.getGrade1()+paper.getGrade2())/2;
        paper.setFinal_grade(medie);

        if(abs(paper.getGrade2()-paper.getGrade1())>=1){
            paper.setRecheck("RECORECTARE");
        }
        else{
            paper.setRecheck("OK");
        }

        service.updatePaper(paper, otherTeacher);
        setPapers();
    }

    private static void showErrorMessage(String err){
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Error message!");
        message.setContentText(err);
        message.showAndWait();
    }

    @Override
    public void paperGraded(List<Paper> papersList) throws RemoteException {
        this.papers.setAll(papersList);
    }
}
