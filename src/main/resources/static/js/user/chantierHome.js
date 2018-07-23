var range = "(" + data.quantitesMonths[1].date + " - " + data.quantitesMonths[12].date + ")";
document.getElementById('range').innerText = range;
//initiate chart1
var mixedChart = new Chart('chart1', {
    type: 'bar',
    data: {
        datasets: [{
            label: "Consommation gasoil totale (L)",
            backgroundColor: 'rgb(66, 244, 176, 0.7)',
            borderColor: data.quantitesMonths.map(function (value) {
                return parseInt(value.quantity, 10) >= parseInt(value.consommationPrevue, 10) * 1.1 ? 'rgb(255, 0, 76)' : 'rgb(4, 150, 50)';
            }),
            borderWidth: 1.5,
            data: data.quantitesMonths.map(function (value) {
                return value.quantity;
            })
        }, {
            label: "Consommation gasoil prevue (L)",
            borderColor: 'rgb(43, 106, 206)',
            backgroundColor: 'rgb(43, 106, 206)',
            type: 'line',
            fill: false,
            borderWidth: 2,
            data: data.quantitesMonths.map(function (value) {
                return value.consommationPrevue;
            })
        }, {
            label: 'Consommation gasoil location (L)',
            backgroundColor: 'rgb(244, 211, 90, 0.7)',
            borderColor: 'rgb(244, 211, 90)',
            borderWidth: 1,
            data: data.quantitesMonths.map(function (value) {
                return value.quantiteLocation;
            })

        }, {
            label: "Quantité gasoil acheté (L)",
            borderColor: 'rgb(190, 65, 244)',
            backgroundColor: 'rgb(190, 65, 244, 0.7)',
            borderWidth: 2,
            data: data.quantitesMonths.map(function (value) {
                return value.gazoilAchete;
            })
        }, {
            label: "Quantité gasoil flotant (L)",
            borderColor: 'rgb(244, 65, 106)',
            backgroundColor: 'rgb(244, 65, 106, 0.7)',
            borderWidth: 2,
            data: data.quantitesMonths.map(function (value) {
                return value.gazoilFlotant;
            })
        }],
        labels: data.quantitesMonths.map(function (value) {
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
                scaleLabel: {
                    display: true,
                    labelString: 'Quantité de gasoile (L)'
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
mixedChart.getDatasetMeta(2).hidden = true;
mixedChart.getDatasetMeta(3).hidden = true;
mixedChart.getDatasetMeta(4).hidden = true;
mixedChart.update();
//initiate chart2
var mixedChart2 = new Chart('chart2', {
    type: 'bar',
    data: {
        datasets: [{
            label: 'Charge locataire totale (DH)',
            backgroundColor: 'rgb(43, 106, 206, 0.1)',
            borderColor: 'rgb(43, 106, 206)',
            borderWidth: 2,
            type: 'line',
            data: data.quantitesMonths.map(function (value) {
                return value.chargeLocataire;
            })
        }, {
            label: 'Charge locataire externe (DH)',
            backgroundColor: 'rgb(242, 48, 138, 0.1)',
            borderColor: 'rgb(242, 48, 138)',
            borderWidth: 2,
            data: data.quantitesMonths.map(function (value) {
                return value.chargeLocataireExterne;
            })
        }],
        labels: data.quantitesMonths.map(function (value) {
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
                scaleLabel: {
                    display: true,
                    labelString: 'Charge locataire (DH)'
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
var chart3 = new Chart('chart3', {
    type: 'bar',
    data: {
        datasets: [{
            label: "Consommation gasoil totale (L)",
            backgroundColor: 'rgb(66, 244, 176, 0.7)',
            borderColor: data.quantitesDays.map(function (value) {
                return parseInt(value.quantity, 10) >= parseInt(value.consommationPrevue, 10) * 1.1 ? 'rgb(255, 0, 76)' : 'rgb(4, 150, 50)';
            }),
            borderWidth: 1.5,
            data: data.quantitesDays.map(function (value) {
                return value.quantity;
            })
        }, {
            label: "Consommation gasoil prevue (L)",
            borderColor: 'rgb(43, 106, 206)',
            backgroundColor: 'rgb(43, 106, 206)',
            type: 'line',
            fill: false,
            borderWidth: 2,
            data: data.quantitesDays.map(function (value) {
                return value.consommationPrevue;
            })
        }, {
            label: 'Consommation gasoil location (L)',
            backgroundColor: 'rgb(244, 211, 90, 0.7)',
            borderColor: 'rgb(244, 211, 90)',
            borderWidth: 1,
            data: data.quantitesDays.map(function (value) {
                return value.quantiteLocation;
            })

        }, {
            label: "Quantité gasoil acheté (L)",
            borderColor: 'rgb(190, 65, 244)',
            backgroundColor: 'rgb(190, 65, 244, 0.7)',
            borderWidth: 2,
            data: data.quantitesDays.map(function (value) {
                return value.gazoilAchete;
            })
        }, {
            label: "Quantité gasoil flotant (L)",
            borderColor: 'rgb(244, 65, 106)',
            backgroundColor: 'rgb(244, 65, 106, 0.7)',
            borderWidth: 2,
            data: data.quantitesDays.map(function (value) {
                return value.gazoilFlotant;
            })
        }],
        labels: data.quantitesDays.map(function (value) {
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
                scaleLabel: {
                    display: true,
                    labelString: 'Quantité de gasoile (L)'
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
chart3.getDatasetMeta(2).hidden = true;
chart3.getDatasetMeta(3).hidden = true;
chart3.getDatasetMeta(4).hidden = true;
chart3.update();
//initiate chart4
var chart4 = new Chart('chart4', {
    type: 'bar',
    data: {
        datasets: [{
            label: 'Charge locataire totale (DH)',
            backgroundColor: 'rgb(43, 106, 206, 0.1)',
            borderColor: 'rgb(43, 106, 206)',
            borderWidth: 2,
            type: 'line',
            data: data.quantitesDays.map(function (value) {
                return value.chargeLocataire;
            })
        }, {
            label: 'Charge locataire externe (DH)',
            backgroundColor: 'rgb(242, 48, 138, 0.1)',
            borderColor: 'rgb(242, 48, 138)',
            borderWidth: 2,
            data: data.quantitesDays.map(function (value) {
                return value.chargeLocataireExterne;
            })
        }],
        labels: data.quantitesDays.map(function (value) {
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
                scaleLabel: {
                    display: true,
                    labelString: 'Charge locataire (DH)'
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
//initiate chart5
var chart5 = new Chart('chart5', {
    type: 'line',
    data: {
        datasets: [{
            label: 'Stock C (L)',
            backgroundColor: 'rgb(244, 65, 214, 0.1)',
            borderColor: 'rgb(244, 65, 214)',
            borderWidth: 2,
            data: data.stocks.map(function (value) {
                return value.stockC;
            })
        }, {
            label: 'Stock réel (L)',
            backgroundColor: 'rgb(107, 244, 66, 0.1)',
            borderColor: 'rgb(107, 244, 66)',
            borderWidth: 2,
            data: data.stocks.map(function (value) {
                return value.stockReel;
            })
        }],
        labels: data.stocks.map(function (value) {
            return value.date;
        })
    },
    options: {
        scales: {
            yAxes: [{
                scaleLabel: {
                    display: true,
                    labelString: 'Stock (L)'
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