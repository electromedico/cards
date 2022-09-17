/* (C) 2022 */
package com.example.demo.web.player;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/player")
public class PlayerController {

  @PostMapping()
  public int addPlayer() {
    return 1;
  }

  @DeleteMapping
  @RequestMapping("${id}")
  public int deletePlayer(@PathVariable int id) {
    return id;
  }
}
