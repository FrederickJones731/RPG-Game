package declatoration.simulatorial.fight;

import static declatoration.simulatorial.fight.fightForeground.sonicJammer;

import android.view.View;
import android.widget.Toast;

import declatoration.simulatorial.enums.damageType;
import declatoration.simulatorial.enums.effectiveness;
import declatoration.simulatorial.enums.moveType;
import declatoration.simulatorial.enums.stat;
import declatoration.simulatorial.enums.statusEffect;

//finds dmg, does visuals & sfx, for single iteration.
// sends slagveld on to deal with dmg. does all sfx though
public class fightMidgrounds {

    private final fightForeground mushroomPeaks;
    private effectiveness affEff;
    Runnable run;

    static fightUpdater battleLog;

    public fightMidgrounds(fightForeground ff, fightUpdater log) {
        mushroomPeaks = ff;

        battleLog = log;
        battleLog.setFightForeground(ff);

        run = () -> sonicJammer.playSound("tap", mushroomPeaks.getBaseContext());
    }

//    public Thread enactSfxify(boolean koed) {
//        sfxify visuals = new sfxify(koed);
//        Thread thread = new Thread(visuals);
//        thread.start();
//
//        return thread;
//    }
    public Thread enactSfxify(boolean koed, Integer dmgVal, boolean printToLog, String printAffType, int targetId) {
        sfxify visuals = new sfxify(koed, dmgVal, printToLog, printAffType, targetId);
        Thread thread = new Thread(visuals);
        thread.start();

        return thread;
    }

    public class sfxify implements Runnable {

        private final boolean koed;
        private Integer dmg, targetId;
        private final boolean print;
        private final String printAff;

//        public sfxify(boolean koed) {
//            this.koed = koed;
//            print = true;
//        }

        public sfxify(boolean koed, Integer dmg, boolean print, String printAffType, int targetId) {
            this.koed = koed;
            this.dmg = dmg;
            this.print = print;
            printAff = printAffType;
            this.targetId = targetId;
        }

