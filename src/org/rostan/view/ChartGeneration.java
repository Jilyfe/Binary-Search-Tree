/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rostan.view;

import java.util.ArrayList;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.fauberteau.rbtree.BSTInfo;

/**
 *
 * @author julien
 */
public class ChartGeneration {
    
    public javafx.scene.chart.LineChart<Number, Number> chart;
    public int maxThread;
    
    public ChartGeneration(int thread, int word, ArrayList<BSTInfo> e){
        
        ArrayList<BSTInfo> d = extractDataFromArray(thread, word, e);
        
        NumberAxis x = new NumberAxis(0,this.maxThread+2, 1);
        NumberAxis y = new NumberAxis();
        x.setLabel("Number of Threads");
        y.setLabel("Time (ns)");

        this.chart = new javafx.scene.chart.LineChart<Number, Number>(x, y);
        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();

        for (int i=0; i<d.size(); i++){
            series.getData().add(new XYChart.Data<Number, Number>((Number)d.get(i).thread, (Number)d.get(i).time));
        }
        
        this.chart.getData().clear();
        this.chart.getData().add(series);
        this.chart.setTitle(" Time according to thread number ");
    }
    public ArrayList extractDataFromArray(int thread, int word, ArrayList<BSTInfo> e){
        ArrayList<BSTInfo> d = new ArrayList<BSTInfo>();
        this.maxThread=0;
        for (int i=0; i<e.size(); i++){
            if (e.get(i).words==word){
                BSTInfo bst= new BSTInfo(e.get(i).time,e.get(i).thread,e.get(i).words);
                d.add(bst);
                if (e.get(i).thread>this.maxThread){
                    this.maxThread=e.get(i).thread;
                }
            }
        }
        return d;
    }
}
