$(document).ready(function () {
    let page = 0; // 현재 페이지 번호
    let isLoading = false; // 데이터 로딩 상태
    let hasMore = true; // 추가 데이터 여부
    let debounceTimer; // 디바운스를 위한 타이머

    // 검색창 입력 이벤트 (디바운스 적용)
    $("#keywordInput").on("input", function () {
        clearTimeout(debounceTimer); // 이전 타이머 취소
        const keyword = $(this).val().trim();

        // 디바운스: 0.5초 후 실행
        debounceTimer = setTimeout(function () {
            if (keyword.length < 2) {
                $("#resultContainer").empty(); // 결과 초기화
                $("#resultContainer").append("<p>검색어를 입력해주세요.</p>")
                page = 0;
                hasMore = true;
                return;
            }

            // 새로운 검색어로 초기화
            $("#resultContainer").empty();
            page = 0;
            hasMore = true;
            loadMoreBooks(keyword);
        }, 500); // 0.5초 대기
    });

    // 스크롤 이벤트 리스너를 #resultContainer에 추가
    $("#resultContainer").on("scroll", function () {
        const container = $(this);
        const scrollTop = container.scrollTop();
        const scrollHeight = container[0].scrollHeight;
        const containerHeight = container.height();

        // 사용자가 스크롤 끝에 도달했을 때
        if (!isLoading && hasMore && scrollTop + containerHeight >= scrollHeight - 50) {
            const keyword = $("#keywordInput").val().trim();
            if (keyword.length >= 2) {
                loadMoreBooks(keyword);
            }
        }
    });

    // AJAX 요청으로 추가 데이터 로드
    function loadMoreBooks(keyword) {
        isLoading = true;
        $.ajax({
            url: "/api/book",
            type: "GET",
            data: {
                title: keyword,
                page: page,
                size: 10
            },
            success: function (response) {
                displayBooks(response.content);
                page++; // 다음 페이지로 이동
                hasMore = !response.last; // 마지막 페이지 여부
            },
            error: function (xhr, status, error) {
                console.error("Error fetching books:", error);
            },
            complete: function () {
                isLoading = false; // 로딩 상태 초기화
            }
        });
    }

    // 데이터 표시
    function displayBooks(books) {
        const resultContainer = $("#resultContainer");

        books.forEach(book => {
            const bookItem = `<div class="book-item">
                <h3>${book.title}</h3>
                <p>ID: ${book.id}</p>
            </div>`;
            resultContainer.append(bookItem);
        });
    }
});