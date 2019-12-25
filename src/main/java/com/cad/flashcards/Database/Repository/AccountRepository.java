package com.cad.flashcards.Database.Repository;

import com.cad.flashcards.Database.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query
    Account getById(Integer id);

    @Query
    Account getByUuid(String uuid);
}
