package declatoration.simulatorial.enums;

public enum stat {
    HP, ATK, DEF, MAG, INT, SPD, LCK,
    HIT, CRIT, PHYSAVO, MAGAVO, SP, SPREGEN, BLOCK, RES, AVO,
    RANDOM, NULL, HIGHEST, ALL;

    public static final stat[] statArray = new stat[]{HP, ATK, DEF, MAG, INT, SPD, LCK,
                HIT, CRIT, PHYSAVO, MAGAVO, SP, SPREGEN, BLOCK, RES};
    public static final stat[] statCardStatArray = new stat[]{ATK, DEF, MAG, INT, SPD, LCK,
            HIT, CRIT, PHYSAVO, MAGAVO, BLOCK, RES};

    public static stat getStatEnum(String str){
        String temp = "NULL";
        if(str!=null) {
            temp = str.toUpperCase();
        }

        return stat.valueOf(temp);
    }

}
