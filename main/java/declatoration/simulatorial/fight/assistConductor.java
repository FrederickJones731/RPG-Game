package declatoration.simulatorial.fight;

import static declatoration.simulatorial.enums.statusEffect.ASSIST;
import static declatoration.simulatorial.fight.fightBackground.combatants;

import java.util.ArrayList;

import declatoration.simulatorial.chars;
import declatoration.simulatorial.enums.stat;
import declatoration.simulatorial.enums.statusEffect;

public class assistConductor {

    ArrayList<duo> duos = new ArrayList<>();


    public assistConductor() {}


    public void addAssistDuo(int assistee, int assistor) {

        for (int i = 0; i < duos.size(); i++) {
            if(duos.get(i).hasId(assistee) || duos.get(i).hasId(assistor)){
                combatants.getBattleToken(duos.get(i).targetId).removeStatusIfExists(statusEffect.ASSIST);
                combatants.getBattleToken(duos.get(i).initiatorId).removeStatusIfExists(statusEffect.ASSIST);

                duos.remove(i);
                i--;
            }
        }

        duos.add(new duo(assistee, assistor));
//        duos.get(duos.size() - 1).setBTTags(combatants, true);
    }
    public void removeDuo(int id) {
        for (int i = 0; i < duos.size(); i++) {
            if (duos.get(i).hasId(id)) {
                combatants.getBattleToken(duos.get(i).targetId).removeStatusIfExists(statusEffect.ASSIST);
                combatants.getBattleToken(duos.get(i).initiatorId).removeStatusIfExists(statusEffect.ASSIST);

                duos.remove(i);
                break;
            }
        }
    }

    public boolean isBeingAssisted(int charId){
        for (int i = 0; i < duos.size(); i++) {
            if(duos.get(i).targetId == charId){
                return true;
            }
        }
        return false;
    }


    public actionBoostParams getAssistStatBonuses(int turnHolder, boolean isMag) {
        if (combatants.getBattleToken(turnHolder).hasStatus(ASSIST)) {
            for (int i = 0; i < duos.size(); i++) {
                if (duos.get(i).hasId(turnHolder)) {

                    chars assistor = combatants.getBattleToken(duos.get(i).initiatorId).getChars();
                    actionBoostParams actionBoostParams = assistor.getAssistParams();

                    float assistMod = 1f;
                    if(combatants.getBattleToken(duos.get(i).getPartnerId(turnHolder)).hasStatus(statusEffect.ASSIST_UP)
                        && (int) combatants.getBattleToken(duos.get(i).getPartnerId(turnHolder)).getBonusEffectExtra(statusEffect.ASSIST_UP) == turnHolder){
                        // if assistor has assist_ip toward this char
                        // gives generous assist bonus (make this viable! no-one will use this anyway!)
                        assistMod = 1.65f;
                    }

                    actionBoostParams bonuses = new actionBoostParams();
                    stat offStat;
                    if(isMag){
                        offStat = stat.MAG;
                    } else{ offStat = stat.ATK;}
                    bonuses.setParams((float) (0.4 * actionBoostParams.attackBoost * assistor.getStatFloat(offStat)),
                            (float) (0.4 * actionBoostParams.hitBoost * assistor.getStatFloat(stat.HIT) * assistMod),
                            (float) (0.4 * actionBoostParams.critBoost * assistor.getStatFloat(stat.CRIT) * assistMod),
                            (float) (0.4 * actionBoostParams.blockBoost * assistor.getStatFloat(stat.LCK) * assistMod));

                    return bonuses;
                }
            }
        }
        return new actionBoostParams();
    }


}
