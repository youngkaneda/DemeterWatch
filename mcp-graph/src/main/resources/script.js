function readTextFile(file, callback) {
    var rawFile = new XMLHttpRequest();
    rawFile.overrideMimeType("application/json");
    rawFile.open("GET", file, true);
    rawFile.onreadystatechange = function() {
        if (rawFile.readyState === 4 && rawFile.status == "200") {
            callback(rawFile.responseText);
        };
    };
    rawFile.send(null);
};
readTextFile("elements.json", function(text){
    var parsedData = JSON.parse(text);
    console.log(parsedData.nodes);
    var data = {
        nodes: parsedData.nodes,
        edges: parsedData.edges
    };
    var options = {};
    var container = document.getElementById('graph');
    var network = new vis.Network(container, data, options);
});
var ns = document.getElementById('nodes');