package declatoration.simulatorial;


//import static declatoration.simulatorial.statBoard.statString;

import static declatoration.simulatorial.enums.stat.ATK;
import static declatoration.simulatorial.enums.stat.BLOCK;
import static declatoration.simulatorial.enums.stat.CRIT;
import static declatoration.simulatorial.enums.stat.DEF;
import static declatoration.simulatorial.enums.stat.HIT;
import static declatoration.simulatorial.enums.stat.HP;
import static declatoration.simulatorial.enums.stat.INT;
import static declatoration.simulatorial.enums.stat.LCK;
import static declatoration.simulatorial.enums.stat.MAG;
import static declatoration.simulatorial.enums.stat.MAGAVO;
import static declatoration.simulatorial.enums.stat.PHYSAVO;
import static declatoration.simulatorial.enums.stat.RES;
import static declatoration.simulatorial.enums.stat.SP;
import static declatoration.simulatorial.enums.stat.SPD;
import static declatoration.simulatorial.enums.stat.SPREGEN;
import static declatoration.simulatorial.enums.stat.statArray;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import declatoration.simulatorial.enums.affinity;
import declatoration.simulatorial.enums.weaponType;
import declatoration.simulatorial.fight.actionBoostParams;
import declatoration.simulatorial.fight.sounds.sfxCharBase;

