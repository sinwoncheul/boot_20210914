package com.example.restcontroller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Board;
import com.example.entity.BoardProjection;
import com.example.repository.BoardRepsoitory;

@RestController // RestController
@RequestMapping(value = "/api") // vue에서 연동되는 주소
public class ApiController {

    @Autowired
    BoardRepsoitory bRepository; // Board 저장소

    @Autowired
    ResourceLoader resourceLoader;

    @Value("${default.image}")
    private String DEFAULTIMAGE;

    // 삭제
    // /ROOT/api/board_delete?no=번호
    @RequestMapping(value = "/board_delete", method = RequestMethod.DELETE, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> boardDelete(@RequestParam(name = "no") long no) {
        Map<String, Long> map = new HashMap<>();
        try {
            bRepository.deleteById(no);
            map.put("result", 1L);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", 0L);
        }
        return map;

    }

    // 수정
    // /ROOT/api/board_update
    @RequestMapping(value = "/board_update", method = RequestMethod.PUT, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> boardUpdate(@RequestBody Board board) {
        Map<String, Long> map = new HashMap<>();
        try {
            Board board1 = bRepository.findById(board.getNo()).get();
            board.setImage(board1.getImage());
            board.setImagename(board1.getImagename());
            board.setImagesize(board1.getImagesize());
            board.setImagetype(board1.getImagetype());
            bRepository.save(board);
            map.put("result", 1L);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", 0L);
        }
        return map;

    }

    // 이전글
    @RequestMapping(value = "/board_select_prev", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> boardSelectPrev(@RequestParam(name = "no") long no) {
        Map<String, Long> map = new HashMap<>();
        map.put("result", 1L);
        map.put("prev", 0L);
        BoardProjection board1 = bRepository.findTop1ByNoLessThanOrderByNoDesc(no);
        if (board1 != null) { // 가져온 값이 있다면
            map.put("prev", board1.getNo());
        }
        return map;
    }

    // 다음글
    @RequestMapping(value = "/board_select_next", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> boardSelectNext(@RequestParam(name = "no") long no) {
        Map<String, Long> map = new HashMap<>();
        map.put("result", 1L);
        map.put("next", 0L);
        BoardProjection board1 = bRepository.findTop1ByNoGreaterThanOrderByNoAsc(no);
        if (board1 != null) { // 가져온 값이 있다면
            map.put("next", board1.getNo());
        }
        return map;
    }

    // 이미지
    // /ROOT/api/select_image
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
                } else if (board.getImagetype().equals("image/gif")) {
                    headers.setContentType(MediaType.IMAGE_GIF);
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

    // GET => /ROOT/api/board_select_one?no=글번호
    // 이미지는 전송하지 않음
    @RequestMapping(value = "/board_select_one", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> boardSelect(@RequestParam(name = "no") long no) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 파일관련 항목은 제외
            BoardProjection board = bRepository.findByNo(no);
            map.put("result", 1);
            map.put("data", board);
            map.put("imageurl", "/ROOT/api/select_image?no=" + no);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", 0);
        }
        return map;
    }

    // /ROOT/api/board_insert
    @RequestMapping(value = "/board_insert", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> boardInsert(@ModelAttribute Board board,
            @RequestParam(name = "file") MultipartFile file) {

        System.out.println(board.toString());
        Map<String, Object> map = new HashMap<>();
        try {
            // board => 제목, 내용, 작성자
            board.setImage(file.getBytes());
            board.setImagename(file.getOriginalFilename());
            board.setImagesize(file.getSize());
            board.setImagetype(file.getContentType());
            bRepository.save(board);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", 0);
        }
        return map;
    }

    // vue에서 /ROOT/api/board_select?page=1&title= 로 호출해야함
    // 반환되는 타입은 JSON포멧으로 반환됨.
    @RequestMapping(value = "/board_select", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> requestMethodName(@RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "title", defaultValue = "") String title) {

        // 페이지네이션 처리
        PageRequest pageable = PageRequest.of(page - 1, 10);

        // 필요 : 번호, 제목, 작성자, 조회수, 날짜
        // 이미지도 포함되어 있어서 BoardProjection으로 제거해야함.
        List<BoardProjection> list = bRepository.findByTitleIgnoreCaseContainingOrderByNoDesc(title, pageable);

        Map<String, Object> map = new HashMap<>();
        map.put("result", 1);
        map.put("data", list);
        return map;
    }

    //
    // RequestBody Map<> => RequestBody Board
    // /ROOT/api/update_hit { no:9 } => @RequestParam()
    @RequestMapping(value = "/update_hit", method = {
            RequestMethod.PUT }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Integer> updateOne(@RequestBody Map<String, Long> map1) {
        // { no:9 }
        System.out.println(map1.get("no"));

        // JSON변경하기 위한 map
        Map<String, Integer> map = new HashMap<>();
        try {
            Board board = bRepository.getById(map1.get("no"));
            board.setHit(board.getHit() + 1);
            bRepository.save(board);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", 0);
        }
        return map;
    }

    // /ROOT/board_select_one?no=11
    // @RequestParam("no") long no 받음

    // POST, PUT, DELETE은 body로 보낼수 있음
    // 이미지 포함되면 form-data =>
    // content-type:"multipart/form-data"
    // @ModelAttribute @RequestParam 받음

    // Content-type:"application/json"
    // JSON(vue, react) 으로 {"no":123,"title":"bbb"}
    // @RequestBody 로 받음
}
