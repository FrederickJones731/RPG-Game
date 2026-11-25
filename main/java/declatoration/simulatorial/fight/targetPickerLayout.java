package declatoration.simulatorial.fight;

import static declatoration.simulatorial.enums.statusEffect.AEGIS_SHIELD;
import static declatoration.simulatorial.enums.statusEffect.GRAPPLE;
import static declatoration.simulatorial.enums.statusEffect.GRAPPLE_RELEASE;
import static declatoration.simulatorial.enums.statusEffect.SWALLOW;
import static declatoration.simulatorial.enums.statusEffect.SWALLOWED;
import static declatoration.simulatorial.enums.statusEffect.UNTARGETABLE;
import static declatoration.simulatorial.enums.target.GRAPPLED;
import static declatoration.simulatorial.enums.target.SELF;
import static declatoration.simulatorial.enums.target.SINGLE_ENEMY;
import static declatoration.simulatorial.enums.target.UNGRAPPLED_ALLY;
import static declatoration.simulatorial.fight.fightBackground.combatants;
import static declatoration.simulatorial.fight.fightBackground.currentTurnCharId;

import java.util.ArrayList;

import declatoration.simulatorial.enums.statusEffect;
import declatoration.simulatorial.enums.target;

public class targetPickerLayout implements Runnable{

    private boolean resultPicked = false;
    private boolean isPlayer;
    private boolean rageTargetSelect = false;

    private int charId;

    private ArrayList<Integer> targetablesId, targets;

    private charClickedBridger foregroundInterface;

    private target attackAOE;
    private battleTokenArray battleTokenArray;

    private boolean hasTargetEffects;
    private ArrayList<statusEffectList.effect> targetAffEff = new ArrayList<>();

    private targetEffectsList statusMap = new targetEffectsList();

    boolean isOffensive;
    fightForeground fightScreen;




    public targetPickerLayout(fightForeground fightScreen, charClickedBridger parentInterface, ArrayList<Integer> targetIds,
                              battleTokenArray combatants, target aoe, boolean isOffensive, boolean isPlayer,
                              int charId){

        foregroundInterface = parentInterface;

        this.battleTokenArray = combatants;
        this.attackAOE = aoe;


        this.fightScreen = fightScreen;
        this.isOffensive = isOffensive;

    // if no rampart effect, just all targetables
        this.targetablesId = targetIds;


        this.isPlayer = isPlayer;
        this.charId = charId;


        targets = new ArrayList<>();

        hasTargetEffects = hasTargetAffStatusEff(charId);

//        if(hasTargetEffects || battleTokenArray.hasRampart(!isPlayer)) {
        statusMap = new targetEffectsList(targetAffEff);
//        }

    }



