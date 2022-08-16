//got to be let, so we can use one script for all deletes and, when we have more than one script per template, the lets can be overwritten
//let csrfHeaderName = document.head.querySelector('[name=_csrf_header]').content
//let csrfHeaderValue = document.head.querySelector('[name=_csrf]').content
//
const deleteButtons = document.querySelectorAll('.delete-btn')
//try with fetch GET to avoid having to use a REST controller

deleteButtons.forEach(btn => {
    btn.addEventListener('click', (event) => onDelete(event))
})

let id = ''
let confirmationMessage = ''
let deleteUrl = ''

async function onDelete(event) {
    event.preventDefault()
    let deleteBtnClassList = event.target.classList

    if (deleteBtnClassList.contains("delete-game-btn")) {
        confirmationMessage = 'Are you sure you want to delete this game?'
        id = event.target.id
        deleteUrl = '/games/' + id + '/delete'

        if (window.confirm(confirmationMessage)) {
            try {
                await fetch(deleteUrl); //DELETE was a GET afaik, and no response was required...
                //with replace()  it is not possible to use the "back" button to navigate back to the original document,
                // which suits us as that's the page of the deleted game
                window.location.replace('/games/all')
            } catch (ex) {
                window.alert(ex.message)
            }
        }

    } else if (deleteBtnClassList.contains('delete-playthrough-btn')) {
        confirmationMessage = 'Are you sure you want to delete this playthrough?'
        id = event.target.id //the playthrough id

        let gameId = document.querySelector('.hidden-info').id

        deleteUrl = '/games/' + gameId + '/playthroughs/' + id + '/delete'

        if (window.confirm(confirmationMessage)) {
            try {
                await fetch(deleteUrl) //DELETE was a GET afaik, and no response was required...
                //with replace()  it is not possible to use the "back" button to navigate back to the original document,
                // which suits us as that's the page of the deleted game

                window.location.replace('/games/' + gameId + '/playthroughs/all') //had skipped first / before games and got it double
            } catch (ex) {
                window.alert(ex.message)
            }
        }


    }

}


//todo - delete comments - let at least the admin delete them
// deleteBtnClass.includes('delete-comment-btn'):
//      confirmationMessage = 'Are you sure you want to delete this comment?'
//      id = event.target.id //the comment's id; how to get the game's id?
//      // Try putting it in some hidden element;
//      //Also have to check if admin or author, only they can delete it
//      //prolly can be done with Thymelieaf and also put in a hidden element
//      deleteUrl = '/games/' + id + '/comments/' + id + '/delete'

// } else {
//     window.alert('Invalid target.')
// }
//
//}








