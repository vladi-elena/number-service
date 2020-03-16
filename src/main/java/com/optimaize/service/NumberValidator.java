package com.optimaize.service;

import java.util.List;

class NumberValidator {

    final void validateNumber(final Long number) {
        checkNotNull(number);
        checkNotNegative(number);
    }

    final void validateNumbers(final List<Long> numbers) {
        checkNotNull(numbers);
        checkNotEmpty(numbers);
    }

    private void checkNotNegative(final Long number) {
        if (number < 0) {
            throw new IllegalArgumentException("Offered numbers must be positive numbers!");
        }
    }

    private void checkNotNull(final Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Offered numbers must not be null!");
        }
    }

    private void checkNotEmpty(final List<Long> numbers) {
        if (numbers.isEmpty()) {
            throw new IllegalArgumentException("List of Offered numbers must not be empty!");
        }
    }
}
