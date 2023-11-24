package com.ssafy.homes.domain.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Response 공통 반환 타입을 위한 클래스
 * Integer count => 반환하는 데이터 건수
 * T data        => 반환하는 데이터 타입
 *
 * 해당 클래스 .of 생성자를 통해 값을 설정하고 controller에서 반환한다.
 * ex) List<ResponseTestType> result = 해당 로직
 * CommonDto.of(result.size(), result)
 */
@Getter
@Setter
@RequiredArgsConstructor(staticName = "of")
public class CommonDto<T> {

    private final Integer count;
    private final T data;
}
