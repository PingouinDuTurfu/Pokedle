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
    public String showLeaderBoardClassic() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        return "redirect:/leaderboard/classic_game/" + dtf.format(now);
    }

    @GetMapping("/leaderboard/classic_game/{date}")
    public String showLeaderBoardClassic(@DateTimeFormat(pattern = "dd-MM-yyyy") @PathVariable("date") Date date, Model model) {
        ZonedDateTime zonedDateTime = date.toInstant().atZone(ZoneId.systemDefault());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        model.addAttribute("dateNotFormatted", date);
        model.addAttribute("dateMax", Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        model.addAttribute("dateNext", "/leaderboard/classic_game/" + dtf.format(zonedDateTime.plusDays(1).toLocalDate()));
        model.addAttribute("datePrevious", "/leaderboard/classic_game/" + dtf.format(zonedDateTime.minusDays(1).toLocalDate()));
        model.addAttribute("dateDay", zonedDateTime.format(DateTimeFormatter.ofPattern("EEEE", Locale.FRANCE)));
        model.addAttribute("date", zonedDateTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH)));
        model.addAttribute("listPlayer", leaderBoard.getClassicMapScoreOfDay(date));
        return "leaderboard";
    }

    @GetMapping("/leaderboard/splash_art_game")
    public String showLeaderBoardSplashArt() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        return "redirect:/leaderboard/splash_art_game/" + dtf.format(now);
    }

    @GetMapping("/leaderboard/splash_art_game/{date}")
    public String showLeaderBoardSplashArt(@DateTimeFormat(pattern = "dd-MM-yyyy") @PathVariable("date") Date date, Model model) {
        ZonedDateTime zonedDateTime = date.toInstant().atZone(ZoneId.systemDefault());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        model.addAttribute("dateNotFormatted", date);
        model.addAttribute("dateMax", Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        model.addAttribute("dateNext", "/leaderboard/splash_art_game/" + dtf.format(zonedDateTime.plusDays(1).toLocalDate()));
        model.addAttribute("datePrevious", "/leaderboard/splash_art_game/" + dtf.format(zonedDateTime.minusDays(1).toLocalDate()));
        model.addAttribute("dateDay", zonedDateTime.format(DateTimeFormatter.ofPattern("EEEE", Locale.FRANCE)));
        model.addAttribute("date", zonedDateTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH)));
        model.addAttribute("listPlayer", leaderBoard.getSplashArtMapScoreOfDay(date));
        return "leaderboard";
    }
}
