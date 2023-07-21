const switchers = [...document.querySelectorAll('.switcher')]
switchers.forEach(item => {
    item.addEventListener('click', function() {
        switchers.forEach(item => item.parentElement.classList.remove('is-active'))
        this.parentElement.classList.add('is-active')
    })
})

document.getElementById('btn-signup').onclick = function() {
    alert("button was clicked");
}

