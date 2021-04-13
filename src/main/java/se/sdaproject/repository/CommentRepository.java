package se.sdaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.sdaproject.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
