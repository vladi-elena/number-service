package com.optimaize.service;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Thread-safe implementation of the {@link NumberService}.
 * <p>
 * NumberService that can be offered positive long numbers.
 * <p>
 * At any given time it can tell 3 things:
 * 1) the smallest number it has encountered so far
 * 2) the largest number it has encountered so far
 * 3) the average of all numbers it has encountered so far
 * <p>
 * All operations are thread-safe.
 *
 * @see NumberService
 */
public class NumberServiceImpl implements NumberService {

    private final AtomicReference<BigInteger> minReference;

    private final AtomicReference<BigInteger> maxReference;

    private final AtomicReference<BigInteger> sumReference;

    private final AtomicReference<BigInteger> counterReference;

    private final NumberValidator numberValidator;

    public NumberServiceImpl() {
        this.minReference = new AtomicReference<>();
        this.maxReference = new AtomicReference<>();
        this.sumReference = new AtomicReference<>();
        this.counterReference = new AtomicReference<>(BigInteger.ZERO);
        this.numberValidator = new NumberValidator();
    }

    @Override
    public final void takeNumber(final Long number) {
        numberValidator.validateNumber(number);
        BigInteger newNumber = toBigInteger(number);
        updateMaxNumber(newNumber);
        updateMinNumber(newNumber);
        updateSumNumber(newNumber);
        updateCounter();
    }

    @Override
    public void takeNumbers(final List<Long> numbers) {
        numberValidator.validateNumbers(numbers);
        List<BigInteger> newNumbers = numbers.stream()
                .map(this::toBigInteger)
                .collect(Collectors.toList());
        updateMaxNumber(Collections.max(newNumbers));
        updateMinNumber(Collections.min(newNumbers));
        updateSumNumber(newNumbers.stream().reduce(BigInteger.ZERO, BigInteger::add));
        updateCounter(newNumbers.size());
    }

    @Override
    public final Long getMaxNumber() {
        if (maxReference.get() != null) {
            return maxReference.get().longValue();
        }
        return null;
    }

    @Override
    public final Long getMinNumber() {
        if (minReference.get() != null) {
            return minReference.get().longValue();
        }
        return null;
    }

    @Override
    public final Long getAverageNumber() {
        if (sumReference.get() == null) {
            return null;
        }
        return sumReference.get().divide(counterReference.get()).longValue();
    }


    private BigInteger toBigInteger(final Long number) {
        numberValidator.validateNumber(number);
        try {
            return new BigInteger(number.toString());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Offered numbers must be whole numbers");
        }
    }

    private void updateMinNumber(final BigInteger number) {
        if (minReference.compareAndSet(null, number)) {
            return;
        }

        BigInteger minValue = minReference.get();
        while (number.compareTo(minValue) < 0) {
            if (minReference.compareAndSet(minValue, number)) {
                return;
            }
            minValue = minReference.get();
        }
    }

    private void updateMaxNumber(final BigInteger number) {
        if (maxReference.compareAndSet(null, number)) {
            return;
        }

        BigInteger maxValue = maxReference.get();
        while (number.compareTo(maxValue) > 0) {
            if (maxReference.compareAndSet(maxValue, number)) {
                return;
            }
            maxValue = minReference.get();
        }
    }

    private void updateSumNumber(final BigInteger number) {
        if (sumReference.compareAndSet(null, number)) {
            return;
        }
        sumReference.accumulateAndGet(number, (n, m) -> m.add(n));
    }

    private void updateCounter() {
        counterReference.accumulateAndGet(BigInteger.ONE, (n, m) -> m.add(n));
    }

    private void updateCounter(final int count) {
        counterReference.accumulateAndGet(BigInteger.valueOf(count), (n, m) -> m.add(n));
    }

}
