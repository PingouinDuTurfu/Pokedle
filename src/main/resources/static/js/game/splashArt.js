const DEFAULT_RESSOURCE = "http://***REMOVED***/pokedle/";

function tryPokemon() {
    const pokemonToTry = $("#selectSearchInput").val();
    $.post("/play/splash_art/try",
        {pokemonName: pokemonToTry},
        function(data, status) {
            if (data.hasOwnProperty("error")) {
                $(".ul-main-error").append("<li><span>" + data["error"] + "</span></li>");
                return;
            }
            displayLineAnswer(data);
            $("#selectSearchInput").val("");
            getSprite();
        }
    );
}

function displayLineAnswer(data) {
    removeFromSelect(data["pokemon"]["id"]);
    const answerTable = $("#splash-game-answer-content");
    const prefix = "<div class=\"answerItem\">";
    const suffix = "</div>";
    const content = prefix +
        "<img class=\"itemImage\" src='" + DEFAULT_RESSOURCE + data["pokemon"]["linkIcon"] + "' alt=\"Pokemon icon\">" +
        suffix;
    answerTable.prepend(content);
    console.log("a")
}

function removeFromSelect(id) {
    const elem = $("#select_pokemon_" + id);
    if (elem != null)
        elem.remove();
}

function selectPokemon(select) {
    $("#selectSearchInput").val(select.name);
    filter("splashGameSearchContent");
}

function filter(selectId) {
    const FILTER_RESET = "----";
    const FILTER_NULL = "";

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
            for (let i = 0; i < data.length; i++) {
                displayLineAnswer(data[i]);
            }
            getSprite();
        }
    );
});
