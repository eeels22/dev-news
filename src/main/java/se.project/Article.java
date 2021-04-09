package se.project;

import javax.persistence.*;
import java.util.List;

@Entity
public class Article {

    // A unique generated ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String body;
    private String authorName;

    // this List will not affect database as the Comment is the "owning side" where need to make persistent changes
    @OneToMany(mappedBy = "article")
    private List<Comment> comments;

    // empty constructor is required
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
}
