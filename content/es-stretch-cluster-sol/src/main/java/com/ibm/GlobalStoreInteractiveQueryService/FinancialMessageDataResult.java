package com.ibm.GlobalStoreInteractiveQueryService;


import java.util.Optional;
import java.util.OptionalInt;

import com.ibm.Model.FinancialMessage;

public class FinancialMessageDataResult {

    private static FinancialMessageDataResult NOT_FOUND =
            new FinancialMessageDataResult(null, null, null);

    private final FinancialMessage result;
    private final String host;
    private final Integer port;

    private FinancialMessageDataResult(FinancialMessage result, String host,
            Integer port) {
        this.result = result;
        this.host = host;
        this.port = port;
    }

    public static FinancialMessageDataResult found(FinancialMessage data) {
        return new FinancialMessageDataResult(data, null, null);
    }

    public static FinancialMessageDataResult foundRemotely(String host, int port) {
        return new FinancialMessageDataResult(null, host, port);
    }

    public static FinancialMessageDataResult notFound() {
        return NOT_FOUND;
    }

    public Optional<FinancialMessage> getResult() {
        return Optional.ofNullable(result);
    }

    public Optional<String> getHost() {
        return Optional.ofNullable(host);
    }

    public OptionalInt getPort() {
        return port != null ? OptionalInt.of(port) : OptionalInt.empty();
    }
}