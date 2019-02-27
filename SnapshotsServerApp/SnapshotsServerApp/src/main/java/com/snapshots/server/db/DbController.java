package com.snapshots.server.db;

import com.snapshots.server.db.model.Snapshot;
import com.snapshots.server.db.repos.SnapshotRepository;
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

    public List<Snapshot> getAllSnapshotsForCamera(String location) {
        Optional<List<Snapshot>> snapshots = snapshotRepository.findByLocation(location);
        if(snapshots.isPresent()){
            return snapshots.get();
        } else {
            return new ArrayList<>();
        }
    }
}
