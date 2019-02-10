package com.b1gg33k.utils;

import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MessageTokenizer;

public class BotSentence {
    private IMessage message = null;
    private MessageTokenizer tokenizer;

    public BotSentence(IMessage message, String sentence) {
        //TODO: Figure out a better way to decide if we drop the COMMAND_CHARACTER
        this.message = message;
        this.tokenizer = new MessageTokenizer(message.getClient(),sentence);
    }

    public BotSentence(IMessage message, MessageTokenizer tokenizer) {
        this.message = message;
        this.tokenizer = tokenizer;
    }

    private BotSentence() {
    }

    public IMessage getMessage() {
        return message;
    }

    public BotSentence setMessage(IMessage message) {
        this.message = message;
        return this;
    }

    public MessageTokenizer getTokenizer() {
        return tokenizer;
    }

    public BotSentence setTokenizer(MessageTokenizer tokenizer) {
        this.tokenizer = tokenizer;
        return this;
    }
}
