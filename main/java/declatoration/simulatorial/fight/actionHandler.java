package declatoration.simulatorial.fight;


import static declatoration.simulatorial.enums.stat.ATK;
import static declatoration.simulatorial.enums.stat.AVO;
import static declatoration.simulatorial.enums.stat.BLOCK;
import static declatoration.simulatorial.enums.stat.CRIT;
import static declatoration.simulatorial.enums.stat.DEF;
import static declatoration.simulatorial.enums.stat.HIT;
import static declatoration.simulatorial.enums.stat.INT;
import static declatoration.simulatorial.enums.stat.LCK;
import static declatoration.simulatorial.enums.stat.MAG;
import static declatoration.simulatorial.enums.stat.MAGAVO;
import static declatoration.simulatorial.enums.stat.PHYSAVO;
import static declatoration.simulatorial.fight.fightBackground.actionTags;
import static declatoration.simulatorial.fight.fightBackground.clashReactionManager;
import static declatoration.simulatorial.fight.fightBackground.combatants;
import static declatoration.simulatorial.fight.fightBackground.grappleManager;
import static declatoration.simulatorial.fight.fightForeground.clashReactionMode;
import static declatoration.simulatorial.fight.fightForeground.sonicJammer;
import static declatoration.simulatorial.fight.trigger.isTriggered;

import java.util.ArrayList;
import java.util.Objects;

import declatoration.simulatorial.affinityTable;
import declatoration.simulatorial.bonusEffects;
import declatoration.simulatorial.charAction;
import declatoration.simulatorial.charSkill;
import declatoration.simulatorial.enums.affinity;
import declatoration.simulatorial.enums.damageType;
import declatoration.simulatorial.enums.dmgMods;
import declatoration.simulatorial.enums.effectiveness;
import declatoration.simulatorial.enums.stat;
import declatoration.simulatorial.enums.statusEffect;
import declatoration.simulatorial.enums.target;
import declatoration.simulatorial.weaponTable;


public class actionHandler {


    skillmaster skillmaster;
    skillLord skillLord;

    weaponTable weaponEffManager;

    // targetId is ONLY for setting attackVals
    int charId, attackValsTargetID;
    ArrayList<Integer> targetIds;

    ArrayList<attackValues> attackVals = new ArrayList<>();
    //    int minDMG, hitChance, critChance, blockChance;
    int lastDmgTaken;
    effectiveness actionEffectiveness, weaponEffectiveness;

    charAction currentAction;

    fightBackground honcho;

    static ArrayList<backgroundTaskReport> returningTasks = new ArrayList<>();


    //ToDo - divide the skills up well, so theyll be easy to use here
    // applicable for -> (dmg) -> during
    // (hit/crit, type eff) -> pre
    // (status immunities / stat raise/drop) -> post
    // but its not so simple so maybe post1, post2, and so on for different check locations


    //ToDo - getProc -> it only includes atkr and def skills when checking
    //  overarching skills are ALWAYS active, even when it isnt chars turn.
    public actionHandler(fightBackground ff) {
        honcho = ff;

        skillLord = new skillLord(combatants);
        weaponEffManager = new weaponTable();
    }

    //finds min dmg, and hitcritblock percentages.
    //safeguards EACH targets chances in attackVals, sets classes' static variables
    //todo- dmg scaling with the dmgMods enums
    public void offActionSelected(int charId1, ArrayList<Integer> targetIdsArray, charAction selectedAction1,
                                  actionBoostParams assistBonuses, boolean isStatus) {

        //things to calc HERE-
        // advantage, rattle, stat alter,
        actionBoostParams additionBonuses = new actionBoostParams();
        returningTasks = new ArrayList<>();

        currentAction = selectedAction1;
        charId = charId1;

        // bt target selection
        if(currentAction.getAffectedTarget() != target.ALL && !getChaosRiftExists(targetIdsArray)) {
            this.targetIds = targetIdsArray;
        } else {
            ArrayList<battleToken> allTokens = combatants.getActiveBattleTokens();

            this.targetIds = new ArrayList<>();

            for (int i = 0; i < allTokens.size(); i++) {
                this.targetIds.add(allTokens.get(i).getBattleSerialNum());
            }
        }

        attackVals = new ArrayList<>(targetIds.size());

        for (int h = 0; h < targetIds.size(); h++) {

            additionBonuses.setParams(assistBonuses);

            boolean hasAegisShield = getBT(targetIds.get(h)).hasAegisShield();

            if(!hasAegisShield) {
                //aegis shield gets no advantages / disadvantages

                //weapon type boost
                if (getCurrentCharBT().hasStatus(statusEffect.TURN_WEAPON_ADVANTAGE)) {
                    //Hazel adapt
                    weaponEffectiveness = effectiveness.EFFECTIVE;
                } else {
                    if (getCurrentAttackValsTargetBT().hasStatus(statusEffect.TURN_WEAPON_ADVANTAGE)) {
                        //enemy attacks hazel
                        weaponEffectiveness = effectiveness.INEFFECTIVE;
                    } else {
                        // normal
                        weaponEffectiveness = weaponEffManager.getWeaponEffectiveness(
                                getBT(charId).getWeapon(),
                                getBT(targetIds.get(h)).getWeapon());
                    }
                }

                // weapon eff bonuses
                if (!isStatus) {
                    int offStatVal;

                    if (currentAction.getIsMag()) {
                        offStatVal = (int) getStat(charId, MAG);
                    } else {
                        offStatVal = (int) getStat(charId, ATK);
                    }

                    additionBonuses.addify(weaponEffManager.getWeaponEffectivenessBonuses(weaponEffectiveness, offStatVal));

                } else {
                    additionBonuses.addify(weaponEffManager.getWeaponEffectivenessBonuses(weaponEffectiveness, 0));

                }

            }

            //skillz
            ArrayList<charSkill> procced = skillLord.proccedSkills(charId, targetIds.get(0), charSkill.skillType.HITCRIT);
            for (int i = 0; i < procced.size(); i++) {
//            bonuses.addify(procced.get(i).getSkillEffects());
            }

//            if (hasAdvantage()) {
//                // everything x1.15
//                bonuses.applyAdvantageStatus();
//            }


            //attack changing skills HERE
            //todo - add overarching skill check as well
            double skillMod = 1;
            procced = skillLord.proccedSkills(charId, targetIds.get(h), charSkill.skillType.DAMAGE);
            for (int i = 0; i < procced.size(); i++) {
                skillMod *= procced.get(i).getSkillEffects().getStatChangeDegree();
            }


            if (!isStatus) {
                //getDMG gets raw damage [(atk - def) * variance * crit / range]
                attackValsTargetID = targetIds.get(h);
                attackVals.add(new attackValues(this, (int) (skillMod * getDamageMinCharOffStat(currentAction,
                        (int) additionBonuses.attackBoost, targetIds.get(h))),
                        (int) (getHitChance(false, (int) additionBonuses.hitBoost)),
                        (int) (getCritChance((int) additionBonuses.critBoost)),
                        (int) (getBlockChance((int) additionBonuses.blockBoost)),
                        targetIds.get(h), weaponEffectiveness, actionEffectiveness, hasAegisShield));

                if(honcho.clashReactionTriggered()){
                    attackVals.set(h, clashReactionManager.preAttackClashUpdate(attackVals.get(h),
                            charId, targetIds.get(h), true));
                }

            } else {
                // only hit is important for status effects
                attackVals.add(new attackValues(this, (int) getHitChance(false, (int) additionBonuses.hitBoost), targetIds.get(h),
                        weaponEffectiveness, hasAegisShield));
            }
        }

    }

