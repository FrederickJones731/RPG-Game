package declatoration.simulatorial.overview;

import static declatoration.simulatorial.enums.stat.statArray;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import declatoration.simulatorial.R;
import declatoration.simulatorial.chars;


public class encyclofrag_bases extends Fragment {

    private TextView nametv, class1tv, class2tv;
    private String charName;
    private chars curChar;
    private encyclofragStatTable table;

    public encyclofrag_bases() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        charName = getArguments().getString("charName");
        curChar = new chars(getContext(), charName);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_encyclofrag_bases, container, false);

        this.nametv = view.findViewById(R.id.current_char_name);
        this.class1tv = view.findViewById(R.id.current_char_class1);
        this.class2tv = view.findViewById(R.id.current_char_class2);

        nametv.setText(curChar.getName());
        class1tv.setText(curChar.getClassName());
        class2tv.setText(curChar.getAuxClassName());


        table = view.findViewById(R.id.table_layout);
        table.init(curChar);

        table.setLayoutParams(new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                0));


        return view;
    }

    public void updateChar(String charName){
        this.charName = charName;
        curChar = new chars(getContext(), charName);

        table.init(curChar);
    }



//    public void updateView(chars currentChar){
//
//        this.nametv.setText(currentChar.getName());
//        this.class1tv.setText(currentChar.getClassName());
//        this.class2tv.setText(currentChar.getAuxClassName());
//        this.hptv.setText(String.valueOf(currentChar.getStatFloat("hp")));
//        this.sptv.setText(String.valueOf(currentChar.getStatFloat("sp")));
//
//        this.atktv.setText(String.valueOf(currentChar.getStatFloat("atk")));
//        this.deftv.setText(String.valueOf(currentChar.getStatFloat("def")));
//        this.magtv.setText(String.valueOf(currentChar.getStatFloat("mag")));
//        this.inttv.setText(String.valueOf(currentChar.getStatFloat("int")));
//        this.spdtv.setText(String.valueOf(currentChar.getStatFloat("spd")));
//        this.lcktv.setText(String.valueOf(currentChar.getStatFloat("luck")));
//        this.hittv.setText(String.valueOf(currentChar.getStatFloat("hit")));
//        this.crittv.setText(String.valueOf(currentChar.getStatFloat("crit")));
//        this.physavotv.setText(String.valueOf(currentChar.getStatFloat("physavo")));
//        this.magavotv.setText(String.valueOf(currentChar.getStatFloat("magavo")));
//        this.restv.setText(String.valueOf(currentChar.getStatFloat("res")));
//        this.blocktv.setText(String.valueOf(currentChar.getStatFloat("block")));
//
//    }
}