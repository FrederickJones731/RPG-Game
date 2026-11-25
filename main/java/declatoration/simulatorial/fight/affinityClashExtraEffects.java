package declatoration.simulatorial.fight;

import static declatoration.simulatorial.enums.statusEffect.ABLAZE;
import static declatoration.simulatorial.enums.statusEffect.ACTION_ADD;
import static declatoration.simulatorial.enums.statusEffect.ACTION_REDUCE;
import static declatoration.simulatorial.enums.statusEffect.ADVANTAGE;
import static declatoration.simulatorial.enums.statusEffect.ANNEAL;
import static declatoration.simulatorial.enums.statusEffect.ATK;
import static declatoration.simulatorial.enums.statusEffect.ATK_HEAL;
import static declatoration.simulatorial.enums.statusEffect.AWAKENED;
import static declatoration.simulatorial.enums.statusEffect.BANE;
import static declatoration.simulatorial.enums.statusEffect.BEDRAGGLE;
import static declatoration.simulatorial.enums.statusEffect.BLEED;
import static declatoration.simulatorial.enums.statusEffect.BLOCK_ALT;
import static declatoration.simulatorial.enums.statusEffect.BRITTLE;
import static declatoration.simulatorial.enums.statusEffect.BURN;
import static declatoration.simulatorial.enums.statusEffect.CLASH_THORNS;
import static declatoration.simulatorial.enums.statusEffect.CORROSION;
import static declatoration.simulatorial.enums.statusEffect.CRIT;
import static declatoration.simulatorial.enums.statusEffect.CRIT_ALT;
import static declatoration.simulatorial.enums.statusEffect.CURBED;
import static declatoration.simulatorial.enums.statusEffect.DEF;
import static declatoration.simulatorial.enums.statusEffect.DEF_HEAL;
import static declatoration.simulatorial.enums.statusEffect.DENT;
import static declatoration.simulatorial.enums.statusEffect.DISADVANTAGE;
import static declatoration.simulatorial.enums.statusEffect.DISSIPATE;
import static declatoration.simulatorial.enums.statusEffect.DOUBLE_DMG;
import static declatoration.simulatorial.enums.statusEffect.ELECTROMAGNET;
import static declatoration.simulatorial.enums.statusEffect.EMPOWER;
import static declatoration.simulatorial.enums.statusEffect.END_TURN;
import static declatoration.simulatorial.enums.statusEffect.ENERVATE;
import static declatoration.simulatorial.enums.statusEffect.ENGAGE;
import static declatoration.simulatorial.enums.statusEffect.EXALTED;
import static declatoration.simulatorial.enums.statusEffect.EX_ADVANTAGE;
import static declatoration.simulatorial.enums.statusEffect.EX_DISADVANTAGE;
import static declatoration.simulatorial.enums.statusEffect.FLINCH;
import static declatoration.simulatorial.enums.statusEffect.FRAZZLE;
import static declatoration.simulatorial.enums.statusEffect.FREEZE;
import static declatoration.simulatorial.enums.statusEffect.HALF_DMG;
import static declatoration.simulatorial.enums.statusEffect.HIT_ALT;
import static declatoration.simulatorial.enums.statusEffect.INSTAKILL;
import static declatoration.simulatorial.enums.statusEffect.INT;
import static declatoration.simulatorial.enums.statusEffect.INTIMIDATE;
import static declatoration.simulatorial.enums.statusEffect.MAG;
import static declatoration.simulatorial.enums.statusEffect.NEUTRALIZE;
import static declatoration.simulatorial.enums.statusEffect.NO_DMG;
import static declatoration.simulatorial.enums.statusEffect.OBEDIENT;
import static declatoration.simulatorial.enums.statusEffect.POISON;
import static declatoration.simulatorial.enums.statusEffect.RAGE;
import static declatoration.simulatorial.enums.statusEffect.RANDOM;
import static declatoration.simulatorial.enums.statusEffect.RATTLE;
import static declatoration.simulatorial.enums.statusEffect.REGEN;
import static declatoration.simulatorial.enums.statusEffect.ROW_KNOCKBACK;
import static declatoration.simulatorial.enums.statusEffect.ROW_SWAP;
import static declatoration.simulatorial.enums.statusEffect.SPD;
import static declatoration.simulatorial.enums.statusEffect.SPREGEN;
import static declatoration.simulatorial.enums.statusEffect.STUN;
import static declatoration.simulatorial.enums.statusEffect.TBD;
import static declatoration.simulatorial.enums.statusEffect.THAW;
import static declatoration.simulatorial.enums.statusEffect.UNBLOCKABLE;
import static declatoration.simulatorial.enums.statusEffect.UNCRITTABLE;
import static declatoration.simulatorial.fight.fightBackground.combatants;
import static declatoration.simulatorial.fight.fightBackground.currentTurnCharId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import declatoration.simulatorial.enums.affinity;
import declatoration.simulatorial.enums.statusEffect;

public class affinityClashExtraEffects {


    HashMap<String, ArrayList<clashEffect>> clashReactionMap = new HashMap<>();


