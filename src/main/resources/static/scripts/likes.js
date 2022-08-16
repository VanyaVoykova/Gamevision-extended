
// - detach section from DOM
//const section = document.getElementById('details');
//section.remove();

//const userData = JSON.parse(sessionStorage.getItem('userData'));
//
//
//let gameId = null;
//
//async function getMovie(id) {
//    currentMovieId = id;
//    section.replaceChildren(elCreator('p', {}, 'Loading...'));
//
//    const requests = [
//        fetch('http://localhost:3030/data/movies/' + currentMovieId),
//        fetch(`http://localhost:3030/data/likes?where=movieId%3D%22${currentMovieId}%22&distinct=_ownerId&count`),
//    ];
//
//    const userData = JSON.parse(sessionStorage.getItem('userData'));
//    if (userData != null) {
//        requests.push(fetch(`http://localhost:3030/data/likes?where=movieId%3D%22${currentMovieId}%22%20and%20_ownerId%3D%22${userData.id}%22`));
//    }
//
//    const [movieRes, likesRes, hasLikedRes] = await Promise.all(requests);
//    const [movieData, likes, hasLiked] = await Promise.all([
//        movieRes.json(),
//        likesRes.json(),
//        hasLikedRes && hasLikedRes.json() //if one of the two is undefined, this .json()
//    ]);
//
//    section.replaceChildren(createDetails(movieData, likes, hasLiked));
//}
//
//function createDetails(movie, likes, hasLiked) {
//    const controls = elCreator('div', { className: 'col-md-4 text-center' },
//        elCreator('h3', { className: 'my-3' }, 'Movie Description'),
//        elCreator('p', {}, movie.description)
//    );
//
//
//
//    if (userData != null) {
//        if (userData.id == movie._ownerId) {
//            controls.appendChild(elCreator('a', { className: 'btn btn-danger', id: 'delBtn', href: '#', onClick: onDelete }, 'Delete')); //TODO: check added id
//            controls.appendChild(elCreator('a', { className: 'btn btn-warning', id: 'editBtn', href: '#', onClick: onEdit }, 'Edit'));//TODO: check added id
//            //   document.getElementById('editBtn').addEventListener('click', (event) => onEdit);
//        } else {
//            if (hasLiked.length > 0) {
//                controls.appendChild(elCreator('a', { className: 'btn btn-primary', href: '#', onClick: onUnlike }, 'Unlike'));
//            } else {
//                controls.appendChild(elCreator('a', { className: 'btn btn-primary', href: '#', onClick: onLike }, 'Like'));
//            }
//        }
//    }
//    controls.appendChild(elCreator('span', { className: 'enrolled-span' }, `Liked ${likes}`));
//
//    const element = elCreator('div', { className: 'container' },
//        elCreator('div', { className: 'row bg-light text-dark' },
//            elCreator('h1', {}, `Movie title: ${movie.title}`),
//            elCreator('div', { className: 'col-md-8' },
//                elCreator('img', { className: 'img-thumbnail', src: movie.img, alt: 'Movie' })
//            ),
//            controls
//        )
//    );
//
//    return element;
//
//    async function onLike() {
//        await fetch('http://localhost:3030/data/likes', {
//            method: 'post',
//            headers: {
//                'Content-Type': 'application/json',
//                'X-Authorization': userData.token
//            },
//            body: JSON.stringify({
//                movieId: currentMovieId
//            })
//        });
//
//        showDetails(currentMovieId);
//    }
//
//    async function onUnlike() {
//        const likeId = hasLiked[0]._id;
//
//        await fetch('http://localhost:3030/data/likes/' + likeId, {
//            method: 'delete',
//            headers: {
//                'X-Authorization': userData.token
//            }
//        });
//
//        showDetails(currentMovieId);
//    }
//
//
//
//    function onEdit() {
//        showEdit();
//        //  event.preventDefault()
//
//
//        console.log('onEdit: ' + movie.title)
//
//        //COULDN'T FIGURE OUT HOW TO AUTO FILL FORM FIELDS WITH EXISTING MOVIE DATA, DATA WASN'T DISPLAYED IN THE FIELDS. NO TIME FOR MORE RESEARCH.
//        //const editSection = document.getElementById('edit-movie');
//        // const editForm = document.getElementById('edit-form');
//        // const formData = new FormData(editForm);
//        //Fill retrieved data in fields
//        // formData.set('title', movie.title);
//        // formData.set('description', movie.description);
//        // formData.set('imageUrl', movie.imageUrl);
//
//
//        const submitEditBtn = document.getElementById('submitEditBtn');
//
//        submitEditBtn.addEventListener('click', (event) => submitEdit(event));
//
//        //Here for visualization with onEdit and Closure for submitEdit
//        const titleInput = document.getElementById('title');
//        const descriptionInput = document.getElementById('description');
//        const imgUrlInput = document.getElementById('imageUrl');
//
//        //Fill in existing data for easier editing
//        titleInput.value = movie.title;
//        descriptionInput.value = movie.description;
//        imgUrlInput.value = movie.imageUrl;
//
//        // const title = formData.get('title').trim();
//        // const description = formData.get('description').trim();
//        // const imageUrl = formData.get('imageUrl').trim();
//        // const title = formData.get('title').trim();
//        // const description = formData.get('description').trim();
//        // const imageUrl = formData.get('imageUrl').trim();
//
//
//        async function submitEdit(event) {
//            event.preventDefault();
//            event.stopImmediatePropagation();
//
//            console.log('submitEdit: ' + movie.title)
//
//
//
//
//            //Trim in case they were edited
//            const title = titleInput.value.trim();
//            const description = descriptionInput.value.trim();
//            const imageUrl = imgUrlInput.value.trim();
//
//
//            if (!title || !description || !imageUrl) {
//                throw new Error('Please fill in all fields.');
//            }
//
//            // const userData = JSON.parse(sessionStorage.getItem('userData'));  //for POST request authorization
//
//            try {
//                const res = await fetch('http://localhost:3030/data/movies/' + currentMovieId, {
//                    method: 'put',
//                    headers: {
//                        'Content-Type': 'application/json',
//                        'X-Authorization': userData.token
//                    },
//                    body: JSON.stringify({ title, description, imageUrl })
//                });
//
//                if (res.ok != true) {
//                    const error = await res.json();
//                    alert('Put request response not ok')
//                    throw new Error(error.message);
//                }
//
//                //Clear input fields - can use it for debug, messed up id-s will get cleared fields
//                // editForm.reset();
//                // titleInput.value = '';
//                // descriptionInput.value = '';
//                // imgUrlInput.value = '';
//                //Redirect to Home
//                console.log('Id doesn\'t update properly, so in some cases the wrong movie gets edited.')
//                showHome();
//
//            } catch (err) {
//                alert('Error fetching put request')
//                alert(err.mesage);
//            }
//        }
//
//
//
//    }
//
//
//    async function onDelete() {
//        console.log(userData.token)
//        console.log('movie id on delete: ' + currentMovieId)
//        try {
//            const res = await fetch('http://localhost:3030/data/movies/' + currentMovieId, {
//                method: 'delete',
//                headers: {
//                    'Content-Type': 'application/json',
//                    'X-Authorization': userData.token
//                }
//
//            });
//
//            if (res.ok != true) {
//                const error = await res.json();
//                throw new Error(error.message);
//            }
//        } catch (err) {
//            alert(err.mesage);
//        }
//        showHome();
//
//    }
//
//
//}
//
//
//
//
//
//