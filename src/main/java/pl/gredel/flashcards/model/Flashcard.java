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
    Category category;


    public Flashcard(String title, String question, String answer, int level, boolean isPublic, Users user, Category category) {
        this.title = title;
        this.question = question;
        this.answer = answer;
        this.level = level;
        this.isPublic = isPublic;
        this.user = user;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Flashcard{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", level=" + level +
                ", isPublic=" + isPublic +
                ", user=" + user.getLogin() +
                ", category=" + category.getName() +
                '}';
    }

    public Flashcard() {
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Flashcard(int id, String title, String question, String answer,
                     int level, boolean isPublic, Users user, Category category) {
        this.id = id;
        this.title = title;
        this.question = question;
        this.answer = answer;
        this.level = level;
        this.isPublic = isPublic;
        this.user = user;
        this.category = category;
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
