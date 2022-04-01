package pl.gredel.flashcards.db.model;

public class Flashcard {
    private int id;
    private String title;
    private String question;
    private String answer;
    private int level;
    private boolean isPublic;
    Users user;

}
