//console.log(data);
var mixedChart = new Chart('chart1', {
    type: 'bar',
    data: {
        datasets: [{
            label: "Consommation gasoil totale (L)",
            backgroundColor: 'rgb(66, 134, 244,0.2)',
            borderColor: 'rgb(66, 134, 244)',
            borderWidth: 1,
            data: data.quantites.map(function (value) {
                return value.quantity;
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
    }
});
var ctx2 = document.getElementById('chart2').getContext('2d');
var mixedChart2 = new Chart(ctx2, {
    type: 'line',
    data: {
        datasets: [{
            label: 'Charge locataire interne (DH)',
            backgroundColor: 'rgb(59, 237, 83, 0.2)',
            borderColor: 'rgb(59, 237, 83)',
            data: data.quantites.map(function (value) {
                return value.chargeLocataire;
            })
        }, {
            label: 'Charge locataire externe (DH)',
            backgroundColor: 'rgb(242, 48, 138, 0.2)',
            borderColor: 'rgb(242, 48, 138)',
            data: data.quantites.map(function (value) {
                return value.chargeLocataireExterne;
            })
        }],
        labels: data.quantites.map(function (value) {
            return value.date;
        })
    }
});