    public affinityClashExtraEffects(){

        clashReactionMap.put("null/null", new ArrayList<>(Collections.singletonList(
                new clashEffect(RANDOM, null, 30, false))));
        clashReactionMap.put("null/steel", new ArrayList<>(Collections.singletonList(
                new clashEffect(ACTION_REDUCE, null, 100, true))));
        clashReactionMap.put("null/venin", new ArrayList<>(Arrays.asList(
                new clashEffect(DISADVANTAGE, null, 100, true),
                new clashEffect(POISON, null, 15, true))));
        clashReactionMap.put("null/aether", new ArrayList<>(Arrays.asList(
                new clashEffect(CRIT_ALT, 1, 100, true),
                new clashEffect(RAGE, null, 20, false))));
        clashReactionMap.put("null/dusk", new ArrayList<>(Collections.singletonList(
                new clashEffect(INTIMIDATE, null, 20, true))));

        clashReactionMap.put("null/inferno", new ArrayList<>(Collections.singletonList(
                new clashEffect(RANDOM, null, 30, false))));
        clashReactionMap.put("null/glacier", new ArrayList<>(Collections.singletonList(
                new clashEffect(RANDOM, null, 30, false))));

        clashReactionMap.put("null/cyclone", new ArrayList<>(Arrays.asList(
                new clashEffect(FRAZZLE, null, 15, true),
                new clashEffect(UNCRITTABLE, null, 100, true))));

        clashReactionMap.put("null/silva", new ArrayList<>(Collections.singletonList(
                new clashEffect(RANDOM, null, 30, false))));
        clashReactionMap.put("null/torrent", new ArrayList<>(Collections.singletonList(
                new clashEffect(RANDOM, null, 30, false))));

        clashReactionMap.put("null/gaia", new ArrayList<>(Collections.singletonList(
                new clashEffect(ACTION_REDUCE, 1, 100, true))));
        clashReactionMap.put("null/thunder", new ArrayList<>(Arrays.asList(
                new clashEffect(CLASH_THORNS, null, 100, true),
                new clashEffect(STUN, null, 10, true))));
        clashReactionMap.put("null/vamp", new ArrayList<>(Arrays.asList(
                new clashEffect(HALF_DMG, null, 35, false),
                new clashEffect(INTIMIDATE, null, 20, true))));
        clashReactionMap.put("null/force", new ArrayList<>(Arrays.asList(
                new clashEffect(ROW_KNOCKBACK, null, 25, true),
                new clashEffect(ATK, 1, 100, true))));
        clashReactionMap.put("null/ghenju", new ArrayList<>(Collections.singletonList(
                new clashEffect(DISADVANTAGE, null, 100, true))));
        clashReactionMap.put("null/dragonsvalk", new ArrayList<>(Collections.singletonList(
                new clashEffect(HALF_DMG, null, 30, true))));

        clashReactionMap.put("steel/null", new ArrayList<>(Collections.singletonList(
                new clashEffect(BLEED, null, 20, false))));
        clashReactionMap.put("steel/steel", new ArrayList<>(Collections.singletonList(
                new clashEffect(BLOCK_ALT, 1, 100, false))));
        clashReactionMap.put("steel/venin", new ArrayList<>(Collections.singletonList(
                new clashEffect(CLASH_THORNS, null, 100, true))));
        clashReactionMap.put("steel/aether", new ArrayList<>(Arrays.asList(
                new clashEffect(HALF_DMG, 1, 30, true),
                new clashEffect(DISADVANTAGE, null, 100, true))));
        clashReactionMap.put("steel/dusk", new ArrayList<>(Collections.singletonList(
                new clashEffect(HALF_DMG, null, 30, true))));
        clashReactionMap.put("steel/inferno", new ArrayList<>(Arrays.asList(
                new clashEffect(CLASH_THORNS, null, 100, true),
                new clashEffect(ANNEAL, null, 100, true))));
        clashReactionMap.put("steel/glacier", new ArrayList<>(Arrays.asList(
                new clashEffect(ADVANTAGE, null, 100, true),
                new clashEffect(BRITTLE, null, 20, true))));
        clashReactionMap.put("steel/cyclone", new ArrayList<>(Collections.singletonList(
                new clashEffect(UNCRITTABLE, null, 100, true))));
        clashReactionMap.put("steel/silva", new ArrayList<>(Collections.singletonList(
                new clashEffect(CRIT_ALT, 1, 100, true))));
        clashReactionMap.put("steel/torrent", new ArrayList<>(Collections.singletonList(
                new clashEffect(UNCRITTABLE, null, 100, true))));
        clashReactionMap.put("steel/gaia", new ArrayList<>(Collections.singletonList(
                new clashEffect(ADVANTAGE, 1, 100, true))));
        clashReactionMap.put("steel/thunder", new ArrayList<>(Arrays.asList(
                new clashEffect(CLASH_THORNS, null, 100, true),
                new clashEffect(ELECTROMAGNET, null, 100, true))));
        clashReactionMap.put("steel/vamp", new ArrayList<>(Collections.singletonList(
                new clashEffect(HALF_DMG, null, 30, false))));
        clashReactionMap.put("steel/force", new ArrayList<>(Arrays.asList(
                new clashEffect(ROW_KNOCKBACK, null, 25, true),
                new clashEffect(ATK, 1, 100, true))));
        clashReactionMap.put("steel/ghenju", new ArrayList<>(Collections.singletonList(
                new clashEffect(DISADVANTAGE, null, 100, true))));
        clashReactionMap.put("steel/dragonsvalk", new ArrayList<>(Collections.singletonList(
                new clashEffect(HALF_DMG, null, 50, true))));

        clashReactionMap.put("venin/null", new ArrayList<>(Collections.singletonList(
                new clashEffect(POISON, null, 20, false))));
        clashReactionMap.put("venin/steel", new ArrayList<>(Arrays.asList(
                new clashEffect(CRIT_ALT, 1, 100, false),
                new clashEffect(CORROSION, null, 35, false))));
        clashReactionMap.put("venin/venin", new ArrayList<>(Arrays.asList(
                new clashEffect(ATK, 2, 100, true),
                new clashEffect(ATK, 2, 100, false),
                new clashEffect(MAG, 2, 100, true),
                new clashEffect(MAG, 2, 100, false))));
        clashReactionMap.put("venin/aether", new ArrayList<>(Collections.singletonList(
                new clashEffect(RAGE, 1, 80, false))));
        clashReactionMap.put("venin/dusk", new ArrayList<>(Collections.singletonList(
                new clashEffect(ENGAGE, null, 100, false))));
        clashReactionMap.put("venin/inferno", new ArrayList<>(Arrays.asList(
                new clashEffect(CLASH_THORNS, null, 100, true),
                new clashEffect(NEUTRALIZE, null, 30, true))));
        clashReactionMap.put("venin/glacier", new ArrayList<>(Collections.singletonList(
                new clashEffect(BLOCK_ALT, 2, 100, false))));
        clashReactionMap.put("venin/cyclone", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("venin/silva", new ArrayList<>(Arrays.asList(
                new clashEffect(CRIT_ALT, 1, 100, true),
                new clashEffect(POISON, null, 40, false))));
        clashReactionMap.put("venin/torrent", new ArrayList<>(Arrays.asList(
                new clashEffect(DISADVANTAGE, null, 100, true),
                new clashEffect(ACTION_REDUCE, 1, 30, true),
                new clashEffect(NEUTRALIZE, null, 30, true))));
        clashReactionMap.put("venin/gaia", new ArrayList<>(Collections.singletonList(
                new clashEffect(END_TURN, null, 100, true))));
        clashReactionMap.put("venin/thunder", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("venin/vamp", new ArrayList<>(Collections.singletonList(
                new clashEffect(NO_DMG, null, 30, false))));
        clashReactionMap.put("venin/force", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 25, true))));
        clashReactionMap.put("venin/ghenju", new ArrayList<>(Collections.singletonList(
                new clashEffect(INT, 1, 30, false))));
        clashReactionMap.put("venin/dragonsvalk", new ArrayList<>(Collections.singletonList(
                new clashEffect(HALF_DMG, null, 20, false))));


