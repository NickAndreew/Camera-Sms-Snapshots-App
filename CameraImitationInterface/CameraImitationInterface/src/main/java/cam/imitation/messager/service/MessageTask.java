package cam.imitation.messager.service;

import cam.imitation.db.model.Snapshot;
import cam.imitation.messager.MessageManager;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.jsoup.Jsoup;
import org.springframework.transaction.annotation.Transactional;

public class MessageTask extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(MessageTask.class);
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH:MM:SS");

    private Snapshot snapshot;
    private MessageManager messageManager;

    public MessageTask(Snapshot snapshot, MessageManager messageManager) {
        this.snapshot = snapshot;
        this.messageManager = messageManager;
    }

    @Override
    @Transactional
    public void run(){
        logger.info("Sending "+snapshot.getFileName()+" to Snapshot Server App");

        String url = "http://localhost:8080/uploadUrl";
        File file = new File("./upload-dir/"+snapshot.getFileName());

        try {
            Document document = Jsoup.connect(url)
                .data("location", snapshot.getLocation())
                .data("timestamp", snapshot.getTimestamp())
                .data("file", file.getName(), new FileInputStream(file))
                .post();
            logger.info("Snapshot : "+snapshot.getFileName()+" has been sent.");
        } catch (Exception e){
            logger.error(e.toString());
        }
    }
}
