package com.b1gg33k.utils;

public class CommandNotFoundException extends Exception {

    public CommandNotFoundException(String commandName) {
        this.commandName = commandName;
    }

    public CommandNotFoundException(String message, String commandName) {
        super(message);
        this.commandName = commandName;
    }

    public CommandNotFoundException(String message, Throwable cause, String commandName) {
        super(message, cause);
        this.commandName = commandName;
    }

    public CommandNotFoundException(Throwable cause, String commandName) {
        super(cause);
        this.commandName = commandName;
    }

    public CommandNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String commandName) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.commandName = commandName;
    }

    private String commandName;

    public String getCommandName() {
        return commandName;
    }

    public CommandNotFoundException setCommandName(String commandName) {
        this.commandName = commandName;
        return this;
    }
}
