package declatoration.simulatorial.enums;

public enum weaponType {
    SWORD,
    SPEAR,
    SCYTHE,
    AXE,

    PUNCH,
    POTION,
    STAFF,
    SPOT,


    MAGIC,
    HEX,
    BEAST,
    DUSTER,
    DRAGON,


    BROADSWORD,
    POLEARM,
    HALBERD,

    BOW,
    CANNON,
    DAGGER,
    RIFLE,
    FLAIL,
    CHAIN,

    STATUS,
    USER_WEAPON;


    public static weaponType getWeaponType(String str, boolean isStatus){
        if(str != null) {
            String finalStr = str;
            if (finalStr.equals("club") || finalStr.equals("CLUB")) {
                finalStr = "axe";
            }
            if (finalStr.equals("heal") || finalStr.equals("HEAL")) {
                finalStr = "staff";
            }
            return weaponType.valueOf(finalStr.toUpperCase());
        } else {
            if(isStatus){
                return STATUS;
            } else {
                return USER_WEAPON;
            }
        }
    }
    public static weaponType getRandomWeaponTypeForHazelAdapt(String colorName, boolean isPhysical){
        weaponType weapon;

        switch (colorName){
            case "green":
                switch ((int) (Math.random() * 3)){
                    case 0:
                        weapon = BROADSWORD;
                        break;
                    case 1:
                    default:
                        weapon = POLEARM;
                        break;
                    case 2:
                        weapon = HALBERD;
                        break;
                }
                break;
            case "purple":
                if (isPhysical){
                    weapon = BEAST;
                } else {
                    weapon = MAGIC;
                }
                break;
            case "orange":
            default:
                switch ((int) (Math.random() * 3)){
                    case 0:
                        weapon = SWORD;
                        break;
                    case 1:
                    default:
                        weapon = SPEAR;
                        break;
                    case 2:
                        weapon = AXE;
                        break;
                }
                break;
        }
        return weapon;
    }
}
