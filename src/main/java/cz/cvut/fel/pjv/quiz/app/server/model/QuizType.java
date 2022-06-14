package cz.cvut.fel.pjv.quiz.app.server.model;

public enum QuizType {

    GUESSSONG("Guess the song"),  GUESSARTIST("Guess the artist"),  FINISHLYRIC("Finish the lyric"), SONGAS("Song association"), SINGBY("Sing a song"),
    TRIVIA("Trivia"),WHATSTHELYRIC("What's the lyric"),RANDOM("Random");

    private final String type;

    QuizType(String type) { this.type = type; }

    @Override
    public String toString() { return type; }

}