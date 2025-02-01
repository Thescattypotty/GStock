package org.gestionstock.stock.Security.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service // in real world i should use a db (like redis) to store the blacklisted tokens
// but for now its fine
@Slf4j
public class BlackListService {
    private final Set<String> blackListedTokens = ConcurrentHashMap.newKeySet();

    public void addTokenToBlackList(String token) {
        log.info("Token added to black list: {}", token);
        blackListedTokens.add(token);
    }

    public boolean isTokenBlackListed(String token) {
        log.info("Checking if token is blacklisted: {}", token);
        return blackListedTokens.contains(token);
    }
}
