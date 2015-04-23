package game.util;

import game.android.project.GameMain;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Explosao {
    private int x;
    private int y;
    private Bitmap bmp;
    private int life = -1;
    private List<Explosao> temps;
	private int width = 102;
	private int height = 102;

    public Explosao(List<Explosao> temps, GameMain gameMain, int x, int y, Bitmap bmp) {
        this.x = x;
        this.y = y;
        this.bmp = bmp;
        this.temps = temps;
    }

    public void onDraw(Canvas canvas) {    	
        update();
        int srcX = (life%4) * width;
	  	int srcY = (life/4) * height;
	  	Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
	  	Rect dst = new Rect(x, y, x + width, y + height);
		canvas.drawBitmap(bmp, src, dst, null);
    }

    private void update() {
          if (life++ > 13) {
                 temps.remove(this);
          }
    }
}