package pl.gredel.flashcards.model;

import pl.gredel.flashcards.db.dao.util.DataTransferObject;

import java.util.List;

public class Deck implements DataTransferObject {
    private int id;
    private String name;
    Users user;
    List<Flashcard> flashcards;

    public Deck(int id, String name, Users user, List<Flashcard> flashcards) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.flashcards = flashcards;
    }

    public Deck() {

    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }
}
