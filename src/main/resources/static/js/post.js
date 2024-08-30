var postId;

function setPostId(id) {
    postId = id;
}

function submitForm() {
    var form = document.getElementById('editForm');
    var formData = new FormData(form);

    fetch(form.action, {
        method: 'PUT',
        body: new URLSearchParams(formData),
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    }).then(response => {
        if (response.ok) {
            window.location.href = '/post/' + postId;
        } else {
            console.error('Error updating post');
        }
    }).catch(error => {
        console.error('Error:', error);
    });
}