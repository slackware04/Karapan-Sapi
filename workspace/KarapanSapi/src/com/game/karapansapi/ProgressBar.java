package com.game.karapansapi;

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;


public class ProgressBar extends Entity  {
    private  Rectangle progressRect = null; // This the progress bar which will increase and decrease
    private Sprite progressOverly =null; // this is used to overlay the progress, so make sure it has transparent background 
    private float proMax = 200; // set the max pro value can be anything like 50,100,200 so on
    private float proWidth = 0; // this will be the width of rectangle which displays the progress aka progressRect
   
    private  float proRatio = 0; // Used to make the progress bars have more then 100% as max value
    private float Progress = 0; // hold our current progress in var
    
    public ProgressBar(float pX,  float pY,  float pWidth,  float pHeight,final ITextureRegion pOverLay,VertexBufferObjectManager pVertexManager) {
            super();
      
       progressOverly= new Sprite(pX,pY,pOverLay,pVertexManager);
          
       // progressRect uses same height and width as the overlay u pass in, you can change it any time 
       // Note: you can use image as progress bar too, just change rectangle to Sprite 
         progressRect = new Rectangle(pX, pY, progressOverly.getWidth(), progressOverly.getHeight(),pVertexManager);
         super.attachChild(progressOverly);
         super.attachChild(this.progressRect);
          
       
         proWidth = progressRect.getWidth(); // apply the width
         proRatio = proWidth / proMax;     // setup Ratio   
         
         setProgress(20); // by default i set it to max
    }
    
    
    
    public void setBarColor(final float pRed, final float pGreen, final float pBlue) {
    	// use this to change colour of you prgress
    	progressRect.setColor(pRed, pGreen, pBlue);
    	
    }
   
    public void setProgressColor(final float pRed, final float pGreen, final float pBlue) {
            this.progressRect.setColor(pRed, pGreen, pBlue);
    }
  
    
    public void setProgress(float pPro) {
            if(pPro < 0){
            	pPro =0;
            	return;
            }
            Progress = pPro;
            progressRect.setWidth(proRatio * pPro);
       // pass in the amount you want to change too
    }
    
    
    
    public void setMax(final int Max){
    	// use this to change max value any time, example you wanted enemies health to be 100%
    	// then in later levels maybe 105% or 300% up to you
    	proMax=Max;
    	proRatio = proWidth / Max;
    }
    
    
    public float getProgress(){
    	// return the current progress
    	return Progress;
    }


    public float getX() {
		// TODO Auto-generated method stub
		return progressOverly.getX();
	}
    
    public float getY() {
		// TODO Auto-generated method stub
		return progressOverly.getY();
	}

	public float getHeight() {
		// TODO Auto-generated method stub
		return progressOverly.getHeight();
	}
	
	public float getWidth() {
		// TODO Auto-generated method stub
		return progressOverly.getWidth();
	}
   
}