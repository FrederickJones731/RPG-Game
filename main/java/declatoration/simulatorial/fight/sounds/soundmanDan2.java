package declatoration.simulatorial.fight.sounds;

import static java.lang.Thread.sleep;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.ArrayList;
import java.util.HashMap;

import declatoration.simulatorial.R;

public class soundmanDan2 {

    String musicName;
    static int initialMusicId, curPlayingId;

    private static MediaPlayer music = new MediaPlayer();
    public static MediaPlayer bonusMp = new MediaPlayer();

    private SoundPool soundPool;
    private AudioManager mAudioManager;


    private static HashMap<String, Integer> lexicon = new HashMap<>();

    public static boolean musicIsPlaying = false;
    public static boolean interrupted = false;

    public boolean init1CharSeqTriggered = false;

    ArrayList<Integer> musicIds = new ArrayList<>();

    public soundmanDan2(){

    }

    public soundmanDan2(Context context, String musicName, boolean isFightClass) {
        soundPool = new SoundPool.Builder().setMaxStreams(20).build();

        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        this.musicName = musicName;

        lexicon.put("click", soundPool.load(context, R.raw.correct, 1));
        lexicon.put("error", soundPool.load(context, R.raw.error, 1));

        if(isFightClass) {
            lexicon.put("no_damage", soundPool.load(context, R.raw.no_damage, 1));
            lexicon.put("hit", soundPool.load(context, R.raw.hit_temp, 1));
            lexicon.put("effective_hit", soundPool.load(context, R.raw.very_effective, 1));
            lexicon.put("ineffective_hit", soundPool.load(context, R.raw.not_very_effective, 1));
            lexicon.put("block", soundPool.load(context, R.raw.heavy_guard, 1));
            lexicon.put("miss", soundPool.load(context, R.raw.miss, 1));
            lexicon.put("critical", soundPool.load(context, R.raw.horn, 1));

            lexicon.put("row_shift", soundPool.load(context, R.raw.frag_shift, 1));
            lexicon.put("row_collapse", soundPool.load(context, R.raw.collapse_big, 1));

            lexicon.put("player_ko", soundPool.load(context, R.raw.awwww, 1));
            lexicon.put("enemy_ko", soundPool.load(context, R.raw.enemy_ko, 1));

            lexicon.put("guard_action", soundPool.load(context, R.raw.guard, 1));
            lexicon.put("focus_action", soundPool.load(context, R.raw.baby_sparkle, 1));
            lexicon.put("assist_action", soundPool.load(context, R.raw.assist, 1));


            lexicon.put("heal", soundPool.load(context, R.raw.heal, 1));
            lexicon.put("good_status_received", soundPool.load(context, R.raw.status_good, 1));
            lexicon.put("status_received", soundPool.load(context, R.raw.status_effect, 1));

            lexicon.put("stat_up", soundPool.load(context, R.raw.power_up, 1));
            lexicon.put("stat_down", soundPool.load(context, R.raw.disappointment, 1));

            lexicon.put("thud", soundPool.load(context, R.raw.thud, 1));
            lexicon.put("nerd", soundPool.load(context, R.raw.nerd, 1));

            lexicon.put("fight_select", soundPool.load(context, R.raw.fight_select, 1));
            lexicon.put("bubble", soundPool.load(context, R.raw.menu_expand, 1));
            lexicon.put("back_button", soundPool.load(context, R.raw.back_button, 1));
            lexicon.put("back_button_final", soundPool.load(context, R.raw.back_button_final, 1));

            lexicon.put("tap", soundPool.load(context, R.raw.click, 1));

            init1CharSeqTriggered = false;

        } else{

            lexicon.put("big_select", soundPool.load(context, R.raw.big_select, 1));
            lexicon.put("squish", soundPool.load(context, R.raw.squish, 1));

            lexicon.put("seekbar", soundPool.load(context, R.raw.spinner_click, 1));
            lexicon.put("spinner", soundPool.load(context, R.raw.spinner_click, 1));
            lexicon.put("menu", soundPool.load(context, R.raw.menu_expand, 1));


        }

        initialMusicId = getMusicId(musicName, context);

        music = MediaPlayer.create(context, initialMusicId);
        music.setLooping(true);
        if(!musicName.equals("game_over") && !musicName.equals("happy_win_yay")) {
            musicIsPlaying = true;
            music.start();
        }
    }


    public static class playNewMusicTrack implements Runnable {

        String musicName;
        Context context;
        boolean backToTheRoots;

