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
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.formatting.Text;

import java.util.Scanner;

/**
 * Created by Dzianis_Haurylavets on 31/08/2016.
 */
public class Runner {
    static {

    }

    public static void main(String[] args) throws ConnectionException, InvalidCredentialsException, ChatNotFoundException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type your login:");
        final String login = scanner.nextLine().toLowerCase();
        System.out.println("Enter your password:");
        final String password = scanner.nextLine();

        final Skype skype = new SkypeBuilder(login, password).withAllResources().build();
        skype.login();
        skype.getEventDispatcher().registerListener(new Listener() {
            @EventHandler
            public void onMessage(MessageReceivedEvent e) {
                String message = e.getMessage().getContent().asPlaintext();
                System.out.printf("Got message: '%s' in room '%s'\n", message, e.getMessage().getChat().getIdentity());
                try {
                    if(message != null) {
                        final String msg = message.toLowerCase();
                        final Chat chat = skype.getOrLoadChat(e.getMessage().getChat().getIdentity());
                        //django
                        if ("19:fd06b03bb2b34bb9b303b7966fb8c740@thread.skype".equals(e.getMessage().getChat().getIdentity())) {
                            if (msg.contains("дима") || msg.contains("димка")) {
                                chat.sendMessage(Message.create().with(Text.plain("Димка? да, да, помню, это тот самый непингуемый что ли?")));
                            } else if (msg.contains("андрей") || msg.contains("андрос") || msg.contains("желез")) {
                                chat.sendMessage(Message.create().with(Text.plain("Андрос? это тот пыхарь что ли?")));
                            } else if(msg.contains("рыжий")){
                                String senderName = e.getMessage().getSender().getUsername();
                                chat.sendMessage(Message.fromHtml("<font color=\"0xee6622\" align=\"right\">                         "
                                        + "<b>Дениска</b> слегка взгрустнул из-за последнего сообщения " + senderName
                                        + ". Не обижайте Дениску ;c </font>"));
                            }
                        // Lenina group
                        } else if ("19:6ed99fad28304c50a5110ef5a5c4ded7@thread.skype".equals(e.getMessage().getChat().getIdentity())) {
                            if(msg.contains("рыжий") || msg.contains("денис")){
                                String senderName = e.getMessage().getSender().getUsername();
                                chat.sendMessage(Message.fromHtml("<font color=\"0xee6622\" align=\"right\">                         "
                                        + "<b>Дениска</b> слегка взгрустнул из-за последнего сообщения " + senderName
                                        + ". Не обижайте Дениску ;c </font>"));
                            }
                        }
                    }
                } catch (ConnectionException | ChatNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

//        for(Contact contact : skype.getAllContacts()){
//            System.out.println(contact.getFirstName() + " " + contact.getLastName());
//            System.out.println(contact.getPrivateConversation().getIdentity());
//        }
//        Chat chat = skype.getOrLoadChat("8:dzmitry_balbosau");
//        chat.sendMessage(Message.create().with(Text.plain("Я бот, я начал работу")));

//        while(scanner.hasNext()) scanner.next();

        System.out.println("BOT STARTED");
        Chat chat = skype.getOrLoadChat("19:6ed99fad28304c50a5110ef5a5c4ded7@thread.skype");
        String messageText = "Hello World!";
        while(!"kill".equals(messageText)){
            Message message = Message.fromHtml("<font color=\"0xee6622\" align=\"right\">" + messageText + "</font>");
            chat.sendMessage(message);
            messageText = scanner.nextLine();
            System.out.println("type kill to stop bot");
        }
        System.out.println("BOT STOPPED");

        skype.logout();
    }
}
