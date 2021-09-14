package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.entity.Board;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    // http://127:0.0.1:8080/ROOT/
    @RequestMapping(value = { "/", "home" }, method = RequestMethod.GET)
    public String home(Model model) {

        String title = "제목입니다.";
        int num = 120;

        Board board = new Board();
        board.setNo(1L);
        board.setTitle("가나다");
        board.setContent("내용");

        Board board1 = new Board();
        board1.setNo(2L);
        board1.setTitle("가나다");
        board1.setContent("내용");

        Board board2 = new Board();
        board2.setNo(3L);
        board2.setTitle("가나다");
        board2.setContent("내용");

        List<Board> list = new ArrayList<>();
        list.add(board);
        list.add(board1);
        list.add(board2);

        model.addAttribute("li", list);

        model.addAttribute("ti", title);
        model.addAttribute("nu", num);
        model.addAttribute("obj", board);

        return "home";
    }

}
