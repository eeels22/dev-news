package se.project;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CommentController {

    CommentRepository commentRepository;
    ArticleRepository articleRepository;

    public CommentController(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    //    POST	/articles/{articleId}/comments	create a new comment on article given by articleId.
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

    //    PUT	/comments/{id}	update the given comment.
    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @Valid @RequestBody Comment updatedComment) {
        commentRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        updatedComment.setId(id);
        commentRepository.save(updatedComment);
        return ResponseEntity.ok(updatedComment);
    }

//    GET	/articles/{articleId}/comments	return all comments on article given by articleId.

//    GET	/comments?authorName={authorName}	return all comments made by author given by authorName.


//    DELETE	/comments/{id}	delete the given comment.
}
