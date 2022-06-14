package cz.cvut.fel.pjv.quiz.app.server.model;

public enum Decade {

    D1960s("60s"),  D1970s("70s"),  D1980s("80s"), D1990s("90s"), D2000s("00s"), D2010s("10s"), D2020s("20s"),RANDOM("Random");

    private final String decade;

    Decade(String decade) { this.decade = decade; }

    @Override
    public String toString() { return decade; }

}

