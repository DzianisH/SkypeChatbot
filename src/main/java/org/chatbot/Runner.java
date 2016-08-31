package org.chatbot;

import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.SkypeBuilder;
import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.events.EventHandler;
import com.samczsun.skype4j.events.Listener;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.exceptions.ChatNotFoundException;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.exceptions.InvalidCredentialsException;
import com.samczsun.skype4j.exceptions.NotParticipatingException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.formatting.Text;
import com.samczsun.skype4j.user.Contact;

import java.util.Objects;
import java.util.Scanner;

/**
 * Created by Dzianis_Haurylavets on 31/08/2016.
 */
public class Runner {
    static {

    }

    public static void main(String[] args) throws ConnectionException, InvalidCredentialsException, ChatNotFoundException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Login?");
        final String login = scanner.nextLine().toLowerCase();
        System.out.println("password?");
        final String password = scanner.nextLine();

        final Skype skype = new SkypeBuilder(login, password).withAllResources().build();
        skype.login();
        skype.getEventDispatcher().registerListener(new Listener() {
            @EventHandler
            public void onMessage(MessageReceivedEvent e) {
//                String message = e.getMessage().getContent().asPlaintext();
//                System.out.printf("Got message: '%s' in room '%s'", message, e.getMessage().getChat().getIdentity());

                try {
                    Chat chat = skype.getOrLoadChat(e.getMessage().getChat().getIdentity());
                    chat.sendMessage(Message.create().with(Text.plain("I'm not available now. "
                            + "I will reply you as soon as I come back next morning.")));
                } catch (ConnectionException | ChatNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });
        skype.subscribe();

//        for(Contact contact : skype.getAllContacts()){
//            System.out.println(contact.getFirstName() + " " + contact.getLastName());
//            System.out.println(contact.getPrivateConversation().getIdentity());
//        }
//        Chat chat = skype.getOrLoadChat("8:dzmitry_balbosau");
//        chat.sendMessage(Message.create().with(Text.plain("Я бот, я начал работу")));

//        while(scanner.hasNext()) scanner.next();

        System.out.println("BOT STARTED");
        do{
            System.out.println("type kill to stop bot");
        }while(!"kill".equals(scanner.nextLine()));
        System.out.println("BOT STOPPED");

        skype.logout();
    }
}
