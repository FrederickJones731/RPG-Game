package declatoration.simulatorial;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class settingsDB extends SQLiteOpenHelper {

    private static final String SETTINGS_DB_NAME = "settei";

    private static final String SETTINGS_TABLE_NAME = "settings_table";
    private static final String SETTINGS_TABLE_ID = "id";
    private static final String SETTINGS_TABLE_MUSIC_ID = "music_id";
    private static final String SETTINGS_TABLE_SETTINGS_MUSIC_ID = "settings_music_id";
    private static final String SETTINGS_TABLE_BACKGROUND_ID = "background_id";
    private static final String SETTINGS_TABLE_CLASH_REACTIONS_MODE = "clash_mode";


    public settingsDB(Context context) {
        super(context, SETTINGS_DB_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase huiop) {
        huiop.execSQL("CREATE TABLE " + SETTINGS_TABLE_NAME +
                "( " + SETTINGS_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SETTINGS_TABLE_MUSIC_ID + " INTEGER, " +
                SETTINGS_TABLE_SETTINGS_MUSIC_ID + " INTEGER, " +
                SETTINGS_TABLE_BACKGROUND_ID + " INTEGER, " +
                SETTINGS_TABLE_CLASH_REACTIONS_MODE + " INTEGER)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }


    public int getMainMusicId() {
        SQLiteDatabase squeeb = this.getReadableDatabase();

        Cursor cur = squeeb.rawQuery("SELECT * FROM " + SETTINGS_TABLE_NAME, new String[]{});
        cur.moveToFirst();

        try {
            int id = cur.getInt(1);

            cur.close();

            return id;
        } catch (Exception e){
            return 2;
        }
    }

    public int getSettingsMusicId() {
        SQLiteDatabase squeeb = this.getReadableDatabase();

        Cursor cur = squeeb.rawQuery("SELECT * FROM " + SETTINGS_TABLE_NAME, new String[]{});
        cur.moveToFirst();

        try {
            int id = cur.getInt(2);

            cur.close();

            return id;
        } catch (Exception e){
            return 1;
        }
    }

    public int getBackgroundResId() {
        SQLiteDatabase squeeb = this.getReadableDatabase();

        Cursor cur = squeeb.rawQuery("SELECT * FROM " + SETTINGS_TABLE_NAME, new String[]{});
        cur.moveToFirst();

        try {
            Integer id = cur.getInt(3);

            cur.close();

            return id;
        } catch (Exception e){
            return 1;
        }
    }

    public int getClashMode() {
        SQLiteDatabase squeeb = this.getReadableDatabase();

        Cursor cur = squeeb.rawQuery("SELECT * FROM " + SETTINGS_TABLE_NAME, new String[]{});
        cur.moveToFirst();

        try {
            int id = cur.getInt(4);

            cur.close();

            if(id < 0 || id > 2){
                id = 1;
            }

            return id;
        } catch (Exception e){
            return 2;
        }
    }


    public void updateAll(int mainId, int setId, int backId, int clashMode) {

        SQLiteDatabase db = this.getWritableDatabase();

//        db.execSQL("DELETE FROM " + SETTINGS_TABLE_NAME + " WHERE id = ?", new String[]{String.valueOf(1)});
        db.execSQL("DELETE FROM " + SETTINGS_TABLE_NAME, new String[]{});

        db.execSQL( "INSERT INTO " + SETTINGS_TABLE_NAME + " ("+ SETTINGS_TABLE_MUSIC_ID +", " +
                SETTINGS_TABLE_SETTINGS_MUSIC_ID +", " + SETTINGS_TABLE_BACKGROUND_ID +", " +
                SETTINGS_TABLE_CLASH_REACTIONS_MODE + ") " +
                "VALUES " +
                "(" + mainId + ", " + setId + ", " + backId + ", " + clashMode + ")"
        );
    }

}
