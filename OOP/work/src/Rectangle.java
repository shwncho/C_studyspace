public class Rectangle {
    private int width;
    private int height;
    private int area;
    
    public void setDimensions(int newWidth, int newHeight){
        width = newWidth;
        height = newHeight;
        area = width*height;
    }
    public int getArea(){
        return area;
    }

    public static void main(String[] args) {
        Rectangle box = new Rectangle();
        box.setDimensions(10,5);
        System.out.println("box.getArea() = " + box.getArea());
    }
}
