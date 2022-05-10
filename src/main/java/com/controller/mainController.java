package com.controller;

import com.db.enity.ShortenedUrl;
import com.db.repository.ShortenedUrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.Optional;

@Controller
public class mainController {
    private final ShortenedUrlRepository shortenedUrlRepository;
    private final Logger logger = LoggerFactory.getLogger(mainController.class);
    private final String urlPrefix = "http://localhost:8080/urlShortener/";
    @Autowired
    public mainController(ShortenedUrlRepository shortenedUrlRepository) {
        this.shortenedUrlRepository = shortenedUrlRepository;
    }

    @GetMapping("/init")
    public String initialRequest(Model model) {
        model.addAttribute("shortUrl", "none");
        logger.info("given");
        return "init";
    }

    @PostMapping("/init")
    public String shortURLRequest(@ModelAttribute("originalUrl") String url, Model model) {
        logger.info("post " + url);
        Optional<ShortenedUrl> shortenedUrl = shortenedUrlRepository.findFirstByOriginalUrl(url);
        if (shortenedUrl.isEmpty()) {
            ShortenedUrl newShortenedUrl = new ShortenedUrl(url);
            shortenedUrlRepository.save(newShortenedUrl);
            model.addAttribute("shortUrl", urlPrefix+newShortenedUrl.getShortUrl());
            logger.info("new");
        } else {
            model.addAttribute("shortUrl", urlPrefix+shortenedUrl.get().getShortUrl());
            logger.info("existed");
        }
        return "init";
    }

    @GetMapping("/{shortUrl}")
    public ModelAndView redirect(@PathVariable("shortUrl") String shortUrl) {
        try {
            Optional<ShortenedUrl> shortenedUrl = shortenedUrlRepository.findFirstByShortUrl(Long.parseLong(shortUrl));
            return shortenedUrl.map(url -> new ModelAndView("redirect:" + url.getOriginalUrl())).orElseGet(()
                    -> new ModelAndView("none"));
        } catch (NumberFormatException e) {
            return new ModelAndView("none");
        }
    }
}