    //this JUST updates targets affected by the attack
    public boolean clicked(int clickedSerial) {

        boolean attackable;

        if(attackAOE == target.SWALLOWED){
            if(clickedSerial == currentTurnCharId){
                clickedSerial = -1;
            }
        }

        boolean finalTargetSelected = clickedSerial == -1;

        // id is a number, initial tap
        if (!finalTargetSelected) {

            // can be clicked, is in targetables
            if (targetablesId.contains(clickedSerial)) {

                statusEffect targetEffect = statusMap.getEffectIfExists(clickedSerial);

                // if target doesnt have any statuses making it untargetable
                boolean canBeTargeted = targetEffect != statusEffect.INTIMIDATE &&
                        targetEffect != statusEffect.RAMPART &&
                        targetEffect != AEGIS_SHIELD &&
                        targetEffect != GRAPPLE && targetEffect != GRAPPLE_RELEASE &&
                        targetEffect != statusEffect.UNTARGETABLE;

                canBeTargeted = canBeTargeted &&
                        ((targetEffect != statusEffect.ENGAGE) ||
                        (targetEffect == statusEffect.ENGAGE &&
                                (clickedSerial == statusMap.imposerId) || clickedSerial == charId));


//                boolean canBeTargeted = statusMap.isAttackable(clickedSerial);
                //adds selectes targets to targets arraylist
                if (canBeTargeted) {
                    targets.clear();

                    if(combatants.getBattleToken(currentTurnCharId).hasStatus(SWALLOWED)){
                        if(attackAOE != SELF) {
                            attackAOE = SINGLE_ENEMY;
                        }
                    }
                    switch (attackAOE) {
                        case SINGLE_ENEMY:
                        case SINGLE_ALLY_NO_USER:
                        case SINGLE_ALLY:
                        case SELF:
                        case GRAPPLED:
                        case SWALLOWED:
                        case UNGRAPPLED_ALLY:
                            targets.add(clickedSerial);
                            break;

                        case ENEMY_ROW:
                            boolean targetIsFrontLine = battleTokenArray.getBattleToken(clickedSerial).getIsFrontLine();

                            for (int i = 0; i < targetablesId.size(); i++) {
                                if (battleTokenArray.getBattleToken(targetablesId.get(i)).getIsFrontLine() == targetIsFrontLine) {
                                    targets.add(targetablesId.get(i));
                                }
                            }
                            break;

                        case ENEMY_COLUMN:
                            targets = battleTokenArray.getRowColumnTargets(clickedSerial);
                            break;

                        case ALL_ENEMIES:
                        case ALL_ALLIES:
                            targets.addAll(targetablesId);
                            break;

                        case ALL:
                            ArrayList<Integer> ids = new ArrayList<>();
                            ArrayList<battleToken> all = battleTokenArray.getActiveBattleTokens();
                            for (int i = 0; i < all.size(); i++) {
                                ids.add(all.get(i).getBattleSerialNum());
                            }
                            targets.addAll(ids);
                            break;

                    }

                    bulkSetFocusedFalse();
//                    bulkSetFocusedTrue(targets);
                    setFocusedTrue(clickedSerial);

                    attackable = true;

                }
                // otherwise doesnt select them, and exacts a toast with the reasoning
                else {

                    // so it doesnt return -1. there are better places to put this
                    battleTokenArray.getBattleToken(clickedSerial).getCharLayout().setIsTargetable(false);

                    // is in targetables, but cannot be selected
                    switch (targetEffect) {
                        case RAMPART:
                            // this also means target is back row. important
                            foregroundInterface.rampartToast(battleTokenArray.getRampName(!isPlayer));
                            break;
                        case INTIMIDATE:
                            foregroundInterface.intimidateToast(battleTokenArray.getBattleToken(clickedSerial).getName());
                            break;
                        case UNTARGETABLE:
                            foregroundInterface.untargetableToast(battleTokenArray.getBattleToken(clickedSerial).getName());
                            break;
                        case ENGAGE:
                            battleToken bt = battleTokenArray.getBattleToken(statusMap.imposerId);
                            if(bt == null){
                                int swal = (int) battleTokenArray.getBattleToken(currentTurnCharId).getBonusEffectExtra(statusEffect.GRAPPLED);
                                bt = battleTokenArray.getBattleToken(swal);
                            }
                            foregroundInterface.engageToast(bt.getName());
                            break;
                        case GRAPPLE:
                        case GRAPPLE_RELEASE:
                            //grapple - target is grappled and cannot be targeted
                            //grap release - no char is grappled, and noone can be targeted
                            foregroundInterface.grappledToast(targetEffect == statusEffect.GRAPPLE,
                                    combatants.getBattleToken(clickedSerial).getName());
                            break;
                        case AEGIS_SHIELD:
                            // either char has aegis shield and cannot be defended
                            foregroundInterface.aegisToast();
                            break;
                        case RAGE:
                        default:
                    }


                    attackable = false;
                }

            } else {
                // not in targetables
                attackable = false;
                battleTokenArray.getBattleToken(clickedSerial).getCharLayout().setIsTargetable(false);
            }
        } else {
            // final target selected

            //adds random to targets arraylist, then exit
            if (attackAOE == target.RANDOM_TARGET || attackAOE == target.RANDOM_FROM_ALL) {
                // for random hitters

                targets.add(targetablesId.get((int) (Math.random() * targetablesId.size())));

            } else if (attackAOE == target.RANDOM_ENEMY_COLUMN) {
                int tar = targetablesId.get((int) (Math.random() * targetablesId.size()));
                targets = battleTokenArray.getRowColumnTargets(tar);
            }
            //finalize
            foregroundInterface.finalTargetsAndProceed(targets);
            bulkSetFocusedFalse();

            resultPicked = true;

            attackable = true;

        }


        return attackable;

    }



