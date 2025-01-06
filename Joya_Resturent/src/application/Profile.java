package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Profile {
    private Employee emp;
    public Profile(Employee emp) {
        this.emp = emp;     
    }
 

    public VBox getProfileView(Stage x1,Stage x2) {
    	String s= emp.getName();
    	String[] words = s.split(" ");
        Label name = new Label("Employee Name : " + words[0]);
        Label id = new Label("Employee ID : " + emp.getId());
        
        name.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        id.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
       Image prof = new Image("file:///C:/Users/abdal/OneDrive/Desktop/تصميم بدون عنوان (1).jpg");
        ImageView profView = new ImageView(prof);
        profView.setFitWidth(80); 
        profView.setFitHeight(80);   
        
        
        Image image = new Image("file:///C:/Users/abdal/OneDrive/Desktop/j.jpg");  // Replace with your image path

        // Create an ImageView to display the image
        ImageView imageView = new ImageView(image);

        // Optionally, resize the image to fit the button
        imageView.setFitWidth(30);  // Set width of the image
        imageView.setFitHeight(30); // Set height of the image

        // Create a button and set the image as the graphic
        Button logout = new Button("Log out");
        logout.setGraphic(imageView);
        logout.setOnAction(e->{
        	x1.close();
        	x2.show();
        });

        
        VBox profileBox = new VBox(10); 
        profileBox.getChildren().addAll(profView,id,name,logout );
        profileBox.setAlignment(Pos.CENTER);
        profileBox.setStyle("-fx-background-color: lightblue;");
        profileBox.setAlignment(Pos.CENTER);
        profileBox.setPrefHeight(200);
		profileBox.setPrefWidth(200);
        return profileBox;
    }
    
    public VBox getProfileView2() {
        Label name = new Label("Employee Name : " + emp.getName());
        Label id = new Label("Employee ID : " + emp.getId());
        Label Salary= new Label("Employee Salary: " + emp.getSalary());
        Label con = new Label("Employee Contact: " + emp.getContactInfo());
        Label Address = new Label("Employee Address: " + emp.getAddress());
        Label Postion = new Label("Employee Postion: " + emp.getPostion());
        Label Password = new Label("Employee Password: " + emp.getPassword());
        
        name.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        id.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        Salary.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        con.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        Address.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        Postion.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        Password.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
       
        
       Image prof = new Image("file:///C:/Users/abdal/OneDrive/Desktop/تصميم بدون عنوان (1).jpg");
        ImageView profView = new ImageView(prof);
        profView.setFitWidth(115); 
        profView.setFitHeight(115);             
        
        VBox profileBox = new VBox(20); 
        profileBox.getChildren().addAll(profView,id,name,con ,Postion,Address,Salary,Password);
        profileBox.setAlignment(Pos.CENTER);
        profileBox.setStyle("-fx-background-color: lightblue;");
        return profileBox;
    }
}