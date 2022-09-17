/* (C) 2022 */
package com.example.demo.persistance.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerEntityDao extends JpaRepository<PlayerEntity, Integer> {}
