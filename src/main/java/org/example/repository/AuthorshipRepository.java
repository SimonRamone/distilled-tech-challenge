package org.example.repository;

import org.example.model.Authorship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorshipRepository extends JpaRepository<Authorship, Long> {
}



