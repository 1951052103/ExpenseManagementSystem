/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function showStats(ctx, labels, data, type, label) {
    let colors=[], borderColors=[];
    let r, g, b;
    for (let i = 0; i < labels.length; i++) {
        r = parseInt(Math.random()*255);
        g = parseInt(Math.random()*255);
        b = parseInt(Math.random()*255);
        colors.push(`rgba(${r}, ${g}, ${b}, 0.8)`);
        borderColors.push(`rgba(${r}, ${g}, ${b}, 1)`);
    }
    
    const myChart = new Chart(ctx, {
        type: type,
        data: {
            labels: labels,
            datasets: [{
                    label: label,
                    data: data,
                    backgroundColor: colors,
                    borderColor: borderColors,
                    borderWidth: 1
                }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}
