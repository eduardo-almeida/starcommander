package game.util.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class HighscoreDAO extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "highscore.db";
	public static final int DATABASE_VERSION = 1;
	public static final String TABLE_NAME = "highscore";

	public HighscoreDAO(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public HighscoreDAO(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuffer sql = new StringBuffer();
		sql.append("create table "+TABLE_NAME +" (");
		sql.append("id integer primary key autoincrement,");
		sql.append("name text,");
		sql.append("score integer)");
		db.execSQL(sql.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists notes");
		onCreate(db);
	}

	public void create(Highscore highscore) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("name", highscore.getName());
		contentValues.put("score", highscore.getScore());
		long id = db.insert(TABLE_NAME, null, contentValues);
		Log.v("SQLite", "create id = " + id);
	}

	public Highscore retrieve(Integer id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor result = db.rawQuery("select * from "+TABLE_NAME+" where id = ?", new String[] { Integer.toString(id) });
		Highscore highscore = null;
		if (result != null && result.getCount() > 0) {
			highscore = new Highscore();
			highscore.setId(result.getInt(0));
			highscore.setName(result.getString(1));
			highscore.setScore(result.getInt(2));
		}
		return highscore;
	}

	public void update(Highscore highscore) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("name", highscore.getName());
		contentValues.put("score", highscore.getScore());
		db.update(TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(highscore.getId()) });
	}

	public void delete(Integer id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, "id = ? ", new String[] { Integer.toString(id) });
	}

	public List<Highscore> list() {
		List<Highscore> highscores = null;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor result = db.rawQuery("select * from "+TABLE_NAME+" order by score DESC LIMIT 10", null);
		if (result != null && result.getCount() > 0) {
			highscores = new ArrayList<Highscore>();
			result.moveToFirst();
			while (result.isAfterLast() == false) {
				Highscore highscore = new Highscore();
				highscore.setId(result.getInt(0));
				highscore.setName(result.getString(1));
				highscore.setScore(result.getInt(2));
				highscores.add(highscore);
				result.moveToNext();
			}
		}
		return highscores;
	}	
	
}
