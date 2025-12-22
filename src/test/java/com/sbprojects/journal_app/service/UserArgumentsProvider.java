package com.sbprojects.journal_app.service;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import com.sbprojects.journal_app.entity.User;


public class UserArgumentsProvider implements ArgumentsProvider{

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext arg0) throws Exception {
        return Stream.of(
            Arguments.of(User.builder().username("momo").password("momo").build()),
            Arguments.of(User.builder().username("lmao").password("").build())
        );
    }
    
}
