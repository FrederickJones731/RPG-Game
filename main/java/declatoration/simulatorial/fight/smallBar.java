package declatoration.simulatorial.fight;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import declatoration.simulatorial.R;

public class smallBar extends RelativeLayout {


    ImageView bar, border;
    View root;
    private boolean isHpBar;
    int length;


    public smallBar(@NonNull Context context) {
        super(context);
    }

    public smallBar(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // this view is set in char layout, and doesnt need to be inflated again
    public void initialize(boolean isHpBar){

        root = inflate(getContext(), R.layout.layout_small_bar, this);
        this.isHpBar = isHpBar;

        this.bar = root.findViewById(R.id.small_bar_bar);
        this.border = root.findViewById(R.id.small_bar_border);

//        if (this.isHpBar){
//            this.bar.setBackgroundColor(R.drawable.hpbar);
//        } else{ this.bar.setBackgroundColor(R.drawable.spbar);}
//
//        this.border.setBackgroundColor(R.drawable.small_bar_border);
//        this.bringChildToFront(this.border);
        this.length = this.bar.getHeight();
        //doesnt work
//        this.setColorAndFullness(50);

    }

    public int getColorAndFullness(float filledPercentage) {

        int color;
        double temp = filledPercentage;

        if(this.isHpBar) {
            if (temp >= 0.5) {
                temp = (temp - 0.5) * 2;
                color = (Integer) new ArgbEvaluator().evaluate((float) temp,
                        Color.parseColor("#B9F8EE33"), Color.parseColor("#D05AED02"));

            } else {
                temp = temp * 2;
                color = (Integer) new ArgbEvaluator().evaluate((float) temp,
                        Color.parseColor("#9CFB1414"), Color.parseColor("#B9F8EE33"));
            }
        } else{

            if (temp >= 0.5) {
                temp = (temp - 0.5) * 2;
                color = (Integer) new ArgbEvaluator().evaluate((float) temp,
                        Color.parseColor("#DD33A9F8"), Color.parseColor("#EE02235C"));

            } else {
                temp = temp * 2;
                color = (Integer) new ArgbEvaluator().evaluate((float) temp,
                        Color.parseColor("#C9E0FFF3"), Color.parseColor("#DD33A9F8"));
            }
        }

        return color;
    }

}
