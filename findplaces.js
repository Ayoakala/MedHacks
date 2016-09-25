var KEY = 'key=AIzaSyCm4iRoiJqxU5e9K47khVmNPN5K1_0Pdcw';

// will use rankby=distance, no radius necessary

/*
 * @input location: string(Lat),string(Long),string(Alt) obtained by Google geolocation API
 * @input intensity: severity of atmospheric condition - affects type of place
 *   e.g., low intensity => pharmacy, high => hospital
 */
function Request(location, intensity) {
    var link = 'https://maps.googleapis.com/maps/api/place/nearbysearch/json?';
    // define intensity threshold
    var threshold = 1;
    var type = (intensity < threshold) ? 'type=pharmacy&' : 'type=hospital&';
    var radius = 'rank=nearby&';
    var open = 'opennow&';
    var loc = 'location=' + location[0] + ',' + location[1] + ',' + location[2] + '&';

    link = link + loc + radius + type + open + KEY;
    print(link);
}

var loc = ["25.3511","50.094","9.2745636"];
var intensity = 1.8;

Request(loc, intensity);
