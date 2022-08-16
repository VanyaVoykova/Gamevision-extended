let csrfHeaderName = document.head.querySelector('[name=_csrf_header]').content
let csrfHeaderValue = document.head.querySelector('[name=_csrf]').content

const adminActionFormContainer = document.getElementById('admin-action-form-container') //This will get hoisted and the function below will still work if this is after it
//Hide form until an action is chosen from the admin nav
window.addEventListener('load', () => {
    adminActionFormContainer.style.display = 'none'
})

const actionBtns = document.querySelectorAll('.admin-nav')
actionBtns.forEach(btn => {
    btn.addEventListener('click', (event) => onNavChoiceVisualizeAction(event))
}) //pass the event so we can get the target, then the text from the target to determine the action

//here so we can define modify and use them in both functions below
let actionUrl = ''
let actionName = ''
//let method = '' all methods are PUT, no delete user


const actionTitleH = document.getElementById('admin-action-title')
const submitActionBtn = document.getElementById('submitActionBtn')

const adminActionForm = document.getElementById('admin-action-form')


//todo: TBI: ADD GAME still uses the old @Controller
//todo: TBI: prevent admins from demoting themselves

const usernameLengthErrorEl = document.getElementById('username-length-error')

//Hide form until an action is chosen in the navbar
function onNavChoiceVisualizeAction(event) { //Switch <h> and button text to display the chosen action
    event.preventDefault()

    const adminAction = event.target.textContent //check which action was chosen in the nav bar

    switch (adminAction) {
        case 'PROMOTE USER':
            actionTitleH.textContent = 'Promote user'
            submitActionBtn.value = 'Promote'
            actionUrl = '/admin/promote'
            actionName = 'promote'
            break
        case 'DEMOTE USER':
            actionTitleH.textContent = 'Demote user'
            submitActionBtn.value = 'Demote'
            actionUrl = '/admin/demote'
            actionName = 'demote'
            break
        case 'BAN USER':
            actionTitleH.textContent = 'Ban user'
            submitActionBtn.value = 'Ban'
            actionUrl = '/admin/ban'
            actionName = 'ban'
            break
        case 'UNBAN USER':
            actionTitleH.textContent = 'Unban user'
            submitActionBtn.value = 'Unban'
            actionUrl = '/admin/unban'
            actionName = 'unban'
            break
        default:
            window.alert('No action chosen.')
    }

    adminActionFormContainer.style.display = 'block' //show the adjusted form peppered with the data above to reflect the action choice

}

submitActionBtn.addEventListener('click', manageUser) //actions set up, add listener

async function manageUser(event) { //Why did I have actionUrl as param here?!?!!? It's taken through closure anyway...
    event.preventDefault() //That's why it was UNDEFINED!

    const inputEl = document.getElementById('username') //only needed here, don't get it too early or it may flub with falsy
    let username = inputEl.value.trim() //don't forget to trim!

    if (!username) { //falsy username
        usernameLengthErrorEl.textContent = 'Username must be between 3 and 20 characters.'
    } else if (username && username.length > 2 && username.length < 21) {

        if (window.confirm('Are you sure you want to ' + actionName + ' user ' + username + '?')) {
            try {
                const options = {
                    method: "PUT",
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json',
                        [csrfHeaderName]: csrfHeaderValue
                    },
                    body: JSON.stringify({username: username})
                }

                const response = await fetch(actionUrl, options)
                const data = await response.json()

                if (!data) { //If for some reason it's falsy
                    window.alert(data.message)
                } else if (data.errorCode === 1004) { //User NOT found, returned by the @ExceptionHandler in  @RestController
                    window.alert('User ' + username + ' does not exist.')
                } else {
                    let pastParticiple = actionName.includes('ban') ? actionName + 'ned' : actionName + 'd'
                    window.alert("User " + username + " was " + pastParticiple + ".")
                }

            } catch (ex) {
                window.alert(ex.message)
            }

            adminActionForm.reset() //clear the form / input field
            //clear error visualization
            usernameLengthErrorEl.innerHTML = ''

        } else {
            return false //doesn't like just return
        }

    }

}


