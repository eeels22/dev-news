package se.project;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    // checks if a topic with the given name exists
    boolean existsTopicByName(String name);
    // Finds a topic with the given name
    Topic findTopicByName(String name);
}
