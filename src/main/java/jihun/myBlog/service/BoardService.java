package jihun.myBlog.service;

import jihun.myBlog.controller.dto.BoardCreateForm;
import jihun.myBlog.controller.dto.BoardEditForm;
import jihun.myBlog.entity.Board;
import jihun.myBlog.exception.CustomException;
import jihun.myBlog.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static jihun.myBlog.exception.ErrorCode.BOARD_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> findBoards() {
        return boardRepository.findAll();
    }

    public Long saveBoard(BoardCreateForm form) {

        // todo: 현재 로그인 정보, 조회수(중복 방지)
        Board board = Board.builder()
                .title(form.getTitle())
                .content(form.getContent())
//                .author()
                .category(form.getCategory())
//                .viewCount()
                .build();

        return boardRepository.save(board).getId();
    }

    public Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new CustomException(BOARD_NOT_FOUND)
        );
    }

    @Transactional
    public void updateBoard(Long id, BoardEditForm form) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new CustomException(BOARD_NOT_FOUND)
        );
        board.updateBoard(form.getTitle(), form.getContent(), form.getCategory());
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
}
