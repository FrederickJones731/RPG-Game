package declatoration.simulatorial.fight;

import static declatoration.simulatorial.fight.fightBackground.actionTags;

import java.util.ArrayList;

import declatoration.simulatorial.charSkill;
import declatoration.simulatorial.charSkill.skillType;

public class skillLord {


    skillDivBinder skillBinder;


    public skillLord(battleTokenArray combatants){
        skillBinder = new skillDivBinder(combatants);
    }


    public ArrayList<charSkill> proccedSkills(int atkrId, int defId, skillType type){
//        return skillBinder.getAvailableSkills(type, atkrId, defId);
        return new ArrayList<>(0);
    }



    public class skillDivBinder {

        ArrayList<skillDiv> hitAlteringSkills, dmgAlteringSkills, affAlteringSkills, postBattleSkills;

        public skillDivBinder(battleTokenArray combatants){

            for (int o = 0; o < combatants.length(); o++) {
                ArrayList<charSkill> curCharSkills = combatants.getBattleToken(o).getChars().getCharSkills();

                for (int i = 0; i < curCharSkills.size(); i++) {
                    getArrayList(curCharSkills.get(i).getSkillType()).add(new skillDiv(curCharSkills.get(i), o));
                }
            }
        }

        public ArrayList<charSkill> getAvailableSkills(skillType type, int charId, int targetId){

            ArrayList<charSkill> proccedSkills = new ArrayList<>();
            ArrayList<skillDiv> cur = getArrayList(type);


            for (int i = 0; i < cur.size(); i++) {
                charSkill skill = cur.get(i).skill;

                // if is a correct turn to trigger the skill (chars turn / is overarching)
                if(skill.isOverarching() || cur.get(i).ownerCheck(charId, targetId)){

                    //if tag1 is present
                    if(actionTags.toString().contains(skill.getTags().get(0))) {
                        if (skill.getTags().size() > 1) {
                            // if tag2 exists, is it present?
                            if (actionTags.toString().contains(skill.getTags().get(1))) {
                                proccedSkills.add(cur.get(i).skill);
                            }
                        } else {
                            proccedSkills.add(cur.get(i).skill);
                        }
                    }
                }
            }

            return proccedSkills;
        }


        private ArrayList<skillDiv> getArrayList(skillType type){
            ArrayList<skillDiv> cur = new ArrayList<>();
            switch (type){
                case HITCRIT:
                    cur = hitAlteringSkills;
                    break;
                case DAMAGE:
                    cur = dmgAlteringSkills;
                    break;
                case AFF_EFF:
                    cur = affAlteringSkills;
                    break;
                case POST:
                    cur = postBattleSkills;
            }
            return cur;
        }


        public class skillDiv {

            charSkill skill;
            skillType type;
            int ownerId;

            public skillDiv(charSkill charSkill, int ownerId) {
                skill = charSkill;
                type = charSkill.getSkillType();
                this.ownerId = ownerId;
            }

            public boolean ownerCheck(int id1, int id2){
                return (ownerId == id1 || ownerId == id2);
            }
        }
    }
}
