/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function deleteIncome(endpoint, id, btn, msg, errMsg) {
    if (confirm(msg)) {
        let r = document.getElementById(`row${id}`);
        let load = document.getElementById(`load${id}`);
        load.style.display = "block";
        btn.style.display = "none";

        fetch(endpoint, {
            method: 'delete'
        }).then(function (res) {
            if (res.status !== 204)
                alert(errMsg);
            load.style.display = "none";
            r.style.display = "none";
        }).catch(function (err) {
            console.error(err);
            btn.style.display = "block";
            load.style.display = "none";
        });
    }
}

function updateIncome(endpoint, id, btn, msg, errMsg) {
    if (confirm(msg)) {
        let r = document.getElementById(`row${id}`);
        let load = document.getElementById(`updateLoad${id}`);
        load.style.display = "block";
        btn.style.display = "none";

        fetch(endpoint, {
            method: 'post',
            body: JSON.stringify({
                "amount": document.getElementById(`amount-${id}`).value,
                "source": document.getElementById(`source-${id}`).value,
                "date": document.getElementById(`date-${id}`).value,
                "description": document.getElementById(`description-${id}`).value
            }),
            headers: {
                "Content-Type": "application/json"
            }
        }).then(function (res) {
            if (res.status !== 201)
                alert(errMsg);
            btn.style.display = "block";
            load.style.display = "none";
        }).catch(function (err) {
            console.error(err);
            btn.style.display = "block";
            load.style.display = "none";
        });
    }
}