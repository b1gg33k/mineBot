package com.b1gg33k.commands;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;

@Retention(RUNTIME)
public @interface BotLexical {
    String command();
}
