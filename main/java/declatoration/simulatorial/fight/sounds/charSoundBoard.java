package declatoration.simulatorial.fight.sounds;

import android.content.Context;
import android.media.SoundPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import declatoration.simulatorial.R;
import declatoration.simulatorial.enums.moveType;


public class charSoundBoard {


    HashMap<moveType, ArrayList<Integer>> board = new HashMap<>();
    boolean initialized = false;

    SoundPool soundPool;

    public charSoundBoard(SoundPool sp, Context context, sfxCharBase charName){

        switch(charName){
            case IKE:
                init_ike(sp, context);
                break;
            case MII:
                init_mii(sp, context);
                break;
            case ZSS:
                init_zss(sp, context);
                break;
            case PYRA:
                init_pyra(sp, context);
                break;
            case ROBIN:
                init_robin(sp, context);
                break;
            case TERRY:
                init_terry(sp, context);
                break;
            case ZELDA:
                init_zelda(sp, context);
                break;
            case CORRIN:
                init_corrin(sp, context);
                break;
            case FALCON:
                init_falcon(sp, context);
                break;
            case LUCINA:
                init_lucina(sp, context);
                break;
            case MYTHRA:
                init_mythra(sp, context);
                break;
            case ROBIN_F:
                init_robin_f(sp, context);
                break;
            case SEPHIROTH:
                init_sephiroth(sp, context);
                break;
            case BYLETH:
            default:
                init_byleth(sp, context);
                break;
            case BYLETH_F:
                init_byleth_f(sp, context);
                break;
            case CLOUD:
                init_cloud(sp, context);
                break;
            case GANON:
                init_ganon(sp, context);
                break;
            case SNAKE:
                init_snake(sp, context);
                break;
            case SHEIK:
                init_sheik(sp, context);
                break;
            case DK:
                init_dk(sp, context);
                break;

        }

        soundPool = sp;
        initialized = true;
    }

