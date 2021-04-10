package se.project;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * Controller for the Comment class
 * @author En-Chi Liu
 */
@RestController
public class CommentController {

    CommentRepository commentRepository;
    ArticleRepository articleRepository;

    public CommentController(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    /**
     * Create a new comment on article given by articleId.
     * @param articleId the article the comment belongs to
     * @param comment the comment being created
     * @return CREATED response status and the new comment
     */
    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable Long articleId, @RequestBody Comment comment) {
        // find the article
        Article article = articleRepository
                .findById(articleId)
                .orElseThrow(ResourceNotFoundException::new);
        comment.setArticle(article);
        commentRepository.save(comment);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(comment);
    }

    /**
     * Updates the given comment
     * @param id the id of the existing comment to be updated
     * @param updatedComment the contents of the updated comment
     * @return OK response status and the updated comment
     */
    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @Valid @RequestBody Comment updatedComment) {
        commentRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        updatedComment.setId(id);
        Comment comment = commentRepository.save(updatedComment);
        return ResponseEntity.ok(comment);
    }

    /**
     * Returns all comments on article given by articleId.
     * @return a list of all comments
     */
    @GetMapping("/comments")
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

//    GET	/comments?authorName={authorName}	return all comments made by author given by authorName.
//    @GetMapping(value="/comments", params = {"authorName"})
//    public List<Comment> getAllCommentsByAuthorName() {
//        commentRepository.findAll()
//
//    }

    /**
     * Deletes the given comment.
     * @param id the id of the comment to delete
     */
    @DeleteMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long id) {
        Comment comment = commentRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        commentRepository.delete(comment);
    }

}