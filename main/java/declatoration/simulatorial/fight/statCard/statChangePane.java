package declatoration.simulatorial.fight.statCard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import declatoration.simulatorial.R;

public class statChangePane extends LinearLayout {

    TextView statNameTV;

    ImageView[] drops = new ImageView[4];
    ImageView[] raises = new ImageView[4];


    public statChangePane(Context context, String statName, int statVal){
        super(context);

        init(statName, statVal);
    }

    public statChangePane(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public void init(String statName, int buffVal){
        inflate(getContext(), R.layout.stat_buff_row, this);


        drops[0] = findViewById(R.id.stat_buff_dot_4);
        drops[1] = findViewById(R.id.stat_buff_dot_3);
        drops[2] = findViewById(R.id.stat_buff_dot_2);
        drops[3] = findViewById(R.id.stat_buff_dot_1);

        raises[0] = findViewById(R.id.stat_buff_dot_5);
        raises[1] = findViewById(R.id.stat_buff_dot_6);
        raises[2] = findViewById(R.id.stat_buff_dot_7);
        raises[3] = findViewById(R.id.stat_buff_dot_8);


        statNameTV = findViewById(R.id.stat_pane_stat_name_tv);

        statNameTV.setText(statName);


        for (int i = 0; i < Math.abs(buffVal) && i < 5; i++) {
            if(buffVal > 0){
                raises[i].setBackgroundResource(R.drawable.up_arrow);
            } else{
                drops[i].setBackgroundResource(R.drawable.down_arrow);
            }
        }
    }
}