    // statuses go here
    public boolean getStatusActionHit(int targetId) {
        attackValsTargetID = targetId;

        //hit assisting skills
        ArrayList<charSkill> procced = skillLord.proccedSkills(charId, targetId, charSkill.skillType.HITCRIT);
        for (int i = 0; i < procced.size(); i++) {
//            bonuses.addify(procced.get(i).getSkillEffects());
        }

        boolean isTargetingAlly = getCurrentCharBT().getIsPlayer() == getCurrentAttackValsTargetBT().getIsPlayer();

        //evade status
        if (!isTargetingAlly && getBT(targetId).hasStatus(statusEffect.EVADE)) {
            returningTasks.add(new backgroundTaskReport(statusEffect.EVADE, null, targetId, 0, "off"));
            getBT(targetId).removeStatusIfExists(statusEffect.EVADE);
            return false;
        }

        if (isTargetingAlly || confirmHit()) {
            actionTags.append("atkrHit");

            //evade dx
            if (!isTargetingAlly && getBT(targetId).hasStatus(statusEffect.EVADE_DX)) {
                returningTasks.add(new backgroundTaskReport(statusEffect.EVADE_DX, null, targetId, 0, "off"));
                getBT(targetId).removeStatusIfExists(statusEffect.EVADE_DX);
                return false;
            }

            //attack changing skills HERE
            //todo - add overarching skill check as well
            procced = skillLord.proccedSkills(charId, targetId, charSkill.skillType.DAMAGE);
            for (int i = 0; i < procced.size(); i++) {
            }

            return true;
        } else {
            //miss
            actionTags.append("atkrMiss");
            actionTags.append("defAvoid");

            return false;
        }
    }

