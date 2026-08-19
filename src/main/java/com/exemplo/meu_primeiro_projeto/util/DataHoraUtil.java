package com.exemplo.meu_primeiro_projeto.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public final class DataHoraUtil {

    private DataHoraUtil() { }

    public static LocalDateTime agora() {
        return LocalDateTime.now(
            ZoneId.of("America/Sao_Paulo")
        );
    }
}
