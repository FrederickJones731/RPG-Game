package declatoration.simulatorial;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;

public class bossDatabase extends SQLiteOpenHelper {

    public static final String DB_NAME = "bossDB";

    public static final String REINFORCEMENTS_TABLE_NAME = "reinforcementsTable";
    public static final String REINFORCEMENTS_COLUMN_ID = "id";
    public static final String REINFORCEMENTS_COLUMN_BOSS_NAME = "bossName";
    public static final String REINFORCEMENTS_COLUMN_SECONDARY_BOSS_NAME = "secondBossName";
    public static final String REINFORCEMENTS_COLUMN_ALLY1 = "ally1";
    public static final String REINFORCEMENTS_COLUMN_ALLY1_PERC = "ally1Perc";
    public static final String REINFORCEMENTS_COLUMN_ALLY2 = "ally2";
    public static final String REINFORCEMENTS_COLUMN_ALLY2_PERC = "ally2Perc";
    public static final String REINFORCEMENTS_COLUMN_ALLY3 = "ally3";
    public static final String REINFORCEMENTS_COLUMN_ALLY3_PERC = "ally3Perc";
    public static final String REINFORCEMENTS_COLUMN_ALLY4 = "ally4";
    public static final String REINFORCEMENTS_COLUMN_ALLY4_PERC = "ally4Perc";
    public static final String REINFORCEMENTS_COLUMN_ALLY5 = "ally5";
    public static final String REINFORCEMENTS_COLUMN_ALLY5_PERC = "ally5Perc";


    public static final String BOSS_TABLE_NAME = "bossTable";
    public static final String BOSS_COLUMN_ID = "id";
    public static final String BOSS_COLUMN_BOSS_NAME = "bossName";
    public static final String BOSS_COLUMN_STRENGTH_LEVEL = "strLevel";
    public static final String BOSS_COLUMN_AI_STYLE = "aiStyle";

