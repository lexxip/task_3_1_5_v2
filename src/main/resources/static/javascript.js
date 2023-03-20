const requestUrl = document.location.origin + '/api/v1/users'
const rolesUrl = document.location.origin + '/api/v1/roles'
const listRoles = new Map()

// Получение/отправка данных
async function sendRequest(url, method, body = null) {
    const headers = {
        'Content-Type': 'application/json'
    }
    try {
        const response = await fetch( url, {
            method:     method,
            headers:    headers,
            body:       body
        })
        if (method === 'DELETE') {
            return await response
        }
        return await response.json()
    } catch (e) {
        console.error(e)
    }
}

//Загрузка ролей
async function loadRoles(id) {
    const data = await sendRequest(rolesUrl + '/' + id, 'GET')
    let entr = Object.entries(data)
    listRoles.set(entr[0][1], entr[1][1])
}

// Заполнение модального окна
async function receiveData(id, form) {
    const data = await sendRequest(requestUrl + '/' + id, 'GET')
    clearRoles(document.querySelector('#edit-form'))

    for (let [key, value] of Object.entries(data)) {
        if (key === 'roles') {
            for (let key_ of Object.entries(value)) {
                form.elements['roles'].options[key_[1]].selected = true
            }
            return
        } else {
            form.elements[key].value = value
        }
    }
}

//Функция инициализации таблицы пользователей
async function init() {
    await listUsers()

    //Установка обратотчиков на кнопки Edit в таблице
    let editbutton = document.querySelectorAll('.edit-button')
    editbutton.forEach(function(item){
        /* Назначаем каждой кнопке обработчик клика */
        item.addEventListener('click', function(event) {
            let button = document.querySelector('#edit-button')
            button.setAttribute('class', 'btn btn-primary')
            button.innerHTML = 'Edit user'
            document.querySelector('#staticBackdropLabel').innerHTML = 'Edit user'
            document.querySelectorAll('.editable').forEach(function(item) {item.removeAttribute('disabled')})
            document.querySelector('#edit-form').method = 'PATCH'
            document.querySelector('[name="_method"]').value = 'PATCH'
            let userid = this.getAttribute('value')
            let form = document.querySelector('#edit-form')
            receiveData(userid, form)
        })
    })

    //Установка обратотчиков на кнопки Delete в таблице
    let deletebutton = document.querySelectorAll('.delete-button')
    deletebutton.forEach(function(item){
        item.addEventListener('click', function(event) {
            let button = document.querySelector('#edit-button')
            button.setAttribute('class', 'btn btn-danger')
            button.innerHTML = 'Delete user'
            document.querySelector('#staticBackdropLabel').innerHTML = 'Delete user'
            document.querySelectorAll('.editable').forEach(function(item) {item.setAttribute('disabled', true)})
            document.querySelector('#edit-form').method = 'DELETE'
            document.querySelector('[name="_method"]').value = 'DELETE'
            let userid = this.getAttribute('value')
            let form = document.querySelector('#edit-form')
            receiveData(userid, form)
        })
    })
}
document.addEventListener('DOMContentLoaded', async function() {
    init()
})

// init()

//Установка обратотчиков
document.addEventListener('DOMContentLoaded', async function() {

    // Установка 'submit' обработчика на таблицу в модальном окне
    let userFormEdit = document.querySelector('#edit-form' )
    userFormEdit.addEventListener('submit', async e => {
        e.preventDefault()
        let id = '/' + document.querySelector('#id').value
        let method = userFormEdit.getAttribute('method')
        let formData = formToJson(userFormEdit)

        try {
            const data = await sendRequest(requestUrl + id, method, formData)
            init()
        } catch (e) {
            console.error(e)
        }

        document.querySelector('#close-button').click()
    })

    // Установка 'submit' обработчика на таблицу вкладки Create
    let userFormCreate = document.querySelector('#create-form', )
    userFormCreate.addEventListener('submit', async e => {
        e.preventDefault()
        let formData = formToJson(userFormCreate)

        try {
            const data = await sendRequest(requestUrl, userFormCreate.getAttribute('method'), formData)
            init()
            document.querySelector('#home-tab').click()
            clearFormCreate()
            console.log(data)
        } catch (e) {
            console.error(e)
        }
    })

})

//Очистка таблицы
function clearList(tbody) {
    let n = tbody.children.length - 1
    for (let i = 0; i < n; i++) {
        tbody.children[1].remove()
    }
}

// Заполнение таблицы
async function listUsers() {
    const data = await sendRequest(requestUrl, 'GET')
    let tbody = document.querySelector('.list-users')
    clearList(tbody)

    for (let row of data) {
        let tr = tbody.children[0].cloneNode(true)
        let td = tr.children

        let i = 0
        let column = Object.entries(row)
        for (let [key, value] of column) {
            if (key === 'id') {
                td[6].children[0].value = value
                td[7].children[0].value = value
            }

            if (key === 'password') {
                break
            }

            td[i].innerHTML = value
            i++
        }

        let role = ''
        let roles = Object.entries(column[6][1])
        for (let [key, value] of roles) {
            if (!listRoles.has(value)) {
                await loadRoles(value)
            }
            role = role + listRoles.get(value).substring(5) + ' '
        }

        td[5].innerHTML = role
        tr.style.display = ''
        tbody.appendChild(tr)
    }
}

function clearFormCreate() {
    let input = document.querySelectorAll('#create-form input')
    for (let i = 1; i < input.length; i++) {
        input[i].value = ''
    }
    clearRoles(document.querySelector('#create-form'))
}

function clearRoles(form) {
    let i = 0
    while (form[i].name != 'roles') {
        i++
    }
    form[i][1].selected = false
    form[i][2].selected = false
}

//Преобразование formData в JSON
function formToJson(userForm) {
    const formData = new FormData(userForm)
    const normalizeValues = (key, values) => (values.length > 1 || key === 'roles') ? values : values[0]
    const object = {}

    for (const [key, value] of formData.entries()) {
        object[key] = normalizeValues(key, formData.getAll(key))
    }

    return JSON.stringify(object)
}
