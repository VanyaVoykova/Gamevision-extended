//const container = document.getElementById('playthroughs-comments-container');

let csrfHeaderName = document.head.querySelector('[name=_csrf_header]').content
let csrfHeaderValue = document.head.querySelector('[name=_csrf]').content

document.getElementById('playthroughs-btn').addEventListener('click', showPlaythroughs);
document.getElementById('comments-btn').addEventListener('click', showComments);

const playthroughsSection = document.getElementById('playthroughs-section');
const commentsSection = document.getElementById('comments-section');

function showPlaythroughs() {
    console.log("Playthroughs clicked")
    commentsSection.style.display = 'none'; //hide comments
    playthroughsSection.style.display = 'block'; //show playthroughs
}

function showComments() {
    playthroughsSection.style.display = 'none'; //show playthroughs
    commentsSection.style.display = 'block'; //hide comments
}