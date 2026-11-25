package declatoration.simulatorial.fight;

import static declatoration.simulatorial.fight.fightBackground.currentTurnCharId;
import static declatoration.simulatorial.fight.fightForeground.sonicJammer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Objects;

import declatoration.simulatorial.R;
import declatoration.simulatorial.charAction;
import declatoration.simulatorial.enums.damageType;
import declatoration.simulatorial.enums.target;
import declatoration.simulatorial.viewAnimationHelper;

public class attackPickerFinaleAction extends ConstraintLayout{

    boolean resultPicked = false;
    boolean bubbleUp = false;
    boolean rowCollapsed;
    String rowActionUnavailableCause;
    int clickX, clickY;
    int result;

    String special, special2, special3;
    charAction specialCharAction, specialCharAction2, specialCharAction3, affSpecialAction;
    attackPickedBridger atkInterface;

//    public static final Object lockAtk = new Object();

    View screenBlocker;
    ConstraintLayout root;
    LinearLayout attackLayout;

    String[] attacksUnavailableCauses;


    ViewGroup v;

    battleTokenArray combatants;

    public attackPickerFinaleAction(Context context, attackPickedBridger atkInterface, int charId,
                                    String rowActionUnavailableCause, boolean rowCollapsed,
                                    battleTokenArray bta, String[] attackUnavailableCause) {
        super(context);
        this.atkInterface = atkInterface;

        this.rowActionUnavailableCause = rowActionUnavailableCause;
        this.rowCollapsed = rowCollapsed;
        combatants = bta;

        this.attacksUnavailableCauses = attackUnavailableCause;

        this.specialCharAction = combatants.getBattleToken(charId).getChars().getCharAction(-7);
        this.specialCharAction2 = combatants.getBattleToken(charId).getChars().getCharAction(-8);
        this.specialCharAction3 = combatants.getBattleToken(charId).getChars().getCharAction(-9);
        affSpecialAction = combatants.getBattleToken(charId).getChars().getCharAction(-11);

        if(specialCharAction != null) {
            this.special = specialCharAction.getName().substring(0, 1).toUpperCase() +
                    specialCharAction.getName().substring(1).toLowerCase();
            this.special = this.special.replace('_', ' ');
        } else { this.special = "";}
        if(specialCharAction2 != null) {
            this.special2 = specialCharAction2.getName().substring(0, 1).toUpperCase() +
                    specialCharAction2.getName().substring(1).toLowerCase();
        } else { this.special2 = "";}
        if(specialCharAction3 != null) {
            this.special3 = specialCharAction3.getName().substring(0, 1).toUpperCase() +
                    specialCharAction3.getName().substring(1).toLowerCase();
        } else { this.special3 = "";}

        initialize();
    }

