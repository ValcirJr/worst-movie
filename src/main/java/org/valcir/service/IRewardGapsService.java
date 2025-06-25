package org.valcir.service;

import org.valcir.DTO.WinningsIntervalDTO;

import java.util.List;

public interface IRewardGapsService {

    List<WinningsIntervalDTO> getBiggestGap();
    List<WinningsIntervalDTO> getSmallestGap();

}
