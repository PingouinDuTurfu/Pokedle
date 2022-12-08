const DEFAULT_RESOURCE = "http://www.pingouinduturfu.fr/pokedle/";
const FILTER_RESET = "----";
const FILTER_NULL = "";

function tryPokemon() {
    const pokemonToTry = $("#selectSearchInput").val();
    $("#ul-main-message").empty();
    $.post("/play/classic/try",
        {pokemonName: pokemonToTry},
        function(data, status) {
            if(data.hasOwnProperty("error")) {
                $("#ul-main-message").append("<li class=\"error\"><span>" + data["error"] + "</span></li>");
                return;
            }
            displayLineAnswer(data);
            $("#selectSearchInput").val(FILTER_NULL);
            if(data["is_same"] === true)
                successDisplay(data["score"]);
        });
}

function displayLineAnswer(data) {
    removeFromSelect(data["pokemon"]["id"]);
    const answerTable = $("#classic-game-answer-content");
    const prefix = "<ul class=\"answerLineContent\">";
    const suffix = "</ul>";
    const content = prefix +
        getHTMLDifference("NEUTRAL", "itemIcon", getHTMLValue("IMAGE", data["pokemon"]["linkIcon"])) +
        getHTMLDifference("NEUTRAL", "itemName", getHTMLValue("TEXT", data["pokemon"]["nameFr"])) +
        getHTMLDifference(data["difference"]["color"], "itemColor", getHTMLValue("COLOR", data["pokemon"]["color"])) +
        getHTMLDifference(data["difference"]["shape"], "itemShape", getHTMLValue("IMAGE",data["pokemon"]["shape"]["linkIcon"])) +
        getHTMLDifference(data["difference"]["type"], "itemType", getHTMLValue("IMAGE", data["pokemon"]["type1"]["linkIcon"], data["pokemon"]["type2"] == null ? null : data["pokemon"]["type2"]["linkIcon"])) +
        getHTMLDifference(data["difference"]["height"], "itemHeight", getHTMLValue("TEXT", data["pokemon"]["height"])) +
        getHTMLDifference(data["difference"]["weight"], "itemWeight", getHTMLValue("TEXT", data["pokemon"]["weight"])) +
        getHTMLDifference(data["difference"]["generation"], "itemGeneration", getHTMLValue("TEXT", data["pokemon"]["generation"])) +
        suffix;
    answerTable.prepend(content);
}

function getHTMLValue(type, value1, value2) {
    switch (type) {
        case "IMAGE":
            if (value2 != null)
                return "<div class=\"itemTopLeft\"><img class=\"itemImage\" src='" + DEFAULT_RESOURCE + value1 + "'></div><div class=\"itemBottomRight\"><img class=\"itemImage\" src='" + DEFAULT_RESOURCE + value2 + "'></div>";
            else
                return "<img class=\"itemImage\" src='" + DEFAULT_RESOURCE + value1 + "'>";
        case "TEXT":
            if(value2 != null)
                return "<span class=\"itemValue\">" + value1 + "</span><span class=\"itemValue\">" + value2 + "</span>";
            else
                return "<span class=\"itemValue\">" + value1 + "</span>";
        case "COLOR":
            return "<span class=\"itemValue\" style=\"background-color: " + (value1 !== "brown" ? value1 : "#8b4513") + "\"></span>";
        default:
            return "<span class=\"itemValue\">" + value1 + "</span>";
    }
}

function getHTMLDifference(difference, className, value) {
    const prefix = "<li class=\"answerItem\">";
    const suffix = "</li>";

    switch (difference) {
        case "INVALID":
            return prefix + "<div class=\"invalid "  + className + "\">" + value + "</div>" + suffix;
        case "VALID":
            return prefix + "<div class=\"valid "  + className + "\">" + value + "</div>" + suffix;
        case "UPPER":
            return prefix + "<div class=\"upper "  + className + "\"><img class=\"arrowUpper\" src='/img/arrow.png'>" + value + "</div>" + suffix;
        case "LOWER":
            return prefix + "<div class=\"lower "  + className + "\"><img class=\"arrowLower\" src='/img/arrow.png'>" + value + "</div>" + suffix;
        case "PARTIAL":
            return prefix + "<div class=\"partial "  + className + "\">" + value + "</div>" + suffix;
        case "NEUTRAL":
            return prefix + "<div class=\"neutral "  + className + "\">" + value + "</div>" + suffix;
        default:
            return prefix + value + suffix;
    }
}

$.initialize(".itemName", function (e) {
    resize_to_fit($(this));
});

function resize_to_fit(element){
    const child = element.children(":first");
    child.css('fontSize', parseFloat(child.css('font-size')) - 1);

    if(child.width() >= element.width() - 10){
        resize_to_fit(element);
    }
}

function removeFromSelect(id) {
    const elem = $("#select_pokemon_" + id);
    if (elem != null)
        elem.remove();
}

function selectPokemon(select) {
    $("#selectSearchInput").val(select.name);
    filter("classicGameSearchContent");
}

function successDisplay(score) {
    $(".search").css("display", "none");
    $(".success")
        .css("display", "flex")
        .append("<span class=\"successContent\">Félicitation vous avez fini avec un score de <span class=\"successScore\">" + score + "</span></span>");
}

function filter(selectId) {
    let input, filter, contentDiv, buttonItems;
    input = $("#selectSearchInput");

    if(input.val() === FILTER_NULL)
        filter = FILTER_RESET;
    else
        filter = input.val().toUpperCase();

    contentDiv = document.getElementById(selectId);
    buttonItems = contentDiv.getElementsByTagName("button");

    for (const item of buttonItems) {
        const textValue = item.getElementsByTagName("span").item(0).textContent;
        if(!textValue.toUpperCase().startsWith(filter) || textValue.toUpperCase() === filter)
            item.style.display = "none";
        else
            item.style.display = "flex";
    }
}

$(document).ready(() => {
    $.post("/play/classic/previous",
        {},
        function(data, status) {
            for (const dataLine of data) {
                displayLineAnswer(dataLine);
                if(dataLine["is_same"] === true)
                    successDisplay(dataLine["score"]);
            }
        }
    );
});