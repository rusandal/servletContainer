package ru.netology.repository;

import ru.netology.model.Post;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

// Stub
public class PostRepository {
  public static ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
  private AtomicInteger lastCurrentId = new AtomicInteger(0);

  public List<Post> all() {
    return posts.values().stream().toList();
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public Post save(Post post) {
    if (post.getId()==0 || !posts.containsKey(post.getId())){
      post.setId(lastCurrentId.getAndIncrement());
      posts.put(post.getId(), post);
    } else {
      //post.setId(lastCurrentId.getAndIncrement());
      posts.replace(post.getId(), post);
    }
    System.out.println(posts);
    return post;
  }

  public void removeById(long id) {
    posts.remove(id);
  }
}
