package se.sdaproject.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Models a comment with id, body and authorName.
 * Each comment must belong to exactly one article.
 * @author En-Chi Liu
 */
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body; // comment text content
    private String authorName; // author of comment

    @ManyToOne // relationship-owning side
    @JoinColumn(nullable = false)
    @NotNull // a comment always needs to belong to an article article
    private Article article; // article on which the comment was posted

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Article getArticle() {
        return article;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
