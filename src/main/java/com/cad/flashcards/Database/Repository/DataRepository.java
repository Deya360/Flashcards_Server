package com.cad.flashcards.Database.Repository;

import com.cad.flashcards.Database.Entity.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DataRepository extends JpaRepository<Data, Integer> {
    @Query
    Data getById(Integer id);

    @Query
    Data getFirstByAccountIdOrderByBackupDateDesc(Integer id);
}
