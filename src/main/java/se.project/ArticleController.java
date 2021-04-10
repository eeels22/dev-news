package se.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for the Article class
 *
 * @author En-Chi Liu
 */
@RequestMapping("/articles")
@RestController
public class ArticleController {

    // connection between Spring and database
    ArticleRepository articleRepository;

    /**
     * @param articleRepository an object that implements interface ArticleRepository
     */
    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * Returns all articles.
     *
     * @return a list of all articles
     */
    @GetMapping()
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    /**
     * Returns a specific article based on the provided id.
     *
     * @param id the id of the desired article
     * @return the desired article
     * @throws ResourceNotFoundException if the article does not exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        Article article = articleRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.ok(article);
    }

    /**
     * Creates a new article.
     *
     * @param article the article to create
     * @return CREATED response status and the new article
     */
    @PostMapping()
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        articleRepository.save(article);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(article);
    }

    /**
     * Updates the given article.
     *
     * @param id             the id of the article to update
     * @param updatedArticle the contents of the update article
     * @return OK response status and the updated article
     */
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

    /**
     * Deletes the given article.
     *
     * @param id the id of the article to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticleById(@PathVariable Long id) {
        Article article = articleRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        articleRepository.delete(article);
        // return type is void as returning the deleted entity may cause problems with one-to-many relationships
    }
}