/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rostan.view;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.fauberteau.rbtree.BSTGeneration;
import org.fauberteau.rbtree.BSTInfo;

/**
 *
 * @author julien
 */
public class MainGUI extends Application {

    public String thread;
    public String word;
    public ArrayList<BSTInfo> data;
    public BSTGeneration bst;
    public javafx.scene.chart.LineChart<Number, Number> chart;
    public Stage stage;
    
    @Override
    public void start(Stage primaryStage) {
        this.thread="1";
        this.word="1000";
        this.data=new ArrayList<BSTInfo>();
        this.stage=primaryStage;
        
        Scene scene = buildScene(0);
        
        primaryStage.setTitle("Multi-Threaded BST Benchmark");

        primaryStage.setScene(scene);

        primaryStage.show();

    }
    public void changeData (String t, String w){
        this.word = w;
        this.thread = t;
    }
    public int getWord (){
        return Integer.parseInt(this.word);
    }
    public int getThread (){
        return Integer.parseInt(this.thread);
    }
    public void displayArray (){
        System.out.println("\nList of elements to display in the line chart : ");
        for (int i=0; i<this.data.size(); i++){
            System.out.println("Thread "+this.data.get(i).thread+" - Words "+this.data.get(i).words+" - Time : "+this.data.get(i).time+" ns");
        }
        System.out.println("\n");
    }
    public void generateBST(){
        bst = new BSTGeneration(getThread(),getWord());
    }
    public ArrayList getChartData(){
        return this.data;
    }
    public void modifyChartData(){
        if(this.data.isEmpty()==true){
            BSTInfo inf = new BSTInfo(this.bst.time,getThread(),getWord());
            this.data.add(inf);
        }
        else{
            int c=0;
            for (int i=0; i<this.data.size(); i++){
                if (this.data.get(i).thread==getThread() && this.data.get(i).words==getWord()){
                    this.data.get(i).time=this.bst.time;
                    c=1;
                }
            }
            if(c==0){
                BSTInfo inf = new BSTInfo(this.bst.time,getThread(),getWord());
                this.data.add(inf);
            }
        }
        displayArray();
    }
    public void displayChart(){

        ChartGeneration lineChart = new ChartGeneration(getThread(),getWord(), this.data);
        
        this.chart=lineChart.chart;
    }
    public Scene buildScene(int state){
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 600, 600);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(15);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);

        //Defining the label and text field for thread number
        Label threadLabel = new Label("Thread number :");
        
        TextField nbt = new TextField();
        nbt.setText(this.thread);
        
        HBox thbox = new HBox(20);
        thbox.getChildren().addAll(threadLabel,nbt);
        grid.add(thbox,0,1);

        //Defining the label and text field for words number
        Label wordLabel = new Label("Word number :");

        TextField nbw = new TextField();
        nbw.setText(this.word);
        
        HBox whbox = new HBox(20);
        whbox.getChildren().addAll(wordLabel,nbw);
        grid.add(whbox,0,2);

        //Defining the benchmark launching button
        Button bench = new Button("Launch the benchmark");
        bench.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                changeData(nbt.getText(),nbw.getText());
                generateBST();
                modifyChartData();
                displayChart();
                
                reload(stage);
                
            }
        });
        bench.setAlignment(Pos.CENTER);
        grid.add(bench, 0, 3);
        if (state>0){
            grid.add(chart,0,4);
        }
        
        
        return scene;
    }
    public void reload(Stage stage){
    
    Scene scene = buildScene(10);
    
    stage.setScene(scene);
    stage.show();

}
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
