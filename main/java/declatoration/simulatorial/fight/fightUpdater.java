package declatoration.simulatorial.fight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.Objects;

import declatoration.simulatorial.enums.stat;
import declatoration.simulatorial.enums.statusEffect;

public class fightUpdater extends androidx.appcompat.widget.AppCompatTextView{

//    ArrayList<singleTurnText> turns = new ArrayList<>();
    ArrayList<String> texts = new ArrayList<>();

    fightForeground forestead;
    StringBuilder printedText = new StringBuilder();

    ScrollView sv;


    public enum textType{
        TURN_START,
        DMG, KOED,
        STAT_RAISE,
        STUN_INF, CHARM_INF, SLEEP_INF, THORNS_INF, BLEED_INF, BURN_INF, POISON_INF, FREEZE_INF,
        ENGAGE_INF, INTIMIDATE_INF,
        WHIRLPOOL_INF, ENERVATE_INF, RAGE_INF, RATTLE_INF, BEDRAGGLE_INF, BANE_INF, VULNERABLE_INF, REGEN_INF,
        RAMPART_INF, COUNTER_INF,
        STUN_TURN, CHARM_TURN, SLEEP_TURN,
        THORNS_DMG, BLEED_DMG, BURN_DMG, POISON_DMG, FREEZE,
        WHIRLPOOL_DMG,  BANE_DMG, COUNTER_DMG,
        NERD,

        RAGE_TURN, RAGE_TARGET_CHANGE, RAGE_ATTACK_CHANGE,

        ADVANTAGE_DX, DISADVANTAGE_DX,

        GRAPPLE,
        GRAPPLE_FAIL,
        GRAPPLE_RELEASE,
        SWALLOW,
        SWALLOW_FAIL,
        SWALLOW_RELEASE,
        SWALLOW_DMG,

        AEGIS_SHIELD_INF,
        AEGIS_SHIELD_TANK,
        AEGIS_SHIELD_BREAK,
        AEGIS_SHIELD_INFILTRATED,

        ASSIST_UP,
        IMMUNE,
        IMMUNITY,
        METAMORPH,
        STAT_DROP,

        STAT_STEAL_SINGLE, STAT_STEAL_ALL,
        DEFEND,
        UNTARGETABLE,

        OUT_OF_AMMO,
        RELOAD,

        ROW_KNOCKBACK, ROW_LURE,
        EVADE, EVADE_DX,
        ACTION_ADD, ACTION_REDUCE, END_TURN,
        SPD_ACTION_ADD, SPD_ACTION_REDUCE,

        HEAL, REGEN_HEAL,
        SP_HEAL,
        // takes 20 sp from target, gives to char
        SP_ABSORB,
        BALM,
        REVIVIFY,
        CURE_STATUS,
        RESET_STAT_CHANGES,
        INSTAKILL,

        ASSIST,

        ATK_HEAL,
        ELEMENTAL_BURST, RUNE_BURST, DIVINE_BURST,
        MOVE_NEXT, CHAOS_RIFT, CHAOS_RIFT_INF, NULL;

