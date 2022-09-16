package com.example.demo.web.game;

import com.example.demo.web.player.PlayerJson;
import com.example.demo.web.player.PlayerJsonMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock
    private GameService gameService;

    @Mock
    private PlayerJsonMapper playerJsonMapper;

    @InjectMocks
    private GameController gameController;

    @Test
    public void givenNewGameRequest_whenNewGame_thenReturnNewGameId() {
        // given
        int gameId = 1;
        when(gameService.newGame()).thenReturn(gameId);

        // when
        int expected = gameController.newGame();

        // then
        verify(gameService).newGame();
        assertThat(expected).isEqualTo(gameId);
    }


    @Test
    public void givenDeleteGameRequest_whenDeleteGame_thenDelegateToService() {
        // given
        int gameId = 1;

        // when
        gameController.deleteGame(gameId);

        // then
        verify(gameService).deleteGame(gameId);
    }


    @Test
    public void givenAddDeckRequest_whenAddDeck_thenReturnNewGameId() {
        // given
        int gameId = 1;

        // when
        gameController.addDeck(gameId);

        // then
        verify(gameService).addDeck(gameId);
    }

    @Test
    public void givenAddPlayerRequest_whenAddNewPlayer_thenDelegateToService() {
        // given
        int gameId = 1;
        int playerId = 2;

        // when
        gameController.addPlayer(gameId, playerId);

        // then
        verify(gameService).addPlayer(gameId, playerId);

    }

    @Test
    public void givenRemovePlayerRequest_whenRemoveNewPlayer_thenDelegateToService() {
        // given
        int gameId = 1;
        int playerId = 2;

        // when
        gameController.removePlayer(gameId, playerId);

        // then
        verify(gameService).removePlayer(gameId, playerId);

    }

    @Test
    public void givenDealCardToPlayerRequest_whenDealCardToPlayer_thenDelegateToService(
            @Mock PlayerJson playerJson,
            @Mock List<Integer> cards
    ) {
        // given
        int gameId = 1;
        int playerId = 2;

        when(gameService.dealCardToPlayer(gameId, playerId)).thenReturn(cards);
        when(playerJsonMapper.mapPlayer(cards)).thenReturn(playerJson);

        // when
        PlayerJson expected = gameController.dealCardToPlayer(gameId, playerId);


        // then
        verify(gameService).dealCardToPlayer(gameId, playerId);
        verify(playerJsonMapper).mapPlayer(cards);
        assertThat(expected).isEqualTo(playerJson);

    }

    @Test
    public void givenShuffleRequest_whenShuffle_thenDelegateToSerive() {
        // given
        int gameId = 1;

        // when
        gameController.shuffle(gameId);

        // then
        verify(gameService).shuffle(gameId);
    }

}
