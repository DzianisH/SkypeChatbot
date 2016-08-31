package org.chatbot;

import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.SkypeBuilder;
import com.samczsun.skype4j.events.EventHandler;
import com.samczsun.skype4j.events.Listener;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.exceptions.InvalidCredentialsException;
import com.samczsun.skype4j.exceptions.NotParticipatingException;

/**
 * Created by Dzianis_Haurylavets on 31/08/2016.
 */
public class Runner {
    public static void main(String[] args) throws ConnectionException, InvalidCredentialsException, NotParticipatingException, InterruptedException {
        Skype skype = new SkypeBuilder("dzianis_haurylavets", "Ches_29121").withAllResources().build();
        skype.login();
        skype.getEventDispatcher().registerListener(new Listener() {
            @EventHandler
            public void onMessage(MessageReceivedEvent e) {
                System.out.println("Got message: " + e.getMessage().getContent());
            }
        });
        skype.subscribe();
// Do stuff
        System.out.println("START");
        Thread.sleep(50000L);
        System.out.println("END");
        skype.logout();
    }
}
