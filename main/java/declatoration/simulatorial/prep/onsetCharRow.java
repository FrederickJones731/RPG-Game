package declatoration.simulatorial.prep;

import static declatoration.simulatorial.fight.fightForeground.sonicJammer;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import declatoration.simulatorial.R;
import declatoration.simulatorial.chars;
import declatoration.simulatorial.fight.sounds.soundmanDan2;
import declatoration.simulatorial.getImageRes;
import declatoration.simulatorial.viewAnimationHelper;

public class onsetCharRow extends ConstraintLayout {

    TextView hpbar, spbar;
    ImageView weaponIcon, affIcon;
    Spinner spinner;

    chars curChar = null;

    soundmanDan2 musicBoy;


    public onsetCharRow(@NonNull Context context) {
        super(context);

        musicBoy = new soundmanDan2();
    }

    public void init(int rowNum, charSelectionArrayAdapter adapter){
        inflate(getContext(), R.layout.onset_char_row, this);

        TextView rowNumTV = findViewById(R.id.onset_row_num);
        rowNumTV.setText(String.valueOf(rowNum));

        affIcon = findViewById(R.id.onset_icon_affinity);
        weaponIcon = findViewById(R.id.onset_icon_weapon);

        hpbar = findViewById(R.id.onset_hpbar);
        spbar = findViewById(R.id.onset_spbar);


        spinner = findViewById(R.id.onset_spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = adapter.getNameAt(position);
                if(name != null && !name.equals("-")) {
                    // check that this is a char
                    curChar = new chars(getContext(), name, 25);
                } else curChar = null;
                updateData();

//                sonicJammer.playSound("spinner");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        viewAnimationHelper.setSpinnerAnimation(spinner);
        ((BaseAdapter) spinner.getAdapter()).notifyDataSetChanged();

//        sonicJammer = new soundmanDan2();
    }


    private void updateData(){
        if(curChar != null) {
            affIcon.setBackgroundResource(getImageRes.getAffinityImageResource(curChar.getAffinity()));
            weaponIcon.setBackgroundResource(getImageRes.getWeaponIconXS(curChar.getWeapon()));

            String hpBarText = (int) curChar.getHPMax() + "/" + (int) curChar.getHPMax();
            hpbar.setText(hpBarText);

            String spBarText = (int) curChar.getSPMax() + "/" + (int) curChar.getSPMax();
            spbar.setText(spBarText);

            hpbar.setVisibility(VISIBLE);
            spbar.setVisibility(VISIBLE);
        } else{
            affIcon.setBackgroundResource(0);
            weaponIcon.setBackgroundResource(0);

            hpbar.setVisibility(INVISIBLE);
            spbar.setVisibility(INVISIBLE);
        }
    }

    public String getName(){
        if(curChar != null){
            return curChar.getName();
        } else {
            return null;
        }
    }
}
