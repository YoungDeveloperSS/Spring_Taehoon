package young.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import young.board.PostService;
import young.board.domain.Category;
import young.board.domain.Post;

import java.util.List;

//테스트 이후 controller -> service 보면서 해보자. 여기서 입력값 에러 다 잡아내야함. form, dto 만들기.
@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @GetMapping
    public String showPostList(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "post-list";
    }

    @GetMapping("/{id}")
    public String showPostDetail(@PathVariable Long id, Model model) {
        Post post = postService.findPost(id);
        model.addAttribute("post", post);
        return "post";
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }

    @GetMapping("/{id}/edit")
    public String editPostForm(@PathVariable Long id, Model model) {
        Post post = postService.findPost(id);
        PostEditForm form = PostEditForm.createPostEditForm(post);
        model.addAttribute("form", form);
        return "post-edit-form";
    }

    @PostMapping("/{id}/edit")
    public String editPost(@PathVariable Long id, @ModelAttribute PostEditForm form) {
        //TODO 검증 -> View에서도 검증 해줘야함.
        validatePostForm(form.getTitle(), form.getWriter(), form.getContent(), form.getCategory());
        postService.update(id, form.getTitle(), form.getWriter(),
                form.getContent(), form.getCategory());
        return "redirect:/posts/"+id;
    }

    @GetMapping("/new")
    public String createPostForm(Model model) {
        model.addAttribute("form", new PostCreateForm());
        return "post-create-form";
    }

    @PostMapping("/new")
    public String createPost(@ModelAttribute PostCreateForm form) {
        //TODO 검증 -> View에서도 검증 해줘야함.
        validatePostForm(form.getTitle(), form.getWriter(), form.getContent(), form.getCategory());
        Long savedId = postService.save(form.getTitle(), form.getWriter(), form.getContent(), form.getCategory());
        return "redirect:/posts/"+savedId;
    }

    @GetMapping("{id}/like")
    public String likePost(@PathVariable Long id) {
        postService.likePost(id);
        return "redirect:/posts/" + id;
    }

    @GetMapping("{id}/dislike")
    public String dislikePost(@PathVariable Long id) {
        postService.disLikePost(id);
        return "redirect:/posts/" + id;
    }

    private void validatePostForm(String title, String writer, String content, Category category) {
        if (title.isBlank() || writer.isBlank() ||
                content == null || category == null ||
                title.length() < 1 || title.length() > 20 ||
                writer.length() < 1 || writer.length() > 8) {
            throw new IllegalArgumentException("파라미터가 제대로 입력되지 않았습니다.");
        }
    }

}
