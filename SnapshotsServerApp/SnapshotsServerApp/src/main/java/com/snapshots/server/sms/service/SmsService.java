package com.snapshots.server.sms.service;

import com.snapshots.server.db.model.Snapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private Logger logger = LoggerFactory.getLogger(SmsService.class);

    public void sendMMS(Snapshot snapshot, String phoneNumber){
        /*
            CODE FOR SENDING MMS
         */

        logger.info("MMS has been send to "+phoneNumber);
    }
}