        public static textType getTextType(statusEffect effect, boolean bool){

            switch (effect) {
                case POISON:
                    return POISON_INF;
                case BURN:
                    return BURN_INF;
                case RATTLE:
                    return RATTLE_INF;
                case BANE:
                    return BANE_INF;
                case BLEED:
                    return BLEED_INF;
                case BEDRAGGLE:
                    return BEDRAGGLE_INF;
                case DISADVANTAGE_DX:
                    return DISADVANTAGE_DX;
                case SLEEP:
                    return SLEEP_INF;
                case STUN:
                    return STUN_INF;
                case FREEZE:
                    return FREEZE_INF;
                case CHARM:
                    return CHARM_INF;
                case RAGE:
                    return RAGE_INF;
                case ENGAGE:
                    return ENGAGE_INF;
                case INTIMIDATE:
                    return INTIMIDATE_INF;
                case ENERVATE:
                    return ENERVATE_INF;
                case TOPPLE:
                    return VULNERABLE_INF;
                case CURE_STATUS:
                    return CURE_STATUS;
                case METAMORPH:
                    return METAMORPH;
                case ASSIST_UP:
                    return ASSIST_UP;
                case IMMUNE:
                    return IMMUNE;
                case EVADE:
                    return EVADE;
                case EVADE_DX:
                    return EVADE_DX;
                case REGEN:
                    return REGEN_INF;
                case THORNS:
                    return THORNS_INF;
                case COUNTER:
                    return COUNTER_INF;
                case RAMPART:
                    return RAMPART_INF;
                case UNTARGETABLE:
                    return UNTARGETABLE;
                case ADVANTAGE_DX:
                    return ADVANTAGE_DX;
                case DEFENDED:
                    return DEFEND;
                case ATK_HEAL:
                    return ATK_HEAL;
                case STAT_RAISE:
                    return STAT_RAISE;
                case STAT_DROP:
                    return STAT_DROP;
                case ROW_KNOCKBACK:
                    return ROW_KNOCKBACK;
                case ROW_LURE:
                    return ROW_LURE;

                case GRAPPLE:
                    if(bool) {
                        return GRAPPLE;
                    } else{
                        return GRAPPLE_FAIL;
                    }
                case SWALLOW:
                    if(bool){
                        return SWALLOW;
                    } else{
                        return SWALLOW_FAIL;
                    }
                case SWALLOWED:
                    return SWALLOW_DMG;
                case GRAPPLE_RELEASE:
                    if(bool){
                        return GRAPPLE_RELEASE;
                    } else{
                        return SWALLOW_RELEASE;
                    }

                case INSTAKILL:
                    return INSTAKILL;
                case STAT_STEAL_ALL:
                    return STAT_STEAL_ALL;
                case STAT_STEAL_SINGLE:
                    return STAT_STEAL_SINGLE;
                case ACTION_ADD:
                    return ACTION_ADD;
                case ACTION_REDUCE:
                    return ACTION_REDUCE;
                case END_TURN:
                    return END_TURN;
                case IMMUNITY:
                    return IMMUNITY;
                case SPREGEN:
                    return SP_HEAL;
                case SP_ABSORB:
                    return SP_ABSORB;
                case ASSIST:
                    return ASSIST;
                default:
                case REINFORCE:
                    return NULL;
                case CHAOS_RIFT:
                    return CHAOS_RIFT_INF;
                case ELEMENTAL_BURST:
                    return ELEMENTAL_BURST;
                case RUNE_BURST:
                    return RUNE_BURST;
                case DIVINE_BURST:
                    return DIVINE_BURST;
                case NERD:
                    return NERD;

                case SPD:
                    return SPD_ACTION_ADD;
                case FLIP_DEF:
                    return SPD_ACTION_REDUCE;


            }
        }
        public static textType getTextTypeTurnEff(statusEffect effect){
            switch (effect){

                case STUN:
                    return STUN_TURN;
                case CHARM:
                    return CHARM_TURN;
                case SLEEP:
                    return SLEEP_TURN;
                case THORNS:
                    return THORNS_DMG;
                case BLEED:
                    return BLEED_DMG;
                case BURN:
                    return BURN_DMG;
                case POISON:
                    return POISON_DMG;
                case FREEZE:
                    return FREEZE;
                case WHIRLPOOL:
                    return WHIRLPOOL_DMG;
                case BANE:
                    return BANE_DMG;
                case COUNTER:
                    return COUNTER_DMG;
                default:
                    return null;
            }
        }
    }

