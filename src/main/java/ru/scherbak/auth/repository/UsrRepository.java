package ru.scherbak.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.scherbak.auth.entity.Usr;

@Repository
public interface UsrRepository extends JpaRepository<Usr, Long> {
    Usr findByUsername(String username);
}
