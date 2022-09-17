/* (C) 2022 */
package com.example.demo.persistance.cards;

import com.example.demo.domain.deck.Card;
import com.example.demo.errors.ServerException;
import com.google.gson.Gson;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import javax.sql.rowset.serial.SerialClob;
import org.springframework.stereotype.Component;

@Component
public class CardEntityMapper {

  public List<Card> mapCards(Clob clob) {
    // todo find a better answer
    List<Card> cards = null;
    if (clob != null) {
      try {
        String json = clob.getSubString(1L, (int) clob.length());
        cards = Arrays.stream(new Gson().fromJson(json, Card[].class)).toList();
      } catch (SQLException e) {
        throw new ServerException(e);
      }
    }

    return cards;
  }

  public Clob mapCards(List<Card> cards) {
    String json = new Gson().toJson(cards);
    try {
      return new SerialClob(json.toCharArray());
    } catch (SQLException e) {
      throw new ServerException(e);
    }
  }
}
