package declatoration.simulatorial.fight;

import static declatoration.simulatorial.getImageRes.getAffinityImageResource;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import declatoration.simulatorial.R;
import declatoration.simulatorial.charAction;
import declatoration.simulatorial.enums.damageType;

public class actionTextBubble extends LinearLayout {



    charAction action;

    ImageView affinityIcon;
    TextView name, spTV, descTV, rangeDmgTV, actionCostTV;


    public actionTextBubble(Context context, charAction charAction){
        super(context);

        this.action = charAction;
        init();
    }


    public void init(){
        inflate(getContext(), R.layout.action_bubble, this);

        affinityIcon = findViewById(R.id.text_bubble_affinity_icon);

        name = findViewById(R.id.text_bubble_action_name);
        spTV = findViewById(R.id.text_bubble_sp_val);
        descTV = findViewById(R.id.text_bubble_action_desc);
        rangeDmgTV = findViewById(R.id.text_bubble_range_dmgtype_tv);
        actionCostTV = findViewById(R.id.text_bubble_action_use_tv);

        affinityIcon.setBackgroundResource(getAffinityImageResource(action.getAffinity()));
        name.setText(action.getName());

        String spString = action.getSPUsage() + "SP";
        spTV.setText(spString);

        descTV.setText(action.getActionDesc());

        String actionUseString = "";
        int actionUse = action.getActionUse();
        if(actionUse != 1) {
             actionUseString = actionUse + " Actions";
        } else actionUseString = actionUse + " Action";
        actionCostTV.setText(actionUseString);

        String str = "Range: ";

        ArrayList <Integer> range = action.getRange();
        if(range.size() != 0) {
            str += range.get(0);
            if (range.size() > 1) {
                str += "-" + range.get(range.size() - 1);
            }

            String target = action.getAffectedTarget().toString();
            target.toLowerCase();
            target.replace("_", " ");
            str += " " + target;

            str += ", ";
            if (action.getIsMag()) {
                str += "Magical";
            } else {
                str += "Physical";
            }
        } else{
            //incorrect for now but who cares fix it later
            if(action.getDamageType() == damageType.HEAL){
                str = "Healing";
            } else {
                str = "Status";
            }
        }

        rangeDmgTV.setText(str);
    }
}
