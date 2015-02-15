/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fauberteau.rbtree;

import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author julien
 */
public class RandomWordGenerator implements Iterable < String >{
    private final int bound ;
    private final Random rand = new Random ();
    
    public RandomWordGenerator ( int bound ) {
        
        this . bound = bound ;
    }
    @Override
    public Iterator < String > iterator () {
    return new Iterator < String >() {
    
        private int counter = 0;
        
        @Override
        public boolean hasNext () {
        return counter < bound ? true : false ;
        }
        
        @Override
        public String next () {
            counter ++;
            //On définit un min et un max aléatoires pour avoir une taille de mot aléatoire
            int max = rand.nextInt(10 - 6 + 1) + 6; //Max entre 6 et 10
            int min = rand.nextInt(3 - 0 + 1) + 0; //Min entre 0 et 3
            int size = rand . nextInt ( max - min + 1) + min ;
            char [] word = new char [ size ];
            for (int i = 0; i < size ; i ++) {
            int c = Math . abs ( rand . nextInt ()) % 26 + 'a';
            word [ i ] = Character . toChars ( c )[0];
            }
            return new String ( word );
        }

    };
    }

}
