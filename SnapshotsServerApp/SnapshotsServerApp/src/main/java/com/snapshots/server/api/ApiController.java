package com.snapshots.server.api;

import com.snapshots.server.db.DbController;
import com.snapshots.server.db.model.Snapshot;
import com.snapshots.server.sms.SmsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class ApiController {

    private Logger logger = LoggerFactory.getLogger(ApiController.class);
    private DbController dbController;
    private SmsController smsController;

    @Autowired
    public ApiController(DbController dbController, SmsController smsController){
        this.dbController = dbController;
        this.smsController = smsController;
    }

    @PostMapping("/uploadUrl")
    public void receiveSnapshots(@RequestParam String location, @RequestParam String timestamp, @RequestBody MultipartFile file) {
        Snapshot snapshot = new Snapshot(file.getName(), timestamp, location);
        File saveFile = new File("./upload-dir/"+file.getOriginalFilename());

        dbController.saveSnapshot(snapshot);

        try {
            file.transferTo(saveFile);
        } catch (IllegalStateException | IOException e) {
            logger.error(e.toString());
        }
    }

    @PostMapping("/getMMS")
    public void sendSmsReciever(@RequestParam String phoneNumber, @RequestParam String location){

    }
}
