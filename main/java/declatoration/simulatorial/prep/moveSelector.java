package declatoration.simulatorial.prep;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

import declatoration.simulatorial.R;
import declatoration.simulatorial.charAction;
import declatoration.simulatorial.fight.actionTextBubble;
import declatoration.simulatorial.getImageRes;
import declatoration.simulatorial.viewAnimationHelper;
import pl.droidsonroids.gif.GifImageButton;

public class moveSelector extends Dialog {

    String charName;
    moveSelectLinker linker;
    boolean randomizeChecked;

    ConstraintLayout root;
    GifImageButton back;
    TextView title;
    View clickView;

    boolean updateSelectedActions = true;
    TextView randomizeMovesTV;
    SwitchCompat randomizeMovesSwitch;

//    ConstraintLayout[] cls = new ConstraintLayout[11];
//    SwitchCompat[] moveSelectorSwitches = new SwitchCompat[11];
//    ImageView[] actionIcons = new ImageView[11];
//    ImageView[] affinityIcons = new ImageView[11];
//    TextView[] actionNames = new TextView[11];
//    TextView[] spTVs = new TextView[11];
//    ImageButton[] infoButtons = new ImageButton[11];

    ArrayList<moveSelectorRow> selectorRows = new ArrayList<>();

    LinearLayout moveHolder;

    int selectedMoveCount = 0;
    ArrayList<String> actionNameList = new ArrayList<>();
    ArrayList<String> selectedActions = new ArrayList<>();

    boolean bubbleUp = false;
    boolean isMainFrag;


    public moveSelector(Activity prepRoomInstance, boolean isPlayer, boolean randomizeIsChecked) {
        super(prepRoomInstance);

        linker = (moveSelectLinker) prepRoomInstance;
        isMainFrag = isPlayer;
        randomizeChecked = randomizeIsChecked;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_move_selector);

        root = findViewById(R.id.root_view);
        back = findViewById(R.id.back_button);
        title = findViewById(R.id.title);
        clickView = findViewById(R.id.clicker);

        moveHolder = findViewById(R.id.move_holder_ll);

        randomizeMovesSwitch = findViewById(R.id.randomize_moves_switch);
        randomizeMovesTV = findViewById(R.id.randomize_moves_tv);

        back.setOnClickListener(v -> {
            if(!randomizeMovesSwitch.isChecked()) {
                if (selectedMoveCount == 5) {
                    linker.exportResult(selectedActions, charName, isMainFrag, randomizeChecked);
                    dismiss();
                } else {
                    if (selectedMoveCount < 5) {
                        for (int i = selectedMoveCount; i < 5; i++) {
                            selectedActions.add(null);
                        }

                        linker.exportResult(selectedActions, charName, isMainFrag, randomizeChecked);
                        dismiss();
                    } else {
                        Toast.makeText(getContext(), "Please select up to 5 actions!", Toast.LENGTH_SHORT).show();

                    }
                }
            } else {
                linker.exportResult(null, charName, isMainFrag, randomizeChecked);
                dismiss();
            }
        });

        randomizeMovesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                randomizeChecked = isChecked;
                if(isChecked){
                    randomizeMovesTV.setText(R.string.randomize);
                    updateSwitches(false);
                } else{
                    randomizeMovesTV.setText(R.string.choose);
                    updateSwitches(true);
                }
            }
        });


    }



    public void setUpActionNames(Context context, ArrayList<charAction> actions, String name,
                                 ArrayList<String> dbSelectedActions){

        charName = name;
        String titleText = "Action Picker - " + charName;
        title.setText(titleText);

        //actions get i+1, to compensate for normal attack
        for (int i = 0; i < actions.size(); i++) {

            if(i < actions.size() - 1) {

                actionNameList.add(actions.get(i + 1).getName());

                selectorRows.add(new moveSelectorRow(getContext()));
                selectorRows.get(i).initialize(this, actions.get(i + 1));

                moveHolder.addView(selectorRows.get(i));

                int finalI = i;
                selectorRows.get(i).getSwitch().setOnCheckedChangeListener((buttonView, isChecked) -> {

                    //if click is not okay (6th action picked)
                    if(isChecked && selectedMoveCount == 5){
                        Toast.makeText(getContext(), "Please Select Up To 5 Moves!", Toast.LENGTH_SHORT).show();
                        selectorRows.get(finalI).setChecked(false);
                    } else {
                        // if click is okay
                        if(updateSelectedActions) {
                            if (isChecked) {
                                selectedMoveCount++;
                                selectedActions.add(selectorRows.get(finalI).getActionName());

                            } else {

                                selectedMoveCount--;

                                //remove from list if it exists
                                if (selectedActions.lastIndexOf(actionNameList.get(finalI)) != -1) {
                                    selectedActions.remove(selectedActions.lastIndexOf(actionNameList.get(finalI)));
                                }
                            }
                        } else {
                            Toast.makeText(context, "Moves are to be Randomized!", Toast.LENGTH_SHORT).show();
                        }
                    }

                });


                if(dbSelectedActions.contains(actionNameList.get(i))){
                    selectorRows.get(i).setChecked(true);
                }

            }

        }


        randomizeMovesSwitch.setChecked(randomizeChecked);
    }

    public void inflateBubble(charAction action){
        if(!bubbleUp) {
            actionTextBubble bubble = new actionTextBubble(getContext(), action);
            bubbleUp = true;

            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 500;
            params.topMargin = 500;
            bubble.setElevation(50);
            bubble.setLayoutParams(params);

            root.addView(bubble);
            viewAnimationHelper.showViewWithAnimation(bubble, 500);

            clickView.setVisibility(View.VISIBLE);
            clickView.setOnClickListener(v1 -> {
                bubbleUp = false;
                viewAnimationHelper.hideViewWithAnimation(bubble, 500).start();
                root.removeView(bubble);
                clickView.setOnClickListener(null);
                clickView.setVisibility(View.GONE);
            });
        } else{
            clickView.callOnClick();
        }
    }

    public int getSelectedMoveCount(){
        return selectedMoveCount;
    }

    public void updateSwitches(boolean toOnState){
        updateSelectedActions = false;

        for (int i = 0; i < selectorRows.size(); i++) {
            if(i < actionNameList.size() &&
                    selectedActions.contains(actionNameList.get(i))) {
                selectorRows.get(i).getSwitch().setChecked(toOnState);
            }
            selectorRows.get(i).setSwitchClickable(toOnState);
        }

        updateSelectedActions = true;
    }
    @Override
    public void onBackPressed() {
        back.callOnClick();
    }
}