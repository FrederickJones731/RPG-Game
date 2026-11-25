package declatoration.simulatorial.enums;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum statusEffect {

    // direct status EFFECTS / char-proper tags

    // electrics cant be stunned
    STUN(10,0),
    // dusk aff cant sleep, sleeping chars are woken up when they are dealt dmg to
    SLEEP(8,1),
    // freeze also stuns and also does damage (SUPER RARE effect) (glacier aff cant be frozen)
    // reduced phys dmg, 3 hits before break, reduced spd during and after
    FREEZE(9,2),
    //venin cant be poisoned, somewhat lowers MAG and INT
    POISON(5,3),
    // fire aff cant be burned, somewhat lowers ATK and DEF
    BURN(5,4),
    //easy to trigger, just does dmg really
    BLEED(4,5),
    // -2 hit, temporarily
    RATTLE(7,6),
    //ghenju cant be intimidated
    INTIMIDATE(4,7),
    // lowers speed :)
    BEDRAGGLE(7,8),
    // high dmg low hit, attack random enemy with random move
    RAGE(8,9),
    // null spregen crit & block, -1 action for min of 1
    ENERVATE(7,10),
    // target must attack attacker
    ENGAGE(7,11),
    // t'tania status effect. grants advantage,
    BANE(4,12),
    // all close ranged attacks are buffed x1.3, all range 2+ are /1.3, until chars next turn
    TOPPLE(4,13),
    // 50% chance for attacks to fail
    CHARM(8,14),


    WHIRLPOOL(4,15),

    // grapple RULES -
    // grappler gets advantage on target. grapplee gets disadvantage.
    // grapplee is considered toppled, and can only attack the grappler.
    // both get SPD & AVO -1.
    // other chars get advantage on a grappling char.
    // can only grapple 1 char at a time, cant grapple someone who is already grappled.
    // grapple gets released on certain moves, upon taking over 50% in one action, or if YOU get grappled.
    GRAPPLE(7, -54),
    GRAPPLED(7, -55),
    SWALLOW(7, -56),
    SWALLOWED(10, -57),
    GRAPPLE_RELEASE(0, -58),


    RANDOM(0,-1),

    // lasting effects
    // only need to add aggro
    // attack is only used with xeno, avo is unused. rip
    EX_ATTACK_MOD(3,16),
    EX_AVO_MOD(2,17),
    EX_AGGRO_MOD(3,-2),
    EX_CRIT(1,18),
    EX_ADVANTAGE(2,19),
    EX_DISADVANTAGE(2,20),

    MOVE_NEXT(0,21),

    //done
    REGEN(1,22),
    IMMUNITY(5, 73),
    RAMPART(4,23),
    COUNTER(7,24),
    THORNS(6,25),
    //evades next attack, whether it hits or not
    EVADE(3,26),
    // evades next attack that would otherwise hit
    EVADE_DX(3,27),
    // ignores block / def / int / avo boosts
    INFILTRATE(4,28),

    // turn long variants of these effects
    ADVANTAGE_DX(2,29),
    DISADVANTAGE_DX(2,30),
    ALWAYS_HIT(0,-3),
    ALWAYS_CRIT(0, -4),

    ASSIST_UP(0,31),
    // to be used with trigger tag. Immune to whatever is written .contains()
    IMMUNE(0, -5),
    // yes metamorph is a status effect, it doesnt change the actual aff in chars proper
    METAMORPH(0, 32),
    RELOAD(0,33),

    RESET_AGGRO(0,-6),
    AGGRO_DOWN(0,-7),
    AGGRO_UP(0, -8),

    STAT_STEAL_SINGLE(0,34),
    STAT_STEAL_ALL(0,35),

    ASSIST(3, -9),
    DEFENDED(6, -10),
    UNTARGETABLE(6, 36),

    // hazel adapt
    TURN_WEAPON_ADVANTAGE(3,37),
    CHAOS_RIFT(10, 72),
    AEGIS_SHIELD(7, 74),
    NERD(5, 75),

    // reform row 2 if it has fallen
    REFORM(0, -11),
    AMBIENCE_BLOOD_MOON(0, -12),

    CLASH_EFFECT(0, -13),



    //action TAGS / effects

    BALM(0,38),
    HEAL(0,39),
    // takes 20 sp from target, gives to char
    // for normal sp heal use SPREGEN tag
    SP_ABSORB(0,40),

    REVIVIFY(0,-14),
    CURE_STATUS(0,41),
    COPY_STAT_CHANGES(0,42),
    RESET_STAT_CHANGES(0,43),
    APPLY_TABLE_EFFECT(0, -15),

    EX_STAT_SWAP(0, 44),

    // for atks that let you end with these actions built-in
    // dante's play dead uses this for several turns, assume you can do that
    FOCUS(0, -16),
    GUARD(0, -17),

    // attack triggerables -
    ADVANTAGE(0, -18),
    DISADVANTAGE(0, -19),
    COMBINE_OFF_STATS(0, -52),
    FLIP_DEF(0, -20),
    DMG_MOD(0, -21),
    INSTAKILL(0, 45),
    STAT_ALTER(0, 46),
    DMG_SCALING(0, -22),


    MISS(0, -23),
    CRITICAL_HIT(0, -24),

    //after attack
    STAT_RAISE(0, 47),
    STAT_DROP(0,48),

    ATK_HEAL(0, -25),
    CHAIN_ATTACK(0, -26),
    RECOIL(0, -27),

    ROW_LURE(0,49),
    ROW_KNOCKBACK(0,50),
    //switches current row -> inflicted row action
    ROW_SWAP(0,51),
    ROW_LOCKDOWN(5,52),

    ELEMENTAL_BURST(0,-28),
    RUNE_BURST(0, -57),
    DIVINE_BURST(0, -59),


    IGNORE_STAT_CHANGES(0, -29),
    IGNORE_ENEMY_BUFFS(0, -30),
    IGNORE_ATK_INEFF(0, -31),

    REINFORCE(0, -32),



    // clashReactions
    DEF_HEAL(0, -33),

    SET_HP(0, -53),
    ATK(0,-34), DEF(0,-35), MAG(0,-36), INT(0,-37),
    SPD(0,-38), CRIT(0,-39),

    ACTION_ADD(0, 53),
    ACTION_REDUCE(0, 54),
    END_TURN(0,-40),

    SPREGEN(0, 55),
    CLASH_THORNS(0, -41),


    HIT_ALT(0, -42),
    CRIT_ALT(0, -43),
    BLOCK_ALT(0, -44),

    UNBLOCKABLE(0, -45),
    UNCRITTABLE(0, -46),
    DOUBLE_DMG(0, -47),
    HALF_DMG(0, -48),
    NO_DMG(0, -49),


    BRITTLE(5,56), EXALTED(5,57), CURBED(5,58), FRAZZLE(5,59),
    ABLAZE(5,60), ANNEAL(5,61),
    CORROSION(5,62), EMPOWER(7,63), ELECTROMAGNET(5, 64),
    NEUTRALIZE(5, 65), THAW(5, 66),
    AWAKENED(5, 67), FLINCH(5, 68), DISSIPATE(5, 69), DENT(5, 70),
    OBEDIENT(5, 71),

    TBD(0, -50),

    NULL(0, -51);



    public final Integer priority;
    public final Integer serial;

    statusEffect(Integer priority, Integer serial) {
        this.priority = priority;
        this.serial = serial;
    }

    // 122
    // damn

    public static statusEffect getStatusEnum(String str){
        try {
            if (str != null) {
                return statusEffect.valueOf(str.toUpperCase());
            }
        } catch (Exception ignored){}

        return NULL;

    }
//    private static final List<statusEffect> VALUES =
//            Collections.unmodifiableList(Arrays.asList(values()));
//    private static final int SIZE = VALUES.size();
    private static final Random RAND = new Random();

    public static statusEffect randomStatus()  {
        ArrayList<statusEffect> allEffects = new ArrayList<>();

        statusEffect[] vals = values();
        for (int i = 0; i < vals.length; i++) {
            if(vals[i].serial > 0 && vals[i].serial < 54 || vals[i].serial > 71){
                allEffects.add(vals[i]);
            }
        }
        return allEffects.get(RAND.nextInt(allEffects.size()));
    }

}
