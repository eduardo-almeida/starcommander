package game.util;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;

public class AnimationSprites extends GameElement{
		
	ArrayList<Image> aImage; 	
	int currentFrame;	
	int MAX_FRAMES;	
	int index_image;	
	Context context;
	boolean animationStoped;	
	boolean playAnimation = false;	
	boolean isLoop;
	
	public AnimationSprites(Context c,int x, int y, int width, int height) {	
		aImage =  new ArrayList<Image>();
		context = c;
		currentFrame = 0;		
		index_image = 0;		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;		
		animationStoped = true;
		isLoop = false;
	}
	
	public void Add(int Id) {		
		aImage.add(new Image(context,Id,x,y,width,height));		
	}
	
    public void Draw(Canvas canvas) {    	
    	if(!animationStoped) {    	
    		currentFrame++;    	
    	    if(currentFrame == MAX_FRAMES) {
    	   	     currentFrame = 0;
    		    index_image++;
    		    if(index_image == aImage.size()) {
    		    	if(isLoop)
    			     index_image = 0;
    		    	else {
    		    	 index_image--;
    		    	 playAnimation = false;
    		    	}    		    	
    		    }
    	     }    	
    	}    	    	
    	aImage.get(index_image).Draw(canvas);
    }
    
    
    public void Draw(Canvas canvas, FlipEffect effect) {    	
    	if(aImage.size() == 0)
    		return;
    	
    	if(!animationStoped) {    	
    		currentFrame++;    	
    	    if(currentFrame == MAX_FRAMES) {
    	   	     currentFrame = 0;
    		    index_image++;
    		    if(index_image == aImage.size()) {    		
    		    	if(isLoop)
    			     index_image = 0;
    		    	else {
    		    	 index_image--;
    		    	 playAnimation = false;
    		    	}    		    	
    		    }
    	     }    	
    	}
    	    	
    	if(effect == FlipEffect.NONE)
    		aImage.get(index_image).Draw(canvas);
    	else
    		aImage.get(index_image).Draw(canvas,FlipEffect.HORIZONTAL);	
    }
    
    public void Start(int frames, boolean loop) {    	
    	currentFrame = 0;
    	MAX_FRAMES = frames;
    	animationStoped = false;
    	isLoop = loop;    	
    	index_image = 0;    	
    	playAnimation = true;    	
    }
    
    public void Stop() {
    	animationStoped = true;
    	playAnimation = false;
    }
    
    
    public void SetX(int x) {
    	this.x = x;
    	//Altera todas as imagens
    	for(int index = 0; index < aImage.size() ; index++) {
    		aImage.get(index).SetX(x);
    	}
    }
    
    public void SetY(int y) {
    	this.y = y;
    	for(int index = 0; index < aImage.size() ; index++) {
    		aImage.get(index).SetY(y);
    	}
    }
    
    public void SetWidth(int w) {
    	this.width = w;
    	for(int index = 0; index < aImage.size() ; index++) {
    		aImage.get(index).SetWidth(w);
    	}
    }
    
    public void SetHeight(int h) {
    	this.height = h;
    	for(int index = 0; index < aImage.size() ; index++) {
    		aImage.get(index).SetHeight(h);
    	}
    }
    
    public void MoveByX(int value) {
    	
    	this.x += value;
    	for(int index = 0; index < aImage.size() ; index++) {
    		aImage.get(index).MoveByX(value);
    	}
    	
    }
    
    public void MoveByY(int value) {
    	
    	this.y += value;
    	for(int index = 0; index < aImage.size() ; index++) {
    		aImage.get(index).MoveByY(value);
    	}
    	
    }
    
    public boolean IsPlaying() {
    	if(isLoop){
    		return true;
    	} else {    		
    		return playAnimation;    		
    	}
    	
    }
   
    public int GetCount() {
    	return aImage.size();
    }

}
