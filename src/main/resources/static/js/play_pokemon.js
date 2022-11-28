const DEFAULT_RESSOURCE = "http://***REMOVED***/pokedle/";

function tryPokemon() {
    const pokemonToTry = $("#selectSearchInput").val();
    $.post("/play/official/try",
        {pokemonName: pokemonToTry},
        function(data, status) {
            displayLineAnswer(data);
        }
    );
}

function displayLineAnswer(data) {
    const answerTable = $("#classic-game-answer-content");
    const prefix = "<ul class=\"answerLineContent\">";
    const suffix = "</ul>";
    const content = prefix +
        getHTMLDifference("IMAGE", getHTMLValue("IMAGE", data["pokemon"]["linkIcon"])) +
        getHTMLDifference(null, getHTMLValue("TEXT", data["pokemon"]["nameFr"])) +
        getHTMLDifference(data["difference"]["color"], getHTMLValue("TEXT", data["pokemon"]["color"])) +
        getHTMLDifference(data["difference"]["shape"], getHTMLValue("IMAGE",data["pokemon"]["shape"]["linkIcon"])) +
        getHTMLDifference(data["difference"]["type"], getHTMLValue("IMAGE", data["pokemon"]["type1"]["linkIcon"], data["pokemon"]["type2"] == null ? null : data["pokemon"]["type2"]["linkIcon"])) +
        getHTMLDifference(data["difference"]["height"], getHTMLValue("TEXT", data["pokemon"]["height"])) +
        getHTMLDifference(data["difference"]["weight"], getHTMLValue("TEXT", data["pokemon"]["weight"])) +
        getHTMLDifference(null, "0") +
        suffix;
    answerTable.append(content);
}

function getHTMLValue(type, value1, value2) {
    switch (type) {
        case "IMAGE":
            if (value2 != null)
                return "<img class=\"itemImage\" src='" + DEFAULT_RESSOURCE + value1 + "'><img class=\"itemImage\" src='" + DEFAULT_RESSOURCE + value2 + "'>";
            else
                return "<img class=\"itemImage\" src='" + DEFAULT_RESSOURCE + value1 + "'>";
        case "TEXT":
            if(value2 != null)
                return "<span class=\"itemValue\">" + value1 + "</span><span class=\"itemValue\">" + value2 + "</span>";
            else
                return "<span class=\"itemValue\">" + value1 + "</span>";
        default:
            return "<span class=\"itemValue\">" + value1 + "</span>";
    }
}

function getHTMLDifference(difference, value) {
    const prefix = "<li class=\"answerItem\">";
    const suffix = "</li>";

    switch (difference) {
        case "INVALID":
            return prefix + "<div class=\"invalid\">" + value + "</div>" + suffix;
        case "VALID":
            return prefix + "<div class=\"valid\">" + value + "</div>" + suffix;
        case "UPPER":
            return prefix + "<div class=\"upper\"><img class=\"arrowUpper\" src='/img/arrow.png'>" + value + "</div>" + suffix;
        case "LOWER":
            return prefix + "<div class=\"lower\"><img class=\"arrowLower\" src='/img/arrow.png'>" + value + "</div>" + suffix;
        case "PARTIAL":
            return prefix + "<div class=\"partial\">" + value + "</div>" + suffix;
        case "NEUTRAL":
            return prefix + "<div class=\"neutral\">" + value + "</div>" + suffix;
        default:
            return prefix + value + suffix;
    }
}

const FILTER_RESET = "----";
const FILTER_NULL = "";

function filterFunction() {
    let input, filter, contentDiv, buttonItems;
    input = document.getElementById("selectSearchInput");

    if(input.value === FILTER_NULL)
        filter = FILTER_RESET;
    else
        filter = input.value.toUpperCase();

    contentDiv = document.getElementById("classicGameSearchContent");
    buttonItems = contentDiv.getElementsByTagName("button");

    for (const item of buttonItems) {
        const textValue = item.getElementsByTagName("span").item(0).textContent;
        if(!textValue.toUpperCase().startsWith(filter) || textValue.toUpperCase() === filter)
            item.style.display = "none";
        else
            item.style.display = "flex";
    }
}

function selectPokemon(select) {
    $("#selectSearchInput").val(select.name);
    filterFunction();
}

$(document).ready(() => {
    $.post("/play/official/previous",
        {},
        function(data, status) {
            console.log(data);
            for (let i = 0; i < data.length; i++) {
                displayLineAnswer(data[i]);
            }
        }
    );
});
