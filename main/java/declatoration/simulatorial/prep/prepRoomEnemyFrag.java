package declatoration.simulatorial.prep;

import static declatoration.simulatorial.fight.fightForeground.sonicJammer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import declatoration.simulatorial.R;
import declatoration.simulatorial.charAction;
import declatoration.simulatorial.databaseExec;
import declatoration.simulatorial.viewAnimationHelper;

public class prepRoomEnemyFrag extends Fragment implements AdapterView.OnItemSelectedListener {



    int enemyNum = 4;

    TextView textViewEnemyNum, enemyToggleTV, aiToggleTV;
    EditText etEnemy, etStrengthLevel;

    SwitchCompat aiSwitch, playerCharSwitch;

    View scrollBlocker;
    Button actionSel1, actionSel2, actionSel3, actionSel4, actionSel5;
    SeekBar seekBarEnemy;
    Spinner spinner_1, spinner_2, spinner_3, spinner_4, spinner_5;
    SwitchCompat switchE1, switchE2, switchE3, switchE4, switchE5;
    int spinnerCountdown = 6;

    moveSelectorDatabase selectorDatabase;

    LinearLayout ll_enemy2, ll_enemy3, ll_enemy4, ll_enemy5;
    ArrayList<String> actionsE1, actionsE2, actionsE3, actionsE4, actionsE5;

    prepRoom prep;
    enemySelectionArrayAdapter adapter;


    public prepRoomEnemyFrag(prepRoom prep, enemySelectionArrayAdapter plimp){
        this.prep = prep;
        adapter = plimp;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        actionsE1 = new ArrayList<>(0);
        actionsE2 = new ArrayList<>(0);
        actionsE3 = new ArrayList<>(0);
        actionsE4 = new ArrayList<>(0);
        actionsE5 = new ArrayList<>(0);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.prep_room_enemy_frag, container, false);

        enemyToggleTV = view.findViewById(R.id.char_enemy_toggle_tv);
        aiSwitch = view.findViewById(R.id.ai_toggle_switch);
        aiToggleTV = view.findViewById(R.id.ai_mode_toggle_tv);

        ll_enemy2 = view.findViewById(R.id.ll_char_selection_2);
        ll_enemy3 = view.findViewById(R.id.ll_char_selection_3);
        ll_enemy4 = view.findViewById(R.id.ll_char_selection_4);
        ll_enemy5 = view.findViewById(R.id.ll_char_selection_5);

        etEnemy = view.findViewById(R.id.edit_text_enemy_lvl);
        etStrengthLevel = view.findViewById(R.id.edit_text_str_level);
        textViewEnemyNum = view.findViewById(R.id.player_side_num_tv);
        playerCharSwitch = view.findViewById(R.id.char_enemy_switch);

        seekBarEnemy = view.findViewById(R.id.seekbar_player_side);

        scrollBlocker = view.findViewById(R.id.scroll_blocker);

        actionSel1 = view.findViewById(R.id.move_selector1);
        actionSel2 = view.findViewById(R.id.move_selector2);
        actionSel3 = view.findViewById(R.id.move_selector3);
        actionSel4 = view.findViewById(R.id.move_selector4);
        actionSel5 = view.findViewById(R.id.move_selector5);

        switchE1 = view.findViewById(R.id.switch_p1);
        switchE2 = view.findViewById(R.id.switch_p2);
        switchE3 = view.findViewById(R.id.switch_p3);
        switchE4 = view.findViewById(R.id.switch_p4);
        switchE5 = view.findViewById(R.id.switch_p5);

        spinner_1 = view.findViewById(R.id.spinner_1);
        spinner_2 = view.findViewById(R.id.spinner_2);
        spinner_3 = view.findViewById(R.id.spinner_3);
        spinner_4 = view.findViewById(R.id.spinner_4);
        spinner_5 = view.findViewById(R.id.spinner_5);

        initialize();

