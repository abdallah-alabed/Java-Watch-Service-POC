package com.JBS.IIAB_POC_Folder_Listen.WatchService;

import java.io.IOException;

import static com.JBS.IIAB_POC_Folder_Listen.WatchService.WatchService.printFileData;

/**
 * This Custom Thread is used for each watch key to run the process of extracting file data alone in a thread
 * for better performance and prevent any race conditions
 */
public class CustomThread implements Runnable{
    private volatile String fileData; // The Data in file
    private volatile String filePath; // The file Path
    public CustomThread(String filePath) {
        this.filePath = filePath;
    }
    @Override
    public void run() {
        try {
            fileData = printFileData(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String getFileData() {
        return fileData;
    }
}
