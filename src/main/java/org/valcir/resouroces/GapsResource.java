package org.valcir.resouroces;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.valcir.DTO.WinningsIntervalDTO;
import org.valcir.service.IRewardGapsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/gaps")
public class GapsResource {

    @Inject IRewardGapsService rewardGapsService;

    @GET
    public Map<String, List<WinningsIntervalDTO>> test() {
        Map<String, List<WinningsIntervalDTO>> returnMap = new HashMap<>();
        returnMap.put("min", rewardGapsService.getBiggestGap());
        returnMap.put("max", rewardGapsService.getSmallestGap());
        return returnMap;
    }
}
