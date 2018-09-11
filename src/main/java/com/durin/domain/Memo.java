package com.durin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.security.sasl.AuthenticationException;

import com.durin.dto.MemoDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Memo extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_memo_writer"))
    @JsonIgnore
    private User writer;

    @Column(nullable = false)
    private String title;

    @Lob
    private String content;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_memo_label"))
    @JsonIgnore
    private Label label;

    private boolean deleted = false;

    public Memo() {

    }

    public Memo(User writer, String title, String content, Label defaultLabel) {
        super(0L);
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.label = defaultLabel;
    }


    public User getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }


    public String getContent() {
        return content;
    }

    public String getModifiedDate() {
        return getFormattedModifiedDate();
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void isOwner(User loginUser) throws AuthenticationException {
        if (!writer.equals(loginUser)) {
            throw new AuthenticationException("본인만");
        }
    }

    public void update(User loginUser, MemoDto memoDto) throws AuthenticationException {
        isOwner(loginUser);
        this.title = memoDto.getTitle();
        this.content = memoDto.getContent();
    }

    public MemoDto toMemoDto() {
        return new MemoDto(getId(), title, content, getModifiedDate());
    }

    @Override
    public String toString() {
        return "Memo [writer=" + writer + ", title=" + title + ", content=" + content + ", label=" + label
                + ", deleted=" + deleted + "]";
    }


}
