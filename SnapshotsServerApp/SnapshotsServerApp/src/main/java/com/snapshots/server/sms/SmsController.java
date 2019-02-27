package com.snapshots.server.sms;

import com.snapshots.server.db.DbController;
import com.snapshots.server.db.model.Snapshot;
import com.snapshots.server.sms.service.SmsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsController {

    private SmsService smsService;
    private DbController dbController;


    public SmsController (SmsService smsService, DbController dbController){
        this.smsService = smsService;
        this.dbController = dbController;
    }

    public void sendMMS(String location, String phoneNumber){
        /*
            Getting the latest snapshot for a particular camera and send MMS
         */

        List<Snapshot>list = dbController.getAllSnapshotsForCamera(location);
        Snapshot snapshot = list.get(list.size()-1);

        smsService.sendMMS(snapshot, phoneNumber);
    }
}
