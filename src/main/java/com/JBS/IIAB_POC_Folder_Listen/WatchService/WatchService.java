package com.JBS.IIAB_POC_Folder_Listen.WatchService;

import com.JBS.IIAB_POC_Folder_Listen.DAO.JdbcImpl;
import com.JBS.IIAB_POC_Folder_Listen.Model.SwiftTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;

@Service
public class WatchService {
    @Autowired
    JdbcImpl jdbc;

    /**
     * Create a Watch Service and Sign it to a directory and keep tracking any new creation actions inside the directory
     * when there's a trigger we run the data extraction process on a separate thread then upload the data on Database
     * @param pathToFolder: The path to the folder to register under watch
     * @throws IOException
     * @throws InterruptedException
     */
    public void run(String pathToFolder) throws IOException, InterruptedException {
        java.nio.file.WatchService watchService = FileSystems.getDefault().newWatchService(); // create the watch service
        Path path = Paths.get(pathToFolder); // set folder path from the request header
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE); // Set the watch service to handle only creation actions
        WatchKey key; // registration object of a watch service in directory (when I watch something it creates a key for this watch)
        while ((key = watchService.take()) != null) { // define the wait time on the thread to detect the change
            for (WatchEvent<?> event : key.pollEvents()) {
                String fileData;
                //SOLUTION 1
//                Thread.sleep(1);
//                fileData = printFileData(pathToFolder + File.separator + event.context());
                //SOLUTION 2
                CustomThread ct = new CustomThread(pathToFolder+File.separator + event.context());
                Thread t1 = new Thread(ct);
                t1.start();
                t1.join();
                fileData = ct.getFileData();
                // Add To Database
                SwiftTransaction swift = new SwiftTransaction(event.context().toString(), fileData);
                uploadToDatabase(swift);
                System.out.println("File affected: " + event.context() + "   ,File Data:  " + fileData);

            }
            key.reset();
        }
    }
    /**
     * Send The Swift Transaction Object to the Persistent Layer to Perform Insertion Query
     * @param swift: Swift transaction object
     */
    public void uploadToDatabase(SwiftTransaction swift){
        jdbc.addTransaction(swift);
    }
    /**
     * Use FileReader and BufferReader to Read File
     * StringBuilder Gather the Text Line By Line To return The File Data as a String
     * @param filePath: File path to read its data
     * @return The file Data as a String
     * @throws IOException
     */
    public static String printFileData(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder(0); // Collect Full Text
        FileReader fr = new FileReader(filePath); // Read The File
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        fr.close();
        return sb.toString();
    }
}
