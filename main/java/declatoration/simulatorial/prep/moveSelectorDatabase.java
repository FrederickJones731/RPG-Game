package declatoration.simulatorial.prep;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class moveSelectorDatabase extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "selector.db";

    public static final String SELECTOR_TABLE_NAME = "selectorTable";
    public static final String COLUMN_SELECTOR_ID = "id";
    public static final String COLUMN_SELECTOR_CHAR_NAME = "name";
    public static final String COLUMN_SELECTOR_MOVE_0 = "move0";
    public static final String COLUMN_SELECTOR_MOVE_1 = "move1";
    public static final String COLUMN_SELECTOR_MOVE_2 = "move2";
    public static final String COLUMN_SELECTOR_MOVE_3 = "move3";
    public static final String COLUMN_SELECTOR_MOVE_4 = "move4";
    public static final String COLUMN_SELECTOR_MOVE_5 = "move5";

    public static final String LAST_CHAR_USED_TABLE_NAME = "lastCharUsedTable";
    public static final String COLUMN_LAST_ID = "id";
    public static final String COLUMN_LAST_CHAR_0 = "char0";
    public static final String COLUMN_LAST_CHAR_1 = "char1";
    public static final String COLUMN_LAST_CHAR_2 = "char2";


    public moveSelectorDatabase(Context c) {
        super(c, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table " + SELECTOR_TABLE_NAME +
                        "(" + COLUMN_SELECTOR_ID + " integer primary key autoincrement, " +

                        COLUMN_SELECTOR_CHAR_NAME + " text, " +

                        COLUMN_SELECTOR_MOVE_0 + " text, " +
                        COLUMN_SELECTOR_MOVE_1 + " text, " +
                        COLUMN_SELECTOR_MOVE_2 + " text, " +
                        COLUMN_SELECTOR_MOVE_3 + " text, " +
                        COLUMN_SELECTOR_MOVE_4 + " text, " +
                        COLUMN_SELECTOR_MOVE_5 + " text " + ")"
        );

        //todo - you can do both of these tables duties with ONLY the first one -
        // sort by date and time, and pull them by most recent while also storing used moves
        db.execSQL(
                "create table " + LAST_CHAR_USED_TABLE_NAME +
                        "(" + COLUMN_LAST_ID + " integer primary key autoincrement, " +

                        COLUMN_LAST_CHAR_0 + " text, " +
                        COLUMN_LAST_CHAR_1 + " text, " +
                        COLUMN_LAST_CHAR_2 + " text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }


    public ArrayList<String> getCharActionNames(String charName){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + SELECTOR_TABLE_NAME + " WHERE name = ?", new String[]{charName});
        c.moveToFirst();

        ArrayList<String> actionNames = new ArrayList<>(Arrays.asList("null", "null", "null", "null", "null"));

        if(!c.isAfterLast()) {
            //only if data exists
            for (int i = 0; i < 5; i++) {
                actionNames.set(i, c.getString(2 + i));
            }
        } else{
            // otherwise returns default
            actionNames.addAll(Arrays.asList(null, null, null, null, null));
            updateActionIds(charName, actionNames);
        }
        c.close();

        return actionNames;
    }

    public void updateActionIds(String charName, ArrayList<String> actionNames){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + SELECTOR_TABLE_NAME + " WHERE name = ?", new String[]{charName});

        if(charName.contains("'")){
            String temp = charName.substring(0, charName.lastIndexOf("'") + 1);
            temp += "'";
            temp += charName.substring(charName.lastIndexOf("'") + 1);
            charName = temp;
        }

        for (int i = 0; i < actionNames.size(); i++) {
            String cur = actionNames.get(i);
            if(cur != null && cur.contains("'")){
                String temp = cur.substring(0, cur.lastIndexOf("'") + 1);
                temp += "'";
                temp += cur.substring(cur.lastIndexOf("'") + 1);
                actionNames.set(i, temp);
            }
        }

        db.execSQL("INSERT INTO " + SELECTOR_TABLE_NAME + " (name, move0, move1, move2, move3, move4) " +
                "VALUES " +
                "('" + charName + "', '"+ actionNames.get(0)+ "', '" + actionNames.get(1) +
                "', '" + actionNames.get(2)+"', '" + actionNames.get(3) + "', '" + actionNames.get(4)+ "')");
    }


    public void setLastCharsUsed(ArrayList<String> chars){
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 0; i < chars.size(); i++) {
            db.execSQL("DELETE FROM " + LAST_CHAR_USED_TABLE_NAME + " WHERE id = ?", new String[]{String.valueOf(i)});

            if(chars.get(i).contains("'")){
                String temp = chars.get(i).substring(0, chars.get(i).lastIndexOf("'") + 1);
                temp += "'";
                temp += chars.get(i).substring(chars.get(i).lastIndexOf("'") + 1);
                chars.set(i, temp);
            }
        }

        db.execSQL("INSERT INTO " + LAST_CHAR_USED_TABLE_NAME + " (char0, char1, char2) " +
                "VALUES ('" + chars.get(0) + "', '"+ chars.get(1) + "', '" + chars.get(2) + "')");
    }

}
