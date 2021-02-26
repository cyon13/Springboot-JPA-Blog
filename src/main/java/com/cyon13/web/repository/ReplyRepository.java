package com.cyon13.web.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cyon13.web.dto.ReplySaveRequestDto;
import com.cyon13.web.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{

	@Modifying
	@Query(value = "INSERT INTO reply( userId, boardId, content, createDate) VALUES(?1,?2,?3,now())", nativeQuery = true)
	int mSave(int userId, int boardId, String content);
}
