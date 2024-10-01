package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.Blog;

import java.util.List;

@ApplicationScoped
public class BlogRepository implements PanacheRepository<Blog> {

    public List<Blog> findByUserId(Long userId) {
        return find("user.id", userId).list(); // Use the relationship to query by user ID
    }

    public Blog findByIdAndUserId(Long blogId, Long userId) {
        return find("id = ?1 and userId = ?2", blogId, userId).firstResult();
    }

    public void deleteByIdAndUserId(Long blogId, Long userId) {
        delete("id = ?1 and userId = ?2", blogId, userId);
    }
}
