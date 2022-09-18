/* (C) 2022 */
package com.example.demo.persistance.player;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerEntityDao extends JpaRepository<PlayerEntity, Integer> {
  void deleteByGameId(Integer gameId);

  List<PlayerEntity> findByGameId(Integer gameId);
}
