package com.spuqyballz.tetriq;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class TetrisEngine {
	private long score;
	private long fLines;
	
	private Timer timer;
	
	private Tetrimino t;
	private Tetrimino b;
	private Playfield p;
	
	private TetrisGUI gui;
	private boolean gameStatus;
	
	public TetrisEngine(){
		this.score = 0;
		this.fLines = 0;
		
		t = new Tetrimino();
		b = new Tetrimino();
		p = new Playfield();
		
		gameStatus = true;
		
		gui = new TetrisGUI(b,t,p, this);
		gui.setTitle("TetriQ");
		gui.setVisible(true);
		gui.addKeyListener(new KeyListener() {
			@Override
		    public void keyPressed(KeyEvent e) {
		    	switch(e.getKeyCode()){
		    	case 16: toggle();
		    		break;
		    	case 37:moveLeft();
		    		break;
		    	case 38:rotate();
		    		break;
		    	case 39:moveRight();
		    		break;
		    	case 40:moveDown();
		    		break;
		    	case 32:dropDown();
		    		break;
		    	case 68: uniteWithField(t);
		    			 tetriminoInit();
		 			break;
		    	case 84: clearTimer();
		 			break;
		    	}
		    }

			@Override
			public void keyReleased(KeyEvent arg0) {}

			@Override
			public void keyTyped(KeyEvent arg0) {}
		});
		
		setTimer(800);
		
		tetriminoInit();
	}
	
	private void clearTimer(){
		timer.cancel();
		timer.purge();
	}
	
	private void setTimer(int ms) {
		// generate ticks
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			  @Override
			  public void run() {
			    tick();
			  }
			}, ms, ms);
	}
	
	private void increaseGameSpeed(){
		this.clearTimer();
		this.setTimer(800-(int)(this.fLines+1)*4);
		System.out.println("gamespeed increased to "+(800-(int)(this.fLines+1)*4)+"ms");
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
		}
		refresh();
	}
	public void moveLeft(){
		if(canMoveLeft()){
			t.setPosX(t.getPosX()-1);
		}
		refresh();
	}
	public void moveRight(){
		if(canMoveRight()){
			t.setPosX(t.getPosX()+1);
		}
		refresh();
	}
	public void moveDown(){
		if(canMoveDown()){
			t.setPosY(t.getPosY()+1);
		}
		refresh();
	}
	
	public void dropDown(){
		while(canMoveDown()){
			moveDown();
		}
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
		}
		if((t.getPosX()+emptyColumsFromLeft)<=0){
			return false;
		}

		// check for other blocks in playfield
		for(j=0;j<t.getySize();j++){
			for(i=0;i<t.getxSize();i++){
				if(!t.isEmpty(i, j) &&  !p.isEmpty(t.getPosX()+i-1,t.getPosY()+j) && p.isInBounds(t.getPosX()+i-1,t.getPosY()+j)){
					return false;
				}
			}
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
		
		// check for other blocks in playfield
		for(j=0;j<t.getySize();j++){
			for(i=0;i<t.getxSize();i++){
				if(!t.isEmpty(i, j) &&  !p.isEmpty(t.getPosX()+i+1,t.getPosY()+j) && p.isInBounds(t.getPosX()+i+1,t.getPosY()+j)){
					return false;
				}
			}
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
		
		// check for other blocks in playfield
		for(j=0;j<t.getySize();j++){
			for(i=0;i<t.getxSize();i++){
				if(!t.isEmpty(i, j) &&  !p.isEmpty(t.getPosX()+i,t.getPosY()+j+1) && p.isInBounds(t.getPosX()+i,t.getPosY()+j+1)){
					return false;
				}
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
	
	public void tick(){
		int lines;
		//System.out.println("tick");
		if(!this.canMoveDown()){
			this.uniteWithField(t);
			
			if(!p.rowIsEmpty(2)){
				this.setGameStatus(false);
	            try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.finalize();
			}
			
			lines = p.removeLines();
			this.score+=lines*lines;
			this.fLines+=lines;
			
			tetriminoInit();
		} else{
			moveDown();
		}
	}
	
	public void refresh(){
		gui.refresh();
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public long getfLines() {
		return fLines;
	}

	public void setfLines(long fLines) {
		this.fLines = fLines;
	}

	public boolean getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(boolean gameStatus) {
		this.gameStatus = gameStatus;
	}

	public void finalize(){
		System.out.println("game over");
		clearTimer();
		gui.dispose();
	}
	
}