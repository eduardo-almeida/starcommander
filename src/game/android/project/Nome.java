package game.android.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Nome extends Activity{
	Button btNome;
	static String nome;
	EditText etNome;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nome);

        btNome	= (Button)   findViewById(R.id.btNome);
        btNome.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				etNome = (EditText)   findViewById(R.id.edtNomeContri);
				nome = etNome.getText().toString();
				Intent ini = new Intent(Nome.this, GameActivity.class);
				startActivity(ini);
			}
		});
	}
}
