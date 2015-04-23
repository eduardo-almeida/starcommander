package game.android.project;

import game.util.Collision;
import game.util.Explosao;
import game.util.GameState;
import game.util.Image;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class GameMain extends SurfaceView {
	GameState gameState;
	Canvas canvas;
	Thread thread = null;
	SurfaceHolder surfaceHolder;
	volatile boolean running = false;
	Context context;
	Image nave, chefe, chefe2, cena1, cena2;
	Image botao_tiro;// , seta_baixo, seta_cima, seta_esquerda, seta_direita;
	private Bitmap bmpBlood = BitmapFactory.decodeResource(getResources(),
			R.drawable.explosao);
	ArrayList<Image> aTiros;
	ArrayList<Image> aInimigos;
	ArrayList<Image> aPower;
	List<Explosao> aExplosoes;
	int contaFrames = 0;
	int contaFramesPower = 0;
	MediaPlayer mp;
	SoundPool sp;
	int id_som_laser;
	int id_som_explosao;
	ArrayList<String[]> recordJogador;

	boolean deuTiro;

	Placar placar;
	Vidas vidas;
	Especial especial;

	int total_itens = 0;
	int total_vidas = 3;
	int quant_tiros = 0;
	int total_especial = 3;
	int vidasChefe = 5;
	int fase = 0;

	// Declare os atributos aqui abaixo

	private long tempoUltAtualizacao = -1;
	private float velocX, velocY, acelX, acelY;
	public static final float PIXEL_POR_METRO = 15;

	public GameMain(Context context) {
		// M�todo construtor, disparado toda vez que o jogo a carregado na
		// mem�ria
		super(context);
		this.context = context;
		surfaceHolder = getHolder();
		// salvar dados em sharedpreference
		nave = new Image(context, R.drawable.nave, 100, 300, 120, 75);
		chefe = new Image(context, R.drawable.chefe2, 3000, 300, 240, 150);
//		chefe2 = new Image(context, R.drawable.chefe2, 3000, 300, 240, 150);
		aTiros = new ArrayList<Image>();
		aInimigos = new ArrayList<Image>();
		aPower = new ArrayList<Image>();
		aExplosoes = new ArrayList<Explosao>();

		placar = new Placar(context, 30, 30);
		placar.SetTotalItens(total_itens);

		vidas = new Vidas(context, 30, 90);
		vidas.SetTotalVidas(total_vidas);

		especial = new Especial(context, 30, 150);
		especial.SetTotalEspecial(total_especial);

		mp = MediaPlayer.create(context, R.raw.boss_battle);
		mp.start();
		mp.setLooping(true);

		sp = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		id_som_laser = sp.load(context, R.raw.laser, 0);
		id_som_explosao = sp.load(context, R.raw.explosao, 0);

		setFocusable(true);
	}

	public void som(int id) {
		sp.play(id, 1, 1, 1, 0, 1f);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		cena1 = new Image(context, R.drawable.cena_1, 0, 0, w, h);
		cena2 = new Image(context, R.drawable.cena_2, w, 0, w, h);
		botao_tiro = new Image(context, R.drawable.botao_tiro, w - 96, h - 96,
				96, 96);
	}

	@SuppressLint("WrongCall")
	public void draw() {
		canvas = surfaceHolder.lockCanvas();
		Update();

		canvas.drawColor(Color.BLACK);
		cena1.Draw(canvas);
		cena2.Draw(canvas);

		nave.Draw(canvas);
		chefe.Draw(canvas);
		for (Image tiros : aTiros) {
			tiros.Draw(canvas);
		}

		for (Image inimigos : aInimigos) {
			inimigos.Draw(canvas);
		}

		for (Image poder : aPower) {
			poder.Draw(canvas);
		}

		for (int i = aExplosoes.size() - 1; i >= 0; i--) {
			aExplosoes.get(i).onDraw(canvas);
		}

		botao_tiro.Draw(canvas);
		placar.Draw(canvas);
		vidas.Draw(canvas);
		especial.Draw(canvas);
		surfaceHolder.unlockCanvasAndPost(canvas);
	}

	public void Update() {
		calcularFisica();
		cena1.MoveByX(-10);
		cena2.MoveByX(-10);
		if (chefe.GetX() > 800) {
			chefe.MoveByX(-20);
		} 
		if (cena1.GetX() < -cena1.GetWidth())
			cena1.SetX(cena2.GetX() + cena2.GetWidth());
		if (cena2.GetX() < -cena2.GetWidth())
			cena2.SetX(cena1.GetX() + cena1.GetWidth());

		if (deuTiro) {
			if (quant_tiros < 5) {
				aTiros.add(new Image(context, R.drawable.tiro, nave.GetX()
						+ nave.GetWidth(), nave.GetY() + 25, 24, 30));
				som(id_som_laser);
				quant_tiros++;
			}
			deuTiro = false;
		}

		contaFrames++;
		if (fase == 0) {
			if ((contaFrames % 15) == 0) {
				// contaFrames = 0;
				int pos_y = (int) Math.round(Math.random()
						* (canvas.getHeight() - 50));
				aInimigos.add(new Image(context, R.drawable.inimigo1, canvas
						.getWidth(), pos_y, 90, 75));
			}
		}
		if (fase == 1) {
			if ((contaFrames % 30) == 0) {
				int pos_y = (int) Math.round(Math.random()
						* (canvas.getHeight() - 50));
				aInimigos.add(new Image(context, R.drawable.inimigo2, canvas
						.getWidth(), pos_y, 90, 75));
			}
			if ((contaFrames % 45) == 0) {
				int pos_y = (int) Math.round(Math.random()
						* (canvas.getHeight() - 50));
				aInimigos.add(new Image(context, R.drawable.chefe1, canvas
						.getWidth(), pos_y, 90, 75));
			}
			if ((contaFrames % 60) == 0) {
				int pos_y = (int) Math.round(Math.random()
						* (canvas.getHeight() - 50));
				aInimigos.add(new Image(context, R.drawable.inimigo3, canvas
						.getWidth(), pos_y, 90, 75));
			}
			if (contaFrames == 100) {
				int pos_y = (int) Math.round(Math.random()
						* (canvas.getHeight() - 50));
				aInimigos.add(new Image(context, R.drawable.chefe2, canvas
						.getWidth(), pos_y, 90, 75));
			}
		}
		contaFramesPower++;
		if (contaFramesPower == 250) {
			contaFramesPower = 0;
			int pos_y = (int) Math.round(Math.random()
					* (canvas.getHeight() - 50));
			aPower.add(new Image(context, R.drawable.power, canvas.getWidth(),
					pos_y, 63, 48));
		}

		for (int x = 0; x < aInimigos.size(); x++) {
			aInimigos.get(x).MoveByX(-10);
			if (aInimigos.get(x).GetX() < -aInimigos.get(x).GetWidth()) {
				aInimigos.remove(x);
				x--;
			}
		}
		for (int x = 0; x < aTiros.size(); x++) {
			aTiros.get(x).MoveByX(10);

			if (Collision.Check(aTiros.get(x), chefe)) {
				aTiros.remove(x);
				som(id_som_explosao);
				--quant_tiros;
				x--;
				--vidasChefe;
				++total_itens;
				placar.SetTotalItens(total_itens);
				aExplosoes.add(new Explosao(aExplosoes, this, chefe.GetX(),
						chefe.GetY(), bmpBlood));
				if (vidasChefe <= 0) {
					if (fase == 0) {
						fase = 1;
						chefe = new Image(context, R.drawable.chefe2, 3000, 300, 240, 150);
						vidasChefe = 10;
					} else {
						onExitGame();
					}
				}
				break;
			}

			for (int y = 0; y < aInimigos.size(); y++) {
				if (!aTiros.isEmpty()) {
					if (Collision.Check(aTiros.get(x), aInimigos.get(y))) {
						aExplosoes.add(new Explosao(aExplosoes, this, aInimigos
								.get(y).GetX(), aInimigos.get(y).GetY(),
								bmpBlood));
						aInimigos.remove(y);
						aTiros.remove(x);
						som(id_som_explosao);
						--quant_tiros;
						x--;
						total_itens++;
						placar.SetTotalItens(total_itens);
						break;
					}
					if (aTiros.get(x).GetX() > 1200) {
						aTiros.remove(x);
						--quant_tiros;
					}
				}
			}
		}
		for (int y = 0; y < aInimigos.size(); y++) {
			if (Collision.Check(aInimigos.get(y), nave)) {
				// iniciar fase
				aExplosoes.add(new Explosao(aExplosoes, this, aInimigos.get(y)
						.GetX(), aInimigos.get(y).GetY(), bmpBlood));
				aInimigos.remove(y);
				total_vidas--;
				vidas.SetTotalVidas(total_vidas);
				quant_tiros--;
				som(id_som_explosao);
				if (total_vidas <= 0) {
					onExitGame();
				}

				break;
			}
		}

		for (int x = 0; x < aPower.size(); x++) {
			aPower.get(x).MoveByX(-10);
			if (aPower.get(x).GetX() < -aPower.get(x).GetWidth()) {
				aPower.remove(x);
				x--;
			}
		}
		for (int y = 0; y < aPower.size(); y++) {
			if (Collision.Check(aPower.get(y), nave)) {
				aPower.remove(y);
				total_especial++;
				especial.SetTotalEspecial(total_especial);
				break;
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {

		return super.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			if (botao_tiro.IsTouch(event.getX(), event.getY())) {
				if (total_especial > 0) {
					total_especial--;
					especial.SetTotalEspecial(total_especial);
					destruirTodos();
				}
			} else {
				deuTiro = true;
			}
		}

		return true;
	}

	public void destruirTodos() {
		if (aInimigos.size() > 0) {
			som(id_som_explosao);
		}
		for (int i = 0; i < aInimigos.size(); i++) {
			aExplosoes.add(new Explosao(aExplosoes, this, aInimigos.get(i)
					.GetX(), aInimigos.get(i).GetY(), bmpBlood));
			aInimigos.remove(i);
			i--;
			total_itens++;
		}
		aInimigos = new ArrayList<Image>();
		placar.SetTotalItens(total_itens);
	}

	public void setAceleracao(float x, float y) {
		acelX = x;
		acelY = y;
	}

	public void addRecord(String a) {
		// add pelo professor estudar depois
		// AddNewRecord addNewRecord = new AddNewRecord(context);
		// addNewRecord.execute(new String[]{a});

	}

	private class AddNewRecord extends AsyncTask<String, Integer, String> {
		private AlertDialog.Builder alertDialogBuilder;
		private Context context;

		public AddNewRecord(Context context) {
			this.context = context;
		}

		protected String doInBackground(String... mesg) {
			return mesg[0];

		}

		protected void onProgressUpdate(Integer... progress) {
		}

		protected void onPostExecute(String result) {
			Toast.makeText(context, result, Toast.LENGTH_LONG).show();
		}
	}

	public void calcularFisica() {
		long tempoAtual = System.currentTimeMillis();
		if (tempoUltAtualizacao < 0) {
			tempoUltAtualizacao = tempoAtual;
			return;
		}

		long tempoDecorrido = tempoAtual - tempoUltAtualizacao;
		tempoUltAtualizacao = tempoAtual;

		velocX += ((acelX * tempoDecorrido) / 1000 * PIXEL_POR_METRO);
		velocY += ((acelY * tempoDecorrido) / 1000 * PIXEL_POR_METRO);

		if (nave.GetY() + acelY * 3 < 0) {
			nave.SetY(0);
		} else if (nave.GetY() + nave.GetHeight() + acelY * 3 > getHeight()) {
			nave.SetY(getHeight() - nave.GetHeight());
		} else {
			nave.SetY((int) (nave.GetY() + acelY * 4));
		}

		if (nave.GetX() + acelX * 3 < 0) {
			nave.SetX(0);
		} else if (nave.GetX() + nave.GetWidth() + acelX * 3 > getWidth()) {
			nave.SetX(getWidth() - nave.GetWidth());
		} else {
			nave.SetX((int) (nave.GetX() + acelX * 4));
		}

	}

	public void onExitGame() {
		// addRecord(String.valueOf(total_itens));
		mp.stop();
		synchronized (surfaceHolder) {
			((GameActivity) context).finish();
		}
		((GameActivity) context).salvar(total_itens);
	}

	public void resumeGame() {
		gameState = GameState.PLAY_RESUME;
		mp.start();
		running = true;
		thread = new Thread(new Runnable() {
			public void run() {
				while (running) {
					if (surfaceHolder.getSurface().isValid()) {
						draw();
					}
				}
			}
		});
		thread.start();
	}

	public void pauseGame() {
		gameState = GameState.PAUSE;
		mp.pause();
		boolean retry = true;
		running = false;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// switch(msg.what){
			// case GroupsActivity.DISMISS_PROGRESS_DIALOG:
			// //your existing dismiss code
			// break;
			// case GroupsActivity.CREATE_PROGRESS_DIALOG:
			// //create the dialog
			// break;
			// }
			Toast.makeText(context, "a", Toast.LENGTH_LONG).show();
		}
	};

}