package declatoration.simulatorial.fight;

import java.util.ArrayList;

public class grappleMediator {


    ArrayList<duo> grappleDuos = new ArrayList<>();



    public grappleMediator(){}


    public ArrayList<Integer> getGrapplingCharIDs(){
        ArrayList<Integer> graps = new ArrayList<>();

        for (int i = 0; i < grappleDuos.size(); i++) {
            graps.add(grappleDuos.get(i).initiatorId);
        }

        return graps;
    }

    // you must release any previous engagements before calling this method
    // (namely for the purpose of switching a grappling target)
    public boolean addDuo(int assistor, int assistee, boolean isSwallowEffect){
        boolean grappleIsPossible = true;

        for (int i = 0; i < grappleDuos.size(); i++) {

            duo cur = grappleDuos.get(i);

            if(isSwallowEffect){
                // if char wants to swallow
                if(cur.isSwallowEffect){
                    // if any of the chars is swallowed or is swallowing
                    grappleIsPossible = false;
                } else {
                    if(cur.targetId == assistor){
                        // cant swallow if grappled

                        grappleIsPossible = false;
                    }
                }
            } else{
                //if char wants to grapple

                if(cur.targetId == assistor || cur.targetId == assistee ||
                        (cur.initiatorId == assistor && cur.isSwallowEffect)){
                    // if either are grappled / swallowed
                    // also if char has someone swallowed
                    grappleIsPossible = false;
                    break;
                }
            }
        }

        // only add effect if neither the assistor or assistee
        //is being grappled
        if(grappleIsPossible) {
            grappleDuos.add(new duo(assistee, assistor, isSwallowEffect));
        }

        return grappleIsPossible;
    }
    public void removeDuo(int idToRemove){
         for (int i = 0; i < grappleDuos.size(); i++) {
             if(grappleDuos.get(i).hasId(idToRemove)){
                 grappleDuos.remove(i);
                 break;
             }
         }
    }

    public duo getDuoIfExists(int charId){

        duo dui = null;

        for (int i = 0; i < grappleDuos.size(); i++) {
            if(grappleDuos.get(i).hasId(charId)){
                dui = grappleDuos.get(i);
                break;
            }
        }

        return dui;
    }


    public int getPartner(int currentTurnCharId) {
        duo d = getDuoIfExists(currentTurnCharId);

        if(d.initiatorId == currentTurnCharId){
            return d.targetId;
        } else return d.initiatorId;
    }
}
