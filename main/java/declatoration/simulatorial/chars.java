package declatoration.simulatorial;

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
import static declatoration.simulatorial.fight.actionHandler.percentagize;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import declatoration.simulatorial.enums.affinity;
import declatoration.simulatorial.enums.stat;
import declatoration.simulatorial.enums.weaponType;
import declatoration.simulatorial.fight.actionBoostParams;
import declatoration.simulatorial.fight.sounds.sfxCharBase;


public class chars {
//
//    public static numDatabase statDatabase;
//    public static actionDatabase actionsDatabase;

    static final ArrayList<charAction> specialAttacks = new ArrayList<>();
    static final ArrayList<String> specialAttackNames = new ArrayList<>();
    static final ArrayList<charAction> affSpecialActions = new ArrayList<>();

    private sfxCharBase SFXCharBase;

    private String name;
    private affinity affinity;
    private weaponType weapon;
    private characterType charType;
    private String className;
    private String auxClassName;
    private int level;
    private float HPMax, SPMax;
    public String[] specialActionNamesArray;

    private final statBoard charStatsMap;
    private ArrayList<charSkill> charSkills = new ArrayList<>(6);
    private ArrayList<charAction> charActions = null;
//    private ArrayList<String> genericActionNames = null;

    public ArrayList<String> selectedActionNames;
    private ArrayList<Integer> range;
    private actionBoostParams actionBoostParams;

    static databaseExec dbExec;


    //todo - add char's immunity list? adapt skills to this as well
    // (skill types - immunities, dmg, hit/crit, so on...)
    // then on immunities, make an arraylist for immune statuses, resistant affs...
    public chars() {
        charStatsMap = new statBoard();
    }

    public chars(Context c, String charName) {
        // bases creation, for bases encyclofrag
        databaseExec dbExec = databaseExec.getInstance(c);
        charStatsMap = new statBoard();

        dbExec.makeChar(this, charName, null, databaseExec.creationMethod.BASES);
    }

    public chars(Context c, String charName, int level) {
        // actionDB class table initialization (warRoom)
        // full on char creation
        charStatsMap = new statBoard();

        dbExec = databaseExec.getInstance(c);

        dbExec.makeChar(this, charName, level, databaseExec.creationMethod.FULL);
    }

    // may be entirely unneeded
//    public chars(chars base) {
//        this.charStatsMap = base.charStatsMap;
//        this.charActions = base.charActions;
//        this.charSkills = base.charSkills;
//        this.genericActionNames = base.genericActionNames;
//        this.specialActionNamesArray = base.specialActionNamesArray;
//
//        this.name = base.getName();
//        this.affinity = base.getAffinity();
//        this.weapon = base.getWeapon();
//        this.className = base.getClassName();
//        this.auxClassName = base.getAuxClassName();
//        this.level = base.getLevel();
//        this.range = statDatabase.getRange(name);
//        this.selectedActionNames = base.selectedActionNames;
//        this.actionBoostParams = base.actionBoostParams;
//        this.charType = base.charType;
//        this.HPMax = base.HPMax;
//        this.SPMax = base.SPMax;
//    }

    //player char init
    public chars(Context context, String charName, ArrayList<String> actionIds, int charLevel) {

        charStatsMap = new statBoard();

        this.selectedActionNames = actionIds;

        dbExec = databaseExec.getInstance(context);
        dbExec.makeChar(this, charName, charLevel, databaseExec.creationMethod.FULL);
    }


    //enemy init
    public chars(Context context, int levelAvg) {

        charStatsMap = new statBoard();

        dbExec = databaseExec.getInstance(context);
        dbExec.makeChar(this, null, levelAvg, databaseExec.creationMethod.ENEMY);
    }

    public Thread exactFetchActions(){
        fetchActions fetch = new fetchActions();
        Thread thread = new Thread(fetch);
        thread.start();

        return thread;
    }
    public class fetchActions implements Runnable{

        @Override
        public void run() {
            if(selectedActionNames != null) {
                copyActions(dbExec.getCharActions(name, level, selectedActionNames));
            } else {
                //enemy assignment, they only get their moves set here
                generateEnemyActions();
            }

        }
    }
    public void releaseActions(){
        charActions.clear();
    }

    public boolean getActionsFilled(){
        return !(selectedActionNames == null || selectedActionNames.contains(null));
    }


    public void setName(String s) {
        this.name = s;
    }
    public void setAffinity(affinity aff) {
        this.affinity = aff;
    }
    public void setWeapon(weaponType weapon) { this.weapon = weapon; }
    public void setLevel(int level) {
        this.level = level;
    }
    public void setRange(ArrayList<Integer> range) { this.range = range; }
    public void setData(stat column, float value) {
        charStatsMap.put(column, value);
        if(column == HP){
            if(value <= 0){
                value = 1;
            }
            setHPMax(value);
        } else if (column == SP){
            setSPMax(value);
        }
    }
    public void setSelectedActions(ArrayList<String> actionNames){
        selectedActionNames = actionNames;
    }

