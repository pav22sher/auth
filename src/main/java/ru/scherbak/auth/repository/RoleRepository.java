package ru.scherbak.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.scherbak.auth.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
