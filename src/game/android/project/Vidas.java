package game.android.project;

import game.util.Image;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;

public class Vidas {
	Paint p;
	Image icone;
	int total_vidas;

	public Vidas(Context context, int x, int y) {
		icone = new Image(context, R.drawable.coracao, x, y, 50, 50);
		p = new Paint();
		p.setStyle(Style.FILL_AND_STROKE);
		p.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
		p.setTextSize(50);
		p.setColor(Color.WHITE);
	}

	public void Draw(Canvas canvas) {
		icone.Draw(canvas);
		canvas.drawText(" x " + total_vidas, icone.GetX() + 55,
				icone.GetY() + 45, p);
	}
	
	public void SetTotalVidas(int total_vidas) {
		this.total_vidas = total_vidas;
	}

}
