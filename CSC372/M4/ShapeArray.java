package CSC372.M4;
public class ShapeArray {
    public static void main(String[] args) {
        Shape[] shapeArray = new Shape[3];
        shapeArray[0] = new Sphere(4.5);
        shapeArray[1] = new Cylinder(3.5, 5.5);
        shapeArray[2] = new Cone(2.5, 4.5);

        for (Shape shape : shapeArray) {
            System.out.println(shape.toString());
        }
    }
}    