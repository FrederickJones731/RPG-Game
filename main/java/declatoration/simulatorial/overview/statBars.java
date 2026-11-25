package declatoration.simulatorial.overview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import declatoration.simulatorial.R;
import declatoration.simulatorial.enums.stat;

public class statBars extends TableRow {

    TextView stat, statVal;
    LinearLayout ll;
    ImageView line1, line2;

    public statBars(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public statBars(Context context, stat statName, float value) {

        super(context);
    }

    public void instigate(stat statName, float value){

        inflate(getContext(), R.layout.stat_table_row, this);

        stat = findViewById(R.id.table_row_stat_name);
        statVal = findViewById(R.id.table_row_val);

        ll = findViewById(R.id.table_row_ll);

        line1 = findViewById(R.id.table_row_bar1);
        line2 = findViewById(R.id.table_row_bar2);


        this.stat.setText(statName.toString());
        statVal.setText(String.valueOf(value));
        setStatColor();

        float fillPercent = value / 10;
        if(fillPercent > 1){
            fillPercent = 1;
        }

        if(value > 10){
            fillPercent = (value - 10) / 10;
            if(fillPercent > 1){
                fillPercent = 1;
            }

//            line1.setLayoutParams(new LinearLayout.LayoutParams(ll.getWidth(), 6));
//            line2.setLayoutParams(new LinearLayout.LayoutParams(
//                    (int) (ll.getWidth() * fillPercent),
//                    6));

            line1.setImageLevel(100);
            line2.setImageLevel((int) (100 * fillPercent));
        } else{

//            line1.setLayoutParams(new LinearLayout.LayoutParams(
//                    (int) (ll.getWidth() * fillPercent),
//                    6));
            line1.setImageLevel((int) (fillPercent * 100));
            line2.setVisibility(GONE);
        }


    }



    private void setStatColor(){
        int val = (int) Float.parseFloat((String) statVal.getText());

        if(val < 2){
            stat.setBackgroundResource(R.drawable.box_very_red);
            line1.setBackgroundResource(R.drawable.box_very_red);
//            statVal.setBackgroundResource(R.drawable.dark_redoo);
        } else if (val < 4.5) {
            stat.setBackgroundResource(R.drawable.box_red);
            line1.setBackgroundResource(R.drawable.box_red);
//            statVal.setBackgroundResource(R.drawable.redoo);
        } else if (val < 6.5) {
            stat.setBackgroundResource(R.drawable.box_yellow);
            line1.setBackgroundResource(R.drawable.box_yellow);
//            statVal.setBackgroundResource(R.drawable.dark_bloo);
        } else if (val < 9.5) {
            stat.setBackgroundResource(R.drawable.box_green);
            line1.setBackgroundResource(R.drawable.box_green);
//            statVal.setBackgroundResource(R.drawable.bloo);
        } else {
            stat.setBackgroundResource(R.drawable.box_very_green);
            line1.setBackgroundResource(R.drawable.box_very_green);
            line2.setBackgroundResource(R.drawable.box_very_green);
//            statVal.setBackgroundResource(R.drawable.groen);
        }
    }

}