public class numDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "nums.db";

    public static final String BASES_TABLE_NAME = "bases";
    public static final String COLUMN_BASE_ID = "id";
    public static final String COLUMN_BASE_HP = "hp";
    public static final String COLUMN_BASE_ATK = "atk";
    public static final String COLUMN_BASE_DEF = "def";
    public static final String COLUMN_BASE_MAG = "mag";
    public static final String COLUMN_BASE_INT = "int";
    public static final String COLUMN_BASE_SPD = "spd";
    public static final String COLUMN_BASE_LUCK = "luck";
    public static final String COLUMN_BASE_HIT = "hit";
    public static final String COLUMN_BASE_CRIT = "crit";
    public static final String COLUMN_BASE_PHYS_AVO = "physavo";
    public static final String COLUMN_BASE_MAG_AVO = "magavo";
    public static final String COLUMN_BASE_BLOCK = "block";
    public static final String COLUMN_BASE_SP = "sp";
    public static final String COLUMN_BASE_SP_REGEN = "sp_regen";
    public static final String COLUMN_BASE_RESILIENCE = "res";
    public static final String COLUMN_BASE_NAME = "name";


    public static final String ENEMY_TABLE_NAME = "e_bases";
    public static final String E_COLUMN_ID = "id";
    public static final String E_COLUMN_HP = "hp";
    public static final String E_COLUMN_ATK = "atk";
    public static final String E_COLUMN_DEF = "def";
    public static final String E_COLUMN_MAG = "mag";
    public static final String E_COLUMN_INT = "int";
    public static final String E_COLUMN_SPD = "spd";
    public static final String E_COLUMN_LUCK = "luck";
    public static final String E_COLUMN_HIT = "hit";
    public static final String E_COLUMN_CRIT = "crit";
    public static final String E_COLUMN_PHYS_AVO = "physavo";
    public static final String E_COLUMN_MAG_AVO = "magavo";
    public static final String E_COLUMN_BLOCK = "block";
    public static final String E_COLUMN_SP = "sp";
    public static final String E_COLUMN_SP_REGEN = "sp_regen";
    public static final String E_COLUMN_RESILIENCE = "resilience";
    public static final String E_COLUMN_NAME = "name";


    public static final String STAT_TABLE_NAME = "stat_table";
    public static final String STAT_COLUMN_ID = "id";
    public static final String STAT_COLUMN_HP = "hp";
    public static final String STAT_COLUMN_ATK = "atk";
    public static final String STAT_COLUMN_DEF = "def";
    public static final String STAT_COLUMN_MAG = "mag";
    public static final String STAT_COLUMN_INT = "int";
    public static final String STAT_COLUMN_SPD = "spd";
    public static final String STAT_COLUMN_LUCK = "luck";
    public static final String STAT_COLUMN_HIT = "hit";
    public static final String STAT_COLUMN_CRIT = "crit";
    public static final String STAT_COLUMN_PHYS_AVO = "physavo";
    public static final String STAT_COLUMN_MAG_AVO = "magavo";
    public static final String STAT_COLUMN_BLOCK = "block";
    public static final String STAT_COLUMN_SP = "sp";
    public static final String STAT_COLUMN_SP_REGEN = "sp_regen";
    public static final String STAT_COLUMN_RESILIENCE = "res";
    public static final String STAT_COLUMN_NAME = "name";
    public static final String STAT_COLUMN_LEVEL = "level";


    public static final String ENAFF_TABLE_NAME = "en_aff_table";
    public static final String ENAFF_COLUMN_ID = "id";
    public static final String ENAFF_ENEMY_NAME = "name";
    public static final String ENAFF_AFF1 = "aff1";
    public static final String ENAFF_AFF1_PERCENT = "p1";
    public static final String ENAFF_AFF2 = "aff2";
    public static final String ENAFF_AFF2_PERCENT = "p2";
    public static final String ENAFF_AFF3 = "aff3";
    public static final String ENAFF_AFF3_PERCENT = "p3";
    public static final String ENAFF_AFF4 = "aff4";
    public static final String ENAFF_AFF4_PERCENT = "p4";


    public static final String CLASS_TABLE_NAME = "classTable";
    public static final String COLUMN_CLASS_ID = "id";
    public static final String COLUMN_CLASS_CHAR_NAME = "name";
    public static final String COLUMN_CLASS_CLASSIFICATION = "classification";
    public static final String COLUMN_CLASS_AFFINITY = "affinity";
    public static final String COLUMN_CLASS_WEAPON = "weapon";
    public static final String COLUMN_CLASS_INITIAL_CLASS = "class1";
    public static final String COLUMN_CLASS_ADVANCED_CLASS = "class2";
    public static final String COLUMN_CLASS_CHAR_IMAGE = "img";
    public static final String COLUMN_CLASS_SPECIAL_ACTION_NAME = "special";
    public static final String COLUMN_CLASS_SPECIAL_ACTION_NAME2 = "special2";
    public static final String COLUMN_CLASS_SPECIAL_ACTION_NAME3 = "special3";
    public static final String COLUMN_CLASS_ASSIST_ATK_BOOST = "assistAtkBoost";
    public static final String COLUMN_CLASS_ASSIST_HIT_BOOST = "assistHitBoost";
    public static final String COLUMN_CLASS_ASSIST_CRIT_BOOST = "assistCritBoost";
    public static final String COLUMN_CLASS_ASSIST_BLOCK_BOOST = "assistBlockBoost";
    public static final String COLUMN_CLASS_BULLET_NUM = "numBullets";
    public static final String COLUMN_CLASS_STR_LEVEL = "strLevel";
    public static final String COLUMN_CLASS_SPECIALTY = "specialty";
    public static final String COLUMN_CLASS_VOICE = "voice";

    static ArrayList<String> bossesNameList;

    public numDatabase(Context c) {
        super(c, DATABASE_NAME, null, 1);
    }


    // honestly dont know if stattable works correctly (references and so on)
    @Override
    public void onCreate(SQLiteDatabase kioku) {


        kioku.execSQL(
                "create table " + BASES_TABLE_NAME +
                        "(" + COLUMN_BASE_ID + " integer primary key autoincrement, " + COLUMN_BASE_HP + " dec(5, 2), " + COLUMN_BASE_ATK + " dec(5, 2)," +
                        COLUMN_BASE_DEF + " dec(5, 2), " + COLUMN_BASE_MAG + " dec(5, 2)," + COLUMN_BASE_INT + " dec(5, 2), " +
                        COLUMN_BASE_SPD + " dec(5, 2), " + COLUMN_BASE_LUCK + " dec(5, 2), " + COLUMN_BASE_HIT + " dec(5, 2), " +
                        COLUMN_BASE_CRIT + " dec(5, 2), " + COLUMN_BASE_PHYS_AVO + " dec(5, 2)," +
                        COLUMN_BASE_MAG_AVO + " dec(5, 2), " + COLUMN_BASE_BLOCK + " dec(5, 2), " + COLUMN_BASE_SP + " dec(5, 2), " +
                        COLUMN_BASE_SP_REGEN + " dec(5, 2)," + COLUMN_BASE_RESILIENCE + " dec(5, 2), " + COLUMN_BASE_NAME + " text unique)"
        );

        kioku.execSQL(
                "create table " + ENEMY_TABLE_NAME +
                        "(" + E_COLUMN_ID + " integer primary key autoincrement, " + E_COLUMN_HP + " dec(5, 2), " + E_COLUMN_ATK + " dec(5, 2)," +
                        E_COLUMN_DEF + " dec(5, 2), " + E_COLUMN_MAG + " dec(5, 2)," + E_COLUMN_INT + " dec(5, 2), " +
                        E_COLUMN_SPD + " dec(5, 2), " + E_COLUMN_LUCK + " dec(5, 2), " + E_COLUMN_HIT + " dec(5, 2), " +
                        E_COLUMN_CRIT + " dec(5, 2), " + E_COLUMN_PHYS_AVO + " dec(5, 2)," +
                        E_COLUMN_MAG_AVO + " dec(5, 2), " + E_COLUMN_BLOCK + " dec(5, 2), " + E_COLUMN_SP + " dec(5, 2), " +
                        E_COLUMN_SP_REGEN + " dec(5, 2)," + E_COLUMN_RESILIENCE + " dec(5, 2), " + E_COLUMN_NAME + " text)"
        );


        kioku.execSQL(
                "create table " + STAT_TABLE_NAME +
                        "(" + COLUMN_BASE_ID + " integer primary key autoincrement, " + STAT_COLUMN_HP + " dec(5, 2), " + STAT_COLUMN_ATK + " dec(5, 2)," +
                        STAT_COLUMN_DEF + " dec(5, 2), " + STAT_COLUMN_MAG + " dec(5, 2)," + STAT_COLUMN_INT + " dec(5, 2), " +
                        STAT_COLUMN_SPD + " dec(5, 2), " + STAT_COLUMN_LUCK + " dec(5, 2), " + STAT_COLUMN_HIT + " dec(5, 2), " +
                        STAT_COLUMN_CRIT + " dec(5, 2), " + STAT_COLUMN_PHYS_AVO + " dec(5, 2)," +
                        STAT_COLUMN_MAG_AVO + " dec(5, 2), " + STAT_COLUMN_BLOCK + " dec(5, 2), " + STAT_COLUMN_SP + " dec(5, 2), " +
                        STAT_COLUMN_SP_REGEN + " dec(5, 2)," + STAT_COLUMN_RESILIENCE + " dec(5, 2), " + STAT_COLUMN_NAME +
                        " text references " + COLUMN_BASE_NAME + ", " + STAT_COLUMN_LEVEL + " integer)"
        );

        kioku.execSQL(
                "create table " + ENAFF_TABLE_NAME +
                        "(" + ENAFF_COLUMN_ID + " integer primary key autoincrement, " + ENAFF_ENEMY_NAME + " text, " + COLUMN_BASE_ATK + " dec(5, 2), " +
                        ENAFF_AFF1 + " text, " + ENAFF_AFF1_PERCENT + " integer," +
                        ENAFF_AFF2 + " text, " + ENAFF_AFF2_PERCENT + " integer," +
                        ENAFF_AFF3 + " text, " + ENAFF_AFF3_PERCENT + " integer," +
                        ENAFF_AFF4 + " text, " + ENAFF_AFF4_PERCENT + " integer" + ")"
        );

        kioku.execSQL(
                "CREATE TABLE " + CLASS_TABLE_NAME +
                        "(" + COLUMN_CLASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_CLASS_CHAR_NAME + " TEXT UNIQUE, " +
                        COLUMN_CLASS_CLASSIFICATION + " TEXT," +
                        COLUMN_CLASS_AFFINITY + " TEXT," +
                        COLUMN_CLASS_WEAPON + " TEXT," +
                        COLUMN_CLASS_INITIAL_CLASS + " TEXT, " +
                        COLUMN_CLASS_ADVANCED_CLASS + " TEXT, " +
                        COLUMN_CLASS_CHAR_IMAGE + " INTEGER," +
                        COLUMN_CLASS_SPECIAL_ACTION_NAME + " TEXT," +
                        COLUMN_CLASS_SPECIAL_ACTION_NAME2 + " TEXT," +
                        COLUMN_CLASS_SPECIAL_ACTION_NAME3 + " TEXT," +

                        COLUMN_CLASS_ASSIST_ATK_BOOST + " DEC(5, 2)," +
                        COLUMN_CLASS_ASSIST_HIT_BOOST + " DEC(5, 2)," +
                        COLUMN_CLASS_ASSIST_CRIT_BOOST +" DEC(5, 2)," +
                        COLUMN_CLASS_ASSIST_BLOCK_BOOST + " DEC(5, 2)," +
                        COLUMN_CLASS_BULLET_NUM + " INTEGER, " +
                        COLUMN_CLASS_STR_LEVEL + " INTEGER, " +
                        COLUMN_CLASS_SPECIALTY + " TEXT," +
                        COLUMN_CLASS_VOICE +" TEXT)");



//        kioku.execSQL("INSERT INTO stat_table(name, level) " +
//                "VALUES " +
//                "('Jim', 1), " +
//                "('Reika', 1), " +
//                "('Dumwan', 1), " +
//                "('Amber', 1), " +
//                "('Luxana', 2), " +
//                "('Krillith', 5), " +
//                "('Xeno', 8), " +
//                "('Hazel', 9)");

        fillDB(kioku);
    }

    @Override
    public void onUpgrade(SQLiteDatabase ppoi, int oldV, int newV) {
        ppoi.execSQL("DROP TABLE IF EXISTS bases");
        onCreate(ppoi);
    }

    private void fillDB(SQLiteDatabase kioku){



        //class table
        if(true) {
            kioku.execSQL("insert into classTable (name, strLevel, classification, affinity, weapon, class1, class2, special, special2, special3, " +
                    "img, assistAtkBoost, assistHitBoost, assistCritBoost, assistBlockBoost, numBullets, specialty, voice)" +
                    " values " +
                    " ('Jim', 5, 'fighter', 'steel', 'spear', 'Knight', 'Knight 2', 'defend', 'balm', 'reorient'," +
                            R.drawable.jim + ", 0.9, 1, 0, 0.6, null, 'def', 'byleth')," +
                    " ('Reika', 5, 'fighter', 'cyclone', 'sword', 'Speedster', 'Flurry Fighter', 'evade', 'inflame'," + " 'zeal'," +
                            R.drawable.reika + ", 0.8, 0.4, 1, 0.3, null, 'spd', 'corrin')," +
                    " ('Dumwan', 5, 'fighter', 'silva', 'axe', 'Brute', 'Oaf', 'attack', 'assess', 'shove'," +
                            R.drawable.dumwan + ", 1, 0.1, 0.6, 0.8, null, 'atk', 'terry')," +
                    " ('Amber', 4, 'mage_long', 'inferno', 'magic', 'Spellcaster', 'Archmage', 'flux', 'infiltrate', 'echo'," +
                            R.drawable.amber + ", 0.7, 0.7, 0.1, 1, null, 'mag', 'zelda')," +
                    " ('Luxana', 4, 'healer', 'aether', 'staff', 'Apprentice', 'Priestess', 'imbue', 'cure', 'balm', " +
                            R.drawable.luxana + ", 1, 0.8, 0.1, 0.6, 0, 'support', 'mythra')," +
                    " ('Krillith', 4, 'archer', 'gaia', 'bow', 'Bow Kid', 'Snipemaster', 'nab', 'inflame', 'swig', " +
                            R.drawable.black_knight_archer + ", 0.4, 1, 1, 0.1, null, 'def', 'sephiroth')," +
                    " ('Xeno (P)', 4, 'mage_med', 'dusk', 'magic', 'Lycan Heir', 'Divergent', 'torment', 'flux', 'anathema', " +
                            R.drawable.hijikata + ", 0.8, 0.3, 0.9, 0.5, null, 'mag', 'cloud')," +
                    " ('Xeno (W)', 5, 'beast', 'dusk', 'beast','-', 'Divergent', 'torment', 'shove', 'anathema', " +
                            R.drawable.monsta + ", 0.9, 0.3, 0.8, 0.5, null, 'atk', 'cloud')," +
                    " ('Hazel', 4, 'fighter', 'glacier', 'spear','Vagabond', 'Legionnaire', 'adapt', 'echo', 'flux', " +
                            R.drawable.hazy_mcfox + ", 1.5, 0.3, 0.5, 0.2, null, 'mix', 'zss'), " +
                    " ('Kurona', 4, 'fighter', 'venin', 'sword', 'Ninj', 'Shadowstriker', 'evade',  'infiltrate', 'assess', " +
                            R.drawable.ninjamor + ", 0.9, 0.3, 1.1, 0.2, null, 'spd', 'sheik')," +
                    " ('T''tania', 6, 'fighter', 'vamp', 'spear', 'Agent of Death', 'Vampiric Overlord', 'reform', 'anathema', 'shove', " +
                            R.drawable.goff + ", 1.3, 0.9, 0.2, 0.2, null, 'atk', 'lucina')," +
                    " ('Odedia', 5, 'fighter', 'cyclone', 'duster', 'Wiz''brah', '''Fro Knight', 'inflame',  'flux', 'balm'," +
                            R.drawable.afroman + ", 0.7, 0.6, 0.6, 0.6, null, 'def', 'falcon')," +
                    " ('Kluane', 5, 'fighter', 'torrent', 'broadsword', 'Buccanneer', 'BIG PiRATE', 'attack',  'shove', 'swig'," +
                            R.drawable.pirate_guy + ", 0.9, 0.1, 0.6, 0.9, null, 'atk', 'ganon')," +
                    " ('Cord', 5, 'fighter', 'steel', 'polearm', 'Armor Guy', 'Princess Pusher', 'defend',  'swig', 'reorient'," +
                            R.drawable.cord + ", 1, 0.1, 0.9, 0.5, null, 'def', 'snake'), " +
                    " ('Fiora', 8, 'flail', 'aether', 'flail', 'Princess', 'Brute', 'convoy',  'inflame', 'cure'," +
                            R.drawable.flailerette + ", 1, 0.1, 0.4, 1, null, 'atk', 'lucina'), " +

                    " ('Desmond', 2, 'dagger', 'venin', 'cannon', 'Explorer', 'Cannonblast Alchemist', 'crud', 'cure', 'reload'," +
                            R.drawable.desmond + ", 0.1, 1.2, 0.2, 1, 2, 'support', 'robin'), " +
                    " ('Desmond (MB)', 5, 'cannoneer', 'venin', 'cannon', 'Explorer', 'Cannonblast Alchemist', 'crud', 'cure', 'reload'," +
                            R.drawable.desmond + ", 1.2, 0, 0.3, 1, 2, 'support', 'robin'), " +
                    " ('Forest Girl', 5, 'healer', 'silva', 'spot', 'Country Gal', 'Holy Knight', 'halcyon', 'cure', 'flux', " +
                            R.drawable.forest_girl + ", 0.5, 0.9, 1, 0.8, null, 'support', 'pyra'), " +
                    " ('Stein', 5, 'fighter', 'gaia', 'halberd', 'City Guard', 'Kingsman', 'reorient', 'defend', 'reform', " +
                            R.drawable.stahl + ", 0.7, 1, 0.2, 0.6, null, 'def', 'byleth'), " +
                    " ('Rudiène', 5, 'fighter', 'cyclone', 'axe', 'War Monica', 'Gale Guardian', 'cure', 'defend', 'balm', " +
                            R.drawable.rudy + ", 0.4, 0.3, 0.9, 0.9, null, 'mix', 'byleth_f'), " +
                    " ('Tobin', 4, 'fighter', 'null', 'sword', 'Blackguard', 'Disheveled Duellist', 'en garde', 'assess', 'torment', " +
                            R.drawable.tobin + ", 0.45, 1, 0.85, 0.2, null, 'atk', 'falcon'), " +
                    " ('Saki', 5, 'fighter', 'inferno', 'punch', 'Ruffianette', 'Scrapper Queen', 'daunt', 'zeal', 'inflame', " +
                            R.drawable.sakiii + ", 0.8, 0.4, 0.9, 0.4, null, 'atk', 'mii'), " +
                    " ('Gordon', 4, 'mage_med', 'force', 'magic', 'goast', 'GOAST', 'attack', 'zeal', 'big brain', " +
                            R.drawable.gordonnnnn + ", 2, 0, 0, 0.5, null, 'def', 'ganon'), " +
                    " ('Volfson', 4, 'dagger', 'dusk', 'dagger', 'Outlaw', 'Pilferer Extraordinare', 'nab', 'evade', 'infiltrate', " +
                            R.drawable.volfson + ", 0.5, 1, 0.7, 0.3, null, 'spd', 'cloud'), " +
                    " ('Sariel', 4, 'fighter', 'glacier', 'punch', 'Wiki Wayfarer', 'Peerless Priest', 'torment', 'imbue', 'infiltrate', " +
                            R.drawable.sariel + ", 0.15, 0.9, 0.9, 0.55, null, 'support', 'robin'), " +
                    " ('Ek''rünne', 5, 'archer', 'thunder', 'bow', 'Wanderer', 'Extordinarr', 'echo', 'cure', 'infiltrate', " +
                            R.drawable.ekrunne + ", 0.3, 0.9, 1, 0.2, null, 'spd', 'robin_f'), " +
                    " ('Zai''us', 6, 'fighter', 'thunder', 'spear', 'Charged Megalancer', 'Lightning Warrior', 'defend', 'zeal', 'reform', " +
                            R.drawable.zaius2 + ", 1, 0.2, 0.9, 0.4, null, 'mag', 'snake'), " +
//                " ('Zai''us', 'fighter', 'thunder', 'spear', 'Tragic Hero', 'Prince of Despair', 'inflame'," + R.drawable.sakiii + ", 0.8, 0.4, 0.9, 0.4), " +

                    " ('Hex''ahlia', 8, 'mage_long', 'ghenju', 'hex', 'Hex Maniac', 'Cataclysma', 'torment', 'reform', 'adapt', " +
                            R.drawable.hexahlia + ", 2, 0, 1, 0, null, 'mag', 'sheik'), " +
                    " ('Dantè', 6, 'range2', 'torrent', 'polearm', 'Puss in Boots', 'Phantom Thief', 'nab', 'balm', 'evade', " +
                            R.drawable.dante + ", 0, 1.2, 0.3, 1, null, 'spd', 'robin'), " +
                    " ('Syphia', 10, 'mage_short', 'dusk_inferno', 'hex', 'Battlemage', 'Shadowheart', 'reform', 'zeal', 'echo', " +
                            R.drawable.syphia + ", 1.1, 0.4, 0, 1, null, 'mag', 'robin'), " +
                    " ('Far''quei', 10, 'fighter', 'inferno', 'axe', 'Plunderer', 'Ravager', 'inflame', 'attack', 'infiltrate', " +
                            R.drawable.farquei + ", 1.3, 0.2, 0.9, 0.1, null, 'atk', 'ganon'), " +
                    " ('Kanna', 7, 'mage_long', 'glacier', 'magic', 'Hex Girl', 'Necrofrost', 'direct', 'flux', 'torment', " +
                            R.drawable.kanna + ", 1, 0.5, 0.5, 0.5, null, 'mag', 'zelda'), " +
                    " ('Firth', 8, 'beast', 'dragonsvalk', 'beast', 'Dragonsvalk', 'Lass', 'shove', 'flux', 'inflame', " +
                            R.drawable.firth + ", 1, 0, 0, 1.5, null, 'mix', 'pyra'), " +

                    " ('Banjo', 5, 'fighter', 'steel', 'sword', 'Samurai', 'Misanthropist', 'infiltrate', 'zeal', 'inflame', " +
                            R.drawable.banjo + ", 0.6, 0.8, 1, 0.1, null, 'def', 'cloud'), " +
                    " ('Leh''wahj', 5, 'fighter', 'gaia', 'spear', 'Defender', 'Stoneface', 'defend', 'torment', 'reform', " +
                            R.drawable.lehwahj + ", 0.9, 0.9, 0.2, 0.5, null, 'def', 'snake'), " +
                    " ('Soluxe', 5, 'mage_med', 'silva', 'magic', 'Fashionista', 'Silkenist', 'flux', 'balm', 'echo', " +
                            R.drawable.soluxe + ", 0.8, 0.4, 0.3, 1, null, 'mag', 'zelda'), " +
                    " ('Aria', 7, 'fighter', 'inferno', 'polearm', 'Amazoness', 'Flame Spirit', 'inflame', 'reorient', 'anathema', " +
                            R.drawable.aria + ", 1.2, 0, 0.8, 0.5, null, 'atk', 'byleth_f'), " +
                    " ('Rozelia', 4, 'archer', 'torrent', 'bow', 'Poacher', 'Highwayman', 'assess', 'nab', 'convoy', " +
                            R.drawable.rozelia + ", 1, 1, 0, 0.5, null, 'spd', 'mythra'), " +

                    " ('Briskette', 5, 'dancer', 'torrent', 'magic', 'Dancer', 'Temptress', 'dance', 'imbue', 'flux', " +
                            R.drawable.briskette + ", 0.6, 0.5, 0.15, 1.25, null, 'support', 'pyra'), " +
                    " ('Rosa', 4, 'gunman_med', 'glacier', 'rifle', 'Medic', 'Amaterasu', 'cure', 'reload', 'echo', " +
                            R.drawable.rosa + ", 0.2, 0.8, 0.3, 1.2, 4, 'support', 'corrin'), " +
                    " ('Juan', 4, 'gunman_long', 'dusk', 'rifle', 'Marksman', 'Eagle Eye', 'balm', 'reload', 'torment', " +
                            R.drawable.juan + ", 0.7, 0.7, 0.8, 0.3, 2, 'atk', 'sephiroth'), " +
                    " ('Penselle', 5, 'gunman_short', 'gaia', 'rifle', 'Savage', 'Berserker', 'attack', 'reload', 'swig', " +
                            R.drawable.penselle + ", 1, 0, 1, 0.5, 5, 'atk', 'mythra'), " +
                    " ('Guatemala', 5, 'cannoneer', 'null', 'cannon', 'Militant', 'Partisan', 'reorient', 'infiltrate', 'reload'," +
                            R.drawable.guatemala + ", 1, 0, 0.2, 1.3, 3, 'atk', 'falcon'), " +
                    " ('Lukio', 6, 'fighter', 'force', 'halberd', 'Roughneck', 'Goon', 'attack', 'inflame', 'swig', " +
                            R.drawable.lukiooooo + ", 0.85, 0.2, 1, 0.45, null, 'atk', 'ike'), " +
                    " ('Lukia', 5, 'dagger', 'aether', 'dagger', 'Trickster', 'Luminary', 'flux', 'nab', 'torment', " +
                            R.drawable.lukia + ", 0.6, 0.3, 0.7, 0.9, null, 'mag', 'robin_f')," +
                    " ('Bridget', 6, 'fighter', 'venin', 'broadsword', 'Mercenary', 'Paragon', 'butter up', 'echo', 'zeal', " +
                            R.drawable.bridget + ", 0.8, 0.8, 0.5, 0.4, null, 'atk', 'lucina'), " +

                    " ('Lumen', 5, 'mage_med', 'ghenju', 'magic', 'Jack-in-the-Box', 'Trickster', 'transposition', 'flux', 'anathema', " +
                            R.drawable.lumen + ", 0.9, 0.2, 1, 0.4, null, 'mag', 'robin'), " +
                    " ('Panda', 6, 'fighter', 'force', 'punch', 'Fighter', 'Pugilist', 'zeal', 'infiltrate', 'balm', " +
                            R.drawable.panda + ", 1.1, 0.4, 0.7, 0.3, null, 'atk', 'dk'), " +
                    " ('Celestina', 6, 'fighter', 'aether', 'spear', 'Paladin', 'Holy Knight', 'defend', 'reform', 'reorient', " +
                            R.drawable.celestina + ", 0.7, 0.7, 0.3, 0.8, null, 'def', 'byleth_f'), " +
                    " ('Vinicius', 6, 'gunman_med', 'aether', 'rifle', 'Operative', 'BEANS', 'reload', 'anathema', 'cure', " +
                            R.drawable.vin + ", 0.6, 1, 0.1, 0.8, 9, 'atk', 'terry'), " +
                    " ('Vale', 8, 'fighter', 'ghenju', 'broadsword', 'Dark Knight', 'Hexblade', 'torment', 'zeal', 'harass', " +
                            R.drawable.vale + ", 0.9, 0.4, 1.1, 0.1, null, 'mag', 'zss'), " +
                    " ('Sable', 8, 'fighter', 'dusk', 'sword', 'Flamebrand', 'Blazeblood', 'limit_break', 'echo', 'zeal', " +
                            R.drawable.sable + ", 0.8, 0.5, 0.9, 0.3, null, 'mixed', 'ike'), " +
                    " ('Felipe', 9, 'beast', 'dragonsvalk', 'beast', 'Ford Focus', 'Focus Ford', 'dance', 'reload', 'cure', " +
                            R.drawable.filipe + ", 1, 0.1, 1, 0.4, 20, 'atk', 'dk')"

            );


            if (true) {
                kioku.execSQL("insert into classTable (name, strLevel, classification, affinity, weapon, class1, class2, special, special2, special3, " +
                    "img, assistAtkBoost, assistHitBoost, assistCritBoost, assistBlockBoost, numBullets, specialty, voice)" +
                    " values " +
                    "('Cactus', 2, 'fighter', 'silva', 'spear', 'Spikeman', 'Cactus', 'infiltrate', null, null, " +
                        R.drawable.cactus + ", 1, 0.4, 0.6, 0.5, null, 'def', 'terry')," +
                    "('Le Roca', 1, 'fighter', 'gaia', 'punch', 'pebble', 'ROCK', null, null, null, " +
                        R.drawable.le_roca + ", 1.3, 0.2, 0.1, 0.9, null, 'def', 'dk')," +
                    "('Dar Fischen', 2, 'fighter', 'torrent', 'punch', 'Small Fry', 'Body Builder', 'inflame', null, null, " +
                        R.drawable.dar_fischen + ", 1, 0, 1, 0.5, null, 'atk', 'dk')," +
                    "('Suneki', 1, 'fighter', 'venin', 'sword', 'snek', 'Suneki', null, null, null, " +
                        R.drawable.suneki + ", 1.1, 0.8, 0.2, 0.4, null, 'spd', 'dk')," +
                    "('Bload', 2, 'fighter', 'glacier', 'punch', 'Bload', 'Bload', null, null, null, " +
                        R.drawable.bload + ", 2, 0.1, 0.4, 0, null, 'def', 'ike')," +
                    "('Berb (Chimken)', 1, 'mage_med', 'cyclone', 'magic', 'Prototype', 'Attack Drone', null, null, null, " +
                        R.drawable.birb3 + ", 1.2, 0.6, 0, 0.7, null, 'mag', 'dk')," +
                    "('Berb (Duck)', 2, 'mage_med', 'torrent', 'magic', 'duck', 'Attack Drone', null, null, null, " +
                        R.drawable.duck + ", 0.8, 0.8, 0.2, 0.7, null, 'mag', 'dk')," +
                    "('Berb (Owl)', 1, 'mage_med', 'cyclone', 'magic', 'Sage', 'Attack Drone', 'balm', null, null, " +
                        R.drawable.birb2 + ", 0.5, 1.3, 0, 0.7, null, 'support', 'dk')," +
                    "('Berb (Goose)', 2, 'fighter', 'dusk', 'punch', 'death', 'Destroyer', 'inflame', null, null, " +
                        R.drawable.goose + ", 1.5, 0, 1, 0, null, 'atk', 'snake')," +

                    "('Swordsman', 4, 'fighter', 'null', 'sword', 'Swordsman', 'Duelist', 'evade', 'defend', 'reform', " +
                        R.drawable.lonkuhuu + ", 0.8, 0.6, 0.6, 0.5, null, 'mix', 'cloud')," +
                    "('Swiftblade', 3, 'fighter', 'cyclone', 'sword', 'Quickster', 'Swiftblade', 'evade', 'infiltrate', 'zeal', " +
                        R.drawable.lyn + ", 1, 0.2, 0.9, 0.4, null, 'spd', 'mythra')," +
                    "('Henchman', 3, 'fighter', 'null', 'spear', 'Henchman', 'Minion', 'defend', 'balm', null, " +
                        R.drawable.henchman + ", 1, 1, 0.1, 0.4, null, 'def', 'ike')," +
                    "('Zealot', 3, 'fighter', 'null', 'axe', 'Zealot', 'Barbarian', 'shove', 'swig', null, " +
                        R.drawable.zealot + ", 1.1, 0, 0.7, 0.7, null, 'atk', 'ganon')," +
                    "('Sharpshooter', 4, 'archer', 'null', 'bow', 'Dullshooter', 'Sharpshooter', 'assess', 'balm', null," +
                        R.drawable.sharpshooter + ", 0.2, 1, 0.6, 0.7, null, 'spd', 'byleth')," +
                    "('Huntress', 4, 'range2', 'silva', 'bow', 'Huntress', 'Amazoness', 'flux', 'inflame', null," +
                        R.drawable.huntress + ", 0.5, 0.8, 0.8, 0.4, null, 'atk', 'lucina')," +
                    "('Magia (F)', 4, 'mage_long', 'inferno', 'magic', 'Magia (F)', 'Flame Elementalist', 'flux', 'zeal', null," +
                        R.drawable.magia + ", 1.2, 0.5, 0.3, 0.5, null, 'mag', 'zelda')," +
                    "('Magia (I)', 3, 'mage_med', 'glacier', 'magic', 'Magia (I)', 'Frost Elementalist', 'flux', 'balm', null," +
                        R.drawable.magia + ", 0.5, 1, 0.5, 0.5, null, 'mag', 'zelda')," +
                    "('Magia (W)', 4, 'mage_med', 'cyclone', 'magic', 'Magia (W)', 'Storm Elementalist', 'flux', 'assess', null," +
                        R.drawable.magia + ", 0.9, 0.65, 0.85, 0.2, null, 'mag', 'zelda')," +
                    "('Hexmage', 4, 'mage_med', 'dusk', 'hex', 'Magia (H)', 'Hexmage', 'anathema',  'infiltrate', 'torment'," +
                        R.drawable.hexmage + ", 1.1, 0.1, 0.8, 0.5, null, 'mag', 'zss')," +

                    "('Cavalier', 4, 'fighter', 'gaia', 'spear', 'Cavalier', 'Paladin', 'defend', 'shove', 'infiltrate'," +
                        R.drawable.cavalier + ", 1.2, 0.8, 0.2, 0.3, null, 'def', 'byleth')," +
                    "('Rogue', 3, 'dagger', 'dusk', 'dagger', 'Rogue', 'Assassin', 'nab', 'evade', 'echo'," +
                        R.drawable.dagger_girl + ", 0.5, 0.8, 1, 0.2, null, 'spd', 'sheik')," +
                    "('Vampire', 4, 'fighter', 'vamp', 'sword', 'Vampire', 'Vampire EX', 'anathema', 'shove', 'reform'," +
                        R.drawable.vampire + ", 1.2, 0.1, 0.2, 1, null, 'atk', 'cloud')," +
                    "('Sea Wolf', 3, 'fighter', 'torrent', 'sword', 'Piratess', 'Buccanneer', 'shove', 'infiltrate', 'zeal'," +
                        R.drawable.piratess + ", 1, 0.1, 0.7, 0.7, null, 'atk', 'lucina')," +
                    "('Duelist', 3, 'fighter', 'force', 'punch', 'Duelist', 'Grappler', 'attack', 'evade', 'inflame'," +
                        R.drawable.chun_li + ", 0.2, 1, 0.5, 0.8, null, 'spd', 'mii')," +
                    "('Cleric', 4, 'healer', 'aether', 'staff', 'Cleric', 'Shrine Maiden', 'imbue', 'cure', 'balm'," +
                        R.drawable.cleric + ", 0.9, 0.1, 0, 1.5, null, 'support', 'corrin')," +
                    "('Raider', 3, 'fighter', 'dusk', 'axe', 'Raider', 'Outlaw', 'inflame', 'swig', null," +
                        R.drawable.raider + ", 0.8, 0.3, 1.2, 0.2, null, 'atk', 'snake')," +

                    "('Monk', 4, 'fighter', 'force', 'punch', 'Monk', 'Monk-E', 'assess', 'reorient', 'zeal'," +
                        R.drawable.monke + ", 0.8, 0.3, 1.2, 0.2, null, 'spd', 'robin')," +
                    "('Hula Girl', 5, 'dagger', 'silva', 'dagger', 'Hula Girl', 'Nature Oracle', 'flux', 'dance', null," +
                        R.drawable.hula_girl + ", 0.8, 0.3, 1.2, 0.2, null, 'mix', 'pyra')," +
                    "('Motorcycle Knight', 7, 'gunman_long', 'dusk', 'rifle', 'Backstreet Biker', 'Motorcycle Knight', 'swig', 'reload', 'inflame'," +
                        R.drawable.motorcycle_knight + ", 0.8, 0.3, 1.2, 0.2, 4, 'atk', 'sephiroth')," +
                    "('Volaguard', 5, 'fighter', 'inferno', 'broadsword', 'Armor Girl', 'Furious Flameblade', 'inflame', 'infiltrate', 'zeal'," +
                        R.drawable.armor_girl + ", 0.8, 0.3, 1.2, 0.2, null, 'def', 'byleth')," +
                    "('Armor Knight', 5, 'fighter', 'steel', 'polearm', 'Armor Knight', 'Tank', 'defend', 'shove', null," +
                        R.drawable.knight + ", 0.8, 0.3, 1.2, 0.2, null, 'def', 'ganon')," +
                    "('Dark Cleric', 4, 'healer', 'ghenju', 'punch', 'Dark Cleric', 'Abyssal Scourger', 'imbue', 'anathema', 'torment'," +
                        R.drawable.dark_cleric + ", 0.8, 0.3, 1.2, 0.2, null, 'mix', 'cloud')," +
                    "('Fireman', 4, 'fighter', 'torrent', 'axe', 'Fireman', 'Aquaman', 'flux', 'balm', 'reorient'," +
                        R.drawable.fireman + ", 0.8, 0.3, 1.2, 0.2, null, 'mix', 'byleth')," +
                    "('Waterman', 4, 'fighter', 'inferno', 'broadsword', 'Waterman', 'Human Torch', 'attack',  'balm', 'flux'," +
                        R.drawable.waterman + ", 0.8, 0.3, 1.2, 0.2, null, 'mix', 'falcon')," +

                    "('Chef', 3, 'dagger', 'inferno', 'dagger', 'Sous Chef', 'Master Chef', 'attack', 'balm', 'inflame'," +
                        R.drawable.chef + ", 0.2, 0.8, 0.8, 0.7, null, 'support', 'falcon')," +
                    "('Wulfmeister', 4, 'archer', 'silva', 'bow', 'Ranger', 'Double Team', 'flux', 'infiltrate', 'zeal'," +
                        R.drawable.volfmeister + ", 0.4, 1, 1, 0.1, null, 'spd', 'robin')," +
                    "('Dancer', 5, 'dancer', 'null', 'magic', 'Belly Dancer', 'Aidoru', 'dance', 'cure', null," +
                        R.drawable.dancer + ", 1, 0.5, 0, 1, null, 'spd', 'zelda')," +
                    "('Cowboy', 5, 'gunman_med', 'inferno', 'rifle', 'Cowboy', 'Wrangler', 'attack', 'reload', 'shove'," +
                        R.drawable.cowboy + ", 1, 1, 0, 0.5, 6, 'atk', 'ike')," +
                    "('Samurai', 4, 'fighter', 'steel', 'sword', 'Samurai', 'Nightstalker', 'evade', 'defend', 'infiltrate'," +
                        R.drawable.samuria + ", 0.5, 0.9, 0.9, 0.2, null, 'spd', 'cloud')," +
                    "('Darkling', 6, 'dagger', 'aether', 'dagger', 'Silvercloak', 'Darkling', 'nab', 'evade', 'torment'," +
                        R.drawable.tife + ", 0.7, 0.4, 0.7, 0.7, null, 'mag', 'sheik')," +
                    "('Sentinel (M)', 5, 'fighter', 'steel', 'halberd', 'Defender', 'Sentinel', 'defend', 'infiltrate', 'reorient'," +
                        R.drawable.halberd_guy + ", 1, 0.5, 0.5, 0.5, null, 'def', 'byleth')," +
                    "('Sentinel (F)', 5, 'fighter', 'steel', 'polearm', 'Defender', 'Sentinel', 'defend', 'infiltrate', 'echo'," +
                        R.drawable.polearmette + ", 1, 0.5, 0.5, 0.5, null, 'def', 'byleth_f')," +
                    "('Cannoneer', 6, 'cannoneer', 'steel', 'cannon', 'Cannoneer', 'Artilerian', 'attack', 'reload', null, " +
                        R.drawable.cannoneer + ", 1, 0.5, 0, 1, 1, 'atk', 'lucina')," +
                    "('Witch', 6, 'mage_long', 'dusk', 'hex', 'Witch', 'Mastermind', 'flux', 'anathema', 'torment', " +
                        R.drawable.which + ", 1.5, 0.3, 0.4, 0.8, null, 'mag', 'robin_f')," +

                    "('Apothecary', 5, 'healer', 'venin', 'potion', 'Clerk', 'Dealer', 'cure', 'balm', 'flux'," +
                        R.drawable.fatman + ", 0.1, 1, 0.1, 1.3, null, 'support', 'robin')," +
                    "('Bard', 5, 'dancer', 'aether', 'magic', 'Street Performer', 'Singer', 'dance', 'reorient', 'echo'," +
                        R.drawable.bard + ", 0.5, 0.5, 0.5, 1, null, 'support', 'robin')," +
                    "('Boltstriker', 5, 'archer', 'steel', 'bow', 'Guard', 'Bow Sentinel', 'assess', 'infiltrate', 'reorient'," +
                        R.drawable.boltstriker + ", 1, 0.8, 0.7, 0, null, 'def', 'ike')," +
                    "('Chainslinger', 6, 'flail', 'steel', 'chain', 'Slinkey', 'Chainslinker', 'defend', 'inflame', 'reform'," +
                        R.drawable.whiplash + ", 1, 0.5, 1, 0, null, 'atk', 'ike')," +
                    "('Cybernaut', 7, 'gunman_med', 'dusk', 'rifle', 'Alien', 'Cybernaut', 'assess', 'flux', 'anathema'," +
                        R.drawable.cybernaut + ", 1.3, 0, 0.5, 0.7, 4, 'mag', 'snake')," +
                    "('Flamestalker', 6, 'archer', 'inferno', 'bow', 'Flamestalker', 'Crimsoncloak', 'inflame', 'nab', 'flux'," +
                        R.drawable.flamearrow + ", 0.6, 0.8, 0.8, 0.3, null, 'mixed', 'mythra')," +
                    "('Gigamor', 8, 'fighter', 'steel', 'polearm', 'CYBORG', 'SHREDDER XT-12', 'reform', 'defend', 'assess'," +
                        R.drawable.gigamor + ", 1.2, 0, 0, 1.3, null, 'def', 'dk')," +
                    "('Gladiator', 7, 'fighter', 'force', 'spear', 'Peasant', 'Gladiator', 'zeal', 'shove', 'infiltrate'," +
                        R.drawable.gladiator + ", 1, 0.7, 0, 0.8, null, 'atk', 'ganon')," +
                    "('Grave Warden', 7, 'mage_short', 'ghenju', 'hex', 'Necroprayer', 'Grave Warden', 'flux', 'anathema', 'torment'," +
                        R.drawable.death_cleric + ", 1, 0.2, 0.3, 1, null, 'mag', 'sephiroth')," +
                    "('Gunslinger', 6, 'gunman_med', 'null', 'rifle', 'Tinkerer', 'Gunslinger', 'reload', 'assess', 'anathema'," +
                        R.drawable.gunslinger + ", 1, 1, 0, 0.5, 6, 'mag', 'robin')," +
                    "('Hexblade', 7, 'fighter', 'ghenju', 'broadsword', 'Devotee', 'Hexblade', 'flux', 'echo', 'reform'," +
                        R.drawable.hexblade + ", 0.8, 0.8, 0, 0.9, null, 'mixed', 'zss')," +
                    "('Knight (M)', 5, 'fighter', 'steel', 'spear', 'Defender', 'Knight', 'defend', 'balm', 'reform'," +
                        R.drawable.knight_male + ", 1, 1, 0, 0.5, null, 'def', 'ike')," +
                    "('Knight (F)', 5, 'fighter', 'steel', 'spear', 'Defender', 'Knight', 'defend', 'balm', 'reform'," +
                        R.drawable.knight_fem + ", 1, 1, 0, 0.5, null, 'def', 'lucina')," +
                    "('Mermaid', 6, 'mage_med', 'torrent', 'magic', 'Fishtail', 'Mermaid', 'flux', 'echo', 'cure'," +
                        R.drawable.mermaid + ", 1, 0.8, 0.1, 0.6, null, 'mag', 'corrin')," +
                    "('Scourger', 7, 'fighter', 'force', 'broadsword', 'Scavenger', 'Scourger', 'zeal', 'reform', 'infiltrate'," +
                        R.drawable.scourger + ", 1.1, 0.4, 1, 0, null, 'atk', 'byleth_f')," +
                    "('Shovel Knight', 5, 'healer', 'gaia', 'halberd', 'Farmer', 'Shovel Knight', 'cure', 'imbue', 'shove'," +
                        R.drawable.shovel_knight + ", 0.3, 1, 0.2, 1, null, 'support', 'zelda')," +

                    "('Snake Rider', 6, 'fighter', 'venin', 'spear', 'Slipp', 'Snake RAIDA', 'anathema', 'infiltrate', 'shove'," +
                        R.drawable.snake_rider + ", 0.8, 0.2, 0.5, 1, null, 'atk', 'dk')," +
                    "('Viking', 5, 'fighter', 'torrent', 'axe', 'Farmer', 'Boatman', 'zeal', 'crud', 'swig'," +
                        R.drawable.viking + ", 1, 0.1, 0.6, 0.8, null, 'atk', 'ike')," +
                    "('Rifle Knight', 6, 'gunman_med', 'steel', 'rifle', 'Pistol Knight', 'Rifle Knight', 'defend', 'assess', 'balm'," +
                        R.drawable.sniper_knight + ", 0.6, 1.1, 0, 0.8, 6, 'def', 'byleth')," +
                    "('Broadswordsman', 5, 'fighter', 'null', 'broadsword', 'Street Rat', 'Cloudmaster', 'limit_break', 'nab', 'zeal'," +
                        R.drawable.cloud + ", 1, 0.3, 0.7, 0.8, null, 'atk', 'cloud')," +
                    "('Killer Priest', 5, 'gunman_med', 'ghenju', 'spear', 'Cultist', 'Reverant', 'echo', 'reload', 'anathema'," +
                        R.drawable.killer_priest + ", 0.6, 0.4, 1, 0.5, null, 'mixed', 'snake')," +
                    "('Druidess', 6, 'mage_med', 'silva', 'axe', 'Ranger', 'Spirit Guardian', 'balm', 'shove', 'flux'," +
                        R.drawable.druidess + ", 1, 0.2, 0.3, 1, null, 'mixed', 'robin_f')," +
                    "('Barbar', 6, 'fighter', 'inferno', 'halberd', 'Dripmaster', 'Demoness', 'inflame', 'swig', 'zeal'," +
                        R.drawable.fembar + ", 0.8, 0, 1, 0.7, null, 'atk', 'byleth_f')," +
                    "('Ninja', 5, 'fighter', 'venin', 'sword', 'Ninj', 'C-No-B', 'infiltrate', 'assess', 'evade'," +
                        R.drawable.fem_ninj + ", 0.3, 0.9, 0.6, 0.7, null, 'spd', 'sheik')," +
                    "('Witch Hunter', 6, 'archer', 'null', 'bow', 'Bounty Hunter', 'Monster Hunter', 'reform', 'shove', 'cure'," +
                        R.drawable.witch_archer + ", 0.6, 1, 0.4, 0.5, null, 'def', 'terry')," +
                    "('Witch Doctor', 6, 'mage_med', 'silva', 'hex', 'Tribeswoman', 'Voodoo Master', 'torment', 'flux', 'anathema'," +
                        R.drawable.witch_doctor + ", 0.9, 0, 1.2, 0.4, null, 'mag', 'sheik')," +

                    "('Javelin-Man', 6, 'range2', 'aether', 'spear', 'Marathoner', 'Javelin-Man', 'defend', 'balm', null," +
                        R.drawable.javelin_man + ", 0.9, 0.5, 0, 1, null, 'def', 'falcon')," +
                    "('Penguin Knight', 6, 'fighter', 'glacier', 'sword', 'Hatchling', 'Penguin Knight', 'shove', 'infiltrate', 'reform'," +
                        R.drawable.pengu + ", 1.2, 0.6, 0.5, 0.2, null, 'def', 'robin')," +
                    "('Guerilla-Man', 5, 'fighter', 'silva', 'axe', 'Monkey-Brain', 'Guerilla-Lord', 'attack', 'cure', 'assess'," +
                        R.drawable.gorr + ", 0.6, 0.2, 0.7, 1, null, 'support', 'ganon')," +
                    "('Lupterra', 6, 'fighter', 'silva', 'spear', 'Furry', 'Lupterra', 'infiltrate', 'zeal', null," +
                        R.drawable.lupterra + ", 1.1, 0.2, 1.1, 0.2, null, 'atk', 'zss')," +
                    "('Heroine', 7, 'healer', 'inferno', 'broadsword', 'Vigilante', 'Heroine', 'defend', 'reorient', 'echo'," +
                        R.drawable.heroine + ", 0.7, 0.7, 0.5, 0.6, null, 'mix', 'lucina'), " +
                    "('Captain', 7, 'fighter', 'steel', 'spear', 'Squady', 'Captain', 'defend', 'reorient', 'reform'," +
                        R.drawable.brittany + ", 0.7, 1.2, 0.1, 0.5, null, 'def', 'snake')," +

                    "('Kremlin', 8, 'fighter', 'null', 'broadsword', 'Moscow Man', 'null', 'inflame', 'balm', 'zeal'," +
                        R.drawable.kremlin + ", 1, 1, 0.5, 0, null, 'atk', 'ike')," +
                    "('Kilde', 8, 'fighter', 'null', 'sword', 'Hideous', 'null', 'en garde', 'infiltrate', 'nab'," +
                        R.drawable.kilde + ", 0.6, 1, 0.5, 0.4, null, 'spd', 'robin')," +
                    "('Krista', 8, 'archer', 'silva', 'bow', 'Predator', 'null', 'assess', 'infiltrate', 'swig'," +
                        R.drawable.krista + ", 0.7, 1, 0.4, 0.4, null, 'atk', 'pyra')," +
                    "('Goat Guy', 8, 'fighter', 'force', 'spot', 'Cavalier', 'null', 'shove', 'zeal', 'reorient'," +
                        R.drawable.goat_guy + ", 0.3, 0.4, 0.8, 1, null, 'atk', 'falcon')," +
                    "('Jordan', 8, 'fighter', 'steel', 'polearm', 'Armorman', 'null', 'reform', 'defend', 'shove'," +
                        R.drawable.jordan + ", 1.1, 0.3, 0.1, 1, null, 'def', 'cloud')," +
                    "('Greybeard', 3, 'mage_med', 'gaia', 'magic', 'cAPtEn', 'null', 'big brain', 'inflame', 'anathema'," +
                        R.drawable.ghost_captain + ", 1.2, 0, 0.1, 1.2, null, 'def', 'ganon')," +
                    "('Master Zhen', 9, 'fighter', 'cyclone', 'punch', 'Cult Leader', 'null', 'evade', 'infiltrate', 'torment'," +
                        R.drawable.master_zhen + ", 0.5, 0.8, 0.6, 0.6, null, 'spd', 'cloud')," +
                    "('Lucida', 10, 'range2', 'aether', 'spear', 'Dimwit', 'null', 'convoy', 'infiltrate', 'dance'," +
                        R.drawable.lucida + ", 0.6, 0.9, 0.6, 0.4, null, 'def', 'corrin')," +
                    "('Eris', 9, 'mage_long', 'inferno', 'magic', 'Sablecoat', 'null', 'inflame', 'echo', 'limit_break'," +
                        R.drawable.eris + ", 1.1, 0.4, 0.5, 0.5, null, 'mag', 'robin_f')," +
                    "('Solstice', 10, 'mage_long', 'dusk', 'hex', 'Futakuchi-Onna', 'null', 'flux', 'torment', 'echo'," +
                        R.drawable.solstice + ", 1, 0, 0.8, 0.7, null, 'mag', 'sheik')," +
                    "('Morgana', 10, 'dagger', 'vamp', 'dagger', 'Lady', 'null', 'reform', 'butter up', 'flux'," +
                        R.drawable.morgana + ", 1.5, 0.3, 0.1, 0.6, null, 'atk', 'zss')," +
                    "('Helga', 10, 'fighter', 'gaia', 'axe', 'Feldlitter', 'null', 'inflame', 'swig', 'infiltrate'," +
                        R.drawable.helga + ", 1.1, 0, 0.4, 1, null, 'atk', 'byleth_f')," +
                    "('Roark', 17, 'fighter', 'dragonsvalk', 'beast', 'Dragonsvalk', 'null', 'inflame', 'zeal', 'shove'," +
                        R.drawable.roark + ", 1.2, 0.5, 0, 0.8, null, 'mix', 'dk')," +
                    "('Sumire', 12, 'fighter', 'venin', 'sword', 'Flurry Fighter+', 'null', 'en garde', 'infiltrate', 'assess'," +
                        R.drawable.summire + ", 0.5, 1, 1, 0, null, 'spd', 'mythra')," +
                    "('Crescentio', 12, 'fighter', 'ghenju', 'halberd', 'One-Winged Angel', 'null', 'flux', 'echo', 'adapt'," +
                        R.drawable.crescentio + ", 1, 1.2, 0.3, 0, null, 'atk', 'sephiroth')," +
                    "('Paragon', 12, 'fighter', 'aether', 'broadsword', 'Fairy Fighter', 'null', 'convoy', 'halcyon', 'reorient'," +
                        R.drawable.paragon + ", 0.6, 0.6, 0.6, 0.6, null, 'support', 'byleth')," +
                    "('Brozigard', 13, 'fighter', 'thunder', 'sword', 'Anti-Hero', 'null', 'evade', 'cure', 'harass'," +
                        R.drawable.brozigard + ", 0.7, 1.2, 0.1, 0.5, null, 'spd', 'robin')"
                );
            }
        }


        //player stats
        if(true) {
            kioku.execSQL("insert into bases (" + COLUMN_BASE_NAME + ", " + COLUMN_BASE_HP + ", " + COLUMN_BASE_ATK + ", " +
                    COLUMN_BASE_DEF + ", " + COLUMN_BASE_MAG + ", " + COLUMN_BASE_INT + ", " + COLUMN_BASE_SPD + ", " +
                    COLUMN_BASE_LUCK + ", " + COLUMN_BASE_HIT + ", " + COLUMN_BASE_CRIT + ", " + COLUMN_BASE_PHYS_AVO + ", " +
                    COLUMN_BASE_MAG_AVO + ", " + COLUMN_BASE_BLOCK + ", " + COLUMN_BASE_SP + ", " + COLUMN_BASE_SP_REGEN + ", "
                    + COLUMN_BASE_RESILIENCE + ")" +
                    " values " +
                    "('Jim', 8.5, 8, 7, 3, 5.5, 7, 7, 8.5, 3, 4.8, 3.5, 4.8, 7.6, 6.1, 6.3), " +
                    "('Reika', 5, 7, 5, 2, 6.5, 10, 8, 7.5, 5, 8.5, 7.2, 1, 4.9, 8.2, 3.8), " +
                    "('Dumwan', 14, 10.5, 6.5, 1, 3, 4.5, 6, 6.5, 5.1, 3.5, 2.5, 3.5, 8.2, 3.8, 9.1), " +
                    "('Amber', 4.5, 1.5, 3.5, 8.5, 7.5, 7.8, 8.5, 7, 2, 5.5, 7, 2.1, 5.4, 9.1, 1.5), " +
                    "('Luxana', 4.9, 2, 4.2, 6.5, 9.4, 6.1, 17, 7, 0.5, 4, 6.2, 3.3, 6.5, 7.8, 2.3), " +
                    "('Krillith', 6.5, 6.8, 8.8, 0.9, 2.6, 7.6, -1, 10, 6.9, 6.5, 3.1, 4.6, 6.1, 4.4, 6.7), " +
                    "('Xeno (P)', 6.8, 4, 7.9, 12, 6.1, 6.2, 4, 4, 5.5, 4.3, 3, 3, 6.4, 7.2, 3.3), " +
                    "('Xeno (W)', 6.8, 15, 11, 0.8, 0.2, 10, 2, 4, 6, 7.2, 0.5, 4.6, 8.8, 0.2, 2.4), " +
                    "('Hazel', 5.7, 7.1, 6.6, 7.4, 5.8, 8.3, 5.4, 7.4, 2.1, 5.1, 6.7, 5.8, 4.1, 2.6, 5.8), " +
                    "('Kurona', 4.3, 5.1, 4.5, 1.8, 3.4, 11, 4.1, 7.1, 8, 9, 8.4, 6.5, 8.6, 8.1, 4.2), " +
                    "('T''tania', 7.5, 11.5, 5.1, 1, 4, 8.9, 2, 7.9, 1.3, 7.1, 7.1, 4.2, 3.7, 4.2, 5.2), " +
                    "('Odedia', 8, 6, 8, 7.5, 8, 6.1, 7.5, 6.5, 1.5, 2.5, 3, 4.2, 8.1, 5.1, 7.1), " +
                    "('Kluane', 9.6, 12, 6, 1.2, 3.4, 5.9, 8.3, 5.7, 5.5, 5.7, 2.6, 5.1, 8.4, 7.4, 8.2), " +
                    "('Cord', 7.6, 6.8, 17.6, 1, 4.2, 4.2, 7.8, 7, 1.5, 1.9, 2.1, 5.1, 6.3, 3.3, 9.8), " +
                    "('Fiora', 6.7, 15.2, 5.7, 5, 7.2, 5.1, 8, 4.8, 1.6, 4, 4, 4.4, 7.8, 8.3, 2.9), " +

                    "('Desmond', 2.3, 3.8, 3.1, 6.2, 7.1, 5, 7.9, 8.1, 1.2, 5.4, 4.2, 2, 6.2, 4.6, 0), " +
                    "('Desmond (MB)', 7.2, 6.8, 3.1, 2.2, 9.1, 5, 5.6, 6.5, 2.2, 4.4, 1.2, 4.8, 6.2, 4.6, 0), " +
                    "('Forest Girl', 5.5, 6, 5.5, 8, 6.6, 6.8, 8.2, 7.1, 1.1, 4.5, 9.1, 3.6, 7.4, 8.6, 11.4), " +
                    "('Stein', 9, 7.8, 8.8, 1.5, 5.5, 6.5, 4.5, 6.8, 2.5, 3.8, 3.8, 5.5, 5.5, 4.5, 7.4), " +
                    "('Rudiène', 7.4, 8, 7.7, 6, 8.4, 6.1, 5.5, 6.7, 2.2, 4.4, 4.3, 4.3, 7.1, 6.1, 6.1), " +
                    "('Tobin', 6.7, 7.3, 6.1, 4.4, 5.8, 8.5, 5.5, 6.1, 5.2, 6.3, 5.4, 4.1, 6.2, 7.3, 5.7), " +
                    "('Saki', 6.4, 6, 4.7, 2.7, 5.2, 9, 4.2, 9.1, 5.8, 7.8, 5.9, 4.5, 4.6, 9.4, 3.9), " +
                    "('Gordon', 10.8, 14.6, 9.2, 2.1, 7.9, 3.8, 1.1, 5.3, 5.3, 4.5, 3.2, 4.1, 8.9, 5.8, 15), " +
                    "('Volfson', 7.8, 5.7, 6.1, 0.6, 1.8, 7.2, 6.8, 8.2, 3.5, 5.4, 6.1, 3.9, 8.3, 6, 6.2), " +
                    "('Sariel', 5.9, 6.8, 5.6, 7.1, 6.7, 6.3, 6.4, 8.1, 2.2, 5.4, 4.7, 3.4, 6.3, 5.1, 4.7), " +
                    "('Ek''rünne', 5.6, 6.2, 5.5, 4, 3.9, 10.5, 3.1, 9.6, 5.4, 6.4, 7.2, 2.5, 4.7, 7.6, 2.7), " +
                    "('Zai''us', 7.8, 8.5, 7.2, 9.5, 6.3, 5.9, 3.1, 8.1, 3.4, 2.9, 2.8, 5.1, 7.9, 2.3, 7.3), " +

                    "('Hex''ahlia', 3.5, 3.8, 9.1, 7.6, 14.1, 8.1, 1.1, 6.1, 9.9, 3, 1, 3.3, 5.6, 10, 1.8), " +
                    "('Dantè', 7, 7.1, 6, 2.6, 7.5, 8, 9, 9.5, 2.5, 7, 5.4, 4, 4.6, 6.2, 3.7), " +
                    "('Syphia', 7.8, 6.1, 10.3, 12, 9.5, 6.7, 3.2, 7.1, 0, 5, 4.7, 4.3, 8.5, 2.1, 5.7), " +
                    "('Far''quei', 10.7, 16.2, 8, 0.5, 4.2, 8.1, 2.6, 6, 6.1, 3, 3, 2.1, 9.2, 3.2, 6.1), " +
                    "('Kanna', 5.3, 3, 2.1, 11.6, 5.2, 9.2, 10.4, 7.1, 4.6, 6.5, 6.1, 3.8, 8.3, 8.4, 1.9), " +
                    "('Firth', 6.1, 13.6, 11.6, 9.7, 13.4, 7.8, 6.1, 7.2, 3.4, 5.3, 5.1, 3.5, 6.3, 4.3, 6.1), " +

                    "('Banjo', 6.4, 6.8, 9.4, 0.8, 1.2, 8.7, 4.6, 9.3, 7.2, 5.8, 6.3, 5, 5.7, 4.3, 8.8), " +
                    "('Leh''wahj', 8.2, 7.9, 8.2, 2, 7.9, 6.7, 6, 8.5, 1.7, 4.1, 3.8, 5.2, 6.6, 4.1, 6.9), " +
                    "('Soluxe', 5.7, 2.6, 5, 8.4, 7.2, 8.2, 7.1, 7.2, 5.2, 6.4, 5.6, 2.9, 4.8, 6.5, 2.1), " +
                    "('Aria', 12.6, 9.8, 5.2, 1.5, 5.9, 7.3, 6, 6.2, 2.6, 4.2, 3.4, 3.6, 7.6, 5.3, 7.9), " +
                    "('Rozelia', 5.1, 5.8, 3.9, 8.4, 5.8, 9.8, 7.1, 5.9, 1.5, 8.1, 8.7, 1.6, 4.5, 5.6, 2.6), " +

                    "('Briskette', 6, 4.7, 5.8, 6.6, 8.1, 9.4, 7.1, 6.4, 3.2, 5.6, 5.4, 1.5, 6.3, 7.2, 2.4), " +
                    "('Rosa', 5.5, 7, 6.3, 6.8, 7.4, 8.4, 8.7, 6.6, 0.8, 4.8, 5.2, 1.9, 8.4, 6.6, 3.8), " +
                    "('Juan', 5.8, 11.4, 4.1, 2.3, 6.1, 4.6, 5.1, 15, 11, 4.1, 4.7, 1, 5.4, 7.8, 3), " +
                    "('Penselle', 8.2, 9.4, 7.6, 1.6, 4.3, 6.1, 6.6, 6.1, 6, 5.6, 3.8, 3.9, 6, 4, 7.5), " +
                    "('Guatemala', 13.2, 10.4, 7.3, 0.8, 2.3, 4.4, 6.4, 7, 4.6, 4.1, 2.2, 6, 4.2, 4.2, 9.6), " +
                    "('Lukio', 7.6, 9.2, 5, 0.6, 3.6, 7.8, 8.1, 8.2, 5.2, 4.3, 3.6, 4.4, 6.1, 4.3, 4.5), " +
                    "('Lukia', 4.8, 5.2, 4.5, 8.3, 9.1, 9.4, 5.3, 7.2, 1.2, 7.8, 8.1, 0.6, 5.6, 8.1, 2.5), " +
                    "('Bridget', 7.8, 8.6, 5.7, 2.1, 6.2, 6.7, 6.7, 7.1, 3.8, 3.8, 3, 8.3, 5.6, 6.1, 5.8)," +

//                    "('Adelina', 5.4, 5.1, 5, 4.2, 7, 9.6, 4.3, 9, 6, 8, 7, 7.5, 4.6, 9.3, 5.7)," +
                    "('Lumen', 10.3, 3, 3.9, 9.3, 6.3, 6.6, 1.3, 6.9, 7.6, 3.6, 4.4, 2, 10, 7.1, 3.8)," +
                    "('Panda', 13, 11.6, 5.1, 2.3, 7.2, 6.2, 5.9, 7.1, 1.1, 4.1, 4.8, 3.6, 6.8, 6.2, 5.2)," +
                    "('Celestina', 8.9, 8.1, 8.6, 5.5, 7.9, 6, 6.4, 7.9, 1.9, 2.1, 3.1, 5.1, 7, 6, 7.1)," +
                    "('Vinicius', 5.9, 7.3, 5.1, 7.9, 6.2, 7.9, 8, 6.6, 2, 4.5, 5.3, 2.1, 7.6, 7.1, 3.6)," +
                    "('Vale', 7.5, 8.1, 8.2, 7.8, 7.3, 5.6, 1.6, 6.6, 4.6, 3.5, 4.2, 4.6, 7.1, 5.4, 6.8)," +
                    "('Sable', 7.1, 8.2, 7.4, 6.4, 6.8, 7.1, 3.9, 7.3, 4.4, 4.5, 4.2, 4.1, 5.5, 5.4, 5)," +
                    "('Felipe', 0.2, 15, 20, 10, 20, 1, 1.5, 7, 5.5, 0.5, 0.5, 10, 8.2, 8.5, 15)"
            );

        }

        // enemy stats
        if(true) {
            kioku.execSQL("insert into e_bases (" + E_COLUMN_NAME + ", " + E_COLUMN_HP + ", " + E_COLUMN_ATK + ", " +
                    E_COLUMN_DEF + ", " + E_COLUMN_MAG + ", " + E_COLUMN_INT + ", " + E_COLUMN_SPD + ", " +
                    E_COLUMN_LUCK + ", " + E_COLUMN_HIT + ", " + E_COLUMN_CRIT + ", " + E_COLUMN_PHYS_AVO + ", " +
                    E_COLUMN_MAG_AVO + ", " + E_COLUMN_BLOCK + ", " + E_COLUMN_SP + ", " + E_COLUMN_SP_REGEN + ", "
                    + E_COLUMN_RESILIENCE + ")" +
                    " values " +
                    "('Cactus', 10, 7.1, 4.8, 1, 2.1, 4.5, 5, 6.5, 3, 1.5, 2.5, 4, 4.4, 4.4, 4.5), " +
                    "('Le Roca', 3.6, 8, 9.8, 0, 2, 3.6, 7, 7.1, 2.2, 1, 1, 6, 1.1, 3, 9.6), " +
                    "('Dar Fischen', 5.5, 5.5, 5.5, 5.5, 5.5, 5.5, 5.5, 7.5, 2.5, 4, 4, 2.5, 4, 4, 4), " +
                    "('Suneki', 3.8, 7.1, 1.4, 1.8, 7.5, 7, 5, 6.8, 2.1, 5.2, 4.3, 0.7, 3.8, 6, 0.4), " +
                    "('Bload' , 26, 9.3, 2.1, 2, 2.4, 2.5, 3.9, 5.8, 1, 0.5, 0.5, 0.5, 6.2, 3.5, 5.2), " +
                    "('Berb (Chimken)', 4.1, 2.6, 2.9, 7.6, 6.4, 6.9, 5, 6.6, 1.5, 5.4, 4.5, 0, 4.4, 6.6, 0.7), " +
                    "('Berb (Duck)', 5.1, 4.1, 3, 7.3, 6.1, 6.1, 5.9, 6.1, 0.3, 4, 4, 0.5, 4.8, 6.1, 1), " +
                    "('Berb (Owl)', 4.5, 4, 4.1, 7.1, 8.6, 5.8, 4.7, 8, 1, 3.8, 3.6, 0.5, 5.2, 7, 0.5), " +
                    "('Berb (Goose)', 6, 6.7, 4.8, 1.2, 3.6, 7, 1.4, 6.7, 6, 4.6, 2, 1.5, 2, 5, 2.6), " +

                    "('Swordsman', 6.9, 7.6, 6.8, 1.5, 4.8, 8.2, 4, 7, 4, 5, 4.2, 3, 6, 4, 5), " +
                    "('Swiftblade', 5.2, 6.1, 4.4, 2.5, 5.7, 9.4, 6, 7.6, 4.7, 8, 8.2, 1, 4.9, 5.8, 2.9), " +
                    "('Henchman', 7.9, 7.2, 7.3, 2.4, 5.3, 6.1, 5.4, 7.8, 2.6, 3.2, 3.6, 3, 6.7, 2.4, 6.3), " +
                    "('Zealot', 9.5, 10.5, 4.1, 0.8, 2, 6.4, 2.4, 6, 4.1, 3.2, 3.4, 2.3, 3.7, 3, 6.1), " +
                    "('Sharpshooter', 6.2, 6.3, 5.4, 3.8, 7.1, 7.2, 8.2, 9.2, 4.2, 4.2, 5.2, 3, 2.1, 4.3, 4.9), " +
                    "('Huntress', 7.4, 8.2, 6.3, 4, 5.1, 6.3, 1.7, 6.7, 4.6, 4.1, 3.2, 3.2, 5.2, 7, 6.8), " +
                    "('Magia (F)', 4.9, 2.2, 4.7, 8.7, 7.6, 7.1, 7.2, 6.8, 2.1, 4.1, 4.8, 3, 6.4, 7.4, 2.4), " +
                    "('Magia (I)', 6.5, 4.3, 6, 6.8, 6.3, 5.8, 7.1, 6.6, 0.6, 2.7, 4.7, 3, 5.9, 5.1, 5.2), " +
                    "('Magia (W)', 3.6, 1.2, 2.4, 7.1, 6.1, 9.2, 8.1, 6.4, 5, 7.1, 7.4, 3, 4.8, 8.9, 0.7), " +
                    "('Hexmage', 6.1, 4.1, 7.4, 7.6, 7.7, 6.2, 1.4, 6.4, 4.3, 3.6, 4.4, 3, 7.6, 5.1, 5.3), " +

                    "('Cavalier', 7.8, 7.5, 8.4, 1.6, 3.4, 6.2, 6.8, 8.1, 2.7, 3.4, 3.8, 8.1, 5.1, 4.8, 11), " +
                    "('Rogue', 4.6, 6.3, 4.1, 4, 5.7, 9.4, 8, 7, 7.6, 7.2, 6.2, 1.3, 3.8, 9.5, 2.3), " +
                    "('Vampire', 8.2, 10, 5.3, 1, 3.4, 8.8, 2, 7.2, 2.3, 5.6, 4.6, 4.2, 3.6, 5.1, 8.1), " +
                    "('Sea Wolf', 9.1, 8.4, 3.4, 2.4, 6.3, 6.1, 8.6, 6.1, 4.2, 3.9, 3.1, 4.2, 7.1, 4.6, 5.2), " +
                    "('Duelist', 5.1, 6.8, 4.5, 3.5, 6.6, 9.3, 5.2, 8.4, 5.4, 6.1, 4.8, 5.1, 4.7, 7.6, 4.1), " +
                    "('Cleric', 4.6, 4.4, 4.5, 8.2, 8.7, 6.3, 9.4, 7.2, 1.4, 4.1, 4.5, 1.7, 6.7, 8.3, 1.9), " +
                    "('Raider', 8.4, 8.7, 5.1, 3, 6.2, 9, 3.1, 5.7, 6, 5.3, 5.2, 2.1, 5.3, 6, 5.3), " +

                    "('Monk', 6.4, 7.6, 6.1, 2, 6, 9.1, 5.2, 7, 4.6, 5.5, 5.5, 5.4, 5.5, 6.6, 5.7), " +
                    "('Hula Girl', 8.1, 7.6, 8.4, 6.1, 9.2, 4.6, 8.6, 8, 2.1, 3.4, 3.9, 1.2, 8.1, 4.6, 6), " +
                    "('Motorcycle Knight', 8.4, 9.1, 6.3, 1.4, 5.6, 8.1, 5.4, 6.4, 6.8, 4.3, 4.8, 6.1, 3.1, 6.2, 7.1), " +
                    "('Volaguard', 7.8, 7.2, 8.8, 3.1, 6.4, 5.7, 4.2, 7.5, 5.4, 3, 2.6, 4.6, 4.6, 5.3, 8.2), " +
                    "('Armor Knight', 8.6, 8.8, 9.8, 1.6, 3.9, 4, 4.9, 6.9, 2.3, 2.1, 2, 6.6, 6.6, 3.1, 10.2), " +
                    "('Dark Cleric', 6.1, 7, 6.7, 7.6, 8.4, 6, 5.1, 6.8, 2.1, 4.7, 5, 5, 7.2, 6.1, 4.2), " +
                    "('Fireman', 6.7, 6.7, 5.9, 7.8, 9.1, 6.1, 7.8, 8, 2, 5.1, 4.2, 2.6, 7, 5, 5), " +
                    "('Waterman', 6.7, 5.9, 6.7, 9.1, 7.8, 7.8, 6.1, 6, 4, 3.2, 4.1, 5.2, 5, 7, 5), " +

                    "('Chef', 11.2, 7.4, 3.6, 6.1, 6.7, 4.6, 11, 7.1, 5.1, 2.7, 4.1, 1.4, 9.4, 4.4, 6.2), " +
                    "('Wulfmeister', 6.1, 6.9, 5.8, 2.4, 6.4, 9, 2.4, 9, 7.4, 6.1, 6.8, 2.4, 5.7, 6.2, 6.6), " +
                    "('Dancer', 5.2, 4.2, 4.1, 6.4, 10.4, 5.8, 8.8, 6.4, 1.1, 6.1, 7, 0.1, 6.3, 7, 1.4), " +
                    "('Samurai', 6.2, 7.2, 8.8, 0.4, 2.6, 8.8, 2.3, 8.6, 8.2, 6.4, 4.1, 5.3, 3.1, 4.2, 8.8), " +
                    "('Cowboy', 9.6, 10.6, 6.1, 0.6, 1, 5.1, 7.2, 7.7, 5.3, 3.8, 3.1, 3.5, 6.1, 1, 7.7), " +
                    "('Witch', 6.4, 3.2, 5.4, 11.4, 9.2, 8.8, 1, 7.2, 3.2, 6.2, 6.8, 3.1, 8.7, 9.4, 4.8), " +
                    "('Sentinel (M)', 8.4, 8.1, 8.3, 1.4, 7.1, 5.5, 6, 8, 2.7, 3, 3, 6.5, 4.2, 3.9, 9.2), " +
                    "('Sentinel (F)', 8.2, 7.6, 7.1, 2.2, 8.3, 5.9, 4.1, 7.1, 4, 3.8, 3.8, 5.8, 5.1, 4.7, 8.4), " +
                    "('Darkling', 4.4, 5.6, 3.9, 8.6, 7.8, 9.1, 3, 6.7, 6.6, 7.1, 7.1, 0.4, 6, 9.8, 1.9), " +
                    "('Cannoneer', 8.4, 17, 8.2, 0.9, 3.2, 2.1, 3.1, 6.1, 3.1, 0.6, 0.6, 4.2, 2.2, 1.6, 15), " +

                    "('Apothecary', 7.1, 6.7, 8.2, 6.4, 3.1, 3.2, 4.5, 6.7, 0.5, 2.5, 2.1, 4.5, 6.4, 9.5, 9), " +
                    "('Bard', 3.6, 4.1, 3.5, 6.1, 8.6, 7.5, 12, 8.4, 2.5, 6.6, 6.8, 0, 7.6, 10, 1.4), " +
                    "('Boltstriker', 6.7, 7.4, 8.6, 1.3, 4.1, 7.1, 5.8, 7.8, 4.1, 4.1, 2.6, 5, 3.1, 4.3, 8), " +
                    "('Chainslinger', 8, 8.6, 7.4, 1.2, 3.8, 6.8, 3.4, 9, 3.5, 6.4, 2.6, 5, 6.2, 4, 10), " +
                    "('Cybernaut', 10, 2.1, 1.9, 10.6, 8.2, 6.3, 9.2, 6.1, 6.2, 1.9, 3.6, 7.4, 8.4, 9.1, 6), " +
                    "('Flamestalker', 5, 7.5, 3.5, 7.5, 5, 8.1, 7.5, 7.5, 8, 6, 5.2, 1, 4.6, 7.6, 2.7), " +
                    "('Gigamor', 10, 12, 19, 0, 4.2, 2.5, 3, 7.5, 4.1, 0.5, 0.5, 8.5, 3.5, 10, 15), " +
                    "('Gladiator', 10, 10, 10, 1.3, 2.1, 5.1, 5.5, 7.1, 4, 3.1, 2.6, 6, 3.8, 2.7, 12), " +
                    "('Grave Warden', 8.3, 7, 6.4, 9.2, 7, 6.1, 1.2, 6.8, 4.5, 3.1, 3.4, 5, 6.3, 8.1, 6.5), " +
                    "('Gunslinger', 5.4, 7.1, 5, 9.2, 6.2, 8.4, 5.4, 8.2, 3.1, 5.6, 6, 1.4, 5.8, 7.2, 1.8), " +
                    "('Hexblade', 7.4, 8.7, 8, 7.1, 7.3, 6.5, 2.1, 6.8, 2.3, 3.1, 2.6, 5.2, 7.4, 6.6, 6.1), " +
                    "('Knight (F)', 7.5, 8, 6.5, 3, 7.5, 7.5, 6, 9.2, 5.1, 4.2, 4.2, 3, 6, 5.5, 7.3), " +
                    "('Knight (M)', 7.5, 8.5, 7, 3, 6.5, 7.2, 6.5, 8.5, 3.5, 4, 4, 5, 7, 4.5, 8), " +
                    "('Mermaid', 6.4, 3.8, 4.5, 8.4, 11, 7.8, 8.6, 7.2, 2.1, 3.6, 5.4, 1, 8.8, 7.6, 3.2), " +
                    "('Scourger', 14, 9.6, 5.1, 1.4, 4.8, 8.3, 5.4, 8.1, 5.1, 2.4, 4.1, 1.2, 4.5, 4.1, 6), " +
                    "('Shovel Knight', 5.8, 7.4, 6.5, 8.2, 7.6, 7.5, 6, 6.8, 2.6, 3.6, 4.8, 4.1, 6.5, 7.1, 6.2), " +

                    "('Viking', 8.4, 8.3, 7.6, 2, 3.6, 6.3, 7.6, 6.4, 5.1, 3.8, 3.4, 6.1, 5.6, 5.5, 8.2), " +
                    "('Rifle Knight', 8.8, 9.7, 8.6, 0.9, 4.1, 5.8, 6.7, 8.1, 4.1, 2.1, 1.5, 3.4, 6, 3.5, 5.9), " +
                    "('Snake Rider', 8.9, 9.1, 9.8, 1, 4.6, 6.3, 2.6, 6.4, 5.5, 3.1, 1.9, 5, 7.1, 2.3, 10), " +
                    "('Druidess', 9, 8.8, 5.1, 8.1, 7.3, 7.1, 5, 6.1, 5.4, 3, 3.6, 3.4, 6.4, 7.6, 4.6), " +
                    "('Broadswordsman', 8, 9.5, 5.4, 1.2, 4.6, 6.6, 5.6, 6.2, 5.6, 3.4, 2.3, 3.1, 5.7, 5.7, 4.2), " +
                    "('Killer Priest', 7.7, 7.6, 7.5, 7.4, 7.3, 6.2, 2, 7.1, 7, 2.6, 2.8, 2.8, 6.7, 5.6, 5), " +
                    "('Barbar', 9.5, 9.9, 5.2, 0.6, 1.8, 8.6, 5.3, 6.7, 4.1, 4, 2.8, 1.2, 8.2, 4.2, 3.1), " +
                    "('Ninja', 3.9, 5, 4.2, 1.8, 4.9, 10.2, 3.6, 8.1, 8.2, 8.8, 8, 4.5, 7.1, 7, 1.9), " +
                    "('Witch Hunter', 7.9, 7.5, 7.6, 1.7, 6, 6.5, 5.4, 8, 3, 3.6, 3, 4.2, 4, 5.6, 6.1), " +
                    "('Witch Doctor', 6.2, 4.9, 5.7, 8.2, 11.4, 6.4, 0, 6.3, 6.7, 3.9, 5.8, 1.5, 7.3, 9.1, 5), " +

                    "('Javelin-Man', 8.4, 7.1, 5.9, 3.4, 7.3, 6, 8, 6.5, 2.4, 2.8, 3.1, 2, 6.1, 4.1, 7.1), " +
                    "('Penguin Knight', 8.2, 10.1, 9.7, 1.1, 4.2, 6.1, 4.3, 7.4, 3.1, 3.3, 3, 5.2, 4.3, 1.2, 10.1), " +
                    "('Guerilla-Man', 9.2, 8.2, 6.1, 6.1, 5.8, 4.1, 4, 5.2, 3.1, 1, 1, 4.1, 8.1, 5.2, 7.1), " +
                    "('Lupterra', 6, 14, 4.2, 0.2, 2.1, 9, 2.3, 6.1, 4.6, 7.5, 7.5, 0, 4.1, 1.1, 2.1), " +
                    "('Captain', 10.7, 9.5, 11.5, 1.2, 5.5, 5.5, 3, 7.2, 1.8, 3.7, 3.4, 3, 7, 2, 8), " +
                    "('Heroine', 7.8, 8.1, 7.8, 7.1, 6.5, 6.8, 4.8, 6.7, 1.4, 3.3, 4.5, 3.1, 4.6, 5.5, 5.6), " +

                    "('Eris', 6.8, 1.7, 5.8, 10.6, 8.8, 7.6, 5.8, 8, 4.4, 4.6, 6.1, 2.3, 8.2, 6.6, 3.9), " +
                    "('Helga', 12.1, 11.6, 6.8, 2, 5.4, 7.2, 4.5, 7.2, 5, 3.7, 3.2, 2, 6.5, 4.5, 5.5), " +
                    "('Paragon', 10, 8, 8, 8, 8, 7, 8, 8, 3, 3.5, 3.5, 5, 8, 8, 8), " +

                    "('Kremlin', 8.4, 8.5, 6.4, 2.1, 5.8, 7.2, 6, 7.7, 3.5, 3, 3.5, 6.4, 6.1, 4, 7.5), " +
                    "('Kilde', 7.9, 7.1, 6, 1.6, 6.4, 8, 7.3, 6.6, 5, 5.1, 5.4, 3.4, 5.8, 7, 5.1), " +
                    "('Krista', 7.2, 8.4, 6.2, 3.8, 4.1, 7.7, 6.8, 7.1, 5, 6.1, 5.2, 4, 5.7, 8.2, 5.7), " +
                    "('Goat Guy', 8.6, 8.1, 6.7, 4.6, 6.4, 8.4, 6.1, 7.4, 4, 2.5, 4.8, 3.3, 9.2, 6.4, 6.1), " +
                    "('Jordan', 14, 9.7, 8.6, 3.5, 4.1, 3.6, 6, 6, 4.4, 1, 1, 6.9, 7.1, 2, 12), " +
                    "('Master Zhen', 7.1, 7.2, 6.1, 7.2, 9.4, 9.3, 5.5, 7.7, 3.1, 5.8, 6.4, 1.6, 4.4, 6.5, 6), " +
                    "('Greybeard', 11, 16.2, 5.4, 1, 7.8, 4.1, 3.2, 5.9, 5.3, 1, 1, 1.1, 9.2, 0.8, 8.6), " +
                    "('Solstice', 7.6, 6.5, 5.4, 8.5, 8.8, 7.5, 1.2, 8.1, 5.1, 4, 5.9, 1.4, 8.4, 7.2, 3.5), " +
                    "('Lucida', 6.2, 7.4, 7.2, 2.1, 9.4, 9.1, 9.3, 7.9, 1.3, 6, 6, 2.1, 6, 9.5, 4.4), " +
                    "('Roark', 7.3, 14, 14, 7.1, 8.2, 6.3, 5.5, 7.1, 3.4, 4.4, 4.6, 5.5, 6.1, 1.1, 11), " +
                    "('Morgana', 8, 10.6, 6, 8.1, 2.9, 8.2, 1.1, 6.6, 1.4, 6.5, 6.5, 5.2, 6.2, 4.2, 5.8), " +
                    "('Sumire', 8.3, 9.2, 6.1, 1.2, 5.1, 9.1, 5.4, 7.7, 5.5, 7.9, 7.4, 4.1, 9.1, 2.9, 8.2), " +
                    "('Crescentio', 9.6, 11.4, 6.8, 3.2, 7.4, 7.5, 6, 8.5, 3.2, 6.2, 6.3, 3.3, 7.8, 4.1, 11.8), " +
                    "('Brozigard', 0.1, 26, 4.1, 32, 4.2, 15, 5.2, 5.9, 7.1, 15.1, 13.8, 1.4, 7.2, 6.1, 14)"
            );
        }


        //enaff table
        if(true) {
            kioku.execSQL(" INSERT INTO " + ENAFF_TABLE_NAME + " (name, aff1, p1, aff2, p2, aff3, p3, aff4, p4)" +
                    "VALUES " +

                    "('Cactus', 'silva', 100, null, null, null, null, null, null)," +
                    "('Le Roca', 'stone', 100, null, null, null, null, null, null)," +
                    "('Dar Fischen', 'torrent', 100, null, null, null, null, null, null)," +
                    "('Bload', 'glacier', 100, null, null, null, null, null, null)," +
                    "('Suneki', 'venin', 100, null, null, null, null, null, null)," +
                    "('Berb (Chimken)', 'cyclone', 100, null, null, null, null, null, null)," +
                    "('Berb (Duck)', 'torrent', 100, null, null, null, null, null, null)," +
                    "('Berb (Owl)', 'cyclone', 90, 'aether', 10, null, null, null, null)," +
                    "('Berb (Goose)', 'dusk', 100, null, null, null, null, null, null)," +

                    "('Swordsman', 'null', 80, 'cyclone', 15, 'steel', 5, null, null)," +
                    "('Swiftblade', 'cyclone', 60, 'null', 40, null, null, null, null)," +
                    "('Henchman', 'null', 80, 'steel', 20, null, null, null, null)," +
                    "('Zealot', 'null', 65, 'force', 15, 'steel', 10, 'inferno', 10)," +
                    "('Sharpshooter', 'null', 90, 'steel', 10, null, null, null, null)," +
                    "('Huntress', 'silva', 65, 'stone', 30, 'force', 5, null, null)," +
                    "('Magia (F)', 'inferno', 100, null, null, null, null, null, null)," +
                    "('Magia (W)', 'cyclone', 100, null, null, null, null, null, null)," +
                    "('Magia (I)', 'glacier', 100, null, null, null, null, null, null)," +
                    "('Hexmage', 'dusk', 55, 'ghenju', 40, 'inferno', 5, null, null)," +

                    "('Cavalier', 'stone', 70, 'steel', 30, null, null, null, null)," +
                    "('Rogue', 'dusk', 55, 'venin', 35, 'force', 10, null, null)," +
                    "('Vampire', 'vamp', 100, null, null, null, null, null, null)," +
                    "('Sea Wolf', 'torrent', 65, 'force', 25, 'null', 10, null, null)," +
                    "('Duelist', 'force', 100, null, null, null, null, null, null)," +
                    "('Cleric', 'aether', 90, 'null', 10, null, null, null, null)," +
                    "('Raider', 'dusk', 45, 'force', 40, 'ghenju', 15, null, null)," +

                    "('Monk', 'aether', 75, 'force', 25, null, null, null, null)," +
                    "('Hula Girl', 'silva', 35, 'torrent', 35, 'inferno', 30, null, null)," +
                    "('Volaguard', 'inferno', 45, 'cyclone', 35, 'steel', 20, null, null)," +
                    "('Armor Knight', 'steel', 80, 'stone', 20, null, null, null, null)," +
                    "('Motorcycle Knight', 'dusk', 40, 'force', 35, 'steel', 20, 'stone', 5)," +
                    "('Dark Cleric', 'ghenju', 65, 'dusk', 35, null, null, null, null)," +
                    "('Fireman', 'torrent', 100, null, null, null, null, null, null)," +
                    "('Waterman', 'inferno', 100, null, null, null, null, null, null)," +

                    "('Chef', 'inferno', 55, 'torrent', 30, 'null', 15, null, null)," +
                    "('Wulfmeister', 'silva', 60, 'dusk', 25, 'stone', 15, null, null)," +
                    "('Dancer', 'aether', 100, null, null, null, null, null, null)," +
                    "('Cowboy', 'null', 50, 'force', 30, 'inferno', 20, null, null)," +
                    "('Samurai', 'steel', 65, 'cyclone', 25, 'torrent', 10, null, null)," +
                    "('Darkling', 'aether', 50, 'dusk', 50, null, null, null, null)," +
                    "('Sentinel (M)', 'null', 55, 'steel', 45, null, null, null, null)," +
                    "('Sentinel (F)', 'null', 45, 'steel', 45, 'aether', 5, 'dusk', 5)," +
                    "('Cannoneer', 'steel', 75, 'stone', 25, null, null, null, null)," +
                    "('Witch', 'dusk', 30, 'glacier', 35, 'inferno', 35, null, null)," +

                    "('Apothecary', 'venin', 40, 'silva', 30, 'null', 30, null, null)," +
                    "('Bard', 'aether', 60, 'null', 40, null, null, null, null)," +
                    "('Boltstriker', 'steel', 55, 'null', 30, 'force', 15, null, null)," +
                    "('Chainslinger', 'steel', 55, 'dusk', 20, 'gaia', 15, 'aether', 10)," +
                    "('Cybernaut', 'dusk', 80, 'thunder', 20, null, null, null, null)," +
                    "('Flamestalker', 'inferno', 70, 'dusk', 25, 'silva', 5, null, null)," +
                    "('Gigamor', 'steel', 100, null, null, null, null, null, null)," +
                    "('Gladiator', 'steel', 35, 'null', 10, 'force', 55, null, null)," +
                    "('Grave Warden', 'ghenju', 100, null, null, null, null, null, null)," +
                    "('Gunslinger', 'null', 50, 'silva', 15, 'inferno', 20, 'torrent', 15)," +
                    "('Hexblade', 'ghenju', 75, 'dusk', 25, null, null, null, null)," +
                    "('Knight (M)', 'steel', 40, 'null', 10, 'dusk', 25, 'aether', 25)," +
                    "('Knight (F)', 'steel', 40, 'null', 10, 'dusk', 25, 'aether', 25)," +
                    "('Mermaid', 'torrent', 85, 'dusk', 15, null, null, null, null)," +
                    "('Scourger', 'force', 35, 'null', 30, 'dusk', 35, null, null)," +
                    "('Shovel Knight', 'gaia', 65, 'null', 15, 'aether', 20, null, null)," +

                    "('Snake Rider', 'venin', 85, 'dusk', 15, null, null, null, null)," +
                    "('Viking', 'torrent', 75, 'steel', 15, 'force', 10, null, null)," +
                    "('Rifle Knight', 'steel', 88, 'aether', 12, null, null, null, null)," +
                    "('Barbar', 'inferno', 90, 'null', 10, null, null, null, null)," +
                    "('Druidess', 'silva', 50, 'gaia', 25, 'torrent', 15, 'cyclone', 10)," +
                    "('Killer Priest', 'ghenju', 65, 'dusk', 25, 'steel', 10, null, null)," +
                    "('Ninja', 'venin', 65, 'dusk', 35, null, null, null, null)," +
                    "('Broadswordsman', 'null', 85, 'gaia', 15, null, null, null, null)," +
                    "('Witch Hunter', 'null', 65, 'aether', 25, 'steel', 10, null, null)," +
                    "('Witch Doctor', 'silva', 65, 'ghenju', 45, null, null, null, null)," +

                    "('Javelin-Man', 'aether', 60, 'null', 40, null, null, null, null)," +
                    "('Penguin Knight', 'glacier', 65, 'steel', 35, null, null, null, null)," +
                    "('Guerilla-Man', 'stone', 60, 'silva', 35, 'aether', 5, null, null)," +
                    "('Lupterra', 'silva', 50, 'null', 50, null, null, null, null)," +
                    "('Captain', 'steel', 100, null, null, null, null, null, null)," +
                    "('Heroine', 'aether', 45, 'inferno', 40, 'steel', 15, null, null)"

            );
        }


        // trash stat table eww never used
        if(true) {
            kioku.execSQL("INSERT INTO stat_table(name, level) " +
                    "VALUES " +
                    "('Jim', 1), " +
                    "('Reika', 1), " +
                    "('Dumwan', 1), " +
                    "('Amber', 1), " +
                    "('Luxana', 1), " +
                    "('Krillith', 1), " +
                    "('Xeno', 1), " +
                    "('Hazel', 1)"
            );
        }

        inductStatDatabase(kioku);
    }
    private void inductStatDatabase(SQLiteDatabase fleef) {
        try {
            for (int i = 0; i < numberOfRows(fleef, BASES_TABLE_NAME); i++) {

                Cursor c = fleef.rawQuery("select * from " + STAT_TABLE_NAME + " where " + STAT_COLUMN_ID + " = ?",
                        new String[]{String.valueOf(i + 1)});
                c.moveToFirst();

                int level = c.getInt(17);


                chars newChar = new chars();
                chars bases = this.getCharStatData(fleef, BASES_TABLE_NAME, i + 1);

                ArrayList<Float> rawBases = bases.getStats();

                // yes level dependancy
                newChar.setData(HP, (float) (rawBases.get(0) * (3.2 + (level - 1))));
                // yes level dependancy
                newChar.setData(ATK, (float) (rawBases.get(1) * (0.8 + 0.4 * Math.random() + (level - 1))));
                newChar.setData(DEF, (float) (rawBases.get(2) * (0.8 + 0.4 * Math.random() + (level - 1))));
                newChar.setData(MAG, (float) (rawBases.get(3) * (0.8 + 0.4 * Math.random() + (level - 1))));
                newChar.setData(INT, (float) (rawBases.get(4) * (0.8 + 0.4 * Math.random() + (level - 1))));
                newChar.setData(SPD, (float) (rawBases.get(5) * (0.8 + 0.4 * Math.random() + (level - 1))));
                newChar.setData(LCK, (float) (rawBases.get(6) * (0.8 + 0.4 * Math.random() + (level - 1))));
                //no level dependancy
                newChar.setData(HIT, rawBases.get(7) * 14);
                newChar.setData(CRIT, rawBases.get(8) * 5);
                newChar.setData(PHYSAVO, rawBases.get(9) * 10);
                newChar.setData(MAGAVO, rawBases.get(10) * 10);
                newChar.setData(BLOCK, rawBases.get(11) * 4);
                // yes level dependancy, though make an exponential relation between sp and sp_regen
                newChar.setData(SP, (float) (rawBases.get(12) * (10 + (level - 1) * 0.5)));
                newChar.setData(SPREGEN, (float) (rawBases.get(13) * (3.2) + (level - 1) * 0.15));
                newChar.setData(RES, (float) (rawBases.get(14) * 6.7));


//          for now all chars will be created at level 1

                fleef.rawQuery("update " + STAT_TABLE_NAME +
                        " set hp = ?, atk = ?, def = ?, mag = ?, int = ?, spd = ?, luck = ?, hit = ?, crit = ?, physavo = ?, " +
                        "magavo = ?, block = ? , sp = ?, sp_regen = ?, res = ? " +
                        "WHERE " + STAT_COLUMN_ID + " = ?", getStatsAsString(newChar, i + 1));
            }
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

    }

    // gets a char with already updated stats, inserts into db
    // noooo do it here todo
    public void levelUp(chars c) {
        SQLiteDatabase deeb = this.getWritableDatabase();
        deeb.rawQuery("update " + STAT_TABLE_NAME +
                " set hp = ?, atk = ?, def = ?, mag = ?, int = ?, spd = ?, luck = ?, hit = ?, crit = ?, physavo = ?, " +
                "magavo = ?, block = ? " +
                "WHERE " + STAT_COLUMN_NAME + " = ?", getStatsAsString(c, c.getName()));

        // ups level by 1
        deeb.rawQuery("update " + STAT_TABLE_NAME +
                " set level = ? " +
                "WHERE " + STAT_COLUMN_NAME + " = ?", new String[]{
                String.valueOf(getLevel(c.getName()) + 1), c.getName()});
    }

    public int getLevel(String charName) {
        SQLiteDatabase squeeb = this.getReadableDatabase();

        Cursor cur = squeeb.rawQuery("SELECT level FROM " + STAT_TABLE_NAME + " " +
                "WHERE name = ?", new String[]{charName});
        cur.moveToFirst();

        return cur.getInt(0);
    }

    private String[] getStatsAsString(chars c, String name) {
        String[] args = new String[statArray.length + 1];
        ArrayList<Float> arrayList = c.getStats();

        // discludes sp, spregen, and resilience
        for (int i = 0; i < statArray.length - 3; i++) {
            args[i] = arrayList.get(i).toString();
        }
        args[statArray.length] = name;

        return args;
    }
    private String[] getStatsAsString(chars c, int id) {
        String[] args = new String[statArray.length + 1];
        ArrayList<Float> arrayList = c.getStats();

        for (int i = 0; i < statArray.length; i++) {
            args[i] = arrayList.get(i).toString();
        }
        args[statArray.length] = String.valueOf(id);

        return args;
    }

    private int numberOfRows(SQLiteDatabase lelmon, String tableName) {
        return (int) DatabaseUtils.queryNumEntries(lelmon, tableName);
    }


    //TEMPORARY METHOD UNTIL I ACTUALLY GET THINGS GOING
    // generates stats in accordance to lvl * bases or something
    public chars getCharStatData(SQLiteDatabase db, String tableName, int id) {

        Cursor c = db.rawQuery("SELECT * FROM " + tableName + " WHERE id = ?", new String[]{String.valueOf(id)});

        c.moveToFirst();
        chars temp = new chars();

        temp.setName(c.getString(16));
        for (int i = 1; i < 15 + 1; i++) {
            temp.setData(statArray[i - 1], c.getFloat(i));
        }
        if (tableName.equals(STAT_TABLE_NAME)) {
            temp.setLevel(c.getColumnIndexOrThrow(STAT_COLUMN_LEVEL));
        }

        c.close();
        return temp;
    }
    public chars getCharStatData(String tableName, int id) {

        id++;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + tableName + " WHERE id = ?", new String[]{String.valueOf(id)});

        c.moveToFirst();
        chars temp = new chars();

        temp.setName(c.getString(16));

        for (int i = 1; i < 15 + 1; i++) {
            temp.setData(statArray[i - 1], c.getFloat(i));
        }
        if (tableName.equals(STAT_TABLE_NAME)) {
            temp.setLevel(c.getColumnIndexOrThrow(STAT_COLUMN_LEVEL));
        }

        c.close();
        return temp;
    }
    public chars getCharStatData(String tableName, String name) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + tableName + " WHERE name = ?", new String[]{String.valueOf(name)});

        c.moveToFirst();
        chars temp = new chars();

        temp.setName(c.getString(16));

        for (int i = 1; i < 15 + 1; i++) {
            temp.setData(statArray[i - 1], c.getFloat(i));
        }
        if (tableName.equals(STAT_TABLE_NAME)) {
            temp.setLevel(c.getColumnIndexOrThrow(STAT_COLUMN_LEVEL));
        }

        c.close();
        return temp;
    }

    public chars getCharStatBases(String charName) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + BASES_TABLE_NAME + " WHERE name = ?", new String[]{charName});

        c.moveToFirst();
        chars temp = new chars();
        if (c.isAfterLast()) {
            c = db.rawQuery("SELECT * FROM " + ENEMY_TABLE_NAME + " WHERE name = ?", new String[]{charName});
            c.moveToFirst();
        }

        temp.setName(c.getString(16));
        for (int i = 1; i < 15 + 1; i++) {
            temp.setData(statArray[i - 1], c.getFloat(i));
        }

        c.close();
        return temp;
    }

    public chars generatePlayer(String charName, int level) {

//        if(charName.equals("Desmond")) { charName = "Desmond (MB)"; }

        chars rawBasesChars = getCharStatBases(charName);
        return generateEnemyStats(rawBasesChars, level);
    }

    public chars generateEnemy(int levelAvg) {

        SQLiteDatabase deba = this.getReadableDatabase();

        chars enemyRawBasesChars = getCharStatData(ENEMY_TABLE_NAME,
                (int) (Math.random() * numberOfRows(deba, ENEMY_TABLE_NAME)));

        while(bossesNameList.contains(enemyRawBasesChars.getName())){
            enemyRawBasesChars = getCharStatData(ENEMY_TABLE_NAME,
                    (int) (Math.random() * numberOfRows(deba, ENEMY_TABLE_NAME)));
        }

//        chars enemyRawBasesChars = new chars(this.getCharStatData(ENEMY_TABLE_NAME,
//                "Cannoneer"));

        // there are a lot of mages, so have a chance to reroll if they are picked
        if ((enemyRawBasesChars.getName().contains("Magia") || enemyRawBasesChars.getName().contains("Berb")) &&
                (Math.random() < 0.5)) {
            enemyRawBasesChars = this.getCharStatData(ENEMY_TABLE_NAME,
                    (int) (Math.random() * numberOfRows(deba, ENEMY_TABLE_NAME)));
        }

        chars enemy = generateEnemyStats(enemyRawBasesChars, levelAvg);
        enemy.setAffinity(getEnemyAffinity(enemyRawBasesChars.getName()));
        enemy.setName(enemyRawBasesChars.getName());

        return enemy;
    }

    //gets level + stats in chars form, returns usable enemy stats
    private chars generateEnemyStats(chars bases, int level) {

        chars newChar = new chars();

        ArrayList<Float> rawBases = bases.getStats();

        newChar.setData(HP, (int) (rawBases.get(0) / 5 * level + rawBases.get(0) * (1.2 + 0.5 * Math.random())));

        for (int i = 1; i < statArray.length; i++) {
            int stat = (int) ((rawBases.get(i) / 5 * level) + rawBases.get(i) * (1.2 + 0.5 * Math.random()));
            newChar.setData(statArray[i], stat);
            if (i == 11) {
                i += 2;
            }
        }
        newChar.setData(SP, rawBases.get(12) * 10);
        newChar.setData(SPREGEN, rawBases.get(13));
        newChar.setData(BLOCK, rawBases.get(11) * 4);

        newChar.setLevel(level);
        newChar.setName(bases.getName());

        return newChar;
    }


    public HashMap<Integer, String> getAllPlayerCharNames() {

        SQLiteDatabase db = this.getReadableDatabase();

        HashMap<Integer, String> temperament = new HashMap<>();

        Cursor c = db.rawQuery("SELECT " + COLUMN_BASE_NAME + " FROM " + BASES_TABLE_NAME, null);
        c.moveToFirst();

        for (int i = 0; i < numberOfRows(db, BASES_TABLE_NAME); i++) {
            temperament.put(i, c.getString(0));
            c.moveToNext();
        }
        c.close();

//        temperament.entrySet().removeIf(entry -> ("Desmond (MB)".equals(entry.getValue())));

        // temp HAS all the results in it
        return temperament;
    }
    public HashMap<Integer, String> getAllEnemyNames() {

        SQLiteDatabase db = this.getReadableDatabase();

        HashMap<Integer, String> temperament = new HashMap<>();

        Cursor c = db.rawQuery("SELECT " + E_COLUMN_NAME + " FROM " + ENEMY_TABLE_NAME, null);
        c.moveToFirst();

        for (int i = 0; i < numberOfRows(db, ENEMY_TABLE_NAME); i++) {
            temperament.put(i, c.getString(0));
            c.moveToNext();
        }
        c.close();

        // temp HAS all the results in it
        return temperament;
    }


    public affinity getEnemyAffinity(String name) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + ENAFF_TABLE_NAME + " WHERE name = ?", new String[]{name});

        c.moveToFirst();

        affinity aff;

        int sum = 0;
        int rand = (int) (Math.random() * 100);

        sum += c.getInt(2);
        if (sum >= rand) {
            // aff1
            aff = affinity.getAffinity(c.getString(1));
        } else {

            sum += c.getInt(4);
            if (sum >= rand) {
                // aff2
                aff = affinity.getAffinity(c.getString(3));
            } else {

                sum += c.getInt(6);
                if (sum >= rand) {
                    // aff3
                    aff = affinity.getAffinity(c.getString(5));
                } else {
                    // aff4
                    aff = affinity.getAffinity(c.getString(7));
                }
            }
        }

        c.close();
        return aff;
    }


    // class table

    public String getPlayerClassification(String charName) {
        SQLiteDatabase db = getReadableDatabase();

        String res = "fighter";

        Cursor c = db.rawQuery("select * from " + CLASS_TABLE_NAME + " where " + COLUMN_CLASS_CHAR_NAME +
                " = ?", new String[]{charName});

        c.moveToFirst();

        //classification, init. class, adv. class

        if(!c.isAfterLast()) {
            res = c.getString(2);
        }


        c.close();

        return res;
    }

    public String[] getPlayerClassData(String charName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] classNames = new String[3];

        Cursor c = db.rawQuery("select * from " + CLASS_TABLE_NAME + " where " + COLUMN_CLASS_CHAR_NAME +
                " = ?", new String[]{charName});

        c.moveToFirst();

        //classification, init. class, adv. class

