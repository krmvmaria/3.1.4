allUsers()

// достаем всех юзеров в таблицу
async function allUsers() {
    const info = document.querySelector('#allUsers');
    let output = '';
    const url = 'http://localhost:8080/api/admin'

    fetch(url, {mode: 'cors'})
        .then(res => res.json())
        .then(data => {
            data.forEach(user => {
                output += `
              <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.roles.map(e => " " + e.name)}</td>
              
               <td>
                   <button type="button" data-userid="${user.id}" data-action="edit" class="btn btn-info"
                    data-toggle="modal" data-target="modal" id="edit-user" data-id="${user.id}">Edit</button>
               </td>
               <td>
                   <button type="button" class="btn btn-danger" id="delete-user" data-action="delete" 
                   data-id="${user.id}" data-target="modal">Delete</button>
                    </td>    
              </tr>
            `
            })
            info.innerHTML = output;
        })
    //открытие модальных окон
    info.addEventListener('click', (e) => {
        e.preventDefault()

        if(e.target.id == 'delete-user'){
            $("#modalDelete").modal("show");
        } else if (e.target.id == 'edit-user'){
            $("#modalEdit").modal("show");
        }
    })
}






