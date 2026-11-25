package declatoration.simulatorial.enums;

import java.util.Objects;

public enum target {
    SELF,
    SINGLE_ALLY,
    SINGLE_ALLY_NO_USER,
    ALL_ALLIES,
    SINGLE_ENEMY,
    ENEMY_ROW,
    ENEMY_COLUMN,
    ALL_ENEMIES,
    ALL,

    // for attacks that only work on a grappled / swallowed opponent
    GRAPPLED,
    SWALLOWED,
    UNGRAPPLED_ALLY,

    KOED_ALLY,
    KOED_ENEMY,
    KOED_ALL,

    //for action bonus effects targets
    RANDOM_TARGET,
    RANDOM_FROM_ALL,
    RANDOM_ENEMY_COLUMN,
    TARGET,
    FIELD;

    public static target getTargetEnum(String str){
            String temp = "NULL";
            if(str!=null) {
                temp = str.toUpperCase();
            }
            if(Objects.equals(str, "SINGLE_TARGET")){
                temp = "SINGLE_ENEMY";
            }
            if(Objects.equals(str, "RANDOM_ENEMY")){
                temp = "RANDOM_TARGET";
            }

            return target.valueOf(temp);

    }

}
