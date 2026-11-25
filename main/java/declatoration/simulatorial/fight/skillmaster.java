package declatoration.simulatorial.fight;


import java.util.ArrayList;
import java.util.HashMap;

import declatoration.simulatorial.charSkill;

public class skillmaster {

    // god save my soul
    // here we go

    HashMap<String, charSkill> skillTable = new HashMap<>();
    int[] skillAmounts = new int[8];
    String actionTags;

    public skillmaster(){
//        for (int i = 0; i < combatants.length() - 1; i++) {
//
//            ArrayList <charSkill> curCharSkills = combatants.getBattleToken(i).getChars().getCharSkills();
//            for (int j = 0; j < curCharSkills.size(); j++) {
//
//                skillTable.put(String.valueOf((i + skillAmounts[i])),
//                        curCharSkills.get(j));
//                skillAmounts[i]++;
//            }
//        }
    }


    private ArrayList<charSkill> getAllCharSkills(int id){
        ArrayList<charSkill> charSkills = new ArrayList<>();

        for (int i = 0; i < skillAmounts[id]; i++) {
            charSkills.add(skillTable.get(String.valueOf(id + i)));
        }

        return charSkills;
    }

    private boolean isTriggered(charSkill curSkill){

        for (int j = 0; j < curSkill.getTags().size(); j++) {
            // kicks you out if a tag doesnt exist
            if(curSkill.getTags().get(j) != null && !actionTags.contains(curSkill.getTags().get(j))){
                break;
            }
            // adds it only if you make it through everything
            if(j == curSkill.getTags().size() - 1){
                return true;
            }
        }
        return false;
    }


    // includes - weaponType, crit%, hit, block%
    // everything for DAMAGEPROCEDURE()

    //assumes all hit/crit/block raising skills, are integers to be added on to final value and NOT multipliers
    public actionBoostParams getHitAtkBoosts(int atkrId, int defId, boolean isMag){

        this.actionTags = fightBackground.actionTags.toString();

        ArrayList<charSkill> procced = new ArrayList<>();
        actionBoostParams changes = new actionBoostParams();

        ArrayList<charSkill> atkrSkills = getAllCharSkills(atkrId);
        ArrayList<charSkill> defSkills = getAllCharSkills(defId);

        for (int i = 0; i < atkrSkills.size(); i++) {

            charSkill curSkill = atkrSkills.get(i);

//            if(curSkill.isOffensive() && curSkill.getProcTime().equals("hit")){
//                if(isTriggered(curSkill)){
//                    switch (curSkill.getSkillEffects().getChangingStat()){
//                        case ATK:
//                            if(!isMag) {
//                                changes.multifyAtk(curSkill.getSkillEffects().getStatChangeDegree());
//                            }
//                            break;
//                        case MAG:
//                            changes.multifyAtk(curSkill.getSkillEffects().getStatChangeDegree());
//                            break;
//                        case HIT:
//                            changes.addify("hit", (int) curSkill.getSkillEffects().getStatChangeDegree());
//                            break;
//                        case CRIT:
//                            changes.addify("crit", (int) curSkill.getSkillEffects().getStatChangeDegree());
//                            break;
//                        case BLOCK:
//                            changes.addify("block", (int) curSkill.getSkillEffects().getStatChangeDegree());
//                    }
//
//                    if(curSkill.getSkillEffects().hasSecondStatChange()){
//                        switch (curSkill.getSkillEffects().getChangingStat2()){
//                            case ATK:
//                                if(!isMag) {
//                                    changes.multifyAtk(curSkill.getSkillEffects().getStatChangeDegree());
//                                }
//                                break;
//                            case MAG:
//                                if(isMag) {
//                                    changes.multifyAtk(curSkill.getSkillEffects().getStatChangeDegree());
//                                }
//                                break;
//                            case HIT:
//                                changes.addify("hit", (int) curSkill.getSkillEffects().getStatChangeDegree());
//                                break;
//                            case CRIT:
//                                changes.addify("crit", (int) curSkill.getSkillEffects().getStatChangeDegree());
//                                break;
//                            case BLOCK:
//                                changes.addify("block", (int) curSkill.getSkillEffects().getStatChangeDegree());
//                        }
//                    }
////                    procced.add(curSkill);
//                }
//            }
        }
        for (int i = 0; i < defSkills.size(); i++) {

            charSkill curSkill = defSkills.get(i);

//            if(!curSkill.isOffensive() && curSkill.getProcTime().equals("hit")){
//
//                if(isTriggered(curSkill)){
//                    switch (curSkill.getSkillEffects().getChangingStat()){
//                        case DEF:
//                            if(!isMag) {
//                                changes.multifyAtk((float) Math.pow(curSkill.getSkillEffects().getStatChangeDegree(), -1));
//                            }
//                            break;
//                        case INT:
//                            if(isMag) {
//                                changes.multifyAtk((float) Math.pow(curSkill.getSkillEffects().getStatChangeDegree(), -1));
//                            }
//                            break;
//                        case PHYSAVO:
//                            if(!isMag) {
//                                changes.addify("hit", (int) - curSkill.getSkillEffects().getStatChangeDegree());
//                            }
//                            break;
//                        case MAGAVO:
//                            if(isMag) {
//                                changes.addify("hit", (int) - curSkill.getSkillEffects().getStatChangeDegree());
//                            }
//                            break;
//                        case CRIT:
//                            changes.addify("crit", (int) - curSkill.getSkillEffects().getStatChangeDegree());
//                            break;
//                        case BLOCK:
//                            changes.addify("block", (int) - curSkill.getSkillEffects().getStatChangeDegree());
//                    }
//
//                    if(curSkill.getSkillEffects().hasSecondStatChange()){
//
//                        switch (curSkill.getSkillEffects().getChangingStat2()){
//                            case DEF:
//                                if(!isMag) {
//                                    changes.multifyAtk((float) Math.pow(curSkill.getSkillEffects().getStatChangeDegree(), -1));
//                                }
//                                break;
//                            case INT:
//                                if(isMag) {
//                                    changes.multifyAtk((float) Math.pow(curSkill.getSkillEffects().getStatChangeDegree(), -1));
//                                }
//                                break;
//                            case PHYSAVO:
//                                if(!isMag) {
//                                    changes.addify("hit", (int) - curSkill.getSkillEffects().getStatChangeDegree());
//                                }
//                                break;
//                            case MAGAVO:
//                                if(isMag) {
//                                    changes.addify("hit", (int) - curSkill.getSkillEffects().getStatChangeDegree());
//                                }
//                                break;
//                            case CRIT:
//                                changes.addify("crit", (int) - curSkill.getSkillEffects().getStatChangeDegree());
//                                break;
//                            case BLOCK:
//                                changes.addify("block", (int) - curSkill.getSkillEffects().getStatChangeDegree());
//                        }
//                    }
////                    procced.add(curSkill);
//                }
//            }
        }
        return changes;
    }


