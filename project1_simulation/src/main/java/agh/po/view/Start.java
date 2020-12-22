package agh.po.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class Start extends Application{
    @Override
    public void start(Stage stage) {
        final int[] simulationCounter = {0};

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(15);
        grid.setPadding(new Insets(5));
        grid.setAlignment(Pos.CENTER);

        Text title = new Text("SET SIMULATION PROPERTIES");
        title.setFill(new Color(0,0,0.7, 1.0));
        title.setFont(Font.font(title.getFont().toString(), FontWeight.BOLD, 25));
        grid.add(title, 0, 0);

        Label map = new Label("Map:");
        map.setFont(new Font("Arial", 20));
        map.setTextFill(new Color(0.45, 0.45, 0, 1.0));
        grid.add(map, 0, 1);

        Label widthLabel = new Label("Width:");
        widthLabel.setFont(new Font("Arial", 15));
        TextField widthField = new TextField("20");
        HBox widthBox = new HBox();
        widthBox.getChildren().addAll(widthLabel, widthField);
        widthBox.setSpacing(10);
        grid.add(widthBox, 0, 2);

        Label heightLabel = new Label("Height:");
        heightLabel.setFont(new Font("Arial", 15));
        TextField heightField = new TextField("20");
        HBox heightBox = new HBox();
        heightBox.getChildren().addAll(heightLabel, heightField);
        heightBox.setSpacing(10);
        grid.add(heightBox, 0, 3);

        Label jungleRatioLabel = new Label("Jungle ratio (as a decimal fraction):");
        jungleRatioLabel.setFont(new Font("Arial", 15));
        TextField jungleRatioField = new TextField("0.5");
        HBox jungleRatioBox = new HBox();
        jungleRatioBox.getChildren().addAll(jungleRatioLabel, jungleRatioField);
        jungleRatioBox.setSpacing(10);
        grid.add(jungleRatioBox, 0, 4);

        Label elements = new Label("Elements:");
        elements.setFont(new Font("Arial", 20));
        elements.setTextFill(new Color(0.45, 0.45, 0, 1.0));
        grid.add(elements, 0, 5);

        Label startAnimalsLabel = new Label("Starting amount of animals:");
        startAnimalsLabel.setFont(new Font("Arial", 15));
        TextField startAnimalsField = new TextField("2");
        HBox startAnimalsBox = new HBox();
        startAnimalsBox.getChildren().addAll(startAnimalsLabel, startAnimalsField);
        startAnimalsBox.setSpacing(10);
        grid.add(startAnimalsBox, 0, 6);

        Label startPlantsLabel = new Label("Starting amount of plants:");
        startPlantsLabel.setFont(new Font("Arial", 15));
        TextField startPlantsField = new TextField("50");
        HBox startPlantsBox = new HBox();
        startPlantsBox.getChildren().addAll(startPlantsLabel, startPlantsField);
        startPlantsBox.setSpacing(10);
        grid.add(startPlantsBox, 0, 7);

        Label energy = new Label("Energy:");
        energy.setFont(new Font("Arial", 20));
        energy.setTextFill(new Color(0.85, 0.85, 0, 1.0));
        grid.add(energy, 0, 8);

        Label startEnergyLabel = new Label("Starting energy:");
        startEnergyLabel.setFont(new Font("Arial", 15));
        TextField startEnergyField = new TextField("10");
        HBox startEnergyBox = new HBox();
        startEnergyBox.getChildren().addAll(startEnergyLabel, startEnergyField);
        startEnergyBox.setSpacing(10);
        grid.add(startEnergyBox, 0, 9);

        Label moveEnergyLabel = new Label("Energy loss on move:");
        moveEnergyLabel.setFont(new Font("Arial", 15));
        TextField moveEnergyField = new TextField("2");
        HBox moveEnergyBox = new HBox();
        moveEnergyBox.getChildren().addAll(moveEnergyLabel, moveEnergyField);
        moveEnergyBox.setSpacing(10);
        grid.add(moveEnergyBox, 0, 10);

        Label plantEnergyLabel = new Label("Energy gain from plant:");
        plantEnergyLabel.setFont(new Font("Arial", 15));
        TextField plantEnergyField = new TextField("5");
        HBox plantEnergyBox = new HBox();
        plantEnergyBox.getChildren().addAll(plantEnergyLabel, plantEnergyField);
        plantEnergyBox.setSpacing(10);
        grid.add(plantEnergyBox, 0, 11);


        Label result = new Label();
        grid.add(result, 0, 13);

        Button apply = new Button("Apply");
        grid.add(apply, 0, 12);
        apply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (checkData(result, widthField.getText(), heightField.getText(), jungleRatioField.getText(), startAnimalsField.getText(), startPlantsField.getText(), startEnergyField.getText(), moveEnergyField.getText(), plantEnergyField.getText())){
                    int width = parseInt(widthField.getText());
                    int height = parseInt(heightField.getText());
                    double jungleRatio = parseDouble(jungleRatioField.getText());
                    int startAnimals = parseInt(startAnimalsField.getText());
                    int startPlants = parseInt(startPlantsField.getText());
                    int startEnergy = parseInt(startEnergyField.getText());
                    int moveEnergy = parseInt(moveEnergyField.getText());
                    int plantEnergy = parseInt(plantEnergyField.getText());

                    simulationCounter[0] += 1;
                    result.setText("Simulation " + simulationCounter[0] + " has started");

                    Simulation simulation = new Simulation();
                    simulation.start(width, height, jungleRatio, startAnimals, startPlants, startEnergy, moveEnergy, plantEnergy, simulationCounter[0]);
                }
            }
        });


        Scene scene = new Scene(grid, 575, 575);
        stage.setScene(scene);
        stage.setTitle("Properties");
        stage.show();
    }

    private boolean checkData(Label result, String width, String height, String jungleRatio, String startAnimals, String startPlants, String startEnergy, String moveEnergy, String plantEnergy) {
        String intPattern = "[0-9]+";
        String dblPattern = "0.[0-9]+";
        if (!width.matches(intPattern) || parseInt(width) <= 0){
            result.setText("Incorrect width value");
            return false;
        }
        if (!height.matches(intPattern) || parseInt(height) <= 0){
            result.setText("Incorrect height value");
            return false;
        }
        if (!startAnimals.matches(intPattern) || parseInt(startAnimals) < 2){
            result.setText("Incorrect starting amount of animals value");
            return false;
        }
        if (!startPlants.matches(intPattern) || parseInt(startPlants) <= 0){
            result.setText("Incorrect starting amount of plants value");
            return false;
        }
        if (!startEnergy.matches(intPattern) || parseInt(startEnergy) <= 0){
            result.setText("Incorrect starting energy value");
            return false;
        }
        if (!moveEnergy.matches(intPattern) || parseInt(moveEnergy) < 0){
            result.setText("Incorrect move energy loss value");
            return false;
        }
        if (!plantEnergy.matches(intPattern) || parseInt(plantEnergy) < 0){
            result.setText("Incorrect plant energy gain value");
            return false;
        }
        if (!jungleRatio.matches(dblPattern) || parseDouble(jungleRatio) <= 0 || parseDouble(jungleRatio) > 1){
            result.setText("Incorrect jungle ratio value");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        launch();
    }
}
