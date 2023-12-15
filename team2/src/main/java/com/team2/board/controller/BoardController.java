package com.team2.board.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
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
import org.springframework.web.bind.annotation.ResponseBody;
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
	public BoardVO testing(@RequestParam BoardVO board) {
		System.out.println(board);
		boardService.createBoard(board);
//		if(csrfToken==null || "".equals(csrfToken)) {
//			throw new RuntimeException("CSRF 토큰이 없습니다.");
//		}else if(!csrfToken.equals(session.getAttribute("csrfToken"))) {
//			throw new RuntimeException("잘 못된 접근이 감지되었습니다.");
//		}
		try{
			MultipartFile file = board.getFile();
			if(file!=null && !file.isEmpty()) {
				BoardUploadFile newfile = new BoardUploadFile();
				newfile.setFileName(file.getOriginalFilename());
				newfile.setFileSize(file.getSize());
				newfile.setFileContentType(file.getContentType());
				newfile.setFileData(file.getBytes());
				boardService.createBoard(board, newfile);
			}else {
				boardService.createBoard(board);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return new BoardVO();
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
		boardService.createReply(reply);
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

	@PutMapping("/update/{boardId}")
	public BoardVO putMethodName(@PathVariable int boardId, @RequestBody BoardVO board) {
		boardService.updateBoard(board);
		return board;
	}
	
	@PutMapping("/updateReply/{replyId}")
	public ReplyVO updateReply(@PathVariable int replyId, @RequestBody ReplyVO reply) {
		boardService.updateReply(reply);
		return reply;
	}
}
