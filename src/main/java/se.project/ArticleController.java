package se.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/articles")
@RestController
public class ArticleController {

    // connection between Spring and database
    ArticleRepository articleRepository;

    // automatically finds a class that implements interface ArticleRepository and sets to to our field
    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    // GET	/articles	return all articles.
    @GetMapping()
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    // GET	/articles/{id}	return a specific article based on the provided id.
    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        Article article = articleRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.ok(article);
    }

    // POST	/articles	create a new article.
    @PostMapping()
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        articleRepository.save(article);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(article);
    }

    // PUT	/articles/{id}	update the given article.
    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article updatedArticle) {
        // check the article exists
        articleRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // set id for updated article to the given id
        updatedArticle.setId(id);
        // overwrites old Article with that id
        Article article = articleRepository.save(updatedArticle);
        return ResponseEntity.ok(article);
    }

    // DELETE	/articles/{id}	delete the given article.
    // changed to return nothing - returning the deleted entity may cause problems with one-to-many relationships
    @DeleteMapping("/{id}")
    public void deleteArticleById(@PathVariable Long id) {
        Article article = articleRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        articleRepository.delete(article);
    }
}
ed