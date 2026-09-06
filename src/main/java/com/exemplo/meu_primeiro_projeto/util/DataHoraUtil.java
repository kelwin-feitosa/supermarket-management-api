package com.exemplo.meu_primeiro_projeto.util;

import java.time.OffsetDateTime;
import java.time.ZoneId;

public final class DataHoraUtil {

    private DataHoraUtil() { }

    public static OffsetDateTime agora() {
        return OffsetDateTime.now(
                ZoneId.of("America/Sao_Paulo")
        );
    }
}