    public void setSFXCharBase(sfxCharBase charname){ this.SFXCharBase = charname; }

    public void setClassData(String[] classData){
        className = classData[0];
        auxClassName = classData[1];
    }
    public void setActionBoostParams(declatoration.simulatorial.fight.actionBoostParams actionBoostParams) {this.actionBoostParams = actionBoostParams; }
    public void setSpecialActionNamesArray(String[] specialActionNamesArray) { this.specialActionNamesArray = specialActionNamesArray; }

    public void setCharType(characterType charType) { this.charType = charType; }
    public void setHPMax(float hpMax){ this.HPMax = hpMax;}
    public void setSPMax(float spMax){ this.SPMax = spMax;}

    public void copyChar(chars original) {
        setName(original.getName());
        setLevel(original.getLevel());
        setAffinity(original.getAffinity());
        setStats(original);
        setCharType(original.getCharType());
        setSFXCharBase(original.SFXCharBase);
    }

    public void copyActions(ArrayList<charAction> charActions){
        this.charActions = charActions;
    }
    public void generateEnemyActions() {

        // enemy assignment
        // get random actions out of selected list
        selectedActionNames = new ArrayList<>();

        ArrayList<charAction> tempActionArray = dbExec.getAllCharUniqueActions(name, level);

        this.charActions = new ArrayList<>();
        this.charActions.add(tempActionArray.get(0));

        tempActionArray.remove(0);


        //add charSpecificActions
        for (int i = 0; i < 4 && i < tempActionArray.size(); i++) {

            int length = tempActionArray.size();

            int temp = (int) (Math.random() * length);

            int y = 0;
            while(selectedActionNames.contains(tempActionArray.get(temp).getName())){
                temp = (int) (Math.random() * length);
                y++;

                if (y > 15){
                    break;
                }
            }

            if(y > 15){
                selectedActionNames.add(null);
            } else {
                selectedActionNames.add(tempActionArray.get(temp).getName());
                tempActionArray.remove(temp);
            }

        }


        tempActionArray.addAll(dbExec.getCharGenerics(name));


        // randomize action ids
        for (int i = 0; i <= 5 - this.charActions.size() && i < tempActionArray.size(); i++) {

            int length = tempActionArray.size();

            int temp = (int) (Math.random() * length);

            int y = 0;
            while(selectedActionNames.contains(tempActionArray.get(temp).getName())){
                temp = (int) (Math.random() * length);
                y++;

                if (y > 5){
                    selectedActionNames.add(null);
                    break;
                }
            }

            if (!(y > 5)) {
                selectedActionNames.add(tempActionArray.get(temp).getName());
                tempActionArray.remove(temp);
            }
        }

//        for (int i = 0; i < selectedActionNames.size() - 1; i++) {
//            if(!Objects.equals(selectedActionNames.get(i), null) && i < tempActionArray.size()) {
//                for (int j = 0; j < charActions.size(); j++) {
//                    if (Objects.equals(charActions.get(j).getName(), selectedActionNames.get(i))){
//                        this.charActions.add(charActions.get(j));
//                        break;
//                    }
//                }
//            }
//        }
    }
//    public void copyActions(ArrayList<charAction> charActions, ArrayList<String> actionNames) {
//
//        //char assignment
//        this.charActions = new ArrayList<>();
//        this.charActions.add(charActions.get(0));
//
//        if(genericActionNames == null) {
//            for (int i = 1; i < charActions.size(); i++) {
//                if (actionNames.contains(charActions.get(i).getName())) {
//                    this.charActions.add(charActions.get(i));
//                }
//            }
//        } else {
//            this.charActions.addAll(actionsDatabase.getGenericActions(selectedActionNames));
//        }
//    }
    // for immutable actions selected in warDB.
    // dont think i want to go through with that.

//    public void setActions(ArrayList<String> actionNames) {
//
//        ArrayList<charAction> charActions = dbExec.getCharActions(name, 25);
//        //char assignment
//        this.charActions = new ArrayList<>();
//        this.charActions.add(charActions.get(0));
//
////        if(genericActionNames == null) {
////            for (int i = 1; i < charActions.size(); i++) {
////                if (actionNames.contains(charActions.get(i).getName())) {
////                    this.charActions.add(charActions.get(i));
////                }
////            }
////        } else {
//            this.charActions.addAll(actionsDatabase.getGenericActions(actionNames));
////        }
//    }

    public void copySkills(ArrayList<charSkill> charSkills) {
        this.charSkills = charSkills;
    }

    public String getName() {
        return this.name;
    }
    public affinity getAffinity() {
        return affinity;
    }
    public weaponType getWeapon() {
        return weapon;
    }

    public sfxCharBase getSFXCharBase(){
        return this.SFXCharBase;
    }

    public characterType getCharType(){ return charType;}
    public String getClassName() {
        return className;
    }
    public String getAuxClassName() {
        return auxClassName;
    }
    public int getLevel() {
        return this.level;
    }
    public float getHPMax() { return HPMax; }
    public float getSPMax() { return SPMax; }

    public statBoard getStatBoard() {
        return charStatsMap;
    }

