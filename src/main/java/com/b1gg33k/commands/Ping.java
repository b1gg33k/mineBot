package com.b1gg33k.commands;

import sx.blah.discord.handle.obj.IMessage;

@BotLexicon()
public class Ping {

    @BotLexical(command = "ping")
    public void pong(IMessage message){
        message.reply("pong");
    }



    @BotLexical(command = "hello")
    public void hello(IMessage message){
        message.reply("Hello Back!");
    }
}
