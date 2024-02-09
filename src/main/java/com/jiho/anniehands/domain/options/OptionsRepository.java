package com.jiho.anniehands.domain.options;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OptionsRepository extends JpaRepository<Options, Integer> {

    List<Options> findAll();

    Optional<Options> findByNo(Integer no);
}
