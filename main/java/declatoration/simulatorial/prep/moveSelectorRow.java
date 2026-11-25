package declatoration.simulatorial.prep;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import declatoration.simulatorial.R;
import declatoration.simulatorial.charAction;
import declatoration.simulatorial.getImageRes;


public class moveSelectorRow extends ConstraintLayout {

    charAction curAction;
    SwitchCompat switcherooni;

    public moveSelectorRow(Context context){
        super(context);
    }

    public moveSelectorRow(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void initialize(moveSelector moveSelector, charAction action){

        inflate(getContext(), R.layout.move_selector_row, this);

        switcherooni = findViewById(R.id.switch0);

        ImageView icon = findViewById(R.id.action_icon);
        ImageView affIcon = findViewById(R.id.aff_icon);

        TextView nameTV = findViewById(R.id.tv);
        TextView spTV = findViewById(R.id.sp_tv);

        ImageButton deets = findViewById(R.id.info);


        nameTV.setText(action.getName());

        String str = action.getSPUsage() + "SP";
        spTV.setText(str);

        icon.setBackgroundResource(action.getIconResource());
        affIcon.setBackgroundResource(getImageRes.getAffinityImageResource(action.getAffinity()));

        curAction = action;


        deets.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                moveSelector.inflateBubble(curAction);
            }
        });

    }


    public boolean isSelected(){
        return switcherooni.isChecked();
    }

    public SwitchCompat getSwitch(){
        return switcherooni;
    }
    public void setSwitchClickable(boolean b){
        switcherooni.setClickable(b);
    }
    public void setChecked(boolean b){
        switcherooni.setChecked(b);
    }

    public String getActionName(){
        return curAction.getName();
    }
}
