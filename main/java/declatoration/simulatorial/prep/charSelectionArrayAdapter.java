package declatoration.simulatorial.prep;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import declatoration.simulatorial.R;
import declatoration.simulatorial.actionDatabase;
import declatoration.simulatorial.databaseExec;
import declatoration.simulatorial.enums.weaponType;
import declatoration.simulatorial.getImageRes;

public class charSelectionArrayAdapter extends ArrayAdapter<String> {


    boolean[] frontlineArray = new boolean[4];
    ArrayList<String> allPlayerCharNames, selectedPlayerNames;


    public charSelectionArrayAdapter(Context context, ArrayList<String> names) {

        super(context, R.layout.spinner_box, R.id.spinner_char_name, names);

        allPlayerCharNames = names;
        allPlayerCharNames.add(0, "-");

        selectedPlayerNames = new ArrayList<>(Arrays.asList("-", "-", "-", "-"));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        v = inflater.inflate(R.layout.spinner_box, null);

        TextView nameTV = v.findViewById(R.id.spinner_char_name);
        ImageView weaponIcon = v.findViewById(R.id.weapon_icon);


        nameTV.setText(allPlayerCharNames.get(position));


        databaseExec dbExec = databaseExec.getInstance(getContext());

        if(allPlayerCharNames.get(position).trim().length() > 1) {
            weaponType weaponType = dbExec.getCharWeaponType(allPlayerCharNames.get(position));
            weaponIcon.setBackgroundResource(getImageRes.getWeaponIcon(weaponType));
        }



        return v;
    }

    public void addToSelection(int sel, int position) {
        if(sel < allPlayerCharNames.size()) {

            if (position == selectedPlayerNames.size()) {
                this.selectedPlayerNames.add(position, allPlayerCharNames.get(sel));
            } else {
                if (position < selectedPlayerNames.size()) {
                    this.selectedPlayerNames.set(position, allPlayerCharNames.get(sel));
                }
            }
        }
    }

    public ArrayList<String> getSelectedPlayerNames(int playerNum){
        for (int i = 0; i < 3; i++) {
            if(selectedPlayerNames.size() > playerNum){
                selectedPlayerNames.remove(selectedPlayerNames.size() - 1);
            }
        }
        return this.selectedPlayerNames;
    }

    public String getSelectedNameAt(int position){
        return selectedPlayerNames.get(position);
    }

    public String getNameAt(int position){
        return allPlayerCharNames.get(position);
    }


    public void setFrontlineArray(boolean[] frontlineArray) {
        this.frontlineArray = frontlineArray;
    }
}