    // returns the final dmg for the action. Includes skill calc, actual action mod, and everything that needs to be calced.
    // heals & statuses should NOT reach here
    public int getFinalActionDmg(int targetId, float dmgMod, int defID) {
        dmgMod *= 0.5;
        attackValsTargetID = targetId;
        getCurrentCharBT().removeStatusIfExists(statusEffect.EX_ADVANTAGE);

        int damageTaken, targetDefensiveStat;

        // grapple release if attacking a char that isnt grappled
        if(getCurrentCharBT().hasStatus(statusEffect.GRAPPLE) &&
                (int) getCurrentCharBT().getBonusEffectExtra(statusEffect.GRAPPLE) != targetId){

            int grapplee = (int) getCurrentCharBT().getBonusEffectExtra(statusEffect.GRAPPLE);

            getCurrentCharBT().grappleRelease(false, false);
            combatants.getBattleToken(grapplee).grappleRelease(false, false);

            fightMidgrounds.post(getCurrentCharBT().getName(), combatants.getBattleToken(grapplee).getName(), null,
                    fightUpdater.textType.GRAPPLE_RELEASE, null, null);
        }

        boolean chaosRift = false;

        if(getBT(targetId).hasStatus(statusEffect.CHAOS_RIFT)){
            chaosRift = true;
            getBT(targetId).removeStatusIfExists(statusEffect.CHAOS_RIFT);

            ArrayList<battleToken> activeBTs = combatants.getActiveBattleTokens();

            honcho.midan.conveyVisual(statusEffect.CHAOS_RIFT, targetId);
            int newTargetId = activeBTs.get((int) (activeBTs.size() * Math.random())).getBattleSerialNum();
            attackValsTargetID = newTargetId;
            honcho.setCurrentTargetBT(newTargetId);

            fightMidgrounds.post(getBT(targetId).getName(), getBT(newTargetId).getName(), null,
                    fightUpdater.textType.CHAOS_RIFT, null, null);

            targetId = newTargetId;

            getAttackValues(targetId, false).applyChaosRift();

            sonicJammer.playSound("chaos_rift", combatants.ff.getBaseContext());

        }

        // cannot defend if chaos rift was triggered
        // defID is -1, unless char is being defended
        if (chaosRift || defID == -1) {

            boolean hasAegisShield = getBT(targetId).hasAegisShield();
            boolean hasInfiltrate = getCurrentCharBT().hasStatus(statusEffect.INFILTRATE);

            if(hasAegisShield && hasInfiltrate){
                getBT(targetId).cancelAegisShield();
                hasAegisShield = false;

                int shieldBreakDMG = getBT(targetId).getHPMax() / 4;
                honcho.actuateDamage(targetId, shieldBreakDMG);

                fightMidgrounds.post(getCurrentCharBT().getName(), getBT(targetId).getName(), null,
                        fightUpdater.textType.AEGIS_SHIELD_INFILTRATED, shieldBreakDMG, null);
            }

            if(!hasAegisShield || chaosRift) {

                if(honcho.clashReactionTriggered()) {
                    setAttackVals(clashReactionManager.preAttackClashUpdate(getAttackValues(targetId, false),
                            charId, targetId, false), targetId);
                }

                // chaos rift ignores aegis shield, but doesnt break it
                boolean hasEvadeStatus = getBT(targetId).hasStatus(statusEffect.EVADE);
//            setAttackVals(clashReactionManager.preAttackClashUpdate(getAttackValues(targetId), charId, targetId, false),
//                    targetId);

                // infiltrate breaks through evade
                if ((!hasEvadeStatus || hasInfiltrate)
                        && confirmHit()) {
                    actionTags.append("atkrHit");

                    // infiltrate still breaks through evade dx
                    if (!getBT(targetId).hasStatus(statusEffect.EVADE_DX) ||
                            (getBT(targetId).hasStatus(statusEffect.EVADE_DX) &&
                                    getCurrentCharBT().hasStatus(statusEffect.INFILTRATE))) {

                        getBT(targetId).removeStatusIfExists(statusEffect.EVADE_DX);

                        //dmg range x1 - x1.15
                        double mod = 1 + (Math.random() * 0.1);

                        if (chaosRift) {
                            // undo range diff
                            mod *= (1 + 2 * rangeify(currentAction.getRange()));
                            // makes attack stronger too
                            mod *= 1.5;
                        }


                        if ((!currentAction.getIsMag() && !currentAction.hasBonusEffect(statusEffect.FLIP_DEF)) ||
                                (currentAction.getIsMag() && currentAction.hasBonusEffect(statusEffect.FLIP_DEF))) {

                            actionTags.append("phys");

                            if (confirmCrit()) {
                                actionTags.append("atkrCrit");
                                mod *= 1.18;
                            }

                            targetDefensiveStat = getTargetDefStat(true, targetId);

                        } else {

                            actionTags.append("mag");

                            if (confirmCrit()) {
                                actionTags.append("atkrCrit");
                                mod *= 1.18;
                            }

                            targetDefensiveStat = getTargetDefStat(false, targetId);
                        }


                        // the mod is only used up on hit, plus is included in calcs before here
                        getCurrentCharBT().removeStatusIfExists(statusEffect.EX_ATTACK_MOD);

                        damageTaken = (int) ((getAttackValues(attackValsTargetID, false).minDMG * mod)
                                - targetDefensiveStat);

                        damageTaken *= dmgMod;

                        if (!currentAction.hasBonusEffect(statusEffect.UNBLOCKABLE) &&
                                confirmBlock()) {
                            actionTags.append("defBlock");
                            damageTaken *= 0.5;
                        }

                        if (damageTaken < 0) {
                            damageTaken = 1;
                        }
                    } else {
                        //evade DX
                        damageTaken = -2;
                        getBT(targetId).removeStatusIfExists(statusEffect.EVADE_DX);
                    }
                } else {
                    actionTags.append("atkrMiss");
                    actionTags.append("defAvoid");

                    if (hasEvadeStatus && !hasInfiltrate) {
                        getBT(targetId).removeStatusIfExists(statusEffect.EVADE);
                        damageTaken = -2;
                    } else {
                        damageTaken = -1;
                    }

                }
            } else{
                // aegis shield triggers

                //dmg range x1 - x1.15
                double mod = 1 + (Math.random() * 0.1);
                // the mod is only used up on hit, plus is included in calcs before here
                getCurrentCharBT().removeStatusIfExists(statusEffect.EX_ATTACK_MOD);

                damageTaken = (int) (getAttackValues(attackValsTargetID, false).minDMG * mod);

                damageTaken *= dmgMod;

            }
        } else {
            // defended by another char.
            // defending char takes half of dmg as if he was attacked directly
            // cannot avoid attack though. CAN block

            targetId = defID;


//            setAttackVals(clashReactionManager.preAttackClashUpdate(getAttackValues(targetId), charId, targetId, false),
//                    targetId);

            //dmg range x1 - x1.15
            double mod = 1 + (Math.random() * 0.15);

            if (!currentAction.getIsMag()) {

                actionTags.append("phys");

                if (confirmCrit()) {
                    actionTags.append("atkrCrit");
                    mod *= 1.15;
                }
                targetDefensiveStat = (int) (getStat(targetId, DEF) * getBT(targetId).getBoostModifier(DEF));

                if (currentAction.hasBonusEffect(statusEffect.FLIP_DEF)) {
                    targetDefensiveStat = (int) (getStat(targetId, INT) * getBT(targetId).getBoostModifier(INT));
                }

            } else {

                actionTags.append("mag");

                if (confirmCrit()) {
                    actionTags.append("atkrCrit");
                    mod *= 1.15;
                }

                targetDefensiveStat = (int) (getStat(targetId, INT) * getBT(targetId).getBoostModifier(INT));

                if (currentAction.hasBonusEffect(statusEffect.FLIP_DEF)) {
                    targetDefensiveStat = (int) (getStat(targetId, DEF) * getBT(targetId).getBoostModifier(DEF));
                }
            }


            // the mod is only used up on hit, plus is included in calcs before here
            getCurrentCharBT().removeStatusIfExists(statusEffect.EX_ATTACK_MOD);

            damageTaken = (int) ((getAttackValues(attackValsTargetID, false).minDMG * mod) - targetDefensiveStat);

            // for defend effect this is halved
            damageTaken *= dmgMod * 0.5;

            if (!currentAction.hasBonusEffect(statusEffect.UNBLOCKABLE) &&
                    confirmBlock()) {
                actionTags.append("defBlock");
                damageTaken *= 0.5;
            }

            if (damageTaken < 0) {
                damageTaken = 1;
            }

        }

//        float dmgSkillBonus = 0;
//        if(damageTaken != -1) {
//            dmgSkillBonus = skillmaster.getDmgSkills(charId, targetId, currentAction.getIsMag()).attackBoost;
//        }
        // upon being attacked, regain x0.5 turn SPRegen
        getBT(targetId).turnRegenSP(0.5f);

        lastDmgTaken = damageTaken;
        fightForeground.setDmgTaken(damageTaken);

        return damageTaken;
    }

