package declatoration.simulatorial.fight.sounds;

public enum sfxCharBase {
    ROBIN,
    BYLETH,
    CLOUD,
    GANON,
    SNAKE,
    IKE,
    SEPHIROTH,
    TERRY,
    DK,
    FALCON,

    LUCINA,
    CORRIN,
    ROBIN_F,
    BYLETH_F,
    ZSS,
    PYRA,
    MYTHRA,
    MII,
    SHEIK,
    ZELDA;

    public static sfxCharBase getName(String str){
        String name = str.toLowerCase();
        sfxCharBase res;

        switch (name){
            case "robin":
                res = ROBIN;
                break;
            case "byleth":
                res = BYLETH;
                break;
            case "cloud":
                res = CLOUD;
                break;
            case "ganon":
                res = GANON;
                break;
            case "snake":
                res = SNAKE;
                break;
            case "ike":
                res = IKE;
                break;
            case "sephiroth":
            case "seph":
                res = SEPHIROTH;
                break;
            case "terry":
                res = TERRY;
                break;
            case "falcon":
                res = FALCON;
                break;
            case "dk":
                res = DK;
                break;
            case "lucina":
                res = LUCINA;
                break;
            case "corrin":
                res = CORRIN;
                break;
            case "robin_f":
                res = ROBIN_F;
                break;
            case "byleth_f":
                res = BYLETH_F;
                break;
            case "zss":
                res = ZSS;
                break;
            case "pyra":
                res = PYRA;
                break;
            case "mythra":
                res = MYTHRA;
                break;
            case "mii":
            default:
                res = MII;
                break;
            case "zelda":
                res = ZELDA;
                break;
            case "sheik":
                res = SHEIK;
                break;
        }

        return res;
    }
}
