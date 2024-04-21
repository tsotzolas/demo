// https://github.com/google/maps-for-work-samples/blob/master/samples/maps/OpenWeatherMapLayer/index.html



var map;
var geoJSON;
var request;
var gettingData = false;
var openWeatherMapKey = "1adce37709f3d61b6e3d6c3695204456"
function initializeWeather() {

    var mapOptions = {
        zoom: 9,
        center: new google.maps.LatLng(38,23)
    };
    map = PF('geoMapV').getMap();

    map.setOptions(mapOptions);
    // map = new google.maps.Map(document.getElementById('geoGmapV'),
    //     mapOptions);
    // Add interaction listeners to make weather requests
    google.maps.event.addListener(map, 'idle', checkIfDataRequested);
    // Sets up and populates the info window with details
    map.data.addListener('click', function(event) {
        infowindow.setContent(
            "<img src=" + event.feature.getProperty("icon") + ">"
            + "<br /><strong>" + event.feature.getProperty("city") + "</strong>"
            + "<br />Temperature " + Math.round(event.feature.getProperty("temperature") * 10) / 10 + "&deg;C"
            + "<br />Min / Max " + Math.round(event.feature.getProperty("min") * 10) / 10 + "&deg;C / "
            + Math.round(event.feature.getProperty("max") * 10) /10 + "&deg;C"
            + "<br />Humidity " + event.feature.getProperty("humidity") + "%"
            + "<br />Wind Speed " + event.feature.getProperty("windSpeed") + " m/s"
            + "<br />Wind Direction " + event.feature.getProperty("windDegrees") + "&deg"
            + "<br />" + event.feature.getProperty("weather")
        );
        infowindow.setOptions({
            position:{
                lat: event.latLng.lat(),
                lng: event.latLng.lng()
            },
            pixelOffset: {
                width: 0,
                height: -15
            }
        });
        infowindow.open(map);
    });

    // var gmap = PF('geoMapV').getMap();
    var myMarkers = map.markers;

    var i = 0;
    for (i = 0; myMarkers.length > i; i++) {
        myMarkers[i].setLabel(myMarkers[i].getTitle());
    }

}
var checkIfDataRequested = function() {
    // Stop extra requests being sent
    while (gettingData === true) {
        request.abort();
        gettingData = false;
    }
    getCoords();
};
// Get the coordinates from the Map bounds
var getCoords = function() {
    var bounds = map.getBounds();
    var NE = bounds.getNorthEast();
    var SW = bounds.getSouthWest();
    getWeather(NE.lat(), NE.lng(), SW.lat(), SW.lng());
};
// Make the weather request
var getWeather = function(northLat, eastLng, southLat, westLng) {
    gettingData = true;
    var requestString = "https//api.openweathermap.org/data/2.5/box/city?bbox="
        + westLng + "," + northLat + "," //left top
        + eastLng + "," + southLat + "," //right bottom
        + map.getZoom()
        + "&cluster=yes&format=json"
        + "&APPID=" + openWeatherMapKey;
    request = new XMLHttpRequest();
    request.onload = proccessResults;
    request.open("get", requestString, true);
    request.send();
};
// Take the JSON results and proccess them
var proccessResults = function() {
    console.log(this);
    var results = JSON.parse(this.responseText);
    if (results.list.length > 0) {
        resetData();
        for (var i = 0; i < results.list.length; i++) {
            geoJSON.features.push(jsonToGeoJson(results.list[i]));
        }
        drawIcons(geoJSON);
    }
};
var infowindow = new google.maps.InfoWindow();
// For each result that comes back, convert the data to geoJSON
var jsonToGeoJson = function (weatherItem) {
    var feature = {
        type: "Feature",
        properties: {
            city: weatherItem.name,
            weather: weatherItem.weather[0].main,
            temperature: weatherItem.main.temp,
            min: weatherItem.main.temp_min,
            max: weatherItem.main.temp_max,
            humidity: weatherItem.main.humidity,
            pressure: weatherItem.main.pressure,
            windSpeed: weatherItem.wind.speed,
            windDegrees: weatherItem.wind.deg,
            windGust: weatherItem.wind.gust,
            /*icon: "http://openweathermap.org/img/w/"
                + weatherItem.weather[0].icon  + ".png",*/
            icon: "../resources/icons/weather/" + weatherItem.weather[0].icon  + ".png",
            coordinates: [weatherItem.coord.Lon, weatherItem.coord.Lat]
        },
        geometry: {
            type: "Point",
            coordinates: [weatherItem.coord.Lon, weatherItem.coord.Lat]
        }
    };
    // Set the custom marker icon
    map.data.setStyle(function(feature) {
        return {
            icon: {
                url: feature.getProperty('icon'),
                anchor: new google.maps.Point(25, 25)
            }
        };
    });
    // returns object
    return feature;
};
// Add the markers to the map
var drawIcons = function (weather) {
    map.data.addGeoJson(geoJSON);
    // Set the flag to finished
    gettingData = false;
};
// Clear data layer and geoJSON
var resetData = function () {
    geoJSON = {
        type: "FeatureCollection",
        features: []
    };
    map.data.forEach(function(feature) {
        map.data.remove(feature);
    });
};

// google.maps.event.addDomListener(window, 'load', initializeWeather);
function showWeather(){

    // alert(2);
// google.maps.event.addDomListener(window, 'load', initializeWeather);
//     google.maps.event.addDomListener(window, 'load', initializeWeather);
    initializeWeather();
    // alert();
    // initialize();
}

function test(){

}