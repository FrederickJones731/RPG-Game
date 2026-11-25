package declatoration.simulatorial.overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import declatoration.simulatorial.R;

public class encyclofrag_skills extends Fragment {


    TextView header;
    String charName;

    encyclofragActionTable skillTable;


    public encyclofrag_skills() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        charName = getArguments().getString("charName");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_encyclofrag_skills, container, false);

        header = view.findViewById(R.id.skill_frag_name_title);
        skillTable = view.findViewById(R.id.skill_frag_action_table);
        skillTable.bestart(charName, true);

        String str = charName + "'s Skills";
        header.setText(str);

        return view;
    }
}