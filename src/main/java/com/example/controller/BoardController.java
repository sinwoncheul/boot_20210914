package com.example.controller;

import java.util.List;

import com.example.entity.Board;
import com.example.repository.BoardRepsoitory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

    // 저장소 객체 생성
    @Autowired
    private BoardRepsoitory bRepository;

    // 127.0.0.1:8080/ROOT/board/select
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public String select(Model model) {
        // 정렬(X)
        List<Board> list = bRepository.findAll();

        // 글번호 최신순으로
        List<Board> list1 = bRepository.findAll(Sort.by(Sort.Direction.DESC, "no"));

        model.addAttribute("list", list);
        return "board_select";
    }

    // 127.0.0.1:8080/ROOT/board/insert
    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public String insertGet() {
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
