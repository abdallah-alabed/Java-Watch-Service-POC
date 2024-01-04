package com.JBS.IIAB_POC_Folder_Listen;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

@Component
public class WatchServiceConfig {
    private WatchService watchService;
    private Path path;
    public boolean run(String pathToFolder , String fileName) throws IOException, InterruptedException {
        Boolean fileChangeFlag = false; // This flag will check if the event of creation is equal to my target file name.
        watchService = FileSystems.getDefault().newWatchService(); // create the watch service
        path = Paths.get(pathToFolder); // set folder path from the request header
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE); // Set the watch service to handle only creation actions
        WatchKey key; // registration object of a watch service in directory (when i watch something it creates a key for this watch)
        // This line of Code will lock the thread for indefinite amount of time [[ key = watchService.take() ]] so we should replace it with the following:
        while ( (key = watchService.poll(10, TimeUnit.SECONDS)) != null) { // define the wait time on the thread to detect the change
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println("Event kind:" + event.kind()+ ". File affected: " + event.context() + ".");
                if(fileName.equals(event.context().toString())){
                    fileChangeFlag = true; // set flag for true only if the created file have a name equal to my target file name sent in the request header
                }
            }
            key.reset();
            if(fileChangeFlag) {
                break;
            }
        }
        return fileChangeFlag;
    }
}
