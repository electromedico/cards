/* (C) 2022 */
package com.example.demo.persistance.deck;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeckEntityDao extends JpaRepository<DeckEntity, Integer> {
  List<DeckEntity> findByGameId(Integer gameId);

  Optional<DeckEntity> findFirstByGameIdNull();
}
