package it.epicode.the_plant_based_hub_backend.repositories;

import it.epicode.the_plant_based_hub_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long> {
    public Optional<User> findByEmail(String email);
}