        public playNewMusicTrack(String musicName, Context context, boolean replayNormalMusic){
            this.musicName = musicName;
            this.context = context;
            backToTheRoots = replayNormalMusic;
        }

        @Override
        public void run() {

                fadeOutMusic fade = new fadeOutMusic(true, false);
                Thread thread = new Thread(fade);
                thread.start();

                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if(backToTheRoots) {
                    music = MediaPlayer.create(context, initialMusicId);
                } else{
                    music = MediaPlayer.create(context, getMusicId(musicName, context));
                }
                music.setLooping(true);

                musicIsPlaying = true;
                music.start();
        }

    }

    private static int getMusicId(String musicName, Context context){
        switch(musicName.toLowerCase()){


            case "main1":
                curPlayingId = R.raw.main1;
                break;
            default:
            case "main2":
                curPlayingId = R.raw.main2;
                break;
            case "main3":
                curPlayingId = R.raw.main3;
                break;
            case "main4":
                curPlayingId = R.raw.main4;
                break;
            case "main5":
                curPlayingId = R.raw.main5;
                break;
            case "main6":
                curPlayingId = R.raw.main6;
                break;
            case "main7":
                curPlayingId = R.raw.main7;
                break;
            case "main8":
                curPlayingId = R.raw.main8;
                break;
            case "main9":
                curPlayingId = R.raw.main9;
                break;
            case "main10":
                curPlayingId = R.raw.main10;
                break;
            case "main11":
                curPlayingId = R.raw.main11;
                break;
            case "main12":
                curPlayingId = R.raw.main12;
                break;
            case "main13":
                curPlayingId = R.raw.main13;
                break;

            case "onset":
                curPlayingId = R.raw.onset;
                break;

            case "settings1":
                curPlayingId = R.raw.settings;
                break;
            case "settings2":
                curPlayingId = R.raw.settings2;
                break;
            case "settings3":
                curPlayingId = R.raw.settings3;
                break;

            case "inter_1-5":
                curPlayingId = R.raw.inter_start;
                break;
            case "inter_6-10":
                curPlayingId = R.raw.inter_poststart;
                break;
            case "inter_11-15":
                curPlayingId = R.raw.inter_premid;
                break;
            case "inter_16-20":
                curPlayingId = R.raw.inter_med;
                break;
            case "inter_21-25":
                curPlayingId = R.raw.inter_postmid;
                break;
            case "inter_26-30":
                curPlayingId = R.raw.inter_postpostmid;
                break;
            case "inter_31-35":
                curPlayingId = R.raw.inter_end;
                break;
            case "inter_36-40":
                curPlayingId = R.raw.inter_final;
                break;

            case "game_over":
                bonusMp = MediaPlayer.create(context, R.raw.game_over_lose);
                bonusMp.start();
                bonusMp.setOnCompletionListener(soundmanDan2::endTask);

                curPlayingId = R.raw.sad_game_over;
                break;
            case "happy_win_yay":
                bonusMp = MediaPlayer.create(context, R.raw.super_high_pitched_yay);
                bonusMp.start();
                bonusMp.setOnCompletionListener(soundmanDan2::endTask);

                curPlayingId = R.raw.malo_mart;
                break;

            // bosses
            case "brozigard":
                curPlayingId = R.raw.boss_brozigard;
                break;
            case "crescentio":
                curPlayingId = R.raw.boss_crescentio;
                break;
            case "sumire":
                curPlayingId = R.raw.boss_sumire;
                break;
            case "kanna":
                curPlayingId = R.raw.boss_kanna;
                break;
            case "hex'ahlia":
                curPlayingId = R.raw.boss_hexahlia;
                break;
            case "lucida":
                curPlayingId = R.raw.boss_lucida;
                break;
            case "kilde":
                curPlayingId = R.raw.boss_kilde;
                break;
            case "krista":
                curPlayingId = R.raw.boss_krista;
                break;
            case "jordan":
                curPlayingId = R.raw.boss_jordan;
                break;
            case "goat guy":
                curPlayingId = R.raw.boss_goat_guy;
                break;
            case "master zhen":
                curPlayingId = R.raw.boss_master_zhen;
                break;
            case "syphia":
                curPlayingId = R.raw.boss_syphia;
                break;
            case "morgana":
                curPlayingId = R.raw.boss_morgana;
                break;
            case "roark":
                curPlayingId = R.raw.boss_roark;
                break;
            case "solstice":
                curPlayingId = R.raw.boss_solstice;
                break;
            case "farquei":
            case "far'quei":
                curPlayingId = R.raw.boss_farquei;
                break;
            case "greybeard":
                curPlayingId = R.raw.boss_ghost;
                break;
            case "kremlin":
                curPlayingId = R.raw.boss_kremlin;
                break;
            case "eris":
                curPlayingId = R.raw.boss_eris;
                break;
            case "helga":
                curPlayingId = R.raw.boss_helga;
                break;
            case "sable":
                curPlayingId = R.raw.boss_sable;
                break;

            case "savannah":
                curPlayingId = R.raw.east_province_battle;
                break;
            case "sf2":
                curPlayingId = R.raw.sf2_ken;
                break;
            case "ruin":
                curPlayingId = R.raw.fea_champion_ablaze;
                break;
            case "desert":
                curPlayingId = R.raw.desert_fee;
                break;
            case "bazaar":
                curPlayingId = R.raw.shantae_burning_town;
                break;
            case "dusk":
                curPlayingId = R.raw.zinnia;
                break;
            case "bright":
                curPlayingId = R.raw.shantae_sequin_land;
                break;
            case "river":
                curPlayingId = R.raw.swear_by_that_sword_jimbo;
                break;
            case "profound":
                curPlayingId = R.raw.profound;
                break;
            case "hills":
                curPlayingId = R.raw.hills;
                break;
            case "castle":
                curPlayingId = R.raw.ff10_battle;
                break;
            case "grassland":
                curPlayingId = R.raw.xy_friend_battle;
                break;
            case "dragonlands":
                curPlayingId = R.raw.dagronlands;
                break;
            case "canyon":
                curPlayingId = R.raw.forward_ho;
                break;
            case "hands":
                curPlayingId = R.raw.mysticy;
                break;
            case "manor_of_doom":
                curPlayingId = R.raw.oras_boss;
                break;
            case "rave":
                curPlayingId = R.raw.penny;
                break;
            case "snowy":
                curPlayingId = R.raw.snowy;
                break;
            case "team_flare":
                curPlayingId = R.raw.team_flare;
                break;
            case "dark":
                curPlayingId = R.raw.xy_big_bad;
                break;
            case "space":
                curPlayingId = R.raw.epic_xy;
                break;
            case "spoopy":
                curPlayingId = R.raw.spoopy_cave;
                break;
            case "town":
                curPlayingId = R.raw.music_town;
                break;
            case "mystic_arena":
                curPlayingId = R.raw.music_mystic_arena;
                break;
            case "fort":
                curPlayingId = R.raw.music_fort;
                break;
            case "frosty":
                curPlayingId = R.raw.music_frosty;
                break;
            case "wastes":
                curPlayingId = R.raw.music_wastes;
                break;
            case "colliseum":
                curPlayingId = R.raw.music_colliseum;
                break;
            case "lunar_plains":
                curPlayingId = R.raw.lunar_plains;
                break;
            case "guild_town":
                curPlayingId = R.raw.guild_town;
                break;
        }

        return curPlayingId;
    }

