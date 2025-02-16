package Naloga9;

public class ListElement {
    private Integer id;
    
    public Integer getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public ListElement(int id) {
        this.id = id;
    }

    public ListElement() {
        this.id = null;
    }
}
