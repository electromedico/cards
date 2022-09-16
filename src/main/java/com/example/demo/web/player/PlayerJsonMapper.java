package com.example.demo.web.player;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerJsonMapper {

    public PlayerJson mapPlayer(List<Integer> cards) {
        return new PlayerJson.PlayerJsonBuilder().cards(cards).build();
    }
}
