package com.example.demo.repository;

import com.example.demo.persistences.DbFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DbFileRepository  extends JpaRepository<DbFile, String> {
    @Query(value = "SELECT * FROM files WHERE task_id = ?1", nativeQuery = true)
    Optional<DbFile> findByTaskId(int task_id);

    @Query(value= "SELECT COUNT(id) FROM files WHERE task_id = ?1", nativeQuery = true)
    Integer countFilebyTask(int task_id);
}
