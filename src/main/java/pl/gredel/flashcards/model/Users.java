package pl.gredel.flashcards.model;

import pl.gredel.flashcards.db.dao.util.DataTransferObject;

import java.util.List;

public class Users implements DataTransferObject{
    private int id;
    private String login;
    private String password;
    private String email;
    List<Flashcard> flashcards;
    List<Deck> decks;

    public Users() {

    }

    public List<Deck> getDecks() {
        return decks;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

    public Users(int id, String login, String password, String email,
                 List<Flashcard> flashcards, List<Deck> decks) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
