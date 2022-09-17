/* (C) 2022 */
package com.example.demo.persistance.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameEntityDao extends JpaRepository<GameEntity, Integer> {}
