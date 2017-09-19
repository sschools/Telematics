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
        System.out.println(json);
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
                // You can use this to create a new instance of Scanner
            }
        }
        System.out.println("File Contents: " + fileContents);
    };

}
