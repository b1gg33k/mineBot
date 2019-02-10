package com.b1gg33k;

import com.b1gg33k.services.CommandService;
import com.b1gg33k.utils.BotSentence;
import com.b1gg33k.utils.BotUtils;
import com.b1gg33k.utils.CommandNotFoundException;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.StatusType;

public class MineBot {
    private static final Character COMMAND_CHARACTER = '!';


    public static void main(String[] args){
        IDiscordClient discordClient = null;
        try {
            discordClient = BotUtils.getBuiltDiscordClient("NTQxNzA0NTA1MTIxODMzMDEw.DzkyIA.uOFTCEuOJNDtCVRewobTdtl9HZA");
            if (!discordClient.isLoggedIn()){
                discordClient.login();
            }

        } catch (Exception e){
            e.printStackTrace(System.err);
        }

        if (null != discordClient) {
            System.out.print("Connecting");
            for (int x=60; x>0;x--){
                if (discordClient.isReady()){
                    x=0;
                    continue;
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print('.');
                }
            }
            System.out.println();

            discordClient.changePresence(StatusType.ONLINE);

            for (IChannel channel : discordClient.getChannels(true)) {
                System.out.println("Channel: " + channel.getName());
                BotUtils.sendMessage(channel, "Hey you guys!");

                discordClient.getDispatcher().registerListener(new IListener() {
                    @Override
                    public void handle(Event event) {
                        if (event instanceof MessageReceivedEvent){
                            MessageReceivedEvent messageEvent = (MessageReceivedEvent) event;
                            IMessage message = messageEvent.getMessage();
                            if (message.getContent().length() > 0) {
                                if (message.getContent().toCharArray()[0] == COMMAND_CHARACTER) {
                                    try {
                                        //                                                             Start at substring index 1 to skip the command char.
                                        CommandService.getInstance().explicate(new BotSentence(message,message.getContent().substring(1)));
                                    } catch (CommandNotFoundException e) {
                                        message.reply("Unknown Command: " + e.getCommandName());
                                    }
                                }
                            }
                        }
                    }
                });
            }
        } else {
            System.err.println("Error connecting.");
        }
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        discordClient.changePresence(StatusType.OFFLINE);
//        System.exit(1);
    }
}
