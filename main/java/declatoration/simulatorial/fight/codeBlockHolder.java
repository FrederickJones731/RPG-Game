package declatoration.simulatorial.fight;


import java.util.ArrayList;
import java.util.HashMap;

import declatoration.simulatorial.enums.affinity;

public class codeBlockHolder {

    battleTokenArray combatants;

    public codeBlockHolder(battleTokenArray battleTokenArray){
        combatants = battleTokenArray;
    }

    public interface actionBlock {
        dmgSet getDmgSet();
    }

    public interface codeBlock{
        int getDmgSet(int charId, int targetId);
    }

    public class dmgSet{
        float mod;
        affinity affinityType;
        int targetId;

        public dmgSet(){}
        public dmgSet(float mod, affinity aff){
            this.mod = mod;
            affinityType = aff;
        }

        public dmgSet(float mod, affinity aff, int targetId){
            this.mod = mod;
            affinityType = aff;
            this.targetId = targetId;
        }
    }

    private HashMap<String, actionBlock> actionBlockHM = new HashMap<>();
    private HashMap<String, codeBlock> skillBlockHM = new HashMap<>();



    public static class elementalBurst implements codeBlock{

        @Override
        public int getDmgSet(int charId, int targetId) {
//            return new dmgSet((float) (0 + 1.5 * Math.random()), getRandomAffinity());
            return 0;
        }

        public static affinity getRandomAffinity(){
            affinity[] affinities = new affinity[]{affinity.SILVA, affinity.CYCLONE, affinity.INFERNO,
                    affinity.GLACIER, affinity.GAIA, affinity.TORRENT};

            return affinities[(int) (Math.random() * affinities.length)];
        }
    }

    public class chainAttack implements codeBlock{

        @Override
        public int getDmgSet(int charId, int targetId) {

            ArrayList<battleToken> bts;


            if(combatants.getBattleToken(charId).getIsPlayer()){
                bts = combatants.getActiveBattleTokens(false);
            } else {
                bts = combatants.getActiveBattleTokens(true);
            }

            int chainTarget = bts.get(0).getBattleSerialNum();

            for (int i = 0; i < 5; i++) {
                chainTarget = bts.get((int) (Math.random() * bts.size())).getBattleSerialNum();
                if(chainTarget != targetId){
                    break;
                }
            }

            return 0;
//            return new dmgSet(0.4f, null, chainTarget);
        }
    }

}
