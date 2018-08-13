package org.alma.mircoservices.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

public class RandomGeneratorServiceTest {
    private RandomGeneratorService randomGeneratorService;
    @Before
    public void setUp() {
        randomGeneratorService = new RandomGeneratorServiceImpl();
    }
   

    @Test
    public void generatedNumberFactorIsBetwwenExpectedLimit() throws Exception {
        // on genere 1000 factors.
        List<Integer> randomFactors = IntStream.range(0,1000)
                                    .map(i-> randomGeneratorService.generateRandomFactor())
                                    .boxed().collect(Collectors.toList());
        
                                    assertThat(randomFactors).containsOnlyElementsOf(
                                        IntStream.range(11,100).boxed().collect(Collectors.toList()));

    }
}