package ru.netology.controller;

import com.google.gson.Gson;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class PostController {
  public static final String APPLICATION_JSON = "application/json";
  private final PostService service;
  private final Gson gson = new Gson();
  private Post data = null;

  public PostController(PostService service) {
    this.service = service;
  }

  public void all(HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var data = service.all();
    response.getWriter().print(gson.toJson(data));
  }

  public void getById(long id, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    try{
      data = service.getById(id);
      response.getWriter().print(gson.toJson(data));
    } catch (NotFoundException e){
      e.getMessage();
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }

  public void save(Reader body, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var post = gson.fromJson(body, Post.class);
    data = service.save(post);
    response.getWriter().print(gson.toJson(data));
  }

  public void removeById(long id, HttpServletResponse response) throws IOException {
    try{
      data = service.getById(id);
      //final var gson = new Gson();
      service.removeById(id);
      response.getWriter().print("Post "+gson.toJson(data)+" is deleted");
    } catch (NotFoundException e){
      e.getMessage();
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }
}
