package CSC372.M4;
public class Cone extends Shape {
     private double radius;
     private double height;
 
     public Cone(double radius, double height) {
         this.radius = radius;
         this.height = height;
     }
     
     @Override
     public double surfaceArea() {
          //A=πr(r+sqaure(h^2+r^2))
          return Math.PI * radius * (radius + Math.sqrt(Math.pow(height, 2) + Math.pow(radius, 2)));     
     }
 
     @Override
     public double volume() {
          //V=πr^2(h/3)
          return Math.PI * Math.pow(radius, 2) * (height / 3);
     }
 
     @Override
     public String toString() {
          return "Cone with a radius of " + radius + 
               " and a height of " + height + 
               ": Surface Area: " + surfaceArea() + 
               ", Volume: " + volume();
     }
}