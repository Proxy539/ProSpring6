package com.apress.prospring6.fourteen.services;

/**
 * Thrown when a {@link CriteriaDto} passed to {@link SingerService#getByCriteriaDto(CriteriaDto)}
 * does not carry enough information to run a search.
 */
public class InvalidCriteriaException extends Exception {

    public InvalidCriteriaException(String message) {
        super(message);
    }
}
