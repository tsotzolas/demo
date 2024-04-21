/*
 * Author: Tsotsolas George (tsotzolas@gmail.com)
 * Αρχείο το οποίο έχει όλα τα Highcharts αρχεία τα οποία χρησιμοποιούνται για τα τα στατιστικά της εφαρμογής.
 *
 * Για να μπορέσει να δούλέψει έχουμε βάλει στο xhtml της εφαρμογής κάποια hidden πεδία απο τα οποία πάμε και πέρνουμε
 * τα δεδομενα τα οποία χρειάζεται για να παίξουν τα διαγράμματα.
 */
/********************WAGONS STATISTICS**********************/

/****                                                  ****/

/**
 * Wagons Statistics
 * Wagon Per Type Bars
 */
function showWagonPerType() {

    var wagonTypes = (document.getElementById('valuesForm:wagonTypes')).value.split(',');
    var wagonData = JSON.parse(document.getElementById('valuesForm:wagonData').value);
    console.log(document.getElementById('valuesForm:wagonData').value);

    var wagonPerType = new Highcharts.Chart({
        chart: {
            renderTo: 'wagonPerType',
            zoomType: 'xy'
        },
        title: {
            text: 'Wagons per Type'
        },
        subtitle: {
            text: 'Source: Pearl'
        },
        xAxis: [{
            categories: wagonTypes,
            crosshair: true
        }],
        yAxis: [{ // Primary yAxis
            labels: {
                format: '{value}',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            },
            title: {
                text: 'Number Of Wagons',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            }
        }, { // Secondary yAxis

        }],
        tooltip: {
            shared: true
        },
        legend: {
            layout: 'vertical',
            align: 'left',
            x: 120,
            verticalAlign: 'top',
            y: 100,
            floating: true,
            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || 'rgba(255,255,255,0.25)'
        },
        series: [{
            name: 'Wagons',
            type: 'column',
            yAxis: 1,
            data: wagonData,
            dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                format: '{point.y:.0f}', // one decimal
                y: 10, // 10 pixels down from the top
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        }]
    });
}



/**
 * Wagons Statistics
 * Wagon Per Status Pie
 */
function showWagonPerStatusPieChart() {

    var wagonStatusPie = document.getElementById('valuesForm:wagonStatusDataPie').value;

    var wagonPerTypePie = new Highcharts.Chart({
        chart: {
            renderTo: 'wagonPerStatus',
            type: 'pie',
            options3d: {
                enabled: false,
                alpha: 45,
                beta: 0
            }
        },
        title: {
            text: 'Wagons per Status Percent (%) Value'
        },
        subtitle: {
            text: 'Source: Pearl'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,
                dataLabels: {
                    enabled: true,
                    format: '{point.name}'
                }
            }
        },
        series: [{
            type: 'pie',
            name: 'Status share',
            data: JSON.parse(wagonStatusPie)
        }]
    });
}


/**
 * Wagons Statistics
 * Wagon Per Lessors Pie
 */
function showChartLessorsPie() {

    var wagonLessorPie = (document.getElementById('valuesForm:wagonLessorPie').value);

    console.log(JSON.parse(wagonLessorPie));

    var lessorsPie = new Highcharts.Chart({
        chart: {
            renderTo: 'wagonPerLessor',
            type: 'pie',
            options3d: {
                enabled: false,
                alpha: 45,
                beta: 0
            }
        },
        title: {
            text: 'Wagons per Lessors Percent (%) Value'
        },
        subtitle: {
            text: 'Source: Pearl'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,
                dataLabels: {
                    enabled: true,
                    format: '{point.name}'
                }
            }
        },
        series: [{
            type: 'pie',
            name: 'Lessor share',
            data: JSON.parse(wagonLessorPie)
        }]
    });
}

/****                                                   ****/
/********************WAGONS STATISTICS**********************/


