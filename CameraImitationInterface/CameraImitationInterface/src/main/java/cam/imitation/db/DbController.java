package cam.imitation.db;

import cam.imitation.db.model.Snapshot;
import cam.imitation.db.repo.SnapshotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DbController {

    private Logger logger = LoggerFactory.getLogger(DbController.class);

    @Autowired
    private SnapshotRepository snapshotRepository;

    public boolean saveSnapshot(Snapshot snapshot){
        Optional<Snapshot> snapsh = Optional.ofNullable(snapshot);
        if(snapsh.isPresent()){
            snapshotRepository.save(snapsh.get());
        }
        return snapsh.isPresent();
    }

    public boolean deleteSnapshot(Snapshot snapshot){
        Optional<Snapshot> snapsh = snapshotRepository.findById(snapshot.getId());
        if(snapsh.isPresent()){
            snapshotRepository.delete(snapsh.get());
        }
        return snapsh.isPresent();
    }

    public List<Snapshot> getAllSnapshots(){
        Optional<List<Snapshot>> snapshots = Optional.ofNullable(snapshotRepository.findAll());
        if(snapshots.isPresent()) {
            return snapshots.get();
        } else {
            return new ArrayList<>();
        }
    }

    public List<Snapshot> getAllSnapshotsForLocation(String cameraLocation) {
        Optional<List<Snapshot>> snapshots = snapshotRepository.findByLocation(cameraLocation);
        if(snapshots.isPresent()){
            return snapshots.get();
        } else {
            return new ArrayList<>();
        }
    }

    public void deleteAllSnapshots(){
        snapshotRepository.deleteAll();
    }
}
