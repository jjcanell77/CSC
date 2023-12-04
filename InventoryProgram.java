import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class InventoryProgram {
    private List<Car> cars;
    private Scanner scanner;

    public InventoryProgram() {
        cars = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public class Car {
        private String make;
        private String model;
        private String color;
        private int year;
        private int mileage;
    
        public Car() {
        }
    
        public Car(String make, String model, String color, int year, int mileage) {
            this.make = make;
            this.model = model;
            this.color = color;
            this.year = year;
            this.mileage = mileage;
        }
    
        public String updateCar(String make, String model, String color, int year, int mileage) {
            try {
                if (make != null && !make.isEmpty()) this.make = make;
                if (model != null && !model.isEmpty()) this.model = model;
                if (color != null && !color.isEmpty()) this.color = color;
                if (!color.isEmpty() && year > 0) this.year = year;
                if (!color.isEmpty() && mileage > 0) this.mileage = mileage;            
                return "Vehicle updated successfully";
            } 
            catch (Exception e) {
                return "Failed to update vehicle: " + e.getMessage();
            }
        }
    
        public String[] listCarInfo() {
            try {
                if (make == null || model == null || color == null || year == 0 || mileage == 0) {
                    return new String[]{"No vehicle data available"};
                }
                return new String[]{make, model, color, String.valueOf(year), String.valueOf(mileage)};
            }
            catch (Exception e) {
                return new String[]{"Failed to list vehicle: " + e.getMessage()};
            }
        }
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("How can I help you today?");
            System.out.println("1. Add a new vehicle");
            System.out.println("2. Update an existing vehicle");
            System.out.println("3. Remove a vehicle");
            System.out.println("4. List vehicle information");
            System.out.println("5. Print all vehicles to a file");
            System.out.println("6. Exit");
            int choice = getValidatedIntegerInput();
            switch (choice) {
                case 1:
                    addCarProcedure();
                    break;
                case 2:
                    updateCarProcedure();
                    break;
                case 3:
                    removeCarProcedure();
                    break;
                case 4:
                    listCarProcedure();
                    break;
                case 5:
                    System.out.println("Enter the file path to print the information:");
                    printToFile("C:\\Users\\jjcan\\OneDrive\\Documents\\Autos.txt");
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option, please choose again.");
            }
        }
        scanner.close();
    }

    private void addCarProcedure() {
        try{
            System.out.println("Enter the make of the vehicle:");
            String make = scanner.nextLine();
            System.out.println("Enter the model of the vehicle:");
            String model = scanner.nextLine();
            System.out.println("Enter the color of the vehicle:");
            String color = scanner.nextLine();
            System.out.println("Enter the year of the vehicle:");
            int year = getValidatedIntegerInput();
            System.out.println("Enter the mileage of the vehicle:");
            int mileage = getValidatedIntegerInput();
            Car newCar = new Car(make, model, color, year, mileage);
            cars.add(newCar);
            System.out.println("Added Vehicle" + make);
            listAllCars();
        }
        catch (Exception e) {
            System.out.println("Failed to add vehicle: " + e.getMessage());
        }
    }
    
    private void updateCarProcedure() {
        try{
            if (cars.isEmpty()) {
            System.out.println("No vehicles to update.");
            return;
            }
            listAllCars();
            System.out.println("Select the index of the vehicle to update:");
            int index = getValidatedIntegerInput();
            if (index >= 0 && index < cars.size()) {
                Car car = cars.get(index);
                System.out.println("Enter the new make of the vehicle (or press Enter to keep current):");
                String make = scanner.nextLine();
                System.out.println("Enter the new model of the vehicle (or press Enter to keep current):");
                String model = scanner.nextLine();
                System.out.println("Enter the new color of the vehicle (or press Enter to keep current):");
                String color = scanner.nextLine();
                System.out.println("Enter the new year of the vehicle (or press Enter 0):");
                int year = getValidatedIntegerInput();
                System.out.println("Enter the new mileage of the vehicle (or press Enter 0):");
                int mileage = getValidatedIntegerInput();
                String result = car.updateCar(make, model, color, year, mileage);
                System.out.println("Updated to " + result);
                listAllCars();
            } 
        }
        catch (Exception e) {
            System.out.println("Failed to update vehicle: " + e.getMessage());
        }
    }

    private void removeCarProcedure() {
        try{
            if (cars.isEmpty()) {
                System.out.println("No vehicles to remove.");
                return;
            }
            listAllCars();
            System.out.println("Select the index of the vehicle to remove:");
            int index = getValidatedIntegerInput();
            if (index >= 0 && index < cars.size()) {
                cars.remove(index);
                System.out.println("Vehicle removed successfully.");
                listAllCars();
            }
        }
        catch (Exception e) {
            System.out.println("Failed to remove vehicle: " + e.getMessage());
        }
    }

    private void listCarProcedure() {
        try{
            if (cars.isEmpty()) {
                System.out.println("No vehicles available.");
                return;
            }
            listAllCars();
            System.out.println("Select the index of the vehicle to view detailed information:");
            int index = scanner.nextInt();
            scanner.nextLine();
            if (index >= 0 && index < cars.size()) {
                Car car = cars.get(index);
                String[] carInfo = car.listCarInfo();
                for (String info : carInfo) {
                    System.out.println(info);
                }
            } 
        }
        catch (Exception e) {
            System.out.println("Failed to remove vehicle: " + e.getMessage());
        }
        
    }

    private void listAllCars() {
        try{
            if (cars.isEmpty()) {
                System.out.println("No vehicles available.");
                return;
            }
            for (int i = 0; i < cars.size(); i++) {
                Car car = cars.get(i);
                String[] carInfo = car.listCarInfo();
                System.out.println("Car " + i + ":");
                for (String info : carInfo) {
                    System.out.println("\t" + info);
                }
                System.out.println();
            }
        }
        catch (Exception e) {
            System.out.println("Failed to list all vehicles: " + e.getMessage());
        } 
    }    

    private void printToFile(String filePath) {
        if (cars.isEmpty()) {
            System.out.println("No vehicles available to print.");
            return;
        }
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Car car : cars) {
                String[] carInfo = car.listCarInfo();
                for (String info : carInfo) {
                    writer.write(info + "\n");
                }
                writer.write("\n");
            }
            System.out.println("Information printed to " + filePath);
        } 
        catch (IOException e) {
            System.out.println("Failed to write to file: " + e.getMessage());
        }
    }

    private int getValidatedIntegerInput() {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("Please enter a number:");
        }
        int number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }
    
    public static void main(String[] args) {
        new InventoryProgram().start();
    }
}

