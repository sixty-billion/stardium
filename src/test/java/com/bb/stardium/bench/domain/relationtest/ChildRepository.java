package com.bb.stardium.bench.domain.relationtest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChildRepository extends JpaRepository<Child, Long> {
    List<Child> findByElements(Element elements);
}
