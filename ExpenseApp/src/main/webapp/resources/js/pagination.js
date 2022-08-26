/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function setPageSize(event) {
    url = new URL(window.location.href);
    if (!url.searchParams.get('pageSize')) {
        url.searchParams.append('pageSize', `${event.target.value}`);
    }
    else {
        url.searchParams.set('pageSize', event.target.value); 
    }
    url.searchParams.set('page', 1); 
    window.location.href = url;
}