// Get modal elements
const modal = document.getElementById('addCategoryModal');
const addBtn = document.getElementById('addCategoryBtn');
const closeBtn = document.getElementById('closeModal');
const submitBtn = document.getElementById('submitCategory');
const categoryInput = document.getElementById('newCategoryName');

// Open modal
addBtn.onclick = function() {
    modal.style.display = 'block';
}

// Close modal
closeBtn.onclick = function() {
    modal.style.display = 'none';
}

// Close modal if clicked outside
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = 'none';
    }
}

// Submit new category
submitBtn.onclick = function() {
    const newCategoryName = categoryInput.value.trim();
    if (newCategoryName) {
        fetch('/category/new', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ name: newCategoryName }),
        })
            .then(response => {
                if (!response.ok) {  // HTTP 응답 코드가 2xx가 아닌 경우 오류로 처리
                    return response.json().then(err => { throw err; });
                }
                return response.json();
            })
            .then(data => {
                console.log('Success:', data);
                // location.reload();
            })
            .catch((error) => {
                console.error('Error:', error);  // 서버에서 반환한 에러 메시지 출력
                alert(error.message || '카테고리 등록에 실패했습니다.');
            });

        modal.style.display = 'none';
        categoryInput.value = '';
    }
}
