package com.durin.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import com.durin.domain.Memo;
import com.durin.domain.Pagination;


public class MemosDto {

    private List<Memo> memos;

    private String pagination;

    public MemosDto() {
    }

    public MemosDto(List<Memo> memos, String pagination) {
        this.memos = memos;
        this.pagination = pagination;
    }

    public MemosDto(List<Memo> memos) {
        this.memos = memos;
    }

    public List<Memo> getMemos() {
        return memos;
    }

    public void setMemos(List<Memo> memos) {
        this.memos = memos;
    }

    public String getPagination() {
        return pagination;
    }

    public void setPagination(String pagination) {
        this.pagination = pagination;
    }

    public static MemosDto of(Page<Memo> postPage, Pagination pagination) {
        return new MemosDto(postPage.getContent(), pagination.makePagination(postPage.getTotalPages()));
    }

    public static MemosDto of(List<Memo> memos) {
        return new MemosDto(memos);
    }


    @Override
    public String toString() {
        return "MemosDto{" +
                "memos=" + memos +
                ", pagination='" + pagination + '\'' +
                '}';
    }
}
