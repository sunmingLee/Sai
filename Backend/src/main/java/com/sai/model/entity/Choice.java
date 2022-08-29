package com.sai.model.entity;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "choice")
public class Choice {
    @Id
    @Column(name="choice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long choiceId;

    @NotBlank
    @Size(max = 40)
    private String text;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "poll_id", nullable = false)
    private Poll poll;

    public Choice() {

    }

    public Choice(String text) {
        this.text = text;
    }

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Choice choice = (Choice) o;
        return Objects.equals(choiceId, choice.choiceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(choiceId);
    }
}