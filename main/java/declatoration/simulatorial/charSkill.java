package declatoration.simulatorial;

import java.util.ArrayList;

import declatoration.simulatorial.enums.stat;
import declatoration.simulatorial.enums.statusEffect;
import declatoration.simulatorial.enums.target;

public class charSkill {

    String skillName;
    String skillDesc;
    int iconResource;

    // up to 3 - action events, weaponType, affinity...
    // this in conjunction with is offensive should cover a LOT of things Ill need here
    ArrayList<String> tagInvokers;

    bonusEffects skillEffects;

    private boolean isOffensive;
    private boolean isOverarching;
    skillType type;

//    float modifier;
//    int effectTimeLength;      // -1 for infinite, otherwise each time a char moves = 1 turn
//
//    boolean hasTriggerPercent;
//    stat triggerStat;
//    float triggerPercent;   // such as Luxana's overflow (luck / 2 %)
//
//    boolean affectsStat;
//    String affectedStat;     // hit%, crit, atk... null for other
//    target affectedTarget;   // enum
//
//
//    boolean afflictsStatusCondition;
//    String statusAfflicted;



    public charSkill() {
        this.tagInvokers = new ArrayList<>();
        skillEffects = new bonusEffects();
    }


    public void setName(String name) {
        this.skillName = name;
    }
    public void setSkillDesc(String skillDesc) { this.skillDesc = skillDesc; }
    public void setIconResId(int iconResource) { this.iconResource = iconResource; }
    public void setType(String procTime) { this.type = skillType.valueOf(procTime.toUpperCase()); }

    public void setModifiers(stat changingStat, float modifier) { skillEffects.setTargetStat(changingStat, modifier);}
    public void setTriggers(String trigCondition, int triggerPercent) { skillEffects.setTriggers(trigCondition, triggerPercent);}
    public void setStatusAfflicted(statusEffect statusAfflicted, target target) { skillEffects.setStatus(statusAfflicted, target, 8);}

    public void setIsOffensive(boolean b){isOffensive = b;}
    public void setIsOverarching(boolean bebe){ isOverarching = bebe;}
    public void addTag(String str) { this.tagInvokers.add(str); }


//    // if conditions are met, return multiplier. otherwise, returns 1
//    //whether or not the skill should run a check, is checked by fight.class
//    public boolean checkProc(chars character, ArrayList<String> existingTags) {
//        boolean res = true;
//
//        if (hasTriggerPercent) {
//            res = percentagize(character);
//        }
//        //demands both reqtags (if exists) to be present
//        if (requirementTags.get(0) != null) {
//            res = res  && existingTags.contains(requirementTags.get(0));
//            if (requirementTags.get(1) != null) {
//                res = res  && existingTags.contains(requirementTags.get(1));
//            }
//        }
//
//        return res;
//    }

    public boolean percentagize(chars character) {
        return ((skillEffects.getTriggerPercent()) >= (Math.random() * 100));
    }


    public String getName() {
        return this.skillName;
    }
    public String getSkillDesc(){ return this.skillDesc;}

    public int getIconResource() {
        return this.iconResource;
    }

    public boolean isOffensive(){return this.isOffensive;}
    public boolean isOverarching(){return this.isOverarching;}
    public skillType getSkillType(){return type;}

    public bonusEffects getSkillEffects(){return skillEffects;}
    public ArrayList<String> getTags() { return tagInvokers; }

    public enum skillType{
        HITCRIT,
        DAMAGE,
        AFF_EFF,
        POST;
    }
}
