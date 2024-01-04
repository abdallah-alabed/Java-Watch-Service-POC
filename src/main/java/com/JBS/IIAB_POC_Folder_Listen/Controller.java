package com.JBS.IIAB_POC_Folder_Listen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

@RestController
public class Controller {
    @Autowired
    WatchServiceConfig wsc;
    @GetMapping("/File/Read") // endpoint
    public String readFile(@RequestHeader String folderPath,@RequestHeader String fileName) throws IOException, InterruptedException { // Will Throw an error if the headers are not in the request with the same exact name
        StringBuilder sb = new StringBuilder();
        Boolean eventFound = wsc.run(String.valueOf(Paths.get(folderPath)),fileName);
        if(eventFound){ // if i have
            try {
                File myObj = new File(folderPath+File.separator+fileName); // define file location
                Scanner myReader = new Scanner(myObj); // read file line by line
                while (myReader.hasNextLine()) {
                    sb.append(myReader.nextLine());
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred."+ e.toString());
                e.printStackTrace();
            }
        }
        return sb.toString(); // output the data
    }
}