        clashReactionMap.put("aether/null", new ArrayList<>(Arrays.asList(
                new clashEffect(DEF_HEAL, null, 30, false),
                new clashEffect(EXALTED, null, 20, false))));
        clashReactionMap.put("aether/steel", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, 1, 100, false))));
        clashReactionMap.put("aether/venin", new ArrayList<>(Collections.singletonList(
                new clashEffect(ENERVATE, null, 80, true))));
        clashReactionMap.put("aether/aether", new ArrayList<>(Arrays.asList(
                new clashEffect(INT, 1, 100, true),
                new clashEffect(INT, 1, 100, false),
                new clashEffect(EMPOWER, null, 100, true),
                new clashEffect(EMPOWER, null, 100, false))));
        clashReactionMap.put("aether/dusk", new ArrayList<>(Arrays.asList(
                new clashEffect(ENERVATE, null, 100, false),
                new clashEffect(MAG, 1, 100, true),
                new clashEffect(MAG, 1, 100, false))));
        clashReactionMap.put("aether/inferno", new ArrayList<>(Collections.singletonList(
                new clashEffect(SPREGEN, null, 100, true))));
        clashReactionMap.put("aether/glacier", new ArrayList<>(Collections.singletonList(
                new clashEffect(BLOCK_ALT, 2, 100, false))));
        clashReactionMap.put("aether/cyclone", new ArrayList<>(Collections.singletonList(
                new clashEffect(ACTION_ADD, 1, 20, true))));
        clashReactionMap.put("aether/silva", new ArrayList<>(Collections.singletonList(
                new clashEffect(DEF_HEAL, null, 100, false))));
        clashReactionMap.put("aether/torrent", new ArrayList<>(Collections.singletonList(
                new clashEffect(EXALTED, null, 100, false))));
        clashReactionMap.put("aether/gaia", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, 1, 100, true))));
        clashReactionMap.put("aether/thunder", new ArrayList<>(Arrays.asList(
                new clashEffect(CLASH_THORNS, null, 100, true),
                new clashEffect(DISADVANTAGE, null, 100, true))));
        clashReactionMap.put("aether/vamp", new ArrayList<>(Collections.singletonList(
                new clashEffect(STUN, null, 100, false))));
        clashReactionMap.put("aether/force", new ArrayList<>(Collections.singletonList(
                new clashEffect(ENERVATE, null, 80, false))));
        clashReactionMap.put("aether/ghenju", new ArrayList<>(Collections.singletonList(
                new clashEffect(POISON, null, 40, true))));
        clashReactionMap.put("aether/dragonsvalk", new ArrayList<>(Collections.singletonList(
                new clashEffect(HALF_DMG, null, 20, true))));


        clashReactionMap.put("dusk/null", new ArrayList<>(Collections.singletonList(
                new clashEffect(INTIMIDATE, null, 10, false))));
        clashReactionMap.put("dusk/steel", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, 1, 100, false))));
        clashReactionMap.put("dusk/venin", new ArrayList<>(Collections.singletonList(
                new clashEffect(ACTION_REDUCE, null, 30, true))));
        clashReactionMap.put("dusk/aether", new ArrayList<>(Arrays.asList(
                new clashEffect(ENGAGE, null, 30, false),
                new clashEffect(ATK, 2, 100, true),
                new clashEffect(ATK, 2, 100, false))));
        clashReactionMap.put("dusk/dusk", new ArrayList<>(Collections.singletonList(
                new clashEffect(ADVANTAGE, null, 100, true))));
        clashReactionMap.put("dusk/inferno", new ArrayList<>(Collections.singletonList(
                new clashEffect(ACTION_REDUCE, 1, 30, true))));
        clashReactionMap.put("dusk/glacier", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("dusk/cyclone", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("dusk/silva", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, 1, 100, true))));
        clashReactionMap.put("dusk/torrent", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("dusk/gaia", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, 1, 100, true))));
        clashReactionMap.put("dusk/thunder", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("dusk/vamp", new ArrayList<>(Collections.singletonList(
                new clashEffect(AWAKENED, null, 100, false))));
        clashReactionMap.put("dusk/force", new ArrayList<>(Collections.singletonList(
                new clashEffect(ATK, 1, 30, true))));
        clashReactionMap.put("dusk/ghenju", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("dusk/dragonsvalk", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 50, true))));


        clashReactionMap.put("inferno/null", new ArrayList<>(Collections.singletonList(
                new clashEffect(ABLAZE, null, 30, false))));
        clashReactionMap.put("inferno/steel", new ArrayList<>(Arrays.asList(
                new clashEffect(ANNEAL, 1, 100, false),
                new clashEffect(ADVANTAGE, null, 100, true))));
        clashReactionMap.put("inferno/venin", new ArrayList<>(Collections.singletonList(
                new clashEffect(NEUTRALIZE, null, 80, false))));
        clashReactionMap.put("inferno/aether", new ArrayList<>(Arrays.asList(
                new clashEffect(SPREGEN, null, 100, false),
                new clashEffect(INT, 1, 100, false))));
        clashReactionMap.put("inferno/dusk", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("inferno/inferno", new ArrayList<>(Arrays.asList(
                new clashEffect(EMPOWER, null, 100, true),
                new clashEffect(EMPOWER, null, 100, false),
                new clashEffect(MAG, 1, 100, true),
                new clashEffect(MAG, 1, 100, false))));
        clashReactionMap.put("inferno/glacier", new ArrayList<>(Collections.singletonList(
                new clashEffect(THAW, null, 80, false))));
        clashReactionMap.put("inferno/cyclone", new ArrayList<>(Collections.singletonList(
                new clashEffect(ABLAZE, null, 100, false))));
        clashReactionMap.put("inferno/silva", new ArrayList<>(Collections.singletonList(
                new clashEffect(ABLAZE, null, 100, false))));
        clashReactionMap.put("inferno/torrent", new ArrayList<>(Arrays.asList(
                new clashEffect(SPD, -1, 100, true),
                new clashEffect(END_TURN, null, 100, true))));
        clashReactionMap.put("inferno/gaia", new ArrayList<>(Arrays.asList(
                new clashEffect(BLOCK_ALT, 1, 100, false),
                new clashEffect(ACTION_REDUCE, 1, 100, true))));
        clashReactionMap.put("inferno/thunder", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("inferno/vamp", new ArrayList<>(Collections.singletonList(
                new clashEffect(FLINCH, null, 30, false))));
        clashReactionMap.put("inferno/force", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, 1, 30, true))));
        clashReactionMap.put("inferno/ghenju", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("inferno/dragonsvalk", new ArrayList<>(Collections.singletonList(
                new clashEffect(DEF_HEAL, null, 100, false))));

        clashReactionMap.put("glacier/null", new ArrayList<>(Collections.singletonList(
                new clashEffect(SPD, -1, 80, false))));
        clashReactionMap.put("glacier/steel", new ArrayList<>(Collections.singletonList(
                new clashEffect(BRITTLE, 1, 80, false))));
        clashReactionMap.put("glacier/venin", new ArrayList<>(Collections.singletonList(
                new clashEffect(SPD, -1, 80, false))));
        clashReactionMap.put("glacier/aether", new ArrayList<>(Arrays.asList(
                new clashEffect(UNCRITTABLE, null, 100, false),
                new clashEffect(HALF_DMG, null, 20, false))));
        clashReactionMap.put("glacier/dusk", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("glacier/inferno", new ArrayList<>(Arrays.asList(
                new clashEffect(BEDRAGGLE, null, 100, false),
                new clashEffect(THAW, null, 100, true))));
        clashReactionMap.put("glacier/glacier", new ArrayList<>(Arrays.asList(
                new clashEffect(EMPOWER, null, 100, true),
                new clashEffect(EMPOWER, null, 100, false),
                new clashEffect(REGEN, null, 100, true),
                new clashEffect(REGEN, null, 100, false))));
        clashReactionMap.put("glacier/cyclone", new ArrayList<>(Collections.singletonList(
                new clashEffect(SPD, -1, 80, false))));
        clashReactionMap.put("glacier/silva", new ArrayList<>(Collections.singletonList(
                new clashEffect(ABLAZE, null, 100, false))));
        clashReactionMap.put("glacier/torrent", new ArrayList<>(Collections.singletonList(
                new clashEffect(FREEZE, null, 80, false))));
        clashReactionMap.put("glacier/gaia", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, 1, 100, false))));
        clashReactionMap.put("glacier/thunder", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("glacier/vamp", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 30, false))));
        clashReactionMap.put("glacier/force", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, 1, 30, true))));
        clashReactionMap.put("glacier/ghenju", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("glacier/dragonsvalk", new ArrayList<>(Collections.singletonList(
                new clashEffect(CRIT_ALT, 1, 100, true))));

        clashReactionMap.put("cyclone/null", new ArrayList<>(Collections.singletonList(
                new clashEffect(FRAZZLE, null, 25, false))));
        clashReactionMap.put("cyclone/steel", new ArrayList<>(Collections.singletonList(
                new clashEffect(HALF_DMG, null, 35, false))));
        clashReactionMap.put("cyclone/venin", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, -1, 80, false))));
        clashReactionMap.put("cyclone/aether", new ArrayList<>(Arrays.asList(
                new clashEffect(ACTION_REDUCE, null, 30, true),
                new clashEffect(SPREGEN, null, 100, false))));
        clashReactionMap.put("cyclone/dusk", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("cyclone/inferno", new ArrayList<>(Collections.singletonList(
                new clashEffect(ABLAZE, null, 30, true))));
        clashReactionMap.put("cyclone/glacier", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("cyclone/cyclone", new ArrayList<>(Arrays.asList(
                new clashEffect(EMPOWER, null, 100, true),
                new clashEffect(EMPOWER, null, 100, false),
                new clashEffect(ATK, 1, 100, true),
                new clashEffect(ATK, 1, 100, false))));
        clashReactionMap.put("cyclone/silva", new ArrayList<>(Collections.singletonList(
                new clashEffect(FRAZZLE, null, 20, false))));
        clashReactionMap.put("cyclone/torrent", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 80, false))));
        clashReactionMap.put("cyclone/gaia", new ArrayList<>(Collections.singletonList(
                new clashEffect(END_TURN, null, 30, true))));
        clashReactionMap.put("cyclone/thunder", new ArrayList<>(Collections.singletonList(
                new clashEffect(CURBED, null, 80, false))));
        clashReactionMap.put("cyclone/vamp", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 30, false))));
        clashReactionMap.put("cyclone/force", new ArrayList<>(Collections.singletonList(
                new clashEffect(ACTION_REDUCE, 1, 20, true))));
        clashReactionMap.put("cyclone/ghenju", new ArrayList<>(Collections.singletonList(
                new clashEffect(DISSIPATE, null, 100, false))));
        clashReactionMap.put("cyclone/dragonsvalk", new ArrayList<>(Collections.singletonList(
                new clashEffect(HALF_DMG, null, 20, true))));

        clashReactionMap.put("silva/null", new ArrayList<>(Collections.singletonList(
                new clashEffect(ATK_HEAL, null, 40, true))));
        clashReactionMap.put("silva/steel", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 35, false))));
        clashReactionMap.put("silva/venin", new ArrayList<>(Arrays.asList(
                new clashEffect(CLASH_THORNS, null, 100, true),
                new clashEffect(POISON, null, 40, true))));
        clashReactionMap.put("silva/aether", new ArrayList<>(Collections.singletonList(
                new clashEffect(AWAKENED, null, 100, true))));
        clashReactionMap.put("silva/dusk", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("silva/inferno", new ArrayList<>(Arrays.asList(
                new clashEffect(ABLAZE, null, 80, true),
                new clashEffect(CLASH_THORNS, null, 100, true))));
        clashReactionMap.put("silva/glacier", new ArrayList<>(Collections.singletonList(
                new clashEffect(CRIT_ALT, -1, 100, true))));
        clashReactionMap.put("silva/cyclone", new ArrayList<>(Collections.singletonList(
                new clashEffect(ENERVATE, null, 80, true))));
        clashReactionMap.put("silva/silva", new ArrayList<>(Collections.singletonList(
                new clashEffect(CRIT_ALT, 1, 100, true))));
        clashReactionMap.put("silva/torrent", new ArrayList<>(Arrays.asList(
                new clashEffect(ATK, 1, 100, true),
                new clashEffect(MAG, 1, 100, true))));
        clashReactionMap.put("silva/gaia", new ArrayList<>(Collections.singletonList(
                new clashEffect(ACTION_ADD, 1, 30, true))));
        clashReactionMap.put("silva/thunder", new ArrayList<>(Collections.singletonList(
                new clashEffect(END_TURN, null, 15, true))));
        clashReactionMap.put("silva/vamp", new ArrayList<>(Collections.singletonList(
                new clashEffect(INSTAKILL, null, 10, false))));
        clashReactionMap.put("silva/force", new ArrayList<>(Arrays.asList(
                new clashEffect(SPD, -1, 30, false),
                new clashEffect(CLASH_THORNS, null, 100, true))));
        clashReactionMap.put("silva/ghenju", new ArrayList<>(Collections.singletonList(
                new clashEffect(POISON, null, 100, true))));
        clashReactionMap.put("silva/dragonsvalk", new ArrayList<>(Collections.singletonList(
                new clashEffect(HALF_DMG, null, 20, true))));


        clashReactionMap.put("torrent/null", new ArrayList<>(Collections.singletonList(
                new clashEffect(BEDRAGGLE, null, 7, false))));
        clashReactionMap.put("torrent/steel", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 35, false))));
        clashReactionMap.put("torrent/venin", new ArrayList<>(Arrays.asList(
                new clashEffect(CURBED, null, 100, false),
                new clashEffect(CLASH_THORNS, null, 100, true))));
        clashReactionMap.put("torrent/aether", new ArrayList<>(Arrays.asList(
                new clashEffect(HALF_DMG, null, 20, true),
                new clashEffect(UNCRITTABLE, null, 100, true),
                new clashEffect(SPREGEN, null, 100, false))));
        clashReactionMap.put("torrent/dusk", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("torrent/inferno", new ArrayList<>(Arrays.asList(
                new clashEffect(ATK, -1, 100, false),
                new clashEffect(MAG, -1, 100, false),
                new clashEffect(NEUTRALIZE, null, 100, false))));
        clashReactionMap.put("torrent/glacier", new ArrayList<>(Collections.singletonList(
                new clashEffect(ACTION_REDUCE, 1, 80, true))));
        clashReactionMap.put("torrent/cyclone", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 80, true))));
        clashReactionMap.put("torrent/silva", new ArrayList<>(Arrays.asList(
                new clashEffect(DEF_HEAL, null, 100, false),
                new clashEffect(ATK, 1, 20, false),
                new clashEffect(MAG, 1, 20, false))));
        clashReactionMap.put("torrent/torrent", new ArrayList<>(Arrays.asList(
                new clashEffect(HALF_DMG, null, 30, false),
                new clashEffect(SPREGEN, null, 100, true),
                new clashEffect(SPREGEN, null, 100, false))));
        clashReactionMap.put("torrent/gaia", new ArrayList<>(Collections.singletonList(
                new clashEffect(ROW_SWAP, null, 30, false))));
        clashReactionMap.put("torrent/thunder", new ArrayList<>(Arrays.asList(
                new clashEffect(CLASH_THORNS, null, 100, true),
                new clashEffect(STUN, null, 20, true))));
        clashReactionMap.put("torrent/vamp", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 10, false))));
        clashReactionMap.put("torrent/force", new ArrayList<>(Collections.singletonList(
                new clashEffect(SPD, -1, 30, false))));
        clashReactionMap.put("torrent/ghenju", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("torrent/dragonsvalk", new ArrayList<>(Collections.singletonList(
                new clashEffect(HALF_DMG, null, 20, true))));

        clashReactionMap.put("gaia/null", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 7, false))));
        clashReactionMap.put("gaia/steel", new ArrayList<>(Collections.singletonList(
                new clashEffect(ACTION_REDUCE, 1, 60, true))));
        clashReactionMap.put("gaia/venin", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, false))));
        clashReactionMap.put("gaia/aether", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 20, true))));
        clashReactionMap.put("gaia/dusk", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("gaia/inferno", new ArrayList<>(Collections.singletonList(
                new clashEffect(CURBED, null, 100, false))));
        clashReactionMap.put("gaia/glacier", new ArrayList<>(Collections.singletonList(
                new clashEffect(CRIT_ALT, 1, 100, true))));
        clashReactionMap.put("gaia/cyclone", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 80, true))));
        clashReactionMap.put("gaia/silva", new ArrayList<>(Arrays.asList(
                new clashEffect(HIT_ALT, -1, 100, true),
                new clashEffect(UNCRITTABLE, null, 100, false))));
        clashReactionMap.put("gaia/torrent", new ArrayList<>(Collections.singletonList(
                new clashEffect(HALF_DMG, null, 30, false))));
        clashReactionMap.put("gaia/gaia", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 30, false))));
        clashReactionMap.put("gaia/thunder", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("gaia/vamp", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 10, false))));
        clashReactionMap.put("gaia/force", new ArrayList<>(Arrays.asList(
                new clashEffect(SPD, -1, 30, false),
                new clashEffect(DEF, -1, 30, true))));
        clashReactionMap.put("gaia/ghenju", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("gaia/dragonsvalk", new ArrayList<>(Collections.singletonList(
                new clashEffect(CRIT_ALT, 1, 100, true))));


        clashReactionMap.put("thunder/null", new ArrayList<>(Collections.singletonList(
                new clashEffect(STUN, null, 5, false))));
        clashReactionMap.put("thunder/steel", new ArrayList<>(Arrays.asList(
                new clashEffect(STUN, null, 80, false),
                new clashEffect(ELECTROMAGNET, null, 100, false))));
        clashReactionMap.put("thunder/venin", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, false))));
        clashReactionMap.put("thunder/aether", new ArrayList<>(Arrays.asList(
                new clashEffect(ENERVATE, null, 30, true),
                new clashEffect(SPREGEN, null, 100, false))));
        clashReactionMap.put("thunder/dusk", new ArrayList<>(Arrays.asList(
                new clashEffect(CRIT_ALT, 1, 100, true),
                new clashEffect(ROW_KNOCKBACK, null, 20, false))));
        clashReactionMap.put("thunder/inferno", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, false))));
        clashReactionMap.put("thunder/glacier", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, 1, 100, true))));
        clashReactionMap.put("thunder/cyclone", new ArrayList<>(Arrays.asList(
                new clashEffect(UNCRITTABLE, null, 100, true),
                new clashEffect(ACTION_REDUCE, 1, 100, true))));
        clashReactionMap.put("thunder/silva", new ArrayList<>(Collections.singletonList(
                new clashEffect(DISADVANTAGE, null, 100, true))));
        clashReactionMap.put("thunder/torrent", new ArrayList<>(Collections.singletonList(
                new clashEffect(MAG, 1, 100, true))));
        clashReactionMap.put("thunder/gaia", new ArrayList<>(Arrays.asList(
                new clashEffect(HALF_DMG, null, 40, false),
                new clashEffect(END_TURN, null, 30, true))));
        clashReactionMap.put("thunder/thunder", new ArrayList<>(Arrays.asList(
                new clashEffect(ATK, 3, 100, true),
                new clashEffect(ATK, 3, 100, false),
                new clashEffect(MAG, 3, 100, true),
                new clashEffect(MAG, 3, 100, false),
                new clashEffect(EMPOWER, null, 100, true),
                new clashEffect(EMPOWER, null, 100, false))));
        clashReactionMap.put("thunder/vamp", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 10, false))));
        clashReactionMap.put("thunder/force", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, -1, 30, false))));
        clashReactionMap.put("thunder/ghenju", new ArrayList<>(Collections.singletonList(
                new clashEffect(DISSIPATE, null, 100, false))));
        clashReactionMap.put("thunder/dragonsvalk", new ArrayList<>(Collections.singletonList(
                new clashEffect(HALF_DMG, null, 20, true))));


        clashReactionMap.put("vamp/null", new ArrayList<>(Collections.singletonList(
                new clashEffect(ATK_HEAL, null, 30, true))));
        clashReactionMap.put("vamp/steel", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 80, false))));
        clashReactionMap.put("vamp/venin", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, false))));
        clashReactionMap.put("vamp/aether", new ArrayList<>(Collections.singletonList(
                new clashEffect(STUN, null, 100, true))));
        clashReactionMap.put("vamp/dusk", new ArrayList<>(Collections.singletonList(
                new clashEffect(BANE, 1, 100, false))));
        clashReactionMap.put("vamp/inferno", new ArrayList<>(Arrays.asList(
                new clashEffect(RATTLE, null, 30, true),
                new clashEffect(ROW_KNOCKBACK, null, 100, true))));
        clashReactionMap.put("vamp/glacier", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, 1, 100, true))));
        clashReactionMap.put("vamp/cyclone", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("vamp/silva", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("vamp/torrent", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, 1, 100, true))));
        clashReactionMap.put("vamp/gaia", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 40, false))));
        clashReactionMap.put("vamp/thunder", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("vamp/vamp", new ArrayList<>(Arrays.asList(
                new clashEffect(ATK, 2, 100, true),
                new clashEffect(ATK, 2, 100, false))));
        clashReactionMap.put("vamp/force", new ArrayList<>(Collections.singletonList(
                new clashEffect(ADVANTAGE, null, 100, true))));
        clashReactionMap.put("vamp/ghenju", new ArrayList<>(Collections.singletonList(
                new clashEffect(BANE, null, 100, false))));
        clashReactionMap.put("vamp/dragonsvalk", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 20, true))));


        clashReactionMap.put("force/null", new ArrayList<>(Collections.singletonList(
                new clashEffect(ROW_KNOCKBACK, null, 30, false))));
        clashReactionMap.put("force/steel", new ArrayList<>(Collections.singletonList(
                new clashEffect(DENT, null, 80, false))));
        clashReactionMap.put("force/venin", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, false))));
        clashReactionMap.put("force/aether", new ArrayList<>(Collections.singletonList(
                new clashEffect(ENERVATE, null, 80, true))));
        clashReactionMap.put("force/dusk", new ArrayList<>(Collections.singletonList(
                new clashEffect(HALF_DMG, null, 40, false))));
        clashReactionMap.put("force/inferno", new ArrayList<>(Collections.singletonList(
                new clashEffect(HALF_DMG, null, 30, true))));
        clashReactionMap.put("force/glacier", new ArrayList<>(Arrays.asList(
                new clashEffect(CRIT_ALT, 1, 100, true),
                new clashEffect(UNBLOCKABLE, null, 100, false))));
        clashReactionMap.put("force/cyclone", new ArrayList<>(Collections.singletonList(
                new clashEffect(RATTLE, null, 30, true))));
        clashReactionMap.put("force/silva", new ArrayList<>(Arrays.asList(
                new clashEffect(ACTION_REDUCE, 1, 25, true),
                new clashEffect(UNBLOCKABLE, null, 100, true))));
        clashReactionMap.put("force/torrent", new ArrayList<>(Collections.singletonList(
                new clashEffect(UNCRITTABLE, null, 100, true))));
        clashReactionMap.put("force/gaia", new ArrayList<>(Collections.singletonList(
                new clashEffect(UNBLOCKABLE, null, 100, false))));
        clashReactionMap.put("force/thunder", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("force/vamp", new ArrayList<>(Collections.singletonList(
                new clashEffect(HALF_DMG, null, 40, true))));
        clashReactionMap.put("force/force", new ArrayList<>(Arrays.asList(
                new clashEffect(ROW_KNOCKBACK, null, 30, true),
                new clashEffect(ROW_KNOCKBACK, null, 30, false))));
        clashReactionMap.put("force/ghenju", new ArrayList<>(Collections.singletonList(
                new clashEffect(DEF_HEAL, null, 100, false))));
        clashReactionMap.put("force/dragonsvalk", new ArrayList<>(Collections.singletonList(
                new clashEffect(HALF_DMG, null, 20, true))));


        clashReactionMap.put("ghenju/null", new ArrayList<>(Arrays.asList(
                new clashEffect(BURN, null, 30, false),
                new clashEffect(POISON, null, 30, false))));
        clashReactionMap.put("ghenju/steel", new ArrayList<>(Collections.singletonList(
                new clashEffect(CORROSION, null, 80, false))));
        clashReactionMap.put("ghenju/venin", new ArrayList<>(Arrays.asList(
                new clashEffect(ATK, 1, 100, true),
                new clashEffect(ATK, -1, 100, false))));
        clashReactionMap.put("ghenju/aether", new ArrayList<>(Collections.singletonList(
                new clashEffect(ACTION_ADD, 1, 100, true))));
        clashReactionMap.put("ghenju/dusk", new ArrayList<>(Collections.singletonList(
                new clashEffect(OBEDIENT, null, 80, false))));
        clashReactionMap.put("ghenju/inferno", new ArrayList<>(Collections.singletonList(
                new clashEffect(MAG, 1, 100, true))));
        clashReactionMap.put("ghenju/glacier", new ArrayList<>(Collections.singletonList(
                new clashEffect(BLOCK_ALT, 2, 100, false))));
        clashReactionMap.put("ghenju/cyclone", new ArrayList<>(Collections.singletonList(
                new clashEffect(END_TURN, null, 100, true))));
        clashReactionMap.put("ghenju/silva", new ArrayList<>(Arrays.asList(
                new clashEffect(BURN, null, 30, false),
                new clashEffect(POISON, null, 30, false))));
        clashReactionMap.put("ghenju/torrent", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, true))));
        clashReactionMap.put("ghenju/gaia", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 100, false))));
        clashReactionMap.put("ghenju/thunder", new ArrayList<>(Arrays.asList(
                new clashEffect(DISADVANTAGE, null, 100, true),
                new clashEffect(CLASH_THORNS, null, 100, true))));
        clashReactionMap.put("ghenju/vamp", new ArrayList<>(Collections.singletonList(
                new clashEffect(DISADVANTAGE, null, 100, true))));
        clashReactionMap.put("ghenju/force", new ArrayList<>(Collections.singletonList(
                new clashEffect(OBEDIENT, null, 100, false))));
        clashReactionMap.put("ghenju/ghenju", new ArrayList<>(Arrays.asList(
                new clashEffect(EMPOWER, null, 100, true),
                new clashEffect(EMPOWER, null, 100, false),
                new clashEffect(CRIT, 1, 100, true),
                new clashEffect(CRIT, 1, 100, false))));
        clashReactionMap.put("ghenju/dragonsvalk", new ArrayList<>(Collections.singletonList(
                new clashEffect(DOUBLE_DMG, null, 30, true))));

        clashReactionMap.put("dragonsvalk/null", new ArrayList<>(Collections.singletonList(
                new clashEffect(FRAZZLE, null, 10, false))));
        clashReactionMap.put("dragonsvalk/steel", new ArrayList<>(Arrays.asList(
                new clashEffect(DENT, null, 15, false),
                new clashEffect(UNBLOCKABLE, null, 100, false))));
        clashReactionMap.put("dragonsvalk/venin", new ArrayList<>(Collections.singletonList(
                new clashEffect(CLASH_THORNS, null, 100, false))));
        clashReactionMap.put("dragonsvalk/aether", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 80, true))));
        clashReactionMap.put("dragonsvalk/dusk", new ArrayList<>(Collections.singletonList(
                new clashEffect(FLINCH, null, 10, false))));
        clashReactionMap.put("dragonsvalk/inferno", new ArrayList<>(Collections.singletonList(
                new clashEffect(ATK_HEAL, null, 35, true))));
        clashReactionMap.put("dragonsvalk/glacier", new ArrayList<>(Collections.singletonList(
                new clashEffect(UNBLOCKABLE, null, 100, false))));
        clashReactionMap.put("dragonsvalk/cyclone", new ArrayList<>(Collections.singletonList(
                new clashEffect(DISADVANTAGE, null, 100, true))));
        clashReactionMap.put("dragonsvalk/silva", new ArrayList<>(Collections.singletonList(
                new clashEffect(ADVANTAGE, 1, 25, true))));
        clashReactionMap.put("dragonsvalk/torrent", new ArrayList<>(Collections.singletonList(
                new clashEffect(UNCRITTABLE, null, 100, true))));
        clashReactionMap.put("dragonsvalk/gaia", new ArrayList<>(Arrays.asList(
                new clashEffect(ACTION_REDUCE, 1, 25, true),
                new clashEffect(UNBLOCKABLE, 1, 100, true))));
        clashReactionMap.put("dragonsvalk/thunder", new ArrayList<>(Collections.singletonList(
                new clashEffect(STUN, null, 10, true))));
        clashReactionMap.put("dragonsvalk/vamp", new ArrayList<>(Collections.singletonList(
                new clashEffect(CRIT_ALT, 1, 100, true))));
        clashReactionMap.put("dragonsvalk/force", new ArrayList<>(Collections.singletonList(
                new clashEffect(TBD, null, 30, true))));
        clashReactionMap.put("dragonsvalk/ghenju", new ArrayList<>(Collections.singletonList(
                new clashEffect(POISON, null, 15, true))));
        clashReactionMap.put("dragonsvalk/dragonsvalk", new ArrayList<>(Arrays.asList(
                new clashEffect(AWAKENED, null, 100, true),
                new clashEffect(AWAKENED, null, 100, false))));

    }



    public boolean percentagize(int percentage){
        return Math.random() * 100 >= percentage;
    }

    public attackValues preAttackClashUpdate(attackValues previousVals, int atkrId, int defId, boolean isPreview){


        String affinityStr = combatants.getBattleToken(atkrId).getAffinity().toString().toLowerCase() + "/" +
                combatants.getBattleToken(defId).getAffinity().toString().toLowerCase();

        ArrayList<clashEffect> respClashEffects = clashReactionMap.get(affinityStr);


        if(respClashEffects != null) {
            for (int i = 0; i < respClashEffects.size(); i++) {

                clashEffect curEff = respClashEffects.get(i);

                if ((isPreview && curEff.percentage == 100) || (!isPreview && percentagize(curEff.percentage))) {


                    switch (curEff.reaction) {
                        case NO_DMG:
                            previousVals.minDMG = 0;
                            break;
                        case HALF_DMG:
                            previousVals.minDMG /= 2;
                            break;
                        case DOUBLE_DMG:
                            previousVals.minDMG *= 2;
                            break;

                        case ADVANTAGE:
                            previousVals.applyAdvantage();
                            break;
                        case DISADVANTAGE:
                            previousVals.applyDisadvantage();
                            break;

                        case UNBLOCKABLE:
                            previousVals.blockChance = 0;
                            break;
                        case UNCRITTABLE:
                            previousVals.hitChance = 0;
                            break;

                        case HIT_ALT:
                            previousVals.applyStatChange(attackValues.attackVal.HIT, curEff.degree);
                            break;
                        case CRIT_ALT:
                            previousVals.applyStatChange(attackValues.attackVal.CRIT, curEff.degree);
                            break;
                        case BLOCK_ALT:
                            previousVals.applyStatChange(attackValues.attackVal.BLOCK, curEff.degree);
                            break;

                        case DEF_HEAL:
                            previousVals.minDMG *= -1;
                            break;

                        case RANDOM:
                            // make this recursify back onto this switch with a randomClashReaction
//                        clashReaction.randomClashReaction();
                            break;
                        case TBD:
                        default:

                    }

                }

            }
        }
        return previousVals;

    }
    public void postAttackClashUpdate(affinity atkAff, int atkrId, int defId){

        battleToken atkBT = combatants.getBattleToken(atkrId);
        battleToken defBT = combatants.getBattleToken(defId);


        String affinityStr = atkAff.toString().toLowerCase() + "/" +
                defBT.getAffinity().toString().toLowerCase();

        ArrayList<clashEffect> respClashEffects = clashReactionMap.get(affinityStr);

        battleToken curBT;

        if(respClashEffects != null) {
            for (int i = 0; i < respClashEffects.size(); i++) {

                clashEffect curEff = respClashEffects.get(i);

                if (percentagize(curEff.percentage)) {

                    if (curEff.affectsUser) {
                        curBT = atkBT;
                    } else {
                        curBT = defBT;
                    }


                    switch (curEff.reaction) {

                        case BANE:
                            combatants.applyEffect(curEff, atkrId, defId, -1);
                            break;

                        case BLEED:
                            combatants.applyEffect(curEff, atkrId, defId, 4);
                            break;

                        case BURN:
                        case POISON:
                            combatants.applyEffect(curEff, atkrId, defId, 3);
                            break;

                        case BEDRAGGLE:
                        case RAGE:
                            combatants.applyEffect(curEff, atkrId, defId, 2);
                            break;

                        case STUN:
                        case FREEZE:
                        case RATTLE:
                        case ENERVATE:
                            combatants.applyEffect(curEff, atkrId, defId, 1);
                            break;

                        case ATK:
                        case DEF:
                        case MAG:
                        case INT:
                        case SPD:
                        case CRIT:

                        case REGEN:
                        case ROW_SWAP:
                        case ROW_KNOCKBACK:
                        case CLASH_THORNS:
                        case ENGAGE:
                        case INTIMIDATE:
                            break;

                        case INSTAKILL:
                            combatants.applyEffect(curEff, atkrId, defId, null);
                            break;

                        case SPREGEN:
                            curBT.setSP(curBT.getSP() + 20);
                            break;

//                    case ATK_HEAL:

                        case ACTION_ADD:
                            curBT.altActionCount(1);
                            break;
                        case ACTION_REDUCE:
                            curBT.altActionCount(-1);
                            break;
                        case END_TURN:
                            curBT.terminateTurn();
                            break;

                        case DENT:
                        case THAW:
                        case CORROSION:
                        case ABLAZE:
                        case ANNEAL:
                        case FLINCH:
                        case BRITTLE:
                        case EMPOWER:
                        case EXALTED:
                        case FRAZZLE:
                        case AWAKENED:
                        case OBEDIENT:
                        case DISSIPATE:
                        case NEUTRALIZE:
                        case ELECTROMAGNET:
                        case CURBED:
                            curBT.addStatusEffect(curEff.reaction);
                            break;

                        default:
                            break;
                    }
                }
            }
        }
    }

