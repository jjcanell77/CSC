public class Milestone {
    public Car() {
        /* */
    }

    public Car(String make, String model, String color, int year, int mileage) {
            /*
            Define Car inner class attributes such as make, model, color, year, mileage
        */
    }

    public String[] listCarInfo() {
        /*
            Will return car atributes as an array of strings or 
            Will return message if there is no data for that Car
            */
    }

    public void InventoryProgram() {
        /*
            Will use a while-loop until the user chooses to exit
            Display a menu with options
            Get user's choice
            Will give the option to either 
                - Add a new car
                - Update an existing car
                - Remove a car
                - List a specific car information
                - Print all cars' information to a file
                - Exit
            */
    }

    private void addCar() {
        /*
            Will prompt user for car atributes
            Create a new car and add it to the list of cars
         */
    }
    
    public String updateCar(String make, String model, String color, int year, int mileage) {
        /*
            If there are no cars are avaialble to update then return to menu
            Display a list of cars for a user to choose
            Update car attributes if new values are entered 
            Update the selected car
            Return success or failure message 
        */
    }

    private void removeCar() {
        /*
            If there are no cars are avaialble to remove then rerurn to menu
            Display a list of cars for a user to choose
            Removes the selected car
        */
    }

    private void listCarProcedure() {
        /*
            If there are no cars are avaialble to see then return to menu
            Display a list of cars for a user to choose
            Diplay only selected car
            Return success or failure message 
        */
    }

    private void listAllCars() {
        /*
            If there are no cars are avaialble to see then return to menu
            Display a list of cars for a user to choose
        */
    }    

    private void printToFile(String filePath) {
        /*
            If there are no cars to print then return to menu
            Display a message if successfull
            File destination will be hard coded
        */
    }
}
