package agh.po.view;

import agh.po.element.Animal;
import agh.po.map.WorldMap;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Simulation {
    public void start(int width, int height, double jungleRatio, int startAnimals, int startPlants, int startEnergy, int moveEnergy, int plantEnergy, int counter) {
        final boolean[] paused = {true};

        WorldMap map = new WorldMap(width, height, jungleRatio, moveEnergy, plantEnergy, startEnergy, startAnimals);
        for (int i = 0; i < startAnimals; i++){
            new Animal(map, startEnergy);
        }
        for (int i = 0; i < startPlants; i++){
            map.growPlants();
        }


        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(5));
        grid.setAlignment(Pos.CENTER);

        GridPane leftPanel = new GridPane();
        leftPanel.setHgap(5);
        leftPanel.setVgap(5);
        leftPanel.setPadding(new Insets(5));
        leftPanel.setAlignment(Pos.TOP_CENTER);

        Button run = new Button("Run");
        leftPanel.add(run, 0, 0);
        run.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                paused[0] = false;
            }
        });

        Button pause = new Button("Pause");
        leftPanel.add(pause, 0, 1);
        pause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                paused[0] = true;
            }
        });
        grid.add(leftPanel, 0 ,0);

        map.mapStats.setGrid(leftPanel);

        Button export = new Button("Export statistics");
        leftPanel.add(export, 0, 10);
        export.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (paused[0]) {
                    map.mapStats.export(counter);
                }
            }
        });

        GridPane mapGrid = new GridPane();
        mapGrid.setHgap(-1);
        mapGrid.setVgap(-1);
        mapGrid.setPadding(new Insets(5));
        mapGrid.setAlignment(Pos.CENTER);

        map.mapView.buildMapStack(650, 650);
        map.mapView.addStackToGrid(mapGrid);

        grid.add(mapGrid, 1, 0, 1, 1);



        Timeline simulation = new Timeline(
                new KeyFrame(Duration.seconds(0.3),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if (!paused[0]){
                                    map.growPlants();
                                    map.removeDeadAnimals();
                                    map.run();
                                    map.eatPlants();
                                    map.breedAnimals();
                                    map.mapStats.update();
                                }
                            }
                        }));
        simulation.setCycleCount(Timeline.INDEFINITE);
        simulation.play();


        Scene scene = new Scene(grid, 700, 700);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Simulation " + counter);
        stage.show();
    }
}
