package com.cdp.tdp.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass // Entity가 자동으로 컬럼으로 인식한다.
@EntityListeners(AuditingEntityListener.class) // 생성/변경 시간을 자동으로 업데이트한다.
public class Timestamped {

    @CreatedDate
    @JsonSerialize
    private String createdAt;

    @LastModifiedDate
    @JsonSerialize
    private String modifiedAt;

    @PrePersist
    public void onPrePersist(){
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.modifiedAt = this.createdAt;
    }

    @PreUpdate
    public void onPreUpdate(){
        this.modifiedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