    public float getStatFloat(stat key) {
        return charStatsMap.get(key);
    }

    public int getCharImageResId() {
        return dbExec.getCharImageResId(this.name);
    }

    public actionBoostParams getAssistParams() {
        return actionBoostParams;
    }

    public void setStats(chars original) {
        setHPMax(original.getHPMax());
        setSPMax(original.getSPMax());

        statBoard ai = original.getStatBoard();

        for (int i = 0; i < statArray.length; i++) {
            if (!Objects.equals(statArray
                    [i], HIT)) {
                charStatsMap.put(statArray
                        [i], ai.get(statArray
                        [i]));
            } else {
                float hitStat = (float) (ai.get(HIT) * 2.1);
                charStatsMap.put(statArray
                        [i], hitStat);
            }
        }


    }

    public ArrayList<Float> getStats() {
        ArrayList<Float> stats = new ArrayList<>();

        for (int i = 0; i < charStatsMap.size(); i++) {
            stats.add(charStatsMap.get(statArray
                    [i]));
        }

        return stats;
    }


    public ArrayList<charSkill> getCharSkills() {
        return charSkills;
    }

    public ArrayList<charAction> getCharSelectedActions() {
        if(this.charActions == null){
            try {
                exactFetchActions().join();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return this.charActions;
    }
    public charAction getCharAction(int id) {
        if(id >= 0) {
            return this.charActions.get(id);
        } else{
            switch (id){
                case -6:
                    //assist. is the last index always
                    return specialAttacks.get(specialAttacks.size() - 1);
                case -7:
                    int index = specialAttackNames.indexOf(specialActionNamesArray[0]);
                    if(index != -1) {
                        return specialAttacks.get(index);
                    } else return null;
                case -8:
                    int index2 = specialAttackNames.indexOf(specialActionNamesArray[1]);
                    if(index2 != -1) {
                        return specialAttacks.get(index2);
                    } else return null;
                case -9:
                    int index3 = specialAttackNames.indexOf(specialActionNamesArray[2]);
                    if(index3 != -1) {
                        return specialAttacks.get(index3);
                    } else return null;
                case -11:
                    charAction affAction = null;

                    if(affinity != declatoration.simulatorial.enums.affinity.AETHER) {
                        for (int i = 0; i < affSpecialActions.size(); i++) {
                            if (affSpecialActions.get(i).getAffinity() == affinity) {
                                affAction = affSpecialActions.get(i);
                                break;
                            }
                        }
                    } else {
                        for (int i = 0; i < affSpecialActions.size(); i++) {
                            if (affSpecialActions.get(i).getName().contains("Enervate")) {
                                affAction = affSpecialActions.get(i);
                                break;
                            }
                        }
                    }

                    return affAction;
                default:
                    // guard, focus, row, and so on
                    return null;
            }
        }
    }

    public static void setSpecialAttacks(ArrayList<String> specialAttacksNameList){

        //assist is added automatically, into the last index
        specialAttacks.clear();
        specialAttacks.addAll(dbExec.getCharSpecialFinaleAttacks(specialAttacksNameList));

        specialAttackNames.clear();
        for (int i = 0; i < specialAttacks.size(); i++) {
            specialAttackNames.add(specialAttacks.get(i).getName().toLowerCase());
        }

    }

    public static void setAffSpecialAttacks(ArrayList<affinity> affinityList){
        affSpecialActions.addAll(databaseExec.getAffSpecialAttacks(affinityList));
    }


    public boolean shouldBeFrontLine(){
        if(range.contains(3) || (range.contains(2) && !range.contains(1))){
            return false;
        } else if(range.contains(1) && range.contains(2)){
            return percentagize(40);
        }
        return true;
    }

    class statBoard extends HashMap<stat, Float> {


        public statBoard() {

            this.put(HP, 0.0F);
            this.put(ATK, 0.0F);
            this.put(DEF, 0.0F);
            this.put(MAG, 0.0F);
            this.put(INT, 0.0F);
            this.put(SPD, 0.0F);
            this.put(LCK, 0.0F);
            this.put(HIT, 0.0F);
            this.put(CRIT, 0.0F);
            this.put(PHYSAVO, 0.0F);
            this.put(MAGAVO, 0.0F);
            this.put(BLOCK, 0.0F);
            this.put(SP, 0.0F);
            this.put(SPREGEN, 0.0F);
            this.put(RES, 0.0F);


        }

    }

    public enum characterType {
        ATK,
        MAG,
        DEF,
        SPD,
        SUPPORT,
        MIXED;

        public static characterType getCharType(String str){
            characterType characterType = null;
            switch (str){
                case "atk":
                    characterType = ATK;
                    break;
                case "mag":
                    characterType = MAG;
                    break;
                case "def":
                    characterType = DEF;
                    break;
                case "spd":
                    characterType = SPD;
                    break;
                case "support":
                    characterType = SUPPORT;
                    break;
                case "mix":
                case "mixed":
                    characterType = MIXED;
                    break;
            }

            return characterType;
        }

    }
}