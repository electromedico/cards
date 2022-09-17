/* (C) 2022 */
package com.example.demo.web.player;

import com.example.demo.web.card.CardJson;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PlayerJson {
  Integer id;
  List<CardJson> cards;
}
