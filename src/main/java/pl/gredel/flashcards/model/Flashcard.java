package pl.gredel.flashcards.model;

import pl.gredel.flashcards.db.dao.util.DataTransferObject;

public class Flashcard implements DataTransferObject {
    private int id;
    private String title;
    private String question;
    private String answer;
    private int level;
    private boolean isPublic;
    Users user;

    public Flashcard(int id, String title, String question, String answer, int level, boolean isPublic, Users user) {
        this.id = id;
        this.title = title;
        this.question = question;
        this.answer = answer;
        this.level = level;
        this.isPublic = isPublic;
        this.user = user;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
