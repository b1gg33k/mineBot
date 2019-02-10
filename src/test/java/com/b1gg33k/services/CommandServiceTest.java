package com.b1gg33k.services;

import com.b1gg33k.utils.CommandNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

import static org.junit.Assert.*;

public class CommandServiceTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getInstance() {
        CommandService commandService = CommandService.getInstance();
        assertNotNull(commandService);
    }

    @Test
    public void explicate() throws CommandNotFoundException {
        CommandService commandService = CommandService.getInstance();
        IMessage message = mock(IMessage.class);
        when(message.getClient()).thenReturn(mock(IDiscordClient.class));

        //Test ping -> Pong
        when(message.getContent()).thenReturn("!ping");
        commandService.explicate(message);
        verify(message).reply("pong");

        when(message.getContent()).thenReturn("!hello");
        commandService.explicate(message);
        verify(message).reply("Hello Back!");

    }

    @Test
    public void findCommandMethod() {
    }
}