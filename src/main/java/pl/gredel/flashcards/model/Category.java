package pl.gredel.flashcards.model;
import pl.gredel.flashcards.db.dao.util.DataTransferObject;

public class Category implements DataTransferObject {
    private int id;
    private String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Category() {}

    public Category(int categoryId) {
        this.id = categoryId;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

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

}
