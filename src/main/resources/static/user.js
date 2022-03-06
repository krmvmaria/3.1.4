let output = '';
const info = document.querySelector('#User');
const url = 'http://localhost:8080/api/user'

fetch(url)
    .then(res => res.json())
    .then(data => {
        data.forEach(user => {
            output += `
                    <td>${user}</td> 
            `
    })
        info.innerHTML = output;
})




