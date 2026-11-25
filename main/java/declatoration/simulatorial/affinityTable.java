package declatoration.simulatorial;

import java.util.HashMap;

import declatoration.simulatorial.enums.affinity;
import declatoration.simulatorial.enums.effectiveness;

public class affinityTable {

    static boolean initialized = false;


    private static final HashMap <String, effectiveness> affinityReactions = new HashMap<>();


    public static effectiveness getEffectiveness(affinity aff1, affinity aff2){
        if(!initialized){
            initializeReactionMap();
        }

        String key = aff1.toString().toLowerCase() + "/" + aff2.toString().toLowerCase();

        return affinityReactions.get(key);
    }


    private static void initializeReactionMap(){
        affinityReactions.put("null/null",effectiveness.NEUTRAL);
        affinityReactions.put("null/steel",effectiveness.INEFFECTIVE);
        affinityReactions.put("null/venin", effectiveness.NEUTRAL);
        affinityReactions.put("null/aether", effectiveness.NEUTRAL);
        affinityReactions.put("null/dusk",effectiveness.INEFFECTIVE);
        affinityReactions.put("null/inferno",effectiveness.NEUTRAL);
        affinityReactions.put("null/glacier",effectiveness.NEUTRAL);
        affinityReactions.put("null/cyclone",effectiveness.NEUTRAL);
        affinityReactions.put("null/silva",effectiveness.NEUTRAL);
        affinityReactions.put("null/torrent",effectiveness.NEUTRAL);
        affinityReactions.put("null/gaia",effectiveness.INEFFECTIVE);
        affinityReactions.put("null/thunder",effectiveness.NEUTRAL);
        affinityReactions.put("null/vamp",effectiveness.NEUTRAL);
        affinityReactions.put("null/force",effectiveness.NEUTRAL);
        affinityReactions.put("null/ghenju",effectiveness.INEFFECTIVE);
        affinityReactions.put("null/dusk_inferno",effectiveness.NEUTRAL);
        affinityReactions.put("null/dragonsvalk",effectiveness.NEUTRAL);

        affinityReactions.put("steel/null",effectiveness.NEUTRAL);
        affinityReactions.put("steel/steel",effectiveness.NEUTRAL);
        affinityReactions.put("steel/venin",effectiveness.NEUTRAL);
        affinityReactions.put("steel/aether",effectiveness.NEUTRAL);
        affinityReactions.put("steel/dusk",effectiveness.NEUTRAL);
        affinityReactions.put("steel/inferno",effectiveness.INEFFECTIVE);
        affinityReactions.put("steel/glacier",effectiveness.NEUTRAL);
        affinityReactions.put("steel/cyclone",effectiveness.NEUTRAL);
        affinityReactions.put("steel/silva",effectiveness.NEUTRAL);
        affinityReactions.put("steel/torrent",effectiveness.INEFFECTIVE);
        affinityReactions.put("steel/gaia",effectiveness.NEUTRAL);
        affinityReactions.put("steel/thunder",effectiveness.IMMUNE);
        affinityReactions.put("steel/vamp",effectiveness.INEFFECTIVE);
        affinityReactions.put("steel/force",effectiveness.NEUTRAL);
        affinityReactions.put("steel/ghenju",effectiveness.NEUTRAL);
        affinityReactions.put("steel/dusk_inferno",effectiveness.INEFFECTIVE);
        affinityReactions.put("steel/dragonsvalk",effectiveness.INEFFECTIVE);

        affinityReactions.put("venin/null",effectiveness.EFFECTIVE);
        affinityReactions.put("venin/steel",effectiveness.IMMUNE);
        affinityReactions.put("venin/venin",effectiveness.INEFFECTIVE);
        affinityReactions.put("venin/aether",effectiveness.EFFECTIVE);
        affinityReactions.put("venin/dusk",effectiveness.NEUTRAL);
        affinityReactions.put("venin/inferno",effectiveness.NEUTRAL);
        affinityReactions.put("venin/glacier",effectiveness.INEFFECTIVE);
        affinityReactions.put("venin/cyclone",effectiveness.NEUTRAL);
        affinityReactions.put("venin/silva",effectiveness.EFFECTIVE);
        affinityReactions.put("venin/torrent",effectiveness.NEUTRAL);
        affinityReactions.put("venin/gaia",effectiveness.INEFFECTIVE);
        affinityReactions.put("venin/thunder",effectiveness.NEUTRAL);
        affinityReactions.put("venin/vamp",effectiveness.INEFFECTIVE);
        affinityReactions.put("venin/force",effectiveness.NEUTRAL);
        affinityReactions.put("venin/ghenju",effectiveness.NEUTRAL);
        affinityReactions.put("venin/dusk_inferno",effectiveness.NEUTRAL);
        affinityReactions.put("venin/dragonsvalk",effectiveness.NEUTRAL);

        affinityReactions.put("aether/null",effectiveness.INEFFECTIVE);
        affinityReactions.put("aether/steel",effectiveness.NEUTRAL);
        affinityReactions.put("aether/venin",effectiveness.NEUTRAL);
        affinityReactions.put("aether/aether",effectiveness.INEFFECTIVE);
        affinityReactions.put("aether/dusk",effectiveness.EFFECTIVE);
        affinityReactions.put("aether/inferno",effectiveness.NEUTRAL);
        affinityReactions.put("aether/glacier",effectiveness.NEUTRAL);
        affinityReactions.put("aether/cyclone",effectiveness.NEUTRAL);
        affinityReactions.put("aether/silva",effectiveness.INEFFECTIVE);
        affinityReactions.put("aether/torrent",effectiveness.NEUTRAL);
        affinityReactions.put("aether/gaia",effectiveness.NEUTRAL);
        affinityReactions.put("aether/thunder",effectiveness.NEUTRAL);
        affinityReactions.put("aether/vamp",effectiveness.EFFECTIVE);
        affinityReactions.put("aether/force",effectiveness.EFFECTIVE);
        affinityReactions.put("aether/ghenju",effectiveness.INEFFECTIVE);
        affinityReactions.put("aether/dusk_inferno",effectiveness.EFFECTIVE);
        affinityReactions.put("aether/dragonsvalk",effectiveness.NEUTRAL);

        affinityReactions.put("dusk/null",effectiveness.NEUTRAL);
        affinityReactions.put("dusk/steel",effectiveness.NEUTRAL);
        affinityReactions.put("dusk/venin",effectiveness.NEUTRAL);
        affinityReactions.put("dusk/aether",effectiveness.EFFECTIVE);
        affinityReactions.put("dusk/dusk",effectiveness.INEFFECTIVE);
        affinityReactions.put("dusk/inferno",effectiveness.NEUTRAL);
        affinityReactions.put("dusk/glacier",effectiveness.NEUTRAL);
        affinityReactions.put("dusk/cyclone",effectiveness.NEUTRAL);
        affinityReactions.put("dusk/silva",effectiveness.NEUTRAL);
        affinityReactions.put("dusk/torrent",effectiveness.NEUTRAL);
        affinityReactions.put("dusk/gaia",effectiveness.NEUTRAL);
        affinityReactions.put("dusk/thunder",effectiveness.NEUTRAL);
        affinityReactions.put("dusk/vamp",effectiveness.IMMUNE);
        affinityReactions.put("dusk/force",effectiveness.NEUTRAL);
        affinityReactions.put("dusk/ghenju",effectiveness.INEFFECTIVE);
        affinityReactions.put("dusk/dusk_inferno",effectiveness.INEFFECTIVE);
        affinityReactions.put("dusk/dragonsvalk",effectiveness.NEUTRAL);

        affinityReactions.put("inferno/null",effectiveness.NEUTRAL);
        affinityReactions.put("inferno/steel",effectiveness.EFFECTIVE);
        affinityReactions.put("inferno/venin",effectiveness.NEUTRAL);
        affinityReactions.put("inferno/aether",effectiveness.INEFFECTIVE);
        affinityReactions.put("inferno/dusk",effectiveness.NEUTRAL);
        affinityReactions.put("inferno/inferno",effectiveness.INEFFECTIVE);
        affinityReactions.put("inferno/glacier",effectiveness.EFFECTIVE);
        affinityReactions.put("inferno/cyclone",effectiveness.EFFECTIVE);
        affinityReactions.put("inferno/silva",effectiveness.EFFECTIVE);
        affinityReactions.put("inferno/torrent",effectiveness.INEFFECTIVE);
        affinityReactions.put("inferno/gaia",effectiveness.INEFFECTIVE);
        affinityReactions.put("inferno/thunder",effectiveness.NEUTRAL);
        affinityReactions.put("inferno/vamp",effectiveness.EFFECTIVE);
        affinityReactions.put("inferno/force",effectiveness.NEUTRAL);
        affinityReactions.put("inferno/ghenju",effectiveness.NEUTRAL);
        affinityReactions.put("inferno/dusk_inferno",effectiveness.INEFFECTIVE);
        affinityReactions.put("inferno/dragonsvalk",effectiveness.IMMUNE);

        affinityReactions.put("glacier/null",effectiveness.NEUTRAL);
        affinityReactions.put("glacier/steel",effectiveness.NEUTRAL);
        affinityReactions.put("glacier/venin",effectiveness.NEUTRAL);
        affinityReactions.put("glacier/aether",effectiveness.NEUTRAL);
        affinityReactions.put("glacier/dusk",effectiveness.NEUTRAL);
        affinityReactions.put("glacier/inferno",effectiveness.INEFFECTIVE);
        affinityReactions.put("glacier/glacier",effectiveness.INEFFECTIVE);
        affinityReactions.put("glacier/cyclone",effectiveness.NEUTRAL);
        affinityReactions.put("glacier/silva",effectiveness.EFFECTIVE);
        affinityReactions.put("glacier/torrent",effectiveness.EFFECTIVE);
        affinityReactions.put("glacier/gaia",effectiveness.NEUTRAL);
        affinityReactions.put("glacier/thunder",effectiveness.NEUTRAL);
        affinityReactions.put("glacier/vamp",effectiveness.NEUTRAL);
        affinityReactions.put("glacier/force",effectiveness.NEUTRAL);
        affinityReactions.put("glacier/ghenju",effectiveness.NEUTRAL);
        affinityReactions.put("glacier/dusk_inferno",effectiveness.INEFFECTIVE);
        affinityReactions.put("glacier/dragonsvalk",effectiveness.EFFECTIVE);

        affinityReactions.put("cyclone/null",effectiveness.NEUTRAL);
        affinityReactions.put("cyclone/steel",effectiveness.INEFFECTIVE);
        affinityReactions.put("cyclone/venin",effectiveness.NEUTRAL);
        affinityReactions.put("cyclone/aether",effectiveness.NEUTRAL);
        affinityReactions.put("cyclone/dusk",effectiveness.NEUTRAL);
        affinityReactions.put("cyclone/inferno",effectiveness.NEUTRAL);
        affinityReactions.put("cyclone/glacier",effectiveness.NEUTRAL);
        affinityReactions.put("cyclone/cyclone",effectiveness.NEUTRAL);
        affinityReactions.put("cyclone/silva",effectiveness.EFFECTIVE);
        affinityReactions.put("cyclone/torrent",effectiveness.NEUTRAL);
        affinityReactions.put("cyclone/gaia",effectiveness.INEFFECTIVE);
        affinityReactions.put("cyclone/thunder",effectiveness.EFFECTIVE);
        affinityReactions.put("cyclone/vamp",effectiveness.NEUTRAL);
        affinityReactions.put("cyclone/force",effectiveness.NEUTRAL);
        affinityReactions.put("cyclone/ghenju",effectiveness.EFFECTIVE);
        affinityReactions.put("cyclone/dusk_inferno",effectiveness.NEUTRAL);
        affinityReactions.put("cyclone/dragonsvalk",effectiveness.NEUTRAL);

        affinityReactions.put("silva/null",effectiveness.NEUTRAL);
        affinityReactions.put("silva/steel",effectiveness.NEUTRAL);
        affinityReactions.put("silva/venin",effectiveness.INEFFECTIVE);
        affinityReactions.put("silva/aether",effectiveness.NEUTRAL);
        affinityReactions.put("silva/dusk",effectiveness.NEUTRAL);
        affinityReactions.put("silva/inferno",effectiveness.INEFFECTIVE);
        affinityReactions.put("silva/glacier",effectiveness.NEUTRAL);
        affinityReactions.put("silva/cyclone",effectiveness.NEUTRAL);
        affinityReactions.put("silva/silva",effectiveness.NEUTRAL);
        affinityReactions.put("silva/torrent",effectiveness.EFFECTIVE);
        affinityReactions.put("silva/gaia",effectiveness.EFFECTIVE);
        affinityReactions.put("silva/thunder",effectiveness.NEUTRAL);
        affinityReactions.put("silva/vamp",effectiveness.EFFECTIVE);
        affinityReactions.put("silva/force",effectiveness.NEUTRAL);
        affinityReactions.put("silva/ghenju",effectiveness.INEFFECTIVE);
        affinityReactions.put("silva/dusk_inferno",effectiveness.INEFFECTIVE);
        affinityReactions.put("silva/dragonsvalk",effectiveness.NEUTRAL);

        affinityReactions.put("torrent/null",effectiveness.NEUTRAL);
        affinityReactions.put("torrent/steel",effectiveness.NEUTRAL);
        affinityReactions.put("torrent/venin",effectiveness.EFFECTIVE);
        affinityReactions.put("torrent/aether",effectiveness.NEUTRAL);
        affinityReactions.put("torrent/dusk",effectiveness.NEUTRAL);
        affinityReactions.put("torrent/inferno",effectiveness.EFFECTIVE);
        affinityReactions.put("torrent/glacier",effectiveness.NEUTRAL);
        affinityReactions.put("torrent/cyclone",effectiveness.NEUTRAL);
        affinityReactions.put("torrent/silva",effectiveness.INEFFECTIVE);
        affinityReactions.put("torrent/torrent",effectiveness.INEFFECTIVE);
        affinityReactions.put("torrent/gaia",effectiveness.EFFECTIVE);
        affinityReactions.put("torrent/thunder",effectiveness.NEUTRAL);
        affinityReactions.put("torrent/vamp",effectiveness.NEUTRAL);
        affinityReactions.put("torrent/force",effectiveness.NEUTRAL);
        affinityReactions.put("torrent/ghenju",effectiveness.NEUTRAL);
        affinityReactions.put("torrent/dusk_inferno",effectiveness.EFFECTIVE);
        affinityReactions.put("torrent/dragonsvalk",effectiveness.INEFFECTIVE);

        affinityReactions.put("gaia/null",effectiveness.NEUTRAL);
        affinityReactions.put("gaia/steel",effectiveness.INEFFECTIVE);
        affinityReactions.put("gaia/venin",effectiveness.NEUTRAL);
        affinityReactions.put("gaia/aether",effectiveness.NEUTRAL);
        affinityReactions.put("gaia/dusk",effectiveness.NEUTRAL);
        affinityReactions.put("gaia/inferno",effectiveness.EFFECTIVE);
        affinityReactions.put("gaia/glacier",effectiveness.EFFECTIVE);
        affinityReactions.put("gaia/cyclone",effectiveness.NEUTRAL);
        affinityReactions.put("gaia/silva",effectiveness.INEFFECTIVE);
        affinityReactions.put("gaia/torrent",effectiveness.NEUTRAL);
        affinityReactions.put("gaia/gaia",effectiveness.NEUTRAL);
        affinityReactions.put("gaia/thunder",effectiveness.NEUTRAL);
        affinityReactions.put("gaia/vamp",effectiveness.NEUTRAL);
        affinityReactions.put("gaia/force",effectiveness.INEFFECTIVE);
        affinityReactions.put("gaia/ghenju",effectiveness.NEUTRAL);
        affinityReactions.put("gaia/dusk_inferno",effectiveness.EFFECTIVE);
        affinityReactions.put("gaia/dragonsvalk",effectiveness.EFFECTIVE);

        affinityReactions.put("thunder/null",effectiveness.NEUTRAL);
        affinityReactions.put("thunder/steel",effectiveness.EFFECTIVE);
        affinityReactions.put("thunder/venin",effectiveness.NEUTRAL);
        affinityReactions.put("thunder/aether", effectiveness.INEFFECTIVE);
        affinityReactions.put("thunder/dusk",effectiveness.NEUTRAL);
        affinityReactions.put("thunder/inferno",effectiveness.NEUTRAL);
        affinityReactions.put("thunder/glacier",effectiveness.NEUTRAL);
        affinityReactions.put("thunder/cyclone",effectiveness.INEFFECTIVE);
        affinityReactions.put("thunder/silva",effectiveness.INEFFECTIVE);
        affinityReactions.put("thunder/torrent",effectiveness.EFFECTIVE);
        affinityReactions.put("thunder/gaia",effectiveness.NEUTRAL);
        affinityReactions.put("thunder/thunder",effectiveness.INEFFECTIVE);
        affinityReactions.put("thunder/vamp",effectiveness.NEUTRAL);
        affinityReactions.put("thunder/force",effectiveness.NEUTRAL);
        affinityReactions.put("thunder/ghenju",effectiveness.EFFECTIVE);
        affinityReactions.put("thunder/dusk_inferno",effectiveness.NEUTRAL);
        affinityReactions.put("thunder/dragonsvalk",effectiveness.NEUTRAL);

        affinityReactions.put("vamp/null",effectiveness.EFFECTIVE);
        affinityReactions.put("vamp/steel",effectiveness.NEUTRAL);
        affinityReactions.put("vamp/venin",effectiveness.NEUTRAL);
        affinityReactions.put("vamp/aether",effectiveness.IMMUNE);
        affinityReactions.put("vamp/dusk",effectiveness.EFFECTIVE);
        affinityReactions.put("vamp/inferno",effectiveness.INEFFECTIVE);
        affinityReactions.put("vamp/glacier",effectiveness.NEUTRAL);
        affinityReactions.put("vamp/cyclone",effectiveness.NEUTRAL);
        affinityReactions.put("vamp/silva",effectiveness.NEUTRAL);
        affinityReactions.put("vamp/torrent",effectiveness.NEUTRAL);
        affinityReactions.put("vamp/gaia",effectiveness.NEUTRAL);
        affinityReactions.put("vamp/thunder",effectiveness.NEUTRAL);
        affinityReactions.put("vamp/vamp",effectiveness.NEUTRAL);
        affinityReactions.put("vamp/force",effectiveness.NEUTRAL);
        affinityReactions.put("vamp/ghenju",effectiveness.EFFECTIVE);
        affinityReactions.put("vamp/dusk_inferno",effectiveness.NEUTRAL);
        affinityReactions.put("vamp/dragonsvalk",effectiveness.NEUTRAL);

        affinityReactions.put("force/null",effectiveness.EFFECTIVE);
        affinityReactions.put("force/steel",effectiveness.EFFECTIVE);
        affinityReactions.put("force/venin",effectiveness.INEFFECTIVE);
        affinityReactions.put("force/aether",effectiveness.INEFFECTIVE);
        affinityReactions.put("force/dusk",effectiveness.NEUTRAL);
        affinityReactions.put("force/inferno",effectiveness.NEUTRAL);
        affinityReactions.put("force/glacier",effectiveness.EFFECTIVE);
        affinityReactions.put("force/cyclone",effectiveness.NEUTRAL);
        affinityReactions.put("force/silva",effectiveness.NEUTRAL);
        affinityReactions.put("force/torrent",effectiveness.INEFFECTIVE);
        affinityReactions.put("force/gaia",effectiveness.EFFECTIVE);
        affinityReactions.put("force/thunder",effectiveness.NEUTRAL);
        affinityReactions.put("force/vamp",effectiveness.INEFFECTIVE);
        affinityReactions.put("force/force",effectiveness.NEUTRAL);
        affinityReactions.put("force/ghenju",effectiveness.NEUTRAL);
        affinityReactions.put("force/dusk_inferno",effectiveness.NEUTRAL);
        affinityReactions.put("force/dragonsvalk",effectiveness.NEUTRAL);

        affinityReactions.put("ghenju/null",effectiveness.EFFECTIVE);
        affinityReactions.put("ghenju/steel",effectiveness.NEUTRAL);
        affinityReactions.put("ghenju/venin",effectiveness.NEUTRAL);
        affinityReactions.put("ghenju/aether",effectiveness.EFFECTIVE);
        affinityReactions.put("ghenju/dusk",effectiveness.INEFFECTIVE);
        affinityReactions.put("ghenju/inferno",effectiveness.EFFECTIVE);
        affinityReactions.put("ghenju/glacier",effectiveness.NEUTRAL);
        affinityReactions.put("ghenju/cyclone",effectiveness.NEUTRAL);
        affinityReactions.put("ghenju/silva",effectiveness.EFFECTIVE);
        affinityReactions.put("ghenju/torrent",effectiveness.NEUTRAL);
        affinityReactions.put("ghenju/gaia",effectiveness.NEUTRAL);
        affinityReactions.put("ghenju/thunder",effectiveness.INEFFECTIVE);
        affinityReactions.put("ghenju/vamp",effectiveness.INEFFECTIVE);
        affinityReactions.put("ghenju/force",effectiveness.EFFECTIVE);
        affinityReactions.put("ghenju/ghenju",effectiveness.NEUTRAL);
        affinityReactions.put("ghenju/dusk_inferno",effectiveness.INEFFECTIVE);
        affinityReactions.put("ghenju/dragonsvalk",effectiveness.NEUTRAL);

        affinityReactions.put("dusk_inferno/null",effectiveness.NEUTRAL);
        affinityReactions.put("dusk_inferno/steel",effectiveness.EFFECTIVE);
        affinityReactions.put("dusk_inferno/venin",effectiveness.NEUTRAL);
        affinityReactions.put("dusk_inferno/aether",effectiveness.EFFECTIVE);
        affinityReactions.put("dusk_inferno/dusk",effectiveness.INEFFECTIVE);
        affinityReactions.put("dusk_inferno/inferno",effectiveness.INEFFECTIVE);
        affinityReactions.put("dusk_inferno/glacier",effectiveness.EFFECTIVE);
        affinityReactions.put("dusk_inferno/cyclone",effectiveness.EFFECTIVE);
        affinityReactions.put("dusk_inferno/silva",effectiveness.EFFECTIVE);
        affinityReactions.put("dusk_inferno/torrent",effectiveness.INEFFECTIVE);
        affinityReactions.put("dusk_inferno/gaia",effectiveness.INEFFECTIVE);
        affinityReactions.put("dusk_inferno/thunder",effectiveness.NEUTRAL);
        affinityReactions.put("dusk_inferno/vamp",effectiveness.IMMUNE);
        affinityReactions.put("dusk_inferno/force",effectiveness.NEUTRAL);
        affinityReactions.put("dusk_inferno/ghenju",effectiveness.INEFFECTIVE);
        affinityReactions.put("dusk_inferno/dusk_inferno",effectiveness.IMMUNE);
        affinityReactions.put("dusk_inferno/dragonsvalk",effectiveness.INEFFECTIVE);

        affinityReactions.put("dragonsvalk/null",effectiveness.NEUTRAL);
        affinityReactions.put("dragonsvalk/steel",effectiveness.NEUTRAL);
        affinityReactions.put("dragonsvalk/venin",effectiveness.NEUTRAL);
        affinityReactions.put("dragonsvalk/aether",effectiveness.NEUTRAL);
        affinityReactions.put("dragonsvalk/dusk",effectiveness.NEUTRAL);
        affinityReactions.put("dragonsvalk/inferno",effectiveness.NEUTRAL);
        affinityReactions.put("dragonsvalk/glacier",effectiveness.NEUTRAL);
        affinityReactions.put("dragonsvalk/cyclone",effectiveness.NEUTRAL);
        affinityReactions.put("dragonsvalk/silva",effectiveness.EFFECTIVE);
        affinityReactions.put("dragonsvalk/torrent",effectiveness.INEFFECTIVE);
        affinityReactions.put("dragonsvalk/gaia",effectiveness.NEUTRAL);
        affinityReactions.put("dragonsvalk/thunder",effectiveness.NEUTRAL);
        affinityReactions.put("dragonsvalk/vamp",effectiveness.NEUTRAL);
        affinityReactions.put("dragonsvalk/force",effectiveness.NEUTRAL);
        affinityReactions.put("dragonsvalk/ghenju",effectiveness.NEUTRAL);
        affinityReactions.put("dragonsvalk/dusk_inferno",effectiveness.NEUTRAL);
        affinityReactions.put("dragonsvalk/dragonsvalk",effectiveness.NEUTRAL);

        initialized = true;
    }

}
