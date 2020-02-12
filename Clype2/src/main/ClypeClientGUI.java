/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import data.ClypeData;
import data.MessageClypeData;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Jason
 */
public class ClypeClientGUI extends Application {
    private ClypeClient client;
    public Label otherUser;
    public Label userList;
    public ImageView recentPicture;
    
    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();
        root.getColumnConstraints().add(new ColumnConstraints(250));
        root.getColumnConstraints().add(new ColumnConstraints(150));
        
        for(int i = 0; i < 5; i++){
            RowConstraints con = new RowConstraints();
            con.setPrefHeight(60);
            root.getRowConstraints().add(con);
        }
        
        //VBoxes
        VBox leftUpperBox = new VBox(20);
        VBox rightLowerBox = new VBox(20);
        VBox leftLowerBox = new VBox();
        VBox rightUpperBox = new VBox();
        HBox bottomBox = new HBox(20);
        bottomBox.setAlignment(Pos.CENTER);
        leftLowerBox.setAlignment(Pos.CENTER);
        rightLowerBox.setAlignment(Pos.CENTER);
        leftUpperBox.setAlignment(Pos.CENTER);
        rightUpperBox.setAlignment(Pos.CENTER);
        
        //Labels
        otherUser = new Label();
        userList = new Label();
        Label currentUser = new Label();
        
        //Textfield
        TextField sendBox = new TextField();
        
        
        
        //Buttons
        Button sendButton = new Button("Send");
        sendButton.setOnAction(evt -> {
            currentUser.setText("You: " + sendBox.getText());
            client.setDataToSendToServer(new MessageClypeData(client.getUserName(), sendBox.getText(), 3));
            sendBox.clear();
            client.sendData();
        });
        Button mediaButton = new Button("Picture");
        Button showUsers = new Button("Users");
        showUsers.setOnAction(evt -> {
            client.setDataToSendToServer(new MessageClypeData(client.getUserName(), "LISTUSERS", 1));
            client.sendData();
        });
        
        //Picture Window
        Image picture = new Image(ClypeClientGUI.class.getResourceAsStream("download.jpg"));
        recentPicture = new ImageView(picture);
        recentPicture.setFitHeight(120);
        recentPicture.setFitWidth(150);
        
        GridPane.setConstraints(rightUpperBox, 1, 0);
        GridPane.setConstraints(bottomBox, 0, 4);
        GridPane.setColumnSpan(bottomBox, 2);
        GridPane.setConstraints(leftUpperBox, 0, 0);
        GridPane.setConstraints(rightLowerBox, 1, 3);
        GridPane.setConstraints(leftLowerBox, 0, 3);
        
        bottomBox.getChildren().addAll(userList, showUsers);
        leftUpperBox.getChildren().addAll(otherUser, currentUser);
        leftLowerBox.getChildren().addAll(sendBox);
        rightLowerBox.getChildren().addAll(sendButton);
        rightUpperBox.getChildren().addAll(recentPicture);
        root.getChildren().addAll(leftUpperBox, rightLowerBox, leftLowerBox, bottomBox, rightUpperBox);
        //root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Clype2.0");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(evt -> {
            client.setDataToSendToServer(new MessageClypeData(client.getUserName(), "DONE", 0));
            client.sendData();
            client.setConnection(true);
        });
        
        String input = JOptionPane.showInputDialog("Enter your information.");
        Scanner kbd = new Scanner(input);
        kbd.useDelimiter("[@:]");
        client = new ClypeClient(kbd.next(), kbd.next(), kbd.nextInt(), this);
        ClypeData joining = new MessageClypeData(client.getUserName(),"I have joined the room", 3);
        client.setDataToSendToServer(joining);
        Thread clientThread = new Thread(client);
        clientThread.start();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
