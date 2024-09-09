package ru.tbank.parser;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.util.function.BiFunction;

@Slf4j
public class Parser<T> extends AbstractParser<T> {

    private final BiFunction<Logger, T, T> handleFunction;

    public Parser(Class<T> parsingClass, BiFunction<Logger, T, T> handleFunction) {
        super(parsingClass);
        this.handleFunction = handleFunction;
    }

    @Override
    protected T handle(T model) {
        return handleFunction.apply(log, model);
    }
}