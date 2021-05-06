package ru.geekbrains.lesson6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.stream.Stream;

public class JUnitHomeTaskTest {
    @ParameterizedTest
    @NullAndEmptySource
    void shouldThrowIllegalArgumentExceptionWhenEmptyArrayPassed(int[] emptyArray) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> JUnitHomeTask.extractAfterFour(emptyArray));
    }

    @Test
    void shouldThrowRuntimeExceptionWhenNoFourFoundInArray() {
        Assertions.assertThrows(RuntimeException.class, () -> JUnitHomeTask.extractAfterFour(new int[]{1}));
    }

    @ParameterizedTest
    @MethodSource("validArrayParameterProvider")
    void shouldCheckValidSubArray(int[] inputArray, int[] expected) {
        Assertions.assertArrayEquals(expected, JUnitHomeTask.extractAfterFour(inputArray));
    }

    private static Stream<Arguments> validArrayParameterProvider() {
        return Stream.of(
                Arguments.of(new int[]{4, 1}, new int[]{1}),
                Arguments.of(new int[]{1, 2, 4, 4, 2, 1, 2, 4, 1, 7}, new int[]{1, 7}),
                Arguments.of(new int[]{1, 2, 3, 4, 2, 5, 2, 3}, new int[]{2, 5, 2, 3})
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldThrowIllegalArgumentExceptionWhenEmptyArrayPassedToOneFourMethod(int[] emptyArray) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> JUnitHomeTask.findOneAndFourInArray(emptyArray));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenArrayContainsOtherDigitsThenOneAndFour() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> JUnitHomeTask.findOneAndFourInArray(new int[]{2, 1, 4, 3, 5, 2}));
    }

    @ParameterizedTest
    @MethodSource("validOneFourArrayProvider")
    void shouldCheckValidOneFourArray(int[] inputArray, boolean expected) {
        Assertions.assertEquals(expected, JUnitHomeTask.findOneAndFourInArray(inputArray));
    }

    private static Stream<Arguments> validOneFourArrayProvider() {
        return Stream.of(
                Arguments.of(new int[]{1, 1, 4, 4, 1, 4, 1}, true),
                Arguments.of(new int[]{1, 1, 4}, true),
                Arguments.of(new int[]{1, 1, 1}, false),
                Arguments.of(new int[]{4, 4, 4}, false)
        );
    }
}
