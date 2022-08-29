package com.sai.model.dto.reply;


import java.time.LocalDateTime;

import com.sai.model.entity.Board;
import com.sai.model.entity.Reply;
import com.sai.model.entity.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ReplyDto {
	private Long replyId;

	private String replyContent;

	private LocalDateTime replyRegDateTime;

	private String userId;

	// �봽濡쒗븘 �궗吏� �씠誘몄� 寃쎈줈
	private String userImagePath;

	// �봽濡쒗븘 �궗吏� �씠誘몄� �씠由�
	private String userImageName;

	// �봽濡쒗븘 �궗吏� �씠誘몄� �냽�꽦
	private String userImageType;

	private Long boardId;

	// Entity to DTO
	public ReplyDto(Reply reply, User user, Board board) {
		this.replyId = reply.getReplyId();
		this.replyContent = reply.getReplyContent();
		this.replyRegDateTime = reply.getReplyRegDateTime();
		this.userId = user.getUserId();
		this.userImagePath = user.getUserImagePath();
		this.userImageName = user.getUserImageName();
		this.userImageType = user.getUserImageType();
		this.boardId = board.getBoardId();
	}

}
