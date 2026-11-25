package declatoration.simulatorial;

import static declatoration.simulatorial.fight.fightForeground.sonicJammer;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import declatoration.simulatorial.fight.fightForeground;

public class viewAnimationHelper {


    public static void showViewWithAnimation(View view, int duration) {
        if (view.getVisibility() == View.VISIBLE) {
            return;
        }

        view.setVisibility(View.VISIBLE);
        view.setAlpha(0f);

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        animator.setDuration(duration);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        animator.start();
    }

    public static void hideViewWithAnimation(View view, int duration, Spinner spin) {
        if (view.getVisibility() != View.VISIBLE) {
            return;
        }

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        animator.setDuration(duration);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
                view.setClickable(false);
                spin.setSelection(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        animator.start();
    }
    public static ObjectAnimator hideViewWithAnimation(View view, int duration) {
        if (view.getVisibility() != View.VISIBLE) {
            return null;
        }

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        animator.setDuration(duration);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
                view.setClickable(false);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        return animator;
    }


    public static AnimatorSet showFightOutcome(fightForeground frif, LinearLayout ll, boolean isPlayer) {

        int startX, endX;
//        if(isPlayer){
            startX = - ll.getRight();
            endX = 0;
//        } else{
//            startX = ll.getLeft() + ll.getWidth();
//            endX = ll.getLeft();
//        }


        ll.setAlpha(0f);

        frif.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ll.setVisibility(View.VISIBLE);
            }
        });

        ObjectAnimator animator = ObjectAnimator.ofFloat(ll, "alpha", 0f, 1f);
        animator.setDuration(300);
        ObjectAnimator animatorM = ObjectAnimator.ofFloat(ll, "translationX",startX, endX);
        animator.setDuration(300);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator, animatorM);
        animatorSet.setInterpolator(new DecelerateInterpolator());

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                frif.runOnUiThread(() -> ll.setVisibility(View.VISIBLE));

                synchronized (this){
                    try {
                        this.wait(550);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                int startX2, endX2;
//                if(isPlayer){
                    startX2 = - ll.getRight();
                    endX2 = 0;
//                } else{
//                    startX2 = ll.getLeft();
//                    endX2 = ll.getLeft();
//                }

                ll.setAlpha(1f);

                ObjectAnimator animator2 = ObjectAnimator.ofFloat(ll, "alpha", 1f, 0f);
                animator2.setDuration(1200);
                ObjectAnimator animatorM2 = ObjectAnimator.ofFloat(ll, "translationX",endX2, startX2);
                animator2.setDuration(1200);


                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(animator2, animatorM2);
                animatorSet2.setInterpolator(new AccelerateInterpolator());

                frif.runOnUiThread(() -> animatorSet2.start());

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                int i = 0;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        return animatorSet;
    }

    public static AnimatorSet showSideBarName(View view, boolean isPlayer){

        int startX, endX;
        if(isPlayer){
            startX = - view.getRight();
            endX = 0;
        } else{
            startX = view.getLeft() + view.getWidth();
            endX = view.getLeft();
        }


        view.setAlpha(0f);


        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        animator.setDuration(350);
        ObjectAnimator animatorM = ObjectAnimator.ofFloat(view, "translationX",startX, endX);
        animator.setDuration(350);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator, animatorM);
        animatorSet.setInterpolator(new DecelerateInterpolator());

        return animatorSet;
    }
    public static AnimatorSet hideSideBarName(View view, boolean isPlayer){

        int startX2, endX2;
        if(isPlayer){
            startX2 = - view.getRight();
            endX2 = 0;
        } else{
            startX2 = view.getLeft() + view.getWidth();
            endX2 = view.getLeft();
        }

        view.setAlpha(1f);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        animator2.setDuration(1200);
        ObjectAnimator animatorM2 = ObjectAnimator.ofFloat(view, "translationX",endX2, startX2);
        animator2.setDuration(1200);


        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animator2, animatorM2);
        animatorSet2.setInterpolator(new DecelerateInterpolator());

        return animatorSet2;
    }



    public static void setSpinnerAnimation(Spinner spinner){
        Animation animation = new ScaleAnimation(1, 1, 0, 1);
        animation.setDuration(200); // Set the duration of the animation (in milliseconds)

// Set an AnimationListener to handle animation events
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Animation start event
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Animation end event
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Animation repeat event
            }
        });

// Apply the animation to the spinner's dropdown
        spinner.setAnimation(animation);
    }

    public static void fightScreenCollapseRows(fightForeground ff, View row1, View row2, boolean isPlayerRows) {

        row1.setAlpha(1f);

        ObjectAnimator row1_disintegrator = ObjectAnimator.ofFloat(row1, "alpha", 1f, 0f);
        row1_disintegrator.setDuration(150);
        row1_disintegrator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                int startX = row2.getLeft();
                int finalX;
                if(isPlayerRows){
                    finalX = row2.getLeft() + 180;
                } else {
                    finalX = startX;
                }

                ObjectAnimator row2_mover = ObjectAnimator.ofFloat(row2, "translationX",
                        startX, finalX);

                row2_mover.setDuration(550);
                row2_mover.setInterpolator(new AccelerateDecelerateInterpolator());
                row2_mover.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        sonicJammer.playSound("row_collapse", ff.getBaseContext());
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        synchronized (this){
                            try {
                                this.wait(150);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {}

                    @Override
                    public void onAnimationRepeat(Animator animation) {}
                });


                if(isPlayerRows) {
                    ff.runOnUiThread(row2_mover::start);
//
                } else{
                    ff.runOnUiThread(() -> row2.setTranslationX(- 220));
                    sonicJammer.playSound("row_collapse", ff.getBaseContext());
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });


        String guys;
        if(isPlayerRows){
            guys = "Megadeath";
        } else { guys = "Enemy" ;}

        String msg = guys + "'s front lines have been breached!";

        ff.runOnUiThread(() -> {
            row1_disintegrator.start();
            Toast.makeText(ff.getBaseContext(), msg, Toast.LENGTH_SHORT).show();
        });


    }
}
