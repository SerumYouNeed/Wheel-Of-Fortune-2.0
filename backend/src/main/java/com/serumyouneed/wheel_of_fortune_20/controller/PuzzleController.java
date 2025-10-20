package com.serumyouneed.wheel_of_fortune_20.controller;

import com.serumyouneed.wheel_of_fortune_20.model.Category;
import com.serumyouneed.wheel_of_fortune_20.model.GameState;
import com.serumyouneed.wheel_of_fortune_20.model.Puzzle;
import com.serumyouneed.wheel_of_fortune_20.model.User;
import com.serumyouneed.wheel_of_fortune_20.service.GameStateService;
import com.serumyouneed.wheel_of_fortune_20.service.PuzzleService;
import com.serumyouneed.wheel_of_fortune_20.utils.CategorySelector;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@SessionAttributes("selectedCategory")
public class PuzzleController {

    private final PuzzleService puzzleService;
    private final GameStateService gameStateService;

    public PuzzleController(PuzzleService puzzleService, GameStateService gameStateService) {
        this.gameStateService = gameStateService;
        this.puzzleService = puzzleService;
    }

    @GetMapping("/select-category")
    public String drawCategory(Model model) {
        Category randomCategory = CategorySelector.selectCategory();
        model.addAttribute("category", randomCategory.name());
        return "fragments/selectors :: categorySelected";
    }

    @GetMapping("/select-puzzle")
    public String drawPuzzle(@ModelAttribute("selectedCategory") Category category, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        String mode = (String) session.getAttribute("mode");

        GameState gameState;
        Puzzle puzzle;

        if ("load".equals(mode)) {
            gameState = gameStateService.loadPreviousGame(user);
    puzzle = gameState.getPuzzle();
        } else {
            puzzle = puzzleService.getPuzzle(category);
            String masked = puzzleService.maskingPuzzle(puzzle);

            gameState = gameStateService.createNewGame(user, puzzle);
            session.setAttribute("mode", "load");
        }
        List<String> maskedPuzzleAsList = puzzleService.getMaskedPuzzleAsList(gameState.getMasked());
        model.addAttribute("puzzle", maskedPuzzleAsList);
        return "fragments/selectors :: puzzleSelected";
    }

}
