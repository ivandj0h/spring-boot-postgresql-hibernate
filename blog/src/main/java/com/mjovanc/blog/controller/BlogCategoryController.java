package com.mjovanc.blog.controller;

import com.mjovanc.blog.model.BlogCategory;
import com.mjovanc.blog.repository.BlogCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/categories")
public class BlogCategoryController {

    @Autowired
    private BlogCategoryRepository blogCategoryRepository;

    @GetMapping
    public ResponseEntity<List<BlogCategory>> getAllBlogCategories() {
        List<BlogCategory> returnBlogCategories = blogCategoryRepository.findAll();
        return new ResponseEntity<>(returnBlogCategories, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<BlogCategory> getBlogCategoryById(@PathVariable Long id) {
        if (blogCategoryRepository.existsById(id)) {
            BlogCategory BlogCategory = blogCategoryRepository.findById(id).get();
            return new ResponseEntity<>(BlogCategory, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<BlogCategory> createBlogCategory(@RequestBody BlogCategory blogCategory) {
        blogCategoryRepository.save(blogCategory);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<BlogCategory> updateBlogCategoryById(@PathVariable Long id, @RequestBody BlogCategory blogCategory) {
        Optional<BlogCategory> blogCategoryToFind = blogCategoryRepository.findById(id);

        if (blogCategoryToFind.isPresent()) {
            BlogCategory blogCategoryToUpdate = blogCategoryToFind.get();
            blogCategoryToUpdate.setName(blogCategory.getName());

            blogCategoryRepository.save(blogCategoryToUpdate);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            blogCategoryRepository.save(blogCategory);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<BlogCategory> deleteBlogCategoryById(@PathVariable Long id) {
        if (blogCategoryRepository.existsById(id)) {
            blogCategoryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}