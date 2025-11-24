package com.serumyouneed.wheel_of_fortune_20.controller;

import com.serumyouneed.wheel_of_fortune_20.model.Category;
import com.serumyouneed.wheel_of_fortune_20.model.GameState;
import com.serumyouneed.wheel_of_fortune_20.model.Puzzle;
import com.serumyouneed.wheel_of_fortune_20.model.User;
import com.serumyouneed.wheel_of_fortune_20.service.GameSessionService;
import com.serumyouneed.wheel_of_fortune_20.service.GameStateService;
import com.serumyouneed.wheel_of_fortune_20.service.PuzzleService;
import com.serumyouneed.wheel_of_fortune_20.utils.CategorySelector;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PuzzleController {

    private final PuzzleService puzzleService;
    private final GameSessionService gameSessionService;

    public PuzzleController(PuzzleService puzzleService, GameSessionService gameSessionService) {
        this.gameSessionService = gameSessionService;
        this.puzzleService = puzzleService;
    }

    @PostMapping("/select-category")
    public String receiveCategory(@RequestParam String category, Model model) {
        model.addAttribute("message", "You selected: " + category);
        return "fragments/messages :: selectedCategory";
    }

    @GetMapping("/categories")
    public String getCategories(Model model) {
        model.addAttribute("categories", Category.values());
        return "fragments/categories :: categorySelect";
    }

//    @GetMapping("/select-category")
//    public String drawCategory(HttpSession session, Model model) {
//        Category randomCategory = CategorySelector.selectCategory();
//        gameSessionService.setCategoryAttr(session, randomCategory);
//        model.addAttribute("category", randomCategory.name());
//        return "fragments/selectors :: categorySelected";
//    }

    @GetMapping("/select-puzzle")
    public String drawPuzzle(HttpSession session, Model model) {
        GameState gameState = gameSessionService.getOrCreateGameState(session);
        if (gameState.getPuzzle() == null) {
            Puzzle puzzle = puzzleService.getPuzzle(gameSessionService.getCategoryAttr(session));
            gameState.setPuzzle(puzzle.getPuzzle().toUpperCase());
            gameState.setMasked(puzzleService.maskingPuzzle(puzzle.getPuzzle()));
        }
        List<List<String>> wordsAsLetters = puzzleService.wordsAsLetters(gameState.getMasked());
        int maxWordLength = puzzleService.maxWordLength(wordsAsLetters);
        model.addAttribute("maxWordLength", maxWordLength);
        model.addAttribute("words", wordsAsLetters);
        gameSessionService.updateGameState(session, gameState);
        return "fragments/selectors :: puzzleSelected";
    }
}
