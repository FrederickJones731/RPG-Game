package declatoration.simulatorial.fight;

import android.graphics.Path;
import android.view.View;
import android.view.ViewGroup;

import androidx.transition.ChangeBounds;
import androidx.transition.Scene;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

public class movementManager{


    View playerCenterView;

    Scene scene;
    TransitionManager transitionManager;

    public movementManager(View playerCenterView) {

        this.playerCenterView = playerCenterView;






    }

    private void apply(Transition transition){

        scene.setEnterAction((Runnable) transition);
        transitionManager.setTransition(scene, transition);
        transitionManager.transitionTo(scene);

    }

    public void centralize(ViewGroup sceneRootView, characterLayout characterLayout){

        androidx.transition.PathMotion pathMotion = new androidx.transition.PathMotion() {
            @Override
            public Path getPath(float startX, float startY, float endX, float endY) {
                Path path = new Path();

                path.moveTo(startX, startY);
                path.lineTo(playerCenterView.getX(), playerCenterView.getY());

                return path;
            }
        };

//        Transition transition = new Transition() {
//            @Override
//            public void captureStartValues(@NonNull TransitionValues transitionValues) {
//
//            }
//
//            @Override
//            public void captureEndValues(@NonNull TransitionValues transitionValues) {
//
//            }
//        };

        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setPathMotion(pathMotion);
        changeBounds.addTarget(characterLayout);
        changeBounds.setDuration(150);

        scene = Scene.getCurrentScene(sceneRootView);

        apply(changeBounds);
    }









}
