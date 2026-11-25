package declatoration.simulatorial;

import static declatoration.simulatorial.enums.effectiveness.EFFECTIVE;
import static declatoration.simulatorial.enums.effectiveness.INEFFECTIVE;
import static declatoration.simulatorial.enums.effectiveness.NEUTRAL;
import static declatoration.simulatorial.enums.weaponType.AXE;
import static declatoration.simulatorial.enums.weaponType.BEAST;
import static declatoration.simulatorial.enums.weaponType.BOW;
import static declatoration.simulatorial.enums.weaponType.BROADSWORD;
import static declatoration.simulatorial.enums.weaponType.CANNON;
import static declatoration.simulatorial.enums.weaponType.CHAIN;
import static declatoration.simulatorial.enums.weaponType.DAGGER;
import static declatoration.simulatorial.enums.weaponType.DRAGON;
import static declatoration.simulatorial.enums.weaponType.DUSTER;
import static declatoration.simulatorial.enums.weaponType.FLAIL;
import static declatoration.simulatorial.enums.weaponType.HALBERD;
import static declatoration.simulatorial.enums.weaponType.HEX;
import static declatoration.simulatorial.enums.weaponType.MAGIC;
import static declatoration.simulatorial.enums.weaponType.POLEARM;
import static declatoration.simulatorial.enums.weaponType.POTION;
import static declatoration.simulatorial.enums.weaponType.PUNCH;
import static declatoration.simulatorial.enums.weaponType.RIFLE;
import static declatoration.simulatorial.enums.weaponType.SCYTHE;
import static declatoration.simulatorial.enums.weaponType.SPEAR;
import static declatoration.simulatorial.enums.weaponType.SPOT;
import static declatoration.simulatorial.enums.weaponType.STAFF;
import static declatoration.simulatorial.enums.weaponType.SWORD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import declatoration.simulatorial.enums.effectiveness;
import declatoration.simulatorial.enums.weaponType;
import declatoration.simulatorial.fight.actionBoostParams;

public class weaponTable {

    boolean initialized = false;

    HashMap <String, effectiveness> weaponEffMap = new HashMap<>();

    ArrayList<weaponType> oranges = new ArrayList<>();
    ArrayList<weaponType> purples = new ArrayList<>();
    ArrayList<weaponType> greens = new ArrayList<>();


    public weaponTable(){

        initialize();
    }

    public effectiveness getWeaponEffectiveness(weaponType atk, weaponType def){

        String str;

        if(isOfSameColor(atk, def)){
            str = atk.toString().toLowerCase() + "/" + def.toString().toLowerCase();
        } else{
            str = getWeaponColor(atk) + "/" + getWeaponColor(def);
        }

        return weaponEffMap.getOrDefault(str, NEUTRAL);
    }

    public actionBoostParams getWeaponEffectivenessBonuses(effectiveness eff, int atkVal){

        actionBoostParams effBonuses = new actionBoostParams();

        switch (eff){
            case NEUTRAL:
            default:
                effBonuses.setParams(0f, 0f, 0f, 0f);
                break;
            case EFFECTIVE:
                effBonuses.setParams((atkVal * 0.08f), 15f, 8f,8f);
                break;
            case INEFFECTIVE:
                effBonuses.setParams(-(atkVal * 0.1f), -12f, -8f, -8f);
        }

        return effBonuses;
    }

    private String getWeaponColor(weaponType weapon){
        String colorName = "";
        if(oranges.contains(weapon)){
            colorName = "orange";
        } else if (purples.contains(weapon)) {
            colorName = "purple";
        } else if (greens.contains(weapon)) {
            colorName = "green";
        }
        return colorName;
    }

    private boolean isOfSameColor(weaponType a, weaponType b){
        return (Objects.equals(getWeaponColor(a), getWeaponColor(b)));
    }

    private void initialize(){

        oranges.add(SWORD);
        oranges.add(SPEAR);
        oranges.add(SCYTHE);
        oranges.add(AXE);
        oranges.add(STAFF);
        oranges.add(PUNCH);
        oranges.add(POTION);
        oranges.add(SPOT);

        purples.add(MAGIC);
        purples.add(HEX);
        purples.add(BEAST);
        purples.add(DRAGON);
        purples.add(DUSTER);
        purples.add(DAGGER);

        greens.add(BOW);
        greens.add(RIFLE);
        greens.add(FLAIL);
        greens.add(CHAIN);
        greens.add(CANNON);

        greens.add(BROADSWORD);
        greens.add(POLEARM);
        greens.add(HALBERD);


        weaponEffMap.put("orange/purple", EFFECTIVE);
        weaponEffMap.put("orange/green", INEFFECTIVE);
        weaponEffMap.put("purple/orange", INEFFECTIVE);
        weaponEffMap.put("purple/green", EFFECTIVE);
        weaponEffMap.put("green/orange", EFFECTIVE);
        weaponEffMap.put("green/purple", INEFFECTIVE);



//        weaponEffMap.put("sword/sword", NEUTRAL);
        weaponEffMap.put("sword/spear", INEFFECTIVE);
        weaponEffMap.put("sword/axe", EFFECTIVE);
//        weaponEffMap.put("sword/punch", NEUTRAL);
//        weaponEffMap.put("sword/staff", NEUTRAL);

        weaponEffMap.put("spear/sword", EFFECTIVE);
//        weaponEffMap.put("spear/spear", NEUTRAL);
        weaponEffMap.put("spear/axe", INEFFECTIVE);
//        weaponEffMap.put("spear/punch", NEUTRAL);
//        weaponEffMap.put("spear/staff", NEUTRAL);

        weaponEffMap.put("axe/sword", INEFFECTIVE);
        weaponEffMap.put("axe/spear", EFFECTIVE);
//        weaponEffMap.put("axe/axe", NEUTRAL);
//        weaponEffMap.put("axe/punch", NEUTRAL);
//        weaponEffMap.put("axe/staff", NEUTRAL);
//
//        weaponEffMap.put("staff/sword", NEUTRAL);
//        weaponEffMap.put("staff/spear", NEUTRAL);
//        weaponEffMap.put("staff/axe", NEUTRAL);
//        weaponEffMap.put("staff/punch", NEUTRAL);
//        weaponEffMap.put("staff/staff", NEUTRAL);
//
//        weaponEffMap.put("punch/sword", NEUTRAL);
//        weaponEffMap.put("punch/spear", NEUTRAL);
//        weaponEffMap.put("punch/axe", NEUTRAL);
//        weaponEffMap.put("punch/punch", NEUTRAL);
//        weaponEffMap.put("punch/staff", NEUTRAL);


        weaponEffMap.put("broadsword/polearm", INEFFECTIVE);
        weaponEffMap.put("broadsword/halberd", EFFECTIVE);

        weaponEffMap.put("polearm/broadsword", EFFECTIVE);
        weaponEffMap.put("polearm/halberd", INEFFECTIVE);

        weaponEffMap.put("halberd/broadsword", INEFFECTIVE);
        weaponEffMap.put("halberd/polearm", EFFECTIVE);

        initialized = true;

    }
}
