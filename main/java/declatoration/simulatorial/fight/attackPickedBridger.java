package declatoration.simulatorial.fight;

public interface attackPickedBridger {
    void attackPicked(int attackId);
    void setAttackFragClickability(boolean clickability);

    void rowCollapsedToast();
    void rowFilledToast();

    void attackUnavailable(String cause);

    void rowLockdownToast();
}
