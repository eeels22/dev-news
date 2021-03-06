package se.sdaproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

/**
 * Models a Topic with id and name.
 * Each topic can be applied to zero or many articles.
 *
 * @author En-Chi Liu
 */
@Entity
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "topics") // inverse (non-owning) side of the relationship
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Article> articles;

    public Topic() {
    }

    public Topic(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

}