    //chain attack ver.
    public int getFinalActionDmg(int targetId, charAction action) {
        currentAction = action;

        attackValsTargetID = targetId;
        getCurrentCharBT().removeStatusIfExists(statusEffect.EX_ADVANTAGE);

        int damageTaken, targetDefensiveStat;


        actionTags.append("atkrHit");

        //dmg range x1 - x1.15
        double mod = 1 + (Math.random() * 0.15);
        // undo range dif
        mod *=  (1 + 2 * rangeify(currentAction.getRange()));
        mod *= 0.5;

        if (!currentAction.getIsMag()) {

//                        actionTags.append("phys");

            if (confirmCrit()) {
                actionTags.append("atkrCrit");
                mod *= 1.15;
            }
            targetDefensiveStat = (int) (getStat(targetId, DEF) * getBT(targetId).getBoostModifier(DEF));

            if (currentAction.hasBonusEffect(statusEffect.FLIP_DEF)) {
                targetDefensiveStat = (int) (getStat(targetId, INT) * getBT(targetId).getBoostModifier(INT));
            }

        } else {

            actionTags.append("mag");

            if (confirmCrit()) {
                actionTags.append("atkrCrit");
                mod *= 1.15;
            }

            targetDefensiveStat = (int) (getStat(targetId, INT) * getBT(targetId).getBoostModifier(INT));

            if (currentAction.hasBonusEffect(statusEffect.FLIP_DEF)) {
                targetDefensiveStat = (int) (getStat(targetId, DEF) * getBT(targetId).getBoostModifier(DEF));
            }
        }



        int minDMG = getDamageMinCharOffStat(currentAction,
                0, targetId);
        damageTaken = (int) (minDMG * mod) - targetDefensiveStat;

        damageTaken *= 0.4;


        if (damageTaken < 0) {
            if(actionEffectiveness == effectiveness.IMMUNE){
                damageTaken = 0;
            } else {
                damageTaken = 1;
            }
        }


//        float dmgSkillBonus = 0;
//        if(damageTaken != -1) {
//            dmgSkillBonus = skillmaster.getDmgSkills(charId, targetId, currentAction.getIsMag()).attackBoost;
//        }
        // upon being attacked, regain x0.5 turn SPRegen
        getBT(targetId).turnRegenSP(0.5f);

        lastDmgTaken = damageTaken;
        fightForeground.setDmgTaken(damageTaken);

        return damageTaken;
    }


