package declatoration.simulatorial.fight;

import static declatoration.simulatorial.fight.fightBackground.clashReactionManager;
import static declatoration.simulatorial.fight.fightBackground.combatants;
import static declatoration.simulatorial.getImageRes.getAffinityImageResource;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import declatoration.simulatorial.R;
import declatoration.simulatorial.enums.affinity;
import declatoration.simulatorial.enums.damageType;
import declatoration.simulatorial.enums.statusEffect;
import declatoration.simulatorial.getImageRes;

public class clashReactionForecast extends LinearLayout {



    ImageView affinityIcon1, affinityIcon2, statusIcon1, statusIcon2;
    TextView statusName1, statusName2, percName1, percName2, targetName1, targetName2;


    public clashReactionForecast(Context context){
        super(context);

        init();
    }

    public clashReactionForecast(Context c, AttributeSet attributeSet){
        super(c, attributeSet);
        init();
    }


    public void init(){
        inflate(getContext(), R.layout.clash_reaction_forecast, this);

        affinityIcon1 = findViewById(R.id.clash_eff_aff_icon1);
        affinityIcon2 = findViewById(R.id.clash_eff_aff_icon2);

        statusName1 = findViewById(R.id.clash_eff_tv_1);
        statusName2 = findViewById(R.id.clash_eff_tv_2);
        statusIcon1 = findViewById(R.id.clash_eff_icon_1);
        statusIcon2 = findViewById(R.id.clash_eff_icon_2);

        percName1 = findViewById(R.id.clash_eff_perc_tv_1);
        percName2 = findViewById(R.id.clash_eff_perc_tv_2);

        targetName1 = findViewById(R.id.clash_eff_target_tv_1);
        targetName2 = findViewById(R.id.clash_eff_target_tv_2);

    }

    public void setInfo(affinity atkAff, int atkrId, int defId){

        battleToken atkrBT = combatants.getBattleToken(atkrId);
        battleToken defBT = combatants.getBattleToken(defId);


        affinity aff2 = defBT.getAffinity();
        affinityIcon1.setBackgroundResource(getImageRes.getAffinityImageResource(atkAff));
        affinityIcon2.setBackgroundResource(getImageRes.getAffinityImageResource(aff2));


        ArrayList<affinityClashExtraEffects.clashEffect> clashEffects = clashReactionManager.getClashReactions(atkAff, aff2);

        if(clashEffects != null && clashEffects.size() > 0) {

            affinityClashExtraEffects.clashEffect cur = clashEffects.get(0);
            if (cur != null && cur.reaction != statusEffect.NULL && cur.reaction != statusEffect.TBD) {
                statusIcon1.setBackgroundResource(getImageRes.getStatusIcon(cur.reaction));

                String statusName = cur.reaction.toString();
                if (cur.degree != null && ((cur.degree >= 2 || cur.degree <= -2))) {
                    statusName += "+";
                }
                statusName1.setText(statusName);

                String percName = cur.percentage + "%";
                percName1.setText(percName);

                if (cur.affectsUser) {
                    targetName1.setText(R.string.self);
                } else {
                    targetName1.setText(R.string.target_opp);
                }

                if(clashEffects.size() > 1) {
                    cur = clashEffects.get(1);
                    statusIcon2.setBackgroundResource(getImageRes.getStatusIcon(cur.reaction));

                    statusName = cur.reaction.toString();
                    if (cur.degree != null&& (cur.degree >= 2 || cur.degree <= -2)) {
                        statusName += "+";
                    }
                    statusName2.setText(statusName);


                    percName = cur.percentage + "%";
                    percName2.setText(percName);

                    if (cur.affectsUser) {
                        targetName2.setText(R.string.self);
                    } else {
                        targetName2.setText(R.string.target_opp);
                    }

                } else{
                    statusIcon2.setBackgroundResource(0);
                    statusName2.setText("");
                    targetName2.setText("");
                    percName2.setText("");
                }
            } else {
                statusIcon1.setBackgroundResource(0);
                statusName1.setText("");
                targetName1.setText("");
                percName1.setText("");

                statusIcon2.setBackgroundResource(0);
                statusName2.setText("");
                targetName2.setText("");
                percName2.setText("");
            }

        } else{

            statusIcon1.setBackgroundResource(0);
            statusName1.setText("");
            targetName1.setText("");
            percName1.setText("");

            statusIcon2.setBackgroundResource(0);
            statusName2.setText("");
            targetName2.setText("");
            percName2.setText("");
        }
    }

}
