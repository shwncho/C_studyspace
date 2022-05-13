public class ShapeDemo {
    public static void main(String[] args) {
        TriangleInterface tri = new Triangle(12,30);
        tri.drawHere();
        RectangleInterface box = new Rectangle(17,8,20);
        box.drawHere();

//        box.set(5,5);
//        box.setOffset(10);
//        box.drawAt(2);


    }
}
