package org.example;

public class KnockKnockProtocol {
    private static final int WAITING = 0;
    private static final int SENT_KNOCK_KNOCK = 1;
    private static final int SENT_CLUE = 2;
    private static final int ANOTHER = 3;

    private int state = WAITING;
    private int currentJoke = 0;

    private final String[] clues = {
            "Turnip",
            "Little Old Lady",
            "Atch",
            "Who",
            "Who"
    };

    private final String[] answers = {
            "Turnip the heat, it's cold in here!",
            "I didn't know you could yodel!",
            "Bless you!",
            "Is there an owl in here?",
            "Is there an echo in here?"
    };

    public String processInput(String input) {
        String output = null;

        if (state == WAITING) {
            output = "Knock! Knock!";
            state = SENT_KNOCK_KNOCK;
        } else if (state == SENT_KNOCK_KNOCK) {
            if (input.equalsIgnoreCase("Who's there?")) {
                output = clues[currentJoke];
                state = SENT_CLUE;
            } else {
                output = "You're supposed to say \"Who's there?\"! Try again. Knock! Knock!";
            }
        } else if (state == SENT_CLUE) {
            if (input.equalsIgnoreCase(clues[currentJoke] + " who?")) {
                output = answers[currentJoke] + " Want another joke? (y/n)";
                state = ANOTHER;
            } else {
                output = "You're supposed to say \"" + clues[currentJoke] + " who?\"" + "! Try again. Knock! Knock!";
                state = SENT_KNOCK_KNOCK;
            }
        } else if (state == ANOTHER) {
            if (input.equalsIgnoreCase("y")) {
                output = "Knock! Knock!";
                if (currentJoke == 4)
                    currentJoke = 0;
                else
                    currentJoke++;
                state = SENT_KNOCK_KNOCK;
            } else {
                output = "Bye.";
                state = WAITING;
            }
        }

        return output;
    }
}