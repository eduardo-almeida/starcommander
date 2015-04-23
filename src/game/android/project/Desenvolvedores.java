package game.android.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Desenvolvedores extends Activity {
	Button btEduardo, btRoney, btKevin;
	
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.desenvolvedores);
			btEduardo	= (Button)   findViewById(R.id.llLayout);
			btRoney	= (Button)   findViewById(R.id.btRoney);
			btKevin	= (Button)   findViewById(R.id.btKevin);
			
			btEduardo.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					Intent ini = new Intent(Desenvolvedores.this, Eduardo.class);
					startActivity(ini);
				}
			});
			btRoney.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					Intent ini = new Intent(Desenvolvedores.this, Roney.class);
					startActivity(ini);
				}
			});
			btKevin.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					Intent ini = new Intent(Desenvolvedores.this, Kevin.class);
					startActivity(ini);
				}
			});
			
	 }
}
