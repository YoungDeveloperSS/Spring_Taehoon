package young.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import young.board.PostService;
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

        System.out.println("확인 -> showPostList");
        for (Post post : posts) {
            System.out.println("확인 -> post의 id = " + post.getId());
        }
        return "post-list";
    }

    @GetMapping("/{id}")//양식맞나
    public String showPostDetail(@PathVariable Long id, Model model) {
        Post post = postService.findPost(id);
        model.addAttribute("post", post);
        //post -> post폼으로 변경(보여줘야 하는 정보만 보여주기 위해 사용. 이름을 뭐로 해야하지?)
        return "post";
    }

    @DeleteMapping("{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }

    @GetMapping("{id}/edit")
    public String editPostForm(@PathVariable Long id, Model model) {
        Post post = postService.findPost(id);
        PostEditForm form = new PostEditForm(post);
        model.addAttribute("form", form);
        return "post-edit-form";
    }

    @PostMapping("{id}/edit")
    public String editPost(@PathVariable Long id, @ModelAttribute PostEditForm postEditForm) { //modelAttribute로 받아야함.
        //데이터 검증
        postService.update(id, postEditForm.getTitle(), postEditForm.getWriter(),
                postEditForm.getContent(), postEditForm.getCategory());
        return "redirect:/posts/"+id;
    }

    @GetMapping("/new")
    public String createPostForm(Model model) {
        //생성폼createPostForm 객체
        model.addAttribute("form", new PostCreateForm());
        return "post-create-form";
    }

    @PostMapping("/new")
    public String createPost(@ModelAttribute PostCreateForm form) {
        //검증
        Long savedId = postService.save(form.getTitle(), form.getWriter(), form.getContent(), form.getCategory());
        return "redirect:/posts/"+savedId;
    }
}