    public bossDatabase(Context c) {
        super(c, DB_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(
                "create table " + REINFORCEMENTS_TABLE_NAME +
                        "(" + REINFORCEMENTS_COLUMN_ID + " integer primary key autoincrement, " +
                        REINFORCEMENTS_COLUMN_BOSS_NAME + " text, " + REINFORCEMENTS_COLUMN_SECONDARY_BOSS_NAME + " text, " +
                        REINFORCEMENTS_COLUMN_ALLY1 + " text, " + REINFORCEMENTS_COLUMN_ALLY1_PERC + " integer," +
                        REINFORCEMENTS_COLUMN_ALLY2 + " text, " + REINFORCEMENTS_COLUMN_ALLY2_PERC + " integer," +
                        REINFORCEMENTS_COLUMN_ALLY3 + " text, " + REINFORCEMENTS_COLUMN_ALLY3_PERC + " integer," +
                        REINFORCEMENTS_COLUMN_ALLY4 + " text, " + REINFORCEMENTS_COLUMN_ALLY4_PERC + " integer," +
                        REINFORCEMENTS_COLUMN_ALLY5 + " text, " + REINFORCEMENTS_COLUMN_ALLY5_PERC + " integer" + ")"
        );

        db.execSQL(
                "create table " + BOSS_TABLE_NAME +
                        "(" + BOSS_COLUMN_ID + " integer primary key autoincrement, " +
                        BOSS_COLUMN_BOSS_NAME + " text, " + BOSS_COLUMN_STRENGTH_LEVEL + " integer, " +
                        BOSS_COLUMN_AI_STYLE + " text)"
        );

        fillDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    private void fillDB(SQLiteDatabase db){


        db.execSQL("INSERT INTO " + BOSS_TABLE_NAME + " (bossName, strLevel, aiStyle) " +
                "VALUES" +
                        "('Kremlin', 1, 'reserved'), " +
                        "('Kilde', 1, 'aggressive'), " +
                        "('Krista', 1, 'neutral'), " +
                        "('Goat Guy', 1, 'neutral'), " +
                        "('Greybeard', 1, 'aggressive'), " +

                        "('Master Zhen', 2, 'reserved'), " +
                        "('Jordan', 2, 'defensive'), " +
                        "('Kanna', 2, 'reserved'), " +
                        "('Hex''ahlia', 2, 'aggressive'), " +
                        "('Morgana', 2, 'neutral'), " +

                        "('Far''quei', 3, 'aggressive'), " +
                        "('Lucida', 3, 'neutral'), " +
                        "('Solstice', 3, 'aggressive'), " +
                        "('Roark', 3, 'neutral'), " +
                        "('Eris', 3, 'neutral'), " +

                        "('Sable', 4, 'aggressive'), " +
                        "('Syphia', 4, 'aggressive'), " +
                        "('Helga', 4, 'aggressive'), " +
                        "('Sumire', 4, 'neutral'), " +
                        "('Crescentio', 4, 'aggressive'), " +

                        "('Brozigard', 5, 'reserved') "

        );

        db.execSQL("INSERT INTO " + REINFORCEMENTS_TABLE_NAME + " (bossName, secondBossName," +
                "ally1, ally1Perc, ally2, ally2Perc, ally3, ally3Perc, ally4, ally4Perc, ally5, ally5Perc)" +
                "VALUES " +
                "('Kremlin', 'Sentinel (M)', 'Swordsman', 20, 'Zealot', 30, 'Sharpshooter', 15, 'Raider', 20, 'Rogue', 15), " +
                "('Kilde', null, 'Swiftblade', 35, 'Dancer', 10, 'Henchman', 25, 'Magia (W)', 30, null, null), " +
                "('Krista', null, 'Volaguard', 35, 'Sentinel (M)', 10, 'Sentinel (F)', 10, 'Darkling', 10, 'Raider', 35), " +
                "('Goat Guy', 'Cavalier', 'Swordsman', 20, 'Swiftblade', 20, 'Guerilla-Man', 20, 'Wulfmeister', 30, 'Magia (I)', 10), " +
                "('Greybeard', 'Magia (F)', 'Sea Wolf', 45, 'Cannoneer', 10, 'Raider', 30, 'Rogue', 15, null, null), " +

                "('Master Zhen', 'Cleric', 'Monk', 30, 'Duelist', 30, 'Samurai', 15, 'Dark Cleric', 10, 'Dancer', 15), " +
                "('Kanna', null, 'Darkling', 30, 'Cavalier', 35, 'Hexmage', 20, 'Witch', 15, null, null), " +
                "('Hex''ahlia', 'Gordon', 'Witch', 30, 'Hexmage', 35, 'Rogue', 20, 'Darkling', 15, null, null), " +
                "('Jordan', 'Volaguard', 'Henchman', 30, 'Cavalier', 20, 'Rifle Knight', 30, 'Barbar', 20, null, null), " +
                "('Morgana', null, 'Dark Cleric', 20, 'Vampire', 35, 'Hexmage', 20, 'Darkling', 15, 'Ninja', 10), " +

                "('Eris', 'Snake Rider', 'Magia (F)', 20, 'Flamestalker', 15, 'Knight (M)', 25, 'Killer Priest', 20, 'Volaguard', 20), " +
                "('Solstice', 'Dark Cleric', 'Hexmage', 30, 'Magia (F)', 25, 'Darkling', 20, 'Witch', 5, 'Raider', 15), " +
                "('Lucida', 'Cowboy', 'Sentinel (M)', 30, 'Magia (W)', 25, 'Zealot', 15, 'Sea Wolf', 22, 'Motorcycle Knight', 8), " +
                "('Far''quei', 'Wulfmeister', 'Zealot', 30, 'Raider', 25, 'Rogue', 25, 'Duelist', 20, null, null), " +
                "('Roark', 'Suneki', 'Lupterra', 25, 'Wulfmeister', 30, 'Druidess', 25, 'Mermaid', 20, null, null), " +

                "('Sable', 'Gigamor', 'Snake Rider', 25, 'Grave Warden', 25, 'Ninja', 20, 'Hexblade', 10, 'Chainslinger', 20), " +
                "('Helga', 'Vale', 'Viking', 30, 'Scourger', 10, 'Witch Hunter', 23, 'Broadswordsman', 22, 'Gladiator', 15), " +
                "('Syphia', 'Dantè', 'Swordsman', 25, 'Volaguard', 25, 'Sentinel (M)', 20, 'Dark Cleric', 10, 'Raider', 20), " +
                "('Sumire', 'Captain', 'Huntress', 30, 'Sentinel (F)', 25, 'Duelist', 20, 'Penguin Knight', 10, 'Cannoneer', 15), " +
                "('Crescentio', 'Witch', 'Sentinel (M)', 30, 'Vampire', 10, 'Captain', 10, 'Armor Knight', 35, 'Magia (I)', 15), " +

                "('Brozigard', 'Paragon', 'Snake Rider', 25, 'Gladiator', 30, 'Dark Cleric', 15, 'Barbar', 20, 'Flamestalker', 10)"
        );

    }



    public void resetDB(){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + REINFORCEMENTS_TABLE_NAME, new String[]{});
        db.execSQL("DELETE FROM " + BOSS_TABLE_NAME, new String[]{});

        fillDB(db);
    }

    public generationSet getBoss(int bossStrVal, ArrayList<String> usedBosses, ArrayList<String> unusables){
        SQLiteDatabase dweeb = getReadableDatabase();

        Cursor cursor;
        String bossName;

        if(bossStrVal <= 0){
            bossStrVal = 1;
        }

        int i = 0;

        do {

            if(i > 3){
                usedBosses.clear();
            }

            cursor = dweeb.rawQuery("SELECT * FROM " + BOSS_TABLE_NAME + " WHERE strLevel = ?",
                    new String[]{String.valueOf(bossStrVal)});

            cursor.moveToPosition((int) (cursor.getCount() * Math.random()));

            bossName = cursor.getString(1);

            i++;
        } while (unusables.contains(bossName) || usedBosses.contains(bossName));

        cursor = dweeb.rawQuery("SELECT * FROM " + REINFORCEMENTS_TABLE_NAME + " WHERE bossName = ?",
                new String[]{String.valueOf(bossName)});

        cursor.moveToFirst();

        String secondaryBossName = null;
        if(!cursor.isNull(2)) {
            secondaryBossName = cursor.getString(2);
        }

        generationSet genSet = new generationSet(bossName, secondaryBossName, getHenchmen(bossName, cursor),
                getHenchmenPerc(bossName, cursor));

        cursor.close();

        return genSet;

    }

    private ArrayList<String> getHenchmen(String bossName, Cursor c){
//        SQLiteDatabase deeb = getReadableDatabase();
//
        ArrayList<String> henchmenNames = new ArrayList<>();
//
//        Cursor c = deeb.rawQuery("SELECT * FROM " + REINFORCEMENTS_TABLE_NAME + " WHERE bossName = ?",
//                new String[]{bossName});

        c.moveToFirst();

        for (int i = 3; i < c.getColumnCount(); i += 2) {
            if(c.getString(i) != null){
                henchmenNames.add(c.getString(i));
            } else {
                break;
            }
        }

        return henchmenNames;
    }
    private ArrayList<Integer> getHenchmenPerc(String bossName, Cursor c){
//        SQLiteDatabase deeb = getReadableDatabase();

        ArrayList<Integer> henchmenPerc= new ArrayList<>();

//        Cursor c = deeb.rawQuery("SELECT * FROM " + REINFORCEMENTS_TABLE_NAME + " WHERE bossName = ?",
//                new String[]{bossName});

        c.moveToFirst();

        for (int i = 4; i < c.getColumnCount(); i += 2) {
            if(c.getString(i) != null){
                henchmenPerc.add(c.getInt(i));
            }
        }

        return henchmenPerc;
    }

    public ArrayList<String> getOnsetUnpickableCharArrayList(){
        SQLiteDatabase debation = getReadableDatabase();

        ArrayList<String> unpickableCharsList = new ArrayList<>();

        Cursor c = debation.rawQuery("SELECT * FROM " + REINFORCEMENTS_TABLE_NAME, new String[]{});
        c.moveToFirst();

        for (int i = 0; i < c.getCount(); i++) {
            // add all names in database, as to remove them from the player char list
            String cur = c.getString(1);
            if(!unpickableCharsList.contains(cur)){
                unpickableCharsList.add(cur);
            }
//            cur = c.getString(2);
//            if(!unpickableCharsList.contains(cur)){
//                unpickableCharsList.add(cur);
//            }

            c.moveToNext();
        }

        return unpickableCharsList;
    }



    public static class generationSet{
        String bossName;
        String secondaryBossName;

        ArrayList<String> henchmenNames;
        ArrayList<Integer> henchmenPerc;
        ArrayList<Integer> powerLevels;

        public generationSet(String bossName, String boss2, ArrayList<String> henchmen, ArrayList<Integer> percentages){
            this.bossName = bossName;
            secondaryBossName = boss2;
            henchmenNames = henchmen;
            henchmenPerc = percentages;
        }

        public ArrayList<String> getHenchmenNames(){return henchmenNames;}
        public void setPowerLevels(ArrayList<Integer> pwrLevels){
            powerLevels = pwrLevels;
        }

        public ArrayList<String> getAdditionals(int strLevelExcludingBoss){

            int remainingStrPoints = strLevelExcludingBoss;

            ArrayList<String> selected = new ArrayList<>();

            while (remainingStrPoints > 0) {
                String selectedHenchman = getRandomHenchman();

                int index = henchmenNames.indexOf(selectedHenchman);

                if(remainingStrPoints < (powerLevels.get(index)) &&
                    remainingStrPoints > (0.5 * powerLevels.get(index))){
                    // dont overdo power lvl pls. if so, get weakest enemy
                    // if there are too little points left just skip it
                    selectedHenchman = getSmallFry();

                    index = henchmenNames.indexOf(selectedHenchman);
                }

                remainingStrPoints -= (powerLevels.get(index));

                selected.add(selectedHenchman);
            }

            return selected;
        }

        public String getRandomHenchman(){
            int perc = (int) (Math.random() * 100);
            int sum = 0;

            for (int i = 0; i < henchmenPerc.size(); i++) {
                sum += henchmenPerc.get(i);
                if(perc <= sum){
                    return henchmenNames.get(i);
                }
            }

            return henchmenNames.get((int) (Math.random() * henchmenPerc.size()));
        }

        private String getSmallFry(){
            int smallFryIndex = powerLevels.lastIndexOf(Collections.min(powerLevels));

            return henchmenNames.get(smallFryIndex);
        }
    }
}
