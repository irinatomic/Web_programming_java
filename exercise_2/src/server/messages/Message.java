package server.messages;

import server.ServerRepository;

import java.time.LocalDateTime;
import java.util.Set;

public class Message {

    private LocalDateTime timestamp;
    private String sender;
    private String content;

    public Message(String sender, String content) {
        this.timestamp = LocalDateTime.now();
        this.sender = sender;
        this.content = censureContent(content);
    }

    private String censureContent(String text) {
        Set<String> censoredWords = ServerRepository.censured;
        String[] words = text.split("\\s+");
        StringBuilder output = new StringBuilder();

        for (String word : words) {
            if (censoredWords.contains(word.toLowerCase()))
                output.append(censureWord(word));
            else
                output.append(word);
            output.append(" ");
        }

        return output.toString().trim();
    }

    private String censureWord(String word) {
        return word.charAt(0) + "*".repeat(word.length() - 2) + word.charAt(word.length() - 1);
    }

    @Override
    public String toString() {
        return timestamp + " - " + sender + ": " + content;
    }
}
