/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fauberteau.rbtree;

/**
 *
 * @author julien
 */
public class BSTInfo {
    public long time;
    public int thread;
    public int words;
    
    public BSTInfo(long time, int thread, int words){
        this.time=time;
        this.thread=thread;
        this.words=words;
    }
}
