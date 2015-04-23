package game.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Image extends GameElement {
	
	private Drawable img, img_flipped;	
	Context context;
	
    public Image(Context context, int id_image, int x, int y, int width, int height) {		
    	this.context = context;
    	img = context.getResources().getDrawable(id_image);    	
    	Bitmap _imgbmp = BitmapFactory.decodeResource(context.getResources(), id_image);    	
     	Matrix mirrorMatrix = new Matrix();     	
        mirrorMatrix.preScale(-1.0f, 1.0f);         
        Bitmap _imgbmp_flipped = Bitmap.createBitmap(_imgbmp, 0, 0, _imgbmp.getWidth(), _imgbmp.getHeight(), mirrorMatrix, false);        
        img_flipped = (Drawable) new BitmapDrawable(_imgbmp_flipped);
        this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
    
    
  public Image(Context context, int id_image, int x, int y, int width, int height, String tag) {		
    	this.context = context;
    	img = context.getResources().getDrawable(id_image);    	
    	Bitmap _imgbmp = BitmapFactory.decodeResource(context.getResources(), id_image);    	
     	Matrix mirrorMatrix = new Matrix();     	
        mirrorMatrix.preScale(-1.0f, 1.0f);         
        Bitmap _imgbmp_flipped = Bitmap.createBitmap(_imgbmp, 0, 0, _imgbmp.getWidth(), _imgbmp.getHeight(), mirrorMatrix, false);        
        img_flipped = (Drawable) new BitmapDrawable(_imgbmp_flipped);    	
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;		
		this.tag = tag;		
	}    
    
    public void Draw(Canvas canvas) {
    	img.setBounds(this.x,this.y,this.width + this.x, this.height + this.y);
    	img.draw(canvas);
    }
    
    public void Draw(Canvas canvas, FlipEffect effect) {    	
    	if(effect == FlipEffect.NONE) {
    		img.setBounds(this.x,this.y,this.width + this.x, this.height + this.y);
    		img.draw(canvas);
    	} else  {    		
    		img_flipped.setBounds(this.x,this.y,this.width + this.x, this.height + this.y);
       	 	img_flipped.draw(canvas);       	 
    	}
    }
}
