const DEFAULT_RESSOURCE = "http://www.pingouinduturfu.fr/pokedle/";

function tryPokemon() {
    const pokemonToTry = $("#selectSearchInput").val();
    $.post("/play/splash_art/try",
        {pokemonName: pokemonToTry},
        function(data, status) {
            if (data.hasOwnProperty("error")) {
                console.log("erreur : " + data["error"]);
                return;
            }
            console.log(data)
            displayLineAnswer(data);
            $("#selectSearchInput").val("");
            getSprite();
        }
    );
}

function displayLineAnswer(data) {
    removeFromSelect(data["pokemon"]["id"]);
    const answerTable = $("#classic-game-answer-content");
    const prefix = "<ul class=\"answerLineContent\">";
    const suffix = "</ul>";
    const content = prefix +
        getHTMLValue("IMAGE", data["pokemon"]["linkIcon"]) +
        getHTMLValue("TEXT", data["pokemon"]["nameFr"]) +
        suffix;
    answerTable.prepend(content);
}


function removeFromSelect(id) {
    const elem = $("#select_pokemon_" + id);
    if (elem != null)
        elem.remove();
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

const FILTER_RESET = "----";
const FILTER_NULL = "";

function filterFunction() {
    let input, filter, contentDiv, buttonItems;
    input = $("#selectSearchInput");

    if(input.val() === FILTER_NULL)
        filter = FILTER_RESET;
    else
        filter = input.val().toUpperCase();

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



function getSprite() {
    $.ajax({
        type: "POST",
        url: "/play/splash_art/partial_splash_art",
        datatype: "image/png",
        contentType: "text/plain",
        success: function (result) {
            $("#splash-art-image").attr("src", "data:image/png;base64," + result);
        }
    });
}

$(document).ready(() => {
    // get all pokemon played
    $.post("/play/splash_art/previous",
        {},
        function(data, status) {
            console.log(data);
            for (let i = 0; i < data.length; i++) {
                displayLineAnswer(data[i]);
            }
            getSprite();
        }
    );
});
