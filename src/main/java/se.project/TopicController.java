package se.project;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Controller for the Topic class.
 * @author En-Chi Liu
 */
@RestController
public class TopicController {

    TopicRepository topicRepository;

    public TopicController(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    /**
     * Returns all topics.
     *
     * @return Ok response status and a list of all topics
     */
    @GetMapping("/topics")
    public ResponseEntity<List<Topic>> getAllTopics() {
        return ResponseEntity.ok(topicRepository.findAll());
    }

    /**
     * Creates a new topic
     *
     * @param topic the topic to be created
     * @return CREATED response status and the newly created topic
     */
    @PostMapping("/topics")
    public ResponseEntity<Topic> createTopic(@RequestBody Topic topic) {
        Topic savedTopic = topicRepository.save(topic);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTopic);
    }

    /**
     * Updates the given topic.
     *
     * @param id           The id of the topic to update.
     * @param updatedTopic the content of the updated topic
     * @return OK response status and the updated topic
     */
    @PutMapping("/topics/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable Long id, @RequestBody Topic updatedTopic) {
        Topic oldTopic = topicRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // Copy over id from old topic
        updatedTopic.setId(id);
        // Copy over articles from old topic
        updatedTopic.setArticles(oldTopic.getArticles());
        Topic savedTopic = topicRepository.save(updatedTopic);
        return ResponseEntity.ok(savedTopic);
    }


    /**
     * Deletes the given topic.
     *
     * @param id The id of the topic to delete
     */
    @DeleteMapping("/topics/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTopic(@PathVariable Long id) {
        Topic topic = topicRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // todo remove associations before deleting the topic

        topicRepository.delete(topic);
    }


    /**
     * Returns all articles associated with the topic given by topicId.
     *
     * @param topicId Id of the topic whose articles we want to return
     * @return articles belonging to the specific topic
     */
    @GetMapping("/topics/{topicId}/articles")
    public ResponseEntity<Set<Article>> getArticlesForTopicByTopicId(@PathVariable Long topicId) {
        Topic topic = topicRepository
                .findById(topicId)
                .orElseThrow(ResourceNotFoundException::new);
        Set<Article> articles = topic.getArticles();
        return ResponseEntity.ok(articles);
    }

}
