package declatoration.simulatorial.fight;

import static declatoration.simulatorial.fight.fightForeground.sonicJammer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

import declatoration.simulatorial.R;
import declatoration.simulatorial.charAction;
import declatoration.simulatorial.viewAnimationHelper;


@SuppressLint("ViewConstructor")
public class attackPickerLayout extends ConstraintLayout {


    boolean resultPicked = false;
    boolean bubbleUp = false;
    int actionsRemaining;
    int result;

    attackPickedBridger atkInterface;

    ArrayList<charAction> charActionArray;

    ConstraintLayout root;
    LinearLayout attackLayout;

    private Button normalAttack, rowAction, hpPotion, spPotion, hideLayout, waitButton;
    private Button[] charActions;
    private TextView actionsRemainingCount;
    private View screenBlocker;

    ViewGroup v;


    public attackPickerLayout(Context context, attackPickedBridger atkInterface, ArrayList<charAction> charActions,
                              int actionsRemaining) {
        super(context);
        this.atkInterface = atkInterface;
        this.charActionArray = charActions;
        this.actionsRemaining = actionsRemaining;

        initialize();
    }


    private void initialize() {

        v = (ViewGroup) inflate(getContext(), R.layout.fragment_attack_picker, this);

        root = v.findViewById(R.id.attack_layout);
        attackLayout = v.findViewById(R.id.attack_button_layout);
        hideLayout = v.findViewById(R.id.hide_attack_layout_button);

        normalAttack = v.findViewById(R.id.special_button1);
//        focusAction = v.findViewById(R.id.focus_action);
        rowAction = v.findViewById(R.id.row_action);
        waitButton = v.findViewById(R.id.wait_action);

        hpPotion = v.findViewById(R.id.hp_potion);
        spPotion = v.findViewById(R.id.sp_potion);

        charActions = new Button[5];
        charActions[0] = v.findViewById(R.id.action1);
        charActions[1] = v.findViewById(R.id.action2);
        charActions[2] = v.findViewById(R.id.action3);
        charActions[3] = v.findViewById(R.id.action4);
        charActions[4] = v.findViewById(R.id.action5);

        actionsRemainingCount = v.findViewById(R.id.actions_remaining_count);
        screenBlocker = v.findViewById(R.id.screen_blocker);


        normalAttack.setText(R.string.attack);
//        guardAction.setText(R.string.guard);
//        focusAction.setText(R.string.focus);
        rowAction.setText(R.string.row);
        waitButton.setText(R.string.wait_action);
        hpPotion.setText(R.string.HP);
        spPotion.setText(R.string.SP);
        actionsRemainingCount.setText(String.valueOf(actionsRemaining));


        hideLayout.setOnClickListener(view -> {
            sonicJammer.playSound("click", getContext());
            if (attackLayout.getVisibility() == VISIBLE) {
                viewAnimationHelper.hideViewWithAnimation(attackLayout, 150).start();
                hideLayout.setRotation(180);
                atkInterface.setAttackFragClickability(false);
            } else {
                viewAnimationHelper.showViewWithAnimation(attackLayout, 150);
                hideLayout.setRotation(0);
                atkInterface.setAttackFragClickability(true);
            }

        });

//        assistAction.setOnClickListener(v -> {
//            if(!bubbleUp) {
//                result = -6;
//                sfxGuy.playSound( "assist_action");
//                exitSequence();
//            }
//        });
        screenBlocker.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                root.callOnClick();
            }
        });
        rowAction.setOnClickListener(v -> {
            if(!bubbleUp) {
                result = -5;
                sonicJammer.playSound( "row_shift", getContext());
                exitSequence();
            }
        });
        spPotion.setOnClickListener(v -> {
            if(!bubbleUp) {
                result = -4;
                sonicJammer.playSound( "heal", getContext());
                exitSequence();
            }
        });
        hpPotion.setOnClickListener(v -> {
            if(!bubbleUp) {
                result = -3;
                sonicJammer.playSound( "heal", getContext());
                exitSequence();
            }
        });
        waitButton.setOnClickListener(v -> {
            if(!bubbleUp) {
                result = -10;
                sonicJammer.playSound( "miss", getContext());
                exitSequence();
            }
        });
