<%@ include file="/WEB-INF/jsp/includes/imports.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>eMu charts</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <link href="//netdna.bootstrapcdn.com/bootswatch/${bootstrapVersion}/cerulean/bootstrap.min.css" rel="stylesheet">
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css" rel="stylesheet">
    
    <style type="text/css">
        .page-content {
            min-height: 900px;
        }
    </style>
</head>
<body>
    <div class="page-body">
        <app:header />

        <div class="container page-content" >

             <div id="hatchers-24h-chart"></div>
             <div id="overall-24h-chart"></div>
        </div>

        <app:footer />

    </div>

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/${bootstrapVersion}/js/bootstrap.min.js"></script>

    <!--Load the AJAX API-->
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>

    <script type="text/javascript">

        NetworkPromotionsTag = function(listingId){
            this.graphContainerId = "network-promotions-graph";
            this.jsonEndpoint = "/analytics/data/" + listingId + "/months_inquiry.jsonp?mapped=true";


            this.areaChartOptions = {
                width: '720',
                height: '220',
                chartArea: {width: '85%', height: '80%'},
                legend: 'none',
                hAxis: {gridlines:{color: '#eee'}},
                vAxis: {minValue: 0, gridlines:{color: '#eee', count: 4}}
            };
        };

        // Load the Visualization API and the piechart package.
        google.load('visualization', '1.0', {'packages':['corechart']});

        // Set a callback to run when the Google Visualization API is loaded.
        google.setOnLoadCallback(function() {
            drawChart('Blocks hatched per hour', '/stats/charts/24hours.json', $('#hatchers-24h-chart'));
        });

        // Callback that creates and populates a data table,
        // instantiates the pie chart, passes in the data and
        // draws it.
        function drawChart(title, endpointUrl, domContainer) {
            var onError = function(jqXHR){
                //do nothing
            };

            var onSuccess = function(response, dataTable){
                if (response.errorCode === 0) {
                    var i;
                    var hatchers = response.data.hatchers;
                //    hatchers = hatchers.slice(0, 1);
                    var hatchersPerBlock = response.data.hatchersPerTimeBlock;

                    dataTable.addColumn('datetime', 'Time');
                    for (i = 0; i < hatchers.length; i++) {
                        dataTable.addColumn('number', hatchers[i]);
                    }

                    // timeBlock id is time in unix format (millis / 1000),
                    // which was later divided by 60 * 60 seconds
                    var multiplier = 1000 * 60 * 60;

                    $.each(hatchersPerBlock, function(timeBlock, hatcherStats) {
                        var row = [], i, count;

                        row[0] =  new Date( timeBlock * multiplier );


                        for (i = 0; i < hatchers.length; i++) {
                            count = hatcherStats[hatchers[i]];
                            row[i + 1] = count || 0;
                        }
                        dataTable.addRow(row);
                    });

                    var columns = [];
                    var series = {};
                    for (i = 0; i < dataTable.getNumberOfColumns(); i++) {
                        columns[i] = i;
                        if (i > 0) {
                            series[i - 1] = {};
                        }
                    }

                    // Set chart options
                    var options = {
                        'title': title,
                        'height': 900,
                        'series': series
                    };

                    // Instantiate and draw our chart, passing in some options.
                    var chart = new google.visualization.LineChart(domContainer[0]);
                    chart.draw(dataTable, options);


                    google.visualization.events.addListener(chart, 'select', function () {
                        var sel = chart.getSelection();
                        // if selection length is 0, we deselected an element
                        if (sel.length > 0) {
                            // if row is undefined, we clicked on the legend
                            if (sel[0].row === null) {
                                var col = sel[0].column;
                                var index = col < columns.length - 1 ? col + 1: col - 1;
                                if (columns[index] == index && columns[1] === 1) {
                                    // hide the data series
                                    for (i = 1; i < columns.length; i++) {
                                        if (col != i) {
                                            columns[i] = {
                                                label: dataTable.getColumnLabel(i),
                                                type: dataTable.getColumnType(i),
                                                calc: function () {
                                                    return null;
                                                }
                                            };
                                        }
                                    }

                                    // grey out the legend entry
                                    $.each(series, function(idx, el){
                                        el.color = '#CCC';
                                    });
                                    series[col - 1].color = null;


                                    var maxY = domContainer.find('g text[text-anchor="end"]:last').text() * 1;

                                    var yMod = {
                                        vAxis: {
                                            viewWindowMode:'explicit',
                                            viewWindow: {
                                                max: maxY,
                                                min: 0
                                            }
                                        }
                                    };

                                    options = $.extend({}, options, yMod);

                                } else {
                                    // show the data series
                                    for (i = 1; i < columns.length; i++) {
                                        columns[i] = i;
                                    }
                                    $.each(series, function(idx, el){
                                        el.color = null;
                                    });
                                }


                                var view = new google.visualization.DataView(dataTable);
                                view.setColumns(columns);
                                chart.draw(view, options);
                            }
                        }
                    });
                } else {
                    onError();
                }

            };

            var dataTable = new google.visualization.DataTable();

            $.ajax({
                url: endpointUrl,
                dataType: 'json',
                cache: false,
                type: 'GET',
                success: function(data) {
                    onSuccess(data, dataTable);
                },
                error: function(jqXHR) {
                    onError(jqXHR);
                }
            });

        }

        google.setOnLoadCallback(function() {
            drawChart('Overall', '/stats/charts/overall/24hours.json', $('#overall-24h-chart'));
        });
    </script>
</body>
</html>