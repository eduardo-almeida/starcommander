package game.android.project;

import game.util.database.Highscore;
import game.util.database.HighscoreDAO;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RecordsActivity extends Activity {

	ListView componenteListaTarefas;
	private List<Highscore> lista;
	HighscoreDAO highscoreDAO;
	ArrayList<String> listaRecord;
	int score;
	EditText txName;
	Highscore player;
	View layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record);
		Intent intent = getIntent();
		score = intent.getIntExtra("score", 0);
		highscoreDAO = new HighscoreDAO(this);
		lista = highscoreDAO.list();
		highscoreDAO = new HighscoreDAO(this);
		layout = (View) findViewById(R.id.llLayout);
		layout.setVisibility(View.GONE);
		TextView txScore = (TextView) findViewById(R.id.tvScore);
		txName = (EditText) findViewById(R.id.etName);
		// Toast.makeText(this, ""+score, Toast.LENGTH_LONG).show();

		if (score != 0) {
			layout.setVisibility(View.VISIBLE);
			txScore.setText("" + score);			
		}

		if (lista == null) {
			Highscore player = new Highscore();
			player.setName("Kevin");
			player.setScore(10);
			highscoreDAO.create(player);
			player.setName("Eduardo");
			player.setScore(15);
			highscoreDAO.create(player);
			lista = highscoreDAO.list();
		}

		listaRecord = new ArrayList<String>();
		for (Highscore record : lista) {
			listaRecord.add(record.getScore() + " - " + record.getName());
		}
		componenteListaTarefas = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_checked, listaRecord);
		componenteListaTarefas.setAdapter(adapter);
	}

	private void eventosListView() {
		BaseAdapter adapter = (BaseAdapter) componenteListaTarefas.getAdapter();
		adapter.notifyDataSetChanged();
	}

	public void add(View view) {
		lista = highscoreDAO.list();
		player = new Highscore();
		player.setScore(score);
		player.setName(txName.getText().toString());
		highscoreDAO.create(player);
		lista = highscoreDAO.list();		
		listaRecord = new ArrayList<String>();
		for (Highscore record : lista) {
			listaRecord.add(record.getScore() + " - " + record.getName());
		}
		componenteListaTarefas = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_checked, listaRecord);
		componenteListaTarefas.setAdapter(adapter);
		layout.setVisibility(View.GONE);
	}

	@Override
	protected void onResume() {
		if (listaRecord != null) {
			System.out.println("Foi");
			componenteListaTarefas = (ListView) findViewById(R.id.listView1);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_checked, listaRecord);
			componenteListaTarefas.setAdapter(adapter);
		} else {
			System.out.println("N�o");
		}
		super.onResume();
	}
}
