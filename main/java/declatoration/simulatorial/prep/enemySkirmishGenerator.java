package declatoration.simulatorial.prep;

import static declatoration.simulatorial.fight.actionHandler.percentagize;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import declatoration.simulatorial.chars;
import declatoration.simulatorial.databaseExec;
import declatoration.simulatorial.enums.affinity;

public class enemySkirmishGenerator {


    ArrayList<affinity> affinityList = new ArrayList<>();
    ArrayList<String> specialAttackNames = new ArrayList<>();


    private static ArrayList<chars> megadeathArrayList = new ArrayList<>();
    private static ArrayList<chars> skirmishArrayList = new ArrayList<>();

    private static final ArrayList<String> usedBossArrayList = new ArrayList<>();

    public enemySkirmishGenerator(){}

    // makes the chars arraylist, adds characters moves and all
//    public void createSkirmish(Context context, int enemyAmount, ArrayList<String> enemyNames,
//                                ArrayList<String> enemyPickedActions, int levelEnemy){
//        //enemy
//
//        for (int i = 0; i < enemyAmount; i++) {
//            chars chars;
//            if(Objects.equals(enemyNames.get(i), "-") || enemyNames.get(i) == null) {
//                chars = new chars(context, levelEnemy);
//            } else{
//                ArrayList<String> pickedActionsEnemy;
//                if(enemyPickedActions == null){
//                    chars = new chars(context, enemyNames.get(i), null, levelEnemy);
//                } else {
//                    pickedActionsEnemy = new ArrayList<>(enemyPickedActions.subList((i * 5), (i * 5) + 4));
//                    chars = new chars(context, enemyNames.get(i), pickedActionsEnemy, levelEnemy);
//                }
//            }
//            skirmishArrayList.add(chars);
//
//            addToSpecialsList(chars.specialActionNamesArray);
//
//            affinity aff = chars.getAffinity();
//            if(!affinityList.contains(aff)) {
//                affinityList.add(aff);
//            }
//        }
//    }

    public void createMegadeath(Context context, ArrayList<String> charNames,
                                ArrayList<String> pickedActions, Integer levelPlayer, ArrayList<chars> existingChars){

        //player

        megadeathArrayList = existingChars;


        for (int i = 0; i < megadeathArrayList.size(); i++) {

            if(megadeathArrayList.get(i) == null){
                megadeathArrayList.remove(i);
                i--;
            } else {

                chars currentCharWhateverMan = megadeathArrayList.get(i);
                addToSpecialsList(currentCharWhateverMan.specialActionNamesArray);

                affinity aff = currentCharWhateverMan.getAffinity();
                if (!affinityList.contains(aff)) {
                    affinityList.add(aff);
                }
            }
        }
    }

    public int createSkirmish(Context context, int strengthValue, ArrayList<String> unlockedCharNames,
                               int roundNum, ArrayList<String> bossNamesIfNeeded){

        databaseExec dbExec = databaseExec.getInstance(context);

        int finalDiffLevel;

        if(roundNum % 5 != 0) {
            ArrayList<String> allNames = dbExec.getOnsetPickableCharNames();

            String charToUnlock = null;
            int size = allNames.size();
            if (size > unlockedCharNames.size() && strengthValue > 5) {
                do {
                    charToUnlock = allNames.get((int) (Math.random() * size));
                } while (unlockedCharNames.contains(charToUnlock) &&
                        allNames.size() > unlockedCharNames.size());
            }

            skirmishArrayList = dbExec.getSkirmish(context, strengthValue, charToUnlock, unlockedCharNames);
            finalDiffLevel = strengthValue;

        } else{
            // boss timeeee
            finalDiffLevel = strengthValue + 3;
            skirmishArrayList = dbExec.getBossSkirmish(context, finalDiffLevel, unlockedCharNames, usedBossArrayList, bossNamesIfNeeded);

            usedBossArrayList.add(skirmishArrayList.get(0).getName());
        }

        for (int i = 0; i < skirmishArrayList.size(); i++) {

            addToSpecialsList(skirmishArrayList.get(i).specialActionNamesArray);

            affinity aff = skirmishArrayList.get(i).getAffinity();
            if (!affinityList.contains(aff)) {
                affinityList.add(aff);
            }
        }

        return finalDiffLevel;
    }



    private void addToSpecialsList(String[] names){
        for (int i = 0; i < names.length; i++) {
            String cur = names[i];
            if (cur != null && !specialAttackNames.contains(cur)) {
                specialAttackNames.add(cur);
            }
        }
    }



    public static ArrayList<chars> getMegadeathArrayList(){
        return megadeathArrayList;
    }
    public static ArrayList<chars> getSkirmishArrayList(){
        return skirmishArrayList;
    }

    public chars.characterType getGroupType() {
        ArrayList<Float> diffArray = new ArrayList<>(Arrays.asList(0f, 0f, 0f, 0f, 0f));

        for (int i = 0; i < skirmishArrayList.size(); i++) {
            float addValue = 1;
            if(i == 0){
                // gives boss more importance
                addValue = 1.5F;
            }
            switch (skirmishArrayList.get(i).getCharType()) {
                case ATK:
                    diffArray.set(0, diffArray.get(0) + addValue);
                    break;
                case DEF:
                    diffArray.set(1, diffArray.get(1) + addValue);
                    break;
                case MAG:
                    diffArray.set(2, diffArray.get(2) + addValue);
                    break;
                case SPD:
                    diffArray.set(3, diffArray.get(3) + addValue);
                    break;
                case SUPPORT:
                    diffArray.set(4, diffArray.get(4) + addValue);
                    break;
                case MIXED:
                    break;
            }
        }


        chars.characterType groupType = null;

        switch (diffArray.indexOf(Collections.max(diffArray))) {
            case 0:
                groupType = chars.characterType.ATK;
                break;
            case 1:
                groupType = chars.characterType.DEF;
                break;
            case 2:
                groupType = chars.characterType.MAG;
                break;
            case 3:
                groupType = chars.characterType.SPD;
                break;
            case 4:
                groupType = chars.characterType.SUPPORT;
                break;
        }

        return groupType;
    }

    public static void reset(){
        usedBossArrayList.clear();
        megadeathArrayList.clear();
        skirmishArrayList.clear();
    }
}
