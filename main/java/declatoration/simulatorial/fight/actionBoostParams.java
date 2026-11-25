package declatoration.simulatorial.fight;

public class actionBoostParams {

    float attackBoost = 0;
    float hitBoost = 0;
    float critBoost = 0;
    float blockBoost = 0;

    public actionBoostParams() {}


    public void setParams(float attackMod, float hitMod, float critMod, float blockMod) {
        attackBoost = attackMod;
        hitBoost = hitMod;
        critBoost = critMod;
        blockBoost = blockMod;
    }
    public void setParams(actionBoostParams other) {
        attackBoost = other.attackBoost;
        hitBoost = other.hitBoost;
        critBoost = other.critBoost;
        blockBoost = other.blockBoost;
    }

    public void addify(actionBoostParams add) {
        this.attackBoost += add.attackBoost;
        this.hitBoost += add.hitBoost;
        this.critBoost += add.critBoost;
        this.blockBoost += add.blockBoost;
    }
//
//    public void addify(String poi, int val) {
//        switch (poi){
//            case "hit":
//                this.hitBoost += val;
//                break;
//            case "crit":
//                this.critBoost += val;
//                break;
//            case "block":
//                this.blockBoost += val;
//                break;
//        }
//
//        used = true;
//    }
//
//    public void multifyAtk(float val) {
//        if(attackBoost == 0) {
//            attackBoost = 1;
//        }
//        this.attackBoost *= val;
//        used = true;
//    }
//
//    public void applyAdvantageStatus(){
//        attackBoost *= 1.15;
//        hitBoost *= 1.15;
//        critBoost *= 1.15;
//        blockBoost *= 1.15;
//    }
}
