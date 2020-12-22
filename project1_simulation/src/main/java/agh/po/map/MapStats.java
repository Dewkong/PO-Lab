package agh.po.map;

import agh.po.element.Animal;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MapStats {
    private final WorldMap map;
    private GridPane grid;
    private int animalCounter = 0;
    private int plantCounter = 0;
    private int energySum;
    private int averageEnergy = 0;
    private int deadCounter = 0;
    private int daysCounter = 0;
    private int averageLife = 0;
    private int childCounter = 0;
    private int averageChild = 0;
    private int[] dominatingGenotype = {0, 0, 0, 0, 0, 0, 0};

    private Text title = new Text("STATISTICS");
    private Label animalCounterLabel = new Label("Number of animals: ");
    private Label plantCounterLabel = new Label("Number of plants: ");
    private Label dominatingGenotypeLabel = new Label("Dominating genotype: ");
    private Label averageEnergyLabel = new Label("Average energy: ");
    private Label averageLifeLabel = new Label("Average length of life: ");
    private Label averageChildLabel = new Label("Average number of children: ");

    public MapStats(WorldMap map){
        this.map = map;
        this.energySum = map.startEnergy * map.startAnimals;
    }

    public void countAnimal(int i){
        animalCounter += i;
    }

    public void countPlants(int i){
        plantCounter += i;
    }

    public void countDead(int i){
        deadCounter += i;
    }

    public void countDays(int i){
        daysCounter += i;
    }


    public void setDominatingGenotype(){
        int[] geneCounter = {0, 0, 0, 0, 0, 0, 0, 0};
        ArrayList<ArrayList<Animal>> animalArrayLists = new ArrayList<>(map.animalsOnMap.values());
        for (ArrayList<Animal> animals : animalArrayLists) {
            for (Animal animal : animals) {
                ArrayList<Integer> genes = animal.getGenes();
                for (Integer gene : genes){
                    geneCounter[gene] += 1;
                }
            }
        }
        int[] dominatingGenotype = {0, 0, 0, 0, 0, 0, 0, 0};
        if (animalCounter >= 1){
            for (int i = 0; i < 8; i++){
                dominatingGenotype[i] = (int) Math.floor(geneCounter[i] / animalCounter);
            }
            if (Arrays.stream(dominatingGenotype).sum() < 32){
                Random rand = new Random();
                dominatingGenotype[rand.nextInt(8)] += 32 - Arrays.stream(dominatingGenotype).sum();
            }
        }
        this.dominatingGenotype = dominatingGenotype;
    }


    public void setGrid(GridPane grid){
        title.setFill(new Color(0,0.6,0.4, 1.0));
        title.setFont(Font.font(title.getFont().toString(), FontWeight.BOLD, 25));
        grid.add(title, 0, 3);

        animalCounterLabel.setFont(new Font("Arial", 15));
        grid.add(animalCounterLabel, 0, 4);

        plantCounterLabel.setFont(new Font("Arial", 15));
        grid.add(plantCounterLabel, 0, 5);

        dominatingGenotypeLabel.setFont(new Font("Arial", 15));
        grid.add(dominatingGenotypeLabel, 0, 6);

        averageEnergyLabel.setFont(new Font("Arial", 15));
        grid.add(averageEnergyLabel, 0, 7);

        averageLifeLabel.setFont(new Font("Arial", 15));
        grid.add(averageLifeLabel, 0, 8);

        averageChildLabel.setFont(new Font("Arial", 15));
        grid.add(averageChildLabel, 0, 9);

        this.grid = grid;
    }

    public void countEnergy(int i){
        energySum += i;
    }

    public void setAverageEnergy(){
        if (animalCounter > 0){
            averageEnergy = energySum / animalCounter;
        }
        else {
            averageEnergy = 0;
        }
    }

    public void setAverageLife(){
        if (deadCounter > 0){
            averageLife = daysCounter / deadCounter;
        }
        else{
            averageLife = 0;
        }
    }

    public void countChild(int i){
        childCounter += i;
    }

    public void setAverageChild(){
        if (animalCounter > 0){
            averageChild = childCounter / animalCounter;
        }
        else {
            averageChild = 0;
        }
    }

    public void update() {
        animalCounterLabel.setText("Number of animals: " + animalCounter);
        plantCounterLabel.setText("Number of plants: " + plantCounter);
        setDominatingGenotype();
        dominatingGenotypeLabel.setText("Dominating genotype: " + getDominatingGenotype());
        setAverageEnergy();
        averageEnergyLabel.setText("Average energy: " + averageEnergy);
        setAverageLife();
        averageLifeLabel.setText("Average length of life: " + averageLife);
        setAverageChild();
        averageChildLabel.setText("Average number of children: " + averageChild);
    }

    public String getDominatingGenotype(){
        String result = "";
        int i = 0;
        for (Integer gene : dominatingGenotype){
            result += "'" + i + "':" + gene + " ";
            i++;
        }
        return result;
    }

    public void export(int counter) {
        try {
            FileWriter myWriter = new FileWriter("statistics" + counter + ".txt");
            myWriter.write("Number of animals: " + animalCounter + "\r\n"
                            + "Number of plants: " + plantCounter + "\r\n"
                            + "Dominating genotype: " + getDominatingGenotype() + "\r\n"
                            + "Average energy: " + averageEnergy + "\r\n"
                            + "Average length of life: " + averageLife + "\r\n"
                            + "Average number of children: " + averageChild);
            myWriter.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("File");
            alert.setHeaderText("File saved");
            alert.setContentText("Check project's general directory");

            alert.showAndWait();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
