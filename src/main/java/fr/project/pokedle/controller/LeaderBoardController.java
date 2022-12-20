package fr.project.pokedle.controller;

import fr.project.pokedle.game.GameManager;
import fr.project.pokedle.game.LeaderBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Controller
public class LeaderBoardController {
    @Autowired
    private LeaderBoard leaderBoard;
    @Autowired
    private GameManager gameManager;

    @GetMapping("/leaderboard/classic_game")
    public String showLeaderBoard() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        return "redirect:/leaderboard/classic_game/" + dtf.format(now);
    }

    @GetMapping("/leaderboard/classic_game/{date}")
    public String showLeaderBoard(@DateTimeFormat(pattern = "dd-MM-yyyy") @PathVariable("date") Date date, Model model) {
        ZonedDateTime zonedDateTime = date.toInstant().atZone(ZoneId.systemDefault());
        model.addAttribute("dateDay", zonedDateTime.format(DateTimeFormatter.ofPattern("EEEE", Locale.FRANCE)));
        model.addAttribute("date", zonedDateTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH)));
        model.addAttribute("listPlayer", leaderBoard.getClassicMapScoreOfDay(date));
        return "leaderboard";
    }
}
