package declatoration.simulatorial.overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import declatoration.simulatorial.R;

public class encyclofrag_actions extends Fragment {


    String charName;
    TextView header;
    encyclofragActionTable actionTable;

    public encyclofrag_actions() {}



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        charName = getArguments().getString("charName");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_encyclofrag_actions, container, false);

        header = view.findViewById(R.id.actions_frag_name_title);
        actionTable = view.findViewById(R.id.actions_frag_action_table);
        actionTable.bestart(charName, false);

        String str = charName + "'s Actions";
        header.setText(str);

        return view;
    }
}