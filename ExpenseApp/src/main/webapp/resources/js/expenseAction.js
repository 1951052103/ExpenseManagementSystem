/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function deleteExpense(endpoint, id, btn, msg, errMsg) {
    if (confirm(msg)) {
        let r = document.getElementById(`expense-row${id}`);
        let load = document.getElementById(`expense-load${id}`);
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

function updateExpense(endpoint, id, btn, msg, errMsg) {
    if (confirm(msg)) {
        let r = document.getElementById(`expense-row${id}`);
        let load = document.getElementById(`expense-updateLoad${id}`);
        load.style.display = "block";
        btn.style.display = "none";

        fetch(endpoint, {
            method: 'post',
            body: JSON.stringify({
                "amount": document.getElementById(`expense-amount-${id}`).value,
                "purpose": document.getElementById(`expense-purpose-${id}`).value,
                "date": document.getElementById(`expense-date-${id}`).value,
                "description": document.getElementById(`expense-description-${id}`).value,
                "approved": document.getElementById(`expense-approved-${id}`).value,
                "confirmed": document.getElementById(`expense-confirmed-${id}`).value
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