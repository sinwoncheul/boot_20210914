package com.example.controller;

import java.util.List;
import java.util.Optional;

import com.example.entity.Board;
import com.example.repository.BoardRepsoitory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

    // 상수
    private final int PAGECNT = 10;

    // 저장소 객체 생성
    @Autowired
    private BoardRepsoitory bRepository;

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updatePost(@ModelAttribute Board board) {
        // 글번호(같으면 수정, 다르면 추가), 제목, 내용, 작성자
        System.out.println(board.toString());
        bRepository.save(board);

        return "redirect:select";
    }

    // http://127.0.0.1:8080/ROOT/board/update?no=17
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(@RequestParam(name = "no", defaultValue = "0") long no, Model model) {
        try {
            System.out.println(no);
            if (no == 0) {
                return "redirect:select";
            }
            Optional<Board> obj = bRepository.findById(no);
            if (obj.isPresent()) {
                Board board = obj.get();
                model.addAttribute("board", board);
            }
            return "board_update";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:select";
        }
    }

    // http://127.0.0.1:8080/ROOT/board/ delete?no=21
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(name = "no", defaultValue = "0") long no, Model model) {
        try {
            System.out.println(no);
            if (no == 0) {
                return "redirect:select";
            }
            bRepository.deleteById(no);
            return "redirect:select";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:select";
        }
    }

    // 127.0.0.1:8080/ROOT/board/select_one?no=13
    @RequestMapping(value = "/select_one", method = RequestMethod.GET)
    public String selectOne(@RequestParam(name = "no", defaultValue = "0") long no, Model model) {
        System.out.println(no);
        if (no == 0) { // 파라미터가 전달되지 못했음.
            return "redirect:select";
        }

        Optional<Board> obj = bRepository.findById(no);
        if (obj.isPresent()) {
            Board board = obj.get();

            // 다음글 객체
            Board board3 = bRepository.findTop1ByNo(no);
            if (board3 == null) {
                model.addAttribute("next", 0);
            } else {
                model.addAttribute("next", board3.getNo());
            }

            // 이전글 객체 가져오기
            Board board1 = bRepository.findTop1ByNoLessThanOrderByNoDesc(no);
            if (board1 == null) { // 가져온 값이 없다면
                model.addAttribute("prev", 0);
            } else {
                model.addAttribute("prev", board1.getNo());
            }

            model.addAttribute("board", board);
        }
        return "board_select_one";
    }

    // 127.0.0.1:8080/ROOT/board/select
    // 127.0.0.1:8080/ROOT/board/select?title=dfdfd&page=1
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public String select(Model model, @RequestParam(name = "title", required = false, defaultValue = "") String title,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {

        if (page == 0) {
            // 강제로 페이지 전환
            return "redirect:select?page=1";
        }

        // 글번호 최신순으로
        // List<Board> list1 = bRepository.findAll(Sort.by(Sort.Direction.DESC, "no"));

        // 페이지숫자(0부터), 개수
        PageRequest pageRequest = PageRequest.of(page - 1, PAGECNT);
        List<Board> list1 = bRepository.findByTitleContainingOrderByNoDesc(title, pageRequest);

        long cnt = bRepository.countByTitleContaining(title);
        model.addAttribute("cnt", (cnt - 1) / PAGECNT + 1);

        model.addAttribute("list", list1);
        return "board_select";
    }

    // 127.0.0.1:8080/ROOT/board/insert
    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public String insertGet() {

        Board b = new Board();
        b.setNo(4L);
        b.setWriter("aaa");

        bRepository.save(b);

        return "board_insert";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insertPost(@ModelAttribute Board board) {
        System.out.println(board.toString());
        bRepository.save(board);

        // 127.0.0.1:8080/ROOT/board/ (insert <= select)
        return "redirect:select";
    }
}