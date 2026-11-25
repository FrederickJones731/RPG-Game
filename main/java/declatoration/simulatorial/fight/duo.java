package declatoration.simulatorial.fight;

public class duo {


    // used also in assistConductor, and also in grappleMediator.
    // boolean obvs only for graps

    Integer targetId;
    Integer initiatorId;

    boolean isSwallowEffect = false;

    public duo(int targetId, int initiatorId) {
        this.targetId = targetId;
        this.initiatorId = initiatorId;
    }
    public duo(int targetId, int initiatorId, boolean isSwallowEffect) {
        this.targetId = targetId;
        this.initiatorId = initiatorId;
        this.isSwallowEffect = isSwallowEffect;
    }


    public boolean hasId(int id) {
        return (initiatorId == id || targetId == id);
    }

    public Integer getTargetId() {
        return targetId;
    }

    public Integer getInitiatorId() {
        return initiatorId;
    }

    public Integer getPartnerId(int id) {
        if(id == targetId){
            return initiatorId;
        } else return targetId;
    }
}
