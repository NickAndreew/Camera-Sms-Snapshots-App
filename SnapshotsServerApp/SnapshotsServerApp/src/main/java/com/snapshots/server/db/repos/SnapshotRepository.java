package com.snapshots.server.db.repos;

import com.snapshots.server.db.model.Snapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SnapshotRepository extends JpaRepository<Snapshot, Long> {

    Optional<List<Snapshot>> findByLocation(String location);

}
