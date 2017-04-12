package org.chatbot;

import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.SkypeBuilder;
import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.exceptions.ChatNotFoundException;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.exceptions.InvalidCredentialsException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.internal.chat.ChatGroup;
import com.samczsun.skype4j.user.Contact;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Dzianis_Haurylavets on 31/08/2016.
 */
public class Runner {

	private static Random random = new Random();

    public static void main(String[] args) throws ConnectionException, InvalidCredentialsException, ChatNotFoundException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type your login:");
        final String login = scanner.nextLine();
		System.out.println("Enter your password:");
        final String password = scanner.nextLine();
		System.out.printf("You type login: '%s' and password: '%s'.\n", login, password);

        final Skype skype = new SkypeBuilder(login, password).withAllResources().build();
        skype.login();
        skype.subscribe();

//        printAvailableContacts(skype);

        System.out.println("BOT STARTED");
        Chat chat = skype.getOrLoadChat("8:alen0044ka");
		startChat(scanner, chat);
        System.out.println("BOT STOPPED");

        scanner.close();
        skype.logout();
    }

	private static void startChat(Scanner scanner, Chat chat) throws ConnectionException {
		String messageText = "";
		while(!"kill".equals(messageText)){
			if(messageText.length() != 0) {
				Message message = createMessage(messageText);
				chat.sendMessage(message);

			}
			messageText = scanner.nextLine();
			System.out.println("type kill to stop bot");
		}
	}

	private static Message createMessage(String messageText) {
		String message = Arrays.stream(messageText.split(" "))
				.map(String::trim)
				.filter(str -> !str.isEmpty())
				.map(word -> String.format("<font color=\"0x%h\" align=\"right\"><b>%s</b></font> ", random.nextInt() & 0x00ffffff, word))
				.reduce("", String::concat) + "";

		return Message.fromHtml(message);
	}

	private static void printAvailableContacts(Skype skype) throws ConnectionException, ChatNotFoundException {
		System.out.println("Available groups: ");
		skype.loadMoreChats(2).stream()
				.filter(ch -> ch instanceof ChatGroup)
				.map(ch -> (ChatGroup) ch)
				.forEach(chat1 -> {
					System.out.println(") " + chat1.getTopic() + "  " + chat1.getIdentity() + "  " + chat1.isLoaded());
				});

		System.out.println("Available personal chats:");
		for(Contact contact : skype.getAllContacts()){
			System.out.println(contact.getFirstName() + " " + contact.getLastName() + " \t" + contact.getPrivateConversation().getIdentity());
		}
	}
}