//         .setOnClickListener(v -> {
//            if(!bubbleUp) {
//                result = -2;
//                sfxGuy.playSound( "focus_action");
//                exitSequence();
//            }
//        });
//        guardAction.setOnClickListener(v -> {
//            if(!bubbleUp) {
//                result = -1;
//                sfxGuy.playSound( "guard_action");
//                exitSequence();
//            }
//        });
        normalAttack.setOnClickListener(v -> {
            if(!bubbleUp) {
                sonicJammer.playSound( "fight_select", getContext());

                result = 0;
                exitSequence();
            }
        });

    }


    public void setupAttackNames(@NonNull ArrayList<String> actionNames, String[] unavailableCause,
                                 boolean rowsCollapsed, String rowActionUnavailableCause) {

        if(rowsCollapsed || rowActionUnavailableCause != null) {
            rowAction.setOnClickListener(v -> {
                if(!bubbleUp) {
                    sonicJammer.playSound( "error", getContext());

                    if(rowsCollapsed){
                        atkInterface.rowCollapsedToast();
                    } else if(rowActionUnavailableCause.equals("full")){
                        atkInterface.rowFilledToast();
                    } else{
                        // row lockdown
                        atkInterface.rowLockdownToast();
                    }
                }
            });

            rowAction.setBackgroundResource(R.drawable.redrivium);

        }

        if(unavailableCause[0] != null){
            normalAttack.setOnClickListener(v -> {
                sonicJammer.playSound( "error", getContext());
                atkInterface.attackUnavailable(unavailableCause[0]);
            });
            normalAttack.setBackgroundResource(R.drawable.redrivium);
        }

        actionNames.trimToSize();

        for (int o = 1; o < 6; o++) {
            //if action exists

            if (actionNames.size() > o) {

                setLongClickListener(charActions[o - 1]);

                if((unavailableCause[o] != null)) {
                    int finalO = o;
                    charActions[o - 1].setOnClickListener(v -> {
                        sonicJammer.playSound( "error", getContext());
                        atkInterface.attackUnavailable(unavailableCause[finalO]);
                    });
                    charActions[o - 1].setBackgroundResource(R.drawable.redrivium);


                } else {
                    final int temp = o;
                    charActions[o - 1].setOnClickListener(v -> {
                                if(!bubbleUp) {
                                    sonicJammer.playSound( "fight_select", getContext());
                                    result = temp;
                                    exitSequence();
                                }
                    });
                }

                String text;
                if(unavailableCause[o] != null && unavailableCause[o].contains("cooldown")){
                     text = actionNames.get(o) + " ";
                     if(unavailableCause[o].charAt(unavailableCause[o].length() - 2) == 'n'){
                         text += unavailableCause[o].charAt(unavailableCause[o].length() - 1);
                     } else {
                         text += unavailableCause[o].charAt(unavailableCause[o].length() - 2) +
                                 unavailableCause[o].charAt(unavailableCause[o].length() - 1);
                     }
                    charActions[o - 1].setText(text);
                } else{ text = actionNames.get(o);}

                charActions[o - 1].setText(text);

            } else{
                charActions[o - 1].setBackgroundColor(R.drawable.deloriasis);
                charActions[o - 1].setText("???");
                charActions[o - 1].setClickable(false);
            }
        }
    }


    private void setLongClickListener(Button b){
        b.setOnLongClickListener(v -> {
            if(bubbleUp){
                root.performClick();
            }
            sonicJammer.playSound( "bubble", getContext());

            actionTextBubble bubble = new actionTextBubble(getContext(), getCharAction((String) b.getText()));

            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 500;
            params.topMargin = 500;
            bubble.setElevation(500);
            bubble.setLayoutParams(params);

            root.addView(bubble);
//            atkInterface.inflateView(bubble, clickX, clickY);
            bubbleUp = true;

            screenBlocker.setVisibility(VISIBLE);
            screenBlocker.setClickable(true);
            root.bringChildToFront(screenBlocker);

            viewAnimationHelper.showViewWithAnimation(bubble, 500);
            root.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    bubbleUp = false;

                    screenBlocker.setVisibility(GONE);
                    screenBlocker.setClickable(false);

                    viewAnimationHelper.hideViewWithAnimation(bubble, 500).start();
//                    atkInterface.removeView(bubble);
                    root.removeView(bubble);
                    root.setOnClickListener(null);


                }
            });

            return false;
        });
    }

    private charAction getCharAction(String actionName){
        for (int i = 0; i < charActionArray.size(); i++) {
            if(actionName.contains(charActionArray.get(i).getName())){
                return charActionArray.get(i);
            }
        }
        return null;
    }

    private void exitSequence() {
        if(!bubbleUp) {
            resultPicked = true;

            atkInterface.attackPicked(result);
        }

    }

}
