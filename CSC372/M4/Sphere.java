package CSC372.M4;
public class Sphere extends Shape {
    private double radius;

    public Sphere(double radius) {
        this.radius = radius;
    }

    @Override
    public double surfaceArea() {
        //A=4πr^2
        return 4 * Math.PI * Math.pow(radius, 2);
    }

    @Override
    public double volume() {
        //V=(4/3)πr^3
        return (4.0/3) * Math.PI * Math.pow(radius, 3);
    }

    @Override
    public String toString() {
        return "Sphere with a radius of " + radius + 
            ": Surface Area: " + surfaceArea() + 
            ", Volume: " + volume();
    }
}
