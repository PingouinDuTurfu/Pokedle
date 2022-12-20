const FILTER_RESET = "----";
const FILTER_NULL = "";

function tryPokemon() {
    const pokemonToTry = $("#selectSearchInput").val();
    $("#ul-main-message").empty();
    $.post("/play/splash_art/try",
        {pokemonName: pokemonToTry},
        function(data, status) {
            if (data.hasOwnProperty("error")) {
                $("#ul-main-message").append("<li class=\"error\"><span>" + data["error"] + "</span></li>");
                return;
            }
            displayLineAnswer(data);
            $("#selectSearchInput").val(FILTER_NULL);
            getSprite();
            if(data["is_same"] === true)
                successDisplay(data["score"]);
        }
    );
}

function displayLineAnswer(data) {
    removeFromSelect(data["pokemon"]["id"]);
    const answerTable = $("#splash-game-answer-content");
    const prefix = "<div class=\"answerItem\">";
    const suffix = "</div>";
    const content = prefix +
        "<img class=\"itemImage\" src='" + data["pokemon"]["linkIcon"] + "' alt=\"Pokemon icon\">" +
        suffix;
    answerTable.prepend(content);
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

function successDisplay(score) {
    $("#splash-art-image").css("filter", "drop-shadow(2px 4px 6px var(--default-black))");
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
    $.post("/play/splash_art/previous",
        {},
        function(data, status) {
            console.log(data);
            for (const dataLine of data) {
                displayLineAnswer(dataLine);
                if(dataLine["is_same"] === true)
                    successDisplay(dataLine["score"]);
            }
            getSprite();
        }
    );
});
