package game.android.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class GameActivity extends Activity implements SensorEventListener {

	GameMain gameMain;

	enum GameState {
		PAUSE, RESUME
	}

	GameState gameState;

	private SurfaceView view;
	private SensorManager sensorManager;
	private long lastSensorUpdate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		view = new SurfaceView(this);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		gameMain = new GameMain(this);
		gameState = GameState.RESUME;

		setContentView(gameMain);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_game, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {

		case R.id.menu_pause_resume_game: {

			if (gameState == GameState.PAUSE) {
				gameMain.resumeGame();
				gameState = GameState.RESUME;

				item.setIcon(R.drawable.pause_game_icon);
				item.setTitle("Pausar Jogo");
			} else {

				gameMain.pauseGame();
				gameState = GameState.PAUSE;

				item.setIcon(R.drawable.play_game_icon);
				item.setTitle("Continuar Jogo");

			}

		}
			break;

		case R.id.menu_exit_game: {

			AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

			dialogo.setTitle("Aviso");
			dialogo.setIcon(R.drawable.game_android_icon);
			dialogo.setMessage("Deseja sair do Jogo ?");
			dialogo.setPositiveButton("Sim",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							GameActivity.this.finish();
						}
					});
			dialogo.setNegativeButton("N�o", null);

			dialogo.show();

		}
			break;

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		super.onPause();
		gameMain.pauseGame();
		sensorManager.unregisterListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Sensor acelerometro = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(this, acelerometro,
				SensorManager.SENSOR_DELAY_GAME);

		gameMain.resumeGame();
	}

	public void salvar(int score) {
		Intent intent = new Intent(this, RecordsActivity.class);
		intent.putExtra("score", score);
		startActivity(intent); 
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//tirei poso ter que voltar
//		gameMain.onExitGame();
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
	
	public void bla(){
		LayoutInflater li = LayoutInflater.from(getApplicationContext());
		final View confirm_view = li.inflate(R.layout.confirm_view, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(confirm_view);

		final EditText userInput = (EditText) confirm_view.findViewById(R.id.editTextDialogUserInput);

		// set dialog message
		alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
//			    	Toast.makeText(context, "a", Toast.LENGTH_LONG).show();
			    }
			  })
			.setNegativeButton("Cancel",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			    }
			  });

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	public void onSensorChanged(SensorEvent event) {
		long curTime = System.currentTimeMillis();
		if ((curTime - lastSensorUpdate) > 50) {
			lastSensorUpdate = curTime;
			// System.out.println(event.values[SensorManager.AXIS_X]+", "
			// +event.values[SensorManager.AXIS_Y]+", " +event.values[0]);
			// gameMain.setAceleracao(event.values[SensorManager.AXIS_X],
			// 5+event.values[SensorManager.AXIS_Y]*-1);
			gameMain.setAceleracao(event.values[SensorManager.AXIS_X], 5
					+ event.values[SensorManager.AXIS_Y] * -1);
			view.invalidate();
		}
	}
}