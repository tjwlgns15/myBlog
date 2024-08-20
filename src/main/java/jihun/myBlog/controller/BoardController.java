package jihun.myBlog.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jihun.myBlog.controller.dto.*;
import jihun.myBlog.entity.Board;
import jihun.myBlog.entity.Category;
import jihun.myBlog.service.CategoryService;
import jihun.myBlog.service.CommentService;
import jihun.myBlog.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;
    private final CategoryService categoryService;

    @GetMapping("")
    public String listForm(Model model) {
        List<Board> boards = boardService.findBoards();
        List<BoardListForm> boardListForms = new ArrayList<>();

        for (Board board : boards) {
            BoardListForm boardListForm = BoardListForm.builder()
                    .id(board.getId())
                    .title(board.getTitle())
//                    .author(board.getAuthor().getNickName())
                    .category(board.getCategory())
                    .createdAt(board.getCreatedAt())
//                    .viewCount(board.getViewCount())
                    .build();
            boardListForms.add(boardListForm);
        }
        model.addAttribute("boards", boardListForms);

        List<CategoryListForm> categoryListForms = categoryService.getCategoryListForms();

        model.addAttribute("categories", categoryListForms);
        return "board/boardList";
    }

    @GetMapping("/{id}")
    public String detailForm(@PathVariable Long id, Model model) {
        Board board = boardService.findBoard(id);
        BoardDetailForm detailForm = BoardDetailForm.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
//                .author(board.getAuthor())
                .category(board.getCategory())
//                .viewCount(board.getViewCount())
                .createdAt(board.getCreatedAt())
                .build();

        model.addAttribute("board", detailForm);
        return "board/boardDetail";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("boardCreateForm", new BoardCreateForm());

        List<CategoryListForm> categoryListForms = categoryService.getCategoryListForms();
        model.addAttribute("categories", categoryListForms);
        return "board/boardCreate";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute BoardCreateForm form, BindingResult result) {
        if (result.hasErrors()) {
            log.info("[Error] fail to save board: {}",result.getAllErrors());
            return "board/boardCreate";
        }
        Long boardId = boardService.saveBoard(form);
        return "redirect:/boards/" + boardId;
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Board board = boardService.findBoard(id);
        BoardEditForm editForm = BoardEditForm.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .category(board.getCategory())
                .build();
        model.addAttribute("boardForm", editForm);
        return "board/boardEdit";
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable Long id, @Valid @ModelAttribute BoardEditForm form, BindingResult result) {

        if (result.hasErrors()) {
            log.info("[Error] fail to edit board: {}",result.getAllErrors());
            return "board/boardEdit";
        }
        boardService.updateBoard(id, form);
        return "redirect:/boards/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return "redirect:/boards";
    }

}
