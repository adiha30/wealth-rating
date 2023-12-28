package com.adiha.wealthrating.repository;

import com.adiha.wealthrating.models.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RichRepository extends JpaRepository<PersonEntity, Long> {
}
