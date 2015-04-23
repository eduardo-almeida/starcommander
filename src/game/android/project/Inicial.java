package game.android.project;

import java.io.File;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Inicial extends Activity{
	Button btIniciar, btRecord, btSobre;
	MediaPlayer mp;
	Button btnLogin;
	EditText etNome;
	public static String nome;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicial);
		btIniciar	= (Button)   findViewById(R.id.btIniciar);
		btRecord   	= (Button)   findViewById(R.id.btRecord);
		btSobre    	= (Button)   findViewById(R.id.btSobre);
//		btSair		= (Button)   findViewById(R.id.btLoad);
		
		mp = MediaPlayer.create(Inicial.this, R.raw.abertura);
		mp.start();
		mp.setLooping(true);
		
		btIniciar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent ini = new Intent(Inicial.this, GameActivity.class);
				startActivity(ini);
			}
		});
		
		btRecord.setOnClickListener(new View.OnClickListener() {
				
			public void onClick(View v) {
				mp.stop();
				Intent ini = new Intent(Inicial.this, RecordsActivity.class);
				startActivity(ini);
			}
		});
		
		btSobre.setOnClickListener(new View.OnClickListener() {
				
			public void onClick(View v) {
				mp.stop();	
				Intent ini = new Intent(Inicial.this, Desenvolvedores.class);
				startActivity(ini);
			}
		});
	 }
	
	@Override
	protected void onResume() {
		mp.start();
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		mp.stop();
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		mp.stop();
		super.onDestroy();
	}
}
