package declatoration.simulatorial.fight;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import declatoration.simulatorial.R;

public class largeBar extends RelativeLayout {


    View v;
    TextView textView;
    ImageView bar;

    private boolean isHpBar;
    private int length;


    public largeBar (Context c, boolean isHpBar) {

        super(c);
//        initiate(isHpBar);
    }

    // IF DOESNT WORK FIND HOW TO GET ATTRS FROM XML
    public largeBar (Context c,  AttributeSet attrs) {

        super(c, attrs);
//        this.initiate(attrs.getAttributeBooleanValue("http://schemas.android.com/apk/res-auto","isHpBar", true));
    }

    public void initiate(boolean isHpBar){

        inflate(getContext(), R.layout.large_bar_layout, this);

        bar = findViewById(R.id.bar);
        textView = findViewById(R.id.barText);

        this.isHpBar = isHpBar;
        this.length = this.bar.getWidth();

        if(!isHpBar){
            bar.setBackgroundColor(R.drawable.spbar);
        }

    }

    //doest thou enrich our lackluster lives with thy work-ing-ness?

    public void setBarColor (double filledPercentage) {

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

        this.bar.setBackgroundColor(color);

//        this.bar.setLayoutParams(new FrameLayout.LayoutParams(this.bar.getWidth(), (int)(this.length * filledPercentage)));

    }
    public void setLargeBarText(String text){
        this.textView.setText(text);
    }

    public int getLength() {return length;}

    public ImageView getBar() {return bar;}
}
