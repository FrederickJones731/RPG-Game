package declatoration.simulatorial.fight.statCard;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import declatoration.simulatorial.R;
import declatoration.simulatorial.fight.statusEffectList.effect;
import declatoration.simulatorial.getImageRes;

public class conditionPaneRow extends LinearLayout {

    effect statusEffect;

    ImageView statusIcon;
    TextView statusNameTV, durationTV;

    public conditionPaneRow(Context context, effect statusEffect){
        super(context);

        this.statusEffect = statusEffect;
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.condition_pane_row, this);

        statusIcon = findViewById(R.id.condition_pane_status_icon);
        statusNameTV = findViewById(R.id.condition_pane_status_name);
        durationTV = findViewById(R.id.condition_pane_duration_tv);

        statusIcon.setBackgroundResource(getImageRes.getStatusIcon(statusEffect.getStatusEffect()));

        statusNameTV.setText(statusEffect.getStatusEffect().toString());

        int duration = statusEffect.getDuration();
        if(duration <= 0) {
            durationTV.setText("-");
        } else {
            durationTV.setText(String.valueOf(statusEffect.getDuration()));
        }

    }








}
