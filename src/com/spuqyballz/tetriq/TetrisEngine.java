package com.spuqyballz.tetriq;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class TetrisEngine {
	private double score;
	private double cycles;
	private double fLines;
	
	private Tetrimino t;
	private Tetrimino b;
	private Playfield p;
	
	private TetrisGUI gui;
	
	public TetrisEngine(){
		this.score = 0;
		this.cycles = 0;
		this.fLines = 0;
		
		t = new Tetrimino();
		b = new Tetrimino();
		p = new Playfield();
		
		t.setPosX(p.getxSize()/2 - t.getxSize()/2);
		t.setPosY(0);
		
		gui = new TetrisGUI(b,t,p);
		gui.setTitle("TetriQ");
		gui.setVisible(true);
		gui.addKeyListener(new KeyListener() {
			@Override
		    public void keyPressed(KeyEvent e) {
		    	switch(e.getKeyCode()){
		    	case 16: toggle();
		    			 System.out.println("KEYPRESS - toggle");
		    		break;
		    	case 37:moveLeft();
   			 			System.out.println("KEYPRESS - left");
		    		break;
		    	case 38:rotate();
		    			System.out.println("KEYPRESS - rotate");
		    		break;
		    	case 39:moveRight();
   			 			System.out.println("KEYPRESS - right");
		    		break;
		    	case 40:moveDown();
   			 			System.out.println("KEYPRESS - down");
		    		break;
		    	case 32:dropDown();
   			 			System.out.println("KEYPRESS - drop");
		    		break;
		    	}
		    }

			@Override
			public void keyReleased(KeyEvent arg0) {}

			@Override
			public void keyTyped(KeyEvent arg0) {}
		});
	}
	
	/*
	 * Tetrimino actions
	 */
	public void toggle(){
		Tetrimino tmp = new Tetrimino();
		if(canToggle()){
			tmp.setMatrix(t.getMatrix());
			t.setMatrix(b.getMatrix());
			b.setMatrix(tmp.getMatrix());
		}
		refresh();
	}
	
	public void rotate(){
		if(canRotate()){
			t.setMatrix(t.rotateLeft(t.getMatrix(), 4, 4));
			System.out.println("Tetrimino rotated");
		}
		refresh();
	}
	public void moveLeft(){
		if(canMoveLeft()){
			t.setPosX(t.getPosX()-1);
			System.out.println("Tetrimino repositioned to ["+t.getPosX()+"]["+t.getPosY()+"]");
		}
		refresh();
	}
	public void moveRight(){
		if(canMoveRight()){
			t.setPosX(t.getPosX()+1);
			System.out.println("Tetrimino repositioned to ["+t.getPosX()+"]["+t.getPosY()+"]");
		}
		refresh();
	}
	public void moveDown(){
		if(canMoveDown()){
			t.setPosY(t.getPosY()+1);
			System.out.println("Tetrimino repositioned to ["+t.getPosX()+"]["+t.getPosY()+"]");
		}
		refresh();
	}
	
	public void dropDown(){

		refresh();
	}
	
	/*
	 * Tetrimino Vs Playfield
	 */
	
	public boolean canRotate(){
		return true;
	}
	public boolean canMoveLeft(){
		return true;
	}
	public boolean canMoveRight(){
		return true;
	}
	public boolean canMoveDown(){
		return true;
	}
	
	public void uniteWithField(Tetrimino t){
		int i,j;
		
		for(j=0;j<t.getySize();j++){
			for(i=0;i<t.getxSize();i++){
				if(!t.isEmpty(i, j))
					p.setElement(t.getPosX()+i,t.getPosY()+j, t.getElement(i, j));
			}
		}
	}
	
	/*
	 * other fancy features
	 */
	
	public boolean canToggle(){
		return true;
	}
	
	public void refresh(){
		gui.refresh();
	}
	
}