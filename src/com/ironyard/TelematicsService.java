package com.ironyard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class TelematicsService {
    public static void report(VehicleInfo vehicleInfo) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(vehicleInfo);
        String title = vehicleInfo.getVIN() + ".json";
        try {
            File file = new File(title);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        File file1 = new File(".");
        List<String> fileContents = new ArrayList<>();
        for (File carFile : file1.listFiles()) {
            if (carFile.getName().endsWith(".json")) {
                try {
                    Scanner fileScanner = new Scanner(carFile);
                    while (fileScanner.hasNext()) {
                        fileContents.add(fileScanner.nextLine());
                    }

                } catch (FileNotFoundException ex) {
                    System.out.println("Could not find file *" + carFile + "*");
                    ex.printStackTrace();
                }
            }
        }
        String [] cars = fileContents.toArray(new String[0]);

        int totalCars = 0;
        double totalMiles = 0.0;
        double totalGas = 0.0;
        double totalMilesSinceOil = 0.0;
        double totalEngine = 0.0;

        for (int i = 0; i < cars.length; i++) {
            try {
                VehicleInfo vi = mapper.readValue(cars[i], VehicleInfo.class);
                totalCars += 1;
                totalMiles += vi.getOdometer();
                totalGas += vi.getConsumption();
                totalMilesSinceOil += vi.getMilesSinceOil();
                totalEngine += vi.getEngineSize();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        double avMiles = totalMiles / totalCars;
        double avGas = totalGas / totalCars;
        double avMilesSinceOil = totalMilesSinceOil / totalCars;
        double avEngine = totalEngine / totalCars;

        System.out.println("Total Cars: " + totalCars);
        System.out.println("Average Miles: " + avMiles);

    };

}
