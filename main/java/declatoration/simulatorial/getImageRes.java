package declatoration.simulatorial;

import android.graphics.Color;

import java.util.Objects;

import declatoration.simulatorial.enums.affinity;
import declatoration.simulatorial.enums.effectiveness;
import declatoration.simulatorial.enums.statusEffect;
import declatoration.simulatorial.enums.weaponType;

public class getImageRes {


    public static int getAffinityImageResource(affinity affinityName) {
        int resId = R.drawable.sett;

        if (affinityName != null) {
            switch (affinityName) {
                case NULL:
                    resId = R.drawable.symbol_null;
                    break;
                case STEEL:
                    resId = R.drawable.symbol_steel;
                    break;
                case VENIN:
                    resId = R.drawable.symbol_venin_wip;
                    break;
                case AETHER:
                    resId = R.drawable.symbol_light;
                    break;
                case DUSK:
                    resId = R.drawable.symbol_dark;
                    break;
                case INFERNO:
                    resId = R.drawable.symbol_fire;
                    break;
                case GLACIER:
                    resId = R.drawable.symbol_glacier;
                    break;
                case TORRENT:
                    resId = R.drawable.symbol_torrent;
                    break;
                case CYCLONE:
                    resId = R.drawable.symbol_cyclone;
                    break;
                case SILVA:
                    resId = R.drawable.symbol_silva;
                    break;
                case GAIA:
                    resId = R.drawable.symbol_physical;
                    break;
                case THUNDER:
                    resId = R.drawable.symbol_thunder;
                    break;
                case VAMP:
                    resId = R.drawable.dark_redoo;
                    break;
                case FORCE:
                    resId = R.drawable.weapon_icon_punch;
                    break;
                case GHENJU:
                    resId = R.drawable.symbol_ghenju;
                    break;
                case DUSK_INFERNO:
                    resId = R.drawable.symbol_dusk_inferno;
                    break;
                case DRAGONSVALK:
                    resId = R.drawable.symbol_dragon;
                    break;
                default:
            }
        } else {
            resId = R.drawable.empty_bar;
        }

        return resId;
    }

    public static int getEffectivenessIcon(effectiveness eff){
        int resId;
        if(Objects.equals(eff, null)){
            eff = effectiveness.NEUTRAL;
        }
        switch (eff){
            case EFFECTIVE:
                resId = R.drawable.up_arrow;
                break;
            case INEFFECTIVE:
                resId = R.drawable.down_arrow;
                break;
            case IMMUNE:
                resId = R.drawable.symbol_null;
                break;
            case NEUTRAL:
            default:
                resId = R.drawable.neutral_dot;
        }

        return resId;
    }

    public static int getWeaponIcon(weaponType weaponName) {
        int resId;

        switch (weaponName) {
            case SWORD:
                resId = R.drawable.weapon_icon_sword;
                break;
            case BROADSWORD:
                resId = R.drawable.symbol_broadsword;
                break;
            case SPEAR:
            case SCYTHE:
                resId = R.drawable.weapon_icon_spear;
                break;
            case POLEARM:
                resId = R.drawable.symbol_polearm;
                break;
            case AXE:
//            case "club":
                resId = R.drawable.weapon_icon_axe;
                break;
            case HALBERD:
                resId = R.drawable.symbol_halberd;
                break;
            case BOW:
                resId = R.drawable.weapon_icon_bow;
                break;
            case PUNCH:
                resId = R.drawable.weapon_icon_punch;
                break;
            case MAGIC:
            case HEX:
                resId = R.drawable.weapon_icon_magic;
                break;
            case DUSTER:
                resId = R.drawable.weapon_icon_duster;
                break;
            case STAFF:
                resId = R.drawable.weapon_icon_healing;
                break;
            case SPOT:
                resId = R.drawable.symbol_spot;
                break;
            case DRAGON:
                resId = R.drawable.symbol_dragon;
                break;
            case FLAIL:
                resId = R.drawable.symbol_flail;
                break;
            case CANNON:
                resId = R.drawable.symbol_cannon;
                break;
            case RIFLE:
                resId = R.drawable.symbol_rifle;
                break;
            case DAGGER:
                resId = R.drawable.symbol_dagger;
                break;

            case BEAST:
            default:
                resId = R.drawable.neutral_dot;
                break;
        }

        return resId;
    }
    public static int getWeaponIconXS(weaponType weaponName) {
        int resId;

        switch (weaponName) {
            case SWORD:
                resId = R.drawable.symbol_sword;
                break;
            case SPEAR:
            case SCYTHE:
                resId = R.drawable.symbol_spear;
                break;
            case AXE:
                resId = R.drawable.symbol_axe;
                break;
            case BOW:
                resId = R.drawable.symbol_bow;
                break;
            case PUNCH:
                resId = R.drawable.weapon_icon_punch;
                break;
            case MAGIC:
            case HEX:
                resId = R.drawable.symbol_magic;
                break;
            case STAFF:
//            case "heal":
                resId = R.drawable.symbol_heal;
                break;
//            case "transformation":
            case BEAST:
            default:
                resId = Color.TRANSPARENT;
                break;
            case SPOT:
            case FLAIL:
            case RIFLE:
            case CANNON:
            case DAGGER:
            case HALBERD:
            case POLEARM:
            case BROADSWORD:
                resId = getWeaponIcon(weaponName);
                break;
        }

        return resId;
    }

    public static int getStatusIcon(statusEffect effect){
        int resId;
        switch (effect){
            case BURN:
                resId = R.drawable.symbol_fire;
                break;
            case STUN:
                resId = R.drawable.symbol_thunder;
                break;
            case FREEZE:
                resId = R.drawable.symbol_glacier;
                break;
            case POISON:
                resId = R.drawable.symbol_venin_wip;
                break;
            case RATTLE:
                resId = R.drawable.symbol_mag;
                break;
            case INTIMIDATE:
                resId = R.drawable.symbol_ghenju;
                break;
            case SLEEP:
                resId = R.drawable.symbol_dark;
                break;
            case CHARM:
                resId = R.drawable.lure;
                break;
            case BANE:
                resId = R.drawable.goff;
                break;
            case RAGE:
                resId = R.drawable.gorr;
                break;
            case BEDRAGGLE:
                resId = R.drawable.symbol_torrent;
                break;
            case UNTARGETABLE:
                resId = R.drawable.dark_redoo;
                break;
            case TOPPLE:
                resId = R.drawable.down_arrow;
                break;
            case ASSIST_UP:
                resId = R.drawable.up_arrow;
                break;
            case ENERVATE:
                resId = R.drawable.symbol_light;
                break;
            case ENGAGE:
                resId = R.drawable.box_yellow;
                break;
            case COUNTER:
                resId = R.drawable.cactus;
                break;
            case BLEED:
                resId = R.drawable.box_very_red;
                break;

            default:
                resId = Color.TRANSPARENT;
        }

        return resId;
    }
}
