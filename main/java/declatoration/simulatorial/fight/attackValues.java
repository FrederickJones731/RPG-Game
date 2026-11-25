package declatoration.simulatorial.fight;

import declatoration.simulatorial.enums.effectiveness;

class attackValues {

    private final declatoration.simulatorial.fight.actionHandler actionHandler;
    int minDMG, hitChance, critChance, blockChance, targetID;
    effectiveness weaponEff, affinityEff;

    boolean hasAegisShield;

    public attackValues(actionHandler actionHandler) {
        this.actionHandler = actionHandler;
    }

    public attackValues(actionHandler actionHandler, int dmg, int hit, int crit, int block, int targetId,
                        effectiveness weaponEff, effectiveness affinityEff, boolean hasAegisEffect) {

        this.actionHandler = actionHandler;
        hasAegisShield = hasAegisEffect;

        minDMG = dmg;
        hitChance = hit;
        critChance = crit;
        blockChance = block;
        targetID = targetId;


        float toppledMod = actionHandler.getToppledModifier();
        if(toppledMod != 1){
            if(toppledMod > 1){
                applyPositiveToppledMod();
            } else{
                applyNegativeToppledMod();
            }
        }

        if (actionHandler.hasAdvantage()) {
            applyAdvantage();
        }
        if (actionHandler.hasDisadvantage()){
            applyDisadvantage();
        }

        if(hasAegisShield){
            applyAegisEffects();
        }

        this.weaponEff = weaponEff;
        this.affinityEff = affinityEff;
    }

    public attackValues(actionHandler actionHandler, int hit, int targetId, effectiveness weaponEff, boolean hasAegisEffect) {

        this.actionHandler = actionHandler;
        hasAegisShield = hasAegisEffect;

        targetID = targetId;
        this.weaponEff = weaponEff;
        hitChance = hit;

        hitChance *= actionHandler.getToppledModifier();
        if (actionHandler.hasAdvantage()) {
            hitChance *= 1.15;
        }
        if(actionHandler.hasDisadvantage()){
            hitChance /= 1.15;
        }

        minDMG = 0;
        critChance = 0;
        blockChance = 0;
        affinityEff = effectiveness.NEUTRAL;

        if(hasAegisShield){
            applyAegisEffects();
        }
    }

    public attackValues(attackValues av) {
        targetID = av.targetID;
        actionHandler = av.actionHandler;

        minDMG = av.minDMG;
        hitChance = av.hitChance;
        critChance = av.critChance;
        blockChance = av.blockChance;

        affinityEff = av.affinityEff;
        weaponEff = av.weaponEff;
    }

    public void applyPositiveToppledMod(){
        minDMG *= 1.2;
        hitChance *= 1.3;
        critChance *= 1.3;
        blockChance /= 1.3;
    }
    public void applyNegativeToppledMod(){
        // just lowers hitcrit
        hitChance /= 1.3;
        critChance /= 1.3;
    }

    public void applyAdvantage(){
        minDMG *= 1.1;
        hitChance *= 1.15;
        critChance *= 1.15;
        blockChance /= 1.15;
    }
    public void applyDisadvantage(){
        minDMG /= 1.1;
        hitChance /= 1.15;
        critChance /= 1.15;
        blockChance *= 1.15;
    }

    public void applyChaosRift(){
        minDMG *= 1.35;
        hitChance *= 1.55;
        critChance *= 1.1;
        blockChance /= 1.15;
    }
    public void applyAegisEffects(){
        hitChance = (int) (55 + (0.45 * hitChance));
        critChance = 0;
        blockChance = 0;
    }

    public void applyStatChange(attackVal val, int degree){
        switch (val){
            case DMG:
                minDMG *= (degree * 1.35);
            case HIT:
                blockChance *= degree * 1.35;
                break;
            case CRIT:
                critChance *= degree * 1.35;
                break;
            case BLOCK:
                blockChance *= degree * 2;
                break;


        }
    }


    public enum attackVal{
        DMG,
        HIT,
        CRIT,
        BLOCK
    }
}
