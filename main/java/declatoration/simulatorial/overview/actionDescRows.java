package declatoration.simulatorial.overview;

import android.content.Context;
import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import declatoration.simulatorial.R;
import declatoration.simulatorial.charAction;

public class actionDescRows extends TableRow {

    ImageView icon;
    ImageView[] symbols = new ImageView[4];

    TextView actionName, actionDesc, SPUse, cooldown;

    charAction curAction;


    public actionDescRows(Context context, charAction curCharAction) {
        super(context);

        curAction = curCharAction;
        init();
    }

    public void init(){

        inflate(getContext(), R.layout.action_desc_row, this);

        icon = findViewById(R.id.action_icon);
        actionName = findViewById(R.id.action_name);
        actionDesc = findViewById(R.id.action_desc);
        cooldown = findViewById(R.id.cooldown_textview);
        SPUse = findViewById(R.id.sp_use_textview);


        symbols[0] = findViewById(R.id.symbol_1);
        symbols[1] = findViewById(R.id.symbol_2);
        symbols[2] = findViewById(R.id.symbol_3);



        icon.setImageResource(curAction.getIconResource());

        actionName.setText(curAction.getName());
        actionName.setPaintFlags(actionName.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        actionDesc.setText(curAction.getActionDesc());

        String text = "(" + curAction.getCooldown() + " Turns)";
        cooldown.setText(text);

        text = curAction.getSPUsage() + "SP";
        SPUse.setText(text);

        symbols[0].setImageResource(curAction.getSymbolResId(0));
        int resId = curAction.getSymbolResId(1);
        if(resId != R.drawable.bloo) {
            symbols[1].setImageResource(resId);
            symbols[2].setImageResource(curAction.getSymbolResId(2));

        } else{
            symbols[1].setImageResource(curAction.getSymbolResId(2));
            symbols[2].setImageResource(0);
        }


    }
}