    public SoundPool getSoundPool(){
        return this.soundPool;
    }

    public void playSound(String str, Context context) {

        if(soundPool != null){
            if(lexicon.containsKey(str)) {
                soundPool.play(lexicon.get(str), 1, 1, 1, 0, 1f);
            } else {
                playLesserSound(context, str);
            }
        }

    }
    public void playSound(Context context, Runnable run) {

        MediaPlayer mp = MediaPlayer.create(context, R.raw.thud);

//        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
////                if (run != null) {
////                    run.run();
////                    mp.release();
////                }
//            }
//        });
        mp.start();
    }
    private void playLesserSound(Context context, String str){

        int idToPlay;

        switch(str){
            case "status_heal":
                idToPlay = soundPool.load(context.getApplicationContext(), R.raw.status_heal, 1);
                break;
            case "reload":
                idToPlay = soundPool.load(context.getApplicationContext(), R.raw.reload, 1);
                break;
            case "step_in":
            default:
                idToPlay = soundPool.load(context.getApplicationContext(), R.raw.step_in, 1);
                break;
            case "sp_absorb":
                idToPlay = soundPool.load(context.getApplicationContext(), R.raw.sp_absorb, 1);
                break;
            case "grapple":
                idToPlay = soundPool.load(context.getApplicationContext(), R.raw.grapple, 1);
                break;
            case "grapple_release":
                idToPlay = soundPool.load(context.getApplicationContext(), R.raw.grapple_rel, 1);
                break;
            case "thorns":
                idToPlay = soundPool.load(context.getApplicationContext(), R.raw.thorns, 1);
                break;
            case "counter":
                idToPlay = soundPool.load(context.getApplicationContext(), R.raw.counter, 1);
                break;
            case "instakill":
                idToPlay = soundPool.load(context.getApplicationContext(), R.raw.instakill, 1);
                break;
            case "instakill2":
                idToPlay = soundPool.load(context.getApplicationContext(), R.raw.sword_shing, 1);
                break;
            case "metamorph":
                idToPlay = soundPool.load(context.getApplicationContext(), R.raw.metamorph, 1);
                break;
            case "bane":
                idToPlay = soundPool.load(context.getApplicationContext(), R.raw.bane_sfx, 1);
                break;
            case "swallow":
                idToPlay = soundPool.load(context.getApplicationContext(), R.raw.swallow_fin, 1);
                break;
            case "chaos_rift":
                idToPlay = soundPool.load(context.getApplicationContext(), R.raw.chaos_rift, 1);
                break;
            case "elemental_burst":
                idToPlay = soundPool.load(context.getApplicationContext(), R.raw.elemental_burst, 1);
                break;
            case "rune_burst":
                idToPlay = soundPool.load(context.getApplicationContext(), R.raw.rune_burst, 1);
                break;
            case "divine_burst":
                idToPlay = soundPool.load(context.getApplicationContext(), R.raw.divine_burst, 1);
                break;
            case "turn_off":
                idToPlay = soundPool.load(context.getApplicationContext(), R.raw.windows, 1);
                break;
            case "stat_steal":
                idToPlay = soundPool.load(context.getApplicationContext(), R.raw.unsheathe2, 1);
                break;
            case "turn_end":
                idToPlay = soundPool.load(context.getApplicationContext(), R.raw.turn_end, 1);
                break;

            case "transform":
                idToPlay = soundPool.load(context.getApplicationContext(), R.raw.transfoooooorrrrmmmm, 1);
                break;
            case "immune":
                idToPlay = soundPool.load(context.getApplicationContext(), R.raw.bathud, 1);
                break;
            case "target_status":
                idToPlay = soundPool.load(context.getApplicationContext(), R.raw.raygun, 1);
                break;
        }

        soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
            if (status == 0) {
                soundPool.play(idToPlay, 1, 1, 1, 0, 1f);
            } else {
                // Handle load error if needed
            }
        });
    }

    public void resumeMusic(Context context){
        if(!musicIsPlaying) {
            interrupted = false;

            try {
                music.start();
                musicIsPlaying = true;
            } catch (IllegalStateException e) {
                e.printStackTrace();
                music = MediaPlayer.create(context, getMusicId(musicName, context));
                music.setLooping(true);
                music.start();
                musicIsPlaying = true;
            }
        }
    }
    public void pauseMusic(){
        music.pause();
        musicIsPlaying = false;
        interrupted = true;
    }

    public void init1CharSequence(Context context) {
        if(!init1CharSeqTriggered) {

            init1CharSeqTriggered = true;

            fadeOutMusic fade = new fadeOutMusic(true, false);
            Thread thread = new Thread(fade);
            thread.start();

            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            switch ((int) (Math.random() * 5)) {

                case 0:
                default:
                    music = MediaPlayer.create(context, R.raw.one_char);
                    break;
                case 1:
                    music = MediaPlayer.create(context, R.raw.one_char2);
                    break;
                case 2:
                    music = MediaPlayer.create(context, R.raw.one_char3);
                    break;
                case 3:
                    music = MediaPlayer.create(context, R.raw.one_char4);
                    break;
                case 4:
                    music = MediaPlayer.create(context, R.raw.one_char5);
                    break;
            }


            music.setLooping(true);
            music.start();
            musicIsPlaying = true;
        }
    }


    public static class fadeOutMusic implements Runnable {

        boolean speedIsFast, settingMusicSwap;
        static boolean isPlaying = false;

        public fadeOutMusic(boolean speedIsFast, boolean settingsMusicSwap){
            this.settingMusicSwap = settingsMusicSwap;
            this.speedIsFast = speedIsFast;
        }


        @Override
        public void run() {
            if(!isPlaying) {
                isPlaying = true;

                int offset;

                if (speedIsFast) {
                    offset = 4;
                } else {
                    offset = 10;
                }

                for (float i = 100; i > 1 && !(music == null); i--) {
                    try {
                        music.setVolume(i / 100, i / 100);
                        sleep(offset);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

//                music.stop();
                if(musicIsPlaying) {
                    music.setVolume(1, 1);
                    musicIsPlaying = false;

                    endTask(music);
                }

                isPlaying = false;
            }
        }
    }

    public static void endTask(MediaPlayer mp) {

        mp.reset();
        mp.release();
    }
}
