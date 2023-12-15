package com.team2.board.controller;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.team2.board.model.BoardUploadFile;
import com.team2.board.model.BoardVO;
import com.team2.board.model.ReplyVO;
import com.team2.board.service.IBoardService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/b")
public class BoardController {

	@Autowired
	IBoardService boardService;
	
//	@PostMapping(value="/create") 
//	public BoardVO testing(@RequestBody BoardVO board) {
//		System.out.println(board);
//		boardService.createBoard(board);
//		return board;
//	}
	
	@PostMapping(value="/create") 
	public BoardVO testing(@RequestBody BoardVO board) {
		boardService.createBoard(board,boardService.maxBoardId());
		return board;
	}
	
	// 게시판 작성시 파일을 같이 첨부할때
	@PostMapping(value="/create2") 
	public BoardVO testing2(@RequestPart(value="file", required = false) MultipartFile file, 
			@RequestPart(value="board") BoardVO board) {
		System.out.println(board);
//		if(csrfToken==null || "".equals(csrfToken)) {
//			throw new RuntimeException("CSRF 토큰이 없습니다.");
//		}else if(!csrfToken.equals(session.getAttribute("csrfToken"))) {
//			throw new RuntimeException("잘 못된 접근이 감지되었습니다.");
//		}
		try{
			//MultipartFile file = board.getFile();
			if(file!=null && !file.isEmpty()) {
				BoardUploadFile newfile = new BoardUploadFile();
				newfile.setFileName(file.getOriginalFilename());
				newfile.setFileSize(file.getSize());
				newfile.setFileContentType(file.getContentType());
				newfile.setFileData(file.getBytes());
				boardService.createBoard(board, newfile);
			}else {
				boardService.createBoard(board,boardService.maxBoardId());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return board;
	}
	
	// 파일만 따로 보낼때
    @PostMapping("/upload")
    public ResponseEntity upload(@RequestPart MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        File destination = new File("upload/dir" + originalFileName);
        try {
            file.transferTo(destination);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(originalFileName);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(originalFileName);
    }
	
	@GetMapping("/file/{fileId}")
	public ResponseEntity<byte[]> getFile(@PathVariable int fileId) {
		BoardUploadFile file = boardService.getFile(fileId);
		System.out.println(file.toString());
		final HttpHeaders headers = new HttpHeaders();
		String[] mtypes = file.getFileContentType().split("/");
		headers.setContentType(new MediaType(mtypes[0], mtypes[1]));
		headers.setContentLength(file.getFileSize());
		try {
			String encodedFileName = URLEncoder.encode(file.getFileName(), "UTF-8");
			headers.setContentDispositionFormData("attachment", encodedFileName);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return new ResponseEntity<byte[]>(file.getFileData(), headers, HttpStatus.OK);
	}
	

	@PostMapping(value="/createReply") 
	public ReplyVO test2(@RequestBody ReplyVO reply) {
		boardService.createReply(reply, boardService.maxReplyId());
		return reply;
	}
	
	@DeleteMapping("/delete/{boardId}")
	public String deleteBoard(@PathVariable int boardId) {
		try {
			boardService.deleteBoard(boardId);
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		return "success";
	}
	
	@DeleteMapping("/deleteReply/{replyId}")
	public String deleteReply(@PathVariable int replyId) {
		try {
			boardService.deleteReply(replyId);
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		return "success";
	}

	@PutMapping("/update")
	public BoardVO putMethodName(@RequestBody BoardVO board) {
		boardService.updateBoard(board);
		return board;
	}
	
	@PutMapping("/updateReply")
	public ReplyVO updateReply(@RequestBody ReplyVO reply) {
		boardService.updateReply(reply);
		return reply;
	}
	
    @GetMapping("/free")
    public List<BoardVO> getFreeBoardList(
            @RequestParam(name = "teamId", defaultValue = "1") int teamId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "per_page", defaultValue = "10") int perPage,
            @RequestParam(name = "order_by", defaultValue = "default") String orderBy) {

        List<BoardVO> boards = new ArrayList<>();
        int start = page * perPage;
        int end = page * perPage + perPage;
        System.out.println("teamID: " + teamId + "page: " + page + "per_page: " + perPage + "order_by: " + orderBy);
        try {
            boards = boardService.getBoardList(teamId, start, end, orderBy);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return boards;
    }
    
    @GetMapping("/team/{teamId}")
    public List<BoardVO> getBoardListSearchByTeamId(
            @PathVariable int teamId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "per_page", defaultValue = "10") int perPage,
            @RequestParam(name = "order_by", defaultValue = "default") String orderBy) {

        List<BoardVO> boards = new ArrayList<>();
        int start = page * perPage;
        int end = page * perPage + perPage;
        System.out.println("teamID: " + teamId + "page: " + page + "per_page: " + perPage + "order_by: " + orderBy);
        try {
            boards = boardService.getBoardList(teamId, start, end, orderBy);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return boards;
    }
    
    @GetMapping("/all") 
    public List<BoardVO> getAllBoardList(
            @RequestParam(name = "teamId", defaultValue = "-1") int teamId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "per_page", defaultValue = "10") int perPage,
            @RequestParam(name = "order_by", defaultValue = "default") String orderBy
    ) {
        List<BoardVO> boards = new ArrayList<>();
        int start = page * perPage;
        int end = page * perPage + perPage;
        try {
            boards = boardService.getBoardList(teamId, start, end, orderBy);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return boards;
    }
    
    @GetMapping("/{boardId}")
    public BoardVO getBoardInfo(@PathVariable int boardId) {
    	try {
        	BoardVO board = boardService.getBoardInfo(boardId);
    		board.setReadNum(board.getReadNum()+1);
    		boardService.updateBoard(board);
    		return board;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    @GetMapping("/search") 
    public List<BoardVO> getBoardListSearchByTitle(
            @RequestParam(name = "title", defaultValue = "") String title,
            @RequestParam(name = "content", defaultValue = "") String content,
            @RequestParam(name= "memberId", defaultValue = "") String memberId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "per_page", defaultValue = "10") int perPage,
            @RequestParam(name = "order_by", defaultValue = "default") String orderBy
    ) {
        List<BoardVO> boards = new ArrayList<>();
    	try {
            int start = page * perPage;
            int end = page * perPage + perPage;
    		boards = boardService.getBoardListSearch(title, content, memberId, start, end, orderBy);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return boards;
    }
    
    @PostMapping("/create/board_team/{boardTeamId}")
    public int createBoardTeam(@PathVariable int boardTeamId) {
    	boardService.createBoardTeam(boardTeamId);
    	return boardTeamId;
    }
}
