package se.project;

import javax.persistence.*;
import java.util.List;

/**
 * Models an article with id, title, body and authorName.
 * @author En-Chi Liu
 */
@Entity
public class Article {

    // A unique auto-generated ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String body;
    private String authorName;

    // this List will not affect database as the Comment class is the "owning side" where need to make persistent changes
    @OneToMany(mappedBy = "article")
    private List<Comment> comments;

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    // need mappedBy to avoid db created two tables mapping Article and Topic IDs
    // Class with mappedBy denotes it is not the owning side
    @ManyToMany(mappedBy = "articles")
    private List<Topic> topics;

    // empty or default constructor is required
    public Article() {
    }

    // getters required to display info to clients
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getAuthorName() {
        return authorName;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
