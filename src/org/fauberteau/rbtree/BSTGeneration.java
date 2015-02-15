/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fauberteau.rbtree;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author julien
 */
public class BSTGeneration {
    
    public long time;
    
    public BSTGeneration (int thread, int words){
        try{
            this.build(thread, words);
        } catch (ExecutionException e) {
                System.out.println("** RuntimeException from thread");

            }
        catch (IOException io) {
            System.out.println(io);
            }
    }
    public void build(int thread, int words) throws IOException, ExecutionException {
        String name = "rbtree"+thread;
        
        this.time=0;
        
        BinarySearchTree<String> rbtree = new BinarySearchTree<String>(thread, words);
        
        ExecutorService executor = Executors.newFixedThreadPool(thread);
        
        RandomWordGenerator gen = new RandomWordGenerator(words);
        
        for (String randString : gen) {
            try {
                time += executor.submit(new BSTAdder(randString, rbtree)).get();

            } catch (RuntimeException e) {
                System.out.println("** RuntimeException from thread");

                throw e;
            } catch (InterruptedException ei) {
                System.out.println(ei);
            }
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads. Time : " + time + " ns");
        PrintWriter writer = new PrintWriter(name + ".dot");
        writer.println(rbtree.toDOT(name));
        writer.close();
        ProcessBuilder builder = new ProcessBuilder("dot", "-Tpdf", "-o", name + ".pdf", name + ".dot");
        builder.start();
    }
    public long getTime(){
        return this.time;
    }
}
