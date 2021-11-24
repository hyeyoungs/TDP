package com.cdp.tdp.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass // Entity가 자동으로 컬럼으로 인식한다.
@EntityListeners(AuditingEntityListener.class) // 생성/변경 시간을 자동으로 업데이트한다.
public class Timestamped {

    @CreatedDate
    @JsonSerialize
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonSerialize
    private LocalDateTime modifiedAt;
}
