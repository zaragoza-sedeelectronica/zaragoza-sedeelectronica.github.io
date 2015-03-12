var osm = L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 18,
    attribution: '&copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
        '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>'
});

var idezar = L.tileLayer.wms("http://idezar.zaragoza.es/wms/IDEZar_base/IDEZar_base", {
    crs: L.CRS.EPSG4326,
    layers: 'base',
    format: 'image/png',
    transparent: false,
    maxZoom: 20,
    version: '1.1.0',
    attribution: '&copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
        '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
        ' &copy; <a href="http://www.zaragoza.es/">Ayuntamiento de Zaragoza</a>'
});

var catastro = L.tileLayer.wms("http://ovc.catastro.meh.es/Cartografia/WMS/ServidorWMS.aspx", {
    crs: L.CRS.EPSG4326,
    layers: 'Catastro',
    format: 'image/png',
    transparent: true,
    maxZoom: 20,
    version: '1.1.1',
    attribution: '&copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
        '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
        ' &copy; <a href="http://www.zaragoza.es/">Ayuntamiento de Zaragoza</a>'
});



var map = L.map('mapa-leaflet',{center: [41.655, -0.877],zoom: 13,layers: [idezar]});

var baseMaps = {
    "IDEZar": idezar,
    "OpenStreetMap": osm
};
var overlays = {
    "Catastro": catastro
};

L.control.layers(baseMaps,overlays).addTo(map);


// Closure
(function(){

    /**
     * Decimal adjustment of a number.
     *
     * @param   {String}    type    The type of adjustment.
     * @param   {Number}    value   The number.
     * @param   {Integer}   exp     The exponent (the 10 logarithm of the adjustment base).
     * @returns {Number}            The adjusted value.
     */
    function decimalAdjust(type, value, exp) {
        // If the exp is undefined or zero...
        if (typeof exp === 'undefined' || +exp === 0) {
            return Math[type](value);
        }
        value = +value;
        exp = +exp;
        // If the value is not a number or the exp is not an integer...
        if (isNaN(value) || !(typeof exp === 'number' && exp % 1 === 0)) {
            return NaN;
        }
        // Shift
        value = value.toString().split('e');
        value = Math[type](+(value[0] + 'e' + (value[1] ? (+value[1] - exp) : -exp)));
        // Shift back
        value = value.toString().split('e');
        return +(value[0] + 'e' + (value[1] ? (+value[1] + exp) : exp));
    }

    // Decimal round
    if (!Math.round10) {
        Math.round10 = function(value, exp) {
            return decimalAdjust('round', value, exp);
        };
    }
    // Decimal floor
    if (!Math.floor10) {
        Math.floor10 = function(value, exp) {
            return decimalAdjust('floor', value, exp);
        };
    }
    // Decimal ceil
    if (!Math.ceil10) {
        Math.ceil10 = function(value, exp) {
            return decimalAdjust('ceil', value, exp);
        };
    }

})();


function dateToString(fecha) {
    var d = new Date(fecha);
    var curr_date = d.getDate();
    var curr_month = d.getMonth();
    curr_month++;
    var curr_year = d.getFullYear();
    return (curr_date + "-" + curr_month + "-" + curr_year);
}
