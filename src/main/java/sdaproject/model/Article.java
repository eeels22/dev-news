package sdaproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

/**
 * Models an article with id, title, body and authorName.
 * Each article can have zero or many topics and zero or many comments.
 * @author En-Chi Liu
 */
@Entity
public class Article {

    // A unique auto-generated ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String body; // article text content
    private String authorName;

    // this List will not affect database as the Comment class is the "owning side" where need to make persistent changes
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Comment> comments;

    // Owning side for the relationship
    @ManyToMany
    private Set<Topic> topics;

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

    public Set<Comment> getComments() {
        return comments;
    }

    public Set<Topic> getTopics() {
        return topics;
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

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }
}