    public void init_lucina(SoundPool soundPool, Context context){
        board.put(moveType.NORMAL_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_lucina_normal_atk1, 1),
                soundPool.load(context, R.raw.vc_lucina_normal_atk2, 1), soundPool.load(context, R.raw.vc_lucina_normal_atk3, 1))));
        board.put(moveType.FAST_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_lucina_fast_atk1, 1),
                soundPool.load(context, R.raw.vc_lucina_fast_atk2, 1))));
        board.put(moveType.POWER_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_lucina_power_atk1, 1),
                soundPool.load(context, R.raw.vc_lucina_power_atk2, 1))));
        board.put(moveType.FINALE_ATTACK, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_lucina_finale, 1))));

        board.put(moveType.BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_lucina_buff, 1))));
        board.put(moveType.DEBUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_lucina_debuff, 1))));
        board.put(moveType.SELF_BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_lucina_self_buff, 1))));
        board.put(moveType.HEAL, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_lucina_heal, 1))));

        board.put(moveType.LIGHT_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_lucina_hit_light1, 1),
                soundPool.load(context, R.raw.vc_lucina_hit_light2, 1))));
        board.put(moveType.MED_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_lucina_hit_med1, 1),
                soundPool.load(context, R.raw.vc_lucina_hit_med2, 1))));
        board.put(moveType.HEAVY_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_lucina_hit_heavy1, 1),
                soundPool.load(context, R.raw.vc_lucina_hit_heavy2, 1))));

        board.put(moveType.KO, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_lucina_ko, 1))));
        board.put(moveType.TAUNT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_lucina_taunt, 1))));

        board.put(moveType.ROW_SHIFT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_lucina_row_shift, 1))));
        board.put(moveType.FOCGAR, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_lucina_focgar, 1))));
    }
    public void init_zelda(SoundPool soundPool, Context context){
        board.put(moveType.NORMAL_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_zelda_normal_atk1, 1),
                soundPool.load(context, R.raw.vc_zelda_normal_atk2, 1), soundPool.load(context, R.raw.vc_zelda_normal_atk3, 1))));
        board.put(moveType.FAST_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_zelda_fast_atk1, 1),
                soundPool.load(context, R.raw.vc_zelda_fast_atk2, 1))));
        board.put(moveType.POWER_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_zelda_power_atk1, 1),
                soundPool.load(context, R.raw.vc_zelda_power_atk2, 1))));
        board.put(moveType.FINALE_ATTACK, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_zelda_finale, 1))));

        board.put(moveType.BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_zelda_buff, 1))));
        board.put(moveType.DEBUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_zelda_debuff, 1))));
        board.put(moveType.SELF_BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_zelda_self_buff, 1))));
        board.put(moveType.HEAL, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_zelda_heal, 1))));

        board.put(moveType.LIGHT_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_zelda_hit_light1, 1),
                soundPool.load(context, R.raw.vc_zelda_hit_light2, 1))));
        board.put(moveType.MED_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_zelda_hit_med1, 1),
                soundPool.load(context, R.raw.vc_zelda_hit_med2, 1))));
        board.put(moveType.HEAVY_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_zelda_hit_heavy1, 1),
                soundPool.load(context, R.raw.vc_zelda_hit_heavy2, 1))));

        board.put(moveType.KO, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_zelda_ko, 1))));
        board.put(moveType.TAUNT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_zelda_taunt, 1))));

        board.put(moveType.ROW_SHIFT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_zelda_row_shift, 1))));
        board.put(moveType.FOCGAR, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_zelda_focgar, 1))));
    }
    public void init_terry(SoundPool soundPool, Context context){
        board.put(moveType.NORMAL_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_terry_normal_atk1, 1),
                soundPool.load(context, R.raw.vc_terry_normal_atk2, 1), soundPool.load(context, R.raw.vc_terry_normal_atk3, 1))));
        board.put(moveType.FAST_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_terry_fast_atk1, 1),
                soundPool.load(context, R.raw.vc_terry_fast_atk2, 1))));
        board.put(moveType.POWER_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_terry_power_atk1, 1),
                soundPool.load(context, R.raw.vc_terry_power_atk2, 1))));
        board.put(moveType.FINALE_ATTACK, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_terry_finale, 1))));

        board.put(moveType.BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_terry_buff, 1))));
        board.put(moveType.DEBUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_terry_debuff, 1))));
        board.put(moveType.SELF_BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_terry_self_buff, 1))));
        board.put(moveType.HEAL, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_terry_heal, 1))));

        board.put(moveType.LIGHT_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_terry_hit_light1, 1),
                soundPool.load(context, R.raw.vc_terry_hit_light2, 1))));
        board.put(moveType.MED_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_terry_hit_med1, 1),
                soundPool.load(context, R.raw.vc_terry_hit_med2, 1))));
        board.put(moveType.HEAVY_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_terry_hit_heavy1, 1),
                soundPool.load(context, R.raw.vc_terry_hit_heavy2, 1))));

        board.put(moveType.KO, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_terry_ko, 1))));
        board.put(moveType.TAUNT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_terry_taunt, 1))));

        board.put(moveType.ROW_SHIFT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_terry_row_shift, 1))));
        board.put(moveType.FOCGAR, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_terry_focgar, 1))));
    }
    public void init_falcon(SoundPool soundPool, Context context){
        board.put(moveType.NORMAL_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_falcon_normal_atk1, 1),
                soundPool.load(context, R.raw.vc_falcon_normal_atk2, 1), soundPool.load(context, R.raw.vc_falcon_normal_atk3, 1))));
        board.put(moveType.FAST_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_falcon_fast_atk1, 1),
                soundPool.load(context, R.raw.vc_falcon_fast_atk2, 1))));
        board.put(moveType.POWER_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_falcon_power_atk1, 1),
                soundPool.load(context, R.raw.vc_falcon_power_atk2, 1))));
        board.put(moveType.FINALE_ATTACK, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_falcon_finale, 1))));

        board.put(moveType.BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_falcon_buff, 1))));
        board.put(moveType.DEBUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_falcon_debuff, 1))));
        board.put(moveType.SELF_BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_falcon_self_buff, 1))));
        board.put(moveType.HEAL, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_falcon_heal, 1))));

        board.put(moveType.LIGHT_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_falcon_hit_light1, 1),
                soundPool.load(context, R.raw.vc_falcon_hit_light2, 1))));
        board.put(moveType.MED_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_falcon_hit_med1, 1),
                soundPool.load(context, R.raw.vc_falcon_hit_med2, 1))));
        board.put(moveType.HEAVY_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_falcon_hit_heavy1, 1),
                soundPool.load(context, R.raw.vc_falcon_hit_heavy2, 1))));

        board.put(moveType.KO, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_falcon_ko, 1))));
        board.put(moveType.TAUNT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_falcon_taunt, 1))));

        board.put(moveType.ROW_SHIFT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_falcon_row_shift, 1))));
        board.put(moveType.FOCGAR, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_falcon_focgar, 1))));
        board.put(moveType.DODGE, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_falcon_dodge1, 1),
                soundPool.load(context, R.raw.vc_falcon_dodge2, 1))));
    }
    public void init_zss(SoundPool soundPool, Context context){
        board.put(moveType.NORMAL_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_zss_normal_atk1, 1),
                soundPool.load(context, R.raw.vc_zss_normal_atk2, 1), soundPool.load(context, R.raw.vc_zss_normal_atk3, 1))));
        board.put(moveType.FAST_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_zss_fast_atk1, 1),
                soundPool.load(context, R.raw.vc_zss_fast_atk2, 1))));
        board.put(moveType.POWER_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_zss_power_atk1, 1),
                soundPool.load(context, R.raw.vc_zss_power_atk2, 1))));
        board.put(moveType.FINALE_ATTACK, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_zss_finale, 1))));

        board.put(moveType.BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_zss_buff, 1))));
        board.put(moveType.DEBUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_zss_debuff, 1))));
        board.put(moveType.SELF_BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_zss_self_buff, 1))));
        board.put(moveType.HEAL, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_zss_heal, 1))));

        board.put(moveType.LIGHT_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_zss_hit_light1, 1),
                soundPool.load(context, R.raw.vc_zss_hit_light2, 1))));
        board.put(moveType.MED_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_zss_hit_med1, 1),
                soundPool.load(context, R.raw.vc_zss_hit_med2, 1))));
        board.put(moveType.HEAVY_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_zss_hit_heavy1, 1),
                soundPool.load(context, R.raw.vc_zss_hit_heavy2, 1))));

        board.put(moveType.KO, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_zss_ko, 1))));
        board.put(moveType.TAUNT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_zss_taunt, 1))));

        board.put(moveType.ROW_SHIFT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_zss_row_shift, 1))));
        board.put(moveType.FOCGAR, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_zss_focgar, 1))));
        board.put(moveType.DODGE, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_zss_dodge1, 1),
                soundPool.load(context, R.raw.vc_zss_dodge2, 1))));
    }
    public void init_pyra(SoundPool soundPool, Context context){
        board.put(moveType.NORMAL_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_pyra_normal_atk1, 1),
                soundPool.load(context, R.raw.vc_pyra_normal_atk2, 1), soundPool.load(context, R.raw.vc_pyra_normal_atk3, 1))));
        board.put(moveType.FAST_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_pyra_fast_atk1, 1),
                soundPool.load(context, R.raw.vc_pyra_fast_atk2, 1))));
        board.put(moveType.POWER_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_pyra_power_atk1, 1),
                soundPool.load(context, R.raw.vc_pyra_power_atk2, 1))));
        board.put(moveType.FINALE_ATTACK, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_pyra_finale, 1))));

        board.put(moveType.BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_pyra_buff, 1))));
        board.put(moveType.DEBUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_pyra_debuff, 1))));
        board.put(moveType.SELF_BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_pyra_self_buff, 1))));
        board.put(moveType.HEAL, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_pyra_heal, 1))));

        board.put(moveType.LIGHT_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_pyra_hit_light1, 1),
                soundPool.load(context, R.raw.vc_pyra_hit_light2, 1))));
        board.put(moveType.MED_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_pyra_hit_med1, 1),
                soundPool.load(context, R.raw.vc_pyra_hit_med2, 1))));
        board.put(moveType.HEAVY_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_pyra_hit_heavy1, 1),
                soundPool.load(context, R.raw.vc_pyra_hit_heavy2, 1))));

        board.put(moveType.KO, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_pyra_ko, 1))));
        board.put(moveType.TAUNT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_pyra_taunt, 1))));

        board.put(moveType.ROW_SHIFT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_pyra_row_shift, 1))));
        board.put(moveType.FOCGAR, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_pyra_focgar, 1))));
        board.put(moveType.DODGE, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_pyra_dodge1, 1),
                soundPool.load(context, R.raw.vc_pyra_dodge2, 1))));
    }
    public void init_mythra(SoundPool soundPool, Context context){
        board.put(moveType.NORMAL_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_mythra_normal_atk1, 1),
                soundPool.load(context, R.raw.vc_mythra_normal_atk2, 1), soundPool.load(context, R.raw.vc_mythra_normal_atk3, 1))));
        board.put(moveType.FAST_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_mythra_fast_atk1, 1),
                soundPool.load(context, R.raw.vc_mythra_fast_atk2, 1))));
        board.put(moveType.POWER_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_mythra_power_atk1, 1),
                soundPool.load(context, R.raw.vc_mythra_power_atk2, 1))));
        board.put(moveType.FINALE_ATTACK, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_mythra_finale, 1))));

        board.put(moveType.BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_mythra_buff, 1))));
        board.put(moveType.DEBUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_mythra_debuff, 1))));
        board.put(moveType.SELF_BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_mythra_self_buff, 1))));
        board.put(moveType.HEAL, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_mythra_heal, 1))));

        board.put(moveType.LIGHT_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_mythra_hit_light1, 1),
                soundPool.load(context, R.raw.vc_mythra_hit_light2, 1))));
        board.put(moveType.MED_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_mythra_hit_med1, 1),
                soundPool.load(context, R.raw.vc_mythra_hit_med2, 1))));
        board.put(moveType.HEAVY_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_mythra_hit_heavy1, 1),
                soundPool.load(context, R.raw.vc_mythra_hit_heavy2, 1))));

        board.put(moveType.KO, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_mythra_ko, 1))));
        board.put(moveType.TAUNT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_mythra_taunt, 1))));

        board.put(moveType.ROW_SHIFT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_mythra_row_shift, 1))));
        board.put(moveType.FOCGAR, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_mythra_focgar, 1))));
        board.put(moveType.DODGE, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_mythra_dodge1, 1),
                soundPool.load(context, R.raw.vc_mythra_dodge2, 1))));
    }
    public void init_robin(SoundPool soundPool, Context context){
        board.put(moveType.NORMAL_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_robin_normal_atk1, 1),
                soundPool.load(context, R.raw.vc_robin_normal_atk2, 1), soundPool.load(context, R.raw.vc_robin_normal_atk3, 1))));
        board.put(moveType.FAST_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_robin_fast_atk1, 1),
                soundPool.load(context, R.raw.vc_robin_fast_atk2, 1))));
        board.put(moveType.POWER_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_robin_power_atk1, 1),
                soundPool.load(context, R.raw.vc_robin_power_atk2, 1))));
        board.put(moveType.FINALE_ATTACK, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_robin_finale, 1))));

        board.put(moveType.BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_robin_buff, 1))));
        board.put(moveType.DEBUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_robin_debuff, 1))));
        board.put(moveType.SELF_BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_robin_self_buff, 1))));
        board.put(moveType.HEAL, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_robin_heal, 1))));

        board.put(moveType.LIGHT_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_robin_hit_light1, 1),
                soundPool.load(context, R.raw.vc_robin_hit_light2, 1))));
        board.put(moveType.MED_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_robin_hit_med1, 1),
                soundPool.load(context, R.raw.vc_robin_hit_med2, 1))));
        board.put(moveType.HEAVY_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_robin_hit_heavy1, 1),
                soundPool.load(context, R.raw.vc_robin_hit_heavy2, 1))));

        board.put(moveType.KO, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_robin_ko, 1))));
        board.put(moveType.TAUNT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_robin_taunt, 1))));

        board.put(moveType.ROW_SHIFT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_robin_row_shift, 1))));
        board.put(moveType.FOCGAR, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_robin_focgar, 1))));
        board.put(moveType.DODGE, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_robin_dodge1, 1),
                soundPool.load(context, R.raw.vc_robin_dodge2, 1))));
    }
    public void init_robin_f(SoundPool soundPool, Context context){
        board.put(moveType.NORMAL_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_robin_f_normal_atk1, 1),
                soundPool.load(context, R.raw.vc_robin_f_normal_atk2, 1), soundPool.load(context, R.raw.vc_robin_f_normal_atk3, 1))));
        board.put(moveType.FAST_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_robin_f_fast_atk1, 1),
                soundPool.load(context, R.raw.vc_robin_f_fast_atk2, 1))));
        board.put(moveType.POWER_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_robin_f_power_atk1, 1),
                soundPool.load(context, R.raw.vc_robin_f_power_atk2, 1))));
        board.put(moveType.FINALE_ATTACK, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_robin_f_finale, 1))));

        board.put(moveType.BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_robin_f_buff, 1))));
        board.put(moveType.DEBUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_robin_f_debuff, 1))));
        board.put(moveType.SELF_BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_robin_f_self_buff, 1))));
        board.put(moveType.HEAL, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_robin_f_heal, 1))));

        board.put(moveType.LIGHT_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_robin_f_hit_light1, 1),
                soundPool.load(context, R.raw.vc_robin_f_hit_light2, 1))));
        board.put(moveType.MED_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_robin_f_hit_med1, 1),
                soundPool.load(context, R.raw.vc_robin_f_hit_med2, 1))));
        board.put(moveType.HEAVY_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_robin_f_hit_heavy1, 1),
                soundPool.load(context, R.raw.vc_robin_f_hit_heavy2, 1))));

        board.put(moveType.KO, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_robin_f_ko, 1))));
        board.put(moveType.TAUNT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_robin_f_taunt, 1))));

        board.put(moveType.ROW_SHIFT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_robin_f_row_shift, 1))));
        board.put(moveType.FOCGAR, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_robin_f_focgar, 1))));
        board.put(moveType.DODGE, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_robin_f_dodge1, 1),
                soundPool.load(context, R.raw.vc_robin_f_dodge2, 1))));
    }
    public void init_corrin(SoundPool soundPool, Context context){
        board.put(moveType.NORMAL_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_corrin_normal_atk1, 1),
                soundPool.load(context, R.raw.vc_corrin_normal_atk2, 1), soundPool.load(context, R.raw.vc_corrin_normal_atk3, 1))));
        board.put(moveType.FAST_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_corrin_fast_atk1, 1),
                soundPool.load(context, R.raw.vc_corrin_fast_atk2, 1))));
        board.put(moveType.POWER_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_corrin_power_atk1, 1),
                soundPool.load(context, R.raw.vc_corrin_power_atk2, 1))));
        board.put(moveType.FINALE_ATTACK, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_corrin_finale, 1))));

        board.put(moveType.BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_corrin_buff, 1))));
        board.put(moveType.DEBUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_corrin_debuff, 1))));
        board.put(moveType.SELF_BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_corrin_self_buff, 1))));
        board.put(moveType.HEAL, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_corrin_heal, 1))));

        board.put(moveType.LIGHT_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_corrin_hit_light1, 1),
                soundPool.load(context, R.raw.vc_corrin_hit_light2, 1))));
        board.put(moveType.MED_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_corrin_hit_med1, 1),
                soundPool.load(context, R.raw.vc_corrin_hit_med2, 1))));
        board.put(moveType.HEAVY_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_corrin_hit_heavy1, 1),
                soundPool.load(context, R.raw.vc_corrin_hit_heavy2, 1))));

        board.put(moveType.KO, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_corrin_ko, 1))));
        board.put(moveType.TAUNT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_corrin_taunt, 1))));

        board.put(moveType.ROW_SHIFT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_corrin_row_shift, 1))));
        board.put(moveType.FOCGAR, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_corrin_focgar, 1))));
        board.put(moveType.DODGE, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_corrin_dodge1, 1),
                soundPool.load(context, R.raw.vc_corrin_dodge2, 1))));
    }
    public void init_sephiroth(SoundPool soundPool, Context context){
        board.put(moveType.NORMAL_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_sephiroth_normal_atk1, 1),
                soundPool.load(context, R.raw.vc_sephiroth_normal_atk2, 1), soundPool.load(context, R.raw.vc_sephiroth_normal_atk3, 1))));
        board.put(moveType.FAST_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_sephiroth_fast_atk1, 1),
                soundPool.load(context, R.raw.vc_sephiroth_fast_atk2, 1))));
        board.put(moveType.POWER_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_sephiroth_power_atk1, 1),
                soundPool.load(context, R.raw.vc_sephiroth_power_atk2, 1))));
        board.put(moveType.FINALE_ATTACK, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_sephiroth_finale, 1))));

        board.put(moveType.BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_sephiroth_buff, 1))));
        board.put(moveType.DEBUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_sephiroth_debuff, 1))));
        board.put(moveType.SELF_BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_sephiroth_self_buff, 1))));
        board.put(moveType.HEAL, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_sephiroth_heal, 1))));

        board.put(moveType.LIGHT_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_sephiroth_hit_light1, 1),
                soundPool.load(context, R.raw.vc_sephiroth_hit_light2, 1))));
        board.put(moveType.MED_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_sephiroth_hit_med1, 1),
                soundPool.load(context, R.raw.vc_sephiroth_hit_med2, 1))));
        board.put(moveType.HEAVY_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_sephiroth_hit_heavy1, 1),
                soundPool.load(context, R.raw.vc_sephiroth_hit_heavy2, 1))));

        board.put(moveType.KO, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_sephiroth_ko, 1))));
        board.put(moveType.TAUNT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_sephiroth_taunt, 1))));

        board.put(moveType.ROW_SHIFT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_sephiroth_row_shift, 1))));
        board.put(moveType.FOCGAR, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_sephiroth_focgar, 1))));
        board.put(moveType.DODGE, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_sephiroth_dodge1, 1),
                soundPool.load(context, R.raw.vc_sephiroth_dodge2, 1))));
    }
    public void init_ike(SoundPool soundPool, Context context){
        board.put(moveType.NORMAL_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_ike_normal_atk1, 1),
                soundPool.load(context, R.raw.vc_ike_normal_atk2, 1), soundPool.load(context, R.raw.vc_ike_normal_atk3, 1))));
        board.put(moveType.FAST_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_ike_fast_atk1, 1),
                soundPool.load(context, R.raw.vc_ike_fast_atk2, 1))));
        board.put(moveType.POWER_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_ike_power_atk1, 1),
                soundPool.load(context, R.raw.vc_ike_power_atk2, 1))));
        board.put(moveType.FINALE_ATTACK, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_ike_finale, 1))));

        board.put(moveType.BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_ike_buff, 1))));
        board.put(moveType.DEBUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_ike_debuff, 1))));
        board.put(moveType.SELF_BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_ike_self_buff, 1))));
        board.put(moveType.HEAL, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_ike_heal, 1))));

        board.put(moveType.LIGHT_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_ike_hit_light1, 1),
                soundPool.load(context, R.raw.vc_ike_hit_light2, 1))));
        board.put(moveType.MED_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_ike_hit_med1, 1),
                soundPool.load(context, R.raw.vc_ike_hit_med2, 1))));
        board.put(moveType.HEAVY_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_ike_hit_heavy1, 1),
                soundPool.load(context, R.raw.vc_ike_hit_heavy2, 1))));

        board.put(moveType.KO, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_ike_ko, 1))));
        board.put(moveType.TAUNT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_ike_taunt, 1))));

        board.put(moveType.ROW_SHIFT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_ike_row_shift, 1))));
        board.put(moveType.FOCGAR, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_ike_focgar, 1))));
        board.put(moveType.DODGE, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_ike_dodge1, 1),
                soundPool.load(context, R.raw.vc_ike_dodge2, 1))));
    }
    public void init_mii(SoundPool soundPool, Context context){
        board.put(moveType.NORMAL_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_mii_normal_atk1, 1),
                soundPool.load(context, R.raw.vc_mii_normal_atk2, 1), soundPool.load(context, R.raw.vc_mii_normal_atk3, 1))));
        board.put(moveType.FAST_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_mii_fast_atk1, 1),
                soundPool.load(context, R.raw.vc_mii_fast_atk2, 1))));
        board.put(moveType.POWER_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_mii_power_atk1, 1),
                soundPool.load(context, R.raw.vc_mii_power_atk2, 1))));
        board.put(moveType.FINALE_ATTACK, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_mii_finale, 1))));

        board.put(moveType.BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_mii_buff, 1))));
        board.put(moveType.DEBUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_mii_debuff, 1))));
        board.put(moveType.SELF_BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_mii_self_buff, 1))));
        board.put(moveType.HEAL, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_mii_heal, 1))));

        board.put(moveType.LIGHT_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_mii_hit_light1, 1),
                soundPool.load(context, R.raw.vc_mii_hit_light2, 1))));
        board.put(moveType.MED_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_mii_hit_med1, 1),
                soundPool.load(context, R.raw.vc_mii_hit_med2, 1))));
        board.put(moveType.HEAVY_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_mii_hit_heavy1, 1),
                soundPool.load(context, R.raw.vc_mii_hit_heavy2, 1))));

        board.put(moveType.KO, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_mii_ko, 1))));
        board.put(moveType.TAUNT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_mii_taunt, 1))));

        board.put(moveType.ROW_SHIFT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_mii_row_shift, 1))));
        board.put(moveType.FOCGAR, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_mii_focgar, 1))));
        board.put(moveType.DODGE, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_mii_dodge1, 1),
                soundPool.load(context, R.raw.vc_mii_dodge2, 1))));
    }
    public void init_byleth(SoundPool soundPool, Context context){
        board.put(moveType.NORMAL_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_byleth_normal_atk1, 1),
                soundPool.load(context, R.raw.vc_byleth_normal_atk2, 1), soundPool.load(context, R.raw.vc_byleth_normal_atk3, 1))));
        board.put(moveType.FAST_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_byleth_fast_atk1, 1),
                soundPool.load(context, R.raw.vc_byleth_fast_atk2, 1))));
        board.put(moveType.POWER_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_byleth_power_atk1, 1),
                soundPool.load(context, R.raw.vc_byleth_power_atk2, 1))));
        board.put(moveType.FINALE_ATTACK, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_byleth_finale, 1))));

        board.put(moveType.BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_byleth_buff, 1))));
        board.put(moveType.DEBUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_byleth_debuff, 1))));
        board.put(moveType.SELF_BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_byleth_self_buff, 1))));
        board.put(moveType.HEAL, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_byleth_heal, 1))));

        board.put(moveType.LIGHT_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_byleth_hit_light1, 1),
                soundPool.load(context, R.raw.vc_byleth_hit_light2, 1))));
        board.put(moveType.MED_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_byleth_hit_med1, 1),
                soundPool.load(context, R.raw.vc_byleth_hit_med2, 1))));
        board.put(moveType.HEAVY_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_byleth_hit_heavy1, 1),
                soundPool.load(context, R.raw.vc_byleth_hit_heavy2, 1))));

        board.put(moveType.KO, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_byleth_ko2, 1))));
        board.put(moveType.TAUNT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_byleth_taunt, 1))));

        board.put(moveType.ROW_SHIFT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_byleth_row_shift, 1))));
        board.put(moveType.FOCGAR, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_byleth_focgar, 1))));
        board.put(moveType.DODGE, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_byleth_dodge1, 1),
                soundPool.load(context, R.raw.vc_byleth_dodge2, 1))));
    }
    public void init_byleth_f(SoundPool soundPool, Context context){
        board.put(moveType.NORMAL_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_byleth_f_normal_atk1, 1),
                soundPool.load(context, R.raw.vc_byleth_f_normal_atk2, 1), soundPool.load(context, R.raw.vc_byleth_f_normal_atk3, 1))));
        board.put(moveType.FAST_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_byleth_f_fast_atk1, 1),
                soundPool.load(context, R.raw.vc_byleth_f_fast_atk2, 1))));
        board.put(moveType.POWER_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_byleth_f_power_atk1, 1),
                soundPool.load(context, R.raw.vc_byleth_f_power_atk2, 1))));
        board.put(moveType.FINALE_ATTACK, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_byleth_f_finale, 1))));

        board.put(moveType.BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_byleth_f_buff, 1))));
        board.put(moveType.DEBUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_byleth_f_debuff, 1))));
        board.put(moveType.SELF_BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_byleth_f_self_buff, 1))));
        board.put(moveType.HEAL, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_byleth_f_heal, 1))));

        board.put(moveType.LIGHT_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_byleth_f_hit_light1, 1),
                soundPool.load(context, R.raw.vc_byleth_f_hit_light2, 1))));
        board.put(moveType.MED_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_byleth_f_hit_med1, 1),
                soundPool.load(context, R.raw.vc_byleth_f_hit_med2, 1))));
        board.put(moveType.HEAVY_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_byleth_f_hit_heavy1, 1),
                soundPool.load(context, R.raw.vc_byleth_f_hit_heavy2, 1))));

        board.put(moveType.KO, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_byleth_f_ko, 1))));
        board.put(moveType.TAUNT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_byleth_f_taunt, 1))));

        board.put(moveType.ROW_SHIFT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_byleth_f_row_shift, 1))));
        board.put(moveType.FOCGAR, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_byleth_f_focgar, 1))));
        board.put(moveType.DODGE, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_byleth_f_dodge1, 1),
                soundPool.load(context, R.raw.vc_byleth_f_dodge2, 1))));
    }
    public void init_snake(SoundPool soundPool, Context context){
        board.put(moveType.NORMAL_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_snake_normal_atk1, 1),
                soundPool.load(context, R.raw.vc_snake_normal_atk2, 1), soundPool.load(context, R.raw.vc_snake_normal_atk3, 1))));
        board.put(moveType.FAST_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_snake_fast_atk1, 1),
                soundPool.load(context, R.raw.vc_snake_fast_atk2, 1))));
        board.put(moveType.POWER_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_snake_power_atk1, 1),
                soundPool.load(context, R.raw.vc_snake_power_atk2, 1))));
        board.put(moveType.FINALE_ATTACK, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_snake_finale, 1))));

        board.put(moveType.BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_snake_buff, 1))));
        board.put(moveType.DEBUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_snake_debuff, 1))));
        board.put(moveType.SELF_BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_snake_self_buff, 1))));
        board.put(moveType.HEAL, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_snake_heal, 1))));

        board.put(moveType.LIGHT_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_snake_hit_light1, 1),
                soundPool.load(context, R.raw.vc_snake_hit_light2, 1))));
        board.put(moveType.MED_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_snake_hit_med1, 1),
                soundPool.load(context, R.raw.vc_snake_hit_med2, 1))));
        board.put(moveType.HEAVY_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_snake_hit_heavy1, 1),
                soundPool.load(context, R.raw.vc_snake_hit_heavy2, 1))));

        board.put(moveType.KO, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_snake_ko, 1))));
        board.put(moveType.TAUNT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_snake_taunt, 1))));

        board.put(moveType.ROW_SHIFT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_snake_row_shift, 1))));
        board.put(moveType.FOCGAR, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_snake_focgar, 1))));
        board.put(moveType.DODGE, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_snake_dodge1, 1),
                soundPool.load(context, R.raw.vc_snake_dodge2, 1))));
    }
    public void init_ganon(SoundPool soundPool, Context context){
        board.put(moveType.NORMAL_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_ganon_normal_atk1, 1),
                soundPool.load(context, R.raw.vc_ganon_normal_atk2, 1), soundPool.load(context, R.raw.vc_ganon_normal_atk3, 1))));
        board.put(moveType.FAST_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_ganon_fast_atk1, 1),
                soundPool.load(context, R.raw.vc_ganon_fast_atk2, 1))));
        board.put(moveType.POWER_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_ganon_power_atk1, 1),
                soundPool.load(context, R.raw.vc_ganon_power_atk2, 1))));
        board.put(moveType.FINALE_ATTACK, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_ganon_finale, 1))));

        board.put(moveType.BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_ganon_buff, 1))));
        board.put(moveType.DEBUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_ganon_debuff, 1))));
        board.put(moveType.SELF_BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_ganon_self_buff, 1))));
        board.put(moveType.HEAL, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_ganon_heal, 1))));

        board.put(moveType.LIGHT_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_ganon_hit_light1, 1),
                soundPool.load(context, R.raw.vc_ganon_hit_light2, 1))));
        board.put(moveType.MED_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_ganon_hit_med1, 1),
                soundPool.load(context, R.raw.vc_ganon_hit_med2, 1))));
        board.put(moveType.HEAVY_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_ganon_hit_heavy1, 1),
                soundPool.load(context, R.raw.vc_ganon_hit_heavy2, 1))));

        board.put(moveType.KO, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_ganon_ko, 1))));
        board.put(moveType.TAUNT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_ganon_taunt, 1))));

        board.put(moveType.ROW_SHIFT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_ganon_row_shift, 1))));
        board.put(moveType.FOCGAR, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_ganon_focgar, 1))));
        board.put(moveType.DODGE, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_ganon_dodge1, 1),
                soundPool.load(context, R.raw.vc_ganon_dodge2, 1))));
    }
    public void init_cloud(SoundPool soundPool, Context context){
        board.put(moveType.NORMAL_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_cloud_normal_atk1, 1),
                soundPool.load(context, R.raw.vc_cloud_normal_atk2, 1), soundPool.load(context, R.raw.vc_cloud_normal_atk3, 1))));
        board.put(moveType.FAST_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_cloud_fast_atk1, 1),
                soundPool.load(context, R.raw.vc_cloud_fast_atk2, 1))));
        board.put(moveType.POWER_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_cloud_power_atk1, 1),
                soundPool.load(context, R.raw.vc_cloud_power_atk2, 1))));
        board.put(moveType.FINALE_ATTACK, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_cloud_finale, 1))));

        board.put(moveType.BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_cloud_buff, 1))));
        board.put(moveType.DEBUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_cloud_debuff, 1))));
        board.put(moveType.SELF_BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_cloud_self_buff, 1))));
        board.put(moveType.HEAL, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_cloud_heal, 1))));

        board.put(moveType.LIGHT_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_cloud_hit_light1, 1),
                soundPool.load(context, R.raw.vc_cloud_hit_light2, 1))));
        board.put(moveType.MED_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_cloud_hit_med1, 1),
                soundPool.load(context, R.raw.vc_cloud_hit_med2, 1))));
        board.put(moveType.HEAVY_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_cloud_hit_heavy1, 1),
                soundPool.load(context, R.raw.vc_cloud_hit_heavy2, 1))));

        board.put(moveType.KO, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_cloud_ko, 1))));
        board.put(moveType.TAUNT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_cloud_taunt, 1))));

        board.put(moveType.ROW_SHIFT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_cloud_row_shift, 1))));
        board.put(moveType.FOCGAR, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_cloud_focgar, 1))));
        board.put(moveType.DODGE, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_cloud_dodge1, 1),
                soundPool.load(context, R.raw.vc_cloud_dodge2, 1))));
    }
    public void init_sheik(SoundPool soundPool, Context context){
        board.put(moveType.NORMAL_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_sheik_normal_atk1, 1),
                soundPool.load(context, R.raw.vc_sheik_normal_atk2, 1), soundPool.load(context, R.raw.vc_sheik_normal_atk3, 1))));
        board.put(moveType.FAST_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_sheik_fast_atk1, 1),
                soundPool.load(context, R.raw.vc_sheik_fast_atk2, 1))));
        board.put(moveType.POWER_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_sheik_power_atk1, 1),
                soundPool.load(context, R.raw.vc_sheik_power_atk2, 1))));
        board.put(moveType.FINALE_ATTACK, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_sheik_finale, 1))));

        board.put(moveType.BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_sheik_buff, 1))));
        board.put(moveType.DEBUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_sheik_debuff, 1))));
        board.put(moveType.SELF_BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_sheik_self_buff, 1))));
        board.put(moveType.HEAL, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_sheik_heal, 1))));

        board.put(moveType.LIGHT_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_sheik_hit_light1, 1),
                soundPool.load(context, R.raw.vc_sheik_hit_light2, 1))));
        board.put(moveType.MED_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_sheik_hit_med1, 1),
                soundPool.load(context, R.raw.vc_sheik_hit_med2, 1))));
        board.put(moveType.HEAVY_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_sheik_hit_heavy1, 1),
                soundPool.load(context, R.raw.vc_sheik_hit_heavy2, 1))));

        board.put(moveType.KO, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_sheik_ko, 1))));
        board.put(moveType.TAUNT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_sheik_taunt, 1))));

        board.put(moveType.ROW_SHIFT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_sheik_row_shift, 1))));
        board.put(moveType.FOCGAR, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_sheik_focgar, 1))));
        board.put(moveType.DODGE, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_sheik_dodge1, 1),
                soundPool.load(context, R.raw.vc_sheik_dodge2, 1))));
    }

    public void init_dk(SoundPool soundPool, Context context){
        board.put(moveType.NORMAL_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_dk_normal_atk1, 1),
                soundPool.load(context, R.raw.vc_dk_normal_atk2, 1), soundPool.load(context, R.raw.vc_dk_normal_atk3, 1))));
        board.put(moveType.FAST_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_dk_fast_atk1, 1),
                soundPool.load(context, R.raw.vc_dk_fast_atk2, 1))));
        board.put(moveType.POWER_ATTACK, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_dk_power_atk1, 1),
                soundPool.load(context, R.raw.vc_dk_power_atk2, 1))));
        board.put(moveType.FINALE_ATTACK, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_dk_finale, 1))));

        board.put(moveType.BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_dk_buff, 1))));
        board.put(moveType.DEBUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_dk_debuff, 1))));
        board.put(moveType.SELF_BUFF, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_dk_self_buff, 1))));
        board.put(moveType.HEAL, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_dk_heal, 1))));

        board.put(moveType.LIGHT_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_dk_hit_light1, 1),
                soundPool.load(context, R.raw.vc_dk_hit_light2, 1))));
        board.put(moveType.MED_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_dk_hit_med1, 1),
                soundPool.load(context, R.raw.vc_dk_hit_med2, 1))));
        board.put(moveType.HEAVY_HIT, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_dk_hit_heavy1, 1),
                soundPool.load(context, R.raw.vc_dk_hit_heavy2, 1))));

        board.put(moveType.KO, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_dk_ko, 1))));
        board.put(moveType.TAUNT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_dk_taunt, 1))));

        board.put(moveType.ROW_SHIFT, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_dk_row_shift, 1))));
        board.put(moveType.FOCGAR, new ArrayList<>(List.of(soundPool.load(context, R.raw.vc_dk_focgar, 1))));
        board.put(moveType.DODGE, new ArrayList<>(Arrays.asList(soundPool.load(context, R.raw.vc_dk_dodge1, 1),
                soundPool.load(context, R.raw.vc_dk_dodge2, 1))));
    }



    public Thread playSFX(moveType moveType) {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                if(soundPool != null){
                    if(board.containsKey(moveType)) {
                        int sfx = getRandomSoundFromList(board.get(moveType));
                        soundPool.play(sfx, 1, 1, 1, 0, 1f);
                    }
                }
            }
        };
        Thread thr = new Thread(run);
        thr.start();

        return thr;
    }

    private int getRandomSoundFromList(ArrayList<Integer> list){
        return list.get((int) (Math.random() * list.size()));
    }

}


