package declatoration.simulatorial.overview;

import static declatoration.simulatorial.enums.stat.statArray;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.widget.LinearLayoutCompat;

import declatoration.simulatorial.chars;

public class encyclofragStatTable extends ScrollView {

    statBars[] rows = new statBars[15];

    TableLayout tableLayout;


    public encyclofragStatTable(Context context, AttributeSet attrs){
        super(context, attrs);

    }


    public void init(chars c){

        tableLayout = new TableLayout(getContext());
        this.addView(tableLayout);

        tableLayout.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        ));

        for (int i = 0; i < statArray.length; i++) {
            rows[i] = new statBars(getContext(), statArray[i], c.getStatFloat(statArray[i]));
            rows[i].instigate(statArray[i], c.getStatFloat(statArray[i]));
            rows[i].setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            int pooi = tableLayout.getWidth();
            rows[i].setMinimumWidth(tableLayout.getWidth());
            tableLayout.addView(rows[i]);


            View linebreak = new View(getContext());
            linebreak.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            linebreak.setBackgroundColor(Color.parseColor("#6B656565"));
            int yoop = linebreak.getWidth();
            tableLayout.addView(linebreak);
        }

    }
}
