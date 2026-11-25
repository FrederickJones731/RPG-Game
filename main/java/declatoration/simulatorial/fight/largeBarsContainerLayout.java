package declatoration.simulatorial.fight;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import declatoration.simulatorial.R;
import declatoration.simulatorial.enums.affinity;
import declatoration.simulatorial.enums.statusEffect;
import declatoration.simulatorial.enums.weaponType;
import declatoration.simulatorial.getImageRes;

public class largeBarsContainerLayout extends LinearLayout {



    int filledBarLength;
    largeBar hp, sp;

    ImageView affinityIV, weaponTypeIV, statusIV;
    TextView name, attackName, bulletTV;

    public largeBarsContainerLayout(Context c, AttributeSet attrs){
        super(c, attrs);
        this.initiate();
    }

    public largeBarsContainerLayout(Context c){super(c);}


    private void initiate(){

        inflate(getContext(),R.layout.large_bars_container_layout,this);

        name = findViewById(R.id.name_text_view);
        attackName = findViewById(R.id.attack_name_tv);
        attackName.setVisibility(GONE);
        bulletTV = findViewById(R.id.bullet_count_text_view);

        affinityIV = findViewById(R.id.bar_affinity_icon);
        weaponTypeIV = findViewById(R.id.bar_weapon_icon);
        statusIV = findViewById(R.id.cur_status_icon);


        hp = findViewById(R.id.hpBarRL);
        sp = findViewById(R.id.spBarRL);

        hp.initiate(true);
        sp.initiate(false);

        filledBarLength = hp.getLength();
    }

    public void isPlayerBars(boolean b){
        if(!b){
            name.setTextColor(Color.parseColor("#EBCC1D1D"));
        } else{
            name.setTextColor(Color.parseColor("#EB3CA1CF"));
        }
    }

    public void updateChar(String charName, affinity aff, weaponType wep, statusEffect stat, int[] hpsp, Integer bulletCount){
        name.setText(charName);
        affinityIV.setBackgroundResource(getImageRes.getAffinityImageResource(aff));
        weaponTypeIV.setBackgroundResource(getImageRes.getWeaponIcon(wep));
        statusIV.setBackgroundResource(getImageRes.getStatusIcon(stat));


        String hpText = hpsp[0] + " / " + hpsp[1];
        String spText = hpsp[2] + " / " + hpsp[3];

        hp.setLargeBarText(hpText);
        sp.setLargeBarText(spText);

        if(bulletCount != null) {
            bulletTV.setText(String.valueOf(bulletCount));
        } else { bulletTV.setText("");}

    }

    public void updateAttackName(String attackName){
        this.attackName.setText(attackName);

    }

}
