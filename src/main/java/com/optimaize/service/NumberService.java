package com.optimaize.service;

import java.util.List;

/**
 * Service that can be offered positive long numbers.
 * <p>
 * At any given time it can tell 3 things:
 * 1) the smallest number it has encountered so far
 * 2) the largest number it has encountered so far
 * 3) the average of all numbers it has encountered so far
 * <p>
 */
public interface NumberService {

    /**
     * Takes positive number.
     *
     * @param number long number
     * @throws IllegalArgumentException if the specified number is null or negative
     */
    void takeNumber(Long number);

    /**
     * Takes list of positive numbers.
     *
     * @param numbers list of long numbers
     * @throws IllegalArgumentException if the specified list is null or empty
     *                                  or specified list contains null or negative
     */
    void takeNumbers(List<Long> numbers);

    /**
     * Returns  the largest number it has encountered so far or null.
     *
     * @return max
     */
    Long getMaxNumber();

    /**
     * Returns the smallest number it has encountered so far or null.
     *
     * @return min
     */
    Long getMinNumber();


    /**
     * Returns average of all numbers it has encountered so far or null.
     *
     * @return average
     */
    Long getAverageNumber();
}