        return view;
    }

    public void initialize(){

        selectorDatabase = new moveSelectorDatabase(prep.getBaseContext());

        spinner_1.setOnItemSelectedListener(this);
        spinner_2.setOnItemSelectedListener(this);
        spinner_3.setOnItemSelectedListener(this);
        spinner_4.setOnItemSelectedListener(this);
        spinner_5.setOnItemSelectedListener(this);


        spinner_1.setAdapter(adapter);
        spinner_2.setAdapter(adapter);
        spinner_3.setAdapter(adapter);
        spinner_4.setAdapter(adapter);
        spinner_5.setAdapter(adapter);


        ArrayList<String> enemyNames = prepRoom.enemyChars;
        switch (enemyNames.size()){
            case 5:
                spinner_5.setSelection(adapter.getPosition(enemyNames.get(4)));
            case 4:
                spinner_4.setSelection(adapter.getPosition(enemyNames.get(3)));
            case 3:
                spinner_3.setSelection(adapter.getPosition(enemyNames.get(2)));
            case 2:
                spinner_2.setSelection(adapter.getPosition(enemyNames.get(1)));
            case 1:
                spinner_1.setSelection(adapter.getPosition(enemyNames.get(0)));
        }
        switch (enemyNames.size()){
            case 1:
                ll_enemy2.setVisibility(View.INVISIBLE);
            case 2:
                ll_enemy3.setVisibility(View.INVISIBLE);
            case 3:
                ll_enemy4.setVisibility(View.INVISIBLE);
            case 4:
                ll_enemy5.setVisibility(View.INVISIBLE);
        }

        viewAnimationHelper.setSpinnerAnimation(spinner_1);
        viewAnimationHelper.setSpinnerAnimation(spinner_2);
        viewAnimationHelper.setSpinnerAnimation(spinner_3);
        viewAnimationHelper.setSpinnerAnimation(spinner_4);
        viewAnimationHelper.setSpinnerAnimation(spinner_5);

        ((BaseAdapter) spinner_1.getAdapter()).notifyDataSetChanged();
        ((BaseAdapter) spinner_2.getAdapter()).notifyDataSetChanged();
        ((BaseAdapter) spinner_3.getAdapter()).notifyDataSetChanged();
        ((BaseAdapter) spinner_4.getAdapter()).notifyDataSetChanged();
        ((BaseAdapter) spinner_5.getAdapter()).notifyDataSetChanged();


        actionSel1.setOnClickListener(v -> inflateMoveSelector(adapter.getSelectedNameAt(0)));
        actionSel2.setOnClickListener(v -> inflateMoveSelector(adapter.getSelectedNameAt(1)));
        actionSel3.setOnClickListener(v -> inflateMoveSelector(adapter.getSelectedNameAt(2)));
        actionSel4.setOnClickListener(v -> inflateMoveSelector(adapter.getSelectedNameAt(3)));
        actionSel5.setOnClickListener(v -> inflateMoveSelector(adapter.getSelectedNameAt(4)));


        actionsE1 = new ArrayList<>(Arrays.asList(null, null, null, null, null));
        actionsE2 = new ArrayList<>(Arrays.asList(null, null, null, null, null));
        actionsE3 = new ArrayList<>(Arrays.asList(null, null, null, null, null));
        actionsE4 = new ArrayList<>(Arrays.asList(null, null, null, null, null));
        actionsE5 = new ArrayList<>(Arrays.asList(null, null, null, null, null));



        etEnemy.setText("25");

        seekBarEnemy.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int enemyAmount = 1 + (progress / 100);

                if (enemyAmount != enemyNum) {

                    enemyNum = enemyAmount;
                    textViewEnemyNum.setText(String.format(Integer.toString(enemyNum)));
                    sonicJammer.playSound("menu", getContext());


                    switch (enemyAmount) {
                        case 1:
                            viewAnimationHelper.hideViewWithAnimation(ll_enemy2, 200, spinner_2);
                            viewAnimationHelper.hideViewWithAnimation(ll_enemy3, 200, spinner_3);
                            viewAnimationHelper.hideViewWithAnimation(ll_enemy4, 200, spinner_4);
                            viewAnimationHelper.hideViewWithAnimation(ll_enemy5, 200, spinner_5);
//                            adapter.updateEnemySize(1);
                            break;
                        case 2:
                            viewAnimationHelper.showViewWithAnimation(ll_enemy2, 200);
                            viewAnimationHelper.hideViewWithAnimation(ll_enemy3, 200, spinner_3);
                            viewAnimationHelper.hideViewWithAnimation(ll_enemy4, 200, spinner_4);
                            viewAnimationHelper.hideViewWithAnimation(ll_enemy5, 200, spinner_5);
//                            adapter.updateEnemySize(2);
                            break;
                        case 3:
                            viewAnimationHelper.showViewWithAnimation(ll_enemy2, 200);
                            viewAnimationHelper.showViewWithAnimation(ll_enemy3, 200);
                            viewAnimationHelper.hideViewWithAnimation(ll_enemy4, 200, spinner_4);
                            viewAnimationHelper.hideViewWithAnimation(ll_enemy5, 200, spinner_5);
//                            adapter.updateEnemySize(3);
                            break;
                        case 4:
                            viewAnimationHelper.showViewWithAnimation(ll_enemy2, 200);
                            viewAnimationHelper.showViewWithAnimation(ll_enemy3, 200);
                            viewAnimationHelper.showViewWithAnimation(ll_enemy4, 200);
                            viewAnimationHelper.hideViewWithAnimation(ll_enemy5, 200, spinner_5);
//                            adapter.updateEnemySize(4);
                            break;
                        case 5:
                            viewAnimationHelper.showViewWithAnimation(ll_enemy2, 200);
                            viewAnimationHelper.showViewWithAnimation(ll_enemy3, 200);
                            viewAnimationHelper.showViewWithAnimation(ll_enemy4, 200);
                            viewAnimationHelper.showViewWithAnimation(ll_enemy5, 200);
//                            adapter.updateEnemySize(5);
                            break;
                        default:
                    }
                }


            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        playerCharSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    scrollBlocker.setClickable(false);
                    scrollBlocker.setVisibility(View.GONE);
                    enemyToggleTV.setText(R.string.enemy_toggle_1);
                } else{
                    scrollBlocker.setClickable(true);
                    scrollBlocker.setVisibility(View.VISIBLE);
                    enemyToggleTV.setText(R.string.enemy_toggle_2);
                }
            }
        });

        aiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    aiToggleTV.setText(R.string.on);
                } else{
                    aiToggleTV.setText(R.string.off);
                }
            }
        });


    }



    //finds chars actions, and then inflates moveSelector using those
    public void inflateMoveSelector(String name){

        ArrayList<charAction> charMoves = new ArrayList<>();

        String charName;
        if(name.contains("(")){
            charName = name.substring(0, name.lastIndexOf("(") - 1);
        } else{ charName = name;}

        databaseExec dbExec = databaseExec.getInstance(getContext());
        charMoves = dbExec.getAllCharUniqueActions(charName, Integer.parseInt(etEnemy.getText().toString()));


        int charIndex = adapter.getSelectedEnemyNames(5).indexOf(name);
        boolean isChecked = prepRoom.enemyRandomize.get(charIndex);

        moveSelector selector = new moveSelector(prep, false, isChecked);

        selector.show();

        int width = (int)(getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels * 0.85);

        selector.getWindow().setLayout(width, height);

        selector.setUpActionNames(prep.getBaseContext(), charMoves, name, selectorDatabase.getCharActionNames(name));
    }


    //simples

    private String getETText(EditText e) {
        String str = "";
        if (e.isClickable()) {
            str = e.getText().toString();
        }
        return str;
    }


    protected boolean enemyFieldsFilled() {
        if(playerCharSwitch.isChecked()) {
            // if player decides to input their own chars
            boolean levelsFilled = true;
            ArrayList<String> namesPicked = adapter.getSelectedEnemyNames(enemyNum);

            if (getETText(etEnemy).equals("")) {
                levelsFilled = false;
            } else if (namesPicked.contains("-")) {
                levelsFilled = false;
            }

            return levelsFilled;
        } else{
            return true;
        }
    }
    protected int getEnemyLevel() {
        if(etEnemy == null){
            return 25;
        }
        return Integer.parseInt(getETText(etEnemy));
    }
    protected ArrayList<Integer> getStrValue(){
        if(etStrengthLevel == null){
            return null;
        } else {
            String str = etStrengthLevel.getText().toString();
            if(!str.equals("")) {
                Integer val = Integer.parseInt(str);
                return new ArrayList<>(List.of(val));
            } else{
                return null;
            }
        }
    }

    protected boolean checkDupes(ArrayList<String> names) {
        boolean dupes = false;

        for (int i = 0; i < names.size(); i++) {
            for (int j = i + 1; j < names.size(); j++) {
                if(Objects.equals(names.get(i), names.get(j))){
                    dupes = true;
                    break;
                }
            }
        }

        return !dupes;
    }

    protected boolean getAiIsOn(){
        return (aiSwitch == null || aiSwitch.isChecked());}
    protected boolean[] getFrontLineArray(){
        if(switchE4 == null){
            return null;
        } else {
            return new boolean[]{switchE1.isChecked(), switchE2.isChecked(), switchE3.isChecked(),
                    switchE4.isChecked(), switchE5.isChecked()};
        }
    }
    protected ArrayList<String> getPickedActionsArrayList(){
        if(playerCharSwitch != null && playerCharSwitch.isChecked()) {
            switch (enemyNum){
                case 5:
                    actionsE5 = selectorDatabase.getCharActionNames(adapter.getSelectedNameAt(4));
                case 4:
                    actionsE4 = selectorDatabase.getCharActionNames(adapter.getSelectedNameAt(3));
                case 3:
                    actionsE3 = selectorDatabase.getCharActionNames(adapter.getSelectedNameAt(2));
                case 2:
                    actionsE2 = selectorDatabase.getCharActionNames(adapter.getSelectedNameAt(1));
                case 1:
                default:
                    actionsE1 = selectorDatabase.getCharActionNames(adapter.getSelectedNameAt(0));
            }


            ArrayList<String> selectedActions = new ArrayList<>(25);

            selectedActions.addAll(actionsE1);
            selectedActions.addAll(actionsE2);
            selectedActions.addAll(actionsE3);
            selectedActions.addAll(actionsE4);
            selectedActions.addAll(actionsE5);

            return selectedActions;
        } else{

            return null;
        }

    }


    public void practicizeResult(ArrayList<String> selectedActions, String name) {

        ArrayList<String> selectedNames = adapter.getSelectedEnemyNames(enemyNum);

        if(Objects.equals(selectedNames.get(0), name)){
            actionsE1 = selectedActions;
        } else if (Objects.equals(selectedNames.get(1), name)) {
            actionsE2 = selectedActions;
        } else if(Objects.equals(selectedNames.get(2), name)){
            actionsE3 = selectedActions;
        } else if(Objects.equals(selectedNames.get(3), name)){
            actionsE4 = selectedActions;
        } else if(Objects.equals(selectedNames.get(4), name)){
            actionsE5 = selectedActions;
        }

        if(selectedActions != null) {
            selectorDatabase.updateActionIds(name, selectedActions);
        }
    }





    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.equals(spinner_1)) {

            adapter.addToSelection(position, 0);
            prepRoom.enemyRandomize.set(0, true);

        } else if (parent.equals(spinner_2)) {
            if (spinner_2.getVisibility() == View.VISIBLE) {
                adapter.addToSelection(position, 1);
                prepRoom.enemyRandomize.set(1, true);

            }
        } else if (parent.equals(spinner_3)) {
            if (spinner_3.getVisibility() == View.VISIBLE) {
                adapter.addToSelection(position, 2);
                prepRoom.enemyRandomize.set(2, true);

            }
        } else if (parent.equals(spinner_4)) {
            if (spinner_4.getVisibility() == View.VISIBLE) {
                adapter.addToSelection(position, 3);
                prepRoom.enemyRandomize.set(3, true);

            }
        } else if (parent.equals(spinner_5)) {
            if (spinner_5.getVisibility() == View.VISIBLE) {
                adapter.addToSelection(position, 4);
                prepRoom.enemyRandomize.set(4, true);

            }
        }

        if (spinnerCountdown == 0) {
            sonicJammer.playSound("spinner", getContext());
        } else {
            spinnerCountdown--;
        }

        prepRoom.enemyChars = adapter.getSelectedEnemyNames(enemyNum);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        sonicJammer.playSound("spinner", getContext());
    }

}
