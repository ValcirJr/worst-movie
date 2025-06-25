package org.valcir.DTO;

import java.util.List;

public record MinMaxGapsIntervalDTO(List<WinningsIntervalDTO> min,
                                    List<WinningsIntervalDTO> max) {
}
