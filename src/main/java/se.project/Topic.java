package se.project;

import javax.persistence.*;
import java.util.List;

/**
 * Models a Topic with id and name.
 * @author En-Chi Liu
 */
@Entity
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // relationship owning side
    @ManyToMany
    private List<Article> articles;

    public Topic() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

}
