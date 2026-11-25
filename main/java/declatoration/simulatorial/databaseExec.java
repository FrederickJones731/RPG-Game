package declatoration.simulatorial;

import static declatoration.simulatorial.fight.actionHandler.percentagize;
import static declatoration.simulatorial.prep.warRoom.roundNum;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import declatoration.simulatorial.enums.affinity;
import declatoration.simulatorial.enums.stat;
import declatoration.simulatorial.enums.weaponType;

public class databaseExec {

    private static databaseExec instance;

    private static numDatabase statDatabase;
    private static actionDatabase actionsDatabase;
    private static bossDatabase bossDatabase;


    private databaseExec(Context context){
        statDatabase = new numDatabase(context);
        actionsDatabase = new actionDatabase(context);
        bossDatabase = new bossDatabase(context);

        numDatabase.bossesNameList = bossDatabase.getOnsetUnpickableCharArrayList();
    }

    public static synchronized databaseExec getInstance(Context context) {
        if (instance == null) {
            instance = new databaseExec(context);
        }
        return instance;
    }

    public void resetActionsDatabase(){
        actionsDatabase.resetDatabase();
        bossDatabase.resetDB();
    }

    // more general stuff

    public HashMap<Integer, String> getAllPlayerCharNames(){
        return statDatabase.getAllPlayerCharNames();
    }
    public ArrayList<String> getOnsetPickableCharNames(){
        HashMap<Integer, String> allNames = getAllPlayerCharNames();
        ArrayList<String> namesToRemove = bossDatabase.getOnsetUnpickableCharArrayList();

//        allNames.entrySet().removeIf(entry -> namesToRemove.contains(entry.getValue()));
//        allNames.put(allNames.size() + 1, "Far'quei");

        ArrayList<String> finalList = new ArrayList<>(allNames.values());
        finalList.removeAll(namesToRemove);
        finalList.remove("Dantè");
        finalList.remove("Gordon");
        finalList.remove("Vale");

        return finalList;
    }


    // chars creation
    public void makeChar(chars c, String charName, Integer level, creationMethod method){

        switch (method){
            case FULL:
                c.copyChar(statDatabase.generatePlayer(charName, level));
                break;
            case BASES:
                c.copyChar(statDatabase.getCharStatBases(charName));
                break;
            case ENEMY:
                c.copyChar(statDatabase.generateEnemy(level));
                break;
        }

        charName = c.getName();

        switch (method){
            case FULL:
            case ENEMY:
                c.setSpecialActionNamesArray(statDatabase.getCharSpecialAttackNames(charName));
                c.setCharType(statDatabase.getCharType(charName));

                c.copySkills(actionsDatabase.getCharSkills(charName, level));

                c.setRange(statDatabase.getRange(charName));
                actionCheck(c);

            case BASES:

                c.setAffinity(statDatabase.getCharAffinity(charName));
                c.setWeapon(statDatabase.getCharWeaponType(charName));
                c.setClassData(statDatabase.getPlayerClassData(charName));
                c.setActionBoostParams(statDatabase.getCharAssistParams(charName));

                c.setSFXCharBase(statDatabase.getSFXBaseChar(charName));

        }

    }
    private void actionCheck(chars c){

        if(!c.getActionsFilled()) {
            ArrayList<charAction> allCharsActions = actionsDatabase.getAllCharUniqueActions(c.getName(), c.getLevel(), statDatabase);

            ArrayList<String> selectedActionNames = new ArrayList<>(Arrays.asList("", "", "", "", ""));

            for (int i = 0; i < selectedActionNames.size(); i++) {
                //null, NOT ""
                // "" means player decided to remove move
                if (selectedActionNames.get(i) == null || Objects.equals(selectedActionNames.get(i), "")) {

                    boolean good = false;
                    int kek = 0;
                    while(!good && kek < 12){
                        String cur = allCharsActions.get((int) (allCharsActions.size() * Math.random())).getName();
                        if(!selectedActionNames.contains(cur)){
                            selectedActionNames.set(i, cur);
                            good = true;
                        }
                        kek++;
                    }
                }
            }

            c.selectedActionNames = selectedActionNames;
        }
    }

    // battle prep - charActions, charSkills...

    public ArrayList<charAction> getAllCharUniqueActions(String name, int level){
        return actionsDatabase.getAllCharUniqueActions(name, level, statDatabase);
    }
    public ArrayList<charAction> getCharGenerics(String name){ return actionsDatabase.getCharGenericAttacks(name); }
    public ArrayList<charAction> getCharActions(String name, int level, ArrayList<String> actionNames){
        return actionsDatabase.getCharActions(name, level, statDatabase, actionNames);
    }

    public ArrayList<charSkill> getCharSkills(String name, int level){
        return actionsDatabase.getCharSkills(name, level);
    }

    //specific needed things
    public int getCharImageResId(String name){
        return statDatabase.getCharImageResId(name);
    }
    public weaponType getCharWeaponType(String name){ return statDatabase.getCharWeaponType(name); }
    public Integer getNumBullets(String name){
        return statDatabase.getNumBullets(name);
    }
    public float getCharBaseStat(String name, stat stat){
        return statDatabase.getCharStatBases(name).getStatFloat(stat);
    }

    public static ArrayList<charAction> getAffSpecialAttacks(ArrayList<affinity> affinityList){
        return (actionsDatabase.getAffinityGenericAttacks(affinityList));
    }
    public ArrayList<charAction> getCharSpecialFinaleAttacks(ArrayList<String> specialAttacksNameList){
        return actionsDatabase.getCharSpecialFinaleAttacks(specialAttacksNameList);
    }


    // fight generation
    public ArrayList<chars> getSkirmish(Context context, int strVal, String charToUnlock, ArrayList<String> unlockedChars){
        return statDatabase.getSkirmish(context, strVal, charToUnlock, unlockedChars);
    }
    public ArrayList<chars> getBossSkirmish(Context context, int strVal, ArrayList<String> unlockedChars,
                                            ArrayList<String> usedBosses, ArrayList<String> unusables){

        float strNum = (roundNum + 5) / 15f;
        int bossStrNum;

        if (strNum < 1){
            strNum = 1;
        }

        // only do this before final round, get random boss str val
        if(strNum <= 4 &&strNum % 1 > 0.1 && percentagize(50)){
            bossStrNum = (int) strNum + 1;
        } else{
            bossStrNum = (int) strNum;
        }

        declatoration.simulatorial.bossDatabase.generationSet bossGenSet;
        bossGenSet = bossDatabase.getBoss(bossStrNum, usedBosses, unusables);
        bossGenSet.setPowerLevels(statDatabase.getPowerLevels(bossGenSet.getHenchmenNames()));

        return statDatabase.getBossSkirmish(context, bossGenSet, strVal);
    }



    public enum creationMethod{
        BASES,
        FULL,
        ENEMY;
    }

}
