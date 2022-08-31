/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function deleteUserFromGroup(endpoint, id, btn, msg, errMsg) {
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
