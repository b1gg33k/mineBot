package com.b1gg33k.commands;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * This annotation is to be used on an a method used to respond to a _command_ help .
 */
@Retention(RUNTIME)
public @interface BotLexicalHelp {
    /**
     * What command to provide help for
     * @return
     */
    String command();
}
