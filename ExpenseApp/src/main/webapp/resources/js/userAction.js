/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function deleteUser(endpoint, id, btn, msg, errMsg) {
    if (confirm(msg)) {
        let r = document.getElementById(`user-row${id}`);
        let load = document.getElementById(`user-load${id}`);
        load.style.display = "block";
        btn.style.display = "none";

        fetch(endpoint, {
            method: 'delete'
        }).then(function (res) {
            if (res.status !== 204) {
                alert(errMsg);
                btn.style.display = "block";
                load.style.display = "none";
            }
            else {
                load.style.display = "none";
                r.style.display = "none";
            }
        }).catch(function (err) {
            console.error(err);
            btn.style.display = "block";
            load.style.display = "none";
        });
    }
}

function updateUser(endpoint, id, btn, msg, errMsg) {
    if (confirm(msg)) {
        let r = document.getElementById(`user-row${id}`);
        let load = document.getElementById(`user-updateLoad${id}`);
        load.style.display = "block";
        btn.style.display = "none";

        fetch(endpoint, {
            method: 'post',
            body: JSON.stringify({
                "username": document.getElementById(`user-username-${id}`).value,
                "password": document.getElementById(`user-password-${id}`).value,
                "role": document.getElementById(`user-role-${id}`).value,
                "active": document.getElementById(`user-active-${id}`).value
            }),
            headers: {
                "Content-Type": "application/json"
            }
        }).then(function (res) {
            if (res.status !== 201) {
                alert(errMsg);
            }
            btn.style.display = "block";
            load.style.display = "none";
        }).catch(function (err) {
            console.error(err);
            btn.style.display = "block";
            load.style.display = "none";
        });
    }
}