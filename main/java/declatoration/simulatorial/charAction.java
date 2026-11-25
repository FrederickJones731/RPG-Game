package declatoration.simulatorial;

import static declatoration.simulatorial.enums.damageType.HEAL;
import static declatoration.simulatorial.enums.damageType.MAG;
import static declatoration.simulatorial.enums.damageType.PHYS;
import static declatoration.simulatorial.enums.damageType.STATUS;

import java.util.ArrayList;

import declatoration.simulatorial.enums.affinity;
import declatoration.simulatorial.enums.damageType;
import declatoration.simulatorial.enums.statusEffect;
import declatoration.simulatorial.enums.target;
import declatoration.simulatorial.enums.weaponType;

public class charAction {

    private boolean hasBonusEffects = false;

    private String name;
    private int iconResource;
    private String actionDesc;

    private double damageMod;
    private double hitMod, critMod, aggroMod;
    private ArrayList<Integer> range;

    private int cooldown;
    private int SPUsage;
    private int actionUse = 1;
    private int numOfAttacks, visualNumOfAttacks;
    private target affectedTarget;
    private ArrayList<bonusEffects> actionBonusEffects = new ArrayList<>();

    private damageType damageType;
    private affinity affinity;
    private weaponType weapon;
    private ArrayList<String> tags;         // sword, fire type, physical etc.



    // only db creates charActions, so this is the only constructor needed

    public charAction() {
        tags = new ArrayList<>();
        range = new ArrayList<>();
        setSPUsage(0);
    }

    //getters setters

    public void setName(String name) {
        this.name = name;
    }
    public void setIconResource(int iconResource) { this.iconResource = iconResource; }
    public void setActionDesc(String actionDesc) { this.actionDesc = actionDesc; }

    public void setAffectedTarget(target affectedTarget) {
        this.affectedTarget = affectedTarget;
    }
    public void setSPUsage(int SPUsage) {
        this.SPUsage = SPUsage;
    }
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
    public void setActionUse(int actionCount){
        this.actionUse = actionCount;
    }
    public void setNumOfAttacks(String numOfAttacks) {
        if(numOfAttacks.contains("/")){
            if(numOfAttacks.charAt(0) == '/'){
                this.numOfAttacks = 1;
                this.visualNumOfAttacks = Integer.parseInt(numOfAttacks.substring(1));
            } else{
                this.numOfAttacks = Integer.parseInt(String.valueOf(numOfAttacks.charAt(0)));
                this.visualNumOfAttacks = Integer.parseInt(numOfAttacks.substring(2));
            }
        } else{
            this.numOfAttacks = Integer.parseInt(numOfAttacks);
            this.visualNumOfAttacks = 1;
        }

    }

    public void setDamageType(String type){
        switch (type){
            case "phys":
                this.damageType = PHYS;
                break;
            case "mag":
                this.damageType = MAG;
                break;
            case "heal":
                this.damageType = HEAL;
                break;
            case "status":
            default:
                this.damageType = STATUS;
        }
    }
    public void setDamageType(damageType dt){
        this.damageType = dt;
    }
    public void setAffinity(affinity str){
        if(str != null) {
            affinity = str;
//            tags.set(3, str.toString().toLowerCase());
        } else{
            affinity = declatoration.simulatorial.enums.affinity.NULL;
        }
    }
    public void setWeaponType(weaponType weaponType){
        this.weapon = weaponType;
    }
    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public void setDamageMod(double damageMod) {
        this.damageMod = damageMod;
    }
    public void setHitMod(double hitMod) { this.hitMod = hitMod; }
    public void setCritMod(double critMod) { this.critMod = critMod; }
    public void setAggroMod(float aggroMod) {this.aggroMod = aggroMod; }
    public void setRange(String range) {

        this.range = new ArrayList<>();

        if (!(range == null)) {
            for (int i = 0; i < range.length(); i += 2) {
                this.range.add(range.charAt(i) - 48);
            }
        }

    }

    public void setActionBonusEffects(ArrayList<bonusEffects> be){
        this.actionBonusEffects = be;
        this.hasBonusEffects = true;
    }



    public String getName() {
        return name;
    }
    public String getActionDesc() {
        return this.actionDesc;
    }
    public int getIconResource() {
        return this.iconResource;
    }

    public target getAffectedTarget() {
        return affectedTarget;
    }
    public int getSPUsage() {
        return this.SPUsage;
    }
    public int getCooldown() {
        return cooldown;
    }
    public int getActionUse() { return actionUse; }
    public int getNumOfAttacks() {
        return numOfAttacks;
    }
    public int getVisualNumOfAttacks() {
        return visualNumOfAttacks;
    }

    public damageType getDamageType(){ return this.damageType;}
    public boolean getIsMag() {
        return this.damageType == MAG;
    }
    public affinity getAffinity() { return affinity; }
    public ArrayList<String> getTags() {
        return tags;
    }
    public weaponType getWeaponType(){ return this.weapon; }

    public boolean getTargetsAlly(){
        return (affectedTarget.toString().contains("ALLY") || affectedTarget.toString().contains("ALLIES")
        || affectedTarget.toString().contains("SELF"));
    }

    public double getDamageMod() {
        return damageMod;
    }
    public double getHitMod() { return hitMod; }
    public double getCritMod() { return critMod; }
    public double getAggroMod() { return aggroMod; }
    public ArrayList<Integer> getRange() {
        return this.range;
    }

    public boolean hasBonusEffects() { return hasBonusEffects; }
    public boolean hasBonusEffect(statusEffect effect){
        for (int i = 0; i < actionBonusEffects.size(); i++) {
            if(actionBonusEffects.get(i).getStatus() == effect){
                return true;
            }
        }
        return false;
    }
    public bonusEffects getBonusEffect(statusEffect effect){
        for (int i = 0; i < actionBonusEffects.size(); i++) {
            if(actionBonusEffects.get(i).getStatus() == effect){
                return actionBonusEffects.get(i);
            }
        }
        return null;
    }
    public ArrayList<bonusEffects> getBonusEffects(){
        return this.actionBonusEffects;
    }

    public int getSymbolResId(int i) {

        int resource = R.drawable.bloo;
        switch (i) {
            // mag or phys symbol
            case 0:
                if(tags.get(2) != null) {
                    resource = getImageRes.getAffinityImageResource(declatoration.simulatorial.enums.affinity.getAffinity(tags.get(2)));
                }
                break;

            // weapon type symbol
            case 1:
                if (tags.get(1) != null) {
                    resource = getImageRes.getWeaponIcon(weaponType.getWeaponType(tags.get(1), damageType == STATUS));
                }
                break;


            // affinity symbol
            case 2:
                switch (damageType){
                    case MAG:
                        resource = R.drawable.symbol_mag;
                        break;
                    case PHYS:
                    default:
                        resource = R.drawable.symbol_physical;
                        break;
                    case HEAL:
                        resource = R.drawable.symbol_heal;
                        break;
                    case STATUS:
                        resource = R.drawable.up_arrow;
                }
                break;
            default:
                return -1;
        }


        return resource;
    }

}
