package declatoration.simulatorial.fight.statCard;

import static declatoration.simulatorial.enums.stat.ATK;
import static declatoration.simulatorial.enums.stat.AVO;
import static declatoration.simulatorial.enums.stat.BLOCK;
import static declatoration.simulatorial.enums.stat.CRIT;
import static declatoration.simulatorial.enums.stat.DEF;
import static declatoration.simulatorial.enums.stat.HIT;
import static declatoration.simulatorial.enums.stat.INT;
import static declatoration.simulatorial.enums.stat.MAG;
import static declatoration.simulatorial.enums.stat.RES;
import static declatoration.simulatorial.enums.stat.SPD;
import static declatoration.simulatorial.enums.stat.statCardStatArray;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

import declatoration.simulatorial.R;
import declatoration.simulatorial.chars;
import declatoration.simulatorial.enums.stat;
import declatoration.simulatorial.fight.battleToken;
import declatoration.simulatorial.fight.statusEffectList;
import declatoration.simulatorial.getImageRes;
import pl.droidsonroids.gif.GifImageButton;

public class statCardXX extends Dialog implements android.view.View.OnClickListener {



//    public Activity fight;
    public Dialog dia;


    chars ch;
    battleToken battleToken;

    stat[] boostableStats = new stat[]{ATK, DEF, MAG, INT, SPD, HIT, CRIT, AVO, BLOCK};

    private conditionPane statusEffectSector;
    private statChangePane statBuffPane;

    TextView[] statTVs = new TextView[12];


    public statCardXX(Activity fightClassInstance, chars chars, battleToken battleTokenRec) {
        super(fightClassInstance);

//        this.fight = fightClassInstance;
        ch = chars;
        battleToken = battleTokenRec;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_stat_card);

        GifImageButton back = findViewById(R.id.back_button);

        TextView nametv = findViewById(R.id.stat_card_name);
        TextView classtv = findViewById(R.id.stat_card_char_class);
        TextView leveltv = findViewById(R.id.stat_card_level);
        TextView hpbar = findViewById(R.id.stat_card_hpbar);
        TextView spbar = findViewById(R.id.stat_card_spbar);

        ImageView affinityIcon = findViewById(R.id.stat_card_affinity_icon);
        ImageView weaponIcon = findViewById(R.id.stat_card_weapon_icon);

        statTVs[0] = findViewById(R.id.stat_card_atk_val);
        statTVs[1] = findViewById(R.id.stat_card_def_val);
        statTVs[2] = findViewById(R.id.stat_card_mag_val);
        statTVs[3] = findViewById(R.id.stat_card_int_val);
        statTVs[4] = findViewById(R.id.stat_card_spd_val);
        statTVs[5] = findViewById(R.id.stat_card_lck_val);
        statTVs[6] = findViewById(R.id.stat_card_hit_val);
        statTVs[7] = findViewById(R.id.stat_card_crit_val);
        statTVs[8] = findViewById(R.id.stat_card_phys_avo_val);
        statTVs[9] = findViewById(R.id.stat_card_mag_avo_val);
        statTVs[10] = findViewById(R.id.stat_card_block_val);
        statTVs[11] = findViewById(R.id.stat_card_res_val);




        nametv.setText(ch.getName());
        classtv.setText(ch.getClassName());
        leveltv.setText("Lv. " + ch.getLevel());
        hpbar.setText(battleToken.getHP() + "/" + battleToken.getHPMax());
        spbar.setText(battleToken.getSP() + "/" + battleToken.getSPMax());

        weaponIcon.setBackgroundResource(getImageRes.getWeaponIcon(ch.getWeapon()));
        affinityIcon.setBackgroundResource(getImageRes.getAffinityImageResource(battleToken.getAffinity()));

