package hello.itemservice.domain.item;

/* Enum : 멤버라 불리는 명명된 값의 집합을 이루는 자료형(열거형, 서로 연관된 상수들의 집합)
*         Java 1.5부터 사용 가능하며, 실제값 뿐만이 아니라 타입까지도 체크하여
*         열거체의 상수값이 재정의 되더라도 재 컴파일 할 필요가 없다.
*         #사용식 : public enum 변수명 { 상수명("상수값"), 상수명2("상수값2"), ...}*/


public enum ItemType {

    BOOK("도서"), FOOD("음식"), ETC("기타");

    /* 불규칙한 특정값 저장을 위해 생성된 인스턴스 변수*/
    private final  String description;

    public String getDescription() {
        return description;
    }

    /* 인스턴스 변수 사용을 위한 생성자 */
    ItemType(String description) {
        this.description = description;
    }
}
