package Naloga9;

public class FileElement {
    private Integer id;
    private int length;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }

    public FileElement(Integer id, int length) {
        this.id = id;
        this.length = length;
    }

    public FileElement(int length) {
        this.id = null;
        this.length = length;
    }
}
