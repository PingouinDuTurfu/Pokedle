package fr.project.pokedle.game;

import fr.project.pokedle.game.classic_game.ClassicGameManager;
import fr.project.pokedle.persistence.game.classic.ClassicGame;
import fr.project.pokedle.persistence.game.classic.ClassicGamePlayer;
import fr.project.pokedle.persistence.registration.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class LeaderBoard {
    @Autowired
    private ClassicGameManager classicGameManager;
    @Autowired
    private GameManager gameManager;

    public JSONObject getJSONScoreOnePlayer(ClassicGamePlayer classicGamePlayer) {
        return getJSONScoreOnePlayer(classicGamePlayer.getUser(), classicGamePlayer.getScore());
    }

    public JSONObject getJSONScoreOnePlayer(User user, Double score) {
        JSONObject json = new JSONObject();
        json.put("username", user.getUsername());

        json.put("score", score);
        return json;
    }

    public JSONArray getLeaderBoardClassicGameOfDay(Date date) {
        JSONArray jsonScores = new JSONArray();
        ClassicGame classicGame = classicGameManager.getClassicGameOfDay(date);
        List<ClassicGamePlayer> playerList = classicGame.getGamePlayers();

        playerList.stream()
                .filter(ClassicGamePlayer::isSuccess)
                .sorted((o1, o2) -> (int) (o2.getScore() - o1.getScore()))
                .map(this::getJSONScoreOnePlayer)
                .map(jsonScores::add);

        return jsonScores;
    }

    public Map<User, Double> getClassicMapScoreOfDay(Date date) {
        return classicGameManager
                .getClassicGameOfDay(date)
                .getGamePlayers()
                .stream()
                .filter(ClassicGamePlayer::isSuccess)
                .collect(Collectors.toMap(ClassicGamePlayer::getUser, ClassicGamePlayer::getScore));
    }
/*
    public Map<User, Double> getMapgeneral(Date date) {
        return classicGameRepository
                .findAll()
                .stream()
                .map(ClassicGame::getGamePlayers)
                .flatMap(List::stream)
                .filter(ClassicGamePlayer::isSuccess)
                .collect(Collectors.groupingBy(ClassicGamePlayer::getUser))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                list -> list.getValue()
                                        .stream()
                                        .reduce(0.0, (sum, classicGamePlayer) -> sum + classicGamePlayer.getScore(), Double::sum)
                        )
                );
    }*/
    /*

    public JSONArray getLeaderBoardClassicGame(Date date) {
        JSONArray jsonScores = new JSONArray();
        Map<User, Double> mapScore = getMapScore();
        mapScore.entrySet()
                .stream()
                .sorted((o1, o2) -> (int) (o2.getValue() - o1.getValue()))
                .map(userDoubleEntry -> jsonScores.add(getJSONScoreOnePlayer(userDoubleEntry.getKey(), userDoubleEntry.getValue())));
        return jsonScores;
    }
    */
}
