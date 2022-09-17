/* (C) 2022 */
package com.example.demo.persistance.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerEntityDao extends JpaRepository<PlayerEntity, Integer> {
  void deleteByGameId(Integer gameId);

  List<PlayerEntity> findByGameId(Integer gameId);
}
