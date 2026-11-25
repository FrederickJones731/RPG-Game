package declatoration.simulatorial.fight;

import java.util.ArrayList;

import declatoration.simulatorial.enums.stat;
import declatoration.simulatorial.enums.statusEffect;

public interface charClickedBridger {

    boolean clicked(int clickedSerial);
    void finalTargetsAndProceed(ArrayList<Integer> finalTargets);

    void post(String charName, String targetName, String attackName, fightUpdater.textType classification, Integer value, stat changingStat);

    void rampartToast(String rampName);
    void intimidateToast(String intimidatorName);
    void untargetableToast(String targetName);
    void engageToast(String engageeName);
    void grappledToast(boolean targetIsGrappled, String targetName);
    void aegisToast();

    void conveyVisual(statusEffect effect, int targetId);

    void inflateStatCard(int id);


}