    private void bulkSetFocusedFalse() {
        for (int i = 0; i < battleTokenArray.length(); i++) {
            this.battleTokenArray.getBattleToken(i).getCharLayout().setFocused(false);
        }
    }
    private void bulkSetFocusedTrue(ArrayList<Integer> serials) {
        for (int serial : serials) {
            this.battleTokenArray.getBattleToken(serial).getCharLayout().setFocused(true);
        }
    }
    private void setFocusedTrue(int targetId) {
            this.battleTokenArray.getBattleToken(targetId).getCharLayout().setFocused(true);

    }


    public boolean hasTargetAffStatusEff(int charId){
        targetAffEff = battleTokenArray.getBattleToken(charId).getTargetAffStatusEffects();

        return !targetAffEff.isEmpty();
    }

    // for rage only
    @Override
    public void run() {

        if(rageTargetSelect){

            boolean targetIsAttackable = false;
            int selectedRandTarget = targetablesId.get((int) (Math.random() * targetablesId.size()));

            while (!targetIsAttackable){

                statusEffect targetEffect = statusMap.getEffectIfExists(selectedRandTarget);

                // if target doesnt have any statuses making it untargetable
                boolean canBeTargeted =
                        targetEffect != statusEffect.RAMPART &&
                        targetEffect != statusEffect.UNTARGETABLE;

                if(canBeTargeted){
                    targetIsAttackable = true;
                } else {
                    selectedRandTarget = targetablesId.get((int) (Math.random() * targetablesId.size()));
                }
            }

            waitASpell(true);

            int finalSelectedRandTarget = selectedRandTarget;
            fightScreen.runOnUiThread(() -> battleTokenArray.getBattleToken(finalSelectedRandTarget).getCharLayout().performClick());

            waitASpell(false);

            fightScreen.runOnUiThread(() -> battleTokenArray.getBattleToken(finalSelectedRandTarget).getCharLayout().performClick());

            rageTargetSelect = false;
        } else{

            if(targetablesId.size() > 0){


                int initialTargetId = fightScreen.setAllCharLayoutFocusFramesInitial(statusMap);


                //largeBars update for initial
                if (initialTargetId != -1) {
                    if (!battleTokenArray.getBattleToken(initialTargetId).getIsPlayer() && isPlayer && isOffensive ||
                            battleTokenArray.getBattleToken(initialTargetId).getIsPlayer() && isPlayer && !isOffensive) {
                        fightScreen.updateLargeBars(initialTargetId, false);
                    } else {
                        fightScreen.updateLargeBars(initialTargetId, true);
                    }

                    this.clicked(initialTargetId);
                }
            }
        }
    }