    //returns the base attack stat, before dmg disparity and enemy's def
    private int getDamageMinCharOffStat(charAction charAction, int attackBoost, int targetId) {

        double mod = 1;

        // mod of 1, 1/3, or 1/5
        mod *= (1.0 / (1 + 2 * rangeify(charAction.getRange())));


        double offStat;
        float baseOffStat = 1;


        affinity actionAffinity = charAction.getAffinity();

        // STAB
        if (actionAffinity == getBT(charId).getAffinity()) {
            baseOffStat *= 1.2;
            actionTags.append("STAB");
        }

        actionEffectiveness = affinityTable.getEffectiveness(actionAffinity, getBT(targetId).getAffinity());
        if(getCurrentAttackValsTargetBT().hasAegisShield()){
            actionEffectiveness = effectiveness.NEUTRAL;
        }

        switch (actionEffectiveness) {
            case NEUTRAL:
                break;
            case EFFECTIVE:
                baseOffStat *= 1.5;
                actionTags.append("affEff");
                break;
            case INEFFECTIVE:
                baseOffStat *= 0.67;
                actionTags.append("affIneff");
                break;
            case IMMUNE:
                baseOffStat *= 0;
                actionTags.append("affImmune");
        }
//        fightForeground.setMoveEffectiveness(actionEffectiveness);


        if (getCurrentCharBT().hasStatus(statusEffect.EX_ATTACK_MOD)) {
            baseOffStat *= (float) (getCurrentCharBT().getBonusEffectExtra(statusEffect.EX_ATTACK_MOD));
        }

        if(currentAction.hasBonusEffect(statusEffect.DMG_SCALING)){
            bonusEffects be = currentAction.getBonusEffect(statusEffect.DMG_SCALING);

            float maxVal = be.getStatChangeDegree();
            dmgMods dmgMod = dmgMods.valueOf(be.getTriggerCondition());

            float dmgChangeVal = combatants.getScalingMod(dmgMod, maxVal, charId, targetId);
            baseOffStat *= dmgChangeVal;
        }

        if(!charAction.hasBonusEffect(statusEffect.COMBINE_OFF_STATS)) {
            if (charAction.getIsMag()) {
                baseOffStat *= ((int) getStat(charId, MAG));
                baseOffStat *= getCurrentCharBT().getBoostModifier(MAG);


                baseOffStat += attackBoost;

                offStat = (baseOffStat * charAction.getDamageMod());

            } else {
                baseOffStat *= (int) (getStat(charId, ATK));
                baseOffStat *= getCurrentCharBT().getBoostModifier(ATK);

                baseOffStat += attackBoost;

                offStat = (baseOffStat * charAction.getDamageMod());

            }
        } else{
            // combines ATK and MAG for dmg. Ekrunne Ignaris
            double holder = baseOffStat;

            baseOffStat *= ((int) getStat(charId, MAG));
            baseOffStat *= getCurrentCharBT().getBoostModifier(MAG);

            holder *= (int) (getStat(charId, ATK));
            holder *= getCurrentCharBT().getBoostModifier(ATK);

            baseOffStat += holder;
            baseOffStat += attackBoost;

            offStat = (baseOffStat * charAction.getDamageMod());
        }

        if (offStat < 0) {
//            actionTags.append("noDmg");
            offStat = 1;
        }

        if (actionEffectiveness != effectiveness.IMMUNE) {
            return (int) (mod * offStat);
        } else{
            return 0;
        }
    }
    public int getTargetDefStat(boolean isPhysical, int targetId){

        int targetDefensiveStat;

        if(!getBT(targetId).hasAegisShield()) {
            stat defStat;
            if (isPhysical) {
                defStat = DEF;
            } else {
                defStat = INT;
            }

            float intBoostMod = getBT(targetId).getBoostModifier(defStat);

            if (getCurrentCharBT().hasStatus(statusEffect.INFILTRATE) && intBoostMod >= 1) {
                targetDefensiveStat = (int) (getStat(targetId, defStat));
            } else {
                targetDefensiveStat = (int) (getStat(targetId, defStat) * intBoostMod);
            }

            targetDefensiveStat *= getStatAlterMod(defStat);
        } else{
            // aegis shield has 0 DEF & INT
            targetDefensiveStat = 0;
        }

        return targetDefensiveStat;
    }


//    private int getDamage(charAction charAction, actionBoostParams bonuses, boolean isPreview) {
//
//        double mod;
//        mod = (float) (1 + (Math.random() * 0.15));
//
//        // mod of 1, 1/3, or 1/5
//        mod *= (1.0 / (1 + 2 * rangeify(charAction.getRange())));
//
//        double dmg;
//        float baseOffStat = 1;
//
//
//        affinity actionAffinity = charAction.getAffinity();
//
//        // STAB
//        if(actionAffinity == getBT(charId).getAffinity()){
//            baseOffStat *= 1.2;
//            actionTags.append("STAB");
//        }
//
//        effectiveness actionEffectiveness = affinityTable.getEffectiveness(actionAffinity, getBT(targetId).getAffinity());
//
//        switch (actionEffectiveness) {
//            case NEUTRAL:
//                break;
//            case EFFECTIVE:
//                baseOffStat *= 1.5;
//                actionTags.append("affEff");
//                break;
//            case INEFFECTIVE:
//                baseOffStat *= 0.67;
//                actionTags.append("affIneff");
//                break;
//            case IMMUNE:
//                baseOffStat *= 0;
//                actionTags.append("affImmune");
//        }
//        fightForeground.setMoveEffectiveness(actionEffectiveness);
//
//
//        if (charAction.getIsMag()) {
//            baseOffStat *= ((int) getStat(charId, MAG));
//            baseOffStat *= getCurrentCharBT().getBoostModifier(MAG);
//
//            if(getCurrentCharBT().hasStatus(statusEffect.EX_ATTACK_MOD)){
//                baseOffStat *= (Float) getCurrentCharBT().getBonusEffectExtra(statusEffect.EX_ATTACK_MOD, isPreview);
//            }
//
//            baseOffStat += bonuses.attackBoost;
//            actionTags.append("mag");
//
//
//
//            if (confirmCrit() && !isPreview) {
//                baseOffStat *= 1.25;
//                actionTags.append("atkrCrit");
//            }
//
//
//            float targetDefensiveStat = getStat(targetId, INT) * getBT(targetId).getBoostModifier(INT);
//
//            if(currentAction.hasBonusEffect(statusEffect.FLIP_DEF)){
//              targetDefensiveStat = getStat(targetId, DEF) * getBT(targetId).getBoostModifier(DEF);
//            }
//
//            dmg = (baseOffStat * charAction.getDamageMod() - targetDefensiveStat);
//
//        } else {
//            baseOffStat *= (int) (getStat(charId, ATK));
//            baseOffStat *= getCurrentCharBT().getBoostModifier(ATK);
//
//            if(getCurrentCharBT().hasStatus(statusEffect.EX_ATTACK_MOD)){
//                baseOffStat *= (Float) getCurrentCharBT().getBonusEffectExtra(statusEffect.EX_ATTACK_MOD, isPreview);
//            }
//
//            baseOffStat += bonuses.attackBoost;
//            actionTags.append("phys");
//
//
//            if (confirmCrit() && !isPreview) {
//                baseOffStat *= 1.45;
//                actionTags.append("atkrCrit");
//            }
//
//
//            float targetDefensiveStat = getStat(targetId, DEF) * getBT(targetId).getBoostModifier(DEF);
//
//            if(currentAction.hasBonusEffect(statusEffect.FLIP_DEF)){
//                targetDefensiveStat = getStat(targetId, INT) * getBT(targetId).getBoostModifier(INT);
//            }
//
//            dmg = (baseOffStat * charAction.getDamageMod() - targetDefensiveStat);
//
//        }
//        if (dmg < 0) {
//            actionTags.append("noDmg");
//            dmg = 0;
//        }
//
//        return (int) (mod * dmg);
//    }


