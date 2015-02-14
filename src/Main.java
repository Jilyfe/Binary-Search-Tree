/* Copyright (c) 2015, Frédéric Fauberteau
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application
{
    
    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Binary Search Tree");
        
        Label nbThreadMsg = new Label("Nombre de Threads");
        
        TextField nbThread = new TextField();
        
        Label nbNoeudsMsg = new Label("Nombre de Noeuds");
        
        TextField nbNoeuds = new TextField();
        
        Button btn = new Button("Go");
        
        btn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                System.out.println("Clic");
            }
        });
        VBox root = new VBox();
        root.getChildren().add(nbThreadMsg);
        root.getChildren().add(nbThread);
        root.getChildren().add(nbNoeudsMsg);
        root.getChildren().add(nbNoeuds);
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
    
	public static final void main(String[] args) throws IOException, InterruptedException
	{
        //launch(args);
		int i;
	    String name = "rbtree";
	    
	    ArrayList<Thread> threads = new ArrayList<>();
	    
		Runnable runnable = new BinarySearchTree<>(5, 10);
		
		for(i=0; i<((BinarySearchTree<?>)runnable).getNbThread(); i++)
		{
			threads.add(i, new Thread(runnable));
			threads.get(i).start();
		}
		for(i=0; i<((BinarySearchTree<?>)runnable).getNbThread(); i++)
		{
			threads.get(i).join();
		}
	    /*
		BinarySearchTree<Integer> rbtree = new BinarySearchTree<>();
	    rbtree.add(10);
	    rbtree.add(15);
	    rbtree.add(8);
	    rbtree.add(12);
	    rbtree.add(4);
	    rbtree.add(9);
	    */
	    PrintWriter writer = new PrintWriter(name + ".dot");
	    writer.println(((BinarySearchTree<?>)runnable).toDOT(name));
	    writer.close();
	    ProcessBuilder builder = new ProcessBuilder("dot",
	    		"-Tpdf", "-o", name + ".pdf", name + ".dot");
	    builder.start();
	    System.exit(0);
	}
}
