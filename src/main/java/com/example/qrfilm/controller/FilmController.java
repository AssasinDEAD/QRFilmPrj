package com.example.qrfilm.controller;
import com.example.qrfilm.entity.Films;
import com.example.qrfilm.repository.FilmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
public class FilmController {

    @Autowired
    private FilmsRepository filmsRepository;

    @GetMapping("/films/add")
    public String showAddFilmForm(Model model) {
        model.addAttribute("film", new Films());
        return "add-film";
    }
    @GetMapping("/films")
    public String getAllFilms(Model model) {
        List<Films> films = filmsRepository.findAll();
        model.addAttribute("films", films);
        return "films";
    }


    @PostMapping("/films")
    public String addFilm(@ModelAttribute("film") Films film) {
        filmsRepository.save(film);
        return "redirect:/films";
    }

}