        @Override
        public void run() {

            int dmgVal;
            if(this.dmg == null){
                dmgVal = fightForeground.dmgTaken;
            } else{
                dmgVal = dmg;
            }

            if (dmgVal >= 0) {

                if(print){
                    mushroomPeaks.damageNums(dmgVal, koed, affEff, printAff, targetId);
                }

                //sfx
                if (fightForeground.isCrit) {
                    sonicJammer.playSound("critical", mushroomPeaks.getBaseContext());
                }
                if (fightForeground.isBlock) {
                    sonicJammer.playSound("block", mushroomPeaks.getBaseContext());
                }
                if (!fightForeground.isCrit && !fightForeground.isBlock) {
                    if (dmgVal > 1) {

                        int visNumOfAttacks = mushroomPeaks.selectedAction.getVisualNumOfAttacks();

                        for (int i = 0; i < visNumOfAttacks; i++) {

                            int numOfAttacks = mushroomPeaks.selectedAction.getNumOfAttacks();
                            if ((visNumOfAttacks > 2 && i == visNumOfAttacks - 1) ||
                                    (numOfAttacks > 2 && i == numOfAttacks - 1) ||
                                    (numOfAttacks == 1) && (visNumOfAttacks == 1)) {

                                switch (affEff) {
                                    case NEUTRAL:
                                    default:
                                        sonicJammer.playSound("hit", mushroomPeaks.getBaseContext());
                                        break;
                                    case IMMUNE:
                                        sonicJammer.playSound("immune", mushroomPeaks.getBaseContext());
                                        break;
                                    case INEFFECTIVE:
                                        sonicJammer.playSound("ineffective_hit", mushroomPeaks.getBaseContext());
                                        break;
                                    case EFFECTIVE:
                                        sonicJammer.playSound("effective_hit", mushroomPeaks.getBaseContext());
                                        break;
                                }

                                try {
                                    synchronized (this) {
                                        this.wait(480);
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            } else {

                                try {
                                    synchronized (this) {
                                        this.wait(300);
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                sonicJammer.playSound("hit", mushroomPeaks.getBaseContext());
                            }

                        }
                    } else {
                        try {
                            synchronized (this) {
                                this.wait(300);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        sonicJammer.playSound("no_damage", mushroomPeaks.getBaseContext());
                    }
                }


//            updateSmallBars(getCurrentTargetBattleToken().getCharLayout(),
//                    getCurrentTargetBattleToken().getSmallBarFilledPercentages());

                // dmg + KO anim if occurs
                // skill / block / crit anims?
                // also separate block and crit visible deets :)

                //thorns and counter and stuff

                //bonusEffects, ONLY if hit

            } else {

                String msg = "MISS!";
                if (dmgVal == -2) {
                    msg += "\nEVADE!";
                }

                if (print) {
                    mushroomPeaks.enactElicitOutcomeView(msg, false);
                }


                try {
                    synchronized (this) {
                        this.wait(250);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mushroomPeaks.playCharSFX(targetId, moveType.DODGE);
                sonicJammer.playSound("miss", mushroomPeaks.getBaseContext());


                // miss animation
            }


            try {
                synchronized (this) {
                    this.wait(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }




    public void conveyVisual(statusEffect effect, int targetId) {

        switch (effect) {
            case BANE:
                sonicJammer.playSound("bane", mushroomPeaks.getBaseContext());
            case POISON:
            case BURN:
            case RATTLE:
            case BLEED:
            case BEDRAGGLE:
            case WHIRLPOOL:

            case DISADVANTAGE_DX:
            case CLASH_EFFECT:

                // just assigns a status effect normally, to target/s determined above
                // -1 for infinite length status
                sonicJammer.playSound("status_received", mushroomPeaks.getBaseContext());
                break;

            case SLEEP:
            case STUN:
            case FREEZE:
                sonicJammer.playSound("turn_off", mushroomPeaks.getBaseContext());
                break;

            case CHARM:
            case RAGE:
            case ENGAGE:
            case INTIMIDATE:
            case ENERVATE:
            case TOPPLE:
                sonicJammer.playSound("target_status", mushroomPeaks.getBaseContext());
                break;


            case CURE_STATUS:
            case ASSIST_UP:
            case IMMUNE:
            case EVADE:
            case EVADE_DX:

            case REGEN:
            case THORNS:
            case COUNTER:
            case RAMPART:

            case UNTARGETABLE:
            case ADVANTAGE_DX:
            case DEFENDED:
            case INFILTRATE:
            case TURN_WEAPON_ADVANTAGE:

            case EX_ATTACK_MOD:
            case EX_AVO_MOD:
            case EX_CRIT:
            case EX_ADVANTAGE:
            case EX_AGGRO_MOD:
                sonicJammer.playSound("good_status_received", mushroomPeaks.getBaseContext());
                break;

            case ATK_HEAL:
            case SPREGEN:
            case IMMUNITY:
                sonicJammer.playSound("heal", mushroomPeaks.getBaseContext());
                break;

            case STAT_RAISE:
                sonicJammer.playSound("stat_up", mushroomPeaks.getBaseContext());
                break;
            case STAT_DROP:
                sonicJammer.playSound("stat_down", mushroomPeaks.getBaseContext());
                break;

            case ROW_KNOCKBACK:
            case ROW_LURE:
                if(!getBT(targetId).getIsGuardActioning()) {
                    if (getBT(targetId).getIsAlive() && mushroomPeaks.rowActionAvailable(targetId)) {
                        mushroomPeaks.setCharRow(targetId, effect == statusEffect.ROW_LURE);
                        sonicJammer.playSound("row_shift", mushroomPeaks.getBaseContext());
                    } else {
                        sonicJammer.playSound("error", mushroomPeaks.getBaseContext());
                    }
                } else{
                    sonicJammer.playSound("guard", mushroomPeaks.getBaseContext());
                }
                break;
            case ROW_SWAP:
                if(!getBT(targetId).hasStatus(statusEffect.ROW_LOCKDOWN) &&
                        getBT(targetId).getIsAlive() && mushroomPeaks.rowActionAvailable(targetId)) {
                    mushroomPeaks.setCharRow(targetId, !getBT(targetId).getIsFrontLine());
                    sonicJammer.playSound("row_shift", mushroomPeaks.getBaseContext());
                } else{
                    if(getBT(targetId).getIsAlive()) {
                        sonicJammer.playSound("error", mushroomPeaks.getBaseContext());
                    }
                }
                break;

            case INSTAKILL:
                sonicJammer.playSound("instakill", mushroomPeaks.getBaseContext());
                sonicJammer.playSound("instakill2", mushroomPeaks.getBaseContext());
                mushroomPeaks.enactElicitOutcomeView("INSTAKILL!!!", true);

                break;

            case RELOAD:
                sonicJammer.playSound("reload", mushroomPeaks.getBaseContext());
                break;

            case SET_HP:
            case SP_ABSORB:
                sonicJammer.playSound("sp_absorb", mushroomPeaks.getBaseContext());
                break;

            case STAT_STEAL_ALL:
                sonicJammer.playSound("stat_steal", mushroomPeaks.getBaseContext());
            case STAT_STEAL_SINGLE:
                sonicJammer.playSound("stat_steal", mushroomPeaks.getBaseContext());
                String msg = getBT(targetId).getName() + " has been robbed!";
                mushroomPeaks.runOnUiThread(() -> Toast.makeText(mushroomPeaks.getBaseContext(), msg, Toast.LENGTH_SHORT).show());
                break;

            case COPY_STAT_CHANGES:
                sonicJammer.playSound("effective_hit", mushroomPeaks.getBaseContext());
                String msg1 = getBT(targetId).getName() + "'s Stat Changes were copied!";
                mushroomPeaks.runOnUiThread(() -> Toast.makeText(mushroomPeaks.getBaseContext(), msg1, Toast.LENGTH_SHORT).show());
                break;

            case ACTION_ADD:
                //spd stat +3 action add
            case SPD:
                sonicJammer.playSound("status_heal", mushroomPeaks.getBaseContext());
                break;
            case ACTION_REDUCE:
            case END_TURN:
                // spd stat -3 action reduce
            case FLIP_DEF:
            case CHAOS_RIFT:
                sonicJammer.playSound("back_button", mushroomPeaks.getBaseContext());
                break;

            case NERD:
                sonicJammer.playSound("nerd", mushroomPeaks.getBaseContext());
            case RUNE_BURST:
                sonicJammer.playSound("rune_burst", mushroomPeaks.getBaseContext());
                break;
            case DIVINE_BURST:
                sonicJammer.playSound("divine_burst", mushroomPeaks.getBaseContext());
                break;
            case ELEMENTAL_BURST:
                sonicJammer.playSound("elemental_burst", mushroomPeaks.getBaseContext());
                break;

            case ASSIST:
                sonicJammer.playSound("assist_action", mushroomPeaks.getBaseContext());
                break;

            case METAMORPH:
                sonicJammer.playSound("metamorph", mushroomPeaks.getBaseContext());
                break;

            case GRAPPLE:
                sonicJammer.playSound("grapple", mushroomPeaks.getBaseContext());
                break;
            case GRAPPLE_RELEASE:
                sonicJammer.playSound("grapple_release", mushroomPeaks.getBaseContext());
                mushroomPeaks.runOnUiThread(() -> {
                try {
                    getBT(targetId).getCharLayout().setVisibility(View.VISIBLE);
                    getBT(targetId).getCharLayout().setClickable(true);
                    getBT(targetId).getCharLayout().requestLayout();
                    getBT(targetId).getCharLayout().invalidate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

                break;
            case SWALLOW:
                sonicJammer.playSound("swallow", mushroomPeaks.getBaseContext());

                mushroomPeaks.runOnUiThread(() -> {
                    getBT(targetId).getCharLayout().setVisibility(View.INVISIBLE);
                    getBT(targetId).getCharLayout().setClickable(false);
                });

                break;

            default:
            case REINFORCE:


        }
    }

    public void battleCharTap() {
        Thread thr = new Thread(run);
        thr.start();
    }

    public battleToken getBT(int id) {
        return mushroomPeaks.getBT(id);
    }

    public void setAffEff(effectiveness eff) {
        affEff = eff;
    }

    public static void post(String charName, String targetName, String attackName,
                     fightUpdater.textType classification, Integer value, stat statChanged) {
        battleLog.post(charName, targetName, attackName, classification, value, statChanged);
    }

}