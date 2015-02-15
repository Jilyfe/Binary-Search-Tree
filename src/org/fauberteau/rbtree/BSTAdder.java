/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fauberteau.rbtree;

import java.util.concurrent.*;

/**
 *
 * @author julien
 */
public class BSTAdder implements Callable<Long>{
    
    public String s;
    public BinarySearchTree<String> rbtree;
    
    public BSTAdder (String s, BinarySearchTree<String> rbtree){
        this.s=s;
        this.rbtree=rbtree;
    }
    
    @Override
    public Long call (){
       long start = System.nanoTime();
       this.rbtree.add(this.s);
       long end = System.nanoTime();
       return end-start;
    }
}
