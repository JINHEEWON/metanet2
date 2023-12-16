package com.team2.board.controller;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import com.team2.board.model.BoardUploadFile;
import com.team2.board.model.BoardVO;
import com.team2.board.service.IBoardService;
import com.team2.member.model.Member;
import com.team2.member.model.MemberUserDetails;
import com.team2.member.service.IMemberService;

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
@RequestMapping("/board")
public class BoardController {

	@Autowired
	IBoardService boardService;
	@Autowired
	IMemberService memberService;
	
	// 게시글 작성 파일 없이 
	@PostMapping(value="/create/{teamId}") 
	public BoardVO createBoard(@RequestBody BoardVO board, @PathVariable String teamId, Principal principal) {
		Member member = memberService.selectMember(principal.getName());
		if(!teamId.equals(Integer.toString(member.getTeamId()))) {
			return null; 
		}
		System.out.println(teamId);
		boardService.createBoard(board,boardService.maxBoardId());
		return board;
	}
	
	// 게시글 작성 파일 같이
	@PostMapping(value="/create_file/{teamId}") 
	public BoardVO createBoardWithFile(
			@RequestPart(value="file", required = false) MultipartFile file, 
			@RequestPart(value="board") BoardVO board,
			@PathVariable String teamId, Principal principal
	) {
		Member member = memberService.selectMember(principal.getName());
		if(!teamId.equals(Integer.toString(member.getTeamId()))) {
			return null; 
		}
		try{
			if(file!=null && !file.isEmpty()) {
				BoardUploadFile newfile = new BoardUploadFile();
				String fileName = file.getOriginalFilename();
				Long fileSize = file.getSize();
				String fileContentType = file.getContentType();
				byte[] bytes = file.getBytes();
				newfile.setFileName(fileName);
				newfile.setFileSize(fileSize);
				newfile.setFileContentType(fileContentType);
				newfile.setFileData(bytes);
				board.setFileName(fileName);
				board.setFileSize(fileSize);
				board.setFileContentType(fileContentType);
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
    public ResponseEntity uploadFile(@RequestPart MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        File destination = new File("upload/dir" + originalFileName);
        try {
            file.transferTo(destination);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(originalFileName);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(originalFileName);
    }
	
    // 파일 정보 가져오기 
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
	
	// 게시글 삭제 
	@DeleteMapping("/delete/{teamId}/{boardId}")
	public String deleteBoard(@PathVariable int boardId, @PathVariable String teamId, Principal principal) {
		Member member = memberService.selectMember(principal.getName());
		if(!teamId.equals(Integer.toString(member.getTeamId()))) {
			return null; 
		}
		try {
			boardService.deleteBoard(boardId);
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		return "success";
	}
	
	// 게시글 수정 
	@PutMapping("/update/{teamId}")
	public BoardVO putMethodName(@RequestBody BoardVO board, @PathVariable String teamId, Principal principal) {
		Member member = memberService.selectMember(principal.getName());
		if(!teamId.equals(Integer.toString(member.getTeamId()))) {
			return null; 
		}
		boardService.updateBoard(board);
		return board;
	}
	
	
	// 자유 게시판 조회 
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
    
    // 팀별 게시판 조회 
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
    
    // 전체 게시판 조회 ( 자유 + 팀별 ) 
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
    
    // 게시판 조회 
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
    
    // 게시글 검색
    // title, content, member 별
    // paging 처리  
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
    
    // 보드 팀 추가 
    @PostMapping("/create/board_team/{boardTeamId}")
    public int createBoardTeam(@PathVariable int boardTeamId) {
    	boardService.createBoardTeam(boardTeamId);
    	return boardTeamId;
    }
}
