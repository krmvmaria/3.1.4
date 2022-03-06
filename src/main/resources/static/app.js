const renderUsers = (users) => {
    users.forEach(user => {
        output += `
              <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.roles}</td>
                    <td>${user.password}</td>
                       
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
}

// достаем всех юзеров в таблицу
    const info = document.querySelector('#allUsers');
    let output = '';
    const url = 'http://localhost:8080/api/admin'

    fetch(url, {mode: 'cors'})
        .then(res => res.json())
        .then(data => renderUsers(data))

    //открытие модальных окон
    info.addEventListener('click', (e) => {
        e.preventDefault()
    })

// добавляем юзера (!) ПЕРЕДАТЬ РОЛЬ В JSON
const addUserForm = document.querySelector('#addUser')

const name3 = document.getElementById('name3')
const lastname3 = document.getElementById('lastname3')
const age3 = document.getElementById('age3')
const password3 = document.getElementById('password3')
const roles3 = document.getElementById('roles3')

addUserForm.addEventListener('submit', (e) =>{
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: name3.value,
            lastName: lastname3.value,
            age: age3.value,
            password: password3.value,
            roles: [
                roles3.value
            ]
        })
    })
        .then(res => res.json())
        .then(data => {
            const dataArr = [];
            dataArr.push(data);
            renderUsers(dataArr);
        })
})

//click all
const on = (element, event, selector, handler) => {
        element.addEventListener(event, e => {
            if(e.target.closest(selector)){
                handler(e)
            }
        })
}

// РЕДАКТИРОВАНИЕ (!) ТОЛЬКО ПОСЛЕ ОБНОВЛЕНИЯ СТРАНИЦЫ РЕЗУЛЬТАТ
on(document, 'click', '#edit-user', e => {
    const fila = e.target.parentNode.parentNode

    document.getElementById('id0').value = fila.children[0].innerHTML
    document.getElementById('name0').value = fila.children[1].innerHTML
    document.getElementById('lastName0').value = fila.children[2].innerHTML
    document.getElementById('age0').value = fila.children[3].innerHTML
    document.getElementById('roles0').value = fila.children[4].innerHTML
    document.getElementById('password0').value = fila.children[5].innerHTML
    option = 'editar'
    $("#modalEdit").modal("show")
})

const editUserForm = document.querySelector('#modalEdit')
editUserForm.addEventListener('submit', (e) =>{
    if(option=='editar'){
      fetch(url, {
          method: 'PUT',
          headers: {
              'Content-Type' : 'application/json'
          },
          body: JSON.stringify({
              id: document.getElementById('id0').value,
              name: document.getElementById('name0').value,
              lastName: document.getElementById('lastName0').value,
              age: document.getElementById('age0').value,
              password: document.getElementById('password0').value,
              roles: [
                  document.getElementById('roles0').value
              ]
          })
      })
          .then(response => response.json())
          .then( response => location.reload())
    }
    $("#modalEdit").modal("hide")
})
// редактирование конец

// удаление пользователя (!) УБРАТЬ ПЕРЕЗАГРУЗКУ
on(document, 'click', '#delete-user', e => {
    const deleteUserForm = document.querySelector('#modalDelete')
    const fila2 = e.target.parentNode.parentNode
    const id = fila2.firstElementChild.innerHTML

    document.getElementById('id2').value = fila2.children[0].innerHTML
    document.getElementById('name2').value = fila2.children[1].innerHTML
    document.getElementById('lastName2').value = fila2.children[2].innerHTML
    document.getElementById('age2').value = fila2.children[3].innerHTML
    document.getElementById('roles2').value = fila2.children[4].innerHTML

    $("#modalDelete").modal("show")

    deleteUserForm.addEventListener('submit', (e) =>{

        fetch('http://localhost:8080/api/admin/'+id, {
            method: 'DELETE'
        })
            .then(res => res.json())
   //         .then(() => location.reload())
            .then( data => {
                const newUser = []
                newUser.push(data)
                renderUsers(newUser)
            })
    })
})
// удаление пользователя конец









