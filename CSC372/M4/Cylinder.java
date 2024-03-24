package CSC372.M4;
public class Cylinder extends Shape {
     private double radius;
     private double height;
 
     public Cylinder(double radius, double height) {
          this.radius = radius;
          this.height = height;
     }
 
     @Override
     public double surfaceArea() {
          //A=2πrh+2πr^2
          return 2 * Math.PI * radius * height + 2 * Math.PI * Math.pow(radius, 2);
     }
 
     @Override
     public double volume() {
          //V=πr^2h
          return Math.PI * Math.pow(radius, 2) * height;
     }
 
     @Override
     public String toString() {
          return "Cylinder with a radius of " + radius + 
               " and a height of " + height + 
               ": Surface Area: " + surfaceArea() + 
               ", Volume: " + volume();
     }
 }