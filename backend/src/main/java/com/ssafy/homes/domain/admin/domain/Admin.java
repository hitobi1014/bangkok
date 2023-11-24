package com.ssafy.homes.domain.admin.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@RequiredArgsConstructor
@Getter
public class Admin {
    @Id
    private Long id;
}