    public double getHitChance(boolean ignoreRange, int hitBonus) {
        double hitP;
        float exMod = 1;
        if (getCurrentAttackValsTargetBT().hasStatus(statusEffect.EX_AVO_MOD)) {
            //def ex_avo mod
            exMod = (float) getCurrentAttackValsTargetBT().getBonusEffectExtra(statusEffect.EX_AVO_MOD);
        }

        float avoStat, avoMod = getBT(attackValsTargetID).getBoostModifier(AVO);
        if (currentAction.getIsMag()) {
             avoStat = getStat(attackValsTargetID, MAGAVO)  * exMod;
            if(!getCurrentCharBT().hasStatus(statusEffect.INFILTRATE) || avoMod < 1){
                avoStat *= avoMod;
            }
            hitP = (getStat(charId, HIT) * currentAction.getHitMod() * getCurrentCharBT().getBoostModifier(HIT)
                    - avoStat);
        } else {
            avoStat = getStat(attackValsTargetID, PHYSAVO)  * exMod;
            if(!getCurrentCharBT().hasStatus(statusEffect.INFILTRATE) || avoMod < 1){
                avoStat *= avoMod;
            }
            hitP = (getStat(charId, HIT) * currentAction.getHitMod() * getCurrentCharBT().getBoostModifier(HIT)
                    - avoStat);
        }

        hitP += getBT(charId).getBaseLCK() - getBT(attackValsTargetID).getBaseLCK() + hitBonus;

        if (!ignoreRange) {
            int rangePenalty;
            switch (rangeify(currentAction.getRange())) {
                case 2:
                    rangePenalty = 40;
                    break;
                case 1:
                    rangePenalty = 15;
                    break;
                case 0:
                default:
                    rangePenalty = 0;
            }

            hitP -= rangePenalty;
        }

        if (hitP < 0) {
            hitP = 0;
        } else if (hitP > 100) {
            hitP = 100;
        }
        return hitP;
    }
    public boolean confirmHit() {
        //ulterior hit calc method

//        if(isMagAttack) {
//            hitP = (getSttargetId, "magavo")) / (getStat(charId, "hit")) * 100;
//        } else{
//            hitP = (getSttargetId, "physavo")) / (getStat(charId, "hit")) * 100;
//        }
//        hitP *= currentAction.getHitMod();

        if (currentAction.getTargetsAlly()) {
            actionTags.append("targetsAlly");
            return true;
        } else {
            return percentagize(getAttackValues(attackValsTargetID, false).hitChance);
        }
    }

    // phys crits do more than mag crits (x1.45 vs x1.25)
    public double getCritChance(int critBoost) {
        double critP;

        // todo - this doesnt work fix it to read from clashReactions not battleToken
        if (getBT(charId).hasStatus(statusEffect.ENERVATE) || getBT(attackValsTargetID).hasStatus(statusEffect.UNCRITTABLE)) {
            critP = 0;
        } else {
            critP = ((getStat(charId, CRIT)) * currentAction.getCritMod() * getCurrentCharBT().getBoostModifier(CRIT)
                    - 0.5 * getStat(attackValsTargetID, LCK) * getBT(attackValsTargetID).getBoostModifier(LCK));
        }
        critP += critBoost;

        if (critP < 0) {
            critP = 0;
        } else if (critP > 100) {
            critP = 100;
        }

        return critP;
    }
    public boolean confirmCrit() {
        boolean res;
        if(getCurrentCharBT().hasStatus(statusEffect.EX_CRIT)){
            res = true;
            getCurrentCharBT().removeStatusIfExists(statusEffect.EX_CRIT);
        } else {
            res = (percentagize(getAttackValues(attackValsTargetID, false).critChance) ||
                    currentAction.hasBonusEffect(statusEffect.ALWAYS_CRIT));
        }
        fightForeground.setIsCrit(res);
        return res;
    }

    public double getBlockChance(int blockNerf) {
        if (!currentAction.hasBonusEffect(statusEffect.UNBLOCKABLE)) {
            double per;
            if (!getBT(attackValsTargetID).getIsGuardActioning()) {
                if (getCurrentAttackValsTargetBT().hasStatus(statusEffect.ENERVATE)) {
                    per = -100;
                } else {
                    float blockMod = getCurrentCharBT().getBoostModifier(BLOCK);
                    if(getCurrentCharBT().hasStatus(statusEffect.INFILTRATE) && blockMod >= 1){
                        per = getStat(attackValsTargetID, BLOCK) - getStat(charId, LCK) * 0.4;
                    } else{
                        per = getStat(attackValsTargetID, BLOCK) * blockMod
                                - getStat(charId, LCK) * 0.4;
                    }
                }
            } else {
                per = 200;
            }
            per -= blockNerf;

            if (per < 0) {
                per = 0;
            } else if (per > 100) {
                per = 100;
            }
            return per;
        } else {
            return 0;
        }
    }
    public boolean confirmBlock() {
        boolean res;

        if (!currentAction.hasBonusEffect(statusEffect.UNBLOCKABLE)) {
            if (!getBT(attackValsTargetID).getIsGuardActioning()) {
                res = percentagize(getAttackValues(attackValsTargetID, false).blockChance);
            } else {
                res = true;
            }
        } else {
            res = false;
        }

        fightForeground.setIsBlock(res);

        return res;
    }


// other stuff

    public attackValues getAttackValues(int targetId, boolean calcDefStat) {

        attackValues res = new attackValues(this);

        for (int i = 0; i < attackVals.size(); i++) {
            if (attackVals.get(i).targetID == targetId) {
                res = new attackValues(attackVals.get(i));
                break;
            }
        }

        if(calcDefStat){

            boolean getTargetDefStat = !currentAction.getIsMag();

            if (currentAction.hasBonusEffect(statusEffect.FLIP_DEF)){
                getTargetDefStat = !getTargetDefStat;
            }

            res.minDMG -=  getTargetDefStat(getTargetDefStat , targetId);
            res.minDMG *= 0.5;

            if(Objects.equals(currentAction.getDamageType(), damageType.MAG) ||
                    Objects.equals(currentAction.getDamageType(), damageType.PHYS) ) {

                if (res.minDMG < 1) {
                    res.minDMG = 1;
                }

            } else{
                res.minDMG = 0;
            }
        }

        return res;
    }
    public void setAttackVals(attackValues vals, int targetId){
        for (int i = 0; i < attackVals.size(); i++) {
            if (attackVals.get(i).targetID == targetId) {
                attackVals.set(i, vals);
                break;
            }
        }
    }

