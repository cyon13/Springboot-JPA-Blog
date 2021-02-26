package com.cyon13.web.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cyon13.web.dto.ReplySaveRequestDto;
import com.cyon13.web.model.Board;
import com.cyon13.web.model.Reply;
import com.cyon13.web.model.RoleType;
import com.cyon13.web.model.User;
import com.cyon13.web.repository.BoardRepository;
import com.cyon13.web.repository.ReplyRepository;
import com.cyon13.web.repository.UserRepository;

import lombok.RequiredArgsConstructor;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌.
@Service
public class Boardservice {

	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void write(Board board,User user) { // title, content
		board.setUser(user);
		boardRepository.save(board);
	}

	@Transactional(readOnly = true)
	public Page<Board> getList(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board detail(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()-> {
					return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을 수 없습니다.");
				});
		
	}
	@Transactional
	public void del(int id) {
		boardRepository.deleteById(id);
	}
	@Transactional
	public void update(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()-> {
					return new IllegalArgumentException("글 찾기 실패: 아이디를 찾을 수 없습니다.");
				}); // 영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수로 종료시(Service가 종료될 때) 트랜잭션이 종료된다. 이때 더티체킹 - 자동 업데이트가 됨. db flush
	}
	
	@Transactional
	public int replySave(ReplySaveRequestDto replySaveRequestDto) {
		int result = replyRepository.mSave(replySaveRequestDto.getUserId(),replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
		return result;
	}

	@Transactional
	public void delReply(int replyId) {
		replyRepository.deleteById(replyId);
	}


	
	/*
	 * @Transactional(readOnly = true) // Select할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료
	 * //(readOnly = true 라고 하면 트랜잭션 도중에는 정합성을 유지시켜준다) public User login(User user)
	 * { return userRepository.findByUsernameAndPassword(user.getUsername(),
	 * user.getPassword()); }
	 */


}
