package com.github.codingsoldier.example.bootweb.common;

import com.github.codingsoldier.common.token.TokenInterface;
import org.springframework.stereotype.Component;

@Component
public class TokenImpl implements TokenInterface<Long> {

    @Override
    public Long getUserId() {
        return 10L;
    }

}
