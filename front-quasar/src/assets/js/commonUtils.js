
/**
 * 거래종류에 따라 화면에 보여질이름 처리
 */
export const convertHouseTranscationType = (transactionType) => {
    // SALE,LEASE,RENT
    let type = '';
    switch (transactionType) {
        case 'SALE':
            type = '매매'
            break;
        case 'LEASE':
            type = '전세'
            break;
        case 'RENT':
            type = '월세'
            break;
    }
    return type;
}

/**
 * 매물종류에 따라 화면에 보여질이름 처리
 */
export const convertHouseType = (houseType) => {
    // ONE, TWO, APT, VILLA, HOUSING, SHOPPING_MALL
    // SALE,LEASE,RENT
    let type = '';
    switch (houseType) {
        case 'ONE':
            type = '원룸'
            break;
        case 'TWO':
            type = '투룸'
            break;
        case 'APT':
            type = '아파트'
            break;
        case 'VILLA':
            type = '빌라'
            break;
        case 'HOUSING':
            type = '주택'
            break;
        case 'SHOPPING_MALL':
            type = '쇼핑몰'
            break;
    }
    return type;
}

/**
 * 금액단위를 3자리씩 끊어 간략하게 표현
 */
export const compactToMoneyUnit = (money) => {
    return new Intl.NumberFormat('ko-KR', {notation: 'compact', maximunSignificantDigits: 3})
        .format(money)
}

/**
 * 이미지가 없는경우 기본이미지 대체
 */
export const setDefaultImage = (fileName) => {
    // console.log(`setDefaultImage name => ${name}`)
    const urlPath = `/src/assets/image/${fileName}`
    return new URL(urlPath, import.meta.url).href;
}

/**
 * 휴대폰번호 포맷
 */
export const formatMobile = (target) => {
    target = target.replace(/[^0-9]/g, '')
        .replace(/(^02.{0}|^01.{1}|[0-9]{3,4})([0-9]{3,4})([0-9]{4})/g, "$1-$2-$3");
    return target
}