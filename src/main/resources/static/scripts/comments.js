let gameId = document.getElementById('gameId').value
let commentForm = document.getElementById('commentForm')
commentForm.addEventListener("submit", handleFormSubmission)

const csrfHeaderName = document.head.querySelector('[name=_csrf_header]').content
const csrfHeaderValue = document.head.querySelector('[name=_csrf]').content


const commentsContainer = document.getElementById('comments-container')


window.onload = loadComments //load comments first - not async as adding new comments gets broken if we don't GET them first
//seems to work alright with the current async/non-async setup


async function handleFormSubmission(event) {
    event.preventDefault()

    const commentText = document.getElementById('comment').value

    try {
        fetch(`http://localhost:8080/api/games/${gameId}/comments`, { //await
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                [csrfHeaderName]: csrfHeaderValue


            },
            body: JSON.stringify({
                text: commentText
            })
        }).then(res => res.json()) //add newly created comment to the comments container
            .then(data => {
                //  commentsContainer.innerHTML += commentAsHtml(data) //can visualize the comment here
                commentForm.reset() //clear the textarea the more civilized way not by just setting the textarea's value to ""

                //clear the existing comments div to avoid duplicating comments
                commentsContainer.innerHTML = ''

                //load comments again
                loadComments() //no need to reload window, just clear the comments container and load comments anew

            })

    } catch (ex) {
        window.alert(ex.message)
    }
}

function commentAsHtml(comment) { //comment is an Object, get its text field
    let timeCreated = new Date(comment.dateTimeCreated) //String to date
    //Sample output: Sat Jul 30 2022 13:11:20 GMT+0300 (Eastern European Summer Time)

    let commentHtml = `<div class="comment-container">\n`
    commentHtml += `<h4>${timeCreated}</h4>\n`
    commentHtml += `<h4>Posted by: ${comment.authorUsername}</h4>\n`
    commentHtml += `<p>${comment.text}</p>\n`
    //  commentHtml += `<button class="like-button btn btn-info w-50">Like</button>\n` //Like button
    //commentHtml += `<div class="likes-counter-div">Likes: ${comment.likesCounter}</div>\n`
    commentHtml += `</div>\n`

    return commentHtml

}


//Seems to work fine w/o async
function loadComments() {
    try {
        fetch(`http://localhost:8080/api/games/${gameId}/comments`, { //` is for literal, needed to plonk gameId
            headers: {
                "Accept": "application/json"
            }
        }).then(res => res.json())
            .then(data => {
                for (let comment of data) {
                    commentsContainer.innerHTML += commentAsHtml(comment) //wasn't adding it...
                }
            })
    } catch (error) {
        document.getElementById("comment-errors").textContent = "Error";
        //TODO: remove these debuggers
        console.log(error.message)
        window.alert(error.message)
    }
}

