/* (C) 2022 */
package com.example.demo.persistance.game;

import javax.persistence.*;

@Entity
@Table(name = "game")
public class GameEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "revision")
  private Integer revision;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getRevision() {
    return revision;
  }

  public void setRevision(Integer revision) {
    this.revision = revision;
  }
}
