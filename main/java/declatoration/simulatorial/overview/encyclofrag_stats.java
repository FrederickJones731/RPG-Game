package declatoration.simulatorial.overview;

import static declatoration.simulatorial.enums.stat.ATK;
import static declatoration.simulatorial.enums.stat.BLOCK;
import static declatoration.simulatorial.enums.stat.CRIT;
import static declatoration.simulatorial.enums.stat.DEF;
import static declatoration.simulatorial.enums.stat.HIT;
import static declatoration.simulatorial.enums.stat.HP;
import static declatoration.simulatorial.enums.stat.INT;
import static declatoration.simulatorial.enums.stat.LCK;
import static declatoration.simulatorial.enums.stat.MAG;
import static declatoration.simulatorial.enums.stat.MAGAVO;
import static declatoration.simulatorial.enums.stat.PHYSAVO;
import static declatoration.simulatorial.enums.stat.RES;
import static declatoration.simulatorial.enums.stat.SP;
import static declatoration.simulatorial.enums.stat.SPD;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import declatoration.simulatorial.R;
import declatoration.simulatorial.chars;
import declatoration.simulatorial.getImageRes;

public class encyclofrag_stats extends Fragment {


    String charName;

    private TextView nametv, classtv, hpbar, spbar, lvltv, atktv, deftv, magtv, inttv, spdtv, lcktv, hittv, crittv,
            physavotv, magavotv, blocktv, restv;
    private ImageView weaponIcon, affIcon;



    public encyclofrag_stats() {}




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        charName = getArguments().getString("charName");
    }

    //sets findViewByIds
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_encyclofrag_stats, container, false);

        this.nametv = view.findViewById(R.id.current_char_name);
        this.classtv = view.findViewById(R.id.current_char_class);
        this.hpbar = view.findViewById(R.id.current_char_hpbar);
        this.spbar = view.findViewById(R.id.current_char_spbar);
        this.weaponIcon = view.findViewById(R.id.weapon_icon);
        this.affIcon = view.findViewById(R.id.aff_icon);
        this.lvltv = view.findViewById(R.id.current_char_level);

        this.atktv = view.findViewById(R.id.current_char_atk);
        this.deftv = view.findViewById(R.id.current_char_def);
        this.magtv = view.findViewById(R.id.current_char_mag);
        this.inttv = view.findViewById(R.id.current_char_int);
        this.spdtv = view.findViewById(R.id.current_char_spd);
        this.lcktv = view.findViewById(R.id.current_char_lck);
        this.hittv = view.findViewById(R.id.current_char_hit);
        this.crittv = view.findViewById(R.id.current_char_crit);
        this.physavotv = view.findViewById(R.id.current_char_phys_avo);
        this.magavotv = view.findViewById(R.id.current_char_mag_avo);
        this.blocktv = view.findViewById(R.id.current_char_block);
        this.restv = view.findViewById(R.id.current_char_res);

        updateView();

        return view;
    }

    //updates all data according to received charName
    public void updateView(){

        chars currentChar = new chars(getContext(), charName, null, 25);

        String levelText = getResources().getString(R.string.Lv) + currentChar.getLevel();
        this.lvltv.setText(levelText);

        this.nametv.setText(currentChar.getName());
        this.classtv.setText(currentChar.getClassName());
        this.hpbar.setText(getHpspFormat((int) currentChar.getStatFloat(HP)));
        this.spbar.setText(getHpspFormat((int) currentChar.getStatFloat(SP)));
        this.weaponIcon.setImageResource(getImageRes.getWeaponIcon(currentChar.getWeapon()));
        this.affIcon.setImageResource(getImageRes.getAffinityImageResource(currentChar.getAffinity()));

        this.atktv.setText(String.valueOf((int) currentChar.getStatFloat(ATK)));
        this.deftv.setText(String.valueOf((int) currentChar.getStatFloat(DEF)));
        this.magtv.setText(String.valueOf((int) currentChar.getStatFloat(MAG)));
        this.inttv.setText(String.valueOf((int) currentChar.getStatFloat(INT)));
        this.spdtv.setText(String.valueOf((int) currentChar.getStatFloat(SPD)));
        this.lcktv.setText(String.valueOf((int) currentChar.getStatFloat(LCK)));
        this.hittv.setText(String.valueOf((int) currentChar.getStatFloat(HIT)));
        this.crittv.setText(String.valueOf((int) currentChar.getStatFloat(CRIT)));
        this.physavotv.setText(String.valueOf((int) currentChar.getStatFloat(PHYSAVO)));
        this.magavotv.setText(String.valueOf((int) currentChar.getStatFloat(MAGAVO)));
        this.restv.setText(String.valueOf((int) currentChar.getStatFloat(RES)));
        this.blocktv.setText(String.valueOf((int) currentChar.getStatFloat(BLOCK)));

    }

    private String getHpspFormat(int stat){
        return (stat + "/" + stat);
    }

}