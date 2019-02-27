package cam.imitation.messager;

import cam.imitation.db.DbController;
import cam.imitation.db.model.Snapshot;
import cam.imitation.messager.service.MessagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class MessageManager {

    private static final Logger logger = LoggerFactory.getLogger(MessageManager.class);

    @Autowired
    private DbController dbController;

    @Autowired
    private MessagerService messagerService;

    public void initSending(){
        /*
            Initialize sending of all files
        */
        messagerService.beginSending(dbController.getAllSnapshots(), this);
        logger.info("Sending files has started");
    }

    public void stopSending(){
        /*
            Stops any sending process
        */
        messagerService.stopSending();
    }

    public void addSnapshotToSendingPool(Snapshot snapshot){
        /*
            Add snapshot to sending poll
        */
        messagerService.sendFile(snapshot);
    }

    public void stopSendingSnapshot(Snapshot snapshot){
        /*
            Stops sending snapshot
        */
        messagerService.stop(snapshot);
    }
}