    private void initialize() {

        v = (ViewGroup) inflate(getContext(), R.layout.fragment_final_action_picker, this);

        root = v.findViewById(R.id.attack_layout);
        attackLayout = v.findViewById(R.id.attack_button_layout);
        Button hideLayout = v.findViewById(R.id.hide_attack_layout_button);

        Button specialAction1 = v.findViewById(R.id.special_button1);
        Button specialAction2 = v.findViewById(R.id.special_button2);
        Button specialAction3 = v.findViewById(R.id.special_button3);
        Button affButton = v.findViewById(R.id.aff_special_button);

        Button waitButton = v.findViewById(R.id.wait_action);
        Button guardAction = v.findViewById(R.id.guard_action);
        Button focusAction = v.findViewById(R.id.focus_action);
        Button rowAction = v.findViewById(R.id.row_action);
        Button assistAction = v.findViewById(R.id.assist_action);

        screenBlocker = v.findViewById(R.id.screen_blocker);


        // 0
        specialAction1.setText(special);
        setLongClickListener(specialAction1);
        // 1
        rowAction.setText(R.string.row);
        setLongClickListener(rowAction);
        // 2
        guardAction.setText(R.string.guard);
        setLongClickListener(guardAction);
        // 3
        focusAction.setText(R.string.focus);
        setLongClickListener(focusAction);
        // 4
        assistAction.setText(R.string.assist);
        setLongClickListener(assistAction);
        // 5
        specialAction2.setText(special2);
        setLongClickListener(specialAction2);
        // 6
        specialAction3.setText(special3);
        setLongClickListener(specialAction3);
        // 7
        affButton.setText(affSpecialAction.getName());
        setLongClickListener(affButton);
        // 8
        waitButton.setText(R.string.wait_action);



        screenBlocker.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                root.callOnClick();
            }
        });
        hideLayout.setOnClickListener(view -> {
            sonicJammer.playSound("click", getContext());
            if (attackLayout.getVisibility() == VISIBLE) {
                viewAnimationHelper.hideViewWithAnimation(attackLayout, 150).start();
                hideLayout.setRotation(180);
            } else {
                viewAnimationHelper.showViewWithAnimation(attackLayout, 150);
                hideLayout.setRotation(0);
            }

        });

        if(rowActionUnavailableCause == null){
        rowAction.setOnClickListener(v -> {
            if(!bubbleUp) {
                result = -5;
                sonicJammer.playSound("row_shift", getContext());
                exitSequence();
            }
        }); } else{
            rowAction.setOnClickListener(v -> {
                if(!bubbleUp) {
                    sonicJammer.playSound( "error", getContext());

                    if(rowCollapsed){
                        atkInterface.rowCollapsedToast();
                    } else if(Objects.equals(rowActionUnavailableCause, "full")){
                        atkInterface.rowFilledToast();
                    } else{
                        // row lockdown
                        atkInterface.rowLockdownToast();
                    }
                }
            });

            rowAction.setBackgroundResource(R.drawable.redrivium);
        }

        waitButton.setOnClickListener(v -> {
            if(!bubbleUp) {
                result = -10;
                sonicJammer.playSound("miss", getContext());
                exitSequence();
            }
        });

        guardAction.setOnClickListener(v -> {
            if(!bubbleUp) {
                result = -1;
                sonicJammer.playSound("guard_action", getContext());
                exitSequence();
            }
        });

        focusAction.setOnClickListener(v -> {
            if(!bubbleUp) {
                result = -2;
                sonicJammer.playSound("focus_action", getContext());
                exitSequence();
            }
        });

        assistAction.setOnClickListener(v -> {
            if(!bubbleUp) {
                result = -6;
                sonicJammer.playSound("assist_action", getContext());
                exitSequence();
            }
        });



        if(attacksUnavailableCauses[0] != null){
            specialAction1.setOnClickListener(v -> {
                sonicJammer.playSound( "error", getContext());
                atkInterface.attackUnavailable(attacksUnavailableCauses[0]);
            });
            specialAction1.setBackgroundResource(R.drawable.redrivium);

        } else{
            specialAction1.setOnClickListener(v -> {
                if(!bubbleUp) {
                    sonicJammer.playSound("fight_select", getContext());

                    result = -7;
                    exitSequence();
                }
            });
        }

        if(!Objects.equals(this.special2, "") && attacksUnavailableCauses[1] == null) {
            specialAction2.setOnClickListener(v -> {
                if (!bubbleUp) {
                    result = -8;
                    sonicJammer.playSound("fight_select", getContext());
                    exitSequence();
                }
            });
        } else{

            if(attacksUnavailableCauses[1] != null &&
                    attacksUnavailableCauses[1].contains("cooldown")) {
                // cooldown
                // else sp deficit
                String name = affSpecialAction.getName() + " " + attacksUnavailableCauses[1].charAt(attacksUnavailableCauses[1].length() - 1);
                affButton.setText(name);
            }

            specialAction2.setBackgroundResource(R.drawable.redrivium);
            specialAction2.setClickable(false);
            specialAction2.setOnClickListener(v -> {
                sonicJammer.playSound( "error", getContext());
                atkInterface.attackUnavailable(attacksUnavailableCauses[1]);
            });
        }

        if(!Objects.equals(this.special3, "")  && attacksUnavailableCauses[2] == null) {
            specialAction3.setOnClickListener(v -> {
                if (!bubbleUp) {
                    result = -9;
                    sonicJammer.playSound("fight_select", getContext());
                    exitSequence();
                }
            });
        } else{
            if(attacksUnavailableCauses[2] != null && attacksUnavailableCauses[2].contains("cooldown")) {
                // cooldown
                // else sp deficit
                String name = affSpecialAction.getName() + " " + attacksUnavailableCauses[2].charAt(attacksUnavailableCauses[2].length() - 1);
                affButton.setText(name);
            }
            specialAction3.setBackgroundResource(R.drawable.redrivium);
            specialAction3.setClickable(false);
            specialAction3.setOnClickListener(v -> {
                sonicJammer.playSound( "error", getContext());
                atkInterface.attackUnavailable(attacksUnavailableCauses[2]);
            });
        }

        if(attacksUnavailableCauses[3] == null) {
            affButton.setOnClickListener(v -> {
                if (!bubbleUp) {
                    result = -11;
                    sonicJammer.playSound("fight_select", getContext());
                    exitSequence();
                }
            });
        } else{
            if(attacksUnavailableCauses[3].contains("cooldown")) {
                // cooldown
                // else sp deficit
                String name = affSpecialAction.getName() + " " + attacksUnavailableCauses[3].charAt(attacksUnavailableCauses[3].length() - 1);
                affButton.setText(name);
            }

            affButton.setBackgroundResource(R.drawable.redrivium);
            affButton.setClickable(false);
            affButton.setOnClickListener(v -> {
                sonicJammer.playSound( "error", getContext());
                atkInterface.attackUnavailable(attacksUnavailableCauses[3]);
            });
        }
    }

    private charAction getCharAction(String actionName){
        charAction action = new charAction();

        switch (actionName.toLowerCase()){
            case "guard":
                action.setName("Guard");
                action.setSPUsage(0);
                action.setDamageType(damageType.STATUS);
                action.setActionDesc(getContext().getString(R.string.guard_desc));
                action.setAffectedTarget(target.SELF);

                break;

            case "attack!":
            case "attack":
                action.setName("Attack!");
                action.setSPUsage(0);
                action.setActionDesc(getContext().getString(R.string.attack_desc));
                action.setAffectedTarget(target.SINGLE_ENEMY);

                action.setDamageType(combatants.getBattleToken(currentTurnCharId).getChars().getCharAction(0).getDamageType());

                break;
            case "row":
                action.setName("Row");
                action.setSPUsage(0);
                action.setDamageType(damageType.STATUS);
                action.setActionDesc(getContext().getString(R.string.row_desc));
                action.setAffectedTarget(target.SELF);

            case "focus":
                action.setName("Focus");
                action.setSPUsage(0);
                action.setDamageType(damageType.STATUS);
                action.setActionDesc(getContext().getString(R.string.focus_desc));
                action.setAffectedTarget(target.SELF);

            case "assist":
                action.setName("Assist");
                action.setSPUsage(0);
                action.setDamageType(damageType.STATUS);
                action.setActionDesc(getContext().getString(R.string.assist_desc));
                action.setAffectedTarget(target.SINGLE_ALLY_NO_USER);

                break;
            default:
                if(actionName.equals(special)){
                    action = specialCharAction;
                } else if (actionName.equals(special2)) {
                    action = specialCharAction2;
                } else if (actionName.equals(special3)) {
                    action = specialCharAction3;
                } else{
                    action = affSpecialAction;
                }
        }
        return action;
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
            root.bringChildToFront(bubble);
//            atkInterface.inflateView(bubble, clickX, clickY);
            bubbleUp = true;
            screenBlocker.setClickable(true);
            root.bringChildToFront(screenBlocker);

            viewAnimationHelper.showViewWithAnimation(bubble, 500);
            root.setOnClickListener(v1 -> {
                bubbleUp = false;

                screenBlocker.setVisibility(GONE);
                screenBlocker.setClickable(false);

                viewAnimationHelper.hideViewWithAnimation(bubble, 500).start();
//                    atkInterface.removeView(bubble);
                root.removeView(bubble);
                root.setOnClickListener(null);
            });

            return false;
        });
    }

    private void exitSequence() {
        if(!bubbleUp) {
            resultPicked = true;

            atkInterface.attackPicked(result);
        }

    }

}
