package com.example.demo.web.game;

import com.example.demo.domain.player.Player;
import com.example.demo.erros.GameNotFoundException;
import com.example.demo.erros.PlayerNotFoundException;
import com.example.demo.erros.RevisionsDontMatch;
import com.example.demo.utils.Revision;
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
    public void givenDeleteGameRequest_whenDeleteGame_thenDelegateToService() throws GameNotFoundException, RevisionsDontMatch {
        // given
        int gameId = 1;
        int revision = 0;

        // when
        gameController.deleteGame(gameId, revision);

        // then
        verify(gameService).deleteGame(gameId, new Revision(revision));
    }

    @Test
    public void givenAddDeckRequest_whenAddDeck_thenReturnNewGameId() throws RevisionsDontMatch, GameNotFoundException {
        // given
        int gameId = 1;
        int revision=0;

        // when
        gameController.addDeck(gameId,revision);

        // then
        verify(gameService).addDeck(gameId, new Revision(revision));
    }

    @Test
    public void givenAddPlayerRequest_whenAddNewPlayer_thenDelegateToService() throws PlayerNotFoundException, GameNotFoundException {
        // given
        int gameId = 1;
        int revision=0;

        // when
        gameController.addPlayer(gameId, revision);

        // then
        verify(gameService).addPlayer(gameId , new Revision(revision));

    }

    @Test
    public void givenRemovePlayerRequest_whenRemoveNewPlayer_thenDelegateToService() {
        // given
        int gameId = 1;
        int playerId = 2;
        int revision=0;

        // when
        gameController.removePlayer(gameId, playerId,revision);

        // then
        verify(gameService).removePlayer(gameId, playerId, new Revision(revision));

    }

    @Test
    public void givenDealCardToPlayerRequest_whenDealCardToPlayer_thenDelegateToService(
            @Mock PlayerJson playerJson,
            @Mock Player player
    ) {
        // given
        int gameId = 1;
        int playerId = 2;
        int revision=0;

        when(gameService.dealCardToPlayer(gameId, playerId, new Revision(revision))).thenReturn(player);
        when(playerJsonMapper.mapPlayer(player)).thenReturn(playerJson);

        // when
        PlayerJson expected = gameController.dealCardToPlayer(gameId, playerId, revision);

        // then
        verify(gameService).dealCardToPlayer(gameId, playerId, new Revision(revision));
        verify(playerJsonMapper).mapPlayer(player);
        assertThat(expected).isEqualTo(playerJson);

    }

    @Test
    public void givenGetPlayers_whenGetPlayers_thenDelegateToService(@Mock Player player, @Mock PlayerJson playerJson) {
        // given
        int gameId = 1;
        when(gameService.getPlayers(gameId)).thenReturn(List.of(player));
        when(playerJsonMapper.mapPlayer(player)).thenReturn(playerJson);

        // when
        List<PlayerJson> expected = gameController.getPlayers(gameId);

        // then
        verify(gameService).getPlayers(gameId);
        verify(playerJsonMapper).mapPlayer(player);
        assertThat(expected).containsExactly(playerJson);

    }

    @Test
    public void givenShuffleRequest_whenShuffle_thenDelegateToService() {
        // given
        int gameId = 1;
        int revision=0;

        // when
        gameController.shuffle(gameId,revision);

        // then
        verify(gameService).shuffle(gameId, new Revision(revision));
    }

}