        for (int i = 0; i < statTVs.length; i++) {
            stat curStat = statCardStatArray[i];

            String temp = String.valueOf((int) ch.getStatFloat(curStat));

            if(battleToken.getBoostModifier(curStat) != 1f){

                int boostVal = (int) (ch.getStatFloat(curStat) * battleToken.getBoostModifier(curStat) -
                        ch.getStatFloat(curStat));

                String temp2 = " ";
                if(boostVal >= 0){
                    temp2 += "+";
                }
                temp2 += String.valueOf(boostVal);

                String total = temp + temp2;
                Spannable spannable = new SpannableString(total);

                int color;
                if(boostVal > 0){
                    color = Color.parseColor("#FF3BC324");
                } else{
                    color = Color.parseColor("#FFF31D1D");
                }

                spannable.setSpan(new ForegroundColorSpan(color), temp.length(),
                        (temp + temp2).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                statTVs[i].setText(spannable, TextView.BufferType.SPANNABLE);
            } else {
                statTVs[i].setText(temp);
            }
        }

        statTVs[11].setText((int) ch.getStatFloat(RES) + "%");

        back.setOnClickListener(v -> dismiss());


        LinearLayout ll =  findViewById(R.id.stat_card_condition_pane);

        statusEffectSector = new conditionPane(getContext(),this.battleToken);
        statusEffectSector.addRowsIfExist();

        ll.addView(statusEffectSector);

        LinearLayout ll2 = findViewById(R.id.stat_buff_layout);
        for (int i = 0; i < boostableStats.length; i++) {
            statBuffPane = new statChangePane(getContext(), boostableStats[i].toString(),
                    battleToken.getBoostVal(boostableStats[i]));
            ll2.addView(statBuffPane);
        }


//        GridLayout gridLayout = findViewById(R.id.stat_buff_layout);
//
//        for (int row = 0; row < 12; row++) {
//            for (int column = 0; column < 8; column++) {
//                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
//
//
//                params.rowSpec = GridLayout.spec(row, 1, 1f);
//                params.width = 0;
//                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//
//                // Set the width of the 3rd column to be wider
//                if (column == 4) {
//                    TextView textView = new TextView(getContext());
//                    params.columnSpec = GridLayout.spec(column, 3, 1f);
//                    params.height = 35;
//
//                    textView.setText(boostableStats[row].toString());
//                    textView.setBackgroundResource(R.drawable.spbar);
//                    textView.setTextColor(Color.BLACK);
//                    textView.setTextSize(20);
//
//                    textView.setLayoutParams(params);
//                    gridLayout.addView(textView);
//                } else {
//                    ImageView imageView = new ImageView(getContext());
//                    params.columnSpec = GridLayout.spec(column, 2, 1f);
//                    params.width = 30;
//                    params.height = 30;
//
//                    imageView.setMaxHeight(30);
//                    imageView.setMaxWidth(30);
//                    imageView.setBackgroundResource(R.drawable.neutral_dot);
//
//                    imageView.setLayoutParams(params);
//                    gridLayout.addView(imageView);
//                }
//            }
//        }
    }




//    protected int getConditionPaneId(){
//        return R.id.stat_card_condition_pane;
//    }

    private static class conditionPane extends LinearLayout {

        battleToken battleToken;

        public conditionPane(Context context, AttributeSet attributeSet){
            super(context, attributeSet);
        }

        public conditionPane(Context context, battleToken battleTokenz){
            super(context);

            this.battleToken = battleTokenz;
        }


        public void addRowsIfExist(){

            setOrientation(VERTICAL);
            if(battleToken.hasStatusEffect()) {
                ArrayList<statusEffectList.effect> conditions = battleToken.getStatusEffects();

                for (int i = 0; i < conditions.size(); i++) {
                    conditionPaneRow statusRow = new conditionPaneRow(getContext(), conditions.get(i));
                    addView(statusRow);

                    View linebreak = new View(getContext());
                    linebreak.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
                    linebreak.setBackgroundColor(Color.parseColor("#6B656565"));
                    addView(linebreak);
                }
            }
        }
    }
//
//    private static class statChangePane extends GridLayout {
//
//        battleToken.boostBoard statChanges;
//        stat[] boostableStats = new stat[]{ATK, DEF, MAG, INT, SPD, LCK, HIT, CRIT, PHYSAVO, MAGAVO, BLOCK, RES};
//
//        public statChangePane(Context context, AttributeSet attributeSet){
//            super(context, attributeSet);
//        }
//
//        public statChangePane(Context context, battleToken battleTokenz){
//            super(context);
//
//            this.statChanges = battleTokenz.getBoostBoard();
//        }
//
//
//        public void addRows(Context context){
//
//            for (int row = 0; row < 12; row++) {
//                for (int column = 0; column < 9; column++) {
//                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
//
//
//                    params.rowSpec = GridLayout.spec(row, 1, 1f);
//                    params.width = 0;
//                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//
//                    // Set the width of the 3rd column to be wider
//                    if (column == 4) {
//                        TextView textView = new TextView(context);
//                        params.columnSpec = GridLayout.spec(column, 3, 1f);
//                        textView.setText(boostableStats[row].toString());
//
//                        textView.setLayoutParams(params);
//                        addView(textView);
//                    } else {
//                        ImageView imageView = new ImageView(context);
//                        params.columnSpec = GridLayout.spec(column, 2, 1f);
//                        imageView.setBackgroundResource(R.drawable.neutral_dot);
//
//                        imageView.setLayoutParams(params);
//                        addView(imageView);
//                    }
//
//
//                }
//            }
//
//
//
//        }
//    }


    @Override
    public void onClick(View v) {
        dismiss();
    }
}
