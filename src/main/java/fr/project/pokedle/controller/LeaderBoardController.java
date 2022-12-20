package fr.project.pokedle.controller;

import fr.project.pokedle.game.GameManager;
import fr.project.pokedle.game.LeaderBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
public class LeaderBoardController {
    @Autowired
    private LeaderBoard leaderBoard;
    @Autowired
    private GameManager gameManager;

    @GetMapping("/leaderBoard/classic_game")
    public String showLeaderBoard() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        return "redirect:/leaderBoard/classic_game/" + dtf.format(now);
    }

    @GetMapping("/leaderBoard/classic_game/{date}")
    public String showLeaderBoard(@DateTimeFormat(pattern = "dd-MM-yyyy") @PathVariable("date") Date date, Model model) {
        model.addAttribute("date", date);
        model.addAttribute("listPlayer", leaderBoard.getClassicMapScoreOfDay(date));
        return "leaderBoard";
    }
}
