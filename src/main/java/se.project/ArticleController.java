package se.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
    TopicRepository topicRepository;

    /**
     * @param articleRepository an object that implements interface ArticleRepository
     */
    @Autowired
    public ArticleController(ArticleRepository articleRepository, TopicRepository topicRepository) {
        this.articleRepository = articleRepository;
        this.topicRepository = topicRepository;
    }

    /**
     * Returns all articles.
     *
     * @return a list of all articles
     */
    @GetMapping()
    public ResponseEntity<List<Article>> getAllArticles() {
        return ResponseEntity.ok(articleRepository.findAll());
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
        Article savedArticle = articleRepository.save(article);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    /**
     * Updates the given article.
     *
     * @param id             the id of the article to update
     * @param updatedArticle the contents of the update article
     * @return OK response status and the updated article
     * @throws ResourceNotFoundException if the article does not exist
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
        Article savedArticle = articleRepository.save(updatedArticle);
        return ResponseEntity.ok(savedArticle);
    }

    /**
     * Deletes the given article.
     *
     * @param id the id of the article to delete
     *           @throws ResourceNotFoundException if the article does not exist
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


    /**
     * Returns all topics associated with the given article .
     *
     * @param articleId id of the article whose topics we want to return
     * @return topics associated with the article
     */
    @GetMapping("/{articleId}/topics")
    public Set<Topic> getAllTopicsForArticle(@PathVariable Long articleId) {
        Article article = articleRepository
                .findById(articleId)
                .orElseThrow(ResourceNotFoundException::new);
        return article.getTopics();
    }

    // TODO should the Topic passed in have the articles listed?
    /**
     * Associates the topic with the article given by articleId. If topic does not already exist, it is created.
     * @param articleId the id of the article
     * @param topicToAssociate the topic to associate with the article
     * @return the topic associated
     */
    @PostMapping("/{articleId}/topics")
    public ResponseEntity<Article> associateArticleWithTopic(@PathVariable Long articleId, @RequestBody Topic topicToAssociate) {
        // get article if it exists
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        // check if topic exists by matching the topic name
        String topicName = topicToAssociate.getName();
        boolean topicExists = topicRepository.existsTopicByName(topicName);
        if (topicExists) {
            article.getTopics().add(topicRepository.findTopicByName(topicName));
        } else {
            // create the topic if it does not already exist
            Topic savedTopic = topicRepository.save(topicToAssociate);
            article.getTopics().add(savedTopic);
        }
        // save to persist changes
        articleRepository.save(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(article);
    }

    /**
     * Deletes the association of a topic for the given article. The topic and article themselves remain.
     * @param articleId Id of the article
     * @param topicId Id of the topic
     */
    @DeleteMapping("/{articleId}/topics/{topicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTopicAssociation(@PathVariable Long articleId, @PathVariable Long topicId) {
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        Topic topic = topicRepository.findById(topicId).orElseThrow(ResourceNotFoundException::new);
        // check association exists
        if (article.getTopics().contains(topic)) {
            article.getTopics().remove(topic);
            articleRepository.save(article);
        } else {
            throw new ResourceNotFoundException();
        }
    }

}