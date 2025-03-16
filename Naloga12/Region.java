package Naloga12;

public class Region {
    private int area;
    private int upperFence;
    private int rightFence;
    private int lowerFence;
    private int leftFence;
    
    public int getArea() {
        return area;
    }
    public void setArea(int area) {
        this.area = area;
    }
    public int getUpperFence() {
        return upperFence;
    }
    public void setUpperFence(int upperFence) {
        this.upperFence = upperFence;
    }
    public int getRightFence() {
        return rightFence;
    }
    public void setRightFence(int rightFence) {
        this.rightFence = rightFence;
    }
    public int getLowerFence() {
        return lowerFence;
    }
    public void setLowerFence(int lowerFence) {
        this.lowerFence = lowerFence;
    }
    public int getLeftFence() {
        return leftFence;
    }
    public void setLeftFence(int leftFence) {
        this.leftFence = leftFence;
    }

    public Region() {
        this.area = 0;
        this.upperFence = 0;
        this.rightFence = 0;
        this.lowerFence = 0;
        this.leftFence = 0;
    }
}