    public float getStat(int id, stat stat) {
        return combatants.getBattleToken(id).getStatFloat(stat);
    }

    public static boolean percentagize(double percentage) {
        return Math.random() * 100 <= percentage;
    }

    public int rangeify(ArrayList<Integer> attackRange) {

        if (attackRange.size() != 0) {
            int range = 1;
            if (!getCurrentCharBT().getIsFrontLine()) {
                range++;
            }
            if (!getCurrentAttackValsTargetBT().getIsFrontLine()) {
                range++;
            }
            int temp = range;

            range -= attackRange.get(0);
            temp -= attackRange.get(attackRange.size() - 1);

            int min = Math.min(Math.min((Math.abs(range)), (Math.abs(temp))), Math.abs(range + temp));

            if (combatants.rowCollapsed(true) && min > 0) {
                min -= 1;
            }
            if (combatants.rowCollapsed(false) && min > 0) {
                min -= 1;
            }

            return min;
        } else {
            return 0;
        }
    }


    public float getToppledModifier(){
        // grappled / swallowed is considered toppled
        if( getCurrentAttackValsTargetBT().hasStatus(statusEffect.TOPPLE)
            || getCurrentAttackValsTargetBT().hasStatus(statusEffect.GRAPPLED)
            || getCurrentAttackValsTargetBT().hasStatus(statusEffect.SWALLOWED)){
            if(rangeify(currentAction.getRange()) > 1){
                return (float) (1 / 1.3);
            } else{
                return 1.3f;
            }
        } else{
            return 1;
        }
    }
    public boolean hasAdvantage() {
        boolean res = false;
        if (getCurrentCharBT().hasStatus(statusEffect.EX_ADVANTAGE) ||
                getCurrentCharBT().hasStatus(statusEffect.ADVANTAGE_DX)) {
            res = true;
        } else {
            if (currentAction.hasBonusEffect(statusEffect.ADVANTAGE)) {
                bonusEffects be = currentAction.getBonusEffect(statusEffect.ADVANTAGE);
                if (be.hasTriggerCondition && isTriggered(be.getTriggerCondition())) {
                    res = true;
                }
            } else {

                if (getCurrentAttackValsTargetBT().hasStatus(statusEffect.BANE) && currentAction.getAffinity() == affinity.VAMP) {
                    res = true;
                } else{
                    duo curDuo = grappleManager.getDuoIfExists(charId);
                    if(curDuo != null){
                        if(curDuo.getInitiatorId() == charId &&
                                Objects.equals(curDuo.getTargetId(), getCurrentAttackValsTargetBT().getBattleSerialNum())){
                            // grappler has advantage
                            res = true;
                        } else{
                            if(Objects.equals(curDuo.getInitiatorId(), getCurrentAttackValsTargetBT().getBattleSerialNum())
                                && curDuo.getTargetId() != charId){
                                // advantage on a grappling char (who ISNT grappling YOU)
                                res = true;
                            }

                        }
                    }
                }
            }
        }

        return res;
    }
    public boolean hasDisadvantage() {
        boolean res = false;
        if (getCurrentCharBT().hasStatus(statusEffect.DISADVANTAGE_DX) ||
                getCurrentCharBT().hasStatus(statusEffect.EX_DISADVANTAGE)) {
            res = true;
        } else {
            if (currentAction.hasBonusEffect(statusEffect.DISADVANTAGE)) {
                bonusEffects be = currentAction.getBonusEffect(statusEffect.DISADVANTAGE);
                if (be.hasTriggerCondition && isTriggered(be.getTriggerCondition())) {
                    res = true;
                }
            } else if(getCurrentCharBT().hasStatus(statusEffect.GRAPPLED)){
                // being grappled gives disadvantage
                res = true;
            }
        }

        return res;
    }


    public battleToken getBT(Integer id) {
        return combatants.getBattleToken(id);
    }
    public battleToken getCurrentCharBT() {
        return getBT(charId);
    }
    public battleToken getCurrentAttackValsTargetBT() {
        return getBT(attackValsTargetID);
    }

    public boolean getChaosRiftExists(ArrayList<Integer> targetIds){
        for (int i = 0; i < targetIds.size(); i++) {
            if(getBT(targetIds.get(i)).hasStatus(statusEffect.CHAOS_RIFT)){
                return true;
            }
        }

        return false;
    }


    public int getLastDmgTaken() {
        return lastDmgTaken;
    }

    public boolean getProc(charSkill skill) {
        boolean proc = true;

        ArrayList<String> reqTags = skill.getTags();
        for (int i = 0; i < reqTags.size(); i++) {
            if (!actionTags.toString().contains(reqTags.get(i))) {
                proc = false;
                break;
            }
        }

        return proc;
    }

    public float getStatAlterMod(stat statroid){
        float mod = 1f;

        bonusEffects be = currentAction.getBonusEffect(statusEffect.STAT_ALTER);

        if(be != null && statroid == be.getChangingStat()){
            mod = be.getStatChangeDegree();
        }

        return mod;
    }


