package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.Board;
import com.example.repository.BoardRepsoitory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/board_api")
public class RestBoardController {

    @Autowired
    private BoardRepsoitory bRepository;

    // 127.0.0.1:8080/ROOT/board_api/select_one.json?no=1
    @RequestMapping(value = "/select_one.json", method = { RequestMethod.GET,
            RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, Object> selectOne(@RequestParam("no") long no) {
        Board board = bRepository.getById(no);

        Map<String, Object> map = new HashMap<>();
        map.put("no", board.getNo());
        map.put("title", board.getTitle());
        map.put("writer", board.getWriter());
        map.put("hit", board.getHit());
        map.put("regdate", board.getRegdate());

        return map;
    }

    // 127.0.0.1:8080/ROOT/board_api/update_hit.json?no=1
    @RequestMapping(value = "/update_hit.json", method = { RequestMethod.PUT,
            RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, Integer> updateOne(@RequestParam("no") long no) {
        Map<String, Integer> map = new HashMap<>();
        try {
            Board board = bRepository.getById(no);
            board.setHit(board.getHit() + 1);
            bRepository.save(board);
            map.put("ret", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("ret", 0);
        }
        return map;
    }

}