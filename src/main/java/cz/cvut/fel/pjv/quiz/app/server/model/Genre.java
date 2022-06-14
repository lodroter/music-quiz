package cz.cvut.fel.pjv.quiz.app.server.model;

public enum Genre {

    POP("Pop"),  RAP("HipHop & Rap"),  ROCK("Rock & Metal"), TECHNO("Techno/Electro"), COUNTRY("Country"), CZSK("CZ/SK"), RANDOM("Random");

    private final String genre;

    Genre(String genre) { this.genre = genre; }

    @Override
    public String toString() { return genre; }

}