//Histock dummy data for testing and for
// var data1 = [
//     ["07 December 2017 00:00","2"],
//     ["23 February 2018 00:00","1"],
//     ["11 March 2018 00:00","1"],
//     ["11 May 2018 00:00","1"],
//     ["24 May 2018 00:00","1"],
//     ["17 June 2018 00:00","1"],
//     ["01 July 2018 00:00","1"],
//     ["09 July 2018 00:00","1"],
//     ["10 July 2018 00:00","1"],
//     ["11 July 2018 00:00","1"],
//     ["13 July 2018 00:00","2"],
//     ["16 July 2018 00:00","1"],
//     ["17 July 2018 00:00","1"],
//     ["19 July 2018 00:00","1"],
//     ["20 July 2018 00:00","1"],
//     ["22 July 2018 00:00","1"],
//     ["26 July 2018 00:00","5"],
//     ["27 July 2018 00:00","1"],
//     ["30 July 2018 00:00","2"],
//     ["03 August 2018 00:00","1"],
//     ["06 August 2018 00:00","3"],
//     ["09 August 2018 00:00","2"],
//     ["11 August 2018 00:00","1"],
//     ["13 August 2018 00:00","2"],
//     ["15 August 2018 00:00","1"],
//     ["16 August 2018 00:00","2"],
//     ["17 August 2018 00:00","4"],
//     ["20 August 2018 00:00","1"],
//     ["21 August 2018 00:00","3"],
//     ["22 August 2018 00:00","2"],
//     ["23 August 2018 00:00","3"],
//     ["26 August 2018 00:00","1"],
//     ["29 August 2018 00:00","1"],
//     ["31 August 2018 00:00","1"],
//     ["01 September 2018 00:00","1"],
//     ["03 September 2018 00:00","4"],
//     ["06 September 2018 00:00","6"],
//     ["09 September 2018 00:00","6"],
//     ["11 September 2018 00:00","4"],
//     ["13 September 2018 00:00","1"],
//     ["14 September 2018 00:00","3"],
//     ["15 September 2018 00:00","1"],
//     ["16 September 2018 00:00","2"],
//     ["20 September 2018 00:00","1"],
//     ["21 September 2018 00:00","1"],
//     ["22 September 2018 00:00","2"],
//     ["23 September 2018 00:00","1"],
//     ["24 September 2018 00:00","2"],
//     ["26 September 2018 00:00","3"],
//     ["27 September 2018 00:00","2"],
//     ["02 October 2018 00:00","1"],
//     ["03 October 2018 00:00","1"],
//     ["12 October 2018 00:00","2"],
//     ["13 October 2018 00:00","1"],
//     ["14 October 2018 00:00","2"],
//     ["15 October 2018 00:00","1"],
//     ["17 October 2018 00:00","1"],
//     ["18 October 2018 00:00","4"],
//     ["22 October 2018 00:00","3"],
//     ["25 October 2018 00:00","3"],
//     ["30 October 2018 00:00","1"],
//     ["31 October 2018 00:00","2"],
//     ["01 November 2018 00:00","1"],
//     ["03 November 2018 00:00","4"],
//     ["04 November 2018 00:00","1"],
//     ["05 November 2018 00:00","2"],
//     ["07 November 2018 00:00","1"],
//     ["08 November 2018 00:00","1"],
//     ["10 November 2018 00:00","1"],
//     ["11 November 2018 00:00","1"],
//     ["14 November 2018 00:00","8"],
//     ["18 November 2018 00:00","3"],
//     ["19 November 2018 00:00","2"],
//     ["20 November 2018 00:00","1"],
//     ["21 November 2018 00:00","4"],
//     ["24 November 2018 00:00","1"],
//     ["25 November 2018 00:00","3"],
//     ["27 November 2018 00:00","4"],
//     ["03 December 2018 00:00","2"],
//     ["04 December 2018 00:00","1"],
//     ["05 December 2018 00:00","3"],
//     ["07 December 2018 00:00","1"],
//     ["13 December 2018 00:00","1"],
//     ["14 December 2018 00:00","1"],
//     ["17 December 2018 00:00","2"],
//     ["18 December 2018 00:00","4"],
//     ["19 December 2018 00:00","3"],
//     ["20 December 2018 00:00","2"],
//     ["21 December 2018 00:00","2"],
//     ["22 December 2018 00:00","1"],
//     ["28 December 2018 00:00","7"],
//     ["02 January 2019 00:00","1"],
//     ["06 January 2019 00:00","1"],
//     ["09 January 2019 00:00","2"],
//     ["10 January 2019 00:00","1"],
//     ["11 January 2019 00:00","2"],
//     ["12 January 2019 00:00","2"],
//     ["14 January 2019 00:00","7"],
//     ["15 January 2019 00:00","1"],
//     ["16 January 2019 00:00","1"],
//     ["17 January 2019 00:00","4"],
//     ["18 January 2019 00:00","2"],
//     ["20 January 2019 00:00","2"],
//     ["21 January 2019 00:00","10"],
//     ["22 January 2019 00:00","1"],
//     ["24 January 2019 00:00","5"],
//     ["25 January 2019 00:00","1"],
//     ["26 January 2019 00:00","4"],
//     ["28 January 2019 00:00","2"],
//     ["29 January 2019 00:00","2"],
//     ["02 February 2019 00:00","1"],
//     ["03 February 2019 00:00","5"],
//     ["04 February 2019 00:00","2"],
//     ["05 February 2019 00:00","4"],
//     ["06 February 2019 00:00","3"],
//     ["08 February 2019 00:00","2"],
//     ["09 February 2019 00:00","3"],
//     ["10 February 2019 00:00","4"],
//     ["11 February 2019 00:00","4"],
//     ["12 February 2019 00:00","3"],
//     ["19 February 2019 00:00","2"]
// ];




