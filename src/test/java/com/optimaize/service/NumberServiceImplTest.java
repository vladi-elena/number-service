package com.optimaize.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class NumberServiceImplTest {

    private NumberService numberService;

    @BeforeEach
    void init() {
        numberService = new NumberServiceImpl();
    }

    @Test
    void shouldThrowExceptionWhenTakeNullNumber() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> numberService.takeNumber(null)
        );

        assertTrue(exception.getMessage().contains("must not be null"));
    }

    @Test
    void shouldThrowExceptionWhenTakeNegativeNumber() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> numberService.takeNumber(-34L)
        );

        assertTrue(exception.getMessage().contains("must be positive numbers"));
    }

    @Test
    void shouldThrowExceptionWhenTakeEmptyNumberList() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> numberService.takeNumbers(new ArrayList<>())
        );

        assertEquals(exception.getMessage(), "List of Offered numbers must not be empty!");
    }

    @Test
    void shouldThrowExceptionWhenTakeNumberListContainingNullValues() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> numberService.takeNumbers(Arrays.asList(123L, null, 76543L))
        );

        assertEquals(exception.getMessage(), "Offered numbers must not be null!");
    }

    @Test
    void shouldThrowExceptionWhenTkeNumberListContainingNegativeValues() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> numberService.takeNumbers(Arrays.asList(123L, -123L, 76543L))
        );

        assertEquals(exception.getMessage(), "Offered numbers must be positive numbers!");
    }

    @Test
    void getMaxNumber() {
        /* Checks */
        assertNull(numberService.getMaxNumber());

        Random random = new Random();
        List<Long> numbers = new ArrayList<>();
        IntStream.range(0, 10000).forEach(i -> {
            /* only positive numbers */
            Long number = random.nextLong() & Long.MAX_VALUE;
            numbers.add(number);
        });

        numberService.takeNumbers(numbers);

        /* Checks */
        assertEquals(Collections.max(numbers), numberService.getMaxNumber());

        IntStream.range(0, 10000).forEach(i -> {
            /* only positive numbers */
            Long number = random.nextLong() & Long.MAX_VALUE;
            numbers.add(number);
            numberService.takeNumber(number);
        });

        /* Checks */
        assertEquals(Collections.max(numbers), numberService.getMaxNumber());
    }

    @Test
    void getMinNumber() {
        /* Checks */
        assertNull(numberService.getMinNumber());

        Random random = new Random();
        List<Long> numbers = new ArrayList<>();
        IntStream.range(0, 10000).forEach(i -> {
            /* only positive numbers */
            Long number = random.nextLong() & Long.MAX_VALUE;
            numbers.add(number);
        });

        numberService.takeNumbers(numbers);

        /* Checks */
        assertEquals(Collections.min(numbers), numberService.getMinNumber());

        IntStream.range(0, 10000).forEach(i -> {
            /* only positive numbers */
            Long number = random.nextLong() & Long.MAX_VALUE;
            numbers.add(number);
            numberService.takeNumber(number);
        });

        /* Checks */
        assertEquals(Collections.min(numbers), numberService.getMinNumber());
    }


    @Test
    void getAverageNumber() {
        /* Checks */
        assertNull(numberService.getAverageNumber());

        Random random = new Random();
        List<Long> numbers = new ArrayList<>();
        IntStream.range(0, 10000).forEach(i -> {
            /* only positive numbers */
            Long number = random.nextLong() & Long.MAX_VALUE;
            numbers.add(number);
        });

        numberService.takeNumbers(numbers);

        /* Checks */
        assertEquals(calculateAverage(numbers), numberService.getAverageNumber());

        IntStream.range(0, 10000).forEach(i -> {
            /* only positive numbers */
            Long number = random.nextLong() & Long.MAX_VALUE;
            numbers.add(number);
            numberService.takeNumber(number);
        });

        /* Checks */
        assertEquals(calculateAverage(numbers), numberService.getAverageNumber());
    }

    private Long calculateAverage(List<Long> numbers) {
        BigInteger sum = numbers.stream().map(BigInteger::valueOf).reduce(BigInteger.ZERO, BigInteger::add);
        return sum.divide(BigInteger.valueOf(numbers.size())).longValue();
    }
}