package game.android.project;

import game.util.Image;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;

public class Especial {
	Paint p;
	Image icone;
	int total_especial;

	public Especial(Context context, int x, int y) {
		icone = new Image(context, R.drawable.botao_tiro_apertado, x, y, 50, 50);
		p = new Paint();
		p.setStyle(Style.FILL_AND_STROKE);
		p.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
		p.setTextSize(50);
		p.setColor(Color.WHITE);
	}

	public void Draw(Canvas canvas) {
		icone.Draw(canvas);
		canvas.drawText(" x " + total_especial, icone.GetX() + 55,
				icone.GetY() + 45, p);
	}

	public void SetTotalEspecial(int total_itens) {
		this.total_especial = total_itens;
	}

}
