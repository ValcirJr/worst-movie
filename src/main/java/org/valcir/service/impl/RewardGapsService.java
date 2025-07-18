package org.valcir.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.valcir.DTO.WinningsIntervalDTO;
import org.valcir.entity.Movie;
import org.valcir.service.EntitiesCRUD.IMovieService;
import org.valcir.service.IRewardGapsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Serviço responsável por calcular os intervalos entre vitórias
 * de produtores no prêmio Golden Raspberry.
 * Retorna os maiores e menores gaps entre vitórias consecutivas.
 */
@ApplicationScoped
public class RewardGapsService implements IRewardGapsService {

    @Inject IMovieService movieService;

    /**
     * Retorna a lista de produtores com o maior intervalo entre vitórias consecutivas.
     *
     * @return lista de {@link WinningsIntervalDTO} com o maior gap encontrado
     */
    public List<WinningsIntervalDTO> getBiggestGap(){
        List<WinningsIntervalDTO> allIntervals = getWinningIntervalsByProducer();
        int max = allIntervals.stream().mapToInt(WinningsIntervalDTO::interval).max().orElse(0);
        return allIntervals.stream().filter(i -> i.interval() == max).toList();
    }

    /**
     * Retorna a lista de produtores com o menor intervalo entre vitórias consecutivas.
     *
     * @return lista de {@link WinningsIntervalDTO} com o menor gap encontrado
     */
    public List<WinningsIntervalDTO> getSmallestGap(){
        List<WinningsIntervalDTO> allIntervals = getWinningIntervalsByProducer();
        int min = allIntervals.stream().mapToInt(WinningsIntervalDTO::interval).min().orElse(0);
        return allIntervals.stream().filter(i -> i.interval() == min).toList();
    }

    private List<WinningsIntervalDTO> getWinningIntervalsByProducer(){
        List<Movie> goldenRaspberryWinners = movieService.findGoldenRaspberryWinners();
        Map<String, List<Integer>> winningYearsByProducer = getWinningYearsByProducer(goldenRaspberryWinners);
         return getWinningsInterval(winningYearsByProducer);
    }

    private Map<String, List<Integer>> getWinningYearsByProducer(List<Movie> goldenRaspberryWinners) {
        return goldenRaspberryWinners.stream()
                .flatMap(movie -> movie.getProducers().stream()
                        .map(producer -> Map.entry(producer.getName(), movie.getYear()))
                )
                .collect(
                        Collectors.groupingBy(
                                Map.Entry::getKey,
                                Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                        )
                );
    }


    private List<WinningsIntervalDTO> getWinningsInterval(Map<String, List<Integer>> winningYearsByProducer) {
        return winningYearsByProducer.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .flatMap(entry -> {
                    List<Integer> ordenedYears = entry.getValue().stream().sorted().toList();
                    List<WinningsIntervalDTO> winningsIntervalsToReturn = new ArrayList<>();
                    buildIntervals(entry, ordenedYears, winningsIntervalsToReturn);
                    return winningsIntervalsToReturn.stream();
                }).toList();
    }

    private void buildIntervals(Map.Entry<String, List<Integer>> entry, List<Integer> ordenedYears,
                                       List<WinningsIntervalDTO> winningsIntervalsToReturn) {
        for (int i = 1; i < ordenedYears.size(); i++) {
            Integer interval = ordenedYears.get(i) - ordenedYears.get(i - 1);
            winningsIntervalsToReturn.add(
                    new WinningsIntervalDTO(entry.getKey(),
                            interval,
                            ordenedYears.get(i - 1),
                            ordenedYears.get(i)));
        }
    }
}
