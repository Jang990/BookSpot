$(document).ready(function () {
    const keywordLengthMin = 2;
    let page = 0; // 현재 페이지 번호
    let isLoading = false; // 데이터 로딩 상태
    let debounceTimer; // 디바운스를 위한 타이머

    // 페이지 로드 시 'keywordInput' 필드에 값이 있으면 그 값으로 API 요청
    const initialKeyword = $("#keywordInput").val().trim();
    if (initialKeyword.length >= keywordLengthMin) {
        loadBooks(initialKeyword); // 데이터 로드
    } else {
        $("#resultContainer").append("<p>검색어를 입력해주세요.</p>");
    }

    // 검색창 입력 이벤트 (디바운스 적용)
    $("#keywordInput").on("input", function () {
        clearTimeout(debounceTimer); // 이전 타이머 취소
        const keyword = $(this).val().trim();

        // 디바운스: 0.5초 후 실행
        debounceTimer = setTimeout(function () {
            if (keyword.length < keywordLengthMin) {
                $("#resultContainer").empty(); // 결과 초기화
                $("#resultContainer").append("<p>검색어를 입력해주세요.</p>");
                page = 0;
                return;
            }

            // 로딩 메시지 표시
            $("#loadingMessage").show();  // "로딩중..." 메시지 표시
            $("#resultContainer").empty(); // 기존 결과 초기화
            page = 0; // 페이지 초기화
            loadBooks(keyword); // 데이터 요청
        }, 500); // 0.5초 대기
    });

    // AJAX 요청으로 데이터 로드
    function loadBooks(keyword) {
        if (isLoading) return; // 중복 요청 방지
        isLoading = true;

        $.ajax({
            url: "/api/book", // API 엔드포인트 수정
            type: "GET",
            data: {
                title: keyword, // 키워드 파라미터로 전달
                page: page,
                size: 10
            },
            success: function (response) {
                displayBooks(response.content);
                page++; // 페이지 증가
            },
            error: function (xhr, status, error) {
                console.error("Error fetching books:", error);
                $("#resultContainer").append("<p>검색 중 오류가 발생했습니다.</p>");
            },
            complete: function () {
                isLoading = false; // 로딩 상태 초기화
                $("#loadingMessage").hide(); // 로딩 메시지 숨김
            }
        });
    }

    // 데이터 표시
    function displayBooks(books) {
        const resultContainer = $("#resultContainer");

        if (books.size === 0) {
            resultContainer.append("<p>검색 결과가 없습니다.</p>");
            return;
        }

        books.forEach(book => {
            const bookItem = `
            <div class="book-item" data-title="${book.title}" data-id="${book.id}">
                <h3>${book.title}</h3>
                <p>ID: ${book.id}</p>
            </div>`;
            resultContainer.append(bookItem);
        });
    }
});
