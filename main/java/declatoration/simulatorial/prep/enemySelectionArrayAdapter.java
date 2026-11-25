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
import declatoration.simulatorial.numDatabase;

public class enemySelectionArrayAdapter extends ArrayAdapter<String> {


    ArrayList<String> allCharNames, selectedEnemyNames;;


    public enemySelectionArrayAdapter(Context context, ArrayList<String> allNames) {

        super(context, R.layout.spinner_box, R.id.spinner_char_name, allNames);

        allCharNames = allNames;
        allCharNames.add(0, "-");

        selectedEnemyNames = new ArrayList<>(Arrays.asList("-", "-", "-", "-", "-"));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        v = inflater.inflate(R.layout.spinner_box, null);

        TextView nameTV = v.findViewById(R.id.spinner_char_name);
        ImageView weaponIcon = v.findViewById(R.id.weapon_icon);


        nameTV.setText(allCharNames.get(position));


         if(allCharNames.get(position).trim().length() > 1) {
             databaseExec dbExec = databaseExec.getInstance(getContext());
             weaponType weaponType = dbExec.getCharWeaponType(allCharNames.get(position));
             weaponIcon.setBackgroundResource(getImageRes.getWeaponIcon(weaponType));
         }

        return v;
    }

    public void addToSelection(int sel, int position) {

            if(position >= selectedEnemyNames.size()) {
                this.selectedEnemyNames.add(position, allCharNames.get(sel));
            } else{
                this.selectedEnemyNames.set(position, allCharNames.get(sel));
            }

    }

    public ArrayList<String> getSelectedEnemyNames(int enemyNum){
        for (int i = 0; i < 2; i++) {
            if(selectedEnemyNames.size() > enemyNum){
                selectedEnemyNames.remove(selectedEnemyNames.size() - 1);
            }
        }
        return this.selectedEnemyNames;
    }

    public String getSelectedNameAt(int position){
            return selectedEnemyNames.get(position);

    }
}
