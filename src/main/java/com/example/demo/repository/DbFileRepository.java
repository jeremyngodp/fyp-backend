package com.example.demo.repository;

import com.example.demo.persistences.DbFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DbFileRepository  extends JpaRepository<DbFile, String> {
}
