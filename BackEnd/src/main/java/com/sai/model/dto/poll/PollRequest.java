package com.sai.model.dto.poll;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sai.model.entity.Board;

import lombok.Data;

@Data
public class PollRequest {
	
//	private Board board;
	private long boardId;
	
    @NotBlank
    @Size(max = 140)
    private String question;

    @NotNull
    @Size(min = 2, max = 6)
    @Valid
    private List<ChoiceRequest> choices;

    @NotNull
    @Valid
    private LocalDateTime expirationDateTime;
}
