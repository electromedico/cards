package com.example.demo.web.player;

import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Builder
@Getter
public class PlayerJson {
    Integer id;
    List<Integer> cards;
}
