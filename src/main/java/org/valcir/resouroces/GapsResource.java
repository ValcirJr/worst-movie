package org.valcir.resouroces;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.valcir.DTO.MinMaxGapsIntervalDTO;
import org.valcir.service.IRewardGapsService;

/**
 * Endpoint REST responsável por expor os intervalos mínimo e máximo
 * entre vitórias de estúdios no prêmio Golden Raspberry.
 */
@Path("/gaps")
public class GapsResource {

    @Inject IRewardGapsService rewardGapsService;

    /**
     * Retorna os estúdios com menor e maior intervalo entre vitórias.
     *
     * @return objeto contendo listas de estúdios com os menores e maiores intervalos
     */
    @GET
    public MinMaxGapsIntervalDTO minMaxGaps() {
        return new MinMaxGapsIntervalDTO(
                rewardGapsService.getSmallestGap(),
                rewardGapsService.getBiggestGap());
    }
}
