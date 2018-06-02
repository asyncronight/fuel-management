var range = "(" + data.quantites[1].date + " - " + data.quantites[12].date + ")";
document.getElementById('range').innerText = range;
document.getElementById('range2').innerText = range;
//initiate chart1
var mixedChart = new Chart('chart1', {
    type: 'bar',
    data: {
        datasets: [{
            label: 'Consommation gasoil location (L)',
            backgroundColor: 'rgb(244, 211, 90, 0.7)',
            borderColor: 'rgb(244, 211, 90)',
            borderWidth: 1,
            data: data.quantites.map(function (value) {
                return value.quantiteLocation;
            })

        }, {
            label: "Consommation gasoil totale (L)",
            backgroundColor: 'rgb(45, 45, 45, 0.7)',
            borderColor: data.quantites.map(function (value) {
                return parseInt(value.quantity, 10) >= parseInt(value.consommationPrevue, 10) * 1.1 ? 'rgb(255, 0, 76)' : 'rgb(4, 150, 50)';
            }),
            borderWidth: 1,
            data: data.quantites.map(function (value) {
                return value.quantity;
            })
        }, {
            label: "Consommation gasoil prevue (L)",
            borderColor: 'rgb(43, 106, 206)',
            backgroundColor: 'rgb(43, 106, 206)',
            type: 'line',
            fill: false,
            borderWidth: 2,
            data: data.quantites.map(function (value) {
                return value.consommationPrevue;
            })
        }, {
            label: "Quantité gasoil acheté (L)",
            borderColor: 'rgb(66, 244, 212)',
            backgroundColor: 'rgb(66, 244, 212, 0.7)',
            borderWidth: 2,
            data: data.quantites.map(function (value) {
                return value.gazoilAchete;
            })
        }, {
            label: "Quantité gasoil flotant (L)",
            borderColor: 'rgb(239, 69, 182)',
            backgroundColor: 'rgb(239, 69, 182, 0.7)',
            borderWidth: 2,
            data: data.quantites.map(function (value) {
                return value.gazoilFlotant;
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
        },
        responsive: true
    }
});
//initiate chart2
var mixedChart2 = new Chart('chart2', {
    type: 'line',
    data: {
        datasets: [{
            label: 'Charge locataire totale (DH)',
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
        },
        responsive: true
    }
});
//initiate chart3
var colors = data.chantierBatch.map(function () {
    var r = Math.floor(Math.random() * 255);
    var g = Math.floor(Math.random() * 255);
    var b = Math.floor(Math.random() * 255);
    return "rgb(" + r + "," + g + "," + b + ")";
});
var chart3 = new Chart('chart3', {
    type: 'bar',
    data: {
        datasets: [{
            label: "Consommation gasoil totale (L)",
            backgroundColor: colors,
            borderWidth: 2,
            data: data.chantierBatch.map(function (value) {
                return value.quantite;
            })
        }, {
            label: "Consommation gasoil locataire (L)",
            backgroundColor: colors,
            borderWidth: 2,
            data: data.chantierBatch.map(function (value) {
                return value.quantiteLocation;
            })
        }, {
            label: "Consommation gasoil prevue (L)",
            borderColor: 'rgb(43, 106, 206)',
            backgroundColor: 'rgb(43, 106, 206, 0.2)',
            type: 'line',
            borderWidth: 2,
            data: data.chantierBatch.map(function (value) {
                return value.consommationPrevue;
            })
        }],
        labels: data.chantierBatch.map(function (value) {
            return '';
        })
    },
    options: {
        title: {
            display: true,
            text: 'Consommation de gasoile en litre'
        },
        onClick: function (evt) {
            window.location = '/user/gazoil/chantier/' + data.chantierBatch[chart3.getElementAtEvent(evt)[0]._index].chantier.id;
        },
        tooltips: {
            mode: 'index',
            intersect: false
        },
        hover: {
            mode: 'index',
            intersect: false
        },
        responsive: true
    }
});
//initiate chart4
var chart4 = new Chart('chart4', {
    type: 'pie',
    data: {
        datasets: [{
            backgroundColor: colors,
            borderWidth: 2,
            data: data.chantierBatch.map(function (value) {
                return value.chargeLocataire;
            })
        }],
        labels: data.chantierBatch.map(function (value) {
            return value.chantier.nom + ' (' + value.chantier.adresse + ')';
        })
    },
    options: {
        title: {
            display: true,
            text: 'Charge locataire totale en dirham'
        },
        responsive: true,
        onClick: function (evt) {
            window.location = '/user/gazoil/chantier/' + data.chantierBatch[chart4.getElementAtEvent(evt)[0]._index].chantier.id;
        },
        legend: {
            labels: {
                fullWidth: false
            },
            position: 'left'
        }
    }
});