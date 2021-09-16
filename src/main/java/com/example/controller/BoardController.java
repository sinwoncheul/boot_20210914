package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Board;

import com.example.repository.BoardRepsoitory;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

    @Autowired
    private ResourceLoader resourceLoader;

    // 상수
    // import org.springframework.beans.factory.annotation.Value;
    @Value("${board.page.count}")
    private int PAGECNT;

    @Value("${default.image}")
    private String DEFAULTIMAGE;

    // 저장소 객체 생성
    @Autowired
    private BoardRepsoitory bRepository;

    // 127.0.0.1:8080/ROOT/board/select_image?no=번호
    // <img src="/board/select_image?no=12" / >
    @RequestMapping(value = "/select_image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> selectImage(@RequestParam("no") long no) throws IOException {
        try {
            Board board = bRepository.getById(no);
            if (board.getImage().length > 0) {
                HttpHeaders headers = new HttpHeaders();
                if (board.getImagetype().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (board.getImagetype().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                }

                // 클래스명 response = new 클래스명( 생성자선택 )
                ResponseEntity<byte[]> response = new ResponseEntity<>(board.getImage(), headers, HttpStatus.OK);
                return response;
            }
            return null;
        }
        // 오라클에 이미지를 읽을 수 없을 경우
        catch (Exception e) {
            InputStream is = resourceLoader.getResource(DEFAULTIMAGE).getInputStream();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            ResponseEntity<byte[]> response = new ResponseEntity<>(is.readAllBytes(), headers, HttpStatus.OK);
            return response;
        }
    }

    // 127.0.0.1:8080/ROOT/board/insert_image
    @RequestMapping(value = "/insert_image", method = RequestMethod.GET)
    public String insertImageGet() {
        return "board_insert_image";
    }

    @RequestMapping(value = "/insert_image", method = RequestMethod.POST)
    public String insertImagePost(@ModelAttribute Board board, @RequestParam(name = "file") MultipartFile file)
            throws IOException {
        // System.out.println(board.toString());
        // System.out.println(file.getOriginalFilename());
        // System.out.println(file.getSize());
        board.setImage(file.getBytes());
        board.setImagename(file.getOriginalFilename());
        board.setImagesize(file.getSize());
        board.setImagetype(file.getContentType());

        bRepository.save(board);

        return "redirect:select";
    }

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
