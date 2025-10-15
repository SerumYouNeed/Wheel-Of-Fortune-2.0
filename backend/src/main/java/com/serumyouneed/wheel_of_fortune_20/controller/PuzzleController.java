package com.serumyouneed.wheel_of_fortune_20.controller;

import com.serumyouneed.wheel_of_fortune_20.model.Category;
import com.serumyouneed.wheel_of_fortune_20.model.Puzzle;
import com.serumyouneed.wheel_of_fortune_20.service.PuzzleService;
import com.serumyouneed.wheel_of_fortune_20.utils.CategorySelector;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PuzzleController {

    private final PuzzleService puzzleService;

    public PuzzleController(PuzzleService puzzleService) {this.puzzleService = puzzleService; }

    @GetMapping("/select-category")
    public String drawCategory(Model model) {
        Category randomCategory = CategorySelector.selectCategory();
        model.addAttribute("category", randomCategory.name());
        return "fragments/selectors :: categorySelected";
    }

    @GetMapping("/select-puzzle")
    public String drawPuzzle(Model model) {
        List<String> masked = puzzleService.getMaskedPuzzleAsList(puzzleService.getPuzzle());
        model.addAttribute("puzzle", masked);
        return "fragments/selectors :: puzzleSelected";
    }

}
