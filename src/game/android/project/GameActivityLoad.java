package game.android.project;

import game.util.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class GameActivityLoad extends Activity implements SensorEventListener{
	
	GameMain gameMain;	
	enum GameState { PAUSE, RESUME }	
	GameState gameState;
	
	private SurfaceView view;
	private SensorManager sensorManager;
	private long lastSensorUpdate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
		view = new SurfaceView(this);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        
        gameMain = new GameMain(this);   
        gameMain.aInimigos = (ArrayList<Image>) ler(new File("save"));
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
    	    	
    	    	if(gameState == GameState.PAUSE) {
    	    		gameMain.resumeGame();
    	    		gameState = GameState.RESUME;
    	    		
    	    		item.setIcon(R.drawable.pause_game_icon);
      	    	    item.setTitle("Pausar Jogo");
    	    	}
    	    	else {
    	    		
    	    	  gameMain.pauseGame();
    	    	  gameState = GameState.PAUSE;
    	    		
    	    	  item.setIcon(R.drawable.play_game_icon);
  	    		  item.setTitle("Continuar Jogo");
    	    		
    	    	}
    	    	
    	    } break;
    	    
    	    
    	    case R.id.menu_exit_game : {
    	    	
    	    	AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
    	    	
    	    	dialogo.setTitle("Aviso");
    	    	dialogo.setIcon(R.drawable.game_android_icon);
    	    	dialogo.setMessage("Deseja sair do Jogo ?");
    	    	dialogo.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
					    GameActivityLoad.this.finish();	
					}
				} );
    	    	dialogo.setNegativeButton("N�o", null);
    	    	
    	    	dialogo.show();
    	    	
    	    } break;
    	
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
		Sensor acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_GAME);
		
    	gameMain.resumeGame();
    }
    
    public void salvar(Object objeto){
    	String FILENAME = "save"; 
		File file =getFileStreamPath(FILENAME); 
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos); 
			oos.writeObject(objeto); 
			oos.close(); 
			fos.close();
			System.out.println("Criou");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    
    public Object ler(File file){
    	FileInputStream fis;
    	Object retorno = null;
		try {
			fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			retorno = (Object) ois.readObject(); 
			fis.close(); 
			ois.close();
			System.out.println("Achou");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("N�O");
		} 
    	return retorno;

    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();    	
//    	gameMain.onExitGame();
    	
    }

	public void onAccuracyChanged(Sensor sensor, int accuracy) {}

	public void onSensorChanged(SensorEvent event) {
		long curTime = System.currentTimeMillis();
		if ((curTime - lastSensorUpdate) > 50) {
			lastSensorUpdate = curTime;
			System.out.println(event.values[SensorManager.AXIS_X]+", " +event.values[SensorManager.AXIS_Y]+", " +event.values[0]);
			gameMain.setAceleracao(event.values[SensorManager.AXIS_X], 5+event.values[SensorManager.AXIS_Y]*-1);
			view.invalidate();
		}		
	}   
}