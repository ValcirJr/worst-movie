package org.valcir.DTO;

public record WinningsIntervalDTO(
        String producer,
        Integer interval,
        Integer previousWin,
        Integer followingWin) {
}
