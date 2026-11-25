package declatoration.simulatorial.prep;

import static declatoration.simulatorial.fight.fightForeground.sonicJammer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import declatoration.simulatorial.R;
import declatoration.simulatorial.charAction;
import declatoration.simulatorial.databaseExec;
import declatoration.simulatorial.settingsDB;
import declatoration.simulatorial.viewAnimationHelper;

public class prepRoomMainFrag extends Fragment implements AdapterView.OnItemSelectedListener {


    int playerNum = 3;

    TextView textViewPlayerNum;
    EditText etPlayer, etMap;

    int clashMode;
    ArrayList<String> clashModeLabels;
    TextView clashTV;
    ImageButton clashModeUp, clashModeDown;

    Button actionSel1, actionSel2, actionSel3;
    SeekBar seekBarPlayer;
    Spinner spinner_1, spinner_2, spinner_3;
    SwitchCompat switchP1, switchP2, switchP3;
    int spinnerCountdown = 6;

    moveSelectorDatabase selectorDatabase;
    settingsDB settingsDatabase;

    LinearLayout ll_player2, ll_player3;
    ArrayList<String> actionsP1, actionsP2, actionsP3;

    prepRoom prep;
    charSelectionArrayAdapter adapter;


    public prepRoomMainFrag(prepRoom prepRoom, charSelectionArrayAdapter gnome){
        prep = prepRoom;
        adapter = gnome;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.prep_room_main_frag, container, false);

        ll_player2 = view.findViewById(R.id.ll_char_selection_2);
        ll_player3 = view.findViewById(R.id.ll_char_selection_3);

        etPlayer = view.findViewById(R.id.edit_text_player_lvl);
        etMap = view.findViewById(R.id.edit_text_map);
        textViewPlayerNum = view.findViewById(R.id.player_num_tv);

        seekBarPlayer = view.findViewById(R.id.seekbar_player);

        clashTV = view.findViewById(R.id.clash_mode_tv);
        clashModeUp = view.findViewById(R.id.clash_up_button);
        clashModeDown = view.findViewById(R.id.clash_down_button);

        actionSel1 = view.findViewById(R.id.move_selector1);
        actionSel2 = view.findViewById(R.id.move_selector2);
        actionSel3 = view.findViewById(R.id.move_selector3);

        switchP1 = view.findViewById(R.id.switch_p1);
        switchP2 = view.findViewById(R.id.switch_p2);
        switchP3 = view.findViewById(R.id.switch_p3);

        spinner_1 = view.findViewById(R.id.spinner_1);
        spinner_2 = view.findViewById(R.id.spinner_2);
        spinner_3 = view.findViewById(R.id.spinner_3);


        actionsP1 = new ArrayList<>(0);
        actionsP2 = new ArrayList<>(0);
        actionsP3 = new ArrayList<>(0);

        initialize();

