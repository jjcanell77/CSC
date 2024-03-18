import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.io.*;
import java.time.LocalDateTime;
import java.util.Random;

public class UserInterfaceMenu extends Application  {
     private TextArea textArea;
     private Circle circle;

     @Override
     public void start(Stage primaryStage) {
         MenuButton  menu = new MenuButton ("Options");
         MenuItem menuItem1 = new MenuItem("Show Date/Time");
         MenuItem menuItem2 = new MenuItem("Save Log");
         MenuItem menuItem3 = new MenuItem("Change Circle Color");
         MenuItem menuItem4 = new MenuItem("Exit");
 
         menu.getItems().addAll(menuItem1, menuItem2, menuItem3, menuItem4);
 
         textArea = new TextArea();
         textArea.setPrefHeight(100);
 
         circle = new Circle(200, 220, 75);
         circle.setFill(Color.rgb(255, 26, 26)); 
 
        
         VBox vBox = new VBox(menu, textArea);
         Group root = new Group(circle, vBox);
 
         Scene scene = new Scene(root, 400, 400, Color.WHITE);
 
         menuItem1.setOnAction(e -> textArea.setText(LocalDateTime.now().toString()));
         menuItem2.setOnAction(e -> saveLog());
         menuItem3.setOnAction(e -> randomColorChange());
         menuItem4.setOnAction(e -> Platform.exit());
 
         primaryStage.setTitle("User Interface with a menu");
         primaryStage.setScene(scene);
         primaryStage.show();
     }
 
     private void saveLog() {
          try (PrintWriter writer = new PrintWriter("log.txt")) {
              writer.write(textArea.getText());
          } catch (IOException e) {
              e.printStackTrace();
          }
     }
  
     private void randomColorChange() {
         Random randomNum = new Random();
         int num1 = (int)randomNum.nextInt(266);
         Color randomGreenHue = Color.rgb(0, num1, 0);
         circle.setFill(randomGreenHue);
     }

     public static void main(String[] args) {
         launch(args);
     }
 }