package com.example.qrfilm.controller;

import com.example.qrfilm.entity.Comment;
import com.example.qrfilm.entity.Rating;
import com.example.qrfilm.entity.Films;
import com.example.qrfilm.repository.RatingRepository;
import com.example.qrfilm.entity.SaveData;
import com.example.qrfilm.repository.CommentRepository;
import com.example.qrfilm.repository.FilmsRepository;
import com.example.qrfilm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class FilmController {

    @Autowired
    private FilmsRepository filmsRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RatingRepository ratingRepository;

    @GetMapping("/films/add")
    public String showAddFilmForm(Model model) {
        checkAuth(model);
        model.addAttribute("film", new Films());
        return "add-film";
    }

    @GetMapping("/films")
    public String getAllFilms(Model model) {
        checkAuth(model);
        List<Films> films = filmsRepository.findAll();
        model.addAttribute("films", films);
        SaveData currentUser = userService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "films";
    }

    @GetMapping("/films/filter")
    public String filterFilmsByGenre(@RequestParam(name = "genre", required = false) String genre, Model model) {
        checkAuth(model);

        List<Films> filteredFilms;
        if (genre == null || genre.isEmpty()) {
            filteredFilms = filmsRepository.findAll();
        } else {
            filteredFilms = filmsRepository.findByGenre(genre);
        }

        model.addAttribute("films", filteredFilms);
        SaveData currentUser = userService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "films";
    }
    private String getYouTubeId(String youtubeUrl) {
        Pattern pattern = Pattern.compile("https?://(?:www\\.|m\\.)?youtube\\.(com|ru)/(?:watch\\?v=|embed/|v/|youtu\\.be/)([\\w-]{11})(?:[?&].*)?");
        Matcher matcher = pattern.matcher(youtubeUrl);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }

    @GetMapping("/watch/{id}")
    public String watchFilm(@PathVariable Long id, Model model) {
        checkAuth(model);
        Films film = filmsRepository.findById(id).orElse(null);
        if (film != null) {
            model.addAttribute("film", film);
            List<Comment> comments = commentRepository.findByFilmId(id);
            model.addAttribute("comments", comments);
            SaveData currentUser = userService.getCurrentUser();
            model.addAttribute("currentUser", currentUser);

            Double averageRating = ratingRepository.findAverageRatingByFilmId(id);
            if (averageRating != null) {
                model.addAttribute("averageRating", averageRating);
            }

        }
        return "watch";
    }

    private void checkAuth(Model model) {
        SaveData currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            model.addAttribute("error", "Вы не авторизованы");
            model.addAttribute("redirect", "/login");
            model.addAttribute("redirectParams", new HashMap<>());
        } else if (currentUser.getRole().equals("user") &&
                (model.getAttribute("redirect") == null ||
                        (!"/users".equals(model.getAttribute("redirect")) &&
                                !"/films/add".equals(model.getAttribute("redirect"))))) {
            model.addAttribute("error", "У вас недостаточно прав");
            model.addAttribute("redirect", "/films");
            model.addAttribute("redirectParams", new HashMap<>());
        }
    }

    @PostMapping("/rating/add")
    public String addRating(@RequestParam("filmId") Long filmId, @RequestParam("rating") Integer rating, Model model) {
        checkAuth(model);
        SaveData currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            Rating existingRating = ratingRepository.findByUidAndFid(currentUser.getId(), filmId);
            if (existingRating != null) {
                existingRating.setRating(rating);
            } else {
                existingRating = new Rating();
                existingRating.setUid(currentUser.getId());
                existingRating.setFid(filmId);
                existingRating.setRating(rating);
            }
            ratingRepository.save(existingRating);
        }
        return "redirect:/watch/" + filmId;
    }
    @PostMapping("/comment/add")
    public String addComment(@RequestParam("filmId") Long filmId, @RequestParam("commentText") String commentText, Model model) {
        checkAuth(model);
        SaveData currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            Films film = filmsRepository.findById(filmId).orElse(null);
            if (film != null) {
                Comment comment = new Comment();
                comment.setUser(currentUser);
                comment.setFilm(film);
                comment.setCommentText(commentText);
                commentRepository.save(comment);
            }
        }
        return "redirect:/watch/" + filmId;
    }
    @PostMapping("/comment/delete")
    public String deleteComment(@RequestParam("commentId") Long commentId, @RequestParam("filmId") Long filmId, Model model) {
        checkAuth(model);
        SaveData currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            Comment comment = commentRepository.findById(commentId).orElse(null);
            if (comment != null && (currentUser.getRole().equals("admin") || comment.getUser().getId().equals(currentUser.getId()))) {
                commentRepository.delete(comment);
            }
        }
        return "redirect:/watch/" + filmId;
    }
    @PostMapping("/films")
    public String addFilm(@ModelAttribute("film") Films film, Model model) {
        checkAuth(model);
        if (film.getLink() != null && !film.getLink().isEmpty()) {
            String youtubeId = getYouTubeId(film.getLink());
            film.setYoutubeId(youtubeId);
        }
        filmsRepository.save(film);
        return "redirect:/films";
    }


}
