package com.mobiledger.backend.repository;

import com.mobiledger.backend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUser_Id(Long userId);
    Optional<Account> findByIdAndUser_Id(Long accountId, Long userId);
}
