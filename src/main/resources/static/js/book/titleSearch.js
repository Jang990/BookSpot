$(document).ready(function () {
    let timer = null;

    $("#keywordInput").on("input", function () {
        const keyword = $(this).val().trim();

        // 입력이 멈출 때까지 기다림
        if (timer) {
            clearTimeout(timer); // 이전 타이머를 취소
        }

        // 0.5초 후 실행
        timer = setTimeout(function () {
            if (keyword.length >= 2) {
                searchBooks(keyword);
            } else {
                clearResults(); // 2글자 미만일 때 기존 결과를 지움
            }
        }, 500); // 500ms 대기
    });
});

// 책 검색 함수
function searchBooks(keyword) {
    $.ajax({
        url: "/api/book", // Spring API 엔드포인트
        type: "GET",
        data: {
            title: keyword,
            page: 0,
            size: 10
        },
        success: function (response) {
            displayBooks(response.content); // 데이터를 화면에 표시
        },
        error: function (xhr, status, error) {
            console.error("Error fetching books:", error);
        }
    });
}

// 화면에 데이터를 표시하는 함수
function displayBooks(books) {
    const resultContainer = $("#resultContainer");
    resultContainer.empty(); // 기존 결과를 지움

    if (books.length === 0) {
        resultContainer.append("<p>책을 찾을 수 없습니다.</p>");
    } else {
        books.forEach(book => {
            const bookItem = `<div class="book-item">
                <h3>${book.title}</h3>
                <p>ID: ${book.id}</p>
            </div>`;
            resultContainer.append(bookItem);
        });
    }
}

// 결과를 초기화하는 함수
function clearResults() {
    $("#resultContainer").empty();
    $("#resultContainer").append("<p>책을 검색해주세요.</p>");
}