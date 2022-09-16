package com.example.demo.domain.player;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Builder
@Getter
public class Player {
    Integer id;
    List<Integer> cards;
}
