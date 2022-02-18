package young.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import young.board.PostService;
import young.board.constants.PostConstant;
import young.board.domain.Category;
import young.board.domain.Post;

import java.util.List;

import static young.board.constants.PostConstant.*;
import static young.board.message.ErrorMessage.PARAM_FORM_ERROR;

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
        try {
            Post post = postService.findPost(id);
            model.addAttribute("post", post);
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
        return "post";
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id, Model model) {
        try {
            postService.deletePost(id);
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
        return "redirect:/posts";
    }

    @GetMapping("/{id}/edit")
    public String editPostForm(@PathVariable Long id, Model model) {
        try {
            Post post = postService.findPost(id);
            PostEditForm form = PostEditForm.createPostEditForm(post);
            model.addAttribute("form", form);
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
        return "post-edit-form";
    }

    @PostMapping("/{id}")
    public String editPost(@PathVariable Long id, @ModelAttribute PostEditForm form, Model model) {
        //TODO 검증 -> View에서도 검증 해줘야함.
        try {
            validatePostForm(form.getTitle(), form.getWriter(), form.getContent(), form.getCategory());
            postService.update(id, form.getTitle(), form.getWriter(),
                    form.getContent(), form.getCategory());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "redirect:/";
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
        return "redirect:/posts/" + id;
    }

    @GetMapping("/new")
    public String createPostForm(Model model) {
        model.addAttribute("form", new PostCreateForm());
        return "post-create-form";
    }

    @PostMapping
    public String createPost(@ModelAttribute PostCreateForm form, Model model) {
        //TODO 검증 -> View에서도 검증 해줘야함.
        try {
            validatePostForm(form.getTitle(), form.getWriter(), form.getContent(), form.getCategory());
            Long savedId = postService.save(form.getTitle(), form.getWriter(), form.getContent(), form.getCategory());
            return "redirect:/posts/" + savedId;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "redirect:/";
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
        //어? 리턴 없어도 돌아가나??
    }
//    @GetMapping("{id}/like")
//    public String likePost(@PathVariable Long id, Model model) {
//        try {
//            postService.likePost(id);
//        } catch (IllegalStateException e) {
//
//        }
//        return "redirect:/posts/" + id;
//    }
//
//    @GetMapping("{id}/dislike")
//    public String dislikePost(@PathVariable Long id, Model model) {
//        try {
//            postService.disLikePost(id);
//        } catch (IllegalStateException e) {
//            model.addAttribute("error", e.getMessage());
//            return "error-page";
//        }
//        return "redirect:/posts/" + id;
//    }

    private void validatePostForm(String title, String writer, String content, Category category) {
        if (title.isBlank() || writer.isBlank() ||
                content == null || category == null ||
                title.length() < TITLE_MIN_LENGTH || title.length() > TITLE_MAX_LENGTH ||
                writer.length() < WRITER_MIN_LENGTH || writer.length() > WRITER_MAX_LENGTH) {
            throw new IllegalArgumentException(PARAM_FORM_ERROR);
            //view에서 검증을 다해줬는데 여기에 들어왔다? 사용자의 악의적인 조작. -> 무슨 처리 해주지 말자. 바로 가장 최상단으로 보내기.
        }
    }

}
