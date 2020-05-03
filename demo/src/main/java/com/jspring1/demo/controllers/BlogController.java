package com.jspring1.demo.controllers;


import com.jspring1.demo.model.Post;
import com.jspring1.demo.repo.PostRepository2;

import com.jspring1.demo.repo.PostRepository3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostRepository2 repository2;
//    private PostRepository postRepository;


    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = repository2.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }
    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
        public String blogPostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
            Post post = new Post(title, anons, full_text);
            repository2.save(post);
            return "redirect:/blog";
        }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value= "id") String id, Model model) {
        if(!repository2.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = repository2.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value= "id") String id, Model model) {
        if(!repository2.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = repository2.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value= "id") String id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Post post = repository2.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        repository2.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value= "id") String id, Model model) {
        Post post = repository2.findById(id).orElseThrow();
        repository2.delete(post);
        return "redirect:/blog";
    }
}