    private void waitASpell(boolean longg){
        try {
            synchronized (this) {
                if(longg) {
                    this.wait(2500);
                } else {
                    this.wait(1200);
                }
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void setRaging(){
        rageTargetSelect = true;
    }

    class targetEffectsList {

//        ArrayList<Integer> untargetables = new ArrayList<>();
        boolean rampartExists = false;
        boolean engageExists = false;
        int imposerId = -1;

        ArrayList<targetEffects> targetEffectsArrayList = new ArrayList<>();


        public targetEffectsList(){}

        public targetEffectsList(ArrayList<statusEffectList.effect> statusList){

            if(battleTokenArray.hasRampart(!isPlayer)){
                rampartExists = true;
            }

//            untargetables = battleTokenArray.getUntargetablesIfExist(!isPlayer);

            for (int i = 0; i < statusList.size(); i++) {

                if (statusList.get(i).statusEffect == statusEffect.ENGAGE) {
                    engageExists = true;
                    imposerId = (int) statusList.get(i).ex;

                    // just used to update the engage status
                    battleTokenArray.updateEngageStatus(charId);
                }
                if(statusList.get(i).statusEffect != statusEffect.RAMPART &&
                        statusList.get(i).statusEffect != statusEffect.UNTARGETABLE&&
                        statusList.get(i).statusEffect != statusEffect.RAGE) {
                    targetEffectsArrayList.add(new targetEffects((int) statusList.get(i).ex, statusList.get(i).statusEffect));
                }
            }
        }

        public statusEffect getEffectIfExists(int targetId){

            if(engageExists && targetId != imposerId ||
                    (combatants.getBattleToken(currentTurnCharId).hasStatus(statusEffect.GRAPPLED) &&
                    (int) combatants.getBattleToken(currentTurnCharId).getBonusEffectExtra(statusEffect.GRAPPLED) != targetId)) {
                // returns engage as char cannot be targeted because of it
                // also grappled applies

                return statusEffect.ENGAGE;

            } else{

                if (rampartExists && attackAOE.toString().contains("ENEM") &&
                        !battleTokenArray.getBattleToken(targetId).getIsFrontLine() &&
                        battleTokenArray.getBattleToken(charId).getIsPlayer() !=
                        battleTokenArray.getBattleToken(targetId).getIsPlayer()) {
                    //returns rampart as the char cannot be targeted because of it
                    return statusEffect.RAMPART;

                } else {
                    if(battleTokenArray.getBattleToken(targetId).hasStatus(statusEffect.UNTARGETABLE)
                        || (battleTokenArray.getBattleToken(targetId).hasStatus(SWALLOWED) &&
                            (int) battleTokenArray.getBattleToken(targetId).getBonusEffectExtra(SWALLOWED) != currentTurnCharId)){
                        // char is untargetable and cannot be targeted
                        return statusEffect.UNTARGETABLE;
                    }

                    for (int i = 0; i < targetEffectsArrayList.size(); i++) {
                        if (targetEffectsArrayList.get(i).isIdValue(targetId)) {
                            return targetEffectsArrayList.get(i).status;
                        }
                    }

                    if(attackAOE == UNGRAPPLED_ALLY &&
                            (combatants.getBattleToken(targetId).hasAegisShield() ||
                                    combatants.getBattleToken(currentTurnCharId).hasAegisShield())){
                        // cant since either char has aegis shield
                        return AEGIS_SHIELD;
                    }

                    if(attackAOE == UNGRAPPLED_ALLY &&
                            combatants.getBattleToken(targetId).hasStatus(statusEffect.GRAPPLED)){
                        // cant target since target is grappled
                        return GRAPPLE;
                    }

                    if(attackAOE == GRAPPLED &&
                            (!combatants.getBattleToken(currentTurnCharId).hasStatus(statusEffect.GRAPPLE) ||
                            !combatants.getBattleToken(targetId).hasStatus(statusEffect.GRAPPLED) ||
                            (int) combatants.getBattleToken(targetId).getBonusEffectExtra(statusEffect.GRAPPLED) != currentTurnCharId)){
//                        &&
//                        (int) combatants.getBattleToken(targetId).getBonusEffectExtra(statusEffect.GRAPPLE) == currentTurnCharId

                        // cant attack because no char is grappled
                        // or char isnt grappled by user

                        return GRAPPLE_RELEASE;
                    }




                    return null;
                }
            }
        }

        //returns to see if the specified char is attackable
        public boolean isAttackable(int id){
            ArrayList<Integer> untargetables = battleTokenArray.getUntargetablesIfExist(!isPlayer);
            if(!untargetables.contains(id)) {
                statusEffect status = getEffectIfExists(id);

                if (status == statusEffect.INTIMIDATE || status == statusEffect.RAMPART || status == statusEffect.ENGAGE
                    || status == UNTARGETABLE) {
                    return false;
                } else {
                    return true;
                }
            }
            return false;
        }

        public int size(){ return targetEffectsArrayList.size();}



        private class targetEffects {

            int targetId;
            statusEffect status;

            public targetEffects(int targetId, statusEffect statusEffect) {
                this.targetId = targetId;
                status = statusEffect;
            }

            public boolean isIdValue(int id){
                return targetId == id;
            }
        }
    }
}