    public fightUpdater(Context context){
        super(context);
    }
    public fightUpdater(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public void setFightForeground(fightForeground ff){
        forestead = ff;
        sv = (ScrollView) this.getParent();
    }

    public void post(String charName, String targetName, String attackName,
                        textType classification, Integer value, stat statChanged){
        if(classification != null) {

            String output = "";

            switch (classification) {
                case KOED:
                    output += targetName + " has been KOED!";
                    break;
                case DMG:
                    if (value == -1) {
                        output = "MISS! (" + attackName + ")";
                    } else {

                        if (value > 1) {
                            output += targetName + " has taken " + value + " DMG! (" + attackName + ")";
                        } else {
                            if(value < 0){
                                output += targetName + " EVADED the attack! (" + attackName + ")";
                            } else {
                                output += targetName + " was scraped for 1 DMG! (" + attackName + ")";
                            }
                        }

                        if (fightForeground.isCrit) {
                            output += " CRITICAL! ";
                        }
                        if (fightForeground.isBlock) {
                            output += " BLOCK! ";
                        }
                    }
                    break;

                case GRAPPLE:
                    output = targetName + " is being tightly GRAPPLED by " + charName + "!";
                    break;
                case GRAPPLE_FAIL:
                    output = targetName + " is ALREADY GRAPPLED!";
                    break;
                case GRAPPLE_RELEASE:
                    if(value != null && value == 50){
                        // grappler took 50% dmg so release
                        output = charName + " has taken a BOATLOAD of damage!\n";
                    }
                    // normal grapple release
                    output += targetName + " was RELEASED from the GRAPPLE!";

                    break;

                case SWALLOW:
                    output = targetName + " was SWALLOWED WHOLE by " + charName + "! (Swallow)";
                    break;
                case SWALLOW_FAIL:
                    output = charName + " was too LATE for LUNCH! (Swallow Failed)";
                    break;
                case SWALLOW_DMG:
                    output = targetName + " was churned up for " + value + " DMG! (Swallow DMG)";
                    break;
                case SWALLOW_RELEASE:
                    output = targetName + " was FREED from a DIGESTIVE demise! (Swallow Release)";
                    break;

                case AEGIS_SHIELD_INF:
                    output = targetName + " is unapologetically CAMPING! (Aegis Shield)";
                    break;
                case AEGIS_SHIELD_INFILTRATED:
                    output = targetName + "'s Aegis Shield was TORN ASUNDER!" +
                            targetName + " took " + value + " DMG! (Aegis Shield Infiltrated)";
                    break;
                case AEGIS_SHIELD_TANK:
                    output = targetName + "'s Aegis Shield has taken " + value + " DMG!";
                    break;
                case AEGIS_SHIELD_BREAK:
                    output = targetName + "'s Aegis \nShield SHATTERED!" +
                            targetName + " took " + value + " DMG!";
                    break;

                case STUN_INF:
                    output = targetName + " is now a HUGE LIABILITY! (Stun)";
                    break;
                case SLEEP_INF:
                    output = targetName + " pulled out the BLANKET! (Sleep)";
                    break;
                case DEFEND:
                    output = targetName + " is under " + charName + "'s protection!";
                    break;
                case BANE_DMG:
                    output = charName + " took " + value + " points of BANE DMG!";
                    break;
                case BANE_INF:
                    output = targetName + " is seeing into the ABYSS! (Bane)";
                    break;
                case BURN_DMG:
                    output = charName + " took " + value + " points of BURN DMG!";
                    break;
                case BURN_INF:
                    output = targetName + " had no comeback to " + charName + "'s SICK BURN! (Burn)";
                    break;
                case RAGE_INF:
                    output = targetName + " is FUMING! (Rage)";
                    break;
                case BLEED_DMG:
                    output = charName + " took " + value + " points of BLEED DMG!";
                    break;
                case BLEED_INF:
                    output = targetName + " is pretending like EVERYTHING is FINE! (Bleed)";
                    break;
                case CHARM_INF:
                    output = targetName + " is SWOONING for " + charName + "! (Charm)";
                    break;
                case RAGE_TURN:
                    output = charName + " is ACTING OUT!";
                    break;
                case REGEN_INF:
                    output = targetName + " is now ANNOYING to fight against! (Regen)";
                    break;
                case STUN_TURN:
                    output = charName + " is immobilized by STUN!";
                    break;
                case ENGAGE_INF:
                    output = targetName + " has some SERIOUS BEEF with " + charName + "! (Engage)";
                    break;
                case FREEZE_INF:
                    output = targetName + " left their coat at HOME! (Freeze)";
                    break;
                case POISON_DMG:
                    output = charName + " took " + value + " points of POISON DMG!";
                    break;
                case POISON_INF:
                    output = targetName + " should go see a DOCTOR! (Poison)";
                    break;
                case RATTLE_INF:
                    output = targetName + " is RETHINKING their life CHOICES! (Rattle)";
                    break;
                case REGEN_HEAL:
                    output = targetName + " regenerated " + value + " HP!";
                    break;
                case SLEEP_TURN:
                    output = charName + " is SNOOZING!";
                    break;
                case THORNS_DMG:
                    output = targetName + " took " + value + " points of Thorns DMG! ";
                    break;
                case THORNS_INF:
                    output = charName + " is looking SPIKY! (Thorns)";
                    break;
                case COUNTER_INF:
                    output = targetName + "'s RETRIBUTION is online! (Counter)";
                    break;
                case RAMPART_INF:
                    output = targetName + " is taking up SPACE! (Rampart)";
                    break;
                case ENERVATE_INF:
                    output = targetName + " would rather TAKE A BREAK! (Enervate)";
                    break;
                case BEDRAGGLE_INF:
                    output = targetName + "'s socks are DRENCHED! (Bedraggle)";
                    break;
                case WHIRLPOOL_DMG:
                    output = charName + " took " + value + " points of WHIRLPOOL DMG!";
                    break;
                case WHIRLPOOL_INF:
                    output = targetName + " is identifying with a GOLDFISH! (Whirlpool)";
                    break;
                case INTIMIDATE_INF:
                    output = targetName + " can't make eye contact with " + charName + "! (Intimidate)";
                    break;
                case VULNERABLE_INF:
                    output = targetName + " was FLIPPED on their BACK! (Topple)";
                    break;
                case RAGE_ATTACK_CHANGE:
                    output = charName + " isn't LISTENING!";
                    break;
                case RAGE_TARGET_CHANGE:
                    output = charName + " shifted the attack to " + targetName + "!";
                    break;
                case ASSIST:
                    output = charName + " is ready to help out " + targetName + "!";
                    break;
                case HEAL:
                    output = targetName + " has been healed for " + value + " HP!";
                    break;
                case FREEZE:
                    output = charName + " is too COOL to fight! (Freeze)";
                    output = charName + " took " + value + " points of FRREZE DMG!";
                    break;
                case IMMUNE:
                    output = targetName + " is immune to something what is this i dunno yet";
                    break;
                case IMMUNITY:
                    output = targetName + " has IMMUNITY and isn''t affected by " + attackName + "!";
                    break;
                case SP_HEAL:
                    output = targetName + " has been regained " + value + " SP!";
                    break;
                case REVIVIFY:
                    output = targetName + " has been REVIVIFIED!";
                    break;
                case ASSIST_UP:
                    output = targetName + " is a SUCKER for " + charName + "! (Assist UP)";
                    break;
                case INSTAKILL:
                    output = targetName + " forgot to TURN OFF THE OVEN! (Instakill)";
                    break;
                case METAMORPH:
                    output = charName + " overdid it with the CAMOUFLAGE! (Metamorph)";
                    break;
                case OUT_OF_AMMO:
                    output = charName + " is OUT OF AMMO!";
                    break;
                case RELOAD:
                    output = charName + " has RELOADED! (" + value + " Shots)";
                    break;
                case BALM:
                    output = targetName + " has RESTORED a stage of " + statChanged + "!";
                    break;
                case SP_ABSORB:
                    output = charName + " just stole " + targetName + "'s LUNCH! (SP Absorbed)";
                    break;
                case CHARM_TURN:
                    output = charName + " can't stop DAYDREAMING about " + targetName + "!";
                    break;
                case STAT_RAISE:
                    output = targetName + "'s " + statChanged.toString() + " rose!";
                    break;
                case STAT_DROP:
                    output = targetName + "'s " + statChanged.toString() + " dropped!";
                    break;
                case TURN_START:
                    output = "~~~\n" + charName + "'s Turn!";
                    break;
                case COUNTER_DMG:
                    output = targetName + " snapped BACK for " + value + " DMG!";
                    break;
                case CURE_STATUS:
                    output = targetName + " had some CHICKEN SOUP! (Status Cure)";
                    break;
                case ADVANTAGE_DX:
                    output = targetName + " is on FIRE! (Advantage DX)";
                    break;
                case UNTARGETABLE:
                    output = targetName + " is CHEESING the GAME! (Untargetable)";
                    break;
                case STAT_STEAL_ALL:
                    output = charName + " ransacked " + targetName + "'s entire HOUSE!";
                    break;
                case DISADVANTAGE_DX:
                    output = targetName + " hit their FUNNY BONE! (Disadvantage DX)";
                    break;
                case STAT_STEAL_SINGLE:
                    output = targetName + "'s " + statChanged.toString() + " has been nabbed!";
                    break;
                case RESET_STAT_CHANGES:
                    output = targetName + " overdid it with the CAMOUFLAGE! (Metamorph)";
                    break;
                case EVADE:
                    output = charName + " is doing CORKSCREWS! (Evade)";
                    break;
                case END_TURN:
                    output = charName + " BONKED! (End Turn)";
                    break;
                case SPD_ACTION_ADD:
                    output = charName + "'s SPD Boosts Grant Another Action!";
                    break;
                case SPD_ACTION_REDUCE:
                    output = charName + "'s SPD Debuffs Reduce their Actions by 1!";
                    break;
                case EVADE_DX:
                    output = charName + " summoned DOUBLES! (Evade DX)";
                    break;
                case ROW_LURE:
                    output = targetName + " was DRAGGED FORWARD! (Row-Lure)";
                    break;
                case ACTION_ADD:
                    output = targetName + " is TOO FAST! (Action +1)";
                    break;
                case ACTION_REDUCE:
                    output = targetName + " stubbed their TOE! (Action -1)";
                    break;
                case ROW_KNOCKBACK:
                    output = targetName + " was PUSHED AWAY! (Row Knockback)";
                    break;
                case ATK_HEAL:
                    output = charName + " has HEALED " + value + " HP from the attack! (ATK Heal)";
                    break;
                case MOVE_NEXT:
                    output = targetName + " is prepared to FOLLOW-UP! (Move Next)";
                    break;
                case ELEMENTAL_BURST:
                    // here attack name is used to store aff name cuz i dont need another variable for this
                    if(value == 0){
                        value = 1;
                    }
                    output = "ELEMENTAL BURST! " + targetName + " has taken " + value + " points of " + attackName + " DMG!";
                    break;
                case RUNE_BURST:
                    // here attack name is used to store aff name cuz i dont need another variable for this
                    output = "RUNE BURST! " + targetName + " has taken " + value + " points of " + attackName + " DMG!";
                    break;
                case CHAOS_RIFT_INF:
                    output = "A strange SHIMMER has appeared around " + charName + "! (Chaos Rift)";
                    break;
                case CHAOS_RIFT:
                    if(Objects.equals(charName, targetName)){
                        output = "The Attack hit " + charName + " anyway! (Chaos Rift)";
                    } else {
                        output = "The Attack bounced off to " + targetName + " ! (Chaos Rift)";
                    }

                    break;

                default:
                case NULL:
            }


            if (!output.equals("")) {
                print(output);
            }
        }
    }


    private void print(String output){

        texts.add(output);
        if(texts.size() > 30){
            String textToRemove = texts.get(0);

            texts.remove(0);
            printedText.delete(0, textToRemove.length());
        }

        printedText.append("\n").append(output);

        View lastChild = sv.getChildAt(sv.getChildCount() - 1);
        int bottom = lastChild.getBottom() + sv.getPaddingBottom();
        int delta = bottom - (sv.getScrollY());


        forestead.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setText(printedText.toString());
            }
        });
        sv.post(() -> sv.fullScroll(View.FOCUS_DOWN));
    }



    private class singleTurnText{

        private ArrayList<String> texts = new ArrayList<>();
        private int textLength = 0;



        public singleTurnText(){}

        public void addToTurn(String str){
            texts.add(str);

            textLength += str.length();
        }

        public int getTextLength() {
            return textLength;
        }
    }

}
