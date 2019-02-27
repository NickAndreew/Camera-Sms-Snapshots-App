package cam.imitation.messager.service;

import cam.imitation.db.model.Snapshot;
import cam.imitation.messager.MessageManager;
import cam.imitation.messager.config.ThreadConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MessagerService {

    private static final Logger logger = LoggerFactory.getLogger(MessagerService.class);
    private Map<String, ScheduledFuture> taskMap = new HashMap<>();

    private MessageManager messageManager;
    private ApplicationContext context;
    private ThreadPoolTaskScheduler taskExecutor;

    public void beginSending(List<Snapshot> snapshots, MessageManager monitorManager){
        this.context = new AnnotationConfigApplicationContext(ThreadConfig.class);
        this.taskExecutor = (ThreadPoolTaskScheduler) context.getBean("taskExecutor");
        this.messageManager = monitorManager;

        if(snapshots.size()>0){
            snapshots.stream().forEach(snapshot -> send(snapshot));
        } else {
            logger.info("Snapshots storage is empty");
        }
    }

    @Async
    public void stopSending(){
        taskMap.entrySet().stream().forEach(future -> {
            future.getValue().cancel(false);
        });
        taskMap.clear();
    }

    @Async
    public void send(Snapshot snapshot){
        ScheduledFuture f = taskMap.get(snapshot.getFileName());
        if(f==null){
            ScheduledFuture future = taskExecutor.scheduleWithFixedDelay(
                new MessageTask(snapshot, messageManager),
                snapshot.getSendDelay()
            );
            taskMap.put(snapshot.getFileName(), future);
        } else {
            if(!f.isDone() || !f.isCancelled()){
                f.cancel(false);
            }
            taskMap.remove(snapshot.getFileName());
            send(snapshot);
        }
    }

    @Async
    public void stop(Snapshot snapshot){
        if(!taskMap.isEmpty() && taskMap.get(snapshot.getFileName())!=null){
            taskMap.get(snapshot.getFileName()).cancel(true);
        }
    }

    @Async
    public void sendFile(Snapshot file){
        if(!taskMap.isEmpty()){
            send(file);
        }
    }
}
