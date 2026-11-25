package declatoration.simulatorial;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import declatoration.simulatorial.enums.affinity;

public class catalog {

    // is a list of all current chars on the screen


    public ArrayList<chars> charList;
    ArrayList<String> specialAttackNames = new ArrayList<>();
    ArrayList<affinity> affinityList = new ArrayList<>();


    public catalog(){
        this.charList = new ArrayList<>();
    }

    //allChars
    public catalog(Context context, List<String> names, ArrayList<String> pickedActions, int levelPlayer,
                   int enemyAmount, ArrayList<String> enemyNames, ArrayList<String> enemyPickedActions, int levelEnemy) {

        this.charList = new ArrayList<>();


        //player
        for (int i = 0; i < names.size(); i++) {

            ArrayList<String> curCharActions = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                curCharActions.add(pickedActions.get(i * 5 + j));
            }

            this.charList.add(new chars(context, names.get(i), curCharActions, levelPlayer));

            chars currentCharWhateverMan = charList.get(i);
            addToSpecialsList(currentCharWhateverMan.specialActionNamesArray);

            affinity aff = currentCharWhateverMan.getAffinity();
            if(!affinityList.contains(aff)) {
                affinityList.add(aff);
            }
        }

        //enemy

        for (int i = 0; i < enemyAmount && i < enemyNames.size(); i++) {
            chars chars;
            if(Objects.equals(enemyNames.get(i), "-") || enemyNames.get(i) == null) {
                chars = new chars(context, levelEnemy);
            } else{
                ArrayList<String> pickedActionsEnemy;
                if(enemyPickedActions == null){
                    chars = new chars(context, enemyNames.get(i), null, levelEnemy);
                } else {
                    pickedActionsEnemy = new ArrayList<>(enemyPickedActions.subList((i * 5), (i * 5) + 4));
                    chars = new chars(context, enemyNames.get(i), pickedActionsEnemy, levelEnemy);
                }
            }
            this.charList.add(chars);

            addToSpecialsList(chars.specialActionNamesArray);

            affinity aff = chars.getAffinity();
            if(!affinityList.contains(aff)) {
                affinityList.add(aff);
            }
        }

        capitalizeAttackNames();

        chars.setSpecialAttacks(specialAttackNames);
        chars.setAffSpecialAttacks(affinityList);
    }

    public catalog(Context context, ArrayList<chars> megadeath, ArrayList<chars> enemyCharsArray) {

        this.charList = new ArrayList<>();

        //player
        for (int i = 0; i < megadeath.size(); i++) {

//            ArrayList<String> curCharActions = new ArrayList<>();
//            for (int j = 0; j < 5; j++) {
//                curCharActions.add(pickedActions.get(i * 5 + j));
//            }

            this.charList.add(megadeath.get(i));

            chars currentCharWhateverMan = charList.get(i);
            addToSpecialsList(currentCharWhateverMan.specialActionNamesArray);

            affinity aff = currentCharWhateverMan.getAffinity();
            if(!affinityList.contains(aff)) {
                affinityList.add(aff);
            }
        }

        //enemy

        for (int i = 0; i < enemyCharsArray.size(); i++) {
            chars chars = enemyCharsArray.get(i);

            this.charList.add(chars);

            addToSpecialsList(chars.specialActionNamesArray);

            affinity aff = chars.getAffinity();
            if(!affinityList.contains(aff)) {
                affinityList.add(aff);
            }
        }

        capitalizeAttackNames();

        chars.setSpecialAttacks(specialAttackNames);
        chars.setAffSpecialAttacks(affinityList);

    }

    public int getCharCount(){
        return this.charList.size();
    }

    private void addToSpecialsList(String[] names){
        for (int i = 0; i < names.length; i++) {
            String cur = names[i];
            if (cur != null && !specialAttackNames.contains(cur)) {
                specialAttackNames.add(cur);
            }
        }
    }

    private void capitalizeAttackNames(){
        for (int i = 0; i < specialAttackNames.size(); i++) {
            String cur = specialAttackNames.get(i);
            String newString;
            if(!cur.contains(" ")) {
                newString = cur.substring(0, 1).toUpperCase() + cur.substring(1).toLowerCase();
            } else{
                int spaceIndex = cur.indexOf(" ");
                newString = cur.substring(0, 1).toUpperCase() + cur.substring(1, spaceIndex).toLowerCase() + ' ';
                newString += cur.substring(spaceIndex + 1, spaceIndex + 2).toUpperCase() +
                        cur.substring(spaceIndex + 2).toLowerCase();
            }

            specialAttackNames.set(i, newString);
        }
    }
}
