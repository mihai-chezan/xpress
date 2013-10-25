$(function () {

    var lalala =  function(){
        $.getJSON("http://localhost:9090/service/graphs/moods/LAST_WEEK", function(data){
            chartOptions.series=data.series;
            $('#container').highcharts(chartOptions);
        })
    }

    var chartOptions = {
        title: {
            text: 'General mood over the last week',
            x: -20 //center
        },
        subtitle: {
            text: ':D',
            x: -20
        },
        xAxis: {
            categories: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fry',
                'Sat']
        },
        yAxis: {
            title: {
                text: 'Count'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: 'no'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: 'Happy',
            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
        }, {
            name: 'Unhappy',
            data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
        }, {
            name: 'Neutral',
            data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
        }],
        colors : [
                  '#65BD22',
                  '#ED0C1B',
                  '#EDB50C',
                  '#000000',
                  ]
    }
    lalala();

});


