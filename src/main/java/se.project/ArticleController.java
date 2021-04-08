package se.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Article article = articleRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.ok(article);
    }





//    POST	/articles	create a new article.
//    PUT	/articles/{id}	update the given article.
//    DELETE	/articles/{id}	delete the given article.
}
