package declatoration.simulatorial.enums;

public enum affinity {
    NULL,
    STEEL,
    VENIN,
    AETHER,
    DUSK,
    INFERNO,
    GLACIER,
    CYCLONE,
    SILVA,
    TORRENT,
    GAIA,
    THUNDER,
    FORCE,
    VAMP,
    GHENJU,
    DRAGONSVALK,
    DUSK_INFERNO,
    USER_TYPE;

    public static affinity getAffinity(String str){
        if(str!=null) {
            switch (str.toLowerCase()) {
                case "null":
                default:
                    return NULL;
                case "steel":
                    return STEEL;
                case "venin":
                    return VENIN;
                case "aether":
                    return AETHER;
                case "dusk":
                    return DUSK;
                case "inferno":
                    return INFERNO;
                case "glacier":
                    return GLACIER;
                case "cyclone":
                    return CYCLONE;
                case "silva":
                    return SILVA;
                case "torrent":
                    return TORRENT;
                case "gaia":
                    return GAIA;
                case "thunder":
                    return THUNDER;
                case "vamp":
                    return VAMP;
                case "force":
                    return FORCE;
                case "ghenju":
                    return GHENJU;
                case "dragonsvalk":
                    return DRAGONSVALK;
                case "user_type":
                    return USER_TYPE;
                case "dusk_inferno":
                    return DUSK_INFERNO;
            }
        }
        return NULL;
    }
}

