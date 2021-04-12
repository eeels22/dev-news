package se.sdaproject.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.sdaproject.model.Comment;
import se.sdaproject.api.exception.ResourceNotFoundException;
import se.sdaproject.model.Article;
import se.sdaproject.repository.ArticleRepository;
import se.sdaproject.repository.CommentRepository;

import javax.validation.Valid;
import java.util.ArrayList;
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
     * Returns all comments on article given by articleId.
     * @return OK response status and a list of all comments
     */
    @GetMapping("/articles/{articleId}/comments")
    public ResponseEntity<List<Comment>> getCommentsByArticleId(@PathVariable Long articleId) {
        //check if article id is valid
        articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        // check all comments for ones whose article matches the article id
        List<Comment> articleComments = new ArrayList<>();
        List<Comment> allComments = commentRepository.findAll();
        for (Comment comment : allComments) {
            if (comment.getArticle().getId().equals(articleId)) {
                articleComments.add(comment);
            }
        }
        return ResponseEntity.ok(articleComments);
    }

    /**
     * Returns all comments made by author given by authorName.
     * @param authorName the author whose comments we want to return
     * @return OK response status and a list of comments by the author
     */
    @GetMapping(value="/comments", params = {"authorName"})
    public ResponseEntity<List<Comment>> getAllCommentsByAuthor(@RequestParam String authorName) {
        List<Comment> authorComments = new ArrayList<>();
        List<Comment> allComments = commentRepository.findAll();
        for (Comment comment : allComments) {
            if (comment.getAuthorName().equals(authorName)) {
                authorComments.add(comment);
            }
        }
        return ResponseEntity.ok(authorComments);
    }


    /**
     * Create a new comment on article given by articleId.
     * @param articleId the article the comment belongs to
     * @param comment the comment being created
     * @return CREATED response status and the new comment
     */
    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable Long articleId, @RequestBody Comment comment) {
        // find the article if it exists
        Article article = articleRepository
                .findById(articleId)
                .orElseThrow(ResourceNotFoundException::new);
        comment.setArticle(article);
        Comment savedComment = commentRepository.save(comment);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedComment);
    }

    /**
     * Updates the given comment
     * @param id the id of the existing comment to be updated
     * @param updatedComment the contents of the updated comment
     * @return OK response status and the updated comment
     */
    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> updateCommentById(@PathVariable Long id, @Valid @RequestBody Comment updatedComment) {
        Comment oldComment = commentRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
                Long articleId = oldComment.getArticle().getId();
        updatedComment.setId(id);
        // copy article to updatedComment
        updatedComment.getArticle().setId(articleId);
        Comment savedComment = commentRepository.save(updatedComment);
        return ResponseEntity.ok(savedComment);
    }

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