    // does the background stuff here, and sends back fightBackground a list of things to do.
    // background not only applies rowChanges, heals, and chainAttacks,
    // it also sends out toasts
    public void enactActionStatuses(int charId, charAction selectedAction,
                                    ArrayList<Integer> hitTargets) {

        for (int j = 0; j < hitTargets.size(); j++) {
            // DOES ONE TARGET AT A TIMEEEEE

            ArrayList<bonusEffects> proccedPostSkillsBonusEffects = new ArrayList<>();
            ArrayList<charSkill> procced = skillLord.proccedSkills(charId, hitTargets.get(j),
                    charSkill.skillType.POST);

            for (int i = 0; i < procced.size(); i++) {
                //only gets PROCCED skills, calc is made before
                proccedPostSkillsBonusEffects.add(procced.get(i).getSkillEffects());
            }


            // if there are any possible bonus effects, whether from action or skills
            if (selectedAction.hasBonusEffects() || !(proccedPostSkillsBonusEffects.isEmpty()) ||
                clashReactionMode != 0) {

                ArrayList<bonusEffects> bonusEffectsArrayList = selectedAction.getBonusEffects();
                bonusEffectsArrayList.addAll(proccedPostSkillsBonusEffects);

//                bonusEffectsArrayList.addAll(getClashReactionBonusEffect(hitTargets));

                //this is a big boi
                for (int i = 0; i < bonusEffectsArrayList.size(); i++) {
                    statusProcedure(bonusEffectsArrayList.get(i), hitTargets.get(j));
                    actionTags.append(hitTargets.get(j)).append(bonusEffectsArrayList.get(i).getStatus().toString());
                }

            }
        }

    }

    private ArrayList<bonusEffects> getClashReactionBonusEffect(ArrayList<Integer> hitTargets) {
        ArrayList<bonusEffects> clashEffects = new ArrayList<>();

        bonusEffects bunos = new bonusEffects();

        for (int i = 0; i < hitTargets.size(); i++) {
            if(honcho.clashReactionTriggered()){

                ArrayList<bonusEffects> be =
                        bunos.getBonusEffectFromClashEffect(clashReactionManager.getClashReactions(getCurrentCharBT().getAffinity(),
                                getBT(hitTargets.get(i)).getAffinity()));

                clashEffects.addAll(be);
            }
        }

        return clashEffects;
    }

    // deals with all the Status effects as they should be, and adds them to returningTasks for fightBackground
    public void statusProcedure(bonusEffects cur, int targetId) {

        int curTargetId;

        if (cur.triggeredViaPercent()) {

            //calcs targets
            switch (cur.getStatusTarget()) {
                case SELF:
                    curTargetId = charId;
                    break;

                case TARGET:
                default:
                    curTargetId = targetId;
                    break;

                case RANDOM_TARGET:
                    ArrayList<battleToken> enemyBTs = combatants.getActiveBattleTokens(!getCurrentCharBT().getIsPlayer());
                    int rand = (int) (Math.random() * enemyBTs.size());

                    for (int i = 0; i < 5; i++) {
                        if (rand != targetId) {
                            break;
                        }
                        rand = (int) (Math.random() * enemyBTs.size());
                    }

                    if (enemyBTs.size() > 0) {
                        curTargetId = enemyBTs.get(rand).getBattleSerialNum();
                    } else {
                        curTargetId = -1;
                    }
                    break;

            }

            // deals with respective statuses
            // ONLY things that specifically need fightBackground are listed.
            // others are dealt with within combatants
            if (curTargetId != -1) {
                switch (cur.getStatus()) {

                    case INSTAKILL:
                    case ELEMENTAL_BURST:
                    case RUNE_BURST:
                    case DIVINE_BURST:
                    case RECOIL:
                    case METAMORPH:
                    case ATK_HEAL:
                    case MOVE_NEXT:
                    case RELOAD:
                    case BALM:
                    case HEAL:

                    case GRAPPLE:
                    case SWALLOW:
                    case GRAPPLE_RELEASE:

                        // NEEDS TO BE DONE IN MAIN, WITH CHAR'S STATS AND STUFF
                        returningTasks.add(new backgroundTaskReport(cur.getStatus(), null,
                                curTargetId, cur.getStatChangeDegree(), null));
                        break;

                    case CHAIN_ATTACK:
                        ArrayList<Integer> chainTargets = getChainAttackTargets(Math.round(cur.getStatChangeDegree()), targetId);
                        for (int i = 0; i < chainTargets.size(); i++) {
                            returningTasks.add(new backgroundTaskReport(cur.getStatus(), null,
                                    chainTargets.get(i), 0.4f, null));
                        }
                        break;

                    default:
                        if(!getBT(targetId).hasStatus(statusEffect.IMMUNITY) ||
                                !statusEffectList.negativeStatusEffects.contains(cur.getStatus())) {
                            // only apply status if char doesnt have immunity, or if is not a negative status
                            combatants.applyEffect(cur, charId, targetId, currentAction.getName());
                        } else{
                            fightMidgrounds.post(getBT(charId).getName(), getBT(targetId).getName(), cur.getStatus().toString(),
                                    fightUpdater.textType.IMMUNE, null, null);
                            sonicJammer.playSound("immune", combatants.ff.getBaseContext());
                        }
                }
            }
        }
    }

    private ArrayList<Integer> getChainAttackTargets(int chainAmount, Integer originalTargetId) {
        ArrayList<Integer> targetables = combatants.findTargetableSerialNums(charId, target.ALL_ENEMIES);
        ArrayList<Integer> chainTargets = new ArrayList<>();

        targetables.remove(originalTargetId);

        for (int i = 0; i < chainAmount && i < targetables.size() + i; i++) {
            int rand = (int) (Math.random() * targetables.size());
            chainTargets.add(targetables.get(rand));
            targetables.remove(rand);
        }
        return chainTargets;

    }


    public void resetReturningTasks() {
        returningTasks = new ArrayList<>();
    }


    public static class backgroundTaskReport {

        statusEffect statusEffect;
        Integer statusTarget, statusLength;
        float mod;

        String annex;

        public backgroundTaskReport(statusEffect statusEffect, Integer statusLength, Integer target,
                                    float mod, String annex) {
            this.statusEffect = statusEffect;
            this.statusLength = statusLength;
            this.statusTarget = target;
            this.mod = mod;
            this.annex = annex;
        }
    }


}