//        classNames[0] = c.getString(2);
        classNames[0] = c.getString(5);
        classNames[1] = c.getString(6);

//        switch (className) {
//            case CLASSIFICATION:
//                res = classNames[0];
//                break;
//            case ADV_CLASS:
//                res = classNames[2];
//                break;
//            case INIT_CLASS:
//            default:
//                res = classNames[1];
//        }


        c.close();

        return classNames;
    }

    public affinity getCharAffinity(String charName) {

        SQLiteDatabase db = this.getReadableDatabase();
        String charAffinity;

        Cursor c = db.rawQuery("select * from " + CLASS_TABLE_NAME + " where " + COLUMN_CLASS_CHAR_NAME +
                " = ?", new String[]{charName});

        c.moveToFirst();

        charAffinity = c.getString(3);
        c.close();

        return affinity.getAffinity(charAffinity);
    }

    public weaponType getCharWeaponType(String charName) {

        SQLiteDatabase db = getReadableDatabase();
        String weaponType = "";

        Cursor c = db.rawQuery("select * from " + CLASS_TABLE_NAME + " where " + COLUMN_CLASS_CHAR_NAME +
                " = ?", new String[]{charName});

        c.moveToFirst();

        if (c.getCount() > 0) {
            weaponType = c.getString(4);
        }

        c.close();

        if(Objects.equals(weaponType, "")){
            weaponType = "magic";
        }

        return declatoration.simulatorial.enums.weaponType.getWeaponType(weaponType, false);
    }

    public String[] getCharSpecialAttackNames(String charName){

        SQLiteDatabase db = this.getReadableDatabase();
        String[] attackNames = new String[3];

        Cursor c = db.rawQuery("select * from " + CLASS_TABLE_NAME + " where " + COLUMN_CLASS_CHAR_NAME +
                " = ?", new String[]{charName});

        c.moveToFirst();

        attackNames[0] = c.getString(8);
        attackNames[1] = c.getString(9);
        attackNames[2] = c.getString(10);

        c.close();

        return attackNames;
    }

    public Integer getNumBullets(String charName){
        SQLiteDatabase db = this.getReadableDatabase();
        Integer numBullets = null;

        Cursor c = db.rawQuery("select * from " + CLASS_TABLE_NAME + " where " + COLUMN_CLASS_CHAR_NAME +
                " = ?", new String[]{charName});

        c.moveToFirst();

        if (c.getCount() > 0) {
            numBullets = c.getInt(15);
            if(numBullets == 0){
                numBullets = null;
            }
        }

        c.close();

        return numBullets;
    }

    public chars.characterType getCharType(String charName){
        SQLiteDatabase db = this.getReadableDatabase();
        chars.characterType type = null;

        Cursor c = db.rawQuery("select * from " + CLASS_TABLE_NAME + " where " + COLUMN_CLASS_CHAR_NAME +
                " = ?", new String[]{charName});

        c.moveToFirst();

        if (c.getCount() > 0) {
            type = chars.characterType.getCharType(c.getString(17));
        }

        c.close();

        return type;
    }
    public sfxCharBase getSFXBaseChar(String charName){
        SQLiteDatabase db = this.getReadableDatabase();
        sfxCharBase name = null;

        Cursor c = db.rawQuery("select * from " + CLASS_TABLE_NAME + " where " + COLUMN_CLASS_CHAR_NAME +
                " = ?", new String[]{charName});

        c.moveToFirst();

        if (c.getCount() > 0) {
            name = sfxCharBase.getName(c.getString(18));
        }

        c.close();

        return name;
    }

    public ArrayList<Integer> getRange(String charName){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("select * from " + CLASS_TABLE_NAME + " where name = ?", new String[]{charName});
        c.moveToFirst();

        String classification;

        classification = c.getString(2);


        ArrayList <Integer> range;
        switch (classification) {
            case "archer":
                range = new ArrayList<>(Arrays.asList(2, 3));
                break;
            case "range2":
                range = new ArrayList<>(List.of(2));
                break;
            case "dagger":
            case "mage_med":
                range = new ArrayList<>(Arrays.asList(1, 2));
                break;
            case "gunman":
            case "mage_long":
            case "cannonneer":
            case "cannon":
                range = new ArrayList<>(Arrays.asList(1, 2, 3));
                break;
            case "fighter":
            case "beast":
            case "healer":
            case "dancer":
            default:
                range = new ArrayList<>(List.of(1));
                break;
        }
        c.close();


        return range;
    }

    public int getCharImageResId(String charName) {

        SQLiteDatabase db = this.getReadableDatabase();
        int id;

        Cursor c = db.rawQuery("select * from " + CLASS_TABLE_NAME + " where " + COLUMN_CLASS_CHAR_NAME +
                " = ?", new String[]{charName});

        c.moveToFirst();

        id = c.getInt(7);
        c.close();

        return id;
    }

    public actionBoostParams getCharAssistParams(String charName) {

        SQLiteDatabase db = this.getReadableDatabase();
        actionBoostParams charActionBoostParams = new actionBoostParams();

        Cursor c = db.rawQuery("select * from " + CLASS_TABLE_NAME + " where " + COLUMN_CLASS_CHAR_NAME +
                " = ?", new String[]{charName});

        c.moveToFirst();
        charActionBoostParams.setParams(c.getFloat(11), c.getFloat(12), c.getFloat(13), c.getFloat(14));
        c.close();

        return charActionBoostParams;
    }



    public ArrayList<chars> getSkirmish(Context context, int strengthVal,
                                        String charToUnlock, ArrayList<String> unlockedChars){

        SQLiteDatabase deeb = this.getReadableDatabase();

        int enemyNum = getSkirmishEnemyNum(strengthVal);
        ArrayList<chars> charsArrayList = new ArrayList<>();
        Cursor cursor;

        int iterator = 0;
        final int enemyNumFinal = enemyNum;

        if(charToUnlock != null){

            charsArrayList.add(new chars(context, charToUnlock, 28));

            cursor = deeb.rawQuery("SELECT * FROM classTable WHERE name = ?",
                    new String[]{charToUnlock});

            cursor.moveToFirst();

            int powerLvl = cursor.getInt(16);
            strengthVal -= powerLvl;

            enemyNum--;
            iterator++;

        }


        for (; iterator < enemyNumFinal && strengthVal > 0; iterator++) {

            int avgPowerLvl = strengthVal / enemyNum;
            if(avgPowerLvl == 0){
                avgPowerLvl = 1;
            }

            cursor = deeb.rawQuery("SELECT * FROM classTable WHERE strLevel = ?",
                    new String[]{String.valueOf(avgPowerLvl)});

            int selectionSize = cursor.getCount();

            int i = 0;

            String name;
            do {
                cursor.moveToPosition((int) (selectionSize * Math.random()));
                name = cursor.getString(1);

                i++;

                if(i > 9){
                    if(avgPowerLvl == 6 && (Math.random() * 100) < 50){
                        avgPowerLvl--;
                    } else{
                        avgPowerLvl++;
                    }

                    i = 0;
                }
            } while(unlockedChars.contains(name) || Objects.equals(name, charToUnlock));


            if(iterator == 0){
                // boss is stronger
                charsArrayList.add(new chars(context, name, 28));
            } else {
                charsArrayList.add(new chars(context, name, 25));
            }

            enemyNum--;
            strengthVal -= avgPowerLvl;

        }


        return charsArrayList;

    }
    public ArrayList<chars> getBossSkirmish(Context context, bossDatabase.generationSet genSet,
                                            int strLevel){

        ArrayList<chars> charsArrayList = new ArrayList<>();
        int strPointsRemaining = strLevel;


        String bossName = genSet.bossName;
        charsArrayList.add(new chars(context, bossName, 28));
        strPointsRemaining -= getPowerLevels(new ArrayList<>(List.of(bossName))).get(0);

        String bossName2 = genSet.secondaryBossName;
        if(bossName2 != null){
            charsArrayList.add(new chars(context, bossName2, 26));
            strPointsRemaining -= getPowerLevels(new ArrayList<>(List.of(bossName2))).get(0);
        }


        ArrayList<String> henchmenNames = genSet.getAdditionals(strPointsRemaining);
        for (int i = 0; i < henchmenNames.size(); i++) {
            // henchmen are much weaker
            charsArrayList.add(new chars(context, henchmenNames.get(i), 24));
        }

        return charsArrayList;
    }

    private int getSkirmishEnemyNum(int strengthVal){

        int rand;
        int enemyNum;

        int div = strengthVal / 5;

        // get number of enemies according to strengthVal
        switch (div){
            case 0:
                // strength level 0 - 4
                if(strengthVal > 2) {
                    rand = (int) (Math.random() * 100);
                    if (rand < 25) {
                        enemyNum = 3;
                    } else {
                        if (rand < 90) {
                            enemyNum = 2;
                        } else {
                            enemyNum = 1;
                        }
                    }
                } else {
                    enemyNum = 1;
                }
                break;

            case 1:
                // strength level 5 - 9
                rand = (int) (Math.random() * 100);
                if(rand < 50){
                    // 50%
                    enemyNum = 3;
                } else{
                    if(rand < 70) {
                        // 20%
                        enemyNum = 2;
                    } else{
                        // 30%
                        enemyNum = 4;
                    }
                }
                break;

            case 2:
                // strength level 10 - 14
                rand = (int) (Math.random() * 100);
                if(rand < 55){
                    // 50%
                    enemyNum = 3;
                } else{
                    // 20%
                    enemyNum = 4;
                }
                break;

            case 3:
                // strength level 15 - 19
                rand = (int) (Math.random() * 100);
                if(rand < 50){
                    // 50%
                    enemyNum = 4;
                } else{
                    // 50%
                    enemyNum = 5;
                }
                break;

            case 4:
                // strength level 20 - 24
                rand = (int) (Math.random() * 100);
                if(rand < 45){
                    enemyNum = 4;
                } else{
                    enemyNum = 5;
                }
                break;

            case 5:
                // strength level 25 - 29
                rand = (int) (Math.random() * 100);
                if(rand < 50) {
                    enemyNum = 5;
                } else {
                    enemyNum = 6;
                }
                break;
            case 6:
                // strength level 30 - 34
                rand = (int) (Math.random() * 100);
                if(rand < 30) {
                    enemyNum = 5;
                } else if( rand < 85){
                    enemyNum = 6;
                } else{
                    enemyNum = 7;
                }
                break;

            case 7:
                // strength level 35 - 39
                rand = (int) (Math.random() * 100);
                if(rand < 55){
                    enemyNum = 6;
                } else{
                    enemyNum = 7;
                }
                break;

            default:
                enemyNum = 4;

        }

        return enemyNum;
    }

    public ArrayList<Integer> getPowerLevels(ArrayList<String> names){
        SQLiteDatabase deeb = getReadableDatabase();

        ArrayList<Integer> powerLevels = new ArrayList<>();

        Cursor cursor;
        for (int i = 0; i < names.size(); i++) {

            cursor = deeb.rawQuery("SELECT * FROM classTable WHERE name = ?",
                    new String[]{names.get(i)});
            cursor.moveToFirst();

            powerLevels.add(cursor.getInt(16));
        }

        return powerLevels;
    }

    enum classNameEnum {
        CLASSIFICATION,
        INIT_CLASS,
        ADV_CLASS
    }

}
