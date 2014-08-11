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
		
		gui = new TetrisGUI(b,t,p);
		gui.setTitle("TetriQ");
		gui.setVisible(true);
		gui.addKeyListener(new KeyListener() {
			@Override
		    public void keyPressed(KeyEvent e) {
				System.out.println("keycode: "+e.getKeyCode());
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
		    	case 68: uniteWithField(t);
		    			 tetriminoInit();
		 			System.out.println("KEYPRESS - dev - unite");
		 			break;
		    	}
		    }

			@Override
			public void keyReleased(KeyEvent arg0) {}

			@Override
			public void keyTyped(KeyEvent arg0) {}
		});
		
		tetriminoInit();
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
		int emptyColumsFromLeft=0, j, i;
		
		// check for field boundaries on the right side
		for(i=0;i<(t.getySize()-1);i++){
			if(!t.columnIsEmpty(i)){
				break;
			}
			emptyColumsFromLeft++;
			System.out.println("empty from left: "+emptyColumsFromLeft);
		}
		
		if((t.getPosX()+emptyColumsFromLeft)<=0){
			return false;
		}
		return true;
	}
	public boolean canMoveRight(){
		int emptyColumsFromRight=0, j, i;
		
		// check for field boundaries on the right side
		for(i=(t.getySize()-1);i>0;i--){
			if(!t.columnIsEmpty(i)){
				break;
			}
			emptyColumsFromRight++;
		}
		
		if((t.getPosX()+t.getxSize()-emptyColumsFromRight)>(p.getxSize()-1)){
			return false;
		}
		return true;
	}
	public boolean canMoveDown(){
		int emptyRowsFromBottom=0, j, i;
		
		// check for field boundaries
		for(j=(t.getySize()-1);j>0;j--){
			if(!t.rowIsEmpty(j)){
				break;
			}
			emptyRowsFromBottom++;
		}
		
		if((t.getPosY()+t.getySize()-emptyRowsFromBottom)>(p.getySize()-1)){
			return false;
		}
		
		// check for other blocks in field
		for(i=0;i<t.getxSize();i++){
			if(!t.isEmpty(i, (t.getySize()-1)) && !p.isEmpty(t.getPosX()+i, (t.getPosY()+t.getySize()) ) && p.isInBounds(t.getPosX()+i, (t.getPosY()+t.getySize()))){
				return false;
			}
		}
		
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
	
	public void tetriminoInit(){
		t.setRandomTetrimino();
		t.setPosX(p.getxSize()/2 - t.getxSize()/2);
		t.setPosY(0);
		refresh();
	}
	
	public void refresh(){
		gui.refresh();
	}
	
}