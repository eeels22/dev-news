package se.project;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TopicController {

    TopicRepository topicRepository;
    ArticleRepository articleRepository;

    public TopicController(TopicRepository topicRepository, ArticleRepository articleRepository) {
        this.topicRepository = topicRepository;
        this.articleRepository = articleRepository;
    }

    /**
     * Returns all topics.
     *
     * @return all topics
     */
    @GetMapping("/topics")
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    /**
     * Returns all topics associated with the given article .
     *
     * @param articleId id of the article whose topics we want to return
     * @return topics associated with the article
     */
    @GetMapping("/articles/{articleId}/topics")
    public List<Topic> getAllTopicsForArticle(@PathVariable Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        return article.getTopics();
    }

//    POST	/articles/{articleId}/topics	associate the topic with the article given by articleId. If topic does not already exist, it is created.
    // TODO should this have {topicID} as second PathVariable?
//    @PostMapping("/articles/{articleId}/topics")
//    public ResponseEntity<Topic> addArticleToTopic(@PathVariable Long articleId, @RequestBody Topic topicInput) {
//        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
//        Topic topic = topicRepository.findOne(topicInput).
//
//    }

    /**
     * Creates a new topic
     *
     * @param topic the topic to be created
     * @return CREATED response status and the newly created topic
     */
    @PostMapping("/topics")
    public ResponseEntity<Topic> createTopic(@RequestBody Topic topic) {
        topicRepository.save(topic);
        return ResponseEntity.status(HttpStatus.CREATED).body(topic);
    }

    /**
     * Updates the given topic.
     * @param id The id of the topic to update.
     * @param updatedTopic the content of the updated topic
     * @return OK response status and the updated topic
     */
    @PutMapping("/topics/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable Long id, @RequestBody Topic updatedTopic) {
        topicRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        updatedTopic.setId(id);
        Topic topic = topicRepository.save(updatedTopic);
        return ResponseEntity.ok(topic);
    }

    /**
     * Deletes the given topic.
     *
     * @param id The id of the topic to delete
     */
    @DeleteMapping("/topics/{id}")
    public void deleteTopic(@PathVariable Long id) {
        Topic topic = topicRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        topicRepository.delete(topic);
    }

//    DELETE	/articles/{articleId}/topics/{topicId}	delete the association of a topic for the given article. The topic & article themselves remain.

    /**
     * Returns all articles associated with the topic given by topicId.
     * @param topicId Id of the topic whose articles we want to return
     * @return articles belonging to the specific topic
     */
    @GetMapping("/topics/{topicId}/articles")
    public ResponseEntity<List<Article>> getArticlesForTopicByTopicId(@PathVariable Long topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(ResourceNotFoundException::new);
        List<Article> articles = topic.getArticles();
        return ResponseEntity.ok(articles);
    }
}