        return view;
    }

    public void initialize(){

        spinner_1.setOnItemSelectedListener(this);
        spinner_2.setOnItemSelectedListener(this);
        spinner_3.setOnItemSelectedListener(this);


        spinner_1.setAdapter(adapter);
        spinner_2.setAdapter(adapter);
        spinner_3.setAdapter(adapter);


        ArrayList<String> playerNames = prepRoom.playerChars;
        if(playerNames.size() == 1){
            playerNames.addAll(Arrays.asList("-", "-"));
        } else {
            if(playerNames.size() == 2){
                playerNames.add("-");
            }
        }

        spinner_1.setSelection(adapter.getPosition(playerNames.get(0)));
        spinner_2.setSelection(adapter.getPosition(playerNames.get(1)));
        spinner_3.setSelection(adapter.getPosition(playerNames.get(2)));

        viewAnimationHelper.setSpinnerAnimation(spinner_1);
        viewAnimationHelper.setSpinnerAnimation(spinner_2);
        viewAnimationHelper.setSpinnerAnimation(spinner_3);

        ((BaseAdapter) spinner_1.getAdapter()).notifyDataSetChanged();
        ((BaseAdapter) spinner_2.getAdapter()).notifyDataSetChanged();
        ((BaseAdapter) spinner_3.getAdapter()).notifyDataSetChanged();

        etPlayer.setText("25");


        actionSel1.setOnClickListener(v -> inflateMoveSelector(adapter.getSelectedNameAt(0)));
        actionSel2.setOnClickListener(v -> inflateMoveSelector(adapter.getSelectedNameAt(1)));
        actionSel3.setOnClickListener(v -> inflateMoveSelector(adapter.getSelectedNameAt(2)));

        clashModeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clashMode != 2) {
                    clashMode++;
                } else{
                    clashMode = 0;
                }

                updateClashText();
            }
        });
        clashModeDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clashMode != 0) {
                    clashMode--;
                } else{
                    clashMode = 2;
                }

                updateClashText();
            }
        });



        seekBarPlayer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int playerAmount = 1 + (progress / 100);

                if (playerAmount != playerNum) {

                    playerNum = playerAmount;
                    textViewPlayerNum.setText(String.format(Integer.toString(playerNum)));
                    sonicJammer.playSound("menu", getContext());


                    switch (playerAmount) {
                        case 1:
                            viewAnimationHelper.hideViewWithAnimation(ll_player2, 200, spinner_2);
                            viewAnimationHelper.hideViewWithAnimation(ll_player3, 200, spinner_3);
                            break;
                        case 2:
                            viewAnimationHelper.showViewWithAnimation(ll_player2, 200);
                            viewAnimationHelper.hideViewWithAnimation(ll_player3, 200, spinner_3);
//                            adapter.updatePlayerSize(2);
                            break;
                        case 3:
                            viewAnimationHelper.showViewWithAnimation(ll_player2, 200);
                            viewAnimationHelper.showViewWithAnimation(ll_player3, 200);
//                            adapter.updatePlayerSize(3);
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


        actionsP1 = new ArrayList<>(5);
        actionsP2 = new ArrayList<>(5);
        actionsP3 = new ArrayList<>(5);

        clashModeLabels = new ArrayList<>(Arrays.asList(getResources().getString(R.string.clash_mode0),
                getResources().getString(R.string.clash_mode1), getResources().getString(R.string.clash_mode2)));

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        settingsDatabase = new settingsDB(getContext());
        selectorDatabase = new moveSelectorDatabase(prep.getBaseContext());

        clashMode = settingsDatabase.getClashMode() - 1;

        if(clashMode < 0 || clashMode > 2){
            clashMode = 1;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        updateClashText();
    }

    //finds chars actions, and then inflates moveSelector using those
    public void inflateMoveSelector(String name){

        ArrayList<charAction> charMoves = new ArrayList<>();

        String charName;
        if(name.contains("(")){
            charName = name.substring(0, name.lastIndexOf("(") - 1);
        } else{ charName = name;}


        databaseExec dbExec = databaseExec.getInstance(getContext());
        charMoves = dbExec.getAllCharUniqueActions(charName, Integer.parseInt(etPlayer.getText().toString()));


        int charIndex = adapter.getSelectedPlayerNames(3).indexOf(name);
        boolean isChecked = prepRoom.playerRandomize.get(charIndex);

        moveSelector selector = new moveSelector(prep, true, isChecked);

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

    private void updateClashText(){
        clashTV.setText(clashModeLabels.get(clashMode));
    }

    protected boolean getMapSelectedValid(){
        return (!(Integer.parseInt(getETText(etMap)) > 15 || Integer.parseInt(getETText(etMap)) < 1));
    }
    protected boolean playerFieldsFilled() {
        boolean levelsFilled = true;
        ArrayList<String> namesPicked = adapter.getSelectedPlayerNames(playerNum);

        if (getETText(etPlayer).equals("")) {
            levelsFilled = false;
        } else if (namesPicked.contains("-")) {
            levelsFilled = false;
        }

        return levelsFilled;
    }
    protected int getPlayerLevel() {
        return Integer.parseInt(getETText(etPlayer));
    }
    protected int getClashMode(){
        settingsDatabase.updateAll(settingsDatabase.getMainMusicId(), settingsDatabase.getSettingsMusicId(),
                settingsDatabase.getBackgroundResId(), clashMode);
        return clashMode;
    }

    protected boolean checkDupes(ArrayList<String> names) {
        return (playerNum > 1 && Objects.equals(names.get(0), names.get(1))) ||
                (playerNum > 2 && Objects.equals(names.get(0), names.get(2))) ||
                (playerNum > 2 && Objects.equals(names.get(1), names.get(2)));
    }

    protected boolean[] getFrontLineArray(){
        return new boolean[]{switchP1.isChecked(), switchP2.isChecked(), switchP3.isChecked()};
    }
    protected ArrayList<String> getPickedActionsArrayList(){
        if(actionsP1 != null && actionsP1.size() == 0){
            actionsP1 = selectorDatabase.getCharActionNames(adapter.getSelectedNameAt(0));
        }
        if(actionsP2 != null && actionsP2.size() == 0 && playerNum > 1){
            actionsP2 = selectorDatabase.getCharActionNames(adapter.getSelectedNameAt(1));
        } else {
            if(playerNum <= 1){
                actionsP2 = new ArrayList<>(Arrays.asList("", "", "", "", ""));
            }}
        if(actionsP3 != null && actionsP3.size() == 0 && playerNum > 2){
            actionsP3 = selectorDatabase.getCharActionNames(adapter.getSelectedNameAt(2));
        } else {
            if(playerNum <= 2){
                actionsP3 = new ArrayList<>(Arrays.asList("", "", "", "", ""));
            }}

        ArrayList<String> selectedActions = new ArrayList<>(15);

        // if moves should be randomly generated
        if(actionsP1 != null) {
            selectedActions.addAll(actionsP1);
        } else selectedActions.addAll(Arrays.asList(null, null, null, null, null));
        if(actionsP2 != null) {
            selectedActions.addAll(actionsP2);
        } else selectedActions.addAll(Arrays.asList(null, null, null, null, null));
        if(actionsP3 != null) {
            selectedActions.addAll(actionsP3);
        } else selectedActions.addAll(Arrays.asList(null, null, null, null, null));

        return selectedActions;
    }


    public void practicizeResult(ArrayList<String> selectedActions, String name) {

        ArrayList<String> selectedNames = adapter.getSelectedPlayerNames(playerNum);


        if (Objects.equals(selectedNames.get(0), name)) {
            actionsP1 = selectedActions;
        } else if (Objects.equals(selectedNames.get(1), name)) {
            actionsP2 = selectedActions;
        } else if (Objects.equals(selectedNames.get(2), name)) {
            actionsP3 = selectedActions;
        }

        if(selectedActions != null) {
            selectorDatabase.updateActionIds(name, selectedActions);
        }
    }





    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        if (parent.equals(spinner_1)) {

            adapter.addToSelection(position, 0);
            prepRoom.playerRandomize.set(0, false);

        } else if (parent.equals(spinner_2)) {
            if (spinner_2.getVisibility() == View.VISIBLE) {
                adapter.addToSelection(position, 1);
                prepRoom.playerRandomize.set(1, false);

            }
        } else {
            if (spinner_3.getVisibility() == View.VISIBLE) {
                adapter.addToSelection(position, 2);
                prepRoom.playerRandomize.set(2, false);
            }
        }

        if (spinnerCountdown == 0) {
            sonicJammer.playSound("spinner", getContext());
        } else {
            spinnerCountdown--;
        }

        prepRoom.playerChars = adapter.getSelectedPlayerNames(playerNum);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        sonicJammer.playSound("spinner", getContext());
    }
}
