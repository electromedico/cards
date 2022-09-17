package com.example.demo.web.game;

import com.example.demo.domain.game.Game;
import com.example.demo.domain.player.Player;
import com.example.demo.errors.EmptyDeckException;
import com.example.demo.errors.GameNotFoundFoundException;
import com.example.demo.errors.PlayerNotFoundException;
import com.example.demo.errors.RevisionsDontMatchException;
import com.example.demo.utils.Revision;
import com.example.demo.web.player.PlayerJson;
import com.example.demo.web.player.PlayerJsonMapper;
import com.example.demo.web.player.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.AbstractMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
    @Mock
    private GameService gameService;
    @Mock
    private PlayerService playerService;
    @Mock
    private PlayerJsonMapper playerJsonMapper;
    @Mock
    private GameJsonMapper gameJsonMapper;

    @InjectMocks
    private GameController gameController;

    @Test
    public void givenNewGameRequest_whenNewGame_thenReturnNewGameId() {
        // given
        int gameId = 1;
        when(gameService.newGame()).thenReturn(gameId);

        // when
        int expected = gameController.newGame(mockHttpServletResponse);

        // then
        verify(gameService).newGame();
        assertThat(expected).isEqualTo(gameId);
    }


    @Test
    public void givenDeleteGameRequest_whenDeleteGame_thenDelegateToService() throws GameNotFoundFoundException, RevisionsDontMatchException {
        // given
        int gameId = 1;
        int revision = 0;

        // when
        gameController.deleteGame(gameId, revision);

        // then
        verify(gameService).deleteGame(gameId, new Revision(revision));
    }

    @Test
    public void givenAddDeckRequest_whenAddDeck_thenReturnNewGameId() throws RevisionsDontMatchException, GameNotFoundFoundException {
        // given
        int gameId = 1;
        int revision=0;

        when(gameService.addDeck(gameId,new Revision(revision))).thenReturn(new Revision(revision+1));

        // when
        gameController.addDeck(gameId,revision,mockHttpServletResponse);

        // then
        verify(gameService).addDeck(gameId, new Revision(revision));
    }

    @Test
    public void givenAddPlayerRequest_whenAddNewPlayer_thenDelegateToService(@Mock AbstractMap.SimpleEntry<Revision, Player> revisionPlayer , @Mock Player player) throws PlayerNotFoundException, GameNotFoundFoundException, RevisionsDontMatchException {
        // given
        int gameId = 1;
        int revision=0;
        int playerId = 0;
        when(playerService.addPlayer(gameId,new Revision(revision))).thenReturn(revisionPlayer);
        when(revisionPlayer.getKey()).thenReturn(new Revision(revision+1));
        when(revisionPlayer.getValue()).thenReturn(player);
        when(player.getId()).thenReturn(playerId);

        // when
        Integer expected = gameController.addPlayer(gameId, revision, mockHttpServletResponse);

        // then
        verify(playerService).addPlayer(gameId , new Revision(revision));
        assertThat(expected).isEqualTo(playerId);

    }

    @Test
    public void givenRemovePlayerRequest_whenRemoveNewPlayer_thenDelegateToService() throws GameNotFoundFoundException, RevisionsDontMatchException, PlayerNotFoundException {
        // given
        int gameId = 1;
        int playerId = 2;
        int revision=0;

        when(gameService.removePlayer(gameId,playerId, new Revision(revision)))
                .thenReturn(new Revision(revision+1));
        // when
        gameController.removePlayer(gameId, playerId,revision, mockHttpServletResponse);

        // then
        verify(gameService).removePlayer(gameId, playerId, new Revision(revision));

    }

    @Test
    public void givenDealCardToPlayerRequest_whenDealCardToPlayer_thenDelegateToService(@Mock GameJson gameJson, @Mock Game game) throws GameNotFoundFoundException, RevisionsDontMatchException, PlayerNotFoundException, EmptyDeckException {
        // given
        int gameId = 1;
        int playerId = 2;
        int revision=0;

        when(gameService.dealCardToPlayer(gameId, playerId, new Revision(revision))).thenReturn(game);
        when(gameJsonMapper.map(game)).thenReturn(gameJson);
        when(game.getRevision()).thenReturn(new Revision(revision+1));

        // when
        GameJson expected = gameController.dealCardToPlayer(gameId, playerId, revision,mockHttpServletResponse);

        // then
        verify(gameService).dealCardToPlayer(gameId, playerId, new Revision(revision));
        verify(gameJsonMapper).map(game);
        assertThat(expected).isEqualTo(gameJson);

    }

    @Test
    public void givenGetPlayers_whenGetPlayers_thenDelegateToService(@Mock Player player, @Mock PlayerJson playerJson) throws GameNotFoundFoundException {
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
    public void givenShuffleRequest_whenShuffle_thenDelegateToService() throws GameNotFoundFoundException, RevisionsDontMatchException {
        // given
        int gameId = 1;
        int revision=0;
        when(gameService.shuffle(gameId,new Revision(revision))).thenReturn(new Revision(revision+1));
        // when
        gameController.shuffle(gameId,revision,mockHttpServletResponse);

        // then
        verify(gameService).shuffle(gameId, new Revision(revision));
    }

}