    //includes - aff stuffs, crit dmg, raw dmg, anything with dmg, defensive boosts, RAW NUMBERS
    // everything for GETDAMAGE()
    public actionBoostParams getDmgSkills(int atkrId, int defId, boolean isMag){

        this.actionTags = fightBackground.getActionTags();

        ArrayList<charSkill> procced = new ArrayList<>();
        actionBoostParams changes = new actionBoostParams();

        ArrayList<charSkill> atkrSkills = getAllCharSkills(atkrId);
        ArrayList<charSkill> defSkills = getAllCharSkills(defId);


        for (int i = 0; i < atkrSkills.size(); i++) {

            charSkill curSkill = atkrSkills.get(i);

//            if(curSkill.isOffensive() && curSkill.getProcTime().equals("dmg")){
//                if(isTriggered(curSkill)){
//                    switch (curSkill.getSkillEffects().getChangingStat()){
//                        case ATK:
//                            if(!isMag) {
//                                changes.multifyAtk(curSkill.getSkillEffects().getStatChangeDegree());
//                            }
//                            break;
//                        case MAG:
//                            if(isMag) {
//                                changes.multifyAtk(curSkill.getSkillEffects().getStatChangeDegree());
//                            }
//                            break;
//                        case HIT:
//                            changes.addify("hit", (int) curSkill.getSkillEffects().getStatChangeDegree());
//                            break;
//                        case CRIT:
//                            changes.addify("crit", (int) curSkill.getSkillEffects().getStatChangeDegree());
//                            break;
//                        case BLOCK:
//                            changes.addify("block", (int) curSkill.getSkillEffects().getStatChangeDegree());
//                    }
//
//                    if(curSkill.getSkillEffects().hasSecondStatChange()){
//                        switch (curSkill.getSkillEffects().getChangingStat2()){
//                            case ATK:
//                                if(!isMag) {
//                                    changes.multifyAtk(curSkill.getSkillEffects().getStatChangeDegree());
//                                }
//                                break;
//                            case MAG:
//                                if(isMag) {
//                                    changes.multifyAtk(curSkill.getSkillEffects().getStatChangeDegree());
//                                }
//                                break;
//                            case HIT:
//                                changes.addify("hit", (int) curSkill.getSkillEffects().getStatChangeDegree());
//                                break;
//                            case CRIT:
//                                changes.addify("crit", (int) curSkill.getSkillEffects().getStatChangeDegree());
//                                break;
//                            case BLOCK:
//                                changes.addify("block", (int) curSkill.getSkillEffects().getStatChangeDegree());
//                        }
//                    }
////                    procced.add(curSkill);
//                }
//            }
        }
        for (int i = 0; i < defSkills.size(); i++) {

            charSkill curSkill = defSkills.get(i);

//            if(!curSkill.isOffensive() && curSkill.getProcTime().equals("dmg")){
//
//                if(isTriggered(curSkill)){
//                    switch (curSkill.getSkillEffects().getChangingStat()){
//                        case DEF:
//                            if(!isMag) {
//                                changes.multifyAtk((float) Math.pow(curSkill.getSkillEffects().getStatChangeDegree(), -1));
//                            }
//                            break;
//                        case INT:
//                            if(isMag) {
//                                changes.multifyAtk((float) Math.pow(curSkill.getSkillEffects().getStatChangeDegree(), -1));
//                            }
//                            break;
//                        case PHYSAVO:
//                            if(!isMag) {
//                                changes.addify("hit", (int) - curSkill.getSkillEffects().getStatChangeDegree());
//                            }
//                            break;
//                        case MAGAVO:
//                            if(isMag) {
//                                changes.addify("hit", (int) - curSkill.getSkillEffects().getStatChangeDegree());
//                            }
//                            break;
//                        case CRIT:
//                            changes.addify("crit", (int) - curSkill.getSkillEffects().getStatChangeDegree());
//                            break;
//                        case BLOCK:
//                            changes.addify("block", (int) - curSkill.getSkillEffects().getStatChangeDegree());
//                    }
//
//                    if(curSkill.getSkillEffects().hasSecondStatChange()){
//
//                        switch (curSkill.getSkillEffects().getChangingStat2()){
//                            case DEF:
//                                if(!isMag) {
//                                    changes.multifyAtk((float) Math.pow(curSkill.getSkillEffects().getStatChangeDegree(), -1));
//                                }
//                                break;
//                            case INT:
//                                if(isMag) {
//                                    changes.multifyAtk((float) Math.pow(curSkill.getSkillEffects().getStatChangeDegree(), -1));
//                                }
//                                break;
//                            case PHYSAVO:
//                                if(!isMag) {
//                                    changes.addify("hit", (int) - curSkill.getSkillEffects().getStatChangeDegree());
//                                }
//                                break;
//                            case MAGAVO:
//                                if(isMag) {
//                                    changes.addify("hit", (int) - curSkill.getSkillEffects().getStatChangeDegree());
//                                }
//                                break;
//                            case CRIT:
//                                changes.addify("crit", (int) - curSkill.getSkillEffects().getStatChangeDegree());
//                                break;
//                            case BLOCK:
//                                changes.addify("block", (int) - curSkill.getSkillEffects().getStatChangeDegree());
//                        }
//                    }
////                    procced.add(curSkill);
//                }
//            }
        }
        return changes;
    }


    //includes - status stuff, heal, and stat changes - this should be lesser called on
    // everything for AFTERPARTY()
    public ArrayList<charSkill> getPostCombatSkills(int atkrId, int defId, boolean isMag){

        this.actionTags = fightBackground.actionTags.toString();

        ArrayList<charSkill> procced = new ArrayList<>();

        ArrayList<charSkill> atkrSkills = getAllCharSkills(atkrId);
        ArrayList<charSkill> defSkills = getAllCharSkills(defId);

        for (int i = 0; i < atkrSkills.size(); i++) {

            charSkill curSkill = atkrSkills.get(i);

//            if(curSkill.isOffensive() && curSkill.getProcTime().equals("status")){
//                if(isTriggered(curSkill)){
//                    procced.add(curSkill);
//                }
//            }
        }
        for (int i = 0; i < defSkills.size(); i++) {

            charSkill curSkill = defSkills.get(i);

//            if(!curSkill.isOffensive() && curSkill.getProcTime().equals("status")){
//                if(isTriggered(curSkill)){
//                    procced.add(curSkill);
//                }
//            }
        }
        return procced;
    }


}
