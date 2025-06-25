package org.valcir.resouroces;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.valcir.DTO.MinMaxGapsIntervalDTO;
import org.valcir.service.IRewardGapsService;

@Path("/gaps")
public class GapsResource {

    @Inject IRewardGapsService rewardGapsService;

    @GET
    public MinMaxGapsIntervalDTO minMaxGaps() {
        return new MinMaxGapsIntervalDTO(
                rewardGapsService.getSmallestGap(),
                rewardGapsService.getBiggestGap());
    }
}
