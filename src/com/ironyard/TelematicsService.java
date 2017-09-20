package com.ironyard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
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

        DecimalFormat df = new DecimalFormat("#.0");

        String avMiles = df.format(totalMiles / totalCars);
        String avGas = df.format(totalGas / totalCars);
        String avMilesSinceOil = df.format(totalMilesSinceOil / totalCars);
        String avEngine = df.format(totalEngine / totalCars);

        try {
            File file = new File("dashboard.html");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("<html>\n");
            fileWriter.write("<title>Vehicle Telematics Dashboard</title>\n");
            fileWriter.write("<body>\n");
            fileWriter.write("<h1 align=\"center\">Averages for " + totalCars + " vehicles</h1>\n");
            fileWriter.write("<table align=\"center\">\n" +
                    "        <tr>\n" +
                    "            <th>Odometer (miles) |</th><th>Consumption (gallons) |</th><th>Last Oil Change |</th><th>Engine Size (liters)</th>\n" +
                    "        </tr>\n" +
                    "        <tr>\n");
            fileWriter.write("<td align=\"center\">" + avMiles + "</td><td align=\"center\">" +avGas + "</td><td align=\"center\">" + avMilesSinceOil + "</td align=\"center\"><td align=\"center\">" + avEngine + "</td>");
            fileWriter.write("</tr>\n" +
                    "    </table>\n" +
                    "    <h1 align=\"center\">History</h1>\n" +
                    "    <table align=\"center\" border=\"1\">\n" +
                    "        <tr>\n" +
                    "            <th>VIN</th><th>Odometer (miles)</th><th>Consumption (gallons)</th><th>Last Oil Change</th><th>Engine Size (liters)</th><th>Gas Mileage</th>\n" +
                    "        </tr>\n");
            for (int i = 0; i < cars.length; i++) {
                try {
                    VehicleInfo vi = mapper.readValue(cars[i], VehicleInfo.class);
                    fileWriter.write("<tr>\n" +
                            "            <td align=\"center\">"+vi.getVIN()+"</td><td align=\"center\">"+vi.getOdometer()+"</td><td align=\"center\">"+vi.getConsumption()+"</td><td align=\"center\">"+vi.getMilesSinceOil()+"</td align=\"center\"><td align=\"center\">"+vi.getEngineSize()+"</td><td align=\"center\">"+vi.milesPerGallon()+"</td\n" +
                            "        </tr>\n");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            fileWriter.write("</table>\n" +
                    "  </body>\n" +
                    "</html>");
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    };

}
