package se.sdaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.sdaproject.model.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    // checks if a topic with the given name exists
    boolean existsTopicByName(String name);
    // Finds a topic with the given name
    Topic findTopicByName(String name);
}
