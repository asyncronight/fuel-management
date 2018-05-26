var range = "(" + data.quantites[1].date + " - " + data.quantites[12].date + ")";
document.getElementById('range').innerText = range;
var mixedChart = new Chart('chart1', {
    type: 'bar',
    data: {
        datasets: [{
            label: "Consommation gasoil totale (L)",
            backgroundColor: 'rgb(7, 102, 255,0.2)',
            borderColor: data.quantites.map(function (value) {
                return parseInt(value.quantity, 10) > parseInt(value.consommationPrevue, 10) ? 'rgb(255, 0, 76)' : 'rgb(76, 220, 0)';
            }),
            borderWidth: 1,
            data: data.quantites.map(function (value) {
                return value.quantity;
            })
        }, {
            label: "Consommation gasoil prevue (L)",
            backgroundColor: 'rgb(0, 212, 255)',
            borderColor: 'rgb(0, 212, 255)',
            type: 'line',
            fill: false,
            borderWidth: 1,
            data: data.quantites.map(function (value) {
                return value.consommationPrevue;
            })
        }, {
            label: 'Consommation gasoil location (L)',
            backgroundColor: 'rgb(249, 140, 62, 0.2)',
            borderColor: 'rgb(249, 140, 62)',
            borderWidth: 1,
            data: data.quantites.map(function (value) {
                return value.quantiteLocation;
            })

        }],
        labels: data.quantites.map(function (value) {
            return value.date;
        })
    },
    options: {
        title: {
            display: true,
            text: 'Consommation gasoil en litre'
        },
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        },
        tooltips: {
            mode: 'index',
            intersect: false
        },
        hover: {
            mode: 'index',
            intersect: false
        }
    }
});
var ctx2 = document.getElementById('chart2').getContext('2d');
var mixedChart2 = new Chart(ctx2, {
    type: 'line',
    data: {
        datasets: [{
            label: 'Charge locataire interne (DH)',
            backgroundColor: 'rgb(43, 106, 206, 0.1)',
            borderColor: 'rgb(43, 106, 206)',
            borderWidth: 2,
            data: data.quantites.map(function (value) {
                return value.chargeLocataire;
            })
        }, {
            label: 'Charge locataire externe (DH)',
            backgroundColor: 'rgb(242, 48, 138, 0.1)',
            borderColor: 'rgb(242, 48, 138)',
            borderWidth: 2,
            data: data.quantites.map(function (value) {
                return value.chargeLocataireExterne;
            })
        }],
        labels: data.quantites.map(function (value) {
            return value.date;
        })
    },
    options: {
        title: {
            display: true,
            text: 'Charge locataire en dirham'
        },
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        },
        tooltips: {
            mode: 'index',
            intersect: false
        },
        hover: {
            mode: 'index',
            intersect: false
        }
    }
});
