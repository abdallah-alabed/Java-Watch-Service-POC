package com.JBS.IIAB_POC_Folder_Listen.Controller;

import com.JBS.IIAB_POC_Folder_Listen.WatchService.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Paths;

@RestController
public class SwiftController {
    @Autowired
    WatchService wsc;
    @GetMapping("/Transaction/File/Read") // endpoint
    public void readFile(@RequestHeader String folderPath) throws IOException, InterruptedException {
        wsc.run(String.valueOf(Paths.get(folderPath)));
    }
}