//    public ArrayList<clashEffect> getFluxActionEffects(int atkrId, int defId){
//
//        battleToken atkBT = combatants.getBattleToken(atkrId);
//        battleToken defBT = combatants.getBattleToken(defId);
//
//
//        String affinityStr = atkBT.getAffinity().toString().toLowerCase() + "/" +
//                defBT.getAffinity().toString().toLowerCase();
//
//        return clashReactionMap.get(affinityStr);
//    }
    public void fluxAction(int atkrId, int defId){

        battleToken atkBT = combatants.getBattleToken(atkrId);
        battleToken defBT = combatants.getBattleToken(defId);


        String affinityStr = atkBT.getAffinity().toString().toLowerCase() + "/" +
                defBT.getAffinity().toString().toLowerCase();

        ArrayList<clashEffect> respClashEffects = clashReactionMap.get(affinityStr);

        battleToken curBT;

        if(respClashEffects != null) {
            for (int i = 0; i < respClashEffects.size(); i++) {

                clashEffect curEff = respClashEffects.get(i);

                //flux always triggers. no percentage checks
//            if(percentagize(curEff.percentage)) {
                if (hasFluxActionEffect(curEff.reaction)) {

                    if (curEff.affectsUser) {
                        curBT = atkBT;
                    } else {
                        curBT = defBT;
                    }


                    switch (curEff.reaction) {

                        case ADVANTAGE:
                            curBT.addStatusEffect(EX_ADVANTAGE);
                            break;
                        case DISADVANTAGE:
                            curBT.addStatusEffect(EX_DISADVANTAGE);
                            break;


                        case BANE:
                            combatants.applyEffect(curEff, atkrId, defId, -1);
                            break;
                        case ENERVATE:
                        case BLEED:
                            combatants.applyEffect(curEff, atkrId, defId, 5);
                            break;

                        case BURN:
                        case POISON:
                            combatants.applyEffect(curEff, atkrId, defId, 4);
                            break;

                        case FREEZE:
                        case BEDRAGGLE:
                        case RAGE:
                        case RATTLE:
                            combatants.applyEffect(curEff, atkrId, defId, 3);
                            break;

                        case STUN:
                            combatants.applyEffect(curEff, atkrId, defId, 1);
                            break;

                        case ATK:
                        case DEF:
                        case MAG:
                        case INT:
                        case SPD:
                        case CRIT:

                        case REGEN:
                        case ROW_SWAP:
                        case ROW_KNOCKBACK:
                        case CLASH_THORNS:
                        case ENGAGE:
                        case INTIMIDATE:
                            break;

                        case INSTAKILL:
                            combatants.applyEffect(curEff, atkrId, defId, null);
                            break;

                        case SPREGEN:
                            curBT.setSP(curBT.getSP() + 20);
                            break;

                        case ACTION_ADD:
                            curBT.altActionCount(1);
                            break;
                        case ACTION_REDUCE:
                            curBT.altActionCount(-1);
                            break;
                        case END_TURN:
                            curBT.terminateTurn();
                            break;

                        case DENT:
                        case THAW:
                        case CORROSION:
                        case ABLAZE:
                        case ANNEAL:
                        case FLINCH:
                        case BRITTLE:
                        case EMPOWER:
                        case EXALTED:
                        case FRAZZLE:
                        case AWAKENED:
                        case OBEDIENT:
                        case DISSIPATE:
                        case NEUTRALIZE:
                        case ELECTROMAGNET:
                        case CURBED:
                            curBT.addStatusEffect(curEff.reaction);
                            break;

                        case RANDOM:
                            // make this recursify back onto this switch with a randomClashReaction
//                        clashReaction.randomClashReaction();
                            break;
                        case TBD:
                        default:


                    }


                    fightMidgrounds.post(combatants.getBattleToken(currentTurnCharId).getName(),
                            curBT.getName(), "Flux Action", fightUpdater.textType.getTextType(curEff.reaction, true),
                            null, null);
                }

            }
        }
    }
    public boolean hasFluxActionEffect(statusEffect effect){
        switch(effect){



            case ATK:
            case DEF:
            case MAG:
            case INT:
            case SPD:
            case CRIT:
            case SPREGEN:

            case BANE:
            case ENERVATE:
            case BLEED:
            case BURN:
            case POISON:
            case FREEZE:
            case BEDRAGGLE:
            case RAGE:
            case RATTLE:
            case STUN:


            case ADVANTAGE:
            case DISADVANTAGE:

            // does thorns dmg x3
            case CLASH_THORNS:

            case ACTION_ADD:
            case ACTION_REDUCE:

            case DENT:
            case THAW:
            case CORROSION:
            case ABLAZE:
            case ANNEAL:
            case FLINCH:
            case BRITTLE:
            case EMPOWER:
            case EXALTED:
            case FRAZZLE:
            case AWAKENED:
            case OBEDIENT:
            case DISSIPATE:
            case NEUTRALIZE:
            case ELECTROMAGNET:
            case CURBED:
            case REGEN:
            case ROW_SWAP:
            case ROW_KNOCKBACK:
            case ENGAGE:
            case INTIMIDATE:
            case INSTAKILL:
            case RANDOM:

                return true;


            default:
                return false;
        }
    }

    public ArrayList<clashEffect> getClashReactions(affinity aff1, affinity aff2){
        String key = aff1.toString().toLowerCase() + "/" + aff2.toString().toLowerCase();
        return clashReactionMap.get(key);
    }


    public static class clashEffect{


        Integer degree, percentage;
        boolean affectsUser;

        statusEffect reaction;



        public clashEffect(statusEffect reaction, Integer degree, int percentage, boolean affectsUser){
            this.degree = degree;
            this.percentage = percentage;

            this.affectsUser = affectsUser;
            this.reaction = reaction;
        }

        public Integer getDegree() {
            return degree;
        }

        public Integer getPercentage() {
            return percentage;
        }

        public statusEffect getReaction() {
            return reaction;
        }

        public boolean getAffectsUser() {
            return affectsUser;
        }
    }